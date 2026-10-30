package interpreter.model.adt;

import interpreter.exceptions.MyException;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final Map<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public V lookUp(K key) throws MyException {
        if (!dictionary.containsKey(key)) {
            throw new MyException("Key '" + key + "' is not defined in the symbol table!");
        }
        return dictionary.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public void update(K key, V value) throws MyException {
        if (!dictionary.containsKey(key)) {
            throw new MyException("Cannot update key '" + key + "': Variable is not defined!");
        }
        dictionary.put(key, value);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }
}