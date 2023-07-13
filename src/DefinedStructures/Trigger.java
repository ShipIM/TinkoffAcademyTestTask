package DefinedStructures;

import Final.Serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class Trigger {
    private boolean condition;
    private boolean compare;
    private int type;
    private long value;
    private String name;

    public Trigger(boolean condition, boolean compare, int type, long value, String name) {
        this.condition = condition;
        this.compare = compare;
        this.type = type;
        this.value = value;
        this.name = name;
    }

    public Trigger() {

    }

    public String getName() {
        return name;
    }

    public boolean isCondition() {
        return condition;
    }

    public boolean isCompare() {
        return compare;
    }

    public int getType() {
        return type;
    }

    public long getValue() {
        return value;
    }

    public Change check(List<Long> values) {
        long current = values.get(type);

        if (compare)
            return current > value ? new Change(name, condition ? 1 : 0) : null;

        return current < value ? new Change(name, condition ? 1 : 0) : null;
    }

    public void deserialize(ByteArrayInputStream stream) throws IOException {
        byte op = (byte) stream.read();
        condition = (op & 1) == 1;
        compare = ((op >> 1) & 1) == 1;
        type = op >> 2;

        value = Serialization.decodeUInt(stream);
        name = Serialization.decodeString(stream);
    }
}
