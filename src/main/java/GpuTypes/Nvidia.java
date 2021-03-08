import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import java.util.ArrayList;
import java.util.concurrent.*;
import org.xml.sax.*;


public class Nvidia implements GpuType, Runnable {
    String smiPrefix;
    private final BlockingQueue<Gpu> queue;
    int tick;

    public Nvidia(String prefix, int tick, BlockingQueue<Gpu> queue) {
        if (prefix != "") {
            this.smiPrefix = prefix + " ";
        } else {
            this.smiPrefix = prefix;
        }

        this.tick = tick;
        this.queue = queue;
    }

    public void run() {
        while(true){
            ArrayList<Gpu> gpus = fetchUpdates();
            try {
                gpus.forEach(g -> {
                        try {
                            queue.put(g);
                        } catch (Exception e) {
                            System.out.println("Error occured while pushing updates");
                        }
                    });
                Thread.sleep(tick);
            } catch (Exception e) {}
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
            dBuilder.setErrorHandler(new ErrorHandler() {
                    @Override
                    public void warning(SAXParseException exception) throws SAXException {
                        throw new SAXException();
                    }

                    @Override
                    public void fatalError(SAXParseException exception) throws SAXException {
                        throw new SAXException();
                    }

                    @Override
                    public void error(SAXParseException exception) throws SAXException {
                        throw new SAXException();
                    }
                });

            Document doc = dBuilder.parse(process.getInputStream());
            gpus = parseUpdates(doc);

        } catch (Exception e) {}

        return gpus;
    }

    public static ArrayList<Gpu> parseUpdates(Document doc) {
        doc.getDocumentElement().normalize();

        ArrayList<Gpu> gpus = new ArrayList();
        NodeList gList = doc.getElementsByTagName("gpu");

        for (int i = 0; i < gList.getLength(); i++) {
            NvidiaGpu g = new NvidiaGpu();
            g.parse((Element) gList.item(i));
            gpus.add(g);
        }

        return gpus;
    }
}
