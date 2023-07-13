package Devices;

public class Clock extends Gadget {
    private long timestamp = Long.MIN_VALUE;

    public Clock(String name, long address, long timestamp) {
        super(name, address, (byte) 6);

        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
