package DefinedStructures;

import Final.Serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Payload {
    private long src;
    private long dst;
    private long serial;
    private byte devType;
    private byte cmd;
    private byte[] cmdBody;

    public Payload(long src, long dst, long serial, byte devType, byte cmd, byte[] cmdBody) {
        this.src = src;
        this.dst = dst;
        this.serial = serial;
        this.devType = devType;
        this.cmd = cmd;
        this.cmdBody = cmdBody;
    }

    public Payload() {

    }

    public long getSrc() {
        return src;
    }

    public long getDst() {
        return dst;
    }

    public long getSerial() {
        return serial;
    }

    public byte getDevType() {
        return devType;
    }

    public byte getCmd() {
        return cmd;
    }

    public byte[] getCmdBody() {
        return cmdBody;
    }

    public byte[] serialize() throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            stream.write(Serialization.encodeUInt(src));
            stream.write(Serialization.encodeUInt(dst));
            stream.write(Serialization.encodeUInt(serial));
            stream.write(devType);
            stream.write(cmd);

            if (cmdBody != null)
                stream.write(cmdBody);

            return stream.toByteArray();
        }
    }

    public void deserialize(byte[] data) throws IOException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            src = (int) Serialization.decodeUInt(stream);
            dst = (int) Serialization.decodeUInt(stream);
            serial = (int) Serialization.decodeUInt(stream);
            devType = (byte) stream.read();
            cmd = (byte) stream.read();

            cmdBody = stream.readAllBytes();
        }
    }
}