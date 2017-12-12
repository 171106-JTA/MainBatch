package com.revature.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.revature.model.Constants;
import com.revature.model.RequestBean;
import com.revature.model.dao.*;

// Controlls p
public class ServiceController {

	private static Map<String, Map<String, String>> globalCaches = Collections
			.synchronizedMap(new HashMap<String, Map<String, String>>());
	private static Thread requestMonitor;
	// private static boolean writing = false;

	// static {
	// globalCaches.put(Constants.requestsCache, null);
	// globalCaches.put(Constants.eventsCache, null);
	// }

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static boolean createEvent(List<String> emps, String evtType, String critType, String dept, String start,
			String end, List<String> grades, List<String> points, String evtAmount) throws IOException {
		String grds = null;
		String pnts = null;
		if (evtType.equals(Constants.grading)) {
			if (grades != null && points != null) {
				if (grades.size() != points.size())
					return false;
				grds = String.join(",", grades);
				pnts = String.join(",", points);
			}
		} else {
			grds = "null";
			pnts = "null";
		}
		start += ":00";
		end += ":00";

		return new EventDao(String.join(",", emps), evtType, critType, dept,
				Timestamp.valueOf(start.replace("T", " ")).toString(),
				Timestamp.valueOf(end.replace("T", " ")).toString(), grds, pnts, evtAmount).insert(false);
	}

	public static boolean uploadFiles(String eventId, String file) throws IOException {
		return new EventDao().insertBlob(false, eventId, file);
	}

	public static boolean createMessage(String toId, String fromId, String subj, String msg, String created)
			throws IOException {
		return new EmployeeDao(toId, fromId, subj, msg, created).insertMessage(false);
	}

	public static boolean createRequestMessage(String toId, String fromId, String reqId, String subj, String msg,
			String created) throws IOException {
		return new EmployeeDao(toId, fromId, reqId, subj, msg, created).insertReqMessage(false);
	}

	public static boolean createRequest(String evtId, String empId, String reqType, String reqAmount)
			throws IOException, SQLException, ParseException {
		Integer reqId;
		Date date = new Date();
		reqId = new RequestDao(reqType, evtId, empId, sdf.format(date), reqAmount).insertGetId(false);
		if (reqId == null)
			return false;

		return generateMessage(reqId, empId, evtId);
	}

	private static boolean generateMessage(Integer reqId, String empId, String evtId)
			throws IOException, ParseException {
		Collection<Map<String, String>> maps;
		Map<String, String> map;
		String toId, start;
		maps = new EmployeeDao().fetchSuper(empId, false);
		if (maps == null || maps.isEmpty())
			return false;
		map = maps.iterator().next();
		if (map == null || map.isEmpty())
			return false;
		toId = map.values().iterator().next();

		maps = new EventDao().fetchEventStart(false, evtId);
		if (maps == null || maps.isEmpty())
			return false;
		map = maps.iterator().next();
		if (map == null || map.isEmpty())
			return false;

		start = map.values().iterator().next();

		Date eventD = sdf.parse(start);
		Calendar cal = Calendar.getInstance();
		cal.setTime(eventD);
		cal.add(Calendar.DAY_OF_MONTH, -Constants.URGENT_TIME);
		Date created = new Date();

		return createRequestMessage(toId, empId, reqId.toString(),
				generateSubject(created.compareTo(cal.getTime()) > 0, reqId.toString(), empId), generateMessage(empId),
				sdf.format(created));
	}

	private static boolean generateMessage(String reqId, String supId, String empId)
			throws IOException, ParseException {
		Collection<Map<String, String>> maps;
		Map<String, String> map;
		String toId, start;
		maps = new EmployeeDao().fetchSuper(supId, false);
		if (maps == null || maps.isEmpty())
			return false;
		map = maps.iterator().next();
		if (map == null || map.isEmpty())
			return false;
		toId = map.values().iterator().next();

		start = map.values().iterator().next();

		Date eventD = sdf.parse(start);
		Calendar cal = Calendar.getInstance();
		cal.setTime(eventD);
		cal.add(Calendar.DAY_OF_MONTH, -Constants.URGENT_TIME);
		Date created = new Date();

		String msg = toId.equals(Constants.executiveID) ? generateAggressiveMessage(supId) : generateMessage(empId);

		return createRequestMessage(toId, empId, reqId.toString(),
				generateSubject(created.compareTo(cal.getTime()) > 0, reqId.toString(), empId), msg,
				sdf.format(created));
	}

