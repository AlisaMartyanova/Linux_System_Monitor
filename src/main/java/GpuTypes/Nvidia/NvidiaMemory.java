import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class NvidiaMemory extends Memory {
    public NvidiaMemory() {}

    public void parse(Element mNode) {
        total = mNode.getElementsByTagName("total").item(0).getTextContent();
        used = mNode.getElementsByTagName("used").item(0).getTextContent();
        free = mNode.getElementsByTagName("free").item(0).getTextContent();
    }

}
