// --== CS400 File Header Information ==--
// Name: Jack Gundrum
// Email: jpgundrum@wisc.edu
// Team: Blue
// Group: KD
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader:
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.io.Reader;  // see if I can do this
import java.io.File;
import java.io.FileReader;

/**
 * This class implements the backend interface. It is used to store movies in a hash table,
 * and also gives the front end ability to add genres, add ratings, remove genres, etc. 
 * It interacts with the data wrangler to obtain the database of movies.
 * 
 * @author jackgundrum
 */
public class Backend implements BackendInterface{
    private HashTableMap<Integer, String> hash1;         // hashtable to store genres
    private HashTableMap<Integer, String> hash2;         // hashtable to store raitngs
    private HashTableMap<Float, MovieInterface> hash3;         // hashtable to store movies
    private ArrayList<Integer> keyHolder;    // holds keys for genres
    private ArrayList<Integer> keyHolder2;   // holds keys for ratings
    private LinkedList<MovieInterface> movies;  // movie in data base as linkedlist
    
    /**
     * Constructor that take command line arugments and creates a hash table of movies
     * out of it.
     * 
     * @param args The command line argument to be added to the hash table
     */
    public Backend(String[] args){
        try {
            MovieDataReader myMovies = new MovieDataReader(); // instantiate interface
            for (int i = 0; i < args.length; i++) {
                File data = new File(args[i]);
                Reader newData = new FileReader(data);
                movies = (LinkedList<MovieInterface>) myMovies.readDataSet(newData);
            }
            float key = 0;
            float vote = 0;
            MovieInterface moviesList;
            hash3 = new HashTableMap<Float, MovieInterface>(288); // 1.25 * data movie size
            for (int i = 0; i < movies.size(); i++) { // adds movies to hash3
                key = 0;
                moviesList = movies.get(i);
                vote = moviesList.getAvgVote();
                key = (float) (((vote) * 10000.3) + i); // makes keys more unique
                hash3.put(key, moviesList); // keys are the same makes it not work.

            }
            hash1 = new HashTableMap<Integer, String>();
            hash2 = new HashTableMap<Integer, String>();
            hash3 = new HashTableMap<Float, MovieInterface>(288);
            keyHolder = new ArrayList<Integer>();
            keyHolder2 = new ArrayList<Integer>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Constructor that obtains the movie data from the data wrangler and adds to movies
     * into a hash table through a reader. The key is based on the average rating on the movie.
     * 
     * @param inputFilerReader The data base that wil be stored in hash3 by average vote
     */
    public Backend(Reader inputFileReader)    {  // uses string args as parameter to use command line
        try {
        MovieDataReader myMovies = new MovieDataReader(); // instantiate interface
        MovieInterface moviesList;
        movies = (LinkedList<MovieInterface>) myMovies.readDataSet(inputFileReader);
        float key = 0;
        float vote = 0;
        hash3 = new HashTableMap<Float, MovieInterface>(288); // 1.25 * data movie size
        for(int i = 0; i < movies.size(); i++) {    // adds movies to hash3
            key = 0;
            moviesList = movies.get(i);
            vote = moviesList.getAvgVote();
            key = (float) (((vote) * 10000.3) + i);     // makes keys more unique
            hash3.put(key,moviesList);  // keys are the same makes it not work. 
       
        }
        hash1 = new HashTableMap<Integer, String>();
        hash2 = new HashTableMap<Integer, String>();
        keyHolder = new ArrayList<Integer>();
        keyHolder2 = new ArrayList<Integer>();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a genre to a hash table where the genres are stored
     * 
     * @param genre The genre to be added
     */
    @Override
    public void addGenre(String genre) {
        if(genre == null)       {  // avoids null pointer exception
            return;
        }
        String toLower = genre.toLowerCase();   // to create the key
        int key = 0;
        for(int i = 0; i < genre.length(); i++) { // this gets me the key of the new genre
            key += (toLower.charAt(i)) - 96;      // allows for a=1, b=2, etc
        }
        hash1.put(key,genre);                 // adds the genre to the hash table
        keyHolder.add(key);                   // adds key to a shared array list to be used later
    }

    /**
     * Adds a given rating to a hash table that stores the ratings that have been added
     * 
     * @param rating A string representation of the raing to be added
     */
    @Override
    public void addAvgRating(String rating) {
        if(rating == null)      {   // avoids null pointer exception
            return;
        }
        int key2 = 0;
        if(rating.charAt(0) < 48 || rating.charAt(0) > 57) { // checks first num is 0-10
            throw new NoSuchElementException("Rating must be between 0-10.");
        }
        if(rating.equals("10")) {
        	key2 = 58;
        }
        else {
        	key2 = rating.charAt(0);// obtains key for rating
        }
        hash2.put(key2,rating);   // adds rating to the hash table
        keyHolder2.add(key2);     // bucket for rating
        
    }
    /**
     * This method removes a genre from the hash table
     * 
     * @param genre The genre to be removed
     */
    @Override
    public void removeGenre(String genre) {
        if(genre == null)   {
            return;
        }
        String toLower = genre.toLowerCase();
        int key = 0;
        for(int i = 0; i < genre.length(); i++) { // this gets me the key of the genre to remove
            key += (toLower.charAt(i)) - 96;      // allows for a=1, b=2, etc
        }
        hash1.remove(key);
        Integer toRemove = key;
        keyHolder.remove(toRemove);
    }

    /**
     * This method removes an average rating from the hash table
     * 
     * @param rating The average rating to be removed
     */
    @Override
    public void removeAvgRating(String rating) {
        if(rating == null)  {
            return;
        }
        int key2 = 0;
        if(rating.charAt(0) < 48 || rating.charAt(0) > 57) { // checks first num is 0-10, bc it must be
            throw new NoSuchElementException("Rating must be between 0-10.");
        }
        if(rating.equals("10")) {
        	key2 = 58;
        }
        else{
        	key2 = rating.charAt(0);
        	}
        hash2.remove(key2);
        Integer toRemove = key2;
        keyHolder2.remove(toRemove);
        
    }

    /**
     * This method obtains a list of all genres currently set in hash1
     * 
     * @return genres The list of genres from the hash table
     */
    @Override
    public List<String> getGenres() {
        List<String> genres = new ArrayList<String>(); // TODO not sure if i can do this
        int numOfGenres = hash1.size();               // tells me how many genres in hash table
        int key = 0;
        if(numOfGenres < 0)       {
            throw new NoSuchElementException("Can't have negative genres.");
        }
        // iterates thru # of genres added and obtains the keys by the shared arraylist
        // then it searches the hash table to find if that key points to a genre
        for(int i = 0; i < numOfGenres; i++)        {   // loops thru genres
            key = keyHolder.get(i);
            if(hash1.containsKey(key) == true) {        // checks if key is in hash table
                genres.add((String)hash1.get(key));     // add if it is
            }
        }
        return genres;

    }

    /**
     * This method obtains a list of the average ratings set in hash2
     * 
     * @return ratings The list of average ratings from the hash table
     */
    @Override
    public List<String> getAvgRatings() {
        List<String> ratings = new ArrayList<String>();
        int numOfRatings = hash2.size();
        int key2 = 0;
        if(numOfRatings < 0) {
            throw new NoSuchElementException("Can't have negative ratings.");
        }
        for(int i = 0; i < numOfRatings; i++)   {
            key2 = keyHolder2.get(i);
            if(hash2.containsKey(key2) == true) {
                ratings.add((String)hash2.get(key2));
            }
        }
        return ratings;
    }

    
    /**
     * This method checks for movies that have all of the set genres and it is in
     * the range of the average set ratings
     * 
     * @return numMovies The number of movies whose genres and ratings match those set
     */
    @Override
    public int getNumberOfMovies() { 
        if (hash1.size() == 0 || hash2.size() == 0) { // must have at least 1 of both set
            return 0;
        }
        MovieInterface currMovie;                     // what curr movie you are on
        List<String> genres = this.getGenres();
        List<String> ratings = this.getAvgRatings();
        List<String> currGenres;  
        int numMovies = 0;                        // rounded version of vote for

        for (int i = 0; i < movies.size(); i++) {
            currMovie = movies.get(i);
            currGenres = currMovie.getGenres();
            if(helper1(currMovie,currGenres,ratings,genres) == true) {
                numMovies++;
            }
        }
        return numMovies;
    }
    
    /**
     * Helper method that returns whether a certain movie can be added based on the resulting
     * set of ratings and genres.
     * 
     * @param movie The current movie to be checking
     * @param currGenres The current set list of gernres
     * @param ratings The current set list of ratings
     * @return true When the movie has the given genres and ratings, fase otherwise
     */
    private boolean helper1 (MovieInterface movie, List<String> currGenres, List<String> ratings, List<String> setGenres) {
        float currRating = 0;
        double round = 0;  
        double parsedVal = 0;
        boolean canAdd = false;
        // helps me see if the movie has the correct genre and rating in the sorted list
        for (int k = 0; k < setGenres.size(); k++) { // loops thru current set genre list
            if (currGenres.contains(setGenres.get(k))) { // checks if genre is in list of current
                                                      // movie
                if (k == setGenres.size() - 1) { // helps me see if every genre has been checked
                    currRating = movie.getAvgVote(); // now want to see if it is in range of
                                                         // avg ratings
                    round = Math.floor(currRating); // rounds to next smallest int
                    for (int j = 0; j < ratings.size(); j++) {
                        parsedVal = Double.parseDouble(ratings.get(j));
                        if (round == parsedVal) {
                            canAdd = true; // ticker increases if it is
                        }
                    }
                }
            } else {
                break;
            }
        }
        return canAdd;
    }

    /**
     * Helper method to order the rankings in a descending order
     * 
     * @param input The array to be changed to descending order
     */
    private void reverse(float[] input) {
        int last = input.length - 1;
        int middle = input.length / 2;
        for (int i = 0; i <= middle; i++) { 
            float temp = input[i];
            input[i] = input[last - i];
            input[last - i] = temp; 
            } 
        } 
    
    /**
     * Obtains three movies from a given starting index where the movies are sorted such 
     * that their average ratings are in descending order. If there are less than 3 movies
     * from the starting index, the list may be empty in some parts
     * 
     * @param startingIndex Place where to start list
     * @return threeMovies List of 3 movies that start from given index
     */
    @Override
    public List<MovieInterface> getThreeMovies(int startingIndex) {
        if (startingIndex < 0) {
            return null; // indicates something went wrong
        }
        
        MovieInterface currMovie;
        List<String> currGenres;
        List<String> setGenres = this.getGenres();
        List<String> ratings = this.getAvgRatings();
        float sortedData[] = new float[250];
        float keyHolder[] = new float[250];
        List<MovieInterface> movieList = new LinkedList<MovieInterface>();
        float currRating = 0;
        if(hash1.size() == 0 || hash2.size() == 0) {    // returns empty list
            return movieList;
        }
        for(int i = 0; i < movies.size(); i++) {
            currMovie = movies.get(i);          // movie to see if it is set
            currGenres = currMovie.getGenres();
            if(helper1(currMovie,currGenres,ratings,setGenres) == true) { // movie is in set
                // if it is set then I want to order it by ratings
                currRating = currMovie.getAvgVote();
                sortedData[i] = currRating;
                keyHolder[i] = (float) (((currRating) * 10000.3) + i);
            }
        }
        
        Arrays.sort(sortedData);
        Arrays.sort(keyHolder);
        reverse(sortedData);    // sorts ratings in descending order
        reverse(keyHolder);     // sorts keys in descending order. 
        float key = 0;
        float validRate = 0;
        MovieInterface previous = null;
        
        // sorts data based on parallel arrays of sorted ratings and sorted hash keys
        try {
        for(int k = startingIndex; k < startingIndex + 3; k++) {// starting based vote lowering
            validRate = sortedData[k];
            for(int j = startingIndex; j < keyHolder.length; j++) {
                key = keyHolder[j];
                currMovie = hash3.get(key);
                currGenres = currMovie.getGenres();
                if(currMovie.getAvgVote().equals(validRate) && 
                    helper1(currMovie,currGenres,ratings,setGenres) == true && 
                    !currMovie.equals(previous)) {  // makes sure data is in set
                    if(startingIndex == 0){
                        movieList.add(currMovie);
                        previous = currMovie;
                        break;
                    }
                    else if(movieList.contains(currMovie) || j < startingIndex) {
                        continue;
                    }
                    movieList.add(currMovie);
                    previous = currMovie;
                    break;
                } 
                }
            
            }
        }
        catch(Exception e) {    // avoids when a value can't be found in hash table
            return movieList;
        }
        return movieList;
    }

    /**
     * Iterates through the entire data set, and obtains all genres.
     * 
     * @return genresList The list of genres from the data set
     */
    @Override
    public List<String> getAllGenres() {
        List<String> genreList = new ArrayList<String>();
        List<String> toReturn = new ArrayList<String>();
        MovieInterface currMovie;
        for(int i = 0; i < movies.size(); i++) {
            currMovie = movies.get(i);
            genreList = currMovie.getGenres();
            for(int k = 0; k < genreList.size(); k++) {
                if(!toReturn.contains(genreList.get(k))) {  // if it does not contain, add
                    if(genreList.get(k).equals("2019") || genreList.get(k).equals("Rescue!\"")) {
                        continue;
                    }
                    toReturn.add(genreList.get(k));
                }
                
            }
        }
        return toReturn;
    }
}
