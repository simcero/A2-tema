package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;
import interpreter.model.PrgState;

public record VarDeclStmt(String name, IType type) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new MyException("Variable " + name + " already exists in the symbol table.");
        }
        symTable.put(name, type.defaultValue());
        return state;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}