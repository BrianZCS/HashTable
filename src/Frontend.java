// --== CS400 File Header ==--
// Name: Ryan Stevenson
// Email: rstevenson5@wisc.edu
// Team: Blue
// Group: KD
// TA: Keren
// Lecturer: Gary Dahl
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.NumberFormatException;
import java.util.Collections;

public class Frontend {
	// create backend object to pull data from
	private static Backend backend;
	// create reader and file objects necessary for creating the backend object
	private static Reader r;
	private static File f = new File("./movies.csv");
	// create a list for unselected ratings
	private static List<String> unselectedRatings;
	// create numOfPages and pageIndex variables needed for baseMode() method
	private static int numOfPages;
	private static int pageIndex;

	/**
	 * Main method used for starting the program in base mode. Instantiates the Backend object,
	 * FileReader, and unselectedRatings. Also adds ratings 0-10 to the backend so all ratings are
	 * selected when the program starts.
	 */
	public static void main(String[] args) {
	    try {
	        r = new FileReader(f);
	    } catch (FileNotFoundException fnfe) {
	        fnfe.printStackTrace();
	    }
		backend = new Backend(r);
		backend.addAvgRating("0");
	    backend.addAvgRating("1");
	    backend.addAvgRating("2");
	    backend.addAvgRating("3");
	    backend.addAvgRating("4");
	    backend.addAvgRating("5");
	    backend.addAvgRating("6");
	    backend.addAvgRating("7");
	    backend.addAvgRating("8");
	    backend.addAvgRating("9");
	    backend.addAvgRating("10");
	    unselectedRatings = new ArrayList<String>();
		baseMode();
	}
	
	/**
	 * Only called in TestFrontend.java. Used to test the program.
	 * @param back The backend object passed by TestFrontend.java
	 */
	public static void run(Backend back) {
		backend = back;
		baseMode();
	}

	/**
	 * Base mode method that tells the user how to use the program and lists 3 movies at a time.
	 * In the base method, the user can select which page of movies they are viewing, and they
	 * can enter either genre or ratings mode.
	 */
	public static void baseMode() {
		System.out.println("Welcome to Movie Mapper Database!");
		System.out.println("Use numbers to select a page of 3 movies. Enter 'g' for genre mode, 'r' for ratings mode, or 'x' to quit.");
		System.out.println("Movies are sorted by their average rating, and movies are listed with their title and rating in parentheses.");
		// if there are three movies on page 1, print them all
		if (backend.getThreeMovies(0).size() >= 3) {
			System.out.println("Page 1: " + backend.getThreeMovies(0).get(0).getTitle() + " (" + backend.getThreeMovies(0).get(0).getAvgVote() + "), "
			+ backend.getThreeMovies(0).get(1).getTitle() + " (" + backend.getThreeMovies(0).get(1).getAvgVote() + "), "
			+ backend.getThreeMovies(0).get(2).getTitle() + " (" + backend.getThreeMovies(0).get(2).getAvgVote() + ")");
		}
		// if there are only two movies on page 1, print those
		else if (backend.getThreeMovies(0).size() == 2) {
			System.out.println("Page 1: " + backend.getThreeMovies(0).get(0).getTitle() + " (" + backend.getThreeMovies(0).get(0).getAvgVote() + "), "
			+ backend.getThreeMovies(0).get(1).getTitle() + " (" + backend.getThreeMovies(0).get(1).getAvgVote() + ")");
		}
		// if there is only one movie, print it
		else if (backend.getThreeMovies(0).size() == 1) {
			System.out.println("Page 1: " + backend.getThreeMovies(0).get(0).getTitle() + " (" + backend.getThreeMovies(0).get(0).getAvgVote() + ")");
		}
		// if there are no movies, direct the user to genre or ratings mode
		else {
			System.out.println("There are no movies that fit your current filters. Use genre mode or ratings mode to expand your search.");
		}
		// Determine how many pages of movies there will be
		if (backend.getNumberOfMovies() % 3 != 0) {
			numOfPages = backend.getNumberOfMovies() / 3 + 1;
		}
		else {
			numOfPages = backend.getNumberOfMovies() / 3;
		}
		System.out.println("Total number of pages: " + numOfPages);
		Scanner input = new Scanner(System.in);
		String str = input.next();
		// If the user's input is an int, then they are selecting a page number
		if (isInt(str)) {
			// If the int is a valid page number, go to that page
			if (Integer.parseInt(str) <= numOfPages && Integer.parseInt(str) > 0) {
				pageIndex = (Integer.parseInt(str) - 1) * 3;
				// if the current page has three movies, print them all
				if (backend.getThreeMovies(pageIndex).size() >= 3) {
					System.out.println("Page " + str + ": " + backend.getThreeMovies(pageIndex).get(0).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(0).getAvgVote() + "), "
					+ backend.getThreeMovies(pageIndex).get(1).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(1).getAvgVote() + "), "
					+ backend.getThreeMovies(pageIndex).get(2).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(2).getAvgVote() + ")");
				}
				// if there are only two movies on the current page, print those
				else if (backend.getThreeMovies(pageIndex).size() >= 2) {
					System.out.println("Page " + str + ": " + backend.getThreeMovies(pageIndex).get(0).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(0).getAvgVote() + "), "
					+ backend.getThreeMovies(pageIndex).get(1).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(1).getAvgVote() + ")");
				}
				// if there is only one movie, print it (the page should never be empty)
				else {
					System.out.println("Page " + str + ": " + backend.getThreeMovies(pageIndex).get(0).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(0).getAvgVote() + ")");
				}
				baseModeRepeat();
			}
			// Restart base mode if the int is not a valid page number
			else {
				System.out.println("Please enter a valid page number");
				baseModeRepeat();
			}
		}
		else if (str.equals("g")) {
			genreMode();
		}
		else if (str.equals("r")) {
			ratingsMode();
		}
		else if (str.equals("x")) {
			System.out.println("Thank you for using Movie Mapper Database!");
		}
		else {
			System.out.println("Please enter a page number, 'g', 'r', or 'x'");
			baseModeRepeat();
		}
		input.close();
	}

