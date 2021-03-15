import java.util.concurrent.*;
import java.util.*;

public class Consumer implements Runnable {
    private final BlockingQueue<Gpu> queue;
    int tick;
    Map<String,Gpu> gpus;

    public Consumer(BlockingQueue<Gpu> queue, int tick){
        this.queue = queue;
        this.tick = tick;
        this.gpus = new HashMap();
    }

    public void run(){
        while(true){
            try{
                Gpu g = queue.take();

                if(gpus.containsKey(g.getName()))
                    gpus.replace(g.getName(), g);
                else
                    gpus.put(g.getName(), g);

                System.out.print("\033[H\033[2J");
                System.out.flush();
                gpus.forEach((k, v) -> v.pprint());
                Thread.sleep(tick);
            } catch (Exception e) {}
        }
    }
}
