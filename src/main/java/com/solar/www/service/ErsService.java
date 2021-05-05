package com.solar.www.service;

import javax.servlet.http.HttpServletRequest;

import com.solar.www.dao.ErsDaoImpl;
import com.solar.www.log.SolarLogger;
import com.solar.www.model.Reimb;
import com.solar.www.model.ReimbView;
import com.solar.www.model.User;

public class ErsService {
	private ErsDaoImpl daoObj;

	public ErsService(ErsDaoImpl daoObj) {
		this.daoObj = daoObj;
	}

	// this method restructures Reimb model into a ReimbView model,
	// so that after it's written via Jackson to the frontend,
	// it can display the author & resolver name, type name, status name,
	// rather than their ids.
	public ReimbView constructReimbView(int reimbId) {
		SolarLogger.solarLogger.debug("In ErsService constructReimbView method.");

		Reimb thisReimb = this.daoObj.getSpecificReimbById(reimbId);

		String typeName = this.daoObj.getTypeNameById(thisReimb.getTypeId());
		String authorName = this.daoObj.getUserNameById(thisReimb.getAuthorId());
		String resolverName = this.daoObj.getUserNameById(thisReimb.getResolverId());
		String statusName = this.daoObj.getStatusNameById(thisReimb.getStatusId());

		ReimbView reimbView = new ReimbView(thisReimb.getReimbId(), thisReimb.getReimbAmount(),
				thisReimb.getReimbSubmitted(), thisReimb.getReimbResolved(), thisReimb.getReimbDescription(),
				thisReimb.getReimbReceipt(), authorName, resolverName, statusName, typeName);

		return reimbView;
	}

	public void processNewReimbCreation(HttpServletRequest req) {
		SolarLogger.solarLogger.debug("In ErsService processNewReimbCreation method.");

		String username = (String) req.getSession().getAttribute("currUserName");
		String password = (String) req.getSession().getAttribute("currPassWord");
		User user = this.daoObj.getUserRecord(username, password);
		
		// an employee enter's the system and submitted a new reimb, and logs out.
		// later an illegal person comes and hit the browser's back button, this will cause a "resubmit" of the previous reimb
		// but by resubmitting, this service method has to obtain the previous employee's record.
		// but the previous employee has logged out, and in the HttpSession there's no his user password and user name.
		// so here the "this.daoObj.getUserRecord(username, password);" will return null.
		// then later if user.getUserId() is invoked, it will trigger a nullpointerexception on the server side.
		// Below is a patch for this situation.
		if(user == null) { return; }

		String description = req.getParameter("inputDescription");
		String amount = req.getParameter("inputAmount");

		if ("".equals(amount)) {
			System.out.println("Insertion of Reimb is interrupted by java, as the amount is empty.");
			return;
		}

		String type = req.getParameter("inputType");

		this.daoObj.createNewReimb(Double.parseDouble(amount), description, user.getUserId(), Integer.parseInt(type));
		System.out.println("new reimb inserted!");
	}

	public void approveCertainReimb(HttpServletRequest req) {
		SolarLogger.solarLogger.debug("In ErsService approveCertainReimb method.");

		String frontendPostedReimbId = req.getParameter("reimbIdStr");

		String currUserName = (String) req.getSession().getAttribute("currUserName");
		String currPassWord = (String) req.getSession().getAttribute("currPassWord");

		User user = this.daoObj.getUserRecord(currUserName, currPassWord);
		int resolverId = user.getUserId();

		this.daoObj.approveReimbById(Integer.parseInt(frontendPostedReimbId), resolverId);

	}

	public void denyCertainReimb(HttpServletRequest req) {
		SolarLogger.solarLogger.debug("In ErsService denyCertainReimb method.");

		String frontendPostedReimbId = req.getParameter("reimbIdStr");

		String currUserName = (String) req.getSession().getAttribute("currUserName");
		String currPassWord = (String) req.getSession().getAttribute("currPassWord");

		User user = this.daoObj.getUserRecord(currUserName, currPassWord);
		int resolverId = user.getUserId();

		this.daoObj.denyReimbById(Integer.parseInt(frontendPostedReimbId), resolverId);

	}

	public boolean processRegister(String username, String password, String f_name, String l_name, String email,
			String usertype) {
		SolarLogger.solarLogger.debug("In ErsService processRegister method.");

		int returned = this.daoObj.createNewUser(username, password, f_name, l_name, email, Integer.parseInt(usertype));
		if (returned == 1) {
			return true;
		}
		return false;
	}

}
