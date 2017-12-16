package p1.revature.beans;

public class ReimbursableExpenseType extends Bean 
{
	private int id;
	private String name;
	private double coverage;
	/**
	 * 
	 * @param id : the primary key in the table
	 * @param name : the name of the ReimbursableEventType
	 * @param coverage : the percentage of this type of event that gets covered
	 */
	public ReimbursableExpenseType(int id, String name, double coverage) {
		super();
		this.id = id;
		this.name = name;
		this.coverage = coverage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCoverage() {
		return coverage;
	}
	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}
	@Override
	public String toString() {
		return "ReimbursableExpenseType [id=" + id + ", name=" + name + ", coverage=" + coverage + "]";
	}
	
}
