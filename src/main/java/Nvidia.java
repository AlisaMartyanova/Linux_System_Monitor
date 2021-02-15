import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import java.util.ArrayList;


public class Nvidia {
    String smiPrefix;

    public Nvidia(String prefix) {
        if (prefix != "") {
            smiPrefix = prefix + " ";
        } else {
            smiPrefix = prefix;
        }
    }

    public ArrayList<Gpu> fetchUpdates() {
        String command = "nvidia-smi -q -x";

        ArrayList<Gpu> gpus = new ArrayList();
        try {
            Process process = Runtime.getRuntime().exec(smiPrefix + command);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(process.getInputStream());

            gpus = parseUpdates(doc);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fetching Nvidia updates goes wrong.");
        }

        return gpus;
    }

    public static ArrayList<Gpu> parseUpdates(Document doc) {
        doc.getDocumentElement().normalize();

        ArrayList<Gpu> gpus = new ArrayList();
        NodeList gList = doc.getElementsByTagName("gpu");

        for (int i = 0; i < gList.getLength(); i++) {
            Gpu g = new Gpu();
            g.parse((Element) gList.item(i));
            gpus.add(g);
        }

        return gpus;
    }
}
