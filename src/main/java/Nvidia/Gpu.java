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

    public Gpu() {
        utilization = new Utilization();
        memory = new Memory();
        processes = new ArrayList();
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

    public void parse(Element gNode) {
        name = gNode.getElementsByTagName("product_name").item(0).getTextContent();
        utilization.parse((Element) gNode.getElementsByTagName("utilization").item(0));
        memory.parse((Element) gNode.getElementsByTagName("fb_memory_usage").item(0));

        NodeList pList = gNode.getElementsByTagName("process_info");
        for (int i = 0; i < pList.getLength(); i++) {
            ProcessInfo p = new ProcessInfo();
            p.parse((Element) pList.item(i));
            processes.add(p);
        }
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
