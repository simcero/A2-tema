package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyIDictionary;
import interpreter.model.value.IValue;

public class ValueExp implements IExp {
    private final IValue val;

    public ValueExp(IValue v) {
        this.val = v;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) throws MyException {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }
}