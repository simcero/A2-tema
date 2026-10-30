package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.PrgState;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue condVal = cond.eval(state.getSymTable());
        if (!(condVal instanceof BoolValue(boolean val))) {
            throw new MyException("Conditional expression is not a boolean.");
        }

        if (val) {
            state.getExeStack().push(thenS);
        } else {
            state.getExeStack().push(elseS);
        }
        return state;
    }

    @Override
    public String toString() {
        return "(IF(" + cond + ") THEN(" + thenS + ") ELSE(" + elseS + "))";
    }
}