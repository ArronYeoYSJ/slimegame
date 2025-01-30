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

    public static Matrix4x4 identity() {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[i][j] = 0.0f;
            }
        }
        for (int i = 0; i < 4; i++) {
            result.matrix[i][i] = 1.0f;
        }
        return result;
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
    // public Matrix4x4 swapMajor() {
    //     float[] array = new float[16];
    //     for (int i = 0; i < 4; i++) {
    //         for (int j = 0; j < 4; j++) {
    //             array[i*4 +j] =matrix[j][i];
    //         }
    //     }
    //     Matrix4x4 temp = new Matrix4x4(array);
    //     return temp;
    // }

    public static Matrix4x4 rotate(float angle, Vector4 axis){
        Matrix4x4 result = Matrix4x4.identity();
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

    public static Matrix4x4 scale(Vector4 scale){
        Matrix4x4 result = Matrix4x4.identity();
        result.matrix[0][0] = scale.getX();
        result.matrix[1][1] = scale.getY();
        result.matrix[2][2] = scale.getZ();
        return result;
    }
    
    public static Matrix4x4 translate(Vector4 translation){
        Matrix4x4 result = Matrix4x4.identity();
        result.matrix[3][0] = translation.getX();
        result.matrix[3][1] = translation.getY();
        result.matrix[3][2] = translation.getZ();
        return result;
    }
    public static Matrix4x4 multiply(Matrix4x4 matrix,Matrix4x4 other){
        Matrix4x4 result = Matrix4x4.identity();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[i][j] = matrix.matrix[i][0] * other.matrix[0][j] +
                                      matrix.matrix[i][1] * other.matrix[1][j] +
                                      matrix.matrix[i][2] * other.matrix[2][j] +
                                      matrix.matrix[i][3] * other.matrix[3][j];
            }
        }
        return result;
    }

    public static Matrix4x4 transform(Vector4 position, Vector4 rotation, Vector4 scale){
        Matrix4x4 result = Matrix4x4.identity();
        Matrix4x4 translation = translate(position);
        Matrix4x4 rotX = rotate(rotation.getX(), new Vector4(1, 0, 0));
        Matrix4x4 rotY = rotate(rotation.getY(), new Vector4(0, 1, 0));
        Matrix4x4 rotZ = rotate(rotation.getZ(), new Vector4(0, 0, 1));
        Matrix4x4 scaleMatrix = Matrix4x4.scale(scale);
        Matrix4x4 rotationMatrix = multiply(rotX, multiply(rotY, rotZ));
        result = Matrix4x4.multiply(translation, Matrix4x4.multiply(rotationMatrix, scaleMatrix));
        return result;
    }

    public static Matrix4x4 projection(float fov, float aspectRatio, float near, float far){
        Matrix4x4 result = Matrix4x4.identity();

        //terms are based off of this image https://i.sstatic.net/zPcST.png
        
        float term00 = 1.0f / (aspectRatio * (float) Math.tan(Math.toRadians(fov / 2.0f)));
        float term11 = 1.0f / ((float) Math.tan(Math.toRadians(fov / 2.0f)));
        float term22 = -((far + near) / (far -near));
        float term23 = -1.0f;
        float term32 = -((2.0f * far * near) / (far - near));
        //assign terms to corresponding matrix values
        result.matrix[0][0] = term00;
        result.matrix[1][1] = term11;
        result.matrix[2][2] = term22;
        result.matrix[2][3] = term23;
        result.matrix[3][2] = term32;
        result.matrix[3][3] = 1f;
                
        return result;
    }

    public static Matrix4x4 view(Vector4 position, Vector4 rotation){
        Matrix4x4 result = Matrix4x4.identity();

        Vector4 negative = new Vector4(-position.getX(), -position.getY(), -position.getZ());
        Matrix4x4 translation = Matrix4x4.translate(negative);
        Matrix4x4 rotX = Matrix4x4.rotate(rotation.getX(), new Vector4(1, 0, 0));
        Matrix4x4 rotY = Matrix4x4.rotate(rotation.getY(), new Vector4(0, 1, 0));
        Matrix4x4 rotZ = Matrix4x4.rotate(rotation.getZ(), new Vector4(0, 0, 1));
        Matrix4x4 rotationMatrix = Matrix4x4.multiply(rotZ, Matrix4x4.multiply(rotY, rotX));
        result = Matrix4x4.multiply(translation, rotationMatrix);
        return result;
    }

        

    public Matrix4x4 getMatrix() {
        return this;
    }

    public void logMatrix() {
        System.out.println("Matrix4x4");
        for (float[] row : this.matrix) {
            for (float value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

}