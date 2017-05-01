package latinsquaresearch;

import java.util.Scanner;
import java.util.ArrayList;

public class LatinSquareSearch {
    public static int m; //размерность
    
    public static int innerSearch(int squares, int curr_position, int curr_digit, int[][] curr_grid){        
//        if (curr_position == m*m+1 && curr_digit == 10){
//            ;
//        }
        
        if (curr_position == m*m+1){
            return squares+1;
        }
        
        if (curr_digit == m){
            int row = ((curr_position+1) /  m) -1;
            int col = (curr_position+1) - row * m;
            curr_grid[row][col] = 1;
            
            innerSearch(squares, curr_position+1, 1, curr_grid);        

        }
        
        for(int i=curr_digit;i<=m;i++){
            int row = (int) Math.floor(curr_position /  m);
            if ((int) curr_position % m ==0)
                row  = row - 1;
            
            int col = (curr_position - row * m) -1;
            
            System.out.println("Digit "+curr_digit);
            System.out.println("Square num "+curr_position);
            
//            ArrayList<Integer> al = new ArrayList<Integer>();
//            for (int g=1;g<=9;g++){
//                al.add(g);
//            }
            
//            for(int g=0;g<m;g++){
//                int grid_row = curr_grid[row][g];
//                if(grid_row != 0)
//                    al.remove(grid_row);
//            }
            
            for(int g=0;g<m;g++){
                int grid_row = curr_grid[row][g];
                if(grid_row == i)
                    return squares;
            }
            
            for(int g=0;g<m;g++){
                int grid_row = curr_grid[g][col];
                if(grid_row == i)
                    return squares;
            }
            
            curr_grid[row][col] = curr_digit;
            System.out.println("Найдено квадратов "+squares+" текущая позиция "+curr_position+" текущее число "+curr_digit);
            innerSearch(squares, curr_position, curr_digit +1, curr_grid);
            
        }
        return squares;
    }
    
    public static void main(String[] args) {
        Scanner squareIn = new Scanner(System.in);
        int squareSize = squareIn.nextInt();
        m = squareSize;
        
        int [][] customSquare = new int[squareSize][squareSize];
        
        for (int i=0; i< squareSize; i++){
            for (int j=0; j< squareSize; j++){
                customSquare[i][j] = 0;
            }
        }
        
//        for (int i=0; i< squareSize; i++){
//            for (int j=0; j< squareSize; j++){
//                customSquare[i][j] = squareIn.nextInt();
//            }
//        }
        
        int squaresSum = 0;
        
        for(int k=1;k<=m;k++){
            customSquare[0][0] = k;
            squaresSum = squaresSum + innerSearch(0,2,1,customSquare);
        }
        
        System.out.println("Найдено "+squaresSum+" возможных комбинаций латинских квадратов");
        
    }
    
}
