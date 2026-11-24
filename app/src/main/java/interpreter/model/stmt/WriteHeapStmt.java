package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record WriteHeapStmt(String varName, IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new StatementException("wH: " + varName + " is not declared");
        if (!(symTable.lookup(varName) instanceof RefValue(int address, var locationType)))
            throw new StatementException("wH: " + varName + " is not a reference");
        if (!heap.isDefined(address))
            throw new StatementException("wH: address " + address + " is not allocated in heap");

        IValue value = exp.eval(symTable, heap);
        if (!value.getType().equals(locationType))
            throw new StatementException("wH: " + exp + " has type " + value.getType()
                    + ", expected " + locationType);

        heap.update(address, value);
        return state;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp + ")";
    }
}