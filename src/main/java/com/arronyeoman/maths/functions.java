package com.arronyeoman.maths;

public class functions {

    //vertex functions
    public Vertex vertAdd(Vertex v1, Vertex v2) {
        return new Vertex(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z, v1.w + v2.w);
    }
    public Vertex vertSub(Vertex v1, Vertex v2) {
        return new Vertex(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, v1.w - v2.w);
    }
    public Vertex vertMult(Vertex v1, Vertex v2) {
        return new Vertex(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z, v1.w * v2.w);
    }
    public Vertex vertDiv(Vertex v1, Vertex v2) {
        return new Vertex(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z, v1.w / v2.w);
    }
    public Vertex vertAdd(Vertex v,float f) {
        return new Vertex(v.x + f, v.y + f, v.z + f, v.w + f);
    }
    public Vertex vertSub(Vertex v,float f) {
        return new Vertex(v.x - f, v.y - f, v.z - f, v.w - f);
    }
    public Vertex vertMult(Vertex v,float f) {
        return new Vertex(v.x * f, v.y * f, v.z * f, v.w * f);
    }
    public Vertex vertDiv(Vertex v,float f) {
        return new Vertex(v.x / f, v.y / f, v.z / f, v.w / f);
    }
    public Vertex vertAbs(Vertex v) {
        return new Vertex(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z), Math.abs(v.w));
    }
    public Vertex vertNegate(Vertex v) {
        return new Vertex(-v.x, -v.y, -v.z, -v.w);
    }
    public float vertMag(Vertex v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w);
    }
    public Vertex vertNorm(Vertex v){
        return vertDiv(v, vertMag(v));
    }
    public float vertDist(Vertex v1, Vertex v2) {
        return (float) Math.sqrt((v1.x - v2.x) * (v1.x - v2.x) + (v1.y - v2.y) * (v1.y - v2.y) + (v1.z - v2.z) * (v1.z - v2.z) + (v1.w - v2.w) * (v1.w - v2.w));
    }
    public Vertex vertAdd(Vertex vx, Vector4 vr){
        return new Vertex(vx.x + vr.x, vx.y + vr.y, vx.z + vr.z, vx.w + vr.w);
    }
    public Vertex vertSub(Vertex vx,Vector4 vr){
        return new Vertex(vx.x - vr.x, vx.y - vr.y, vx.z - vr.z, vx.w - vr.w);
    }
    public Vertex vertMult(Vertex vx,Vector4 vr){
        return new Vertex(vx.x * vr.x, vx.y * vr.y, vx.z * vr.z, vx.w * vr.w);
    }
    public Vertex vertDiv(Vertex vx,Vector4 vr){
        return new Vertex(vx.x / vr.x, vx.y / vr.y, vx.z / vr.z, vx.w / vr.w);
    }

    // public float vertDot(vertex v) {
    //     return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;
    // }
    // public vertex vertCross(vertex v) {
    //     return new vertex(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x, 0);
    // }

    //vector functions
    public Vector4 vecAdd(Vector4 v1, Vector4 v2) {
        return new Vector4(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z, v1.w + v2.w);
    }
    public Vector4 vecSub(Vector4 v1, Vector4 v2) {
        return new Vector4(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, v1.w - v2.w);
    }
    public Vector4 vecMult(Vector4 v1, Vector4 v2) {
        return new Vector4(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z, v1.w * v2.w);
    }
    public Vector4 vecDiv(Vector4 v1, Vector4 v2) {
        return new Vector4(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z, v1.w / v2.w);
    }
    public Vector4 VecAdd(Vector4 v1, float f) {
        return new Vector4(v1.x + f, v1.y + f, v1.z + f, v1.w + f);
    }
    public Vector4 vecSub(Vector4 v1, float f) {
        return new Vector4(v1.x - f, v1.y - f, v1.z - f, v1.w - f);
    }
    public Vector4 vecMult(Vector4 v1, float f) {
        return new Vector4(v1.x * f, v1.y * f, v1.z * f, v1.w * f);
    }
    public Vector4 vecDiv(Vector4 v1, float f) {
        return new Vector4(v1.x / f, v1.y / f, v1.z / f, v1.w / f);
    }
    public Vector4 vecAbs(Vector4 v) {
        return new Vector4(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z), Math.abs(v.w));
    }
    public Vector4 vecNegate(Vector4 v) {
        return new Vector4(-v.x, -v.y, -v.z, -v.w);
    }
    public float vecDot(Vector4 v1, Vector4 v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w;
    }
    public Vector4 vecCross(Vector4 v1, Vector4 v2) {
        return new Vector4(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x, 0);
    }
}