package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;
import interpreter.model.adt.MyIDictionary;

public class AssignStmt implements IStmt {
    private final String id;
    private final IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();

        if (symTable.isDefined(id)) {
            IValue val = exp.eval(symTable);
            IType typId = (symTable.lookUp(id)).getType();
            if (val.getType().equals(typId)) {
                symTable.update(id, val);
            } else {
                throw new MyException("Declared type of variable " + id + " and type of the assigned expression do not match.");
            }
        } else {
            throw new MyException("The used variable " + id + " was not declared before.");
        }
        return null;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }
}