	private static boolean generateMessage(String reqId, String supId, String empId, String evtId)
			throws IOException, ParseException {
		Collection<Map<String, String>> maps;
		Map<String, String> map;
		String toId, start;
		maps = new EmployeeDao().fetchSuper(supId, false);
		if (maps == null || maps.isEmpty())
			return false;
		map = maps.iterator().next();
		if (map == null || map.isEmpty())
			return false;
		toId = map.values().iterator().next();

		start = map.values().iterator().next();

		Date eventD = sdf.parse(start);
		Calendar cal = Calendar.getInstance();
		cal.setTime(eventD);
		cal.add(Calendar.DAY_OF_MONTH, -Constants.URGENT_TIME);
		Date created = new Date();

		String msg = toId.equals(Constants.executiveID) ? generateAggressiveMessage(supId) : generateMessage(empId);

		return createRequestMessage(toId, empId, reqId.toString(),
				generateSubject(created.compareTo(cal.getTime()) > 0, reqId.toString(), empId), msg,
				sdf.format(created));
	}

	public static boolean processRequest(String req, String stat) throws IOException {
		Collection<Map<String, String>> maps = RequestDao.fetchEmpByRequest(req, false);
		Map<String, String> map;
		String empId, amount;
		int additionalAmount;
		Integer newAmount;
		if (maps != null && !maps.isEmpty()) {
			map = maps.iterator().next();
			if (map != null && !map.isEmpty()) {
				empId = map.values().iterator().next();

				maps = EmployeeDao.fetchAmount(empId, false);
				if (maps != null && !maps.isEmpty()) {
					map = maps.iterator().next();
					if (map != null && !map.isEmpty()) {
						amount = map.values().iterator().next();
						if (Integer.parseInt(stat) == Constants.approved) {
							additionalAmount = Constants.MAX_AMOUNT - Integer.parseInt(amount);
							newAmount = (Integer.parseInt(amount) + additionalAmount);
						} else {
							newAmount = Integer.parseInt(amount);
						}
						return new RequestDao(req, stat, newAmount.toString()).update(false);
					}
				}
			}
		}
		return false;
	}

	public static boolean escalateRequest(String reqId, String empId, String reportsTo, String evtId)
			throws IOException, ParseException {
		if (RequestDao.escalate(reqId, reportsTo, sdf.format(new Date()), false))
			return generateMessage(reqId, reportsTo, empId, evtId);
		return false;
	}

	public static String generateSubject(boolean urgent, String reqId, String empId) {
		StringBuilder sb = new StringBuilder();
		String req = "Request " + reqId + " for Employee " + empId;
		if (urgent)
			req = sb.append(Constants.URGENT_STR + req).toString();
		return req;
	}

	public static String generateMessage(String id) {
		return String.format(Constants.MsgStr, id);
	}

	public static String generateAggressiveMessage(String id) {
		return String.format(Constants.AggroMsgStr, id);
	}

	public static boolean formatMessage(String toId, String fromId, String subj, String msg, boolean urgent)
			throws IOException {
		if (urgent)
			subj = Constants.URGENT_STR + subj;
		return createMessage(toId, fromId, subj, msg, sdf.format(new Date()));
	}

	public static void startMonitors() {
		requestMonitor = new MonitorThread();
		requestMonitor.setDaemon(true);
		requestMonitor.start();

	}

	private static class MonitorThread extends Thread {

		@Override
		public void run() {
			while (true) {
				Collection<Map<String, String>> maps = RequestDao.fetchDates();
				for (Map<String, String> map : maps) {
					try {
						Date date = sdf.parse(map.get(Constants.createdDate));
						if (date.compareTo(new Date()) > 0) {
							escalateRequest(map.get(Constants.reqID), map.get(Constants.empID),
									map.get(Constants.reportsTo), map.get(Constants.eventID));
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}
}
