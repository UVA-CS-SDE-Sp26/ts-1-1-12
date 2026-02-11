import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class fildhandlertest {

    private static final String DATA_FOLDER = "data";

    @BeforeEach
    void setUp() throws IOException {
        // Create data directory
        Files.createDirectories(Paths.get(DATA_FOLDER));

        // Create test files
        Files.writeString(Paths.get(DATA_FOLDER, "test1.txt"), "Hello");
        Files.writeString(Paths.get(DATA_FOLDER, "test2.txt"), "World");
    }

    @AfterEach
    void tearDown() throws IOException {
        // Delete files and directory
        Files.walk(Paths.get(DATA_FOLDER))
                .sorted((a, b) -> b.compareTo(a)) // delete children first
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException ignored) {
                    }
                });
    }

    //CONSTRUCTOR TESTS
    @Test
    void constructor_createsSuccessfully_ifDataFolderExists() {
        assertDoesNotThrow(filehandler::new, "Constructor should not throw when data folder exists");
    }

    // listFiles() TESTS

    @Test
    void listFiles_addsAllFileNamesToList() {
        filehandler fh = new filehandler();

        fh.listFiles();
        List<String> files = fh.getFilenames();

        assertEquals(2, files.size(), "Filenames list should contain 2 files");
        assertTrue(files.contains("test1.txt"), "Filenames should include test1.txt");
        assertTrue(files.contains("test2.txt"), "Filenames should include test2.txt");
    }

    @Test
    void listFiles_clearsPreviousResults() {
        filehandler fh = new filehandler();

        fh.listFiles();
        fh.listFiles(); // call twice

        assertEquals(2, fh.getFilenames().size(), "Filenames list should be cleared before repopulating");
    }

// readFile() TESTS

    @Test
    void readFile_returnsCorrectFileContents() throws IOException {
        filehandler fh = new filehandler();

        String result = fh.readFile("test1.txt");

        assertTrue(result.contains("Hello"), "File content should contain 'Hello'");
    }

    @Test
    void readFile_throwsException_ifFileDoesNotExist() {
        filehandler fh = new filehandler();

        assertThrows(IOException.class, () -> {
            fh.readFile("missing.txt");
        }, "Reading a missing file should throw IOException");
    }

    @Test
    void readFile_throwsException_ifPathIsDirectory() throws IOException {
        filehandler fh = new filehandler();

        Files.createDirectories(Paths.get(DATA_FOLDER, "subfolder"));

        assertThrows(IOException.class, () -> {
            fh.readFile("subfolder");
        }, "Reading a directory should throw IOException");
    }
}
