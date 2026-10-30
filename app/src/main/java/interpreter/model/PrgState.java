package interpreter.model;

import interpreter.model.adt.MyIDictionary;
import interpreter.model.adt.MyIList;
import interpreter.model.adt.MyIStack;
import interpreter.model.value.IValue;
import interpreter.model.stmt.IStmt;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private IStmt originalProgram; // Optional fields depending on exact lab requirements, but very helpful

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, IValue> symTable, MyIList<IValue> out, IStmt program) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program; // You can deep copy if needed later
        stack.push(program);
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public void setOut(MyIList<IValue> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return "PrgState:\n" +
                "ExeStack: " + exeStack + "\n" +
                "SymTable: " + symTable + "\n" +
                "Out: " + out + "\n---";
    }
}