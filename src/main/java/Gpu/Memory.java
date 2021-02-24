import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Memory {
    String total;
    String used;
    String free;

    public Memory() {}

    public Memory(String total, String used, String free) {
        this.total = total;
        this.used = used;
        this.free = free;
    }

    public String getTotal() {
        return total;
    }

    public String getUsed() {
        return used;
    }

    public String getFree() {
        return free;
    }

    public void pprint() {
        System.out.println("  Memory:");
        System.out.println("    Total: " + total);
        System.out.println("    Used: " + used);
        System.out.println("    Free: " + free);
    }
}
