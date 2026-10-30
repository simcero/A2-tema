package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private final IRepository repo;
    private boolean displayFlag = false; // Flag to control whether to print the state

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();
        if (stack.isEmpty())
            throw new MyException("program state stack is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();

        // Optional log file writing or console display
        if (displayFlag) {
            System.out.println(prg);
        }

        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);

            if (displayFlag) {
                System.out.println(prg);
            }

            // If you implemented log file writing in your Repository, you can call it here:
            // repo.logPrgStateExec(prg);
        }
    }
}