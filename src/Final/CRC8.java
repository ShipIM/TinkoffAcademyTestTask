package Final;

public class CRC8 {
    private static int[] table;

    private static void generateTable() {
        table = new int[256];
        byte generator = 0x1D;

        for (int i = 0; i < 256; ++i)
            table[i] = pushByte(i, generator);
    }

    private static int pushByte(int b, byte generator) {
        for (int i = 0; i < 8; ++i)
            if ((b & 0x80) != 0) {
                int step1 = b << 1;
                b = step1 ^ generator;
            } else
                b = b << 1;

        return b & 0xFF;
    }

    public static int compute(byte[] bytes) {
        if (table == null) generateTable();
        int crc = 0;

        for (byte b : bytes)
            crc = table[crc ^ (b & 0xFF)];

        return crc;
    }
}
