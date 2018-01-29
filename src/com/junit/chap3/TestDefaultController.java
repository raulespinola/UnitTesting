package com.junit.chap3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestDefaultController {

	private DefaultController controller;
	private Request request;
	private RequestHandler handler;
	
	@Before
	public void setUp() throws Exception {
		controller = new DefaultController();
		request = new TestRequest();
		handler = new TestHandler();
		controller.addHandler(request, handler);
	}

	@Test
	public void testMethod() {
		//throw new RuntimeException ("implement me");
	}
	
	public void testAddHandler(){
		RequestHandler handler2= controller.getHandler(request);
		assertSame(handler2,handler);
	}
	
	public void testProcessRequest(){
			
		Response response = controller.processRequest(request);
		assertNotNull("Must not return a null response", response);
		assertEquals(TestResponse.class, response.getClass());
		assertEquals(new TestResponse(), response);
	}
	
	
	private class TestRequest implements Request{
		public String getName(){
			return "Test";
		}
	}
	private class TestHandler implements RequestHandler{
		public Response process (Request request) throws Exception{
			return new TestResponse();
		}
	}	
	private class TestResponse implements Response{
		private static final String NAME= "Test";
		
		public String getName(){
			return NAME;
		}
		public boolean equals (Object object){
			boolean result = false;
			if (object instanceof TestResponse){
				result = ((TestResponse)object).getName().equals(getName());
			}
			return result;
		}
		public int hashCode(){
			return NAME.hashCode();
		}
	}
	

}
