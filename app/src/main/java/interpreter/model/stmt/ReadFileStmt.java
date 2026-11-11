package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt { // S9
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();

        // 1. Check if varName is declared in symTable and has type int
        if (!symTable.isDefined(varName))
            throw new StatementException("readFile: variable " + varName + " is not declared");
        if (!symTable.lookup(varName).getType().equals(new IntType()))
            throw new StatementException("readFile: variable " + varName + " is not of type int");

        // 2. Evaluate expression to a StringValue filename
        if (!(exp.eval(symTable) instanceof StringValue fileName))
            throw new StatementException("readFile: " + exp + " is not a string");

        // 3. Check if file is present in FileTable
        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName))
            throw new StatementException("readFile: " + fileName + " is not open");

        // 4. Read line, parse, and update symTable
        try {
            BufferedReader reader = fileTable.lookup(fileName);
            String line = reader.readLine();
            int value = (line == null) ? 0 : Integer.parseInt(line.strip());
            symTable.update(varName, new IntValue(value));
        } catch (IOException | NumberFormatException e) {
            throw new StatementException("readFile: error reading from " + fileName + ": " + e.getMessage());
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}