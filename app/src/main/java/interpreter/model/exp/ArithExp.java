package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyIDictionary;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public class ArithExp implements IExp {
    private final IExp e1;
    private final IExp e2;
    private final char op; // '+' , '-' , '*' , '/'

    public ArithExp(char op, IExp e1, IExp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) throws MyException {
        IValue v1 = e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            IValue v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1 = i1.getVal();
                int n2 = i2.getVal();

                switch (op) {
                    case '+': return new IntValue(n1 + n2);
                    case '-': return new IntValue(n1 - n2);
                    case '*': return new IntValue(n1 * n2);
                    case '/':
                        if (n2 == 0) throw new MyException("Division by zero!");
                        return new IntValue(n1 / n2);
                    default:
                        throw new MyException("Invalid arithmetic operation!");
                }
            } else {
                throw new MyException("Second operand is not an integer.");
            }
        } else {
            throw new MyException("First operand is not an integer.");
        }
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}