import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Solve {
    public Solve() {
        long startTime = System.nanoTime();
        List<Integer> arr = new ArrayList<>();

        CompletableFuture<Void> start = CompletableFuture.runAsync(() -> generateArray(arr));
        CompletableFuture<Void> show = start.thenAcceptAsync(unused -> printElapsedTime("Generated array", startTime));

        CompletableFuture<Void> findResult = show.thenAcceptAsync(unused -> {
            System.out.println("Finding result: ");
            int result = 1;
            for (int i = 0; i < arr.size() - 1; i++) 
                result *= arr.get(i + 1) - arr.get(i);                
            
            System.out.println("Result is " + result);
            printElapsedTime("Time to find a result", startTime);
        });

        findResult.join();
    }

    private void printElapsedTime(String message, long startTime) {
        long elapsedTime = System.nanoTime() - startTime;
        System.out.printf("%s - Time elapsed: %d ms\n", message, TimeUnit.NANOSECONDS.toMillis(elapsedTime));
    }

    private void generateArray(List<Integer> arr) {
        System.out.println("Generate array: ");
        Random r = new Random();
        
        for (int i = 0; i < 20; i++) {
            arr.add(r.nextInt(100) + 15 / 2);
        }
        System.out.println("Array is: " + arr);
    }
}
