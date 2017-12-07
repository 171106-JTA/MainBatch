package com.revature.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.revature.daos.ApprovalStatusesDAO;
import com.revature.daos.FacilitiesDAO;
import com.revature.daos.PositionsDAO;
import com.revature.daos.ReimbursementEventsDAO;

public class Loader {
	private ArrayList<ReimbursementEvent> reimbursementevents;
	private ArrayList<Position> positions;
	private ArrayList<Facility> facilities;
	private ArrayList<ApprovalStatus> approvalstatuses;
	public Loader() throws IOException, SQLException {
		this.reimbursementevents = new ArrayList<ReimbursementEvent>();
		this.setReimbursementevents(ReimbursementEventsDAO.getReimbursementEvents());
		this.positions = new ArrayList<Position>();
		this.setPositions(PositionsDAO.getPositions());
		this.facilities = new ArrayList<Facility>();
		this.setFacilities(FacilitiesDAO.getFacilities());
		this.approvalstatuses = new ArrayList<ApprovalStatus>();
		this.setApprovalstatuses(ApprovalStatusesDAO.getApprovalStatuses());
	}
	public ArrayList<ReimbursementEvent> getReimbursementevents() {
		return reimbursementevents;
	}
	public void setReimbursementevents(ArrayList<ReimbursementEvent> reimbursementevents) {
		this.reimbursementevents = reimbursementevents;
	}
	public ArrayList<Position> getPositions() {
		return positions;
	}
	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}
	public ArrayList<Facility> getFacilities() {
		return facilities;
	}
	public void setFacilities(ArrayList<Facility> facilities) {
		this.facilities = facilities;
	}
	public ArrayList<ApprovalStatus> getApprovalstatuses() {
		return approvalstatuses;
	}
	public void setApprovalstatuses(ArrayList<ApprovalStatus> approvalstatuses) {
		this.approvalstatuses = approvalstatuses;
	}
	public String toJSON() {
		Iterator<ReimbursementEvent> eventIterator = this.getReimbursementevents().iterator();
		StringBuilder events = new StringBuilder();
		Iterator<Position> positionIterator = this.getPositions().iterator();
		StringBuilder pos = new StringBuilder();;
		Iterator<Facility> facilityIterator = this.getFacilities().iterator();
		StringBuilder facs = new StringBuilder();;
		Iterator<ApprovalStatus> statusIterator  = this.getApprovalstatuses().iterator();
		StringBuilder stats = new StringBuilder();;
		while(eventIterator.hasNext() && positionIterator.hasNext() && facilityIterator.hasNext() && statusIterator.hasNext()) {
			if(eventIterator.hasNext() && positionIterator.hasNext() && facilityIterator.hasNext() && statusIterator.hasNext()) {
				events.append(eventIterator.next().toJSON() + ", ");
				pos.append(positionIterator.next().toJSON() + ", ");
				facs.append(facilityIterator.next().toJSON() + ", ");
				stats.append(statusIterator.next().toJSON() + ", ");
			} else {
				events.append(eventIterator.next().toJSON());
				pos.append(positionIterator.next().toJSON());
				facs.append(facilityIterator.next().toJSON());
				stats.append(statusIterator.next().toJSON());
			}
		}
		return "{"
				+ "'reimbursementevents':" + events.toString() + ","
				+ "'positions':" + pos.toString() + ","
				+ "'facilities':" + facs.toString() + ","
				+ "'approvalstatuses':" + stats.toString()
				+ "}"; 
	}
}
