package com.arronyeoman.engine.gameobjects.testobjects;

import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.graphics.Mesh;
import com.arronyeoman.maths.Vector4;

public class WorldFloor implements GameObject {

    public WorldFloor() {
        System.out.println("Creating World Floor");
    }

    public void create() {
        System.out.println("Creating World Floor");
    }
    public Mesh getMesh() {
        return null;
    }
    public Vector4 getRotation(){
        return null;
    };
    public Vector4 getScale(){
        return null;
    };
    public Vector4 getPosition(){
        return null;
    };

    @Override
    public void destroy() {
        System.out.println("Destroying World Floor");
    }

}
