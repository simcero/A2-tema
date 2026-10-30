package interpreter.model.adt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.MyException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> dictionary = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public V lookup(K key) throws MyException {
        V value = dictionary.get(key);
        if (value == null) {
            throw new MyException("Key " + key + " is not defined in the dictionary.");
        }
        return value;
    }

    @Override
    public void update(K key, V value) throws MyException {
        if (!dictionary.containsKey(key)) {
            throw new MyException("Key " + key + " is not defined, cannot update.");
        }
        dictionary.put(key, value);
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }
}