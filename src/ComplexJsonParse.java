import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// This is mocking the res
		JsonPath js = new JsonPath(payload.CoursePrice());

		
		// Return Number of courses
		int count = js.getInt("courses.size()");
		/*		System.out.println(count);

		//Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		String website = js.getString("dashboard.website");
		System.out.println(totalAmount);

		//Print title of the first course 
		System.out.println(website);
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		 */
		//Print All course titles and their respective courses

		for(int i=0;i<count;i++) {
			String courseTitles = js.get("courses["+i+"].title");
			System.out.println(js.get("courses["+i+"].price").toString());
			System.out.println(courseTitles);

		}

		//Print no of copies sold by RPA Course
		System.out.println("Print no of copies sold by RPA Course");

		for(int i =0 ;i<count;i++) {
			String courseTitles = js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		//Verify if Sum of all Courses prices matches with Purchase Amount
		//It is in SumValidation Class
            		 
		
	}

}


