// --== CS400 File Header Information ==--
// Name: Jack Gundrum
// Email: jpgundrum@wisc.edu
// Team: Blue
// Group: KD
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader:
import java.util.NoSuchElementException;

/**
 * Interface to be implemented in HashTableMap
 * 
 * @author CS400 instructors
 */
public interface MapADT<KeyType, ValueType> {

    public boolean put(KeyType key, ValueType value);
    public ValueType get(KeyType key) throws NoSuchElementException;
    public int size();
    public boolean containsKey(KeyType key);
    public ValueType remove(KeyType key);
    public void clear();
    
}