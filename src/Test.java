import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		System.out.println(testGetAverageRatings());
	}
	
	public static boolean testGetAverageRatings()   {
	    try {
	        BackendInterface backendToTest = new Backend(new FileReader(new File("./movies.csv")));
	        // Trying to see if the inputed ratings work as expected. 
	        // getAvgRatings should return a string list of ratings rounded to the left most digit
	        List<String> test = new ArrayList<String>();
	        List<String> toCompare = new ArrayList<String>();
	        test.add("3");
	        test.add("2");
	        test.add("5");
	        backendToTest.addAvgRating("0");
	        backendToTest.addAvgRating("1");
	        backendToTest.addAvgRating("2");
	        backendToTest.addAvgRating("3");
	        backendToTest.addAvgRating("4");
	        backendToTest.addAvgRating("5");
	        backendToTest.addAvgRating("6");
	        backendToTest.addAvgRating("7");
	        backendToTest.addAvgRating("8");
	        backendToTest.addAvgRating("9");
	        backendToTest.addAvgRating("10");
		    backendToTest.addAvgRating("10");
	        System.out.println( backendToTest.getAvgRatings()); // calls method and the lists should match
	        if(!toCompare.equals(test)) {              // tests to see if they are equal
	            return false;
	            // test failed
	        } else {
	                return true;
	             // test passed
	            }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	        // test failed
	        return false;
	    }
	}
}
