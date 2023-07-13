package DefinedStructures;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Packet {
    private byte length;
    private byte[] payload;
    private int crc8;

    public Packet(byte length, byte[] payload, byte crc8) {
        this.length = length;
        this.payload = payload;
        this.crc8 = crc8;
    }

    public Packet() {

    }

    public byte[] getPayload() {
        return payload;
    }

    public int getCrc8() {
        return crc8;
    }

    public byte[] serialize() throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            stream.write(length);

            if (payload != null)
                stream.write(payload);

            stream.write((byte) crc8);

            return stream.toByteArray();
        }
    }

    public void deserialize(ByteArrayInputStream stream) throws IOException {
        length = (byte) stream.read();
        payload = stream.readNBytes(length);
        crc8 = stream.read();
    }
}