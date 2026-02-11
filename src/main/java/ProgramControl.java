import java.io.IOException;
import java.util.List;

public class ProgramControl {

    private filehandler fh;

    public ProgramControl(filehandler fh) {
        this.fh = fh;
    }

    public String handleRequest(String[] args) {
        try {
            // Refresh the file list
            fh.listFiles();
            List<String> files = fh.getFilenames();

            // No arguments -> List all files
            if (args.length == 0) {
                if (files.isEmpty()) {
                    return "No files found in data folder.";
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < files.size(); i++) {
                    // Formating: 01 filename.txt
                    sb.append(String.format("%02d %s%n", i + 1, files.get(i)));
                }
                return sb.toString().trim();
            }

            // User selected a file number -> Show content
            int fileIndex = Integer.parseInt(args[0]) - 1; // Convert 1-based to 0-based

            if (fileIndex < 0 || fileIndex >= files.size()) {
                return "Error: File number out of range.";
            }

            String targetFileName = files.get(fileIndex);
            String content = fh.readFile(targetFileName);
            return content;

        } catch (NumberFormatException e) {
            return "Error: Argument must be a valid number.";
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }
}