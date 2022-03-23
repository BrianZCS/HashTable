// --== CS400 File Header Information ==--
// Name: Jack Gundrum
// Email: jpgundrum@wisc.edu
// Team: Blue
// Group: KD
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: 

/**
 * This class was created to test the functionality of the HashTableMap class to see
 * if the hash table is working as expected.
 * 
 * @author jackgundrum
 */
public class TestHashTable {

    /**
    * Main driver that allows me to calls my test methods to see if they work properly.
    * 
    * @param args Input arguments if any
    */
    public static void main(String[] args) {
        System.out.println("Test put method for HashTable: " + test1());
        System.out.println("Test for rehashing: " + test2());
        System.out.println("Test remove method for HashTable: " + test3());
        System.out.println("Test get method for HashTable: " + test4());
        System.out.println("Test other methods: " + test5());
    }

    /**
     * Test 1 that checks if adding values to the hash table works as expected.
     * 
     * @return true if all tests pass, and false otherwise.
     */
    public static boolean test1() {
        HashTableMap<Integer, String> hash1 = new HashTableMap<Integer, String>();
        int key = 104; // for hash rep of "western" for class project
        String value = "Western";
        if (hash1.put(null, null) != false) {
            return false;
        }
        if (hash1.put(key, value) != true) { // test fails if it can't add western
            return false;
        }
        if (hash1.put(key, value) != false) { // checks for duplicates
            return false;
        }
        int key2 = 62; // test for "action" (something I need for project 1)
        String value2 = "Action";
        if (hash1.put(key2, value2) != true) {
            return false;
        }
        int key3 = 94; // check the linked list chaining behavior
        String value3 = "LinkedListCheck";
        if (hash1.put(key3, value3) != true) {
            return false;
        }
        return true;
    }
    
    /**
     * Test 2 that checks if rehashing a hash table performs the expected behavior.
     * 
     * @return true if all tests pass, and false otherwise.
     */
    public static boolean test2() {
        HashTableMap<Integer, String> hash1 = new HashTableMap<Integer, String>(5);
        int key = 100;
        String value = "Pizza"; // arbitrary names & keys, just trying to test rehash behavior
        int key2 = 101;
        String value2 = "Chicken";
        int key3 = 103;
        String value3 = "Burger";
        int key4 = 104;
        String value4 = "Eggs";
        int key5 = 111;
        String value5 = "Yogurt";

        hash1.put(key, value); // adds vals
        hash1.put(key2, value2);
        hash1.put(key3, value3);
        hash1.put(key4,value4);
        hash1.put(key5,value5);
        if (hash1.size() != 5) { // size should be 3, even after allocating only 2 spots to array
            return false;
        }
        return true;
    }
    
    /**
     * Test 3 that checks if removing values to the hash table works as expected.
     * 
     * @return true if all tests pass, and false otherwise.
     */
    public static boolean test3() {
        HashTableMap<Integer, String> hash1 = new HashTableMap<Integer, String>();
        if (hash1.remove(null) != null) {
            return false;
        }
        int key = 100;
        String value = "Pizza"; // arbitrary names & keys, just trying to test rehash behavior
        int key2 = 101;
        String value2 = "Chicken";
        int key3 = 103;
        String value3 = "Burger";
        int key4 = 104;
        String value4 = "Eggs";
        hash1.put(key, value); // adds vals
        hash1.put(key2, value2);
        hash1.put(key3, value3);
        hash1.put(key4, value4);
        if (hash1.remove(50) != null) { // tests when a key isn't in the list
            return false;
        }
        if (!hash1.remove(key).equals("Pizza")) { // tests to see if pizza was removed
            return false;
        }
        int key5 = 111;
        String value5 = "Yogurt";
        hash1.put(key5, value5);
        if (!hash1.remove(key5).equals("Yogurt")) { // tests if remove works in linked list chain
            return false;
        }
        hash1.put(key5, value5); // add same val back and check if I can remove chicken
        if (!hash1.remove(key2).equals("Chicken")) {
            return false;
        }
        return true;
    }
    
    /**
     * Test 4 that checks the get method on the hash table. I expect the method to return
     * appropriate value given a key.
     * 
     * @return true if all tests pass, and false otherwise.
     */
    public static boolean test4() {
        try {
            HashTableMap<Integer, String> hash1 = new HashTableMap<Integer, String>();
            int key = 100;
            String value = "Pizza"; // arbitrary names & keys, just trying to test rehash behavior
            int key2 = 101;
            String value2 = "Chicken";
            int key3 = 103;
            String value3 = "Burger";
            int key4 = 104;
            String value4 = "Eggs";
            int key5 = 111;
            String value5 = "Yogurt";
            int key6 = 121;
            String value6 = "Milk";
            hash1.put(key, value); // adds vals
            hash1.put(key2, value2);
            hash1.put(key3, value3);
            hash1.put(key4, value4);
            hash1.put(key5, value5);
            hash1.put(key6, value6);
            if (!hash1.get(key2).equals("Chicken")) {
                return false;
            }
            if (!hash1.get(key5).equals("Yogurt")) {
                return false;
            }
            if (!hash1.get(key6).equals("Milk")) {
                return false;
            }
            hash1.get(50); // should be caught below
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    
    /**
     * Test 5 is a miscellaneous that tests multiple methods. It tests the behavior of:
     * size() method, containsKey() method, and clear() method.
     * 
     * @return true if all tests pass, and false otherwise.
     */
    public static boolean test5() {
        try {
            HashTableMap<Integer, String> hash1 = new HashTableMap<Integer, String>();
            int key = 100;
            String value = "Pizza"; // arbitrary names & keys, just trying to test rehash behavior
            int key2 = 101;
            String value2 = "Chicken";
            int key3 = 103;
            String value3 = "Burger";
            int key4 = 104;
            String value4 = "Eggs";
            int key5 = 111;
            String value5 = "Yogurt";
            int key6 = 121;
            String value6 = "Milk";
            hash1.put(key, value); // adds vals
            if (hash1.size() != 1) { // tests the size
                return false;
            }
            hash1.put(key2, value2);
            if (hash1.size() != 2) { // test size
                return false;
            }
            hash1.put(key3, value3);
            hash1.put(key4, value4);
            hash1.put(key5, value5);
            hash1.put(key6, value6);
            if (hash1.size() != 6) { // test size
                return false;
            }
            if (hash1.containsKey(key) == false) { // tests containsKey
                return false;
            }
            if (hash1.containsKey(key2) == false) {
                return false;
            }
            if (hash1.containsKey(key3) == false) {
                return false;
            }
            if (hash1.containsKey(key4) == false) {
                return false;
            }
            if (hash1.containsKey(key5) == false) {
                return false;
            }
            if (hash1.containsKey(key6) == false) {
                return false;
            }
            if (hash1.containsKey(50) != false) {
                return false;
            }
            hash1.clear();
            if (hash1.get(key) != null) { // exception should be thrown, showing clear() works
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
