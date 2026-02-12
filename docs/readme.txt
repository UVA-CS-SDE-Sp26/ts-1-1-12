TopSecret documentation starter file

PROGRAM ENTRY POINT IS TOPSECRET CLASS
RUN TOPSECRET FROM DOCS FOLDER WITH COMMAND (JAVA TOPSECRET)
DATA FOLDER MUST BE IN SRC/MAIN/JAVA


A: asks for user input and return a string
if no arguemtn, display files
if argyuemtn take it and pass along

public class TopSecret {
    public static void main(String[] args) {
        if arg.length == 0
            string[] files = getFiles();
            print files
        else:
            check if valid input(type is correct)
                arg[1] pass to program control getSpecificFileName(), should return the file contents
            non valid input, throw an error
    }
}

program control class:

getSpecificFileName(int fileIndex):
    string[] files = getFiles();
    call getFileContent(string FileName)


