package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt { // S10
    @Override
    public PrgState execute(PrgState state) throws MyException {
        // 1. Evaluate expression to a StringValue filename
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StatementException("closeRFile: " + exp + " is not a string");

        // 2. Check if file is present in FileTable
        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName))
            throw new StatementException("closeRFile: " + fileName + " is not open");

        // 3. Close the reader and remove from FileTable
        try {
            BufferedReader reader = fileTable.lookup(fileName);
            reader.close();
            fileTable.remove(fileName);
        } catch (IOException e) {
            throw new StatementException("closeRFile: cannot close " + fileName + ": " + e.getMessage());
        }

        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}