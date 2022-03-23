// --== CS400 File Header Information ==--
// Name: Zhi Zheng
// Email: zzheng94@wisc.edu
// Team: Blue
// Group: KD
// TA: KEREN CHEN
// Lecturer: Gary
// Notes to Grader: <optional extra notes>
import java.util.NoSuchElementException;
import java.util.LinkedList;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

	private LinkedList<HashNode<KeyType, ValueType>>[] hashTable;
	public int capacity;
	private int size = 0;

	public HashTableMap(int capacity) {
		this.capacity = capacity;
		hashTable = (LinkedList<HashNode<KeyType, ValueType>>[]) new LinkedList[capacity];
	}

	public HashTableMap() {
		this.capacity = 10;
		hashTable = (LinkedList<HashNode<KeyType, ValueType>>[]) new LinkedList[capacity];
	}

	// private helper method to help get the index in the hashtable corresponding to
	// the HashTableMap's capacity
	private int getKeyIndex(KeyType key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	@Override
	public boolean put(KeyType key, ValueType value) {
		if (key == null || this.containsKey(key)) {
			return false;
		}
		if (hashTable[getKeyIndex(key)] == null) {
			hashTable[getKeyIndex(key)] = new LinkedList<HashNode<KeyType, ValueType>>();
			hashTable[getKeyIndex(key)].add(new HashNode<KeyType, ValueType>(key, value));
			size++;
		} else {
			hashTable[getKeyIndex(key)].add(new HashNode<KeyType, ValueType>(key, value));
			size++;
		}
		if ((1.0 * this.size) / capacity >= 0.85) {
			rehashing();
		}
		return true;
	}

	//private help method to help double the increase the capacity and rehash all the elements
	//when the load capacity is >= 85%.
	private void rehashing() {
		this.capacity = 2 * capacity;
		LinkedList<HashNode<KeyType, ValueType>>[] temp = hashTable;
		this.clear();
		for (int i = 0; i < temp.length; ++i) {
			if (temp[i] != null) {
				for (int j = 0; j < temp[i].size(); ++j) {
					put(temp[i].get(j).key, temp[i].get(j).value);
				}
			}
		}
	}

	@Override
	public ValueType get(KeyType key) throws NoSuchElementException {
		if (hashTable[this.getKeyIndex(key)] != null) {
			for (int j = 0; j < hashTable[getKeyIndex(key)].size(); ++j) {
				if (key.equals(hashTable[getKeyIndex(key)].get(j).key)) {
					return hashTable[getKeyIndex(key)].get(j).value;
				}
			}
		}
		throw new NoSuchElementException("The key does not exist.");
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean containsKey(KeyType key) {
		if (hashTable[this.getKeyIndex(key)] != null) {
			for (int j = 0; j < hashTable[getKeyIndex(key)].size(); ++j) {
				if (key.equals(hashTable[getKeyIndex(key)].get(j).key)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ValueType remove(KeyType key) {
		if (hashTable[getKeyIndex(key)] != null) {
			for (int j = 0; j < hashTable[getKeyIndex(key)].size(); ++j) {
				if (key.equals(hashTable[getKeyIndex(key)].get(j).key)) {
					ValueType tempValue = hashTable[getKeyIndex(key)].get(j).value;
					hashTable[getKeyIndex(key)].remove(hashTable[getKeyIndex(key)].get(j));
					size--;
					return tempValue;
				}
			}
		}
		return null;
	}

	@Override
	public void clear() {
		hashTable = (LinkedList<HashNode<KeyType, ValueType>>[]) new LinkedList[capacity];
		this.size = 0;
	}

}

// internal class to warp the Keytype and Valuetype as a instance.
class HashNode<KeyType, ValueType> {
	protected KeyType key;
	protected ValueType value;
	
	protected HashNode(KeyType key, ValueType value) {
		this.key = key;
		this.value = value;
	}
}
