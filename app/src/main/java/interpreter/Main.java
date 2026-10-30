package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.RunExample;
import interpreter.view.TextMenu;

public class Main {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExample("0", "Exit", null));

        // Example 1: int v; v=2; print(v)
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex1);
        IRepository repo1 = new Repository(prg1);
        Controller ctrl1 = new Controller(repo1);
        menu.addCommand(new RunExample("1", "int v; v=2; print(v)", ctrl1));

        // Example 2: int a; int b; a=2+3*5; b=a+1; print(b)
        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)), new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp(ArithOp.ADD, new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex2);
        IRepository repo2 = new Repository(prg2);
        Controller ctrl2 = new Controller(repo2);
        menu.addCommand(new RunExample("2", "int a; int b; a=2+3*5; b=a+1; print(b)", ctrl2));

        menu.show();
    }
}