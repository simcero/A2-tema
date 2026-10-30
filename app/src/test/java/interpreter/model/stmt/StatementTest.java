package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatementTest {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;

    @BeforeEach
    void setUp() {
        exeStack = new MyStack<>();
        symTable = new MyDictionary<>();
        out = new MyList<>();
    }

    // Helper to execute a statement on a fresh state
    private PrgState runStmt(IStmt stmt) throws MyException {
        PrgState state = new PrgState(exeStack, symTable, out, stmt);
        // Execute statements until the execution stack is empty
        while (!state.getExeStack().isEmpty()) {
            IStmt currentStmt = state.getExeStack().pop();
            currentStmt.execute(state);
        }
        return state;
    }

    @Test
    void varDeclAndAssignmentSuccess() throws MyException {
        // S1 & S2: int v; v = 10;
        IStmt stmt = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new AssignStmt("v", new ValueExp(new IntValue(10)))
        );
        PrgState state = runStmt(stmt);
        assertEquals(new IntValue(10), state.getSymTable().lookup("v"));
    }

    @Test
    void redeclaredVariableThrows() {
        // S1 Error: Variable declared twice
        IStmt stmt = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new VarDeclStmt("v", new IntType())
        );
        assertThrows(StatementException.class, () -> runStmt(stmt));
    }

    @Test
    void assignUndeclaredVariableThrows() {
        // S2 Error: Assignment to an undeclared variable
        IStmt stmt = new AssignStmt("v", new ValueExp(new IntValue(10)));
        assertThrows(StatementException.class, () -> runStmt(stmt));
    }

    @Test
    void typeMismatchAssignmentThrows() {
        // S2 Error: Type mismatch (int variable assigned a boolean value)
        IStmt stmt = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new AssignStmt("v", new ValueExp(new BoolValue(true)))
        );
        assertThrows(MyException.class, () -> runStmt(stmt));
    }

    @Test
    void ifStatementSuccess() throws MyException {
        // S4: if (true) v = 2 else v = 3; print(v)
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
        // S4 Error: Non-boolean condition in If statement (evaluates to Int instead of Bool)
        IStmt stmt = new IfStmt(
                new ValueExp(new IntValue(1)),
                new AssignStmt("v", new ValueExp(new IntValue(2))),
                new AssignStmt("v", new ValueExp(new IntValue(3)))
        );
        assertThrows(StatementException.class, () -> runStmt(stmt));
    }

    @Test
    void nopAndPrintExecution() throws MyException {
        // S5 & S6: NopStmt and PrintStmt
        IStmt stmt = new CompStmt(
                new NopStmt(),
                new PrintStmt(new ValueExp(new IntValue(42)))
        );
        PrgState state = runStmt(stmt);
        assertEquals(new IntValue(42), state.getOut().get(0));
    }
}