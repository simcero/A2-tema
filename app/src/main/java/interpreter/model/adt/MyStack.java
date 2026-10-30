package interpreter.model.adt;

import interpreter.exceptions.MyException;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    private final Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() throws MyException {
        if (stack.isEmpty()) {
            throw new MyException("Execution stack is empty!");
        }
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
