package DefinedStructures;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EnvSensorProps implements Serializable {
    private byte sensors;
    private List<Trigger> triggers;

    public EnvSensorProps(byte sensors, List<Trigger> triggers) {
        this.sensors = sensors;
        this.triggers = triggers;
    }

    public EnvSensorProps() {

    }

    public byte getSensors() {
        return sensors;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void deserialize(byte[] data) throws IOException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            sensors = (byte) stream.read();
            triggers = new ArrayList<>();

            int length = stream.read();
            for (int i = 0; i < length; i++) {
                Trigger trigger = new Trigger();
                trigger.deserialize(stream);
                triggers.add(trigger);
            }
        }
    }
}