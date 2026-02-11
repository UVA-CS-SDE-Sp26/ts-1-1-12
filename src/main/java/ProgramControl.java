import java.io.IOException;
import java.util.List;

public class ProgramControl {

    private filehandler fh;
    public ProgramControl() {
        this.fh = new filehandler();
    }

    public ProgramControl(filehandler fh) {
        this.fh = fh;
    }

    /**
     * Called when user runs "java topsecret" (no args)
     */
    public void displayFilesListed() {
        fh.listFiles();
        List<String> files = fh.getFilenames();

        if (files.isEmpty()) {
            System.out.println("No files found in data folder.");
            return;
        }

        // Print the list with numbers: 01 filea.txt
        for (int i = 0; i < files.size(); i++) {
            System.out.printf("%02d %s%n", i + 1, files.get(i));
        }
    }

    /**
     * Called when user runs "java topsecret 1"
     */
    public void displayFileContents(int fileIndex) {
        try {
            // We must refresh the list to map the Number (1) to a Name (filea.txt)
            fh.listFiles();
            List<String> files = fh.getFilenames();

            // Convert 1-based index (User input) to 0-based index (List)
            int internalIndex = fileIndex - 1;

            if (internalIndex < 0 || internalIndex >= files.size()) {
                System.out.println("Error: File number out of range.");
                return;
            }

            String fileName = files.get(internalIndex);

            // Read and print the content
            String content = fh.readFile(fileName);
            System.out.println(content);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}