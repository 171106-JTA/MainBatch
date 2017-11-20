package com.revature.dao;

import java.util.List;

import com.revature.objects.Person;

public interface PersonDao {

	public void newPerson(Person person);
	public Person findById(int id);
	public void updatePerson(Person person);
	public List<Person> listOfAllPerson();
	public int deletePerson(Integer id);

}
