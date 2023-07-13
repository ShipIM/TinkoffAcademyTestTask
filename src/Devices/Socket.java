package Devices;

public class Socket extends ConditionGadget {
    public Socket(String name, long address) {
        super(name, address, (byte) 5);
    }
}
