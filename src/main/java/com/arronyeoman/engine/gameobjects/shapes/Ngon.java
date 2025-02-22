package com.arronyeoman.engine.gameobjects.shapes;

import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.graphics.*;
import com.arronyeoman.maths.*;

public class Ngon implements GameObject{
    private int sides;
    private float radius;
    private Vector4 position, rotation, scale, origin;
    private Material mat;
    private Mesh mesh;
    private Vector4 colour = new Vector4(1f, 1f, 1f, 1f);

    public Ngon(int sides, float radius, String textureName){
        this.sides = sides;
        this.radius = radius;
        this.position = new Vector4(0f, 0f, 0f, 1f);
        this.rotation = new Vector4(0f, 270f, 0f, 1f);
        this.scale = new Vector4(1f, 1f, 1f, 1f);
        origin = new Vector4(0f, 0f, 0f, 1f);
        mat = new Material(textureName);
        create();
    }
    public Ngon(int sides, float radius, String textureName, Vector4 position){
        this.sides = sides;
        this.radius = radius;
        this.rotation = new Vector4(0, 0, 0, 1);
        this.scale = new Vector4(1, 1, 1, 1);
        this.position = position;
        origin = new Vector4(0, 0, 0, 1);
        mat = new Material(textureName);
        create();
    }
    public void create(){
        double angle = 2 * Math.PI / sides;
        VertPN[] verts = new VertPN[sides + 1];
        for (int i = 0; i < sides; i++){
            float x = (float) (radius * Math.sin(angle * i));
            float y = (float) (radius * Math.cos(angle * i));
            verts[i] = new VertPN(
                new Vector4( x, y, 0, 1),
                new Vector4(0, 0, 1, 1),
                new Vector4(0.5f + x/2, 0.5f + y/2));
            }
        verts[sides] = new VertPN(origin, new Vector4(0, 0, 1, 1), new Vector4(0.5f, 0.5f));
        int[] indices = new int[sides * 3];
        for (int i = 0; i < sides; i++){
            indices[i * 3] = i;
            indices[i * 3 + 1] = sides;
            indices[i * 3 + 2] = i + 1;
        }
        indices[sides * 3 - 1] = 0;
        mesh = new Mesh(verts, indices, mat);
        mesh.initMesh();
    }
    public Vector4 getPosition() {
        return position;
    }
    public Vector4 getRotation() {
        return rotation;
    }
    public Vector4 getScale() {
        return scale;
    }
    public Mesh getMesh() {
        return mesh;
    }
    public void destroy() {
        mesh.destroy();
        System.gc();
    }
    public void setPosition(Vector4 position) {
        this.position = position;
    }
    public void setRotation(Vector4 rotation) {
        this.rotation = rotation;
    }
    public void setScale(Vector4 scale) {
        this.scale = scale;
    }
    public void setMat(Material mat) {
        this.mat = mat;
        mesh.destroy();
        create();
    }
    public Vector4 getColour() {
        return this.colour;
    }
    public void setColour(Vector4 colour) {
        this.colour = colour;
    }
    public void setAlpha(float alpha) {
        this.colour.setW(alpha);
    }
    public float getAlpha() {
        return this.colour.getW();
    }
    
}
