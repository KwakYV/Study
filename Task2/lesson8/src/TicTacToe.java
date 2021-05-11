import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;

public class TicTacToe extends JFrame {
    private static final int SIZE = 3;
    private static final String DOT_EMPTY = "*";
    private static final String DOT_X= "X";
    private static final String DOT_O = "O";
    private static final int limitForWin = 3;
    private static JLabel[][] map;
    private Object[] options = {"Yes", "No"};

    public TicTacToe(){
        setTitle("TicTacToe");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(300,300,400,400);
        setLayout(new GridLayout(SIZE, SIZE));
        initMap(SIZE);
        setVisible(true);
    }

    /**
     * Init map with empty values
     * @param size
     */
    private void initMap(int size) {
        map = new JLabel[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JLabel lbl = new JLabel(DOT_EMPTY, JLabel.CENTER);
                lbl.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        JLabel label = (JLabel)mouseEvent.getSource();
                        String currentLabel = label.getText();
                        if (DOT_EMPTY.equals(currentLabel)){
                            label.setText(DOT_X);
                            if (checkWin(DOT_X, limitForWin, map)){
                                int n = JOptionPane.showConfirmDialog(mouseEvent.getComponent(),
                                        "You win!!! Would you like to start game again?",
                                        "Game over!",
                                        JOptionPane.YES_NO_OPTION);
                                if (n == 0)
                                    setDotEmpty();
                                else
                                    dispose();
                            }else if (isFull()){
                                int n = JOptionPane.showConfirmDialog(mouseEvent.getComponent(),
                                        "Dead heat!!! Would you like to restart the game?",
                                        "Game over!",
                                        JOptionPane.YES_NO_OPTION);
                                if (n == 0)
                                    setDotEmpty();
                                else
                                    dispose();
                            }else{
                                System.out.println("Computer turn");
                                aiTurn();
                                if (checkWin(DOT_O, limitForWin, map)){
                                    int n = JOptionPane.showConfirmDialog(mouseEvent.getComponent(),
                                            "Computer win!!! Would you like to start game again?",
                                            "Game over!",
                                            JOptionPane.YES_NO_OPTION);
                                    if (n == 0)
                                        setDotEmpty();
                                    else
                                        dispose();
                                }

                                if (isFull()){
                                    int n = JOptionPane.showConfirmDialog(mouseEvent.getComponent(),
                                            "Dead heat!!! Would you like to restart the game?",
                                            "Game over!",
                                            JOptionPane.YES_NO_OPTION);
                                    if (n == 0)
                                        setDotEmpty();
                                    else
                                        dispose();
                                }
                            }

                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {
                        System.out.println("Mouse pressed");
                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {
                        System.out.println("Mouse released");
                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {
                        System.out.println("Mouse entered");
                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {
                        System.out.println("Mouse exited");
                    }


                });
                map[i][j] =  lbl;
                add(lbl);
            }
        }


    }

    private void setDotEmpty(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j].setText(DOT_EMPTY);
            }
        }
    }

    private  boolean isFull(){
        for (int i= 0; i < SIZE; i++) {
            for (int j =0; j < SIZE; j++){
                if (map[i][j].getText().equals(DOT_EMPTY))
                    return false;
            }

        }
        return true;
    }

    private void aiTurn() {
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

    private int[] getCellForWin(String dotX) {
        for (int rowIndex = 0; rowIndex < map.length; rowIndex++){
            for (int colIndex = 0; colIndex < map[rowIndex].length; colIndex++){
                if (map[rowIndex][colIndex].getText().equals(DOT_EMPTY) && isWinMoving(rowIndex, colIndex, dotX))
                    return new int[] {rowIndex, colIndex};
            }
        }
        return null;
    }

    private boolean isWinMoving(int rowIndex, int colIndex, String dotX) {
        setCell(rowIndex, colIndex, dotX);
        boolean isWin = checkWin(dotX, limitForWin, map);
        setCell(rowIndex, colIndex, DOT_EMPTY);
        return isWin;
    }

    private void setCell(int x, int y, String val){
        map[x][y].setText(val);
    }

    private int[] getRandomCell() {
        Random rand = new Random();
        int x, y;
        do{
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        }while (!map[x][y].getText().equals(DOT_EMPTY));

        return new int[] {x, y};
    }


    private boolean checkWin(String dotX, int lim, JLabel[][] aMap) {
        int rowCount = 0;
        int columnCount = 0;
        int leftDiagCount = 0;
        int rightDiagCount = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // check for rows
                if (aMap[i][j].getText().equals(dotX)){
                    rowCount+=1;
                    if (rowCount == lim)
                        return true;
                }else{
                    rowCount = 0;
                }

                // check for columns
                if (aMap[j][i].getText().equals(dotX)){
                    columnCount +=1;
                    if (columnCount == lim)
                        return true;
                }else{
                    columnCount = 0;
                }

            }

            // check for left diagonal
            if (aMap[i][i].getText().equals(dotX)){
                leftDiagCount += 1;
                if (leftDiagCount == lim)
                    return true;
            }else {
                leftDiagCount = 0;
            }

            // check for right diagonal
            if (aMap[i][SIZE-1-i].getText().equals(dotX)){
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

    public static void main(String[] args) {
        new TicTacToe();
    }

}
