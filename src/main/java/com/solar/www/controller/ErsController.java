package com.solar.www.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solar.www.dao.ErsDaoImpl;
import com.solar.www.log.SolarLogger;
import com.solar.www.model.Reimb;
import com.solar.www.model.ReimbView;
import com.solar.www.model.User;
import com.solar.www.service.ErsService;

// Controller层和Service层之间的关系很模糊。其实都可以取消Controller层。
public class ErsController {
	// this method determines whether it's employee or manager page to enter, after
	// logging in. Returns to front end a page's html location.
	public String findPageToEnter(HttpServletRequest req) {
		/**
		 * findPageToEnter is where a user is trying to access an employee or manager page.
		 * these 2 pages contain sensitive information, which should not be accessed without proper login.
		 * 
		 * there're 3 scenarios that may happen when attempting to access the employee/manager page:
		 * s1: A totally fresh login as a user.
		 * s2: After an employee or manager logs out from the employee/manager page, 
		 * 	   a different user comes and uses the login page to login as a different person.
		 * s3: After an employee or manager logs out from the employee/manager page, 
		 *     a hacker comes and click the "back button" on the not-so-smart chrome browser,
		 *     and try to access the previous employee/manager page.
		 *     
		 * The browser by default, is with cache enabled. So when the back button is clicked, 
		 * it returns to a cached version of the previous page. So if a person logs out from the employee page,
		 * and somebody else click the back button, then they can access the cached version of the employee page.
		 * In this way, the sensitive information from the previous person is disclosed.
		 * 
		 * To prevent the cache, I need to set the response header, as shown in the ErsServlet.java.
		 * 
		 * This is however, not enough. I need to operate on the session. When a user logs out,
		 * I set the current session's held currUserName to be "", while still keep this HttpSession.
		 * I also invalidate the current HttpSession, if there's any, when a user access the login.view page.
		 * 
		 * In this way:
		 * 1. If a user performs a totally fresh new login, then there's no available HttpSession.
		 *    In this way, sess == null, and if(sess != null && sess.getAttribute("currUserName") == null)
		 *    cannot capture it. Then the user just normally login.
		 * 2. If userA logs out and userB tries to log in, then the HttpSession is also invalidated when userB access the login.view page. Then sess here == null, still userB can normally login.
		 * 3. If a user tries to perform illegal action by clicking the back button after a previous user logs out,
		 * 	  then the sess != null, but currUserName == null. Then he gets kicked back to loginfailure page.
		 */
		
		HttpSession sess = req.getSession(false);
		if (sess != null && sess.getAttribute("currUserName") == null) {
			return "pages/loginfailure.html";
		}
		String userName = req.getParameter("username");
		String passWord = req.getParameter("password");

		// skip service layer, directly invoke dao layer's method to avoid redundancy
		User user = new ErsDaoImpl().getUserRecord(userName, passWord);
		if (user == null) {
			return "pages/loginfailure.html";
		}
		int roleId = user.getRoleId();
		HttpSession session = req.getSession();
		session.setAttribute("currUserName", userName);
		session.setAttribute("currPassWord", passWord);

		SolarLogger.solarLogger.debug("In ErsController findPageToEnter method.");
		SolarLogger.solarLogger.debug("currUserName is: " + userName + ", currPassWord is: " + passWord);

		if (roleId == 2) {
			// supply the manager page
			return "pages/manager.html";
		} else if (roleId == 1) {
			// supply the employee page
			return "pages/employee.html";
		}
		return null;
	}

