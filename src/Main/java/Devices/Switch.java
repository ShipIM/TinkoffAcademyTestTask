package Devices;

import DefinedStructures.Change;
import Final.Serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Switch extends ConditionGadget {
    private List<String> connected;

    public Switch(String name, long address) {
        super(name, address, (byte) 3);
    }

    @Override
    public void init(byte[] props) throws IOException {
        this.setCondition(-1);

        try (ByteArrayInputStream stream = new ByteArrayInputStream(props)) {
            connected = new ArrayList<>();
            int length = stream.read();

            for (int i = 0; i < length; i++)
                connected.add(Serialization.decodeString(stream));
        }
    }

    @Override
    public List<Change> setStatus(byte[] data) {
        int currentCondition = this.getCondition();

        if (currentCondition == -1) {
            this.setCondition(data[0]);
            return null;
        }

        if (currentCondition == data[0]) return null;

        this.setCondition(data[0]);
        List<Change> result = new ArrayList<>();
        for (String name : connected)
            result.add(new Change(name, data[0]));

        return result;
    }
}
