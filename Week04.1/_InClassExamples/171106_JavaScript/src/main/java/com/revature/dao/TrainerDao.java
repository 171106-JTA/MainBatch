package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Trainer;
import com.revature.util.ConnectionUtil;
import static com.revature.util.CloseStreams.close;

public class TrainerDao {
	public void insertNewTrainer(Trainer t){
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO trainer (trainer_username,trainer_password, trainer_email, trainer_fname, trainer_mname, trainer_lname)" +
					"VALUES(?,?,?,?,?,?)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getEmail());
			ps.setString(4, t.getFname());
			ps.setString(5, t.getMname());
			ps.setString(6, t.getLname());
			int affected = ps.executeUpdate();
			
			System.out.println(affected + " ROWS INSERTED");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}
	
	public Trainer selectTrainerByUsername(String username){
		Trainer t = null;
		String sql = "SELECT * FROM trainer WHERE trainer_username = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			while(rs.next()){
				t = new Trainer(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getDate(8)
						);
			}

			
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
		
		return t;
	}
}
