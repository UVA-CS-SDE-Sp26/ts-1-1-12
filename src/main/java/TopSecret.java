/**
 * Commmand Line Utility
 */

public class TopSecret {
    static ProgramControl control = new ProgramControl();
    public static void main(String[] args) {
        if (args.length == 0){
            control.displayFilesListed();
            return;
        }
        if(args.length >= 2){
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

        control.displayFileContents(fileIndex); // if arg length == 1
    }

}
