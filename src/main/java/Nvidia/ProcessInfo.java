import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ProcessInfo {
    String pid;
    String name;
    String usedMemory;

    public ProcessInfo() {}

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getUsedMemory() {
        return usedMemory;
    }

    public void parse(Element piNode) {
        pid = piNode.getElementsByTagName("pid").item(0).getTextContent();
        name = piNode.getElementsByTagName("process_name").item(0).getTextContent();
        usedMemory = piNode.getElementsByTagName("used_memory").item(0).getTextContent();
    }

    public void pprint() {
        System.out.println("    ProcessInfo:");
        System.out.println("      Pid: " + pid);
        System.out.println("      Name: " + name);
        System.out.println("      UsedMemory: " + usedMemory);
    }
}
