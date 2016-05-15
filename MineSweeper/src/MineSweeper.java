
import java.util.*;

public class MineSweeper {
    int size;
    int mines;
    int covered;
    int[][] mineField;
    boolean inGame = true;
    GridPrint gridPrint = new GridPrint();

    public void Derive(int[][] field, int Size, Scanner scanner) {
        size = Size;
        mineField = field;
        mines = 0;
        covered = size * size;
        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++) {
                if(mineField[x][y] == 9) mines++;
                mineField[x][y] += 10;
            }

        String[] set = new String[size];
        for(int i = 0; i < size; i++) set[i] = "" + (char) (i + 65);
        gridPrint.column = set;
        set = new String[size];
        for(int i = 0; i < size; i++) {
            if(i > 9) set[i] = "" + i;
            else set[i] = i + " ";
        }
        gridPrint.row = set;
        while(inGame) {
            SetGrid();
            System.out.print("Select Cell: ");
            String s = scanner.nextLine();
            gridPrint.Select(s.toUpperCase());
            NextTurn();
        }
    }

    public void SetGrid() {
        gridPrint.content = new String[size][size];
        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++) {
                if(mineField[x][y] == 0) gridPrint.content[x][y] = ".";
                else if(mineField[x][y] == 9) gridPrint.content[x][y] = "*";
                else if(mineField[x][y] > 9) gridPrint.content[x][y] = "_";
                else gridPrint.content[x][y] = "" + mineField[x][y];
            }
        gridPrint.Generate();
    }

    public void NextTurn() {
        int i = mineField[gridPrint.selectX][gridPrint.selectY];
        i -= 10;
        if(i == 9) {
            System.out.println();
            System.out.println("  You Lose!  ");
            for(int x = 0; x < size; x++) for(int y = 0; y < size; y++) if(mineField[x][y] > 9) mineField[x][y] -= 10;
            mineField[gridPrint.selectX][gridPrint.selectY] = i;
            inGame = false;
            SetGrid();
        } else {
            int y = gridPrint.selectY;
            boolean moving = true;
            int dirY = 1;
            int dirX = 1;
            while(dirY != 0) {
                while(gridPrint.contains(gridPrint.selectX, y) && moving && mineField[gridPrint.selectX][y] != 19) {
                    if(mineField[gridPrint.selectX][y] > 9) {
                        mineField[gridPrint.selectX][y] -= 10;
                        covered--;
                    }
                    dirX = 1;
                    while(dirX != 0) {
                        moving = true;
                        int x = gridPrint.selectX + dirX;
                        while(gridPrint.contains(x, y) && moving && mineField[x][y] != 19) {
                            if(mineField[x][y] > 9) {
                                mineField[x][y] -= 10;
                                covered--;
                            }
                            if(mineField[x][y] == 0) x += dirX;
                            else moving = false;
                        }
                        if(dirX == 1) dirX = -1;
                        else dirX = 0;
                    }
                    moving = false;
                    if(mineField[gridPrint.selectX][y] == 0) {
                        y += dirY;
                        if(gridPrint.contains(gridPrint.selectX, y)) moving = true;
                    }
                }
                if(dirY == 1) {
                    dirY = -1;
                    y = gridPrint.selectY - 1;
                } else dirY = 0;
            }
            if(covered == mines) {
                System.out.println();
                System.out.println("  You Win!  ");
                for(int x = 0; x < size; x++) for(y = 0; y < size; y++) if(mineField[x][y] > 9) mineField[x][y] -= 10;
                inGame = false;
                SetGrid();
            }
        }
    }
}