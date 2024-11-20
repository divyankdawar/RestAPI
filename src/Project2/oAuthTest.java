package Project2;

import static io.restassured.RestAssured.given;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;

public class oAuthTest {
	public static void main(String args[]) throws InterruptedException {
	
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
/*		System.setProperty("webdriver.chrome.driver", "E:\\Canada Preparation\\Chrome & Gecko Driver\\chromedriver_win32\\chromedriver.exe");
        //We are creating a ChromeDriver Instance and storing it in a variable called driver, which is of type 'WebDriver' interface.
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("divyankfl@gmail.com");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("divyankfl@gmail.com");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		String url = driver.getCurrentUrl();
*/		
		String url  = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVHEtk4I2_GgcN6ozXdsGYTXCdXYSnfyhtPhbopP-Pl3XIlQfJhxu8Jku_Yxz3vrx66bKQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		System.out.println(code);
		
		String response = given().urlEncodingEnabled(false)
				.queryParam("code",code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		
		 System.out.println(accessToken);
//r2 is an object 
		GetCourse r2 = given().contentType("application/json").queryParam("access_token",accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php")
				.as(GetCourse.class);
		System.out.println(r2);
		System.out.println(r2.getLinkedIn());
		System.out.println(r2.getInstructor());
		System.out.println(r2.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses = r2.getCourses().getApi();
		
		for(int i=0;i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		//Get the course names for web Automation 
		ArrayList<String> actualList = new ArrayList<String>();
		List<pojo.WebAutomation> w = r2.getCourses().getWebAutomation();
		
		for(int j=0;j<w.size();j++) {
			actualList.add(w.get(j).getCourseTitle());
		}
		
		//Converting Array to ArrayList
		List<String> expectedList = Arrays.asList(courseTitles);
		
		Assert.assertTrue(actualList.equals(expectedList));
		
	}

}