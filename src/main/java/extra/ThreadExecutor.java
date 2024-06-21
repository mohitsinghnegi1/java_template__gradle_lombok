package extra;

import java.util.concurrent.*;

public class ThreadExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(()->{
            System.out.println("hello");
        });

        executorService.submit(()->{
            System.out.println("Bye Bye");
        });
        Future<Integer> fu = executorService.submit(() -> 20);
        fu.get(); // Blocking

        executorService.awaitTermination(1900, TimeUnit.MILLISECONDS);
        executorService.shutdown();


    }
}
