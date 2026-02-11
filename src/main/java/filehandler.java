import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class filehandler {

    private final String dataFolder = "data";
    private List<String> filenames = new ArrayList<>();

    // constructor
    public filehandler() {
        File folder = new File(dataFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalStateException(
                    "Data folder '" + dataFolder + "' does not exist."
            );
        }
    }

    public String getDataFolder() {
        return dataFolder;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        if (filenames == null) {
            throw new IllegalArgumentException("filenames cannot be null");
        }
        this.filenames = filenames;
    }

    public void listFiles() {
        filenames.clear(); // avoid duplicates
        File folder = new File(dataFolder);

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filenames.add(file.getName());
                }
            }
        }
    }

    public String readFile(String fileName) throws IOException {
        File fileToBeAccessed =
                new File(dataFolder + File.separator + fileName);

        if (!fileToBeAccessed.exists() || !fileToBeAccessed.isFile()) {
            throw new IOException(
                    "File '" + fileName + "' not found in data folder."
            );
        }

        StringBuilder contents = new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileToBeAccessed))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contents.append(line)
                        .append(System.lineSeparator());
            }
        }
        return contents.toString();
    }
}
