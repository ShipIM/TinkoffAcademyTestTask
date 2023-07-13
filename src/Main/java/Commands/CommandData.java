package Commands;

public class CommandData {
    private final byte type;
    private final long address;
    private final byte[] cmdBody;

    public CommandData(byte type, long address, byte[] cmdBody) {
        this.type = type;
        this.address = address;
        this.cmdBody = cmdBody;
    }

    public byte getType() {
        return type;
    }

    public long getAddress() {
        return address;
    }

    public byte[] getCmdBody() {
        return cmdBody;
    }
}
