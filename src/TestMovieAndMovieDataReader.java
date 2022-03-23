// --== CS400 File Header Information ==--
// Name: Alan Jordao Cortez
// Email: ajcortez@wisc.edu 
// Team: blue
// Group: KD
// TA: Keren
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * This class contains a set of tests for the MovieInterface and MovieDataReaderInterface
 * implementation of the Movie Mapper project.
 */
public class TestMovieAndMovieDataReader {
	
	MovieDataReaderInterface readerToTest;
	
	public static void main(String[] args) {
		(new TestMovieAndMovieDataReader()).runTests();
	}

	/**
	 * This method calls all of the test methods in the class and ouputs pass / fail
	 * for each test.
	 */
	public void runTests() {
		// instantiate reader to test once it is implemented
		readerToTest = new MovieDataReader();
		
		// add all tests to this method
		if (this.testReaderNumberOfMovies()) {
			System.out.println("Test number of movies: PASSED");
		} else {
			System.out.println("Test number of movies: FAILED");
		}
		if (this.testReaderMovieTitles()) {
			System.out.println("Test movie titles: PASSED");
		} else {
			System.out.println("Test movie titles: FAILED");
		}
		if (this.testMovieOrder()) {
			System.out.println("Test movie order: PASSED");
		} else {
			System.out.println("Test movie order: FAILED");
		}
		if (this.testReadDataSet()) {
			System.out.println("Test read dataset: PASSED");
		} else {
			System.out.println("Test read dataset: FAILED");
		}
		if (this.testGetGenres()) {
			System.out.println("testGetGenres: PASSED");
		} else {
			System.out.println("testGetGenres: FAILED");
		}
		if (this.testGetAvgRateAndCompareTo()) {
			System.out.println("testGetAvgRateAndCompareTo: PASSED");
		} else {
			System.out.println("testGetAvgRateAndCompareTo: FAILED");
		}
		if (this.testGetDirectorAndGetDescription()) {
			System.out.println("testGetDirectorAndGetDescription: PASSED");
		} else {
			System.out.println("testGetDirectorAndGetDescription: FAILED");
		}
		if (this.testGetTitleAndGetYear()) {
			System.out.println("testGetTitleAndGetYear: PASSED");
		} else {
			System.out.println("testGetTitleAndGetYear: FAILED");
		}
	}
	
