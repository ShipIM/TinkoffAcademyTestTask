package DefinedStructures;

import Final.Serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TimerCmdBody {
    private long timestamp;

    public TimerCmdBody(long timestamp) {
        this.timestamp = timestamp;
    }

    public TimerCmdBody() {

    }

    public long getTimestamp() {
        return timestamp;
    }

    public void deserialize(byte[] data) throws IOException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            timestamp = Serialization.decodeUInt(stream);
        }
    }
}