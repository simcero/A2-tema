package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader; // Needed if FileTable holds BufferedReader

import static org.junit.jupiter.api.Assertions.*;

class StatementTest {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IList<IValue> out;
    private IHeap heap;

    @BeforeEach
    void setUp() {
        exeStack = new MyStack<>();
        symTable = new MyDictionary<>();
        fileTable = new MyDictionary<>();
        out = new MyList<>();
        heap = new MyHeap();
    }

    private PrgState runStmt(IStmt stmt) throws MyException {
        PrgState state = new PrgState(exeStack, symTable, out, fileTable,heap,stmt);
        while (!state.getExeStack().isEmpty()) {
            IStmt currentStmt = state.getExeStack().pop();
            currentStmt.execute(state);
        }
        return state;
    }

    @Test
    void varDeclAndAssignmentSuccess() throws MyException {
        IStmt stmt = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new AssignStmt("v", new ValueExp(new IntValue(10)))
        );
        PrgState state = runStmt(stmt);
        assertEquals(new IntValue(10), state.getSymTable().lookup("v"));
    }

    @Test
    void redeclaredVariableThrows() {
        IStmt stmt = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new VarDeclStmt("v", new IntType())
        );
        assertThrows(StatementException.class, () -> runStmt(stmt));
    }

    @Test
    void assignUndeclaredVariableThrows() {
        IStmt stmt = new AssignStmt("v", new ValueExp(new IntValue(10)));
        assertThrows(StatementException.class, () -> runStmt(stmt));
    }

    @Test
    void typeMismatchAssignmentThrows() {
        IStmt stmt = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new AssignStmt("v", new ValueExp(new BoolValue(true)))
        );
        assertThrows(MyException.class, () -> runStmt(stmt));
    }

    @Test
    void ifStatementSuccess() throws MyException {
        IStmt stmt = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new IfStmt(
                                new ValueExp(new BoolValue(true)),
                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                        ),
                        new PrintStmt(new VarExp("v"))
                )
        );
        PrgState state = runStmt(stmt);
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void nonBooleanIfConditionThrows() {
        IStmt stmt = new IfStmt(
                new ValueExp(new IntValue(1)),
                new AssignStmt("v", new ValueExp(new IntValue(2))),
                new AssignStmt("v", new ValueExp(new IntValue(3)))
        );
        assertThrows(StatementException.class, () -> runStmt(stmt));
    }

    @Test
    void nopAndPrintExecution() throws MyException {
        IStmt stmt = new CompStmt(
                new NopStmt(),
                new PrintStmt(new ValueExp(new IntValue(42)))
        );
        PrgState state = runStmt(stmt);
        assertEquals(new IntValue(42), state.getOut().get(0));
    }
}