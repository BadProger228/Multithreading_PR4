import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Solve {
    public Solve() {
        long startTime = System.nanoTime();
        double course = 0.024;
        List<Integer> grn = new ArrayList<>();
        List<Double> finalTransfer = new ArrayList<>();

        CompletableFuture<Void> start = CompletableFuture.runAsync(() -> generateGrnArray(grn));
        printElapsedTime("Generate grn array", startTime);

        CompletableFuture<List<Double>> convertToDollars = start.thenApplyAsync(unused -> {
            System.out.println("Convert to dollars");
            List<Double> dollarsAsync = new ArrayList<>();
            for (var val : grn) {
                double dol = val * course;
                dollarsAsync.add(dol);
                System.out.println(dol);
            }
            printElapsedTime("Convert to dollars", startTime);
            return dollarsAsync;
        });

        CompletableFuture<Void> commission = convertToDollars.thenAcceptAsync(dollars -> {
            System.out.println("Final dollars after commission: ");
            for (var val : dollars) {
                double finalValue = val - val * 0.05;
                finalTransfer.add(finalValue);
                System.out.println(finalValue);
            }
            printElapsedTime("Final dollars after commission", startTime);
        });

        commission.join();
    }

    private void printElapsedTime(String message, long startTime) {
        long elapsedTime = System.nanoTime() - startTime;
        System.out.printf("%s - Time elapsed: %d ms\n", message, TimeUnit.NANOSECONDS.toMillis(elapsedTime));
    }

    private void generateGrnArray(List<Integer> arr) {
        System.out.println("Generate grn array: ");
        Random r = new Random();
        int size = r.nextInt(25) + 1;
        for (int i = 0; i < size; i++) {
            arr.add(r.nextInt(100) + 15 / 2);
        }

        System.out.println("Array is: ");
        for (var val : arr) {
            System.out.println(val);
        }
    }
}
