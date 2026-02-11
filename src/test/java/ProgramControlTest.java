import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramControlTest {

    // 1. Output Capture Tools (To check System.out.println)
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void displayFilesListed_printsFormattedList() {
        // 1. Create the Mock
        filehandler mockFH = mock(filehandler.class);

        // 2. Define Behavior
        doNothing().when(mockFH).listFiles();
        when(mockFH.getFilenames()).thenReturn(Arrays.asList("alpha.txt", "beta.txt"));

        // 3. Inject Mock into ProgramControl
        ProgramControl ctrl = new ProgramControl(mockFH);

        // 4. Act
        ctrl.displayFilesListed();

        // 5. Assert Output
        String output = outContent.toString();
        assertTrue(output.contains("01 alpha.txt"));
        assertTrue(output.contains("02 beta.txt"));

        // 6. Verify Interaction
        verify(mockFH, times(1)).listFiles();
    }

    @Test
    void displayFilesListed_handlesEmptyFolder() {
        filehandler mockFH = mock(filehandler.class);
        when(mockFH.getFilenames()).thenReturn(Collections.emptyList());

        ProgramControl ctrl = new ProgramControl(mockFH);

        ctrl.displayFilesListed();

        String output = outContent.toString();
        assertTrue(output.contains("No files found"));
    }

    @Test
    void displayFileContents_printsFileContent() throws IOException {
        filehandler mockFH = mock(filehandler.class);

        // Setup the list so index 1 works
        doNothing().when(mockFH).listFiles();
        when(mockFH.getFilenames()).thenReturn(Arrays.asList("target.txt"));

        // Setup the content reading
        when(mockFH.readFile("target.txt")).thenReturn("Secret Message");

        ProgramControl ctrl = new ProgramControl(mockFH);

        // User asks for file "1"
        ctrl.displayFileContents(1);

        assertEquals("Secret Message" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void displayFileContents_handlesInvalidIndex() {
        filehandler mockFH = mock(filehandler.class);
        // List only has 1 file
        when(mockFH.getFilenames()).thenReturn(Arrays.asList("only_one.txt"));

        ProgramControl ctrl = new ProgramControl(mockFH);

        // User asks for file "5"
        ctrl.displayFileContents(5);

        String output = outContent.toString();
        assertTrue(output.contains("Error"));
        assertTrue(output.contains("range"));
    }

    @Test
    void displayFileContents_handlesReadError() throws IOException {
        filehandler mockFH = mock(filehandler.class);
        when(mockFH.getFilenames()).thenReturn(Arrays.asList("corrupt.txt"));

        // Force an IOException when reading
        when(mockFH.readFile("corrupt.txt")).thenThrow(new IOException("Disk Failure"));

        ProgramControl ctrl = new ProgramControl(mockFH);

        ctrl.displayFileContents(1);

        String output = outContent.toString();
        assertTrue(output.contains("Error reading file"));
        assertTrue(output.contains("Disk Failure"));
    }
}