package interpreter.model.adt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ADTTest {

    private IStack<String> stack;
    private IDictionary<String, Integer> dictionary;
    private IList<String> list;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
        dictionary = new MyDictionary<>();
        list = new MyList<>();
    }

    @Test
    void stackLifoOrderAndPopEmptyThrows() throws MyException {
        stack.push("first");
        stack.push("second");
        assertEquals("second", stack.pop());
        assertEquals("first", stack.pop());
        assertTrue(stack.isEmpty());

        assertThrows(ADTException.class, () -> stack.pop());
    }

    @Test
    void dictionaryOperationsAndMissingKeyThrows() throws MyException {
        dictionary.put("a", 10);
        assertEquals(10, dictionary.lookup("a"));

        dictionary.update("a", 20);
        assertEquals(20, dictionary.lookup("a"));

        assertThrows(ADTException.class, () -> dictionary.lookup("missing"));
    }

    @Test
    void listAddGetSize() throws MyException{
        list.add("item1");
        assertEquals(1, list.size());
        assertEquals("item1", list.get(0));
    }
}