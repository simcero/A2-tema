package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.IntType;

public class IntValue implements IValue {
    private final int val;

    public IntValue(int v) {
        this.val = v;
    }

    public int getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof IntValue) {
            return this.val == ((IntValue) another).getVal();
        }
        return false;
    }
}