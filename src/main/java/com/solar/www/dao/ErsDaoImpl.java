package com.solar.www.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.solar.www.model.Reimb;
import com.solar.www.model.User;

public class ErsDaoImpl implements ErsDao {

	public static Connection createConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String dbUrl = "jdbc:postgresql://guannan-database.ccddyamvcxtk.us-east-2.rds.amazonaws.com:5432/ersdb";
		String dbUserName = "guannan";
		String dbPassWord = "lgn19910708";
		return DriverManager.getConnection(dbUrl, dbUserName, dbPassWord);
	}

	public User getUserRecord(String userName, String passWord) {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select * from ers_users where ers_username = ? and ers_password = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, passWord);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getInt(7));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public int createNewUser(String userName, String passWord, String firstName, String lastName, String email,
//			int roleId) {
//		if ("".equals(userName) || "".equals(passWord) || "".equals(firstName) || "".equals(lastName)
//				|| "".equals(email)) {
//			return -1;
//		}
//		// add a new user to the ers_users table
//		try (Connection con = ErsDaoImpl.createConnection()) {
//			String sql = "insert into ers_users(ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) values (?, ?, ?, ?, ?, ?)";
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setString(1, userName);
//			ps.setString(2, passWord);
//			ps.setString(3, firstName);
//			ps.setString(4, lastName);
//			ps.setString(5, email);
//			ps.setInt(6, roleId);
//			return ps.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}
	
	// This is a different version of the above, by using the SQL function and CallableStatment.
	public int createNewUser(String userName, String passWord, String firstName, String lastName, String email,
			int roleId) {
		if ("".equals(userName) || "".equals(passWord) || "".equals(firstName) || "".equals(lastName)
				|| "".equals(email)) {
			return -1;
		}
		// add a new user to the ers_users table
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "{? = call insert_new_user(?,?,?,?,?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setString(2, userName);
			cs.setString(3, passWord);
			cs.setString(4, firstName);
			cs.setString(5, lastName);
			cs.setString(6, email);
			cs.setInt(7, roleId);
			boolean val = cs.execute();
			if (!val) {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getRoleIdByRoleName(String roleName) {
		// given the roleName (i.e. employee/manager), find the roleId.
		// if found then return roleId, if not then return -1.
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select ers_user_role_id from ers_user_roles where user_role = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, roleName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("ers_user_role_id");
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void createNewReimb(double amount, String description, int authorId, int typeId) {
		// method to insert a new record to the ers_reimbursement table.
		// reimb_status_id of this table is automatically set to 3 upon insertion,
		// because it's pending.
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "insert into ers_reimbursement(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) values(?, current_timestamp, ?, ?, 3, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, amount);
			ps.setString(2, description);
			ps.setInt(3, authorId);
			ps.setInt(4, typeId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Reimb> getOnesPendingReimbList(int authorId) {
		// this method retrieves a certain person's pending reimbursement applications.
		// It returns all such reimbs in a list.
		// Note: The receipt file object is null, and it also returns the reimb_id.
		// I will need this reimb_id to be a flag on the list item rendered on view.
		// When clicking that list item, it will perform database query:
		// select * from ers_reimbursement where reimb_id = xxx.
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_type_id from ers_reimbursement where reimb_author = ? and reimb_status_id = 3";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, authorId);
			ResultSet rs = ps.executeQuery();
			List<Reimb> list = new ArrayList<>();
			while (rs.next()) {
				int reimb_id = rs.getInt(1);
				double reimb_amount = rs.getDouble(2);
				Timestamp reimb_submitted = rs.getTimestamp(3);
				String reimb_description = rs.getString(4);
				int reimb_type_id = rs.getInt(5);
				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted.toString().split("\\.")[0], "",
						reimb_description, null, authorId, -1, 3, reimb_type_id);
				list.add(reimb);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reimb> getOnesApprovedReimbList(int authorId) {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_type_id from ers_reimbursement where reimb_author = ? and reimb_status_id = 1";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, authorId);
			ResultSet rs = ps.executeQuery();
			List<Reimb> list = new ArrayList<>();
			while (rs.next()) {
				int reimb_id = rs.getInt(1);
				double reimb_amount = rs.getDouble(2);
				Timestamp reimb_submitted = rs.getTimestamp(3);
				String reimb_description = rs.getString(4);
				int reimb_type_id = rs.getInt(5);
				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted.toString().split("\\.")[0], "",
						reimb_description, null, authorId, -1, 1, reimb_type_id);
				list.add(reimb);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reimb> getOnesDeniedReimbList(int authorId) {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_type_id from ers_reimbursement where reimb_author = ? and reimb_status_id = 2";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, authorId);
			ResultSet rs = ps.executeQuery();
			List<Reimb> list = new ArrayList<>();
			while (rs.next()) {
				int reimb_id = rs.getInt(1);
				double reimb_amount = rs.getDouble(2);
				Timestamp reimb_submitted = rs.getTimestamp(3);
				String reimb_description = rs.getString(4);
				int reimb_type_id = rs.getInt(5);
				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted.toString().split("\\.")[0], "",
						reimb_description, null, authorId, -1, 2, reimb_type_id);
				list.add(reimb);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reimb> getAllPendingReimbList() {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_type_id from ers_reimbursement where reimb_status_id = 3";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Reimb> list = new ArrayList<>();
			while (rs.next()) {
				int reimb_id = rs.getInt(1);
				double reimb_amount = rs.getDouble(2);
				Timestamp reimb_submitted = rs.getTimestamp(3);
				String reimb_description = rs.getString(4);
				int reimb_type_id = rs.getInt(5);
				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted.toString().split("\\.")[0], "",
						reimb_description, null, -1, -1, 3, reimb_type_id);
				list.add(reimb);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reimb> getAllApprovedReimbList() {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_type_id from ers_reimbursement where reimb_status_id = 1";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Reimb> list = new ArrayList<>();
			while (rs.next()) {
				int reimb_id = rs.getInt(1);
				double reimb_amount = rs.getDouble(2);
				Timestamp reimb_submitted = rs.getTimestamp(3);
				String reimb_description = rs.getString(4);
				int reimb_type_id = rs.getInt(5);
				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted.toString().split("\\.")[0], "",
						reimb_description, null, -1, -1, 1, reimb_type_id);
				list.add(reimb);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reimb> getAllDeniedReimbList() {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_type_id from ers_reimbursement where reimb_status_id = 2";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Reimb> list = new ArrayList<>();
			while (rs.next()) {
				int reimb_id = rs.getInt(1);
				double reimb_amount = rs.getDouble(2);
				Timestamp reimb_submitted = rs.getTimestamp(3);
				String reimb_description = rs.getString(4);
				int reimb_type_id = rs.getInt(5);
				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted.toString().split("\\.")[0], "",
						reimb_description, null, -1, -1, 2, reimb_type_id);
				list.add(reimb);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reimb> getAllReimbList() {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_type_id from ers_reimbursement";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Reimb> list = new ArrayList<>();
			while (rs.next()) {
				int reimb_id = rs.getInt(1);
				double reimb_amount = rs.getDouble(2);
				Timestamp reimb_submitted = rs.getTimestamp(3);
				String reimb_description = rs.getString(4);
				int reimb_type_id = rs.getInt(5);
				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted.toString().split("\\.")[0], "",
						reimb_description, null, -1, -1, -1, reimb_type_id);
				list.add(reimb);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Reimb getSpecificReimbById(int reimbId) {
		// get a certain reimbursement record based on its id.
		// if found then return this record constructed in Reimb model.
		// if not found then return null.
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select * from ers_reimbursement where reimb_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, reimbId);
			ResultSet rs = ps.executeQuery();
			// since reimb_id in the ers_reimbursement is primary key which is unique.
			// so there's only 1 matched record.
			if (rs.next()) {
				double reimb_amount = rs.getDouble(2);
				Timestamp reimb_submitted = rs.getTimestamp(3);
				String[] subStrs = reimb_submitted.toString().split("\\.");

				Timestamp reimb_resolved = rs.getTimestamp(4);

				String reimbResolvedStr = reimb_resolved == null ? "" : reimb_resolved.toString().split("\\.")[0];

				String reimb_description = rs.getString(5);
				Object reimb_receipt = null;
				int reimb_author = rs.getInt(7);
				int reimb_resolver = rs.getInt(8);
				int reimb_status_id = rs.getInt(9);
				int reimb_type_id = rs.getInt(10);

				System.out.println();
				Reimb reimb = new Reimb(reimbId, reimb_amount, subStrs[0], reimbResolvedStr, reimb_description,
						reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id);
				return reimb;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void denyReimbById(int reimbId, int resolverId) {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "update ers_reimbursement set reimb_status_id = 2, reimb_resolver = ?, reimb_resolved = current_timestamp where reimb_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, resolverId);
			ps.setInt(2, reimbId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void approveReimbById(int reimbId, int resolverId) {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "update ers_reimbursement set reimb_status_id = 1, reimb_resolver = ?, reimb_resolved = current_timestamp where reimb_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, resolverId);
			ps.setInt(2, reimbId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getUserNameById(int id) {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select user_first_name, user_last_name from ers_users where ers_users_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString(1);
				String lastName = rs.getString(2);
				return firstName + " " + lastName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTypeNameById(int id) {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_type from ers_reimbursement_type where reimb_type_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String typeName = rs.getString(1);
				return typeName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStatusNameById(int id) {
		try (Connection con = ErsDaoImpl.createConnection()) {
			String sql = "select reimb_status from ers_reimbursement_status where reimb_status_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String statusName = rs.getString(1);
				return statusName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
