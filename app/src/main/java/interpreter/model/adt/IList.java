package interpreter.model.adt;

import interpreter.exceptions.MyException;
import java.util.List;

public interface IList<T> {
    void add(T elem);
    T get(int index) throws MyException;
    int size();
    List<T> getContent();
}