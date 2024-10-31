import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Sudoku {
    private final Integer[][] sudoku;
    private final boolean[][] predefinedValue;

    Sudoku() {
        sudoku = new Integer[9][9];
        predefinedValue = new boolean[9][9];
    }

    /**
     * Reads a sudoku from the file given by param sudokuFilePath and initialises the sudoku member variable.
     * @param sudokuFilePath The path of the file to read the sudoku.
     * @throws IOException In case the file could not be read.
     */
    public void readSudoku (String sudokuFilePath) throws IOException {

            BufferedReader bufReader = new BufferedReader(new FileReader(sudokuFilePath));

            String line;
            int i = 0;
            while ((line = bufReader.readLine()) != null) {
                //process the line
                if (line.equals("-------|-------|-------")) {
                    continue;
                }
                this.sudoku[i] = convertIntfromString(line);
                i++;
            }
            genPredefinedValueTable();
            bufReader.close();
        }

    /**
     * converts a string given by param line into an array of integer values.
     * @param line the string to be converted.
     * @return an array of values after converting.
     */
    private Integer[] convertIntfromString (String line)
        {
            char[] chars = line.toCharArray();
            int len = chars.length;
            Integer[] pos = new Integer[9];
            int j = 0;
            for (int i = 0; i < len; i++) {
                if (chars[i] == '.') {
                    pos[j] = 0;
                    j++;
                } else if (chars[i] == '1' || chars[i] == '2' || chars[i] == '3' || chars[i] == '4' || chars[i] == '5' || chars[i] == '6' || chars[i] == '7' || chars[i] == '8' || chars[i] == '9') {
                    pos[j] = Integer.valueOf(String.valueOf(chars[i]));
                    j++;
                }
            }
            return pos;
        }

    /**
     *genarates a table that identifies which values in a Sudoku puzzle are already provided as part od the initial puzzle.
     */
    private void genPredefinedValueTable ()
        {
            for (int i = 0; i < this.sudoku.length; i++) {
                for (int j = 0; j < this.sudoku.length; j++) {
                    predefinedValue[i][j] = this.sudoku[i][j] != 0;
                }
            }
        }

    /**
     * determines the potential numbers that can be placed in a specific cell of a Sudoku puzzle.
     * @param m represents the row of a specific cell in the Sudoku grid.
     * @param n represents the column of a specific cell in the Sudoku grid.
     * @return an  array of integer values, representing the numbers that could potentially go into cell (m, n).
     */
        private Integer[] possibleNumbers (int m, int n)
        {
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= 9; i++) {
                list.add(i);
            }

            for (int i = 0; i < this.sudoku.length; i++) {
                if (this.sudoku[m][i] == 0) {
                    continue;
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        if (Objects.equals(this.sudoku[m][i], list.get(j))) {
                            list.remove(j);
                        }
                    }
                }
            }

            for (Integer[] integers : this.sudoku) {
                if (integers[n] == 0) {
                    continue;
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        if (integers[n] == list.get(j)) {
                            list.remove(j);
                        }
                    }
                }
            }

            int quotZei = m / 3;
            int quoSpal = n / 3;

            for (int i = 3 * quotZei; i < 3 * quotZei + 3; i++) {
                for (int j = 3 * quoSpal; j < 3 * quoSpal + 3; j++) {
                    if (this.sudoku[i][j] != 0) {
                        for (int k = 0; k < list.size(); k++) {
                            if (Objects.equals(this.sudoku[i][j], list.get(k))) {
                                list.remove(k);
                            }
                        }
                    }
                }
            }

            return list.toArray(new Integer[0]);
        }

    /**
     * trys to place possible values in each cell and backtracking if no  valid number can be placed.
     * @param m represents the row of a specific cell in the Sudoku grid.
     * @param n represents the column of a specific cell in the Sudoku grid.
     * @return boolean value indicating whether the Sudoku puzzle can be solved.
     */
        private boolean isSolvableHelper (int m, int n){
            if (!(m == 8 && n == 9)) {
                if (n > 8) {
                    m++;
                    n = 0;
                }
                if (this.sudoku[m][n] > 0) {

                    return isSolvableHelper(m, n + 1);
                }
                Integer[] possibleNumbers = possibleNumbers(m, n);
                if (possibleNumbers.length != 0) {
                    for (Integer possibleNumber : possibleNumbers) {
                        this.sudoku[m][n] = possibleNumber;
                        if (isSolvableHelper(m, n + 1))
                            return true;
                    }
                    if (!predefinedValue[m][n]) {
                        this.sudoku[m][n] = 0;
                    }
                    return false;
                } else {
                    if (!predefinedValue[m][n]) {
                        this.sudoku[m][n] = 0;
                    }
                    return false;
                }
            }

            int belegt = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (this.sudoku[i][j] > 0) {
                        belegt++;
                    }
                }
            }

            return belegt == 81;
        }

    /**
     * displays the Sudoku puzzle.
     */
    public void printSudoku ()
        {
            for (int i = 0; i < this.sudoku.length; i++) {
                System.out.println(Arrays.toString(this.sudoku[i]));
            }
            System.out.println("\n");
        }

    /**
     * starts solving the Sudoku puzzle.
     * @return true if a solution found and false otherwise.
     */
    public boolean solve()
        {
            return isSolvableHelper(0, 0);
        }
    }

