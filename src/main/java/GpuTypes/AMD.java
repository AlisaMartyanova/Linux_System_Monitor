import java.io.*;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import java.util.ArrayList;


public class AMD implements GpuType {
    String radeontopPrefix;

    public AMD(String prefix) {
        if (prefix != "") {
            radeontopPrefix = prefix + " ";
        } else {
            radeontopPrefix = prefix;
        }
    }

    public ArrayList<Gpu> fetchUpdates() {
        String command = "radeontop -l 1 -d -";

        ArrayList<Gpu> gpus = new ArrayList();
        try {
            Process process = Runtime.getRuntime().exec(radeontopPrefix + command);

            Scanner scanner = new Scanner(process.getInputStream());

            scanner.useDelimiter("\\A");
            String commandOutput = scanner.next();

            String[] usageInfoEntries = commandOutput.split("\n")[1].split(":")[1].split(",");


            String gpuUtil = usageInfoEntries[1].split(" ")[2];
            String memoryUtil = usageInfoEntries[12].split(" ")[2];

            String usedMemory = usageInfoEntries[12].split(" ")[3];
            String totalMemory = String.valueOf(Float.parseFloat(usedMemory.replace("mb", "")) * 100 / Float.parseFloat(memoryUtil.replace("%", ""))) + "mb";
            String freeMemory = String.valueOf(Float.parseFloat(totalMemory.replace("mb", "")) - Float.parseFloat(usedMemory.replace("mb", ""))) + "mb";

            Utilization utilization = new Utilization(gpuUtil, memoryUtil);
            Memory memory = new Memory(totalMemory, usedMemory, freeMemory);

            String name = usageInfoEntries[0].strip();

            Gpu gpu = new Gpu(name, utilization, memory, new ArrayList<ProcessInfo>());

            gpus.add(gpu);
        } catch (Exception e) {}

        return gpus;
    }
}
