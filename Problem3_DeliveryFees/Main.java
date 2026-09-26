import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

interface Delivery {
    double fee();
}

class StandardDelivery implements Delivery {
    private final double weight;
    private final double distance;
    StandardDelivery(double weight, double distance) { this.weight = weight; this.distance = distance; }
    @Override public double fee() { return 5 + 0.50 * weight + 0.10 * distance; }
}

class ExpressDelivery implements Delivery {
    private final double weight;
    private final double distance;
    ExpressDelivery(double weight, double distance) { this.weight = weight; this.distance = distance; }
    @Override public double fee() { return 15 + weight + 0.20 * distance; }
}

class InternationalDelivery implements Delivery {
    private final double weight;
    private final double distance;
    private final double customsFee;
    InternationalDelivery(double weight, double distance, double customsFee) {
        this.weight = weight;
        this.distance = distance;
        this.customsFee = customsFee;
    }
    @Override public double fee() { return 25 + 2 * weight + 0.50 * distance + customsFee; }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, Function<String[], Delivery>> deliveryTypes = new HashMap<>();
        deliveryTypes.put("STANDARD", p -> new StandardDelivery(Double.parseDouble(p[1]), Double.parseDouble(p[2])));
        deliveryTypes.put("EXPRESS", p -> new ExpressDelivery(Double.parseDouble(p[1]), Double.parseDouble(p[2])));
        deliveryTypes.put("INTERNATIONAL", p -> new InternationalDelivery(
                Double.parseDouble(p[1]), Double.parseDouble(p[2]), Double.parseDouble(p[3])));

        var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine().trim());
        double total = 0;
        for (int i = 0; i < count; i++) {
            String[] parts = reader.readLine().trim().split("\\s+");
            Function<String[], Delivery> constructor = deliveryTypes.get(parts[0]);
            if (constructor == null) throw new IllegalArgumentException("Unknown delivery type: " + parts[0]);
            double fee = constructor.apply(parts).fee();
            total += fee;
            System.out.printf("%s: %.2f%n", parts[0], fee);
        }
        System.out.printf("Total: %.2f%n", total);
    }
}