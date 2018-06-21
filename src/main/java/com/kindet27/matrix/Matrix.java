package com.kindet27.matrix;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Function;

import static java.lang.Math.pow;

public class Matrix<E extends Number> {
    private final int rows;
    private final int columns;
    private Number[][] arr;

    private Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.arr = new Number[rows][columns];
    }

    public Matrix(int size) {
        this(size, size);
    }

    public Matrix(Scanner scanner, Function<Scanner, E> parser) {
        this(scanner.nextInt());
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                arr[i][j] = parser.apply(scanner);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public E getElement(int row, int column){
        return (E) arr[row][column];
    }

    public int getRows(){
        return rows;
    }

    public void  setElement(E element, int columns, int rows){
        this.arr[columns][rows] = element;
    }

    public void matrixSout(){
        System.out.println(columns);
        for (int i = 0; i < columns ; i++) {
            for (int j = 0; j < rows; j++) {
                System.out.print(arr[i][j]+ " ");
            }
        }
    }

    private Number[][] getMatrixMinor(Number[][] inArr, int column ){
        Number[][] minor = new Number[inArr.length-1][inArr.length-1];
        for (int i = 0; i < minor.length; i++) {
            for (int j = 0; j < minor.length; j++) {
                minor[i][j] = (column <= j )? inArr[i + 1][j + 1] : inArr[i+1][j];
            }
        }
        return minor;
    }

    public int calculateDeterminant(){
        return calcDeterminant(this.arr);
    }

    private int calcDeterminant(Number[][] inArr){
        int determinant = 0;
        if(inArr.length == 1){
            return (int) inArr[0][0];
        }
        else{
            for (int i = 0; i < inArr.length; i++) {
                determinant +=  pow(-1, i) * (int) inArr[0][i] * calcDeterminant(getMatrixMinor(inArr, i));
            }
        }
        return determinant;
    }

    public void matrixRowsSort(Comparator<Number[]> comparator){
        Arrays.sort(arr, comparator);
    }

    public void swapTwoRows(int row1, int row2){
        Number[] temp = arr[row1];
        arr[row1] = arr[row2];
        arr[row2] = temp;
    }
}