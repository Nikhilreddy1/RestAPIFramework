package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class MyStepDefinition extends Utils{
	RequestSpecification res; //These variables need not be static as they become null after each scenario and are updated with new data
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id; //Need to use static here as this variable needs to be accessed across multiple scenarios
	
	@Given("Add place payload {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException 	 {
		res = given().spec(RequestSpecification()).body(data.AddPlacePayLoad(name, language, address));
	}
	
	@When("User calls {string} with {string} Http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		if(httpMethod.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getResource());
		else if(httpMethod.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	@Then("The API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(),200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {	
    	assertEquals(getJsonPath(response, keyValue	), expectedValue);
	}
	
	@Then("Verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		place_id = getJsonPath(response,"place_id");
		res = given().spec(RequestSpecification()).queryParam("place_id",place_id);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response,"name");
		assertEquals(actualName, expectedName);
	}
	
	@Given("deletePlaceAPI Payload")
	public void delete_place_api_payload() throws IOException {
		res = given().spec(RequestSpecification()).body(data.deletePlacePayload(place_id));
		
	}
}