	/**
	 * Private helper method used to determine if a string input represents an integer.
	 * Allows baseMode() and ratingsMode() to use Integer.parseInt without throwing errors.
	 * @param num The string that is determined to be an integer or not
	 * @return true if the string represents an integer, false otherwise
	 */
	private static boolean isInt(String num) {
		if (num == null) {
			return false;
		}
		try {
			int i = Integer.parseInt(num);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Private method used to restart base mode without printing the welcome message and instructions every time.
	 */
	private static void baseModeRepeat() {
        Scanner input = new Scanner(System.in);
        String str = input.next();
        // If the user's input is an int, then they are selecting a page number
        if (isInt(str)) {
            // If the int is a valid page number, go to that page
            if (Integer.parseInt(str) <= numOfPages && Integer.parseInt(str) > 0) {
                pageIndex = (Integer.parseInt(str) - 1) * 3;
                // if the current page has three movies, print them all
                if (backend.getThreeMovies(pageIndex).size() >= 3) {
                    System.out.println("Page " + str + ": " + backend.getThreeMovies(pageIndex).get(0).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(0).getAvgVote() + "), "
                    + backend.getThreeMovies(pageIndex).get(1).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(1).getAvgVote() + "), "
                    + backend.getThreeMovies(pageIndex).get(2).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(2).getAvgVote() + ")");
                }
                // if there are only two movies on the current page, print those
                else if (backend.getThreeMovies(pageIndex).size() >= 2) {
                    System.out.println("Page " + str + ": " + backend.getThreeMovies(pageIndex).get(0).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(0).getAvgVote() + "), "
                    + backend.getThreeMovies(pageIndex).get(1).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(1).getAvgVote() + ")");
                }
                // if there is only one movie, print it (the page should never be empty)
                else {
                    System.out.println("Page " + str + ": " + backend.getThreeMovies(pageIndex).get(0).getTitle() + " (" + backend.getThreeMovies(pageIndex).get(0).getAvgVote() + ")");
                }
                baseModeRepeat();
            }
            // Restart base mode if the int is not a valid page number
            else {
                System.out.println("Please enter a valid page number");
                baseModeRepeat();
            }
        }
        else if (str.equals("g")) {
            genreMode();
        }
        else if (str.equals("r")) {
            ratingsMode();
        }
        else if (str.equals("x")) {
            System.out.println("Thank you for using Movie Mapper Database!");
        }
        else {
            System.out.println("Please enter a page number, 'g', 'r', or 'x'");
            baseModeRepeat();
        }
        input.close();
	}

	/**
	 * Genre mode lists all available genres and the user's selected genres, then allows the user
	 * to select or deselect genres. Selecting genres filters results in base mode to only show movies
	 * with all selected genres.
	 */
	public static void genreMode() {
		System.out.println("Select a genre from the list of available genres to filter results in base mode to only include movies with all selected genres.");
		System.out.println("Select a genre from the list of selected genres to remove it from your selection.");
		System.out.println("Enter x to go back to base mode.");
		System.out.println("Available genres: " + backend.getAllGenres());
		System.out.println("Selected genres: " + backend.getGenres());
		Scanner input = new Scanner(System.in);
		if (input.hasNext()) {
		  String str = input.next();
	        // If the input genre is a valid genre and is not in the user's selected genres, add it to the selected genres.
	        if (backend.getAllGenres().contains(str) && !backend.getGenres().contains(str)) {
	            backend.addGenre(str);
	            System.out.println("Selected genres: " + backend.getGenres());
	            genreModeRepeat();
	        }
	        // If the input genre is already selected, deselect it.
	        else if (backend.getGenres().contains(str)) {
	            backend.removeGenre(str);
	            System.out.println("Selected genres: " + backend.getGenres());
	            genreModeRepeat();
	        }
	        else if (str.equals("x")) {
	            baseMode();
	        }
	        else {
	            System.out.println("Please enter a genre or x to return to base mode.");
	            genreModeRepeat();
	        }
	        input.close();
		}
	}

	/**
	 * Private method used to restart genre mode without printing the instructions every time
	 */
	private static void genreModeRepeat() {
	  Scanner input = new Scanner(System.in);
      if (input.hasNext()) {
        String str = input.next();
          // If the input genre is a valid genre and is not in the user's selected genres, add it to the selected genres.
          if (backend.getAllGenres().contains(str) && !backend.getGenres().contains(str)) {
              backend.addGenre(str);
              System.out.println("Selected genres: " + backend.getGenres());
              genreModeRepeat();
          }
          // If the input genre is already selected, deselect it.
          else if (backend.getGenres().contains(str)) {
              backend.removeGenre(str);
              System.out.println("Selected genres: " + backend.getGenres());
              genreModeRepeat();
          }
          else if (str.equals("x")) {
              baseMode();
          }
          else {
              System.out.println("Please enter a genre or x to return to base mode.");
              genreModeRepeat();
          }
          input.close();
      }
	}

	/**
	 * Ratings mode allows the user to select ratings to filter results in base mode. Only movies that
	 * have an average rating that is selected will appear in base mode.
	 */
	public static void ratingsMode() {
		System.out.println("Select a rating 0-10 to filter results in base mode based on rating.");
		System.out.println("Selecting a rating will filter results to only include movies with that average rating ignoring decimals.");
		System.out.println("For example, selecing '4' will filter results to include movies with ratings 4.0-4.999...");
		System.out.println("Select a rating from the list of selected ratings to remove it from your selection.");
		System.out.println("Enter x to go back to base mode.");
		System.out.println("Selected ratings: " + backend.getAvgRatings());
	    System.out.println("Unselected ratings: " + unselectedRatings);
		Scanner input = new Scanner(System.in);
		if (input.hasNext()) {
		    String str = input.next();
	        if (isInt(str)) {
	            // if the input string is an integer between 0 and 10 and is not already selected, add it to the
	            // list of selected ratings
	            if (Integer.parseInt(str) >=0 && Integer.parseInt(str) <=10 && !backend.getAvgRatings().contains(str)) {
	                backend.addAvgRating(str);
	                unselectedRatings.remove(str);
	                List<Integer> numberList = new ArrayList<Integer>();
	                for(String number : backend.getAvgRatings() ) {
	                	   numberList.add(Integer.parseInt(number)); 
	                	}
	                Collections.sort(numberList);
	                System.out.println("Selected ratings: " + numberList);
	                List<Integer> numberList2 = new ArrayList<Integer>();
	                for(String number : unselectedRatings) {
	                	   numberList2.add(Integer.parseInt(number)); 
	                	}
	                Collections.sort(numberList2);
	                System.out.println("Unselected ratings: " + numberList2);
	                ratingsModeRepeat();
	            }
	            // if the input string is an integer between 0 and 10 and is already selected, deselect it
	            else if (backend.getAvgRatings().contains(str)) {
	                backend.removeAvgRating(str);
	                unselectedRatings.add(str);
	                List<Integer> numberList = new ArrayList<Integer>();
	                for(String number : backend.getAvgRatings() ) {
	                	   numberList.add(Integer.parseInt(number)); 
	                	}
	                Collections.sort(numberList);
	                System.out.println("Selected ratings: " + numberList);
	                List<Integer> numberList2 = new ArrayList<Integer>();
	                for(String number : unselectedRatings) {
	                	   numberList2.add(Integer.parseInt(number)); 
	                	}
	                Collections.sort(numberList2);
	                System.out.println("Unselected ratings: " + numberList2);
	                ratingsModeRepeat();
	            }
	            // if the input string is an integer that is not between 0 and 10, tell the user to select
	            // a valid integer
	            else if (Integer.parseInt(str) > 10 || Integer.parseInt(str) < 0) {
	                System.out.println("Please enter an integer between 0 and 10.");
	                ratingsModeRepeat();
	            }
	        }
	        else if (str.equals("x")) {
	            baseMode();
	        }
	        else {
	            System.out.println("Please enter an integer between 0 and 10 or 'x' to return to base mode.");
	            ratingsModeRepeat();
	        }
		}
		input.close();
	}

	/**
	 * Private method used to restart ratings mode without printing the instructions every time
	 */
	private static void ratingsModeRepeat() {
        Scanner input = new Scanner(System.in);
        if (input.hasNext()) {
            String str = input.next();
            if (isInt(str)) {
                // if the input string is an integer between 0 and 10 and is not already selected, add it to the
                // list of selected ratings
                if (Integer.parseInt(str) >=0 && Integer.parseInt(str) <=10 && !backend.getAvgRatings().contains(str)) {
                    backend.addAvgRating(str);
                    unselectedRatings.remove(str);
	                List<Integer> numberList = new ArrayList<Integer>();
	                for(String number : backend.getAvgRatings() ) {
	                	   numberList.add(Integer.parseInt(number)); 
	                	}
	                Collections.sort(numberList);
	                System.out.println("Selected ratings: " + numberList);
	                List<Integer> numberList2 = new ArrayList<Integer>();
	                for(String number : unselectedRatings) {
	                	   numberList2.add(Integer.parseInt(number)); 
	                	}
	                Collections.sort(numberList2);
	                System.out.println("Unselected ratings: " + numberList2);
                    ratingsModeRepeat();
                }
                // if the input string is an integer between 0 and 10 and is already selected, deselect it
                else if (backend.getAvgRatings().contains(str)) {
                    backend.removeAvgRating(str);
                    unselectedRatings.add(str);
	                List<Integer> numberList = new ArrayList<Integer>();
	                for(String number : backend.getAvgRatings() ) {
	                	   numberList.add(Integer.parseInt(number)); 
	                	}
	                Collections.sort(numberList);
	                System.out.println("Selected ratings: " + numberList);
	                List<Integer> numberList2 = new ArrayList<Integer>();
	                for(String number : unselectedRatings) {
	                	   numberList2.add(Integer.parseInt(number)); 
	                	}
	                Collections.sort(numberList2);
	                System.out.println("Unselected ratings: " + numberList2);
                    ratingsModeRepeat();
                }
                // if the input string is an integer that is not between 0 and 10, tell the user to select
                // a valid integer
                else if (Integer.parseInt(str) > 10 || Integer.parseInt(str) < 0) {
                    System.out.println("Please enter an integer between 0 and 10.");
                    ratingsModeRepeat();
                }
            }
            else if (str.equals("x")) {
                baseMode();
            }
            else {
                System.out.println("Please enter an integer between 0 and 10 or 'x' to return to base mode.");
                ratingsModeRepeat();
            }
        }
        input.close();
  	}
}
