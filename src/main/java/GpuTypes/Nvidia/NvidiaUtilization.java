import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class NvidiaUtilization extends Utilization {
    public NvidiaUtilization() {}

    public void parse(Element uNode) {
        gpuUtil = uNode.getElementsByTagName("gpu_util").item(0).getTextContent();
        memoryUtil = uNode.getElementsByTagName("memory_util").item(0).getTextContent();
    }
}
