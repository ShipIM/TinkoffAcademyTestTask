package Devices;

import DefinedStructures.Change;
import DefinedStructures.EnvSensorProps;
import DefinedStructures.EnvSensorStatusCmdBody;
import DefinedStructures.Trigger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnvSensor extends InitializableGadget {
    private byte sensors;
    private List<Trigger> triggers;
    private final List<Long> values;

    public EnvSensor(String name, long address) {
        super(name, address, (byte) 2);

        values = new ArrayList<>((List.of(0L, 0L, 0L, 0L)));
    }

    @Override
    public void init(byte[] props) throws IOException {
        EnvSensorProps sensorProps = new EnvSensorProps();
        sensorProps.deserialize(props);

        sensors = sensorProps.getSensors();
        triggers = sensorProps.getTriggers();
    }

    @Override
    public List<Change> setStatus(byte[] data) throws IOException {
        EnvSensorStatusCmdBody sensorStatus = new EnvSensorStatusCmdBody();
        sensorStatus.deserialize(data);

        List<Long> current = sensorStatus.getValues();

        if (current == null)
            return null;

        int counter = 0;
        for (int i = 0; i < 4 && counter < current.size(); i++) {
            values.set(i, ((sensors >> i) & 1) == 1 ? current.get(counter++) : 0);
        }

        return checkTriggers();
    }

    private List<Change> checkTriggers() {
        List<Change> result = new ArrayList<>();

        for (Trigger trigger : triggers) {
            Change change = trigger.check(values);

            if (change != null) result.add(change);
        }

        return result;
    }
}
