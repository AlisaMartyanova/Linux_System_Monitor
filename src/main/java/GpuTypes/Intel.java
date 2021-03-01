import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.LogRecord;
import java.util.concurrent.*;

public class Intel implements Runnable {
    // ArrayList<Double> overall_gpu_usage;
    // int time_tick_millis;
    String intelPrefix;
    private final BlockingQueue<Gpu> queue;
    int tick;

    // public Intel(int time_tick_millis){
    //     overall_gpu_usage = new ArrayList<>();
    //     this.time_tick_millis = time_tick_millis;
    // }

    public Intel(String prefix, int tick, BlockingQueue<Gpu> queue) {
        if (prefix != "") {
            this.intelPrefix = prefix + " ";
        } else {
            this.intelPrefix = prefix;
        }

        this.tick = tick;
        this.queue = queue;
    }

    public void run(){
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec(intelPrefix + "intel_gpu_top -l -s " + tick);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            if ((s = br.readLine()) != null){
                if (!s.equals(" Freq MHz      IRQ RC6 Power     IMC MiB/s           RCS/0           BCS/0           VCS/0          VECS/0 ")){
                    System.out.println("Wrong version of intel_gpu_top");
                    return;
                }
            }
            br.readLine();
            while((s = br.readLine()) != null) {
                if (s.equals(" Freq MHz      IRQ RC6 Power     IMC MiB/s           RCS/0           BCS/0           VCS/0          VECS/0 ") || s.equals("  req  act       /s   %     W     rd     wr       %  se  wa       %  se  wa       %  se  wa       %  se  wa ")){
                    continue;
                }
                Scanner parser = new Scanner(s);
                // System.out.println("line: " + s);
                for (int i = 0; i < 7; i++) {
                    parser.next();
                }
                // overall_gpu_usage.add(parser.nextDouble());

                String gpuUtil = String.valueOf(parser.nextDouble()) + "%";
                Gpu g = genGpu(gpuUtil);

                queue.put(g);
                // System.out.println(overall_gpu_usage.get(overall_gpu_usage.size() - 1));
            }
            p.waitFor();
            // System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
    }

    private Gpu genGpu(String gpuUtil){
        Utilization util = new Utilization(gpuUtil, "undefined");
        Memory mem = new Memory("undefined", "undefined", "undefined");
        ArrayList<ProcessInfo> ps = new ArrayList();
        Gpu g = new Gpu("Intel integrated graphics", util, mem, ps);
        return g;
    }

    // public void start(){
    //     String s;
    //     Process p;
    //     try {
    //         p = Runtime.getRuntime().exec("sudo intel_gpu_top -l -s " + time_tick_millis);
    //         BufferedReader br = new BufferedReader(
    //                 new InputStreamReader(p.getInputStream()));
    //         if ((s = br.readLine()) != null){
    //             if (!s.equals(" Freq MHz      IRQ RC6 Power     IMC MiB/s           RCS/0           BCS/0           VCS/0          VECS/0 ")){
    //                 System.out.println("Wrong version of intel_gpu_top");
    //                 return;
    //             }
    //         }
    //         br.readLine();
    //         while((s = br.readLine()) != null) {
    //             if (s.equals(" Freq MHz      IRQ RC6 Power     IMC MiB/s           RCS/0           BCS/0           VCS/0          VECS/0 ") || s.equals("  req  act       /s   %     W     rd     wr       %  se  wa       %  se  wa       %  se  wa       %  se  wa ")){
    //                 continue;
    //             }
    //             Scanner parser = new Scanner(s);
    //             System.out.println("line: " + s);
    //             for (int i = 0; i < 7; i++) {
    //                 parser.next();
    //             }
    //             overall_gpu_usage.add(parser.nextDouble());
    //             System.out.println(overall_gpu_usage.get(overall_gpu_usage.size() - 1));
    //         }
    //         p.waitFor();
    //         System.out.println ("exit: " + p.exitValue());
    //         p.destroy();
    //     } catch (Exception e) {}
    // }
}
