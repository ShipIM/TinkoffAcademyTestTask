package DefinedStructures;

import Final.Serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnvSensorStatusCmdBody {
    private List<Long> values;

    public EnvSensorStatusCmdBody() {

    }

    public List<Long> getValues() {
        return values;
    }

    public void deserialize(byte[] data) throws IOException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            values = new ArrayList<>();
            int length = stream.read();

            for (int i = 0; i < length; i++) {
                values.add(Serialization.decodeUInt(stream));
            }
        }
    }
}