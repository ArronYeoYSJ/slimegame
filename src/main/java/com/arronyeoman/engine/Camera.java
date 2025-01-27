package com.arronyeoman.engine;

import com.arronyeoman.maths.Vector4;

public class Camera {
    private Vector4 position, rotation;

    public Camera(Vector4 position, Vector4 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {
        
    }

    public void move(Vector4 direction) {
        position.add(direction);
    }

    public void rotate(Vector4 rotation) {
        this.rotation.add(rotation);
    }

    public Vector4 getPosition() {
        return position;
    }

    public Vector4 getRotation() {
        return rotation;
    }

    
}
