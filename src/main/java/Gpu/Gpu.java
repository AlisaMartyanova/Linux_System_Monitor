import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.ArrayList;

public class Gpu {
    String name;
    Utilization utilization;
    Memory memory;
    ArrayList<ProcessInfo> processes;

    public Gpu(){
        utilization = new NvidiaUtilization();
        memory = new NvidiaMemory();
        processes = new ArrayList();
    }

    public Gpu(String name, Utilization utilization, Memory memory, ArrayList<ProcessInfo> processes) {
        this.name = name;
        this.utilization = utilization;
        this.memory = memory;
        this.processes = processes;
    }

    public String getName() {
        return name;
    }

    public Utilization getUtilization() {
        return utilization;
    }

    public Memory getMemory() {
        return memory;
    }

    public ArrayList<ProcessInfo> getProcesses() {
        return processes;
    }

    public void pprint() {
        System.out.println("Gpu:");
        System.out.println("  Name: " + name);
        utilization.pprint();
        memory.pprint();
        System.out.println("  Per processes info: ");
        processes.forEach(p -> p.pprint());
        System.out.println();
    }
}
