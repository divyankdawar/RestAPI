import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

public class Basics {
	public static void main(String args[]) throws IOException {
         // For static packages eclipse will not show auto suggestions 
		 // Validate if Add Place API is working as Expected 
         //Add Place -> Update Place with New Address -> Get place to validate if New Address is present in response 
		 // Given - all input details
	     //When - Submit the API - resource, http method
	     //Then - Validate the response
         // content of the file to string -> content of file can convert into byte -> data to string 
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response =given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
//				.body(payload.AddPlace()).when().post("maps/api/place/add/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\DIVYANK\\Desktop\\addPlace.json")))).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();	
// The header written above are output validation
		System.out.println(response);
		// From the above response I only want to extract place ID so we have to parse the JSON
		//Parsing the Json it is taking string as Input and converting it as Json
		JsonPath js = new JsonPath(response); // for parsing Json
		String placeId = js.getString("place_id");
		System.out.println(placeId);

		// Update Place

		String newAddress = "San Francisco, Washington";
// By adding place id like this we are telling java that do not read it as a string read it as a variable 
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").
		when().put("maps/api/place/update/json")
		.then().assertThat().log().all().body("msg",equalTo("Address successfully updated"));

		//Get Place
		// In when we write the resource name
		String getPlaceResponse	= given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId).when().get("maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();

//		JsonPath js1 = new JsonPath(getPlaceResponse);
		
		//Another way if we don't want the java assertion
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);

		//TestNg

		Assert.assertEquals(actualAddress,newAddress);




	}
}