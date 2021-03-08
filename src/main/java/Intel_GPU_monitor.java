import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.LogRecord;

public class Intel_GPU_monitor {

    ArrayList<Double> overall_gpu_usage;
    int time_tick_millis;

    Intel_GPU_monitor(int time_tick_millis){
        overall_gpu_usage = new ArrayList<>();
        this.time_tick_millis = time_tick_millis;
    }

    public void start(){
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec("sudo intel_gpu_top -l -s " + time_tick_millis);
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
                overall_gpu_usage.add(parser.nextDouble());

                System.out.println("Intel gpu usage: ");
                System.out.println(overall_gpu_usage.get(overall_gpu_usage.size() - 1));
            }
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
    }
}
