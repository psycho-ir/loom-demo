import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Program {

    public static void main(String[] args) {
        var start = System.nanoTime();
        ThreadFactory factory = Thread.builder().name("soroosh", 0).virtual().factory();
        var deadline = Instant.now().plusSeconds(200);
        try (var executor = Executors.newFixedThreadPool(1000, factory).withDeadline(deadline)) {
            for (int i = 0; i < 1000; i++) {
                final var j = i;
                executor.submit(() -> {
                    try {
                        final InetAddress[] allByName = InetAddress.getAllByName("google" + j + ".com");
//                        System.out.println(String.format("google.com hosts %s", allByName));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        var end = System.nanoTime();
        System.out.println("we are done and it took " + (end - start) / 1_000_000);


        var startWithThread = System.nanoTime();
        ThreadFactory factoryThreads = Thread.builder().name("soroosh", 0).factory();
        try (var executor = Executors.newFixedThreadPool(1000, factory).withDeadline(deadline)) {
            for (int i = 0; i < 1000; i++) {
                executor.submit(() -> {
                    try {
                        final InetAddress[] allByName = InetAddress.getAllByName("google.com");
//                        System.out.println(String.format("google.com hosts %s", allByName));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        var endWithThread = System.nanoTime();
        System.out.println("we are done and it took " + (endWithThread - startWithThread) / 1_000_000);

    }


}
