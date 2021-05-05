package com.solar.www.dispatcher;

import javax.servlet.http.HttpServletRequest;

import com.solar.www.controller.ErsController;
import com.solar.www.log.SolarLogger;

public class ErsDispatcher {
	// This class is going to return back the resource location as String
	public static String getResourceLocation(HttpServletRequest req) {
		String uri = req.getRequestURI(); // i.e. /ERS_System/guannan.view
		String resourceLocation = "";

		if (req.getMethod().equals("GET")) {
			switch (uri) {
			case "/ERS_System/landing.view":
				resourceLocation = "pages/landing.html";
				break;
			case "/ERS_System/login.view":
				// when the login.view is requested, forcefully invalidate the current session
				new ErsController().initLogIn(req);
				resourceLocation = "pages/login.html";
				break;
			case "/ERS_System/register.view":
				resourceLocation = "pages/register.html";
				break;
			case "/ERS_System/logout.view":
				// when the logout.view is requested, delete the attributes currUserName & currPassWord in current session
				// while still keep the session alive
				new ErsController().logOut(req);
				resourceLocation = "pages/landing.html";
				break;
			}
		} else if (req.getMethod().equals("POST")) {
			switch (uri) {
			case "/ERS_System/enter.view":
				resourceLocation = new ErsController().findPageToEnter(req);
				break;
			case "/ERS_System/create-new-reimb.view":
				new ErsController().createNewReimb(req);
				resourceLocation = "pages/employee.html";
				break;
			case "/ERS_System/create-new-user.view":
				resourceLocation = new ErsController().processRegister(req);
				break;
			}
		}

		SolarLogger.solarLogger
				.debug("In ErsPispatcher's getResourceLocation method, resourceLocation is: " + resourceLocation);
		return resourceLocation;
	}
}
