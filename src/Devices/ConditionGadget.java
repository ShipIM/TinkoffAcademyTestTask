package Devices;

import DefinedStructures.Change;

import java.io.IOException;
import java.util.List;

public abstract class ConditionGadget extends InitializableGadget {
    private int condition;

    public ConditionGadget(String name, long address, byte type) {
        super(name, address, type);
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    @Override
    public void init(byte[] props) throws IOException {

    }

    @Override
    public List<Change> setStatus(byte[] data) throws IOException {
        condition = data[0];

        return null;
    }
}
