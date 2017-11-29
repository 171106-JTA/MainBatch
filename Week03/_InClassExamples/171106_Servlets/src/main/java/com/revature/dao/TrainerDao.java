package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Trainer;
import com.revature.util.ConnectionUtil;

public class TrainerDao {
	public void insertTrainer(Trainer t){// throws ClassNotFoundException{
		/*
		 * Try-With-Resources
		 * In a try block, you can include any streams as a parameter and
		 * Java will manage it for you and close it automatically without you having
		 * to worry about it. Any class that implements autocloseable can do this.
		 */
		PreparedStatement ps = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "insert into trainer (trainer_username, trainer_password, trainer_email, trainer_fname, trainer_mname, trainer_lname)" + 
						"VALUES(?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, t.getUsername().toLowerCase());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getEmail().toLowerCase());
			ps.setString(4, t.getFname().toLowerCase());
			ps.setString(5, t.getMname().toLowerCase());
			ps.setString(6, t.getLname().toLowerCase());
			int affected = ps.executeUpdate();
			
			System.out.println(affected + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}
	public Trainer selectTrainerByUsername(String username){
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Trainer t = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM trainer WHERE trainer_username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username.toLowerCase());
			rs = ps.executeQuery();
			
			while(rs.next()){
				t = new Trainer( 
						rs.getInt(1),
						rs.getString(2),
						rs.getString(2),
						rs.getString(2),
						rs.getString(2),
						rs.getString(2),
						rs.getString(2),
						null
						);
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		return t;
	}

}
