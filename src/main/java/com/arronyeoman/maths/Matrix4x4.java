package com.arronyeoman.maths;

public class Matrix4x4 {

float[][] matrix;

    public Matrix4x4 () {
        float[][] matrix = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = 0.0f;
            }
        }
    }

    public Matrix4x4 identity() {
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

    public Matrix4x4(float[] values) {
        if (values.length != 16) {
            throw new IllegalArgumentException("Matrix4x4 must have 16 values");
        }
        float[][] matrix = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = values[(i * 4) + j];
            }
        }
    }

    public float get(int x, int y){
        return matrix[x][y];
    }

    public float[] getAll() {
        float[] values = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                values[(i * 4) + j] = matrix[i][j];
            }
        }
        return values;
    }

    // @TODO: simplifyn this, we are converting a matrix to float
    // then converting it back to a matrix, to be passed to a function that
    // will convert it back to a float array to be passed to a shader.
    public Matrix4x4 swapMajor() {
        float[] array = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array[i*4 +j] =matrix[j][i];
            }
        }
        Matrix4x4 temp = new Matrix4x4(array);
        return temp;
    }

    

    public Matrix4x4 getMatrix() {
        return this;
    }

    public Matrix4x4 setMatrix(float[][] matrix) {
        this.matrix = matrix;
        return this;
    }
}