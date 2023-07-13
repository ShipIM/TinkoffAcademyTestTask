package Devices;

public abstract class Gadget {
    private final String name;
    private final long address;
    private final byte type;

    public Gadget(String name, long address, byte type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public long getAddress() {
        return address;
    }

    public byte getType() {
        return type;
    }
}
