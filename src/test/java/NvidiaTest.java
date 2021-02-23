import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;


public class NvidiaTest {

    private ArrayList<Gpu> readSample(String fileName) {
        ArrayList<Gpu> gpus = new ArrayList();

        try {
            File sampleFile = new File("test_samples/nvidia/" + fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(sampleFile);

            gpus = Nvidia.parseUpdates(doc);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while reading sample xml file");
        }

        return gpus;
    }

    @Test
    public void noProcesses() {
        Gpu gpu = readSample("no-processes.xml").get(0);

        assertEquals("GeForce GTX 1050", gpu.getName());

        assertEquals("1 %", gpu.getUtilization().getGpuUtil());
        assertEquals("0 %", gpu.getUtilization().getMemoryUtil());

        assertEquals("4042 MiB", gpu.getMemory().getTotal());
        assertEquals("0 MiB", gpu.getMemory().getUsed());
        assertEquals("4042 MiB", gpu.getMemory().getFree());

        assertEquals(0, gpu.getProcesses().size());
    }

    @Test
    public void xorgOnly() {
        Gpu gpu = readSample("xorg-only.xml").get(0);

        assertEquals("GeForce GTX 1050", gpu.getName());

        assertEquals("0 %", gpu.getUtilization().getGpuUtil());
        assertEquals("0 %", gpu.getUtilization().getMemoryUtil());

        assertEquals("4042 MiB", gpu.getMemory().getTotal());
        assertEquals("6 MiB", gpu.getMemory().getUsed());
        assertEquals("4036 MiB", gpu.getMemory().getFree());

        assertEquals(1, gpu.getProcesses().size());

        ProcessInfo pi = gpu.getProcesses().get(0);

        assertEquals("383865", pi.getPid());
        assertEquals("/usr/lib/Xorg", pi.getName());
        assertEquals("5 MiB", pi.getUsedMemory());
    }

}
