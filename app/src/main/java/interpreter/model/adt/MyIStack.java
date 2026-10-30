package interpreter.model.adt;

import interpreter.exceptions.MyException;

public interface MyIStack<T> {
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    String toString();
}
