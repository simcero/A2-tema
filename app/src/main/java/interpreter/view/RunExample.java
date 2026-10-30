package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String desc, Controller controller) {
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            // Enable display flag so you can see the trace step-by-step
            controller.setDisplayFlag(true);
            controller.allStep();
        } catch (MyException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("UNEXPECTED ERROR: " + e.getMessage());
        }
    }
}