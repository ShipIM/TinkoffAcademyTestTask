package Final;

import DefinedStructures.Device;
import DefinedStructures.Payload;

import java.io.IOException;

public class PayloadFormer {
    private final String name;
    private final long src;
    private long serial = 1;
    private final byte devType;

    public PayloadFormer(String name, long src, byte devType) {
        this.name = name;
        this.src = src;
        this.devType = devType;
    }

    public Payload whoIsHere() throws IOException {
        Device device = new Device(name, null);

        return new Payload(src, 16383, serial++, devType, (byte) 1, device.serialize());
    }

    public Payload iAmHere() throws IOException {
        Device device = new Device(name, null);

        return new Payload(src, 16383, serial++, devType, (byte) 2, device.serialize());
    }

    public Payload getStatus(long destination, byte devType) {
        return new Payload(src, destination, serial++, devType, (byte) 3, null);
    }

    public Payload setStatus(long destination, byte devType, byte[] body) {
        return new Payload(src, destination, serial++, devType, (byte) 5, body);
    }
}
