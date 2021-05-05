package com.solar.www.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.solar.www.dispatcher.ErsDispatcher;
import com.solar.www.log.SolarLogger;

// This is the master servlet class. All requests come to here first.
public class ErsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		SolarLogger.solarLogger.debug("Front end request enters ErsServlet doGet method");
		
		// forcefully demand the not so smart browser to disable the cache.
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // for http1.1
		res.setHeader("Pragma", "no-cache"); // for http1.0
		res.setHeader("Expires", "0"); // for proxy

		req.getRequestDispatcher(ErsDispatcher.getResourceLocation(req)).forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		SolarLogger.solarLogger.debug("Front end request enters ErsServlet doPost method");

		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Expires", "0");

		req.getRequestDispatcher(ErsDispatcher.getResourceLocation(req)).forward(req, res);
	}
}
