package com.revature.server.session.require;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;

public final class Require {
	public static void requireSelf(Request request) throws RequestException {
		String id = Long.toString(request.getUserId());
		
		// If has query then ID must apply to self
		if (request.getQuery() != null)
			requireQuery(new String[] { Info.USERID, User.ID }, new String[] { id, id }, request);
	}
	
	public static void requireAllQuery(String[] params, Request request) throws RequestException {
		validateAll(params, request.getQuery(), request);
	}
	
	public static void requireAllQuery(String[] params, String[] values, Request request) throws RequestException {
		validateAll(params, values, request.getQuery(), request);
	}
	
	public static void requireAllTransaction(String[] params, Request request) throws RequestException {
		validateAll(params, request.getTransaction(), request);
	}
	
	public static void requireAllTransaction(String[] params, String[] values, Request request) throws RequestException {
		validateAll(params, values, request.getQuery(), request);
	}
	
	public static void requireQuery(String[] params, Request request) throws RequestException {
		validateAll(params, request.getQuery(), request);
	}
	
	public static void requireQuery(String[] params, String[] values, Request request) throws RequestException {
		validateAll(params, values, request.getQuery(), request);
	}
	
	public static void requireTransaction(String[] params, Request request) throws RequestException {
		validate(params, request.getTransaction(), request);
	}
	
	public static void requireTransaction(String[] params, String[] values, Request request) throws RequestException {
		validate(params, values, request.getTransaction(), request);
	}
	
	public static void require(String[] params, String[] values, FieldParams data, Request request) throws RequestException {
		validate(params, values, data, request);
	}
	
	public static void require(String[] params, Request request) throws RequestException {
		requireQuery(params, request);
		requireTransaction(params, request);
	}
	
	public static void require(String[] params, String[] values, Request request) throws RequestException {
		requireQuery(params, values, request);
		requireTransaction(params, values, request);
	}
	
	public static void require(Checkpoint[] checkpoints, Request request) throws RequestException {
		int index = Collections.indexOfSubList(Arrays.asList(checkpoints), Arrays.asList(new Checkpoint[] {request.getCheckpoint()}));
		
		// If we did not find checkpoint
		if (index < 0) 
			throw new RequestException(request, "You do not have to required permissions to perform this action");
	}
	
	public static void require(Checkpoint[] checkpoints, Checkpoint checkpoint, Request request) throws RequestException {
		int index = Collections.indexOfSubList(Arrays.asList(checkpoints), Arrays.asList(new Checkpoint[] { checkpoint }));
		
		// If we did not find checkpoint
		if (index < 0) 
			throw new RequestException(request, "You do not have to required permissions to perform this action");
	}
	
	public static void requireAll(String[] params, Request request) throws RequestException {
		requireAllQuery(params, request);
		requireAllTransaction(params, request);
	}
	
	public static void requireAll(String[] params, String[] values, FieldParams data, Request request) throws RequestException {
		validateAll(params, values, data, request);
	}
	
	public static void requireUnique(String table, FieldParams query, Request request) throws RequestException {
		if (Server.database.select(table, query).size() > 0)
			throw new RequestException(request, "Data with values specified already exist!");
	}
	
	public static void requireExists(String table, FieldParams query, Request request) throws RequestException {
		if (Server.database.select(table, query).size() == 0)
			throw new RequestException(request, "Data with values specified does not exist!");
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private static void validateAll(String[] params, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParams(params, requestParams);
		
		// Do we have any errors?
		if (errors.size() > 0) {
			String message = "Request missing params=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	private static void validateAll(String[] params, String[] values, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParamValues(params, values, requestParams);

		// Do we have any errors?
		if (errors.size() > 0) {
			String message = "Request has invalid values for params=[" + String.join(",", errors) + "] ";
			throw new RequestException(request, message);
		}
	}
	
	private static void validate(String[] params, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParams(params, requestParams);
		
		// Do we have any errors?
		if (errors.size() == params.length) {
			String message = "Request requires at least one of the following params=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	private static void validate(String[] params, String[] values, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParamValues(params, values, requestParams);
		
		// Do we have any errors?
		if (errors.size() == values.length) {
			String message = "Request requires at least one of the following params with currect value=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	private static List<String> validateRequestParams(String[] params, FieldParams requestParams) {
		List<String> errors = new ArrayList<>();
		String value;
		
		// Request parameters are REQUIRED
		if (requestParams == null) 
			errors.addAll(Arrays.asList(params));
		else {
			// Check if request has required parameters 
			for (String param : params) {
				if (!requestParams.containsKey(param)) {
					errors.add(param);
				} else {
					if ((value = requestParams.get(param)) == null || value.length() == 0)
						errors.add(param + " <cannot be null or empty!>");
				}
			}
		}
		return errors;
	}
	
	private static List<String> validateRequestParamValues(String[] params, String[]values, FieldParams requestParams) {
		List<String> errors = new ArrayList<>();
		Iterator<String> itParams;
		Iterator<String> itValues;
		String param;
		String value;
		
		// Request parameters are REQUIRED
		if (requestParams == null) 
			errors.addAll(Arrays.asList(params));
		else {
			// Server should not run if required parameters not properly entered.
			assert params.length == values.length;
			
			itParams = Arrays.asList(params).iterator();
			itValues = Arrays.asList(values).iterator();
			
			// Check if request has required parameters 
			for (;itParams.hasNext();) {
				param = itParams.next();
				value = itValues.next();
				
				// Check if parameter not missing
				if (requestParams.containsKey(param)) {
					// Check if value valid 
					if (!value.equals(requestParams.get(param))) 
						errors.add(param);
				}
			}
		}
		
		return errors;
	}
	
}
