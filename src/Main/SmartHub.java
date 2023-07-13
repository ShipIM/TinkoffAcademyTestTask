package Main;

import Commands.Command;
import Commands.CommandData;
import Commands.CommandFactory;
import DefinedStructures.Packet;
import DefinedStructures.Payload;
import Final.CRC8;
import Final.PayloadFormer;
import Final.Register;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SmartHub {
    public static PayloadFormer former;
    public static long currentTime;

    public static void main(String[] args) {
        String dest = "http://localhost:9998"; // args[0];
        long address = 819; // Long.decode(args[1]);

        List<Packet> input = new ArrayList<>();
        former = new PayloadFormer("SmartHub", address, (byte) 1);
        Register register = new Register();

        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        try {
            byte[] bytes = former.whoIsHere().serialize();

            Packet packet = new Packet((byte) bytes.length, bytes, (byte) CRC8.compute(bytes));
            bytes = packet.serialize();

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(Base64
                            .getUrlEncoder()
                            .withoutPadding()
                            .encodeToString(bytes)))
                    .uri(URI.create(dest))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            while (response.statusCode() != 204) {
                if (response.statusCode() != 200)
                    System.exit(99);

                input.clear();
                bytes = Base64.getUrlDecoder().decode(response.body());
                try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes)) {
                    while (stream.available() > 0) {
                        Packet answer = new Packet();
                        answer.deserialize(stream);
                        input.add(answer);
                    }
                }

                try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                    for (Packet answer : input) {
                        try {
                            if (CRC8.compute(answer.getPayload()) != answer.getCrc8())
                                continue;

                            Payload payload = new Payload();
                            payload.deserialize(answer.getPayload());

                            Command command = CommandFactory.getCommand(payload.getCmd());

                            if (command == null)
                                continue;

                            List<Payload> payloads = command.execute(register,
                                    new CommandData(payload.getDevType(), payload.getSrc(), payload.getCmdBody()));

                            if (payloads == null)
                                continue;

                            for (Payload content : payloads) {
                                byte[] serialized = content.serialize();
                                Packet wrapper = new Packet((byte) serialized.length,
                                        serialized,
                                        (byte) CRC8.compute(serialized));
                                stream.write(wrapper.serialize());
                            }
                        } catch (IOException e) {
                            //ignored;
                        }
                    }

                    bytes = stream.toByteArray();

                    request = HttpRequest.newBuilder()
                            .POST(HttpRequest.BodyPublishers.ofString(Base64
                                    .getUrlEncoder()
                                    .withoutPadding()
                                    .encodeToString(bytes)))
                            .uri(URI.create(dest))
                            .build();

                    response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                }
            }

            System.exit(0);
            //TODO Здесь должна быть обработка полученных пакетов.

        } catch (IOException | InterruptedException e) {
            System.exit(99);
        }
    }
}
