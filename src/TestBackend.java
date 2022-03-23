// --== CS400 File Header Information ==--
// Name: Jack Gundrum
// Email: jpgundrum@wisc.edu
// Team: Blue
// Group: KD
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader:
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a set of tests for the back end of the Movie Mapper project.
 * 
 * @author jackgundrum
 */
public class TestBackend {
	
	public static void main(String[] args) {
		(new TestBackend()).runTests();
	}


	public void runTests() {
		// add all tests to this method
	    
		if (this.testInitialNumberOfMovies()) {
			System.out.println("Test initial number of movies: PASSED");
		} else {
			System.out.println("Test initial number of movies: FAILED");
		}
	    if(this.testAddGenre())    {
	        System.out.println("Test add genre: PASSED");
	    }
	    else {
	        System.out.println("Test add genre: FAILED");
	    }
		if (this.testGetAllGenres()) {
			System.out.println("Test get all genres: PASSED");
		} else {
			System.out.println("Test get all genres: FAILED");
		}
		if (this.testGetThreeMoviesInitial()) {
			System.out.println("Test get three movies sorted by rating: PASSED");
		} else {
			System.out.println("Test get three movies sorted by rating: FAILED");
		}
		if (this.testNumberOfMovies()) {
            System.out.println("Test get number of movies: PASSED");
         } else {
            System.out.println("Test get number of movies: FAILED"); 
		}
		if(this.testGetAverageRatings()){
		    System.out.println("Test get average ratings: PASSED");
        } else {
            System.out.println("Test get average ratings: FAILED");
               
       } 	
	}
	
	/**
     * This test instantiates the back end with movies from data base and tests whether the
     * initial selection is empty (getNumberOfMovies() returns 0). It passes when
     * 0 is returned and fails in all other cases, including when an exception is
     * thrown.
     * @return true if the test passed, false if it failed
     */
    public boolean testInitialNumberOfMovies() {
        try {
            // instantiate once BackendInterface is implemented
           
            BackendInterface backendToTest = new Backend(new FileReader(new File("./movies.csv")));
            
            if (backendToTest.getNumberOfMovies() == 0) {
                // test passed
                return true;
            } else {
                // test failed
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // test failed
            return false;
        }
    }
	
	/**
     * This test instantiates the back end with three movies and tests whether the
     * adding a genre works as expected. It uses the removeGenre and getGenres method 
     * to further test the behavior is as expected.
     * 
     * @return true if the test passed, false if it failed
     */
    public boolean testAddGenre() {
        try {
            String genre = "Western";
            String genre2 = "Thriller";
            String genre3 = "Action";
            List<String> test = new ArrayList<String>();
            List<String> toCompare = new ArrayList<String>();
            BackendInterface backendToTest = new Backend(new StringReader(
                "title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
                + "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
                + "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
                + "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
        ));
            backendToTest.addGenre(genre);
            backendToTest.addGenre(genre2);
            backendToTest.addGenre(genre3);
            test.add(genre);
            test.add(genre2);
            test.add(genre3);
            toCompare = backendToTest.getGenres();
            if(!toCompare.equals(test)) {   // checks if genre list and expected is the same
                return false;
            }
            backendToTest.removeGenre(genre2);
            toCompare = backendToTest.getGenres();
            test.remove(genre2);
            if(!toCompare.equals(test)) {  // checks equality after removing a genre
                return false;
            }
            else {
                return true;
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * This test instantiates the back end with three movies and tests whether
	 * the getAllGenres method return the correct set of genres for those three
	 * movies.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testGetAllGenres() {
		try {
			// instantiate once BackendInterface is implemented
			BackendInterface backendToTest = new Backend(new StringReader(
					"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
					+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
					+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
					+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
			));
			if (backendToTest.getAllGenres().size() == 5
					&& backendToTest.getAllGenres().contains("Horror")
					&& backendToTest.getAllGenres().contains("Action")
					&& backendToTest.getAllGenres().contains("Comedy")
					&& backendToTest.getAllGenres().contains("Musical")
					&& backendToTest.getAllGenres().contains("Romance")) {
				// test passed
				return true;
			} else {
				// test failed
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// test failed
			return false;
		}
	}
	
	/**
	 * This test instantiates the back end with three movies and tests whether the
	 * initial list returned by getThreeMovies starting with the first movie (0)
	 * is empty. It passes when 0 is returned and fails in all other cases, including
	 * when an exception is thrown.
	 * @return true if the test passed, false if its failed
	 */
	public boolean testGetThreeMoviesInitial() {
		try {
			// instantiate once BackendInterface is implemented
			BackendInterface backendToTest = new Backend(new FileReader(new File("./movies.csv")));
			
			List<MovieInterface> test = new ArrayList<MovieInterface>();
			if(!backendToTest.getThreeMovies(0).equals(test)) {  // checks initial empty list
			    return false;
			}
			else {
				// test failed
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// test failed
			return false;
		}
	}
	
	/**
     * This test instantiates the back end with movies from data base and tests whether the
     * size returned by getNumberOfMovies.size() is equal to 14. If the size is 14
     * then the getNumberOfMovies method works as expected. But if it doesn't I know there
     * is an error.
     * @return true if the test passed, false if its failed
     */
	public boolean testNumberOfMovies()  {
	    try    {
	        BackendInterface backendToTest = new Backend(new FileReader(new File("./movies.csv")));
	        backendToTest.addGenre("Drama");
            backendToTest.addGenre("War");
            backendToTest.addGenre("Action");
            backendToTest.addAvgRating("6");
            if(backendToTest.getNumberOfMovies() != 1) {    // only 1 movie in data base
                return false;                               // has these genres and rating
            }
            backendToTest.removeGenre("War");
            backendToTest.removeGenre("Action");
            if(backendToTest.getNumberOfMovies() != 14) {   // should be 14 movies that work
                return false;
            }else {
	            // test failed
	            return true;
	        }
	    }
	    catch (Exception e)    {
	        e.printStackTrace();
	        // test failed
	        return false;
	    }
	}
	
	/**
     * This test instantiates the back end with three movies and tests whether the
     * getAvgRatings() method call works as expected. If it does, then the test should pass.
     * It it fails I know there is an error in my getAvgRatings method in my BackendInterface.
     * @return true if the test passed, false if its failed
     */
	public boolean testGetAverageRatings()   {
	    try {
	        BackendInterface backendToTest = new Backend(new StringReader(
	            "title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
	            + "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
	            + "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
	            + "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
	    ));
	        // Trying to see if the inputed ratings work as expected. 
	        // getAvgRatings should return a string list of ratings rounded to the left most digit
	        List<String> test = new ArrayList<String>();
	        List<String> toCompare = new ArrayList<String>();
	        test.add("3");
	        test.add("2");
	        test.add("5");
	        backendToTest.addAvgRating("3");
	        backendToTest.addAvgRating("2");
	        backendToTest.addAvgRating("5");
	        toCompare = backendToTest.getAvgRatings(); // calls method and the lists should match
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
