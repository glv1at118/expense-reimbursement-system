package com.solar.www.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SolarLogger {

	public static final Logger solarLogger = Logger.getLogger(SolarLogger.class);

	static {
		solarLogger.setLevel(Level.DEBUG);
//		solarLogger.setLevel(Level.OFF);
	}
}
