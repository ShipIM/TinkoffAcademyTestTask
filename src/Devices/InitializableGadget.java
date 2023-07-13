package Devices;

public abstract class InitializableGadget extends Gadget implements Initializable {
    public InitializableGadget(String name, long address, byte type) {
        super(name, address, type);
    }
}
