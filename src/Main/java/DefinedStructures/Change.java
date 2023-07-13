package DefinedStructures;

public class Change {
    private final String name;
    private final int condition;

    public Change(String name, int condition) {
        this.name = name;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public int getCondition() {
        return condition;
    }
}
