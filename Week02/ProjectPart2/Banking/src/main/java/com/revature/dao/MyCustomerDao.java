package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.revature.objects.Bank_customer;
import com.revature.util.ConnectionUtil;

public class MyCustomerDao implements CustomerDao {
	private Statement myStatement = null;
	
	public void newCustomer(Bank_customer cust) {
		try(Connection conn = ConnectionUtil.getConnection();){
						
			String sql = "INSERT INTO BANK_CUSTOMER(SINCE, USERNAME, PASS, ROLEID, PERSONID, ISACTIVE) VALUES(" +
					"SYSDATE , '" + cust.getUserName() + "', '" + cust.getPassword() + "', " + 
					cust.getRoleID() + ", " + cust.getPersonID() + ", " + cust.isActive() + ")";
								
			myStatement = conn.createStatement();
			
			int created = myStatement.executeUpdate(sql);

			System.out.println(created + " customer created");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}


	public Bank_customer findById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Bank_customer cust = null;
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM Bank_customer WHERE customerid = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			
			while(rs.next()){
				cust = new Bank_customer( 
						rs.getInt("customerID"), 
						rs.getDate("since"), 
						rs.getString("userName"), 
						rs.getString("pass"), 
						rs.getInt("roleID"), 
						rs.getInt("personID"), 
						rs.getInt("isActive")
						);			
			}
			

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return cust;
	}

	public void validateCustomer(int customerid) {
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "UPDATE BANK_CUSTOMER SET ISACTIVE = 1 WHERE CUSTOMERID = " + customerid;
								
			myStatement = conn.createStatement();
			
			int validated = myStatement.executeUpdate(sql);

			System.out.println(validated + " customer validated");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}
	
	public void promoteCustomer(int customerid) {
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "UPDATE BANK_CUSTOMER SET ROLEID = 0 WHERE CUSTOMERID = " + customerid;
								
			myStatement = conn.createStatement();
			
			int promoted = myStatement.executeUpdate(sql);

			System.out.println(promoted + " customer promoted");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}			
	}

	public int deleteCustomer(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Bank_customer> listOfAllCustomer() {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		ArrayList<Bank_customer>listCustomers = new ArrayList<>();
	
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM Bank_customer";
			ps = conn.prepareStatement(sql);
			//ps.setString(1, accountNber);
			rs = ps.executeQuery();
			
			while(rs.next()){
				listCustomers.add(new Bank_customer( 
						rs.getInt("customerID"), 
						rs.getDate("since"), 
						rs.getString("userName"), 
						rs.getString("pass"), 
						rs.getInt("roleID"), 
						rs.getInt("personID"), 
						rs.getInt("isActive")
						));			
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return listCustomers;
	}

	public Bank_customer login(String username, String password) {
		
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Bank_customer cust = null;
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM Bank_customer WHERE username = ? and pass = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				while(rs.next()){
					cust = new Bank_customer( 
						rs.getInt("customerID"), 
						rs.getDate("since"), 
						rs.getString("userName"), 
						rs.getString("pass"), 
						rs.getInt("roleID"), 
						rs.getInt("personID"), 
						rs.getInt("isActive")
						);			
			}
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return cust;
	}
}
