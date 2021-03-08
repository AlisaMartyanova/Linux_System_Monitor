import java.util.concurrent.*;

public class Main {
    public static void main(String[] args){
        BlockingQueue<Gpu> queue = new LinkedBlockingQueue<Gpu>(15);
        Runner.run(queue, 5000, 2000);
    }
}
