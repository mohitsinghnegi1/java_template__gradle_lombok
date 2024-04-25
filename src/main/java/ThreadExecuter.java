import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecuter {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);


        for(int i=0;i<20;i++) {
            int finalI = i;
            executor.execute(() -> {
//                System.out.println("hello");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task id"+ String.valueOf(finalI));
            });
        }

        executor.shutdown();
    }
}
