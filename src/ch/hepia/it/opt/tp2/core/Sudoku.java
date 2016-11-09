package ch.hepia.it.opt.tp2.core;

/**
 * Created by Thomas on 09.11.16.
 */
public class Sudoku {

    public static void main(String[] args) {
        int[][] init = {
                {0, 0, 0, 0, 0, 0, 8, 1, 0},
                {0, 4, 0, 8, 0, 6, 0, 0, 0},
                {0, 0, 5, 0, 0, 2, 0, 9, 0},

                {8, 7, 0, 4, 0, 1, 9, 0, 0},
                {0, 0, 0, 0, 7, 0, 0, 0, 0},
                {0, 0, 4, 5, 0, 9, 0, 8, 3},

                {0, 6, 0, 1, 0, 0, 4, 0, 0},
                {0, 0, 0, 6, 0, 5, 0, 2, 0},
                {0, 2, 8, 0, 0, 0, 0, 0, 0},
        };

        SudokuState initalState = new SudokuState(init);
        System.out.println(initalState);
        System.out.println();
        SudokuState s = new Sudoku().PSCBacktracking(initalState);
        System.out.println(s);
    }

    public SudokuState PSCBacktracking(SudokuState A) {
        if(A.isComplete()) return A;
        SudokuState r;
        SudokuTile X = A.getMostConstraintVar();
        for (int i : X.getPossibleValues()) {
            X.setValue(i);
            if(A.isValid()) {
                r = PSCBacktracking(A);
                if(r != null) return r;
            }
        }
        return null;
    }
}
