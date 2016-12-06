package com.company;

/**
 * Created by benz on 17.11.2016.
 * LAB_2D
 */

class Matrix {

    private int row, col;
    private double[][] matrix;

    Matrix(int row, int col) {
        this.row = row;
        this.col = col;

        matrix = new double[row][col];
    }

    Matrix(int rowCount, int columnCount, double[] vector) {

        row = rowCount;
        col = columnCount;
        matrix = new double[row][col];

        int k = 0;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                matrix[i][j] = vector[k];
                k++;
            }
        }
    }

    Matrix(Matrix matrixToCopy) {
        this.row = matrixToCopy.row;
        this.col = matrixToCopy.col;

        matrix = matrixToCopy.matrix;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    double[][] getMatrix() {
        return matrix;
    }

    Matrix pow(Matrix matrix) {

        Matrix res = new Matrix(row, matrix.col);

        res.row = this.row;
        res.col = matrix.col;

        if (col == matrix.row) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < matrix.col; j++) {
                    for (int k = 0; k < matrix.row; k++) {
                        res.matrix[i][j] += this.matrix[i][k] * matrix.matrix[k][j];
                    }
                }
            }
        } else throw new Error("ЧЕТ НЕ ТАК");

        return res;
    }
}
