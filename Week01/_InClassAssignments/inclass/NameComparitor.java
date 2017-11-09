package n4.revature.inclass;

import java.util.Comparator;

public class NameComparitor implements Comparator<PersonName> {

	//Override compare method from Comparator interface
	@Override
	public int compare(PersonName p1, PersonName p2) {
		// TODO Auto-generated method stub
		return (p1.getLastName().compareTo(p2.getLastName())) * -1;
	}

	
}
