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
	@Test
	public void testAddHandler(){
		RequestHandler handler2= controller.getHandler(request);
		assertSame(handler2,handler);
	}
	@Test
	public void testProcessRequest(){
			
		Response response = controller.processRequest(request);
		assertNotNull("Must not return a null response", response);
		assertEquals(TestResponse.class, response.getClass());
		assertEquals(new TestResponse(), response);
	}
	
	@Test
	public void testProcessRequesAnsweresErrorResponse(){
		TestRequest request = new TestRequest("testError");
		TestExceptionHandler handler = new TestExceptionHandler();
		controller.addHandler(request, handler);
		Response response = controller.processRequest(request);
		assertNotNull("Must not return a null reponse", response);
		assertEquals(ErrorResponse.class, response.getClass());
	}
	
	@Test
	public void testGetHandlerNotDefined(){
		TestRequest request = new TestRequest("testNotDefined");
		try{
			controller.getHandler(request);
			fail("An exception shour be raised if the request"
					+ "handles has not been registered");
		}
		catch(RuntimeException expected){
			assertTrue(true);
		}
	}
	
	@Test
	public void TestAddRequestDuplicateName(){
		TestRequest request = new TestRequest();
		TestHandler handler = new TestHandler();
		
		try{
			controller.addHandler(request, handler);
			fail ("An Exception should be raised if the default"
			+ "TestRequest has already been registered");
		}
		catch (RuntimeException expected){
			assertTrue(true);
		}
	}
	
	
	
	private class TestExceptionHandler implements RequestHandler{
		public Response process (Request request) throws Exception{
				throw new Exception ("error processing request");
		}
	}
	
	private class TestRequest implements Request{
		private static final String DEFAULT_NAME = "test";
		private String name;
		
		public TestRequest (String name){
			this.name= name;
		}
		
		public TestRequest(){
			this(DEFAULT_NAME);
		}
		
		
		public String getName(){
			return this.name;
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
