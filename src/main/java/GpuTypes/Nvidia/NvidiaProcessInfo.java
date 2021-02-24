import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class NvidiaProcessInfo extends ProcessInfo {
    public NvidiaProcessInfo() {}

    public void parse(Element piNode) {
        pid = piNode.getElementsByTagName("pid").item(0).getTextContent();
        name = piNode.getElementsByTagName("process_name").item(0).getTextContent();
        usedMemory = piNode.getElementsByTagName("used_memory").item(0).getTextContent();
    }
}
