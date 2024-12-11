import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Solve {

    public Solve(int n) {
        long startTime = System.nanoTime();

        CompletableFuture<List<Integer>> generateArray = CompletableFuture.supplyAsync(() -> {
            List<Integer> arr = new ArrayList<>();
            Random r = new Random();
            System.out.println("Generated values:");
            for (int i = 0; i < n; i++) {
                int val = r.nextInt(100);
                arr.add(val);
                System.out.println(val);
            }
            printElapsedTime("First array generated", startTime);
            return arr;
        });

        CompletableFuture<List<Integer>> plus10 = generateArray.thenApplyAsync(arr -> {
            List<Integer> plus10List = new ArrayList<>();
            System.out.println("Array values plus 10:");
            for (int val : arr) {
                int newVal = val + 10;
                plus10List.add(newVal);
                System.out.println(newVal);
            }
            printElapsedTime("Array plus 10 generated", startTime);
            return plus10List;
        });

        CompletableFuture<List<Double>> divBy2 = plus10.thenApplyAsync(arr -> {
            List<Double> divBy2List = new ArrayList<>();
            System.out.println("Array values divided by 2:");
            for (int val : arr) {
                double newVal = val / 2.0;
                divBy2List.add(newVal);
                System.out.println(newVal);
            }
            printElapsedTime("Array div by 2 generated", startTime);
            return divBy2List;
        });

        divBy2.thenAcceptAsync(finalArray -> {
            System.out.println("Final array: " + finalArray);
            printElapsedTime("Final array displayed", startTime);
        }).join(); 
    }

    private void printElapsedTime(String message, long startTime) {
        long elapsedTime = System.nanoTime() - startTime;
        System.out.printf("%s - Time elapsed: %d ms\n", message, TimeUnit.NANOSECONDS.toMillis(elapsedTime));
    }
}
