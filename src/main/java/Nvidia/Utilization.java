import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Utilization {
    String gpuUtil;
    String memoryUtil;

    public Utilization() {}

    public String getGpuUtil() {
        return gpuUtil;
    }

    public String getMemoryUtil() {
        return memoryUtil;
    }

    public void parse(Element uNode) {
        gpuUtil = uNode.getElementsByTagName("gpu_util").item(0).getTextContent();
        memoryUtil = uNode.getElementsByTagName("memory_util").item(0).getTextContent();
    }

    public void pprint() {
        System.out.println("  Utilization:");
        System.out.println("    Gpu: " + gpuUtil);
        System.out.println("    Memory: " + memoryUtil);
    }
}
