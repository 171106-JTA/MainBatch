package p1.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.util.ConnectionUtil;

import p1.revature.beans.ReimbursableExpenseType;

public class ReimbursableExpenseTypeDao {
	public List<ReimbursableExpenseType> getAllExpenseTypes()
	{
		Statement stmt = null;
		ResultSet rs   = null;
		
		List<ReimbursableExpenseType> expenseTypes = new LinkedList<>();

		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_reimbursable_expense_types";
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			while (rs.next())
			{
				expenseTypes.add(new ReimbursableExpenseType(rs.getInt("RET_ID"),
					rs.getString("RET_TYPE"),
					rs.getDouble("RET_COVERAGE")));
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(stmt);
			close(rs);
		}

		return expenseTypes;
	}

	public ReimbursableExpenseType getTypeByName(ReimbursableExpenseType type)
	{
		PreparedStatement ps = null;
		ResultSet rs         =  null;
		ReimbursableExpenseType fetchedType = null;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_reimbursable_expense_types WHERE ret_type = ?";
			ps = conn.prepareStatement(sql);
			// set parameters here
			ps.setString(1, type.getName());
			rs = ps.executeQuery();
			if (rs.next())
			{
				fetchedType = new ReimbursableExpenseType(rs.getInt("RET_ID"),
						rs.getString("RET_TYPE"),
						rs.getDouble("RET_COVERAGE"));
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(ps);
			close(rs);
		}

		return fetchedType;
	}
}
