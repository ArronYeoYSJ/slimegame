package com.arronyeoman.engine.gameobjects;

import com.arronyeoman.maths.*;
import com.arronyeoman.graphics.*;


public class GameObjectold {
    private Vector4 position, scale, rotation;
    private Mesh mesh;

    public GameObjectold(Vector4 position, Vector4 scale, Vector4 rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public void update() {
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
