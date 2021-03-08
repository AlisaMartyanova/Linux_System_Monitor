import java.util.concurrent.*;

public class Runner {
    public static void run(BlockingQueue<Gpu> queue, int tickP, int tickC){
        Thread pIntel = new Thread(new Intel("sudo", tickP, queue));
        Thread pNvidia = new Thread(new Nvidia("optirun", tickP, queue));
        Thread pAmd = new Thread(new AMD("sudo", tickP, queue));
        Thread c = new Thread(new Consumer(queue, tickC));

        try{
            pIntel.start();
            pNvidia.start();
            pAmd.start();
            c.start();

            pIntel.join();
            pNvidia.join();
            pAmd.join();
            c.join();
        } catch (Exception e) {}
    }
}
