import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

class TopSecretTest {

    @Test
    void noArguments_callsDisplayFilesListed() {
        // 1. Create a mock ProgramControl
        ProgramControl mockControl = mock(ProgramControl.class);

        // 2. Inject it into TopSecret
        TopSecret.control = mockControl;

        // 3. Call main as usual
        TopSecret.main(new String[]{});

        // 4. Verify expected interactions
        verify(mockControl, times(1)).displayFilesListed();
        verify(mockControl, never()).displayFileContents(anyInt());

        // 5. Reset static field to avoid affecting other tests
        TopSecret.control = null;
    }
    @Test
    void main1Argument() {
        ProgramControl mockControl = mock(ProgramControl.class);
        TopSecret.control = mockControl;


        TopSecret.main(new String[]{"1"});

        verify(mockControl, times(1)).displayFileContents(1);
        verify(mockControl, never()).displayFilesListed();

        TopSecret.control = null; // reset
    }
    @Test
    void mainInvalidArgument() {
        ProgramControl mockControl = mock(ProgramControl.class);
        TopSecret.control = mockControl;


        TopSecret.main(new String[]{"a"}); // non-integer

        verify(mockControl, never()).displayFilesListed();
        verify(mockControl, never()).displayFileContents(anyInt());

        TopSecret.control = null; // reset
    }
    @Test
    void main2Argument() {
        ProgramControl mockControl = mock(ProgramControl.class);
        TopSecret.control = mockControl;

        TopSecret.main(new String[]{"1", "2"}); // too many args

        verify(mockControl, never()).displayFilesListed();
        verify(mockControl, never()).displayFileContents(anyInt());

        TopSecret.control = null; // reset
    }
}