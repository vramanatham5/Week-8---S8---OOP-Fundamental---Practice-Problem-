import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

interface PaymentMethod {
    double finalAmount(double amount);
}

class FeeBasedPayment implements PaymentMethod {
    private final double feeRate;

    FeeBasedPayment(double feeRate) {
        this.feeRate = feeRate;
    }

    @Override
    public double finalAmount(double amount) {
        return amount * (1 + feeRate);
    }
}

class BankTransferPayment implements PaymentMethod {
    @Override
    public double finalAmount(double amount) {
        return amount;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Map<String, PaymentMethod> methods = new HashMap<>();
        methods.put("CARD", new FeeBasedPayment(0.02));
        methods.put("WALLET", new FeeBasedPayment(0.01));
        methods.put("BANKTRANSFER", new BankTransferPayment());

        var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine().trim());
        double total = 0;
        for (int i = 0; i < count; i++) {
            String[] parts = reader.readLine().trim().split("\\s+");
            PaymentMethod method = methods.get(parts[0]);
            if (method == null) throw new IllegalArgumentException("Unknown payment type: " + parts[0]);
            double adjusted = method.finalAmount(Double.parseDouble(parts[1]));
            total += adjusted;
            System.out.printf("%s: %.2f%n", parts[0], adjusted);
        }
        System.out.printf("Total: %.2f%n", total);
    }
}