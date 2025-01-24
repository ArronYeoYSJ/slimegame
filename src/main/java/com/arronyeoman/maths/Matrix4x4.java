package com.arronyeoman.maths;

public class Matrix {

    public Matrix () {
        float[][] matrix = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = 0.0f;
            }
        }
    }

    public Matrix identity() {
        float[][] matrix = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = 0.0f;
            }
        }
        for (int i = 0; i < 4; i++) {
            matrix[i][i] = 1.0f;
        }
        return this;
    }

    public Matrix(float[] values, int width, int height) {
        float[][] matrix = new float[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = values[(i * height) + j];
            }
        }
    }
}