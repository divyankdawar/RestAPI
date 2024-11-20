package libraryAPI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

//This is for Section 7 Udemy Rest API and Postman collection is Library API
public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle) { 

		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json").
				body(payload.Addbook(isbn,aisle)).
				when()
				.post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);

	}
	//Given should be imported manually 
	//Data Provider : - 	
	@DataProvider(name = "BooksData")
	public Object[][] getData() {

		//	array = collection of elements
		//	multidimensional arrays = collection of arrays
		return new Object[][] {{"querty","357159"},{"abghy","55687"}};

	}
}