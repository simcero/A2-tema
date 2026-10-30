package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionExceptionTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void divisionByZeroThrows() {
        IExp e = new ArithExp(ArithOp.DIV, num(10), num(0));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertNotNull(ex.getMessage());
    }

    @Test
    void nonIntegerOperandThrows() {
        // e.g., using a boolean value or wrong type where int is expected
        // Assuming you have a BoolValue/BoolType setup
        IExp e = new ArithExp(ArithOp.ADD, num(5), new ValueExp(new interpreter.model.value.BoolValue(true)));
        assertThrows(MyException.class, () -> e.eval(symTable));
    }
}