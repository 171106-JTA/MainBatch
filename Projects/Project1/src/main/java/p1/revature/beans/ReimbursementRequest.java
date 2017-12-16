package p1.revature.beans;

public class ReimbursementRequest extends Bean {
	private int id, requesterID;
	private double amount;
	private String status;
	private int reimbursableEventID;
	private ReimbursableEvent reimbursableEvent;
	
	/**
	 * Default constructor
	 */
	public ReimbursementRequest() {
		super();
	}
	/**
	 * Constructor for everything but the id. This is used to insert into the reimbursement request table
	 * @param requesterID : the ID of the Employee requesting reimbursement
	 * @param amount : the amount requested
	 * @param status : the status of the request
	 * @param reimbursableEventID : the id of the ReimbursableEvent the requester is trying to have covered
	 */
	public ReimbursementRequest(int requesterID, double amount, String status, int reimbursableEventID) {
		super();
		this.requesterID = requesterID;
		this.amount = amount;
		this.status = status;
		this.reimbursableEventID = reimbursableEventID;
	}
	
	/**
	 * Constructor for every field but the reimbursable event id. Used for rendering material client-side
	 * @param id
	 * @param requesterID
	 * @param amount
	 * @param status
	 * @param reimbursableEvent
	 */
	public ReimbursementRequest(int id, int requesterID, double amount, String status,
			ReimbursableEvent reimbursableEvent) {
		super();
		this.id = id;
		this.requesterID = requesterID;
		this.amount = amount;
		this.status = status;
		this.reimbursableEvent = reimbursableEvent;
	}
	/**
	 * Constructor for every field. Used for select queries.
	 * @param id : the primary key of this request
	 * @param requesterID : the ID of the Employee requesting reimbursement
	 * @param amount : the amount requested
	 * @param status : the status of the request
	 * @param reimbursableEventID : the id of the ReimbursableEvent the requester is trying to have covered
	 */
	public ReimbursementRequest(int id, int requesterID, double amount, String status, int reimbursableEventID) {
		super();
		this.id = id;
		this.requesterID = requesterID;
		this.amount = amount;
		this.status = status;
		this.reimbursableEventID = reimbursableEventID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRequesterID() {
		return requesterID;
	}
	public void setRequesterID(int requesterID) {
		this.requesterID = requesterID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getReimbursableEventID() {
		return reimbursableEventID;
	}
	public void setReimbursableEventID(int reimbursableEventID) {
		this.reimbursableEventID = reimbursableEventID;
	}
	public ReimbursableEvent getReimbursableEvent() {
		return reimbursableEvent;
	}
	public void setReimbursableEvent(ReimbursableEvent reimbursableEvent) {
		this.reimbursableEvent = reimbursableEvent;
	}
	@Override
	public String toString() {
		return "ReimbursementRequest [id=" + id + ", requesterID=" + requesterID + ", amount=" + amount + ", status="
				+ status + ", reimbursableEventID=" + reimbursableEventID + ", reimbursableEvent=" + reimbursableEvent
				+ "]";
	}

	
}
