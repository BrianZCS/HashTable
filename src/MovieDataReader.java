// --== CS400 File Header Information ==--
// Name: Alan Jordao Cortez
// Email: ajcortez@wisc.edu 
// Team: blue
// Group: KD
// TA: Keren
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class MovieDataReader implements MovieDataReaderInterface {
	/*
	 * Method responsible to read the database of movies and putting them in a list of movie objects
	 * @param FileReader object with the file that will be read.
	 * @return a List object of Moive objects.
	 */
	@Override
	public List<MovieInterface> readDataSet(Reader inputFileReader)
			throws FileNotFoundException, IOException, DataFormatException {
		
		Scanner input = new Scanner(inputFileReader);
		input.useDelimiter("\n");   //sets the delimiter pattern, every new line is a new element
		
		//local variables that will hold movie values
		String movTitle = null;
		Integer movYear = 0;
		String movDirector = null;
		String movDescription = null;
		Float movAvgVote = (float) 0.0;
		List<String> movGenres = new LinkedList<String>();
		String[] movArr = new String[1000]; //oversize array that temporatly holds movies strings from database
		int movArrSize = 0; //holds size of oversize array
		
		input.next(); //first line is not a movie
		int counter = 0;
		while (input.hasNext()) {   
		 movArr[counter] = input.next();  //find and returns the next complete token from this scanner
		 movArrSize++;
		 counter++;
		}   
		
		List<MovieInterface> moviesList = new LinkedList(); //holds the List of movie objects that will be returned
		
		for(int i = 0; i < movArrSize; i++) { 
			
			movTitle = getTitleHelper(movArr[i]);
			movYear = getYearHelper(movArr[i]);
			movDirector = getDirectorHelper(movArr[i]);
			movDescription = getDescriptionHelper(movArr[i]);
			movAvgVote = getAvgVoteHelper(movArr[i]);
			movGenres = getGenresHelper(movArr[i]);
			
			Movie movieToAdd = new Movie(movTitle, movYear, movDirector, movDescription, movAvgVote, movGenres);
			moviesList.add(movieToAdd);
		}
		
		return moviesList;
	}
	
	/*
	 * Helper method to read Average vote from current movie line
	 * @param String that holds all info of a line(movie) from database
	 * @return Float object that holds Average rate vote for such a movie
	 */
	private Float getAvgVoteHelper(String movieData) {
		int startVoteIndex = 0; //where average vote starts in string
		for(int j = movieData.length() -1; j > 0; j-- ) {
			if(movieData.charAt(j) == ',') { //if last comma is found in the movieData string   
				startVoteIndex = j+1; //get index after such a comma
				break;
			}
		}
		String voteStr = movieData.substring(startVoteIndex, movieData.length() -1);
		Float avgVote = new Float(voteStr);
		return avgVote;
	}
	
	/*
	 * Helper method to read Description from current movie line
	 * @param String that holds all info of a line(movie) from database
	 * @return String that holds description movie description from such a movie 
	 */
	private String getDescriptionHelper(String movieData) {
		
		int commaCounter = 0; //counts how many valid commas have been read
		boolean validComma = true; //only valid commas(commas between categories, such as title and year) are counted
		boolean enteredDescrip = false; 
		int startDescriptionIndex = 0;
		int endDescriptionIndex = 0;
		
		for(int i = 0; i < movieData.length(); i++) {
			if(commaCounter == 11 && movieData.charAt(i) == '"') { //makes sure the pointer is on the description
				enteredDescrip = true;
			}
			if(movieData.charAt(i) == '"' && enteredDescrip == false) { 
				validComma = !validComma; //switches the value of valid comma until closing double quotes shows up
			}
			
			if(validComma && movieData.charAt(i) == ',') { //if a valid comma is seen, increment comma counter
				commaCounter++;
			}
			
			if(enteredDescrip) {
				//can get index of beginning of description, then break
				startDescriptionIndex = i+1; //char after enteredDescript char(the opening souble quotes)
				break;
			}
		}
		
		for(int j = movieData.length() -1; j > startDescriptionIndex; j--) {
			if(movieData.charAt(j) == '"') {
				//saves j and break(last double quotes of movieDta string).
				endDescriptionIndex = j; 
				break;
			}
		}
		
		String description = movieData.substring(startDescriptionIndex , endDescriptionIndex); 
		return description;		
	}
	
	/*
	 * Helper method to read director from current movie line
	 * @param String that holds all info of a line(movie) from database
	 * @return String that holds director info
	 */
	private String getDirectorHelper(String movieData) {
		int commaCounter = 0;
		boolean validComma = true;
		int elementLength = 0;
		boolean severalDirec = false;
		for(int i = 0; i < movieData.length(); i++) {
			if(movieData.charAt(i) == '"') {
				validComma = !validComma; //switches the value of valid comma until closing double quotes shows up
			}			
			if(validComma && movieData.charAt(i) == ',') {
				commaCounter++;
			}
			if(commaCounter == 7) {
				if(movieData.charAt(i+1) == '"' && !severalDirec) { //checks if there is more than one director(they are between double quotes)
					severalDirec = true;
				}
				if(severalDirec) {
					if(movieData.charAt(i+2) == '"') {
						//i+2 avoids the comma and double quotes; -elementLength is to get the string just read, because the pointer has moved as its length is being counted.
						String directors = movieData.substring(i+2 -elementLength , i+2); 
						return directors;
					}
					elementLength++;
				} else {
					if(movieData.charAt(i+1) == ',') { //while 8th comma(ending comma for director) doesn't shows up
						//i+1 avoids the comma -elementLength is to get the string just read, because the pointer has moved as its length is being counted.
						String director = movieData.substring(i+1 -elementLength , i+1); 
						return director;
					}
					elementLength++;
				}
			}
		}
		return null;
	}
	
	/*
	 * Helper method to read title from current movie line
	 * @param String that holds all info of a line(movie) from database
	 * @return String that holds title info
	 */
	private String getTitleHelper(String movieData) {
		for(int i = 0; i < movieData.length(); i++) {
			if(movieData.charAt(i) == ',') { //if first comma shows up
				return movieData.substring(0, i);
			}
		}	
		return null;
	}
	
	/*
	 * Helper method to read year from current movie line
	 * @param String that holds all info of a line(movie) from database
	 * @return Integer that holds movie year info
	 */
	private Integer getYearHelper(String movieData) {
		int startIndex = 0; //holds the index after a comma shows up 
		for(int i = 0; i < movieData.length(); i++) {
			if(movieData.charAt(i) == ',') {
				String element =  movieData.substring(startIndex, i);
				if(isInteger(element)) { 
					try {
						Integer year = new Integer(element);
						return year;
					} catch(Exception e ) {
						System.out.println(e.getMessage());
					}	
				}
				startIndex += element.length()+1; //updates index to element after current comma 
			}		
		}
		
		return null;
	}
	
	/*
	 * Helper method that makes sure a String is can be turned into a Integer
	 * @param substring from movie line that might be an integer
	 * @return true if substring can be turned into integer, false otherwise. 
	 */
	private boolean isInteger(String element) {
	    for (int i = 0; i < element.length(); i++) {
	        char c = element.charAt(i);
	        if (c < '0' || c > '9') { //if the character is not a digit return false
	            return false;
	        }
	    }
	    return true;
	}
	
	/*
	 * Helper method to read genres from current movie line
	 * @param String that holds all info of a line(movie) from database
	 * @return List of Strings that holds movie's genres
	 */
	private List<String> getGenresHelper(String moviesArray) {
		
		int commaCounter = 0; //counts how many valid commas have been read
		List<String> genres = new LinkedList<String>();
		boolean severalGenres = false;
		//This for loop deals with one genres only movies
		for (int i = 0; i < moviesArray.length(); i++) {
			if(moviesArray.charAt(i) == ',') { //increments comma counter everytime a comma shows up
				commaCounter++;
			}
			
			if(commaCounter == 3) { //genres is after the third comma
				if(moviesArray.charAt(i+1) == '"' && !severalGenres) { //checks if there is more than one genres(they are between double quotes)
					severalGenres = true;
				}
				if(severalGenres) {
					int stringSize = 0; //holds how many characters will be needed before comma shows up
					int newElementIndex = i+2; //i is at the third comma, so i+2 is the first char of the genre string
					for(int j = i+2; j < moviesArray.length(); j++) {
						if(moviesArray.charAt(j) == '"') { //if the list ended
							String arrElement = moviesArray.substring(newElementIndex, newElementIndex + stringSize); //creates a substring for that element inside double quotes
							genres.add(arrElement.trim());
							return genres;
						}
						
						if(moviesArray.charAt(j) == ',') {
							String arrElement = moviesArray.substring(newElementIndex, newElementIndex + stringSize); //creates a substring for that element inside double quotes						
							genres.add(arrElement.trim());
							newElementIndex += stringSize+1; //to avoid comma 
							stringSize = 0;
						} else {
							stringSize++; 
						}						
					}
					
				} else {
					int stringSize = 0; //holds how many characters will be needed before comma shows up
					int newElementIndex = i+1;
					for(int j = i+1; j < moviesArray.length(); j++) {
						if(moviesArray.charAt(j) == ',') { //if the list ended
							String arrElement = moviesArray.substring(newElementIndex, newElementIndex + stringSize); //creates a substring for that element inside double quotes
							genres.add(arrElement.trim());
							return genres;
						}
						stringSize++;
					}
				}
			}
		}
		return genres;	
	}
	
}
