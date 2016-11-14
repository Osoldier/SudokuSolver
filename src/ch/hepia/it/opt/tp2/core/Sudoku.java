package ch.hepia.it.opt.tp2.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thomas on 09.11.16
 */
public class Sudoku {

    public static void main(String[] args) {

        SudokuState initalState = new SudokuState(readFile("sudoku.txt"));
        System.out.println(initalState);

        Sudoku su = new Sudoku();
        long t1 = System.currentTimeMillis();
        SudokuState s = su.PSCBacktracking(initalState);
        System.out.println(System.currentTimeMillis()-t1);
        System.out.println(s);
    }

    public static int[][] readFile(String f) {
        int[][] state = new int[9][9];
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String line;
            int j = 0;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                for (String s : data) {
                    state[i][j] = Integer.parseInt(s);
                    i++;
                }
                i = 0;
                j++;
            }

            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return state;
    }

    public SudokuState PSCBacktracking(SudokuState A) {
        if(A.isComplete()) return A;
        SudokuState r;
        SudokuTile X = A.getMostConstraintVar();

        List<Integer> D = new ArrayList<>();
        D.addAll(X.getPossibleValues());
        for (int i : D) {
            A.setMostConstraitVarValue(i);
            if(A.isValid()) {
                r = PSCBacktracking(new SudokuState(A));
                if(r != null) return r;
            }
        }
        return null;
    }
}
