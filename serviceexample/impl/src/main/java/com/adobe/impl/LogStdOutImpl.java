package com.adobe.impl;

import com.adobe.api.LogService;

public class LogStdOutImpl implements LogService {

	@Override
	public void log(String msg) {
			System.out.println("STDOUT: " + msg);
	}

}
