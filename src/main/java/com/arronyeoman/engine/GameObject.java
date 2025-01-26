package com.arronyeoman.engine;

import com.arronyeoman.maths.*;
import com.arronyeoman.graphics.*;


public class GameObject {
    private Mesh mesh;
    private Vector4 position, scale, rotation;
    private int frames = 0;
    private float temp;

    public GameObject(Mesh mesh, Vector4 position, Vector4 scale, Vector4 rotation) {
        this.mesh = mesh;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public void update() {
        frames++;
        position.setX((float)Math.sin(frames / 60f) * 0.5f);
        position.setY((float)Math.cos(frames / 60f) * 0.5f);
        scale.setX((float)Math.sin(frames / 60f));
        scale.setY((float)Math.sin(frames / 60f));
        rotation.setZ(frames);
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector4 getPosition() {
        return position;
    }

    public Vector4 getScale() {
        return scale;
    }

    public Vector4 getRotation() {
        return rotation;
    }

    
}
