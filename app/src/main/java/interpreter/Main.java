package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.StringValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import interpreter.view.RunExample;
import interpreter.view.TextMenu;

public class Main {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExample("0", "Exit", null));

        /* Example 1
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.ex1);
        IRepository repo1 = new Repository(prg1);
        Controller ctrl1 = new Controller(repo1);
        menu.addCommand(new RunExample("1", "bool a; a=2; print(a)", ctrl1));

        // Example 2
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.ex2);
        IRepository repo2 = new Repository(prg2);
        Controller ctrl2 = new Controller(repo2);
        menu.addCommand(new RunExample("2", "int a; int b; a=2+3*5; b=a+1; print(b)", ctrl2));

        // Example 3
        PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.ex3);
        IRepository repo3 = new Repository(prg3);
        Controller ctrl3 = new Controller(repo3);
        menu.addCommand(new RunExample("3", " bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)", ctrl3));

        // Example 4
        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.ex4);
        IRepository repo4 = new Repository(prg4);
        Controller ctrl4 = new Controller(repo4);
        menu.addCommand(new RunExample("4", "int v; v=2; print(v)", ctrl4));*/

        // Example 4: Testing strings, file opening, reading, and printing



        menu.show();
    }
}