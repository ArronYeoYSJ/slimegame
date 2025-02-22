package com.arronyeoman.engine.gameobjects;

import com.arronyeoman.maths.*;

import com.arronyeoman.graphics.Mesh;

public interface GameObject {
    //public Mesh mesh = null;
    //public Vector4 position = null;

    void create();
    //int getTextureID();
    Mesh getMesh();
    Vector4 getPosition();
    Vector4 getRotation();
    Vector4 getScale();
    Vector4 getColour();

    void destroy();
}
