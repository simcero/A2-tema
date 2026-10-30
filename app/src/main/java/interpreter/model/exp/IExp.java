package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyIDictionary;
import interpreter.model.value.IValue;

public interface IExp {
    IValue eval(MyIDictionary<String, IValue> tbl) throws MyException;
}