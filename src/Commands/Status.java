package Commands;

import DefinedStructures.Change;
import DefinedStructures.Payload;
import Devices.Gadget;
import Devices.InitializableGadget;
import Final.Register;
import Main.SmartHub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Status implements Command {
    @Override
    public List<Payload> execute(Register register, CommandData data) throws IOException {
        InitializableGadget gadget = (InitializableGadget) register.getByAddress(data.getAddress());

        if (gadget == null) return null;

        List<Payload> result = new ArrayList<>();
        List<Change> changes = gadget.setStatus(data.getCmdBody());

        if (changes == null)
            return result;

        for (Change change : changes) {
            Gadget target = register.getByName(change.getName());

            result.add(SmartHub.former.setStatus(target.getAddress(),
                    target.getType(),
                    new byte[]{(byte) change.getCondition()}));
        }

        return result;
    }
}
