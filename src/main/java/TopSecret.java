/**
 * Commmand Line Utility
 */

public class TopSecret {
    static ProgramControl control;
    static ProgramControl getControl() {
        if (control == null) {
            control = new ProgramControl();
        }
        return control;
    }
    public static void main(String[] args) {
        if (args.length == 0){
            getControl().displayFilesListed();
            return;
        }
        if(args.length > 2) {
            System.err.println("Error too many arguments");
            return;
        }

        int fileIndex;
        try{
            fileIndex = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            System.err.println("Error in file index");
            return;
        }
        if(args.length == 2){
            String key = args[1];
        }
        getControl().displayFileContents(fileIndex); // if arg length == 1
    }

}
