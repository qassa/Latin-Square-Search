package latinsquaresearch;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LatinSquareSearch {
    public static int m; //размерность
    
    public static int innerSearch(int squares, int position, List<elemParams> changable, List<elemParams> fixed, int[][] curr_square){       
        elemParams currElem = changable.get(position);
        int row = currElem.row;
        int col = currElem.col;
            
        for(int i=1;i<=m;i++){

            boolean cflag = false;
                       
            //очистка последующей матрицы для прогона всех возможных вариантов
            if (curr_square[row][col] != i && curr_square[row][col] != 0){
                for (int clrPos = position; clrPos < changable.size(); clrPos++){
                    currElem = changable.get(clrPos);
                    curr_square[currElem.row][currElem.col] = 0;
                }        
            }       

            //проверка на дублирование числа в строке
            if (checkRow(i,curr_square,row) == true) cflag = true;

            //проверка на дублирование числа в колонке
            if (checkCol(i,curr_square,col) == true) cflag = true;  
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
            squares = innerSearch(squares, position+1, changable, fixed, curr_square);
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
    
    //проверить колонку на дублирование числа
    public static boolean checkCol(int i, int[][] customSquare, int colStart){
        for(int g=0;g<m;g++){
            int grid_row = customSquare[g][colStart];
                if(grid_row == i){
                    return true;
                }
        }
        return false;
    }
    
    //проверить строку на дублирование числа
    public static boolean checkRow(int i, int[][] customSquare, int rowStart){
        for(int g=0;g<m;g++){
        int grid_row = customSquare[rowStart][g];
            if(grid_row == i){
                return true;
            }
        }
        return false;
    }
    
    //проверка введенного частичного квадрата на дублирование в строках и столбцах
    public static boolean duplicates(int[] separateRow){
        Set<Integer> lump = new HashSet<>();
        for (int i : separateRow){
            if (i!=0 && lump.contains(i)) return true;
            lump.add(i);
        }
        return false;
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
        public int row;
        public int col;
        
        public elemParams(int value, int row, int col){
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
        
        //проверка частичного квадрата на корректность (отсутствие дубликатов чисел)
        boolean incorrect = false;
        int [] rowInts = new int[m];
        for (int i=0; i< m; i++){
            for (int j=0; j< m; j++){
                rowInts[j] = customSquare[i][j];
            }
            if (duplicates(rowInts)) incorrect = true;
        }
        
        for (int i=0; i< m; i++){
            for (int j=0; j< m; j++){
                rowInts[j] = customSquare[j][i];
            }
            if (duplicates(rowInts)) incorrect = true; 
        }
        if (incorrect){
            System.out.println("Некорректный частичный квадрат - дубликаты в колонке или столбце");   
            return;
        }
        
        //поиск первого элемента, которому присвоено значение 0 /т.е. не присвоено значение
        boolean ex = false;
        int rowStart = 0;
        int colStart = 0;
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
        
        //заполнение изменяемой части массива
        List<elemParams> changable = new ArrayList();
        for (int i=0; i< squareSize; i++){
            for (int j=0; j< squareSize; j++){
                if (customSquare[i][j] == 0 && (i != rowStart || j != colStart)){
                    changable.add(new elemParams(customSquare[i][j], i, j));        
                }
            }
        }     
          
        //доступные для перебора числа в общем цикле
        List<Integer> kk = new ArrayList();
        
        for (int i = 1; i<=m; i++){
            boolean cflag = false;
            
            if (checkRow(i,customSquare,rowStart) == true) cflag = true;
            if (checkCol(i,customSquare,colStart) == true) cflag = true;
            
            if (cflag == false)
                kk.add(i);
        }
                  
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
               squaresSum += innerSearch(0,0,changable,fixed,currSquare);
            }
        }
        System.out.println("Найдено "+squaresSum+" возможных комбинаций латинских квадратов");  
    } 
}
