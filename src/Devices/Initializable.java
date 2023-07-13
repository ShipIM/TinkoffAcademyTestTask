package Devices;

import DefinedStructures.Change;

import java.io.IOException;
import java.util.List;

public interface Initializable {
    void init(byte[] data) throws IOException;

    List<Change> setStatus(byte[] data) throws IOException;
}
