package Final;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Serialization {
    public static byte[] encodeUInt(long data) {
        return Uleb128.fromLong(data).asBytes();
    }

    public static long decodeUInt(ByteArrayInputStream stream) throws IOException {
        return Uleb128.fromByteStream(stream).asLong();
    }

    public static byte[] encodeString(String data) throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            stream.write((byte) data.length());
            for (char ch : data.toCharArray())
                stream.write(ch);

            return stream.toByteArray();
        }
    }

    public static String decodeString(ByteArrayInputStream stream) throws IOException {
        int length = stream.read();
        return new String(stream.readNBytes(length));
    }
}
