import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.ArrayList;

public class NvidiaGpu extends Gpu {
    NvidiaUtilization nvUtilization;
    NvidiaMemory nvMemory;

    public NvidiaGpu() {
        super();
        nvUtilization = new NvidiaUtilization();
        nvMemory = new NvidiaMemory();
    }

    public void parse(Element gNode) {
        name = gNode.getElementsByTagName("product_name")
            .item(0).getTextContent();
        nvUtilization.parse((Element) gNode
                   .getElementsByTagName("utilization").item(0));
        nvMemory.parse((Element) gNode
                   .getElementsByTagName("fb_memory_usage").item(0));

        NodeList pList = gNode.getElementsByTagName("process_info");
        for (int i = 0; i < pList.getLength(); i++) {
            NvidiaProcessInfo p = new NvidiaProcessInfo();
            p.parse((Element) pList.item(i));
            processes.add(p);
        }

        utilization = nvUtilization;
        memory = nvMemory;
    }

}
