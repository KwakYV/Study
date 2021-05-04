import org.w3c.dom.ls.LSInput;

import java.security.Signature;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static final int SIZE = 5;
    private static final char DOT_EMPTY = '*';
    private static final char DOT_X= 'X';
    private static final char DOT_O = 'O';
    private static final int limitForWin = 4;
    private static char[][] map;

    public static void main(String[] args) {
        initMap(SIZE);
        printMap(map);
//        map[0][0] = 'Y'; map[0][1] = 'X'; map[0][2] = 'Y';
//        map[1][0] = 'X'; map[1][1] = 'X'; map[1][2] = 'X';
//        map[2][0] = 'X'; map[2][1] = 'X'; map[2][2] = 'X';

//        map[0][0] = 'Y'; map[1][0] = 'X'; map[2][0] = 'Y';
//        map[0][1] = 'X'; map[1][1] = 'X'; map[2][1] = 'X';
//        map[0][2] = 'X'; map[1][2] = 'X'; map[2][2] = 'X';

//        map[0][0] = 'X'; map[1][1] = 'Y'; map[2][2] = 'Y';
//        map[0][2] = 'Y'; map[1][1] = 'X'; map[2][0] = 'Y';
        while (true){
            playerTurn();
            printMap(map);
            if (checkWin(DOT_X, limitForWin, map)){
                System.out.println("Player win");
                break;
            }

            if (isFull()){
                System.out.println("No one wins");
                break;
            }
            aiTurn();
            printMap(map);
            if (checkWin(DOT_O, limitForWin, map)){
                System.out.println("Computer win");
                break;
            }

            if (isFull()){
                System.out.println("No one wins");
                break;
            }

        }
        System.out.println("Game over!!!");
    }

    private static void aiTurn() {
        System.out.println("Ход компьютера");
        int[] cell = getCellForWin(DOT_O);
        if (cell == null){
            cell = getCellForWin(DOT_X);
            if (cell == null){
                cell = getRandomCell();
            }
        }
        setCell(cell[0],cell[1], DOT_O);
    }

    private static int[] getCellForWin(char dotX) {
        for (int rowIndex = 0; rowIndex < map.length; rowIndex++){
            for (int colIndex = 0; colIndex < map[rowIndex].length; colIndex++){
                if (map[rowIndex][colIndex] == DOT_EMPTY && isWinMoving(rowIndex, colIndex, dotX))
                    return new int[] {rowIndex, colIndex};
            }
        }
        return null;
    }

    private static boolean isWinMoving(int rowIndex, int colIndex, char dotX) {
        setCell(rowIndex, colIndex, dotX);
        boolean isWin = checkWin(dotX, limitForWin, map);
        setCell(rowIndex, colIndex, DOT_EMPTY);
        return isWin;
    }

    private static void setCell(int x, int y, char val){
        map[x][y] = val;
    }

    private static int[] getRandomCell() {
        Random rand = new Random();
        int x, y;
        do{
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        }while (map[x][y] != DOT_EMPTY);

        return new int[] {x, y};
    }

    private static boolean checkWin(char dotX, int lim, char[][] aMap) {
        int rowCount = 0;
        int columnCount = 0;
        int leftDiagCount = 0;
        int rightDiagCount = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // check for rows
                if (aMap[i][j] == dotX){
                    rowCount+=1;
                    if (rowCount == lim)
                        return true;
                }else{
                    rowCount = 0;
                }

                // check for columns
                if (aMap[j][i] == dotX){
                    columnCount +=1;
                    if (columnCount == lim)
                        return true;
                }else{
                    columnCount = 0;
                }

            }

            // check for left diagonal
            if (aMap[i][i] == dotX){
                leftDiagCount += 1;
                if (leftDiagCount == lim)
                    return true;
            }else {
                leftDiagCount = 0;
            }

            // check for right diagonal
            if (aMap[i][SIZE-1-i] == dotX){
                rightDiagCount += 1;
                if (rightDiagCount == lim)
                    return true;
            }
            else{
                rightDiagCount = 0;
            }

            rowCount = 0;
            columnCount = 0;

        }

        return false;
    }

    /**
     * player turn to put X om board
     */
    private static void playerTurn() {
        System.out.println("Ход игрока. Введите координаты X и Y через пробел");
        Scanner scanner = new Scanner(System.in);
        int x;
        int y;
        while (true) {
            x = scanner.nextInt();
            y = scanner.nextInt();

            if (x < 1 || y < 1 || x > SIZE || y > SIZE){
                System.out.println("Вводимые координаты должны быть в пределах от 1 до " + SIZE);
                scanner.nextLine();
                continue;
            }
            x = x - 1;
            y = y - 1;
            if (map[x][y] == DOT_EMPTY){
                map[x][y] = DOT_X;
                break;
            }else{
                System.out.println("Данная ячейка занята");
                scanner.nextLine();
                continue;
            }
        }
    }

    /**
     * Print current state of game map
     * @param aMap
     */
    private static void printMap(char[][] aMap) {
        for (int i = 0; i <=SIZE ; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < aMap.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(aMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Init map with empty values
     * @param size
     */
    private static void initMap(int size) {
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(map[i], DOT_EMPTY);
        }
    }

    private static boolean isFull(){
        for (int i= 0; i < SIZE; i++) {
            for (int j =0; j < SIZE; j++){
                if (map[i][j] == DOT_EMPTY)
                    return false;
            }

        }
        return true;
    }
}
