// --== CS400 File Header Information ==--
// Name: Alan Jordao Cortez
// Email: ajcortez@wisc.edu 
// Team: blue
// Group: KD
// TA: Keren
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.util.List;

/**
 * This class represents movie objects found in the database
 * In Addition, the class implements the MovieInterface and the MovieDataReaderInterface
 * @author Alan Cortez.
 */
public class Movie implements MovieInterface {
	
	//instance variables
	private String title;
	private Integer year;
	private String director;
	private String description;
	private Float avgVote;
	private List<String> genres;
	
	/*
	 * No-argument constructor
	 */
	public Movie() {
		this.title = "unknown";
		this.year = 0000;
		this.director = "unknown";
		this.description = "unknown";
		this.avgVote = (float) 0.0;
		this.genres = null;
	}
	/*
	 * Six argument constructor for movie objects
	 * @param String with movie title
	 * @param Integer with movie year
	 * @param String with movie director
	 * @param String with movie description
	 * @param Float with movie average vote
	 * @param List(LinkedList) of Strings with movie genres
	 */
	public Movie(String movTitle, Integer movYear, String movDirec, String movDesc, Float movAvgVote, List<String> movGenres) {
		 
		this.title = movTitle;
		this.year = movYear;
		this.director = movDirec;
		this.description = movDesc;
		this.avgVote = movAvgVote;
		this.genres = movGenres;
	}
	
	/*
	 * Getter method 
	 * @return true if substring can be turned into integer, false otherwise. 
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public Integer getYear() {
		return this.year;
	}

	@Override
	public List<String> getGenres() {
		return this.genres;
	}

	@Override
	public String getDirector() {
		return this.director;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Float getAvgVote() {
		return this.avgVote;
	}

	//order movie objects by their average rating (in descending order)
	@Override
	public int compareTo(MovieInterface otherMovie) {
		if(this.avgVote > otherMovie.getAvgVote()) {
			return -1;
		} else if(this.avgVote < otherMovie.getAvgVote()) {
			return 1;
		} else {
			return 0;
		}	
	}

}