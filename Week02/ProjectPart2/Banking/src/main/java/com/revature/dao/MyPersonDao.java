package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.revature.objects.Person;
import com.revature.util.ConnectionUtil;

public class MyPersonDao implements PersonDao {
	private Statement myStatement = null;
	public void newPerson(Person person) {
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "INSERT INTO PERSON(FNAME, LNAME, ADDRESS, CITY, STATE, AREACODE, " + 
				    "COUNTRY, EMAIL, PHONE, SSN, DOB) VALUES('" + 
				    person.getFName() + "', '" + person.getLName() + "', '" + person.getAdress() + "', '" + 
				    person.getCity() + "', '" + person.getState() + "', '" + person.getAreacode() + "', '" + 
				    person.getCountry() + "', '" + person.getEmail() + "', '" + person.getPhone() + "', '" + 
				    person.getSsn() + "', TO_DATE('01/01/1980', 'mm/dd/yyyy'))";
		    		//person.getSsn() + "', TO_DATE('" + person.getDob() + "', 'mm/dd/yyyy')";
			
			myStatement = conn.createStatement();
			int created = myStatement.executeUpdate(sql);
			
			System.out.println(created + " persons created");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}

	public Person findById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Person person = null;
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM person WHERE personid = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			int x = 0;
			
			while(rs.next()){

				person = new Person( 
						rs.getInt("PERSONID"), 
						rs.getString("FNAME"), 
						rs.getString("LNAME"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"), 
						rs.getString("AREACODE"), 
						rs.getString("COUNTRY"), 
						rs.getString("EMAIL"), 
						rs.getString("PHONE"), 
						rs.getString("SSN"), 
						rs.getDate("DOB")
						);
				
			}
			
			System.out.println(x);

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return person;
	}

	public void updatePerson(Person person) {
		// TODO Auto-generated method stub
		
	}

	public List<Person> listOfAllPerson() {
		// TODO Auto-generated method stub
		return null;
	}

	public int deletePerson(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
