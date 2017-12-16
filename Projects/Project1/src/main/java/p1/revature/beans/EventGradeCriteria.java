package p1.revature.beans;

import java.util.LinkedList;
import java.util.List;

public class EventGradeCriteria extends Bean {
	private int id, eventID;
	private String letterGrade;
	private double cutoffPercentage;
	private static List<EventGradeCriteria> rubric;
	public EventGradeCriteria() {
		super();
	}
	public EventGradeCriteria(int eventID, String letterGrade, double cutoffPercentage) {
		super();
		this.eventID = eventID;
		this.letterGrade = letterGrade;
		this.cutoffPercentage = cutoffPercentage;
	}
	public EventGradeCriteria(int id, int eventID, String letterGrade, double cutoffPercentage) {
		super();
		this.id = id;
		this.eventID = eventID;
		this.letterGrade = letterGrade;
		this.cutoffPercentage = cutoffPercentage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getLetterGrade() {
		return letterGrade;
	}
	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}
	public double getCutoffPercentage() {
		return cutoffPercentage;
	}
	public void setCutoffPercentage(double cutoffPercentage) {
		this.cutoffPercentage = cutoffPercentage;
	}
	
	public static List<EventGradeCriteria> getDefaultRubric()
	{
		if (rubric == null)
		{
			rubric = new LinkedList<>();
			rubric.add(new EventGradeCriteria(NULL, "A", 90));
			rubric.add(new EventGradeCriteria(NULL, "B", 80));
			rubric.add(new EventGradeCriteria(NULL, "C", 70));
			rubric.add(new EventGradeCriteria(NULL, "D", 60));
			rubric.add(new EventGradeCriteria(NULL, "F", 50));
		}
		return rubric;
	}
}
