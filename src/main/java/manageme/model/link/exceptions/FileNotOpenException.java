package manageme.model.link.exceptions;

public class FileNotOpenException extends RuntimeException {

    /**
     * Signals the file opening process failed and shows the error message from the terminal when trying to open a file
     * in linux.
     */
    public FileNotOpenException(String message) {
        super("Cannot open file with the following error:\n" + message
                + "\nPlease make sure both the file to open and the program used for opening the file exist.");
    }

    /**
     * Signals the file opening process failed.
     */
    public FileNotOpenException() {
        super("Cannot open file. Please make sure both the file to open and the program used for opening the file "
                + "exist.");
    }
}
