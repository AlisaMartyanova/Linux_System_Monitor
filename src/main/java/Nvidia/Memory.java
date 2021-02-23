import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Memory {
    String total;
    String used;
    String free;

    public Memory() {}

    public String getTotal() {
        return total;
    }

    public String getUsed() {
        return used;
    }

    public String getFree() {
        return free;
    }

    public void parse(Element mNode) {
        total = mNode.getElementsByTagName("total").item(0).getTextContent();
        used = mNode.getElementsByTagName("used").item(0).getTextContent();
        free = mNode.getElementsByTagName("free").item(0).getTextContent();
    }

    public void pprint() {
        System.out.println("  Memory:");
        System.out.println("    Total: " + total);
        System.out.println("    Used: " + used);
        System.out.println("    Free: " + free);
    }
}
