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
        this.w = 1f;
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

    public static Vector4 add(Vector4 a, Vector4 b) {
        return new Vector4(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w);
    }

    public static Vector4 subtract(Vector4 a, Vector4 b) {
        return new Vector4(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w);
    }

    public static Vector4 multiply(Vector4 a, Vector4 b) {
        return new Vector4(a.x * b.x, a.y * b.y, a.z * b.z, a.w * b.w);
    }

    public static Vector4 divide(Vector4 a, Vector4 b) {
        return new Vector4(a.x / b.x, a.y / b.y, a.z / b.z, a.w / b.w);
    }

    public static float magnitude(Vector4 a) {
        return (float)Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
    }

    public static Vector4 normalise(Vector4 a) {
        float mag = magnitude(a);
        return new Vector4(a.x / mag, a.y / mag, a.z / mag);
    }

    public static float dot(Vector4 a, Vector4 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static Vector4 cross(Vector4 a, Vector4 b) {
        return new Vector4(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        result = prime * result + Float.floatToIntBits(w);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector4 other = (Vector4) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
            return false;
        return true;
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
