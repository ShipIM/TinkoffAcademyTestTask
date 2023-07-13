package Devices;

public class Lamp extends ConditionGadget {
    public Lamp(String name, long address) {
        super(name, address, (byte) 4);
    }
}
