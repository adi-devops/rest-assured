package com.abc.automate;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(Lifecycle.PER_CLASS)
public class TestToDo {
	
	@BeforeAll
	public void setup() {
		
		RestAssured.
					filters(new RequestLoggingFilter(),new ResponseLoggingFilter());

	    RestAssured.baseURI = "http://my-json-server.typicode.com/";
	}
	
	@Test()
	@DisplayName("verify array size")
	public void testGetTodoListArraySize() {
		when().
				get("adi-devops/todo/todo").
		then().
				statusCode(200).
				assertThat().body("size()", is(2));
	}
	
	@Test
	@DisplayName("verify array first value")
	public void testGetTodoListFirstValue() {

		Todo[] todos= 	when().
							get("adi-devops/todo/todo").
						then().
							extract().as(Todo[].class);

		assertEquals(1,todos[0].getId());	
	}
	
	@Test
	@DisplayName("verify header value")
	public void testGetTodoListHeader() {
		when().
				get("adi-devops/todo/todo/1").
		then().
				statusCode(200).
		assertThat().header("Content-Type", "application/json; charset=utf-8");
	}
	
	@Test
	@DisplayName("verify basic auth")
	public void testGetTodoListWithBasicAuth() {
		given().
				auth().basic("123", "abc").
		when().
				get("adi-devops/todo/todo/1").
		then().
				statusCode(200).
		assertThat().header("Content-Type", "application/json; charset=utf-8");
	}
	
	@Test
	@DisplayName("verify delete operation")
	public void testDeleteTodoValue() {

		 when().
		 		delete("adi-devops/todo/todo/1").
		 then().
		 		statusCode(200);
	}
	
	@Test
	@DisplayName("verify post operation")
	public void testPostTodoValue() {

		Todo likeVideo = new Todo();
		likeVideo.setCompleted(false);
		likeVideo.setId(3);
		likeVideo.setTitle("please like the video");
		
		given().
				body(likeVideo).
		when().
				post("adi-devops/todo/todo/").
		then().
				statusCode(201);
	}

}
