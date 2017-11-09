package n4.revature.inclass;

public class PersonName implements Comparable<PersonName> {

	//Data fields
	private String firstName;
	private String lastName;
	private int age;
	
	//No-arg Constructor
	public PersonName() {
		this("Bobbert", "Johnson", 30);
	}
	
	//Overloaded Constructor
	public PersonName(String firstName, String lastName, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	//Generate getters and setters
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	//Override toString() method
	@Override
	public String toString() {
		return "PersonName [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + "]";
	}
	
	//Override compareTo method from Comparable interface
	@Override
	public int compareTo(PersonName person) {
		if (this.getAge() < person.getAge()) {
			return -1;
		} else if(this.getAge() > person.getAge()) {
			return 1;
		}
		return 0;
	}

	
}
