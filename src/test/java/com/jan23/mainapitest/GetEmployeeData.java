package com.jan23.mainapitest;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.models.CreateEmployeePOJO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetEmployeeData  {
	
	@BeforeClass
	public void init() {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";    
	}
	
	@Test
	public void GetRequestToEmployees() {
		Response res = RestAssured
		.given()
		.when()
			.get("/employees");
		
		res.then()
			.statusCode(200)
			.contentType(ContentType.JSON);
		
		res.then().body("status",is("success"));
		res.prettyPrint();
		
	}
	

	public int createEmployee() {
		CreateEmployeePOJO ob = new CreateEmployeePOJO();
		ob.setName1("test");
		ob.setSalary1("123");
		ob.setAge1("23");
		
		Response res = RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(ob)
		.when()
			.post("/create");
		res.then()
			.statusCode(200);
		res.then().body("data.name", is("test"));
		res.then().body("data.salary", is("123"));
		res.then().body("data.age", is("23"));
		res.then().body("status",is("success"));
		res.prettyPrint();
		int id = res.body().jsonPath().getInt("data.id");
		System.out.println("create employee id ="+id);
		return id;
	}
	
	@Test
	public void deleteEmployee() {
		Response res= RestAssured
		.given()
			.contentType(ContentType.JSON)
	        .pathParam("id", createEmployee())
		.when()
		 	.delete("/delete/{id}");
		res.then().statusCode(200);
		res.prettyPrint();
		res.then().body("status",is("success"));
	}
	
	@Test
	public void deleteEmp() {
		Response res1= RestAssured
				.given()
					.contentType(ContentType.JSON)
			        .pathParam("id",0)
				.when()
				 	.delete("/delete/{id}");
				res1.then().statusCode(400);
				res1.prettyPrint();
				res1.then().body("status",is("error"));	

}

@Test
public void EmployeeId() {
	Response res = RestAssured
			.given()
				.pathParam("id", 2)
			.when()
				.get("/employee/{id}");
			
			res.then()
				.statusCode(200)
				.contentType(ContentType.JSON);
			
			res.then().body("status",is("success"));
			res.prettyPrint();
			String employee_name = res.body().jsonPath().getString("data.employee_name");   
			System.out.println("employee_name = "+employee_name);
			String employee_salary = res.body().jsonPath().getString("data.employee_salary");   
			System.out.println("employee_salary = "+employee_salary);
			String employee_age = res.body().jsonPath().getString("data.employee_age");   
			System.out.println("employee_age = "+employee_age);
}
}
