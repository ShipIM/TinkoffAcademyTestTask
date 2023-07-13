package Commands;

import DefinedStructures.Device;
import DefinedStructures.Payload;
import Devices.GadgetFactory;
import Devices.InitializableGadget;
import Final.Register;
import Main.SmartHub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IAmHere implements Command {
    @Override
    public List<Payload> execute(Register register, CommandData data) throws IOException {
        Device device = new Device();
        device.deserialize(data.getCmdBody());

        InitializableGadget gadget = (InitializableGadget) GadgetFactory
                .getGadget(device.getDevName(), data.getAddress(), data.getType());

        if (gadget == null) return null;

        gadget.init(device.getDevProps());
        register.add(gadget);

        return new ArrayList<>(Collections.singletonList(SmartHub.former.getStatus(data.getAddress(),
                gadget.getType())));
    }
}
