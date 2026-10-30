package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyIDictionary;
import interpreter.model.value.IValue;

public class VarExp implements IExp {
    private final String key;

    public VarExp(String k) {
        this.key = k;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) throws MyException {
        return tbl.lookUp(key);
    }

    @Override
    public String toString() {
        return key;
    }
}