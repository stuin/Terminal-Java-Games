
public class Sweep {
    public Sweep(int thisX, int thisY, boolean clear) {
        MineSweeper.visited[thisX][thisY] = true;
        for(int x = thisX - 1; x <= thisX + 1; x++) if(x >= 0 && x < MineSweeper.size) for(int y = thisY - 1; y <= thisY + 1; y++) if(y >= 0 && y < MineSweeper.size && (y != thisY || x != thisX)){
            if(MineSweeper.mineField[x][y] > 9 && clear) {
                MineSweeper.mineField[x][y] -= 10;
                MineSweeper.covered--;
            }
            if((MineSweeper.mineField[x][y] == 0 || MineSweeper.mineField[x][y] == 10) && !MineSweeper.visited[x][y]) new Sweep(x, y, true);
        }
    }
}
