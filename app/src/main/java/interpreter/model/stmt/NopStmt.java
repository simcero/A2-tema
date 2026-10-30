package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;

public record NopStmt() implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementException {
        return state;
    }

    @Override
    public String toString() {
        return "nop";
    }
}