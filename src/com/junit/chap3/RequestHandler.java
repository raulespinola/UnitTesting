package com.junit.chap3;

public interface RequestHandler {
	Response process(Request request) throws Exception;
}
