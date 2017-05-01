package latinsquaresearch;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class LatinSquareSearch {
    public static int m; //размерность
    
    public static int innerSearch(int squares, int position, List<elemParams> changable, List<elemParams> fixed, int[][] curr_square, int[][] custom_square){       
        for(int i=1;i<=m;i++){

            boolean cflag = false;
            
            elemParams currElem = changable.get(position);
            int row = currElem.row;
            int col = currElem.col;
            
            //очистка последующей матрицы для прогона всех возможных вариантов
            if (curr_square[row][col] != i && curr_square[row][col] != 0){
                for (int clrPos = position; clrPos < changable.size(); clrPos++){
                    currElem = changable.get(clrPos);
                    curr_square[currElem.row][currElem.col] = 0;
                }        
            }       
            
            //проверка на дублирование числа в строке
            for(int g=0;g<m;g++){
                int grid_row = curr_square[row][g];
                if(grid_row == i){
                    cflag = true;
                    break;
                }
            }
            
            //проверка на дублирование числа в колонке
            for(int g=0;g<m;g++){
                int grid_row = curr_square[g][col];
                if(grid_row == i){
                    cflag = true;
                    break;
                }
            }
            
            if (cflag == true) continue;
            curr_square[row][col] = i;
            
            int isLatin = 0;
            for (int ii=0;ii<m;ii++){
                for (int jj=0;jj<m;jj++){
                    if( curr_square[ii][jj] != 0)
                        isLatin++;
                }
            }
            
            if (isLatin == m*m){
            System.out.println("Найден латинский квадрат\n");
            for (int ii=0;ii<m;ii++){
                for (int jj=0;jj<m;jj++){
                    System.out.print(curr_square[ii][jj]+" ");
                }
                System.out.println("");
            }
            System.out.println("");
            
            squares = squares +1;
            return squares;
            }
            
            squares = innerSearch(squares, position+1, changable, fixed, curr_square, custom_square);
            

        }
        
        return squares;
    }
    
    //извлечь номер строки из порядкового номера
    public static int getRow(int position){
        int row = (int) Math.floor(position /  m);
            if ((int) position % m ==0)
                row  = row - 1;
        return row;
    }
    
    //извлечь номер колонки из порядкового номера
    public static int getCol(int position){
        int row = getRow(position);
        int col = (position - row * m) -1;
        return col;
    }
    
    //получить позицию при известных строке и колонке
    public static int getPosition(int row, int col){
        int position = row * m + (col + 1);
        return position;
    }
    
    public static int[][] initializeArray(int[][] customSquare){
        int [][] currSquare = new int[m][m];
        for (int i=0; i< m; i++){
            for (int j=0; j< m; j++){
                currSquare[i][j] = customSquare[i][j];
            }
        }
        return currSquare;
    }
    
    //описание для хранения элемента квадрата отдельно от матрицы
    public static class elemParams{
        public int value;
        public int row;
        public int col;
        
        public elemParams(int value, int row, int col){
            this.value = value;
            this.row = row;
            this.col = col;
        }
    }
    
    public static void main(String[] args) {
        Scanner squareIn = new Scanner(System.in);
        int squareSize = squareIn.nextInt();
        m = squareSize;
        
        int [][] customSquare = new int[squareSize][squareSize];
        int [][] currSquare = new int[squareSize][squareSize];
        
        for (int i=0; i< squareSize; i++){
            for (int j=0; j< squareSize; j++){
                customSquare[i][j] = squareIn.nextInt();
                if ((customSquare[i][j] < 0) || customSquare[i][j] > m){
                    System.out.println("Некорректный ввод");
                    j--;
                }
            }
        }
        
        int squaresSum = 0;
        
        //поиск первого элемента, которому присвоено значение 0 /т.е. не присвоено значение
        boolean ex = false;
        int rowStart = 0;
        int colStart = 0;
        int posStart = 0;
        for (int i=0; i< squareSize; i++){
            for (int j=0; j< squareSize; j++){
                if(customSquare[i][j] == 0){
                    rowStart = i;
                    colStart = j;
                    ex = true;
                    break;
                }
            }
            if (ex == true) break;
        }
        posStart = getPosition(rowStart, colStart);
        
        //заполнение изменяемой части массива
        List<elemParams> changable = new ArrayList();
        for (int i=0; i< squareSize; i++){
            for (int j=0; j< squareSize; j++){
                if (customSquare[i][j] == 0 && (i != rowStart || j != colStart)){
                    changable.add(new elemParams(customSquare[i][j], i, j));
                    System.out.println("Найдено "+i+" "+j);          
                }
            }
        } 
        System.out.println("Строка начальная"+rowStart);
                System.out.println("Колонка начальная"+colStart);
                  System.out.println(changable);      
          
        //доступные для перебора числа в общем цикле
        List<Integer> kk = new ArrayList();
        
        for (int i = 1; i<=m; i++){
            boolean cflag = false;
            for(int g=0;g<m;g++){
                int grid_row = customSquare[rowStart][g];
                    if(grid_row == i){
                        System.out.println("В строке уже присутствует это число");
                        cflag = true;
                        break;
                    }
                }
            
            for(int g=0;g<m;g++){
                int grid_row = customSquare[g][colStart];
                    if(grid_row == i){
                        System.out.println("В колонке уже присутствует это число");
                        cflag = true;
                        break;
                    }
            }
            if (cflag == false)
                kk.add(i);
        }
                  System.out.println(kk);
                  
        if (kk.size() == 1 && changable.isEmpty()){
            customSquare[rowStart][colStart] = kk.get(0);
            System.out.println("Найден латинский квадрат\n");
            for (int ii=0;ii<m;ii++){
                for (int jj=0;jj<m;jj++){
                    System.out.print(customSquare[ii][jj]+" ");
                }
                System.out.println("");
            }
            System.out.println("");
            squaresSum ++;
        }
        else{
            for(int k : kk){
                customSquare[rowStart][colStart] = k;
                currSquare = initializeArray(customSquare);

                //заполнение неизменяемой части массива
                List<elemParams> fixed = new ArrayList();
                for (int i=0; i< squareSize; i++){
                    for (int j=0; j< squareSize; j++){
                        if (customSquare[i][j] != 0){
                            fixed.add(new elemParams(customSquare[i][j], i, j));
                        }
                   }
               } 

                squaresSum += innerSearch(0,0,changable,fixed,currSquare,customSquare);
            }
        }
        
        System.out.println("Найдено "+squaresSum+" возможных комбинаций латинских квадратов");
        
    }
    
}
