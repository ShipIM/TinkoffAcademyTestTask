package Commands;

public class CommandFactory {
    public static Command getCommand(byte number) {
        switch (number) {
            case 1:
                return new WhoIsHere();
            case 2:
                return new IAmHere();
            case 4:
                return new Status();
            case 6:
                return new Tick();
            default:
                return null;
        }
    }
}
