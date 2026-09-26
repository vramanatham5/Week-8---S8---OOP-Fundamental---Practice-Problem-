import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

interface Transport {
    double fare();
}

class BusTransport implements Transport {
    private final double distance;
    BusTransport(double distance) { this.distance = distance; }
    @Override public double fare() { return Math.min(2 + 0.10 * distance, 10); }
}

class TrainTransport implements Transport {
    private final double distance;
    TrainTransport(double distance) { this.distance = distance; }
    @Override public double fare() { return 3 + 0.15 * distance; }
}

class MetroTransport implements Transport {
    private final double distance;
    private final double peakHourFactor;
    MetroTransport(double distance, double peakHourFactor) {
        this.distance = distance;
        this.peakHourFactor = peakHourFactor;
    }
    @Override public double fare() { return (1.50 + 0.20 * distance) * peakHourFactor; }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, Function<String[], Transport>> transportTypes = new HashMap<>();
        transportTypes.put("BUS", p -> new BusTransport(Double.parseDouble(p[1])));
        transportTypes.put("TRAIN", p -> new TrainTransport(Double.parseDouble(p[1])));
        transportTypes.put("METRO", p -> new MetroTransport(Double.parseDouble(p[1]), Double.parseDouble(p[2])));

        var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine().trim());
        double total = 0;
        for (int i = 0; i < count; i++) {
            String[] parts = reader.readLine().trim().split("\\s+");
            Function<String[], Transport> constructor = transportTypes.get(parts[0]);
            if (constructor == null) throw new IllegalArgumentException("Unknown transport type: " + parts[0]);
            double fare = constructor.apply(parts).fare();
            total += fare;
            System.out.printf("%s: %.2f%n", parts[0], fare);
        }
        System.out.printf("Total: %.2f%n", total);
    }
}