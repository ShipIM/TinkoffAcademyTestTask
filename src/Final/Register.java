package Final;

import Devices.Gadget;

import java.util.HashMap;
import java.util.Map;

public class Register {
    private final Map<Long, Gadget> addresses;
    private final Map<String, Gadget> names;

    private final Map<Gadget, Long> activeRequests;

    private final long EXPIRATION = 300;

    public Register() {
        names = new HashMap<>();
        addresses = new HashMap<>();
        activeRequests = new HashMap<>();
    }

    public Gadget getByName(String name) {
        Gadget gadget = names.get(name);

        removeActiveRequest(gadget);
        return gadget;
    }

    public Gadget getByAddress(long address) {
        Gadget gadget = addresses.get(address);

        removeActiveRequest(gadget);
        return addresses.get(address);
    }

    public void add(Gadget gadget) {
        addresses.put(gadget.getAddress(), gadget);
        names.put(gadget.getName(), gadget);
    }

    public void addActiveRequest(Gadget gadget, long timestamp) {
        activeRequests.putIfAbsent(gadget, timestamp);
    }

    private void removeActiveRequest(Gadget gadget) {
        activeRequests.remove(gadget);
    }

    public void expire(long currentTime) {
        for (Map.Entry<Gadget, Long> entry : activeRequests.entrySet())
            if (Math.abs(entry.getValue() - currentTime) > EXPIRATION) {
                addresses.remove(entry.getKey().getAddress());
                names.remove(entry.getKey().getName());

                removeActiveRequest(entry.getKey());
            }
    }
}
