package extra.revature.lambdas;

public class Employee {
	private String name;
	private int age;
	private int ssn;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSsn() {
		return ssn;
	}
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	
	public Employee(String name, int age, int ssn) {
		super();
		this.name = name;
		this.age = age;
		this.ssn = ssn;
	}
	public String toString(){
		return ("Name: " + name + "|Age: " + age + "|ssn: " + ssn);
	}
}
