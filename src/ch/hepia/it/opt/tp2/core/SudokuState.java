package ch.hepia.it.opt.tp2.core;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Thomas on 09.11.16.
 */
public class SudokuState {

    private SudokuTile[][] tiles;
    private boolean valid = false;

    public SudokuState(int[][] tiles) {
        this.tiles = new SudokuTile[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.tiles[i][j] = new SudokuTile();
                this.tiles[i][j].setValue(tiles[i][j]);
            }
        }
        update();
    }

    public boolean isComplete() {
        if(isValid()) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if(tiles[i][j].getValue() == 0) return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isValid() {
        update();
        return valid;
    }

    public boolean update() {
        //For each tiles remove the forbiden values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tiles[i][j].resetValues();
                List<Integer> occupiedSquare = getSquareNumbers(i, j);
                List<Integer> occupiedLR = getLineRowNumbers(i, j);

                for (Integer v : occupiedSquare) {
                    tiles[i][j].getPossibleValues().remove(v);
                    if(v == tiles[i][j].getValue()) {
                        valid = false;
                        return false;
                    }
                }

                for (Integer v : occupiedLR) {
                    tiles[i][j].getPossibleValues().remove(v);
                    if(v == tiles[i][j].getValue()) {
                        valid = false;
                        return false;
                    }
                }
            }
        }
        valid = true;
        return true;
    }


    private List<Integer> getSquareNumbers(int x, int y) {
        List<Integer> values = new ArrayList<>(9);
        int x1 = (int)Math.floor(x/3)*3;
        int y1 = (int)Math.floor(y/3)*3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int t = tiles[x1+i][y1+j].getValue();
                if(t != 0 && x1 != y && y1 != y) {
                    values.add(t);
                }
            }
        }
        return values;
    }
    
    private List<Integer> getLineRowNumbers(int k, int l) {
        List<Integer> values = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            if(tiles[k][i].getValue() != 0 && i != l) {
                values.add(tiles[k][i].getValue());
            }

            if(tiles[i][l].getValue() != 0 && !values.contains(tiles[i][l].getValue()) && i != k) {
                values.add(tiles[i][l].getValue());
            }
        }
        return values;
    }

    public SudokuTile getMostConstraintVar() {
        SudokuTile most = null;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(most == null || tiles[i][j].getPossibleValues().size() < most.getPossibleValues().size()) {
                    most = tiles[i][j];
                }
            }
        }
        return most;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s.append(tiles[i][j].getValue()+" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