	/**
	 * This test reads in 3 movies and tests whether the list of movies
	 * returned is of size 3. It fails if the size is not 3 or if an
	 * exception occurs while reading in the movies.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testReaderNumberOfMovies() {
		List<MovieInterface> movieList;
		try {
			movieList = readerToTest.readDataSet(new StringReader(
					"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
					+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
					+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Par�, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
					+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
			));
		} catch (Exception e) {
			e.printStackTrace();
			// test failed
			return false;
		}
		if (movieList.size() == 3) {
			// test passed
			return true;
		} else {
			// test failed
			return false;
		}
	}

    /**
	 * This test reads in 3 movies and tests whether the list of movies
	 * contains all 3 titles of the source data in any order. It fails
	 * if the list returned is missing one or more titles or if an
	 * exception occurs while reading in the data.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testReaderMovieTitles() {
		List<MovieInterface> movieList;
		try {
			movieList = readerToTest.readDataSet(new StringReader(
					"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
					+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
					+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Par�, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
					+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
			));
		} catch (Exception e) {
			e.printStackTrace();
			// test failed
			return false;
		}
		String title1 = "The Source of Shadows";
		String title2 = "The Insurrection";
		String title3 = "Valley Girl";
		boolean equalOne = true;
		// check if first movie is has of the above titles
		equalOne = equalOne && (title1.equals(movieList.get(0).getTitle()) ||
								title2.equals(movieList.get(0).getTitle()) ||
								title3.equals(movieList.get(0).getTitle()));
		// check if second movie is has of the above titles
		equalOne = equalOne && (title1.equals(movieList.get(1).getTitle()) ||
								title2.equals(movieList.get(1).getTitle()) ||
								title3.equals(movieList.get(1).getTitle()));
		// check if third movie is has of the above titles
		equalOne = equalOne && (title1.equals(movieList.get(2).getTitle()) ||
								title2.equals(movieList.get(2).getTitle()) ||
								title3.equals(movieList.get(2).getTitle()));
		// true if the three movies have the right titles
		return equalOne;
	}

	/**
	 * This test reads in 3 movies, then sorts them. It then checks whether
	 * the default sorting order is descending based on the average ratings.
	 * @return true if the test passed, false if it failed
	 */
	public boolean testMovieOrder() {
		List<MovieInterface> movieList;
		try {
			movieList = readerToTest.readDataSet(new StringReader(
					"title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
					+ "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor Botkin\",Four Thieves Productions,\"Ashleigh Allard, Tom Bonington, Eliane Gagnon, Marissa Kaye Grinestaff, Jenna Heffernan, Joshua Hummel, Janice Kingsley, Chris Labasbas, Jared Laufree, Dominic Lee, Vic May, Sienna Mazzone, Lizzie Mounter, Grace Mumm, Ashley Otis\",\"A series of stories woven together by one of our most primal fears, the fear of the unknown.\",3.5\n"
					+ "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Par�, Wilma Elles, Joseph Camilleri, Rebecca Tarabocchia, Jeanine Harrington, Malorie Glavan, Danner Boyd, Michael Cendejas, Woody Clendenen, Keely Dervin, Aaron Harvey, Tony Jackson, Michael Jarrod, Angelina Karo, Bernie Kelly\",The director of the largest media company wants to expose how left-wing powers use film to control populations.,2.9\n"
					+ "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",Sneak Preview Productions,\"Jessica Rothe, Josh Whitehouse, Jessie Ennis, Ashleigh Murray, Chloe Bennet, Logan Paul, Mae Whitman, Mario Revolori, Rob Huebel, Judy Greer, Alex Lewis, Alex MacNicoll, Danny Ramirez, Andrew Kai, Allyn Rachel\",\"Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay together. A musical adaptation of the 1983 film.\",5.4\n"
			));
		} catch (Exception e) {
			e.printStackTrace();
			// test failed
			return false;
		}
		Collections.sort(movieList);
		double lastRating = 11.0;
		for (MovieInterface movie : movieList) {
			if (movie.getAvgVote() > lastRating) {
				// test fails
				return false;
			}
			lastRating = movie.getAvgVote();
		}
		// test passes
		return true;
	}
	
