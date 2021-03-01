import java.util.concurrent.*;

public class Consumer implements Runnable {
    private final BlockingQueue<Gpu> queue;
    int tick;

    public Consumer(BlockingQueue<Gpu> queue, int tick){
        this.queue = queue;
        this.tick = tick;
    }

    public void run(){
        while(true){
            try{
                Gpu g = queue.take();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                g.pprint();
                Thread.sleep(tick);
            } catch (Exception e) {}
        }
    }
}
