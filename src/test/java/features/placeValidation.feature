Feature: Validating place API's
@AddPlace @Regression
Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
	Given Add place payload "<name>" "<language>" "<address>"
	When User calls "addPlaceAPI" with "POST" Http request
	Then The API call is success with status code 200 
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And Verify place_id created maps to "<name>" using "getPlaceAPI"
	
Examples:
	|name  |language |address|
	|Nikhil|Telugu   |BTM 2nd stage|
#	|Vishnu|English  |Marathalli|

@DeletePlace @Regression
Scenario: Verify if delete place functionality is working fine
	Given deletePlaceAPI Payload
	When User calls "deletePlaceAPI" with "POST" Http request
	Then The API call is success with status code 200
	And "status" in response body is "OK"
	
