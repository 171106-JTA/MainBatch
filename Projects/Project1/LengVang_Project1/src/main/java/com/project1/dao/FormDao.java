package com.project1.dao;

import static com.project1.util.CloseStreams.close;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

import com.project1.classes.Form;
import com.project1.util.ConnectionUtil;

public class FormDao {
	private Statement stmt = null;

	public void createForm(Form currForm) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			PreparedStatement ps = null;

			String sql = "INSERT INTO forms (emp_id, amount, rem_type, curr_viewer, date_sent, status, file_doc, file_name, alter_amount, alter_approve) "
					+ "VALUES('" + currForm.getEmployeeID() + "', '" + currForm.getAmount() + "', '"
					+ currForm.getTypeRem() + "', '" + currForm.getCurrViewer() + "', CURRENT_TIMESTAMP, '"
					+ currForm.getStatus() + "'" + ", ?, '" + currForm.getFileName() + "'"
							+ ",'" + currForm.getAlteredAmount() + "', '" + currForm.getAlterApproved() + "' )";
			ps = conn.prepareStatement(sql);
			ps.setBinaryStream(1, currForm.getDocument().getInputStream(), (int) currForm.getDocument().getSize());
			ps.executeQuery();
			/*
			 * stmt = conn.createStatement(); ResultSet affected = stmt.executeQuery(sql);
			 */
			// System.out.println(affected);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public void updateForm(Form currForm) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			PreparedStatement ps = null;

			String sql = "UPDATE forms SET curr_viewer = " + currForm.getCurrViewer() + ", status = '"+ currForm.getStatus() + "' " + "WHERE request_id = "
					+ currForm.getRequestID();
			ps = conn.prepareStatement(sql);
			ps.executeQuery();
			// System.out.println(affected);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public void alterForm(Form currForm) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			PreparedStatement ps = null;

			String sql = "UPDATE forms SET amount = '" + currForm.getAmount() + "', alter_amount = " + currForm.getAlteredAmount()+ ", status = '"+ currForm.getStatus() + "', alter_approve = '"+ currForm.getAlterApproved() + "' " + "WHERE request_id = "
					+ currForm.getRequestID();
			ps = conn.prepareStatement(sql);
			ps.executeQuery();
			// System.out.println(affected);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}
	public Form getFormById(int id, int viewer) { // NOT DONE Implementing
		Form currForm = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM forms " + "WHERE request_id = " + id + "AND curr_viewer = " + viewer;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				// System.out.println(rs);
				currForm = new Form(rs.getString("emp_id"), Double.parseDouble(rs.getString("amount")),
						rs.getString("rem_type"));
				currForm.setRequestID(rs.getInt("request_id"));
				currForm.setCurrViewer(Integer.parseInt(rs.getString("curr_viewer")));
				currForm.setStatus(rs.getString("status"));
				currForm.setDateSub(rs.getDate("date_sent"));
				currForm.setFileName(rs.getString("file_name"));
				currForm.setAlterApproved(Integer.parseInt(rs.getString("alter_approve")));
				currForm.setAlteredAmount(Double.parseDouble(rs.getString("alter_amount")));
				/*
				 * Blob blob = rs.getBlob("file_doc"); InputStream in = blob.getBinaryStream();
				 * FileItem currFile = null; OutputStream os = new FileOutputStream(currFile);
				 */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}

		return currForm;
	}

	public List<Form> getFormsMatchingEmpId(String empID) {

		List<Form> formList = new ArrayList<Form>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Form currForm = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM forms " + "WHERE emp_id = '" + empID + "'ORDER BY request_id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				// System.out.println(rs);
				currForm = new Form(rs.getString("emp_id"), Double.parseDouble(rs.getString("amount")),
						rs.getString("rem_type"));
				currForm.setRequestID(rs.getInt("request_id"));
				currForm.setCurrViewer(Integer.parseInt(rs.getString("curr_viewer")));
				currForm.setStatus(rs.getString("status"));
				currForm.setDateSub(rs.getDate("date_sent"));
				currForm.setAlterApproved(Integer.parseInt(rs.getString("alter_approve")));
				currForm.setAlteredAmount(Double.parseDouble(rs.getString("alter_amount")));
				formList.add(currForm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}

		return formList;

	}

	public List<Form> getFormsMatching(int viewer) {

		List<Form> formList = new ArrayList<Form>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Form currForm = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM forms " + "WHERE curr_viewer = " + viewer + "ORDER BY request_id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				// System.out.println(rs);
				currForm = new Form(rs.getString("emp_id"), Double.parseDouble(rs.getString("amount")),
						rs.getString("rem_type"));
				currForm.setRequestID(rs.getInt("request_id"));
				currForm.setCurrViewer(Integer.parseInt(rs.getString("curr_viewer")));
				currForm.setStatus(rs.getString("status"));
				currForm.setDateSub(rs.getDate("date_sent"));
				formList.add(currForm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}

		return formList;

	}
}
