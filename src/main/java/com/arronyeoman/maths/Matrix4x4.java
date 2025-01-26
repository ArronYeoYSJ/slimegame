package com.arronyeoman.maths;

public class Matrix4x4 {

private float[][] matrix;

    public Matrix4x4 () {
        matrix = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = 0.0f;
            }
        }
    }

    public Matrix4x4 identity() {
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

    public Matrix4x4 rotate(float angle, Vector4 axis){
        Matrix4x4 result = new Matrix4x4().identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        float oneLessCos = 1.0f - cos;
        float axisX = axis.getX();
        float axisY = axis.getY();
        float axisZ = axis.getZ();

        result.matrix[0][0] = cos   + axisX * axisX      * oneLessCos;
        result.matrix[0][1] = axisX * axisY * oneLessCos - axisZ * sin;
        result.matrix[0][2] = axisX * axisZ * oneLessCos + axisY * sin;
        result.matrix[1][0] = axisY * axisX * oneLessCos + axisZ * sin;
        result.matrix[1][1] = cos   + axisY * axisY      * oneLessCos;
        result.matrix[1][2] = axisY * axisZ * oneLessCos - axisX * sin;
        result.matrix[2][0] = axisZ * axisX * oneLessCos - axisY * sin;
        result.matrix[2][1] = axisZ * axisY * oneLessCos + axisX * sin;
        result.matrix[2][2] = cos   + axisZ * axisZ      * oneLessCos;


        return result;
    }

    public Matrix4x4 createScalar(Vector4 scale){
        Matrix4x4 result = new Matrix4x4().identity();
        result.matrix[0][0] = scale.getX();
        result.matrix[1][1] = scale.getY();
        result.matrix[2][2] = scale.getZ();
        return result;
    }
    
    public Matrix4x4 translate(Vector4 translation){
        Matrix4x4 result = new Matrix4x4().identity();
        result.matrix[0][3] = translation.getX() + matrix[0][3];
        result.matrix[1][3] = translation.getY() + matrix[1][3];
        result.matrix[2][3] = translation.getZ() + matrix[2][3];
        return result;
    }
    public Matrix4x4 multiply(Matrix4x4 other){
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[i][j] = matrix[i][0] * other.matrix[0][j] +
                                      matrix[i][1] * other.matrix[1][j] +
                                      matrix[i][2] * other.matrix[2][j] +
                                      matrix[i][3] * other.matrix[3][j];
            }
        }
        return result;
    }

    public Matrix4x4 getMatrix() {
        return this;
    }

    public Matrix4x4 setMatrix(float[][] matrix) {
        this.matrix = matrix;
        return this;
    }
}