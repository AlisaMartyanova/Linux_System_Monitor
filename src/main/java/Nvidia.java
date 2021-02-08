import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Nvidia {
    String smiPrefix;
    String tmpFileName;

    public Nvidia(String prefix) {
        if (prefix != "") {
            smiPrefix = prefix + " ";
        } else {
            smiPrefix = prefix;
        }

        tmpFileName = "nvidia.tmp";
    }

    public void fetchUpdates() {
        String command = "nvidia-smi -q -x";
        try {
            Process process = Runtime.getRuntime().exec(smiPrefix + command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Fetching Nvidia updates goes wrong.");
        }
    }
}
