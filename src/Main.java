import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    /**
     * innitializes a Sudoku object, reads the sudoku puzzle from a file, solves it and prints the results.
     * @param args receives any command-line arguments passed to the program.
     * @throws IOException for any issues in file input/output to be handled.
     */
    public static void main(String[] args) throws IOException {
        Sudoku sudoku1 = new Sudoku();
        sudoku1.readSudoku("/home/ngoc/Documents/Projects/Projekt1/text");
        sudoku1.printSudoku();

        sudoku1.solve();
        sudoku1.printSudoku();
    }
}