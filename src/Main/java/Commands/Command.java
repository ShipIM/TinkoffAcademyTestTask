package Commands;

import DefinedStructures.Payload;
import Final.Register;

import java.io.IOException;
import java.util.List;

public interface Command {
    List<Payload> execute(Register register, CommandData data) throws IOException;
}