	// this method returns to the front end a json in the res body
	public void obtainOnesList(HttpServletRequest req, HttpServletResponse res, String listType) {
		SolarLogger.solarLogger.debug("In ErsController obtainOnesList method.");

		Object value1 = req.getSession().getAttribute("currUserName");
		String userName = (String) value1;
		Object value2 = req.getSession().getAttribute("currPassWord");
		String passWord = (String) value2;
		User user = new ErsDaoImpl().getUserRecord(userName, passWord);
		int userId = user.getUserId();

		if ("pending".equals(listType)) {
			List<Reimb> list = new ErsDaoImpl().getOnesPendingReimbList(userId);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			try {
				String jsonStr = new ObjectMapper().writeValueAsString(list);
				res.getWriter().write(jsonStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if ("approved".equals(listType)) {
			List<Reimb> list = new ErsDaoImpl().getOnesApprovedReimbList(userId);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			try {
				String jsonStr = new ObjectMapper().writeValueAsString(list);
				res.getWriter().write(jsonStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if ("denied".equals(listType)) {
			List<Reimb> list = new ErsDaoImpl().getOnesDeniedReimbList(userId);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			try {
				String jsonStr = new ObjectMapper().writeValueAsString(list);
				res.getWriter().write(jsonStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// this method returns to the front end a json in the res body
	public void obtainOnesName(HttpServletRequest req, HttpServletResponse res) {
		SolarLogger.solarLogger.debug("In ErsController obtainOnesName method.");

		Object value = req.getSession().getAttribute("currUserName");
		String userName = (String) value;
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		try {
			String jsonStr = new ObjectMapper().writeValueAsString(userName);
			res.getWriter().write(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取email功能待实现
	public void obtainOnesEmail(HttpServletRequest req, HttpServletResponse res) {
		SolarLogger.solarLogger.debug("In ErsController obtainOnesEmail method.");

		String userName = (String) req.getSession().getAttribute("currUserName");
		String passWord = (String) req.getSession().getAttribute("currPassWord");
		User u = new ErsDaoImpl().getUserRecord(userName, passWord);
		String email = u.getEmail();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		try {
			String jsonStr = new ObjectMapper().writeValueAsString(email);
			res.getWriter().write(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// this method returns to the front end a json in the res body
	public void getCertainReimbDetail(HttpServletRequest req, HttpServletResponse res) {
		SolarLogger.solarLogger.debug("In ErsController getCertainReimbDetail method.");

		String frontEndPostData = req.getParameter("reimbIdStr");
		int frontEndPostDataInt = Integer.parseInt(frontEndPostData);

		ErsService serv = new ErsService(new ErsDaoImpl());
		ReimbView thisView = serv.constructReimbView(frontEndPostDataInt);

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		try {
			String jsonStr = new ObjectMapper().writeValueAsString(thisView);
			res.getWriter().write(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createNewReimb(HttpServletRequest req) {
		SolarLogger.solarLogger.debug("In ErsController createNewReimb method.");
		new ErsService(new ErsDaoImpl()).processNewReimbCreation(req);
	}

	public void obtainAllReimbList(HttpServletResponse res) {
		SolarLogger.solarLogger.debug("In ErsController obtainAllReimbList method.");

		List<Reimb> list = new ErsDaoImpl().getAllReimbList();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		try {
			String jsonStr = new ObjectMapper().writeValueAsString(list);
			res.getWriter().write(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void obtainAllPendingReimbList(HttpServletResponse res) {
		SolarLogger.solarLogger.debug("In ErsController obtainAllPendingReimbList method.");

		List<Reimb> list = new ErsDaoImpl().getAllPendingReimbList();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		try {
			String jsonStr = new ObjectMapper().writeValueAsString(list);
			res.getWriter().write(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void obtainAllApprovedReimbList(HttpServletResponse res) {
		SolarLogger.solarLogger.debug("In ErsController obtainAllApprovedReimbList method.");

		List<Reimb> list = new ErsDaoImpl().getAllApprovedReimbList();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		try {
			String jsonStr = new ObjectMapper().writeValueAsString(list);
			res.getWriter().write(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void obtainAllDeniedReimbList(HttpServletResponse res) {
		SolarLogger.solarLogger.debug("In ErsController obtainAllDeniedReimbList method.");

		List<Reimb> list = new ErsDaoImpl().getAllDeniedReimbList();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		try {
			String jsonStr = new ObjectMapper().writeValueAsString(list);
			res.getWriter().write(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void approveCertainReimb(HttpServletRequest req) {
		SolarLogger.solarLogger.debug("In ErsController approveCertainReimb method.");

		new ErsService(new ErsDaoImpl()).approveCertainReimb(req);
	}

	public void denyCertainReimb(HttpServletRequest req) {
		SolarLogger.solarLogger.debug("In ErsController denyCertainReimb method.");

		new ErsService(new ErsDaoImpl()).denyCertainReimb(req);
	}

	public String processRegister(HttpServletRequest req) {
		SolarLogger.solarLogger.debug("In ErsController processRegister method.");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String f_name = req.getParameter("f_name");
		String l_name = req.getParameter("l_name");
		String email = req.getParameter("email");
		String usertype = req.getParameter("usertype");

		if ("".equals(username) || "".equals(password) || "".equals(f_name) || "".equals(l_name) || "".equals(email)
				|| "".equals(usertype)) {
			System.out.println("front end is trying to register a new user with at least 1 empty field.");
			return "pages/registerfailure.html";
		}

		boolean succeeded = new ErsService(new ErsDaoImpl()).processRegister(username, password, f_name, l_name, email,
				usertype);
		if (succeeded) {
			return "pages/registersuccess.html";
		} else {
			return "pages/registerfailure.html";
		}
	}

	public void logOut(HttpServletRequest req) {
		req.getSession().removeAttribute("currUserName");
		req.getSession().removeAttribute("currPassWord");
	}
	
	public void initLogIn(HttpServletRequest req) {
		req.getSession().invalidate();
	}
}
