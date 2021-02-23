import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Utilization {
    String gpuUtil;
    String memoryUtil;

    public Utilization(String gpuUtil, String memoryUtil) {
        this.gpuUtil = gpuUtil;
        this.memoryUtil = memoryUtil;

    }

    public String getGpuUtil() {
        return gpuUtil;
    }

    public String getMemoryUtil() {
        return memoryUtil;
    }

    public void pprint() {
        System.out.println("  Utilization:");
        System.out.println("    Gpu: " + gpuUtil);
        System.out.println("    Memory: " + memoryUtil);
    }
}