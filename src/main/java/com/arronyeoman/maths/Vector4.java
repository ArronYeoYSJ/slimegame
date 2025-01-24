package com.arronyeoman.maths;

public class Vector4 {
    
    public float x, y, z, w;
    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    // overload for null vector
    //@Override
    public Vector4() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1;
    }
    // overload for 3d vector
    //@Override
    public Vector4(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }
    // overload for 2d vector
    //@Override
    public Vector4(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 1;
    }
    
    public void setVector(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public void setVector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public float getZ() {
        return z;
    }
    public void setZ(float z) {
        this.z = z;
    }
    public float getW() {
        return w;
    }
    public void setW(float w) {
        this.w = w;
    }
    
    
}
