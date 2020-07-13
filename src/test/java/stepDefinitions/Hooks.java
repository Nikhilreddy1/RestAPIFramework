package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		// Write a code that will give you place_id
		// Execute this code only when place_id is null

		MyStepDefinition sd = new MyStepDefinition();
		if (MyStepDefinition.place_id == null) {
			sd.add_place_payload("Nikhil", "EN", "BTM 2nd stage");
			sd.user_calls_with_http_request("addPlaceAPI", "POST");
			sd.verify_place_id_created_maps_to_using("Nikhil", "getPlaceAPI");
		}
	}
}
