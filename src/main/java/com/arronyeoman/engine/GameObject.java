package com.arronyeoman.engine;

import com.arronyeoman.maths.*;
import com.arronyeoman.graphics.*;

public class GameObject {
    private Mesh mesh;
    private Vector4 position, scale, rotation;

    public GameObject(Mesh mesh, Vector4 position, Vector4 scale, Vector4 rotation) {
        this.mesh = mesh;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
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
