package com.arronyeoman.maths;

public class Vertex {
    public float x, y, z, w;
    public float r, g, b, a;
    public Vertex(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.r = 1.0f;
        this.g = 1.0f;
        this.b = 1.0f;
        this.a = 1.0f;
    }
    public Vertex() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1.0f;
        this.r = 1.0f;
        this.g = 1.0f;
        this.b = 1.0f;
        this.a = 1.0f;
    }
    public Vertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0f;
        this.r = 1.0f;
        this.g = 1.0f;
        this.b = 1.0f;
        this.a = 1.0f;
    }
    public Vertex(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 1.0f;
        this.r = 1.0f;
        this.g = 1.0f;
        this.b = 1.0f;
        this.a = 1.0f;
    }

    public Vertex getPosition() {
        return new Vertex(this.x, this.y, this.z, this.w);
    }
    public Vertex getcolour() {
        return new Vertex(this.r, this.g, this.b, this.a);
    }

    public void setColour(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void setPos(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public void setVertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void setVertex(float x, float y) {
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
