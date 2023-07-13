package Devices;

public class GadgetFactory {
    public static Gadget getGadget(String name, long address, byte number) {
        switch (number) {
            case 2:
                return new EnvSensor(name, address);
            case 3:
                return new Switch(name, address);
            case 4:
                return new Lamp(name, address);
            case 5:
                return new Socket(name, address);
            default:
                return null;
        }
    }
}
