package Commands;

import DefinedStructures.Payload;
import DefinedStructures.TimerCmdBody;
import Devices.Clock;
import Final.Register;

import java.io.IOException;
import java.util.List;

public class Tick implements Command {
    @Override
    public List<Payload> execute(Register register, CommandData data) throws IOException {
        TimerCmdBody timerBody = new TimerCmdBody();
        timerBody.deserialize(data.getCmdBody());

        long address = data.getAddress();
        Clock clock = (Clock) register.getByAddress(address);

        if (clock == null) {
            clock = new Clock("SystemClock", address, timerBody.getTimestamp());
            register.add(clock);
        } else
            clock.setTimestamp(timerBody.getTimestamp());

        return null;
    }
}
