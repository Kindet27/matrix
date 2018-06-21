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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                arr[i][j] = parser.apply(scanner);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public E getElement(int row, int column) {
        return (E) arr[row][column];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Number[] getRowByInd(int ind) {
        return arr[ind];
    }

    public Number[] getColumnByInd(int ind) {
        Number[] column = new Number[rows];
        for (int i = 0; i < rows; i++) {
            column[i] = arr[i][ind];
        }
        return column;
    }

    public Integer[] convertToIntArr(Number[] numArr) {
        Integer[] inArr = new Integer[numArr.length];
        for (int i = 0; i < inArr.length; i++) {
            inArr[i] = (Integer) numArr[i];
        }
        return inArr;
    }

    public void setElement(E element, int columns, int rows) {
        this.arr[columns][rows] = element;
    }

    public void matrixSout() {
        System.out.println(rows);
        if (rows != columns) {
            System.out.println(columns);
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private Number[][] getMatrixMinor(Number[][] inArr, int column) {
        Number[][] minor = new Number[inArr.length - 1][inArr.length - 1];
        for (int i = 0; i < minor.length; i++) {
            for (int j = 0; j < minor.length; j++) {
                minor[i][j] = (column <= j) ? inArr[i + 1][j + 1] : inArr[i + 1][j];
            }
        }
        return minor;
    }

    public int calculateDeterminant() {
        return calcDeterminant(this.arr);
    }

    private int calcDeterminant(Number[][] inArr) {
        int determinant = 0;
        if (inArr.length == 1) {
            return (int) inArr[0][0];
        } else {
            for (int i = 0; i < inArr.length; i++) {
                determinant += pow(-1, i) * (int) inArr[0][i] * calcDeterminant(getMatrixMinor(inArr, i));
            }
        }
        return determinant;
    }

    public void matrixRowsSort(Comparator<Number[]> comparator) {
        Arrays.sort(arr, comparator);
    }

    public void swapTwoRows(int row1, int row2) {
        if (row1 == row2) {
            return;
        }
        Number[] temp = arr[row1];
        arr[row1] = arr[row2];
        arr[row2] = temp;
    }

    public void swapTwoColumns(int column1, int column2) {
        if (column1 == column2) {
            return;
        }
        for (int i = 0; i < rows; i++) {
            Number temp = arr[i][column1];
            arr[i][column1] = arr[i][column2];
            arr[i][column2] = temp;
        }
    }

    public Matrix<E> deleteRow(int rowToDelete) {
        Matrix<E> editedMatrix = new Matrix<>(this.rows - 1, this.columns);
        for (int i = 0; i < editedMatrix.rows; i++) {
            for (int j = 0; j < editedMatrix.columns; j++) {
                editedMatrix.setElement(this.getElement(i >= rowToDelete ? i + 1 : i, j), i, j);
            }
        }
        return editedMatrix;
    }

    public Matrix<E> deleteColumn(int columnToDelete) {
        Matrix<E> editedMatrix = new Matrix<>(this.rows, this.columns - 1);
        for (int i = 0; i < editedMatrix.rows; i++) {
            for (int j = 0; j < editedMatrix.columns; j++) {
                editedMatrix.setElement(this.getElement(i, j >= columnToDelete ? j + 1 : j), i, j);
            }
        }
        return editedMatrix;
    }
}