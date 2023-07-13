package DefinedStructures;

import Final.Serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Device {
    private String devName;
    private byte[] devProps;

    public Device(String devName, byte[] devProps) {
        this.devName = devName;
        this.devProps = devProps;
    }

    public Device() {

    }

    public String getDevName() {
        return devName;
    }

    public byte[] getDevProps() {
        return devProps;
    }

    public byte[] serialize() throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            stream.write(Serialization.encodeString(devName));

            if (devProps != null)
                stream.write(devProps);

            return stream.toByteArray();
        }
    }

    public void deserialize(byte[] data) throws IOException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            devName = Serialization.decodeString(stream);

            devProps = stream.readAllBytes();
        }
    }
}