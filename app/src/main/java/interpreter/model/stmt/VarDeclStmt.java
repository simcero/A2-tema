package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;
import interpreter.model.adt.MyIDictionary;

public class VarDeclStmt implements IStmt {
    private final String name;
    private final IType type;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new MyException("Variable " + name + " already exists in the symbol table.");
        } else {
            symTable.put(name, type.defaultValue());
            // Note: If your IType interface doesn't have defaultValue() yet, we can add it or assign a standard default based on type!
        }
        return null;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}