	/**
	 * This method is responsible for testing the readDataSet method from movie class
	 * @return true if all method passed all tests, false otherwise.
	 */
	public boolean testReadDataSet() {
		
		// getting database ready(reading it) to enter the method
		FileReader databaseReader = null;
		try {
			databaseReader = new FileReader(new File("./movies.csv"));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		List movieList = new LinkedList();
		try {
			movieList = readerToTest.readDataSet(databaseReader);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (DataFormatException e) {
			System.out.println(e.getMessage());
			return false;
		}
		//check if the list size has changed to 231
		if(movieList.size() != 231) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method is responsible for testing the getTitle and getYear methods from movie class
	 * @return true if all method passed all tests, false otherwise.
	 */
	public boolean testGetTitleAndGetYear() {
		// getting database ready(reading it) to enter the method
		FileReader databaseReader = null;
		try {
			databaseReader = new FileReader(new File("./movies.csv"));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		List movieList;
		try {
			movieList = readerToTest.readDataSet(databaseReader);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (DataFormatException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		//try getting the title of 3 movies from database
		if(!((Movie) movieList.get(0)).getTitle().equals("La battaglia di Long Tan")) {
			return false;
		}
		
		if(!((Movie) movieList.get(183)).getTitle().equals("Camp Blood 8: Revelations")) {
			return false;
		}
		
		if(!((Movie) movieList.get(230)).getTitle().equals("My Salinger Year")) {
			return false;
		}
		
		//try getting the year of 3 movies
		if(!(((Movie) movieList.get(1)).getYear() == 2019)) {
			return false;
		}
		
		if(!(((Movie) movieList.get(89)).getYear() == 2019)) {
			return false;
		}
		
		if(!(((Movie) movieList.get(227)).getYear() == 2020)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method is responsible for testing the getDirector and getDescription method from movie class
	 * @return true if all method passed all tests, false otherwise.
	 */
	public boolean testGetDirectorAndGetDescription() {
		// getting database ready(reading it) to enter the method
		FileReader databaseReader = null;
		try {
			databaseReader = new FileReader(new File("./movies.csv"));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		List movieList;
		try {
			movieList = readerToTest.readDataSet(databaseReader);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (DataFormatException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		//try getting the director of 3 movies from database
		if(!((Movie) movieList.get(2)).getDirector().equals("Jeff Tremaine")) {
			return false;
		}
		
		if(!((Movie) movieList.get(8)).getDirector().equals("Stephen Cedars, Benji Kleiman")) {
			return false;
		}
		
		if(!((Movie) movieList.get(219)).getDirector().equals("Rob Savage")) {
			return false;
		}
		
		//try getting the description of 3 movies from database
		if(!((Movie) movieList.get(0)).getDescription().equals("In August 1966, in a Vietnamese rubber plantation called Long Tan, 108 young and inexperienced Australian and New Zealand soldiers are fighting for their lives against 2500 North Vietnamese and Viet Cong soldiers.")) {
			System.out.println(((Movie) movieList.get(0)).getDescription());
			return false;
		}
		
		if(!((Movie) movieList.get(97)).getDescription().equals("A 20 something woman goes back to her hometown for the holidays for the first time in several years and is not only met with people from her past, but she's forced to deal with unresolved ...")) {
			return false;
		}
		
		if(!((Movie) movieList.get(194)).getDescription().equals("Five friends go on a vacation to a cabin in the woods, but find that leaving isn't as simple as just walking away.")) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method is responsible for testing the getAvgRate and CompareTo methods from movie class
	 * @return true if all method passed all tests, false otherwise.
	 */
	public boolean testGetAvgRateAndCompareTo() {
		// getting database ready(reading it) to enter the method
		FileReader databaseReader = null;
		try {
			databaseReader = new FileReader(new File("./movies.csv"));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		List movieList;
		try {
			movieList = readerToTest.readDataSet(databaseReader);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (DataFormatException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		//try getting the average vote for 3 movies
		if((((Movie) movieList.get(1)).getAvgVote() != (float) 6.7)) {
			System.out.println(((Movie) movieList.get(1)).getAvgVote());
			return false;
		}
		
		if((((Movie) movieList.get(89)).getAvgVote() != (float) 4.5)) {
			return false;
		}
		
		if((((Movie) movieList.get(227)).getAvgVote() !=  (float) 5.1)) {
			return false;
		}
		
		//try comparing a movie with a lower rating movie - expects to return -1
		Movie toBeCompared = ((Movie) movieList.get(227));
		if(toBeCompared.compareTo( (Movie) movieList.get(89)) != -1) {
			return false;
		}
		
		//try comparing a movie with a higher rating movie - expects to return 1
		if(toBeCompared.compareTo( (Movie) movieList.get(1)) != 1) {
			return false;
		}
		
		//try comparing a movie with itself(same rating) - expects 0
		if(toBeCompared.compareTo(toBeCompared) != 0) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method is responsible for testing the getGenres method from movie class
	 * @return true if all method passed all tests, false otherwise.
	 */
	public boolean testGetGenres() {
		// getting database ready(reading it) to enter the method
		FileReader databaseReader = null;
		try {
			databaseReader = new FileReader(new File("./movies.csv"));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		List movieList;
		try {
			movieList = readerToTest.readDataSet(databaseReader);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (DataFormatException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		//try getting the genres of 2 movies with more than one genre.
		List genres1 = ( (Movie)  movieList.get(9) ).getGenres();
		//check for the list size
		if(genres1.size() != 2) {
			return false;
		}
		//check for its content 
		if( !(genres1.get(0).equals("Comedy") && genres1.get(1).equals("Drama")) ) {
			return false;
		}
		
		List genres2 = ( (Movie)  movieList.get(53) ).getGenres();

		//check for the list size
		if(genres2.size() != 3) {
			return false;
		}
		//check for its content 
		if( !(genres2.get(0).equals("Comedy") && genres2.get(1).equals("Drama") && genres2.get(2).equals("Romance")) ) {
			return false;
		}
		
		//Try getting the genre of 2 movies with one genre only
		List genres3 = ( (Movie)  movieList.get(64) ).getGenres();
		//check for the list size
		if(genres3.size() != 1) {
			return false;
		}
		//check for its content 
		if( !(genres3.get(0).equals("Romance")) ) {
			return false;
		}
		
		List genres4 = ( (Movie)  movieList.get(230) ).getGenres();

		//check for the list size
		if(genres4.size() != 1) {
			return false;
		}
		//check for its content 
		if( !(genres4.get(0).equals("Drama")) ) {
			return false;
		}		
		return true;
	}

}
