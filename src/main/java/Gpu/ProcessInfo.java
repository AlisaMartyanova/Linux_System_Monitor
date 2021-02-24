import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ProcessInfo {
    String pid;
    String name;
    String usedMemory;

    public ProcessInfo() {}

    public ProcessInfo(String pid, String name, String usedMemory) {
        this.pid = pid;
        this.name = name;
        this.usedMemory = usedMemory;
    }

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getUsedMemory() {
        return usedMemory;
    }

    public void pprint() {
        System.out.println("    ProcessInfo:");
        System.out.println("      Pid: " + pid);
        System.out.println("      Name: " + name);
        System.out.println("      UsedMemory: " + usedMemory);
    }
}
