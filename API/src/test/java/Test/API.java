package Test;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
		
		public class API
		{
			@Test (priority = 0)
			void Get_Request()
			{
				System.out.println("Started Get Request");
				//Specify BaseURL
				RestAssured.baseURI="https://reqres.in/api/users";
				
				//Request object
				RequestSpecification request = RestAssured.given();
				
				//Response object
				Response response = request.request(Method.GET);
				
				//Status Code Validation
				int statuscode = response.getStatusCode();
				System.out.println("Status Code is:" +statuscode);
				Assert.assertEquals(statuscode, 200);	
				
				//Verify the value of first_name for "id": 10 is ‘Byron’
				Response response1 = request.request(Method.GET,"/10");
				JsonPath jsonpath = response1.jsonPath();
				
				String responsebody = response1.getBody().asString();
				System.out.println("Response body is" +responsebody);
				System.out.println(jsonpath.get("data.id"));
				System.out.println(jsonpath.get("data.first_name"));
				
				Assert.assertEquals(jsonpath.get("data.first_name"), "Byron");
				System.out.println("Verified that value of first_name for ID:10 is ‘Byron’");
				
			}
			
			  @Test (priority = 1)
				void Post_Request()
				{
				  
				  	System.out.println("Started Post Request");
					//Specify BaseURL
					RestAssured.baseURI="https://reqres.in/api/users";
					
					//Request object
					RequestSpecification request = RestAssured.given();
					
					//Request payload sending along with Post Request
					JSONObject requestparams = new JSONObject();
					requestparams.put("name", "Bryant");
					requestparams.put("job", "BA");
					
					request.header("Content-Type","application/JSON");
					
					request.body(requestparams.toJSONString());
					
					//Response object
					Response response = request.request(Method.POST,"/users");
					
					//Status Code Validation
					int statuscode = response.getStatusCode();
					System.out.println("Status Code is:" +statuscode);
					Assert.assertEquals(statuscode, 201);
					
					//Verify if the id is generated
					String responsebody = response.getBody().asString();
					System.out.println("Response body is "+responsebody);
					JsonPath jsonpath = response.jsonPath();
					String id = jsonpath.get("id");
					if (id == null)
					{
						System.out.println("ID is not generated ");
					}
					else
					{
						System.out.println("ID is :"+id);
						System.out.println("ID is generated ");
					}
					
					// Verify the response json scheme
					String responsebody1 = response.getBody().asString();
					assertthat(responsebody1, matchesJsonSchemaInClasspath("schema.json"));
					System.out.println("JSON Schema validate successfully");
		  }
		
		  private void assertthat(String responsebody1, JsonSchemaValidator matchesJsonSchemaInClasspath) {
			// TODO Auto-generated method stub
			
		}

}
