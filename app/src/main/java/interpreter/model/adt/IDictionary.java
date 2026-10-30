package interpreter.model.adt;

import interpreter.exceptions.MyException;
import java.util.Map;

public interface IDictionary<K, V> {
    boolean isDefined(K key);
    void put(K key, V value);
    V lookup(K key) throws MyException;
    void update(K key, V value) throws MyException;
    Map<K, V> getContent();
}