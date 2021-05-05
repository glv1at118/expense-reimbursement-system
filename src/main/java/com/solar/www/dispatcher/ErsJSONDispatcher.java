package com.solar.www.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.solar.www.controller.ErsController;
import com.solar.www.log.SolarLogger;

public class ErsJSONDispatcher {

	public void sendJSONToFrontEnd(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod().equals("GET")) {
			SolarLogger.solarLogger.debug("In ErsJSONDispatcher's sendJSONToFrontEnd method. Request is GET");
			
			switch (req.getRequestURI()) {
			case "/ERS_System/employee-pendings.json":
				new ErsController().obtainOnesList(req, res, "pending");
				break;
			case "/ERS_System/employee-approved.json":
				new ErsController().obtainOnesList(req, res, "approved");
				break;
			case "/ERS_System/employee-denied.json":
				new ErsController().obtainOnesList(req, res, "denied");
				break;
			case "/ERS_System/employee-name.json":
				new ErsController().obtainOnesName(req, res);
				break;
			case "/ERS_System/all-pendings.json":
				new ErsController().obtainAllPendingReimbList(res);
				break;
			case "/ERS_System/all-approved.json":
				new ErsController().obtainAllApprovedReimbList(res);
				break;
			case "/ERS_System/all-denied.json":
				new ErsController().obtainAllDeniedReimbList(res);
				break;
			case "/ERS_System/all-list.json":
				new ErsController().obtainAllReimbList(res);
				break;
			case "/ERS_System/user-email.json":
				new ErsController().obtainOnesEmail(req, res);
				break;
			}

		} else if (req.getMethod().equals("POST")) {
			SolarLogger.solarLogger.debug("In ErsJSONDispatcher's sendJSONToFrontEnd method. Request is POST");
			
			switch (req.getRequestURI()) {
			case "/ERS_System/reimb-details.json":
				new ErsController().getCertainReimbDetail(req, res);
				break;
			case "/ERS_System/reimb-approve.json":
				new ErsController().approveCertainReimb(req);
				break;
			case "/ERS_System/reimb-deny.json":
				new ErsController().denyCertainReimb(req);
				break;
			}
		}

	}
}
