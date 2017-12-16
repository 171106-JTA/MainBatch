package p1.revature.beans;

import java.sql.Date;

public class ReimbursableEvent extends Bean{
	private int eventID;
	private int typeID;
	private String typeName;
	private Date startDate, endDate;
	private int locationID;
	private String description;
	private double cost;
	private boolean isSatisfactory, presentationNeeded, presentationPassing;
	private double numericGrade;
	private ReimbursableExpenseType type;
	private Location location;
	
	
	public ReimbursableEvent() {
		super();
	}

	// TODO: generate constructors
	/**
	 * A comprehensive constructor, using eventID instead of eventName.
	 * @param eventID : the ID of the ReimbursableEvent
	 * @param typeID : ID of the ReimbursableExpenseType
	 * @param startDate : the date this event started
	 * @param endDate : the date this event ended
	 * @param locationID : the id of the location of this event
	 * @param description : description of this event
	 * @param cost
	 * @param isSatisfactory
	 * @param presentationNeeded
	 * @param presentationPassing
	 * @param numericGrade
	 */
	public ReimbursableEvent(int eventID, int typeID, Date startDate, Date endDate, int locationID, String description,
			double cost, boolean isSatisfactory, boolean presentationNeeded, boolean presentationPassing,
			double numericGrade) {
		super();
		this.eventID = eventID;
		this.typeID = typeID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.locationID = locationID;
		this.description = description;
		this.cost = cost;
		this.isSatisfactory = isSatisfactory;
		this.presentationNeeded = presentationNeeded;
		this.presentationPassing = presentationPassing;
		this.numericGrade = numericGrade;
	}
	
	public ReimbursableEvent(int eventID, int typeID, String startDateString, String endDateString, int locationID,
			String description, double cost, boolean isSatisfactory, boolean presentationNeeded, 
			boolean presentationPassing, double numericGrade) throws IllegalArgumentException
	{
		// validate the dates...
		validateDates(startDateString, endDateString);
		this.eventID = eventID;
		this.typeID = typeID;
		this.locationID = locationID;
		this.description = description;
		this.cost = cost;
		this.isSatisfactory = isSatisfactory;
		this.presentationNeeded = presentationNeeded;
		this.presentationPassing = presentationPassing;
		this.numericGrade = numericGrade;
	}
	
	public ReimbursableEvent(int eventID, String typeName, Date startDate, Date endDate, String description,
			double cost, boolean isSatisfactory, boolean presentationNeeded, boolean presentationPassing,
			double numericGrade, ReimbursableExpenseType type, Location location) {
		super();
		this.eventID = eventID;
		this.typeName = typeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.cost = cost;
		this.isSatisfactory = isSatisfactory;
		this.presentationNeeded = presentationNeeded;
		this.presentationPassing = presentationPassing;
		this.numericGrade = numericGrade;
		this.type = type;
		this.location = location;
	}
	
	public ReimbursableEvent(String typeName, Date startDate, Date endDate, String description, double cost,
			boolean isSatisfactory, boolean presentationNeeded, boolean presentationPassing, double numericGrade,
			ReimbursableExpenseType type, Location location) {
		super();
		this.typeName = typeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.cost = cost;
		this.isSatisfactory = isSatisfactory;
		this.presentationNeeded = presentationNeeded;
		this.presentationPassing = presentationPassing;
		this.numericGrade = numericGrade;
		this.type = type;
		this.location = location;
	}

	/**
	 * Everything but the event id. 
	 * @param typeID
	 * @param typeName
	 * @param startDate
	 * @param endDate
	 * @param locationID
	 * @param description
	 * @param cost
	 * @param isSatisfactory
	 * @param presentationNeeded
	 * @param presentationPassing
	 * @param numericGrade
	 */
	public ReimbursableEvent(int typeID, String typeName, Date startDate, Date endDate, int locationID,
			String description, double cost, boolean isSatisfactory, boolean presentationNeeded,
			boolean presentationPassing, double numericGrade) {
		super();
		this.typeID = typeID;
		this.typeName = typeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.locationID = locationID;
		this.description = description;
		this.cost = cost;
		this.isSatisfactory = isSatisfactory;
		this.presentationNeeded = presentationNeeded;
		this.presentationPassing = presentationPassing;
		this.numericGrade = numericGrade;
	}

	/**
	 * Same as above, but without eventID, and assuming the end user doesn't have to present anything
	 * @param typeID
	 * @param startDate
	 * @param endDate
	 * @param locationID
	 * @param description
	 * @param cost
	 * @param isSatisfactory
	 * @param numericGrade
	 */
	public ReimbursableEvent(int typeID, Date startDate, Date endDate, int locationID, String description, double cost,
			boolean isSatisfactory, double numericGrade) {
		super();
		this.typeID = typeID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.locationID = locationID;
		this.description = description;
		this.cost = cost;
		this.isSatisfactory = isSatisfactory;
		this.numericGrade = numericGrade;
		// stuff I actually wrote
		eventID = Bean.NULL;
		presentationNeeded = false;	// presentation not needed by default
		presentationPassing = true; // since presentation not needed, it passed by default
	}

	/**
	 * No event id, no type id, no presentation needed
	 * @param typeName
	 * @param startDate
	 * @param endDate
	 * @param locationID
	 * @param description
	 * @param cost
	 * @param isSatisfactory
	 * @param numericGrade
	 */
	public ReimbursableEvent(String typeName, Date startDate, Date endDate, int locationID, String description,
			double cost, boolean isSatisfactory, double numericGrade) {
		super();
		this.typeName = typeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.locationID = locationID;
		this.description = description;
		this.cost = cost;
		this.isSatisfactory = isSatisfactory;
		this.numericGrade = numericGrade;
	}

	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public boolean isSatisfactory() {
		return isSatisfactory;
	}
	public void setSatisfactory(boolean isSatisfactory) {
		this.isSatisfactory = isSatisfactory;
	}
	public boolean isPresentationNeeded() {
		return presentationNeeded;
	}
	public void setPresentationNeeded(boolean presentationNeeded) {
		this.presentationNeeded = presentationNeeded;
	}
	public boolean isPresentationPassing() {
		return presentationPassing;
	}
	public void setPresentationPassing(boolean presentationPassing) {
		this.presentationPassing = presentationPassing;
	}
	public double getNumericGrade() {
		return numericGrade;
	}
	public void setNumericGrade(double numericGrade) {
		this.numericGrade = numericGrade;
	}

	public ReimbursableExpenseType getType() {
		return type;
	}

	public void setType(ReimbursableExpenseType type) {
		this.type = type;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	private void validateDates(String startDateString, String endDateString) throws IllegalArgumentException
	{
		
		this.startDate = Date.valueOf(startDateString);
		this.endDate   = Date.valueOf(endDateString);
		
	}

	@Override
	public String toString() {
		return "ReimbursableEvent [eventID=" + eventID + ", typeID=" + typeID + ", typeName=" + typeName
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", locationID=" + locationID + ", description="
				+ description + ", cost=" + cost + ", isSatisfactory=" + isSatisfactory + ", presentationNeeded="
				+ presentationNeeded + ", presentationPassing=" + presentationPassing + ", numericGrade=" + numericGrade
				+ ", type=" + type + ", location=" + location + "]";
	}
	
	
}
