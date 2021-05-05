package com.solar.www.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.solar.www.dispatcher.ErsJSONDispatcher;
import com.solar.www.log.SolarLogger;

public class ErsJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		SolarLogger.solarLogger.debug("Front end request enters ErsJSONServlet doGet method");
		new ErsJSONDispatcher().sendJSONToFrontEnd(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		SolarLogger.solarLogger.debug("Front end request enters ErsJSONServlet doPost method");
		new ErsJSONDispatcher().sendJSONToFrontEnd(req, res);
	}
}
