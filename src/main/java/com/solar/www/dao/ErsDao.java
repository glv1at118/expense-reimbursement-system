package com.solar.www.dao;

import java.util.List;

import com.solar.www.model.Reimb;
import com.solar.www.model.User;

public interface ErsDao {
//	public abstract String getPassWordBasedOnUserName(String userName);
	public abstract User getUserRecord(String userName, String passWord);

	public abstract int createNewUser(String userName, String passWord, String firstName, String lastName,
			String email, int roleId);

	public abstract int getRoleIdByRoleName(String roleName);

	public abstract void createNewReimb(double amount, String description, int authorId, int typeId);

	public abstract List<Reimb> getOnesPendingReimbList(int authorId);

	public abstract List<Reimb> getOnesApprovedReimbList(int authorId);

	public abstract List<Reimb> getOnesDeniedReimbList(int authorId);
	
	public abstract List<Reimb> getAllPendingReimbList();

	public abstract List<Reimb> getAllApprovedReimbList();

	public abstract List<Reimb> getAllDeniedReimbList();
	
	public abstract List<Reimb> getAllReimbList();
	
	public abstract Reimb getSpecificReimbById(int reimbId);
	
	public abstract void denyReimbById(int reimbId, int resolverId);
	
	public abstract void approveReimbById(int reimbId, int resolverId);
	
	public abstract String getUserNameById(int id);
	
	public abstract String getTypeNameById(int id);
	
	public abstract String getStatusNameById(int id);

}
