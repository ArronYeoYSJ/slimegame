package com.arronyeoman.engine.gameobjects.testobjects;

import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.graphics.Mesh;
import com.arronyeoman.maths.Vector4;
import com.arronyeoman.maths.Vertex;

public class WorldFloor implements GameObject {

    public Mesh mesh;
    private Vector4 position = new Vector4(0, 0, 0, 1);
    private Vector4 rotation = new Vector4(0, 0, 0, 1);
    private Float yDepth = 0.5f;
    private Vector4 scale = new Vector4(1, 1, 1, 1);
    private Float size = 1.0f;
    private Float sizeHalf;
    private Vector4 uvBounds= new Vector4(0f, 1f);
    private float[] UVs = { uvBounds.getX(), uvBounds.getX(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY()};
    private int[] indices = { 0, 1, 2, 2, 3, 0};
    private Vertex[] verts;

    public WorldFloor(float size, Vector4 position, Vector4 rotation, Vector4 scale, float yDepth) {
        this.size = size;
        this.yDepth = yDepth = 5f;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        create();
        //System.out.println("Creating Cube");
    }

    public WorldFloor(float size, Vector4 position){
        this.size = size;
        this.position = position;
        create();
    }

    public WorldFloor(float size){
        this.size = size;
        create();
    }

    public WorldFloor(float size, Vector4 position, float yDepth){
        this.size = size;
        this.position = position;
        this.yDepth = yDepth;
        create();
    }

    public void create() {
        this.sizeHalf = size / 2.0f;
        this.verts = new Vertex[] {
        //front face
        new Vertex(-sizeHalf, -yDepth, -sizeHalf, 1.0f),    //0
        new Vertex(sizeHalf, -yDepth, -sizeHalf, 1.0f),     //1
        new Vertex(sizeHalf, -yDepth, sizeHalf, 1.0f),    //2
        new Vertex(-sizeHalf, -yDepth, sizeHalf, 1.0f)    //3
        };
        
        this.mesh = new Mesh(verts, indices, UVs);
        mesh.initMesh();
    }
    public Mesh getMesh() {
        return this.mesh;
    }
    public Vector4 getRotation(){
        return this.rotation;
    };
    public Float getYDepth(){
        return this.yDepth;
    };
    public void setYDepth(Float yDepth){
        this.yDepth = yDepth;
    };

    public Vector4 getScale(){
        return this.scale;
    };
    public Vector4 getPosition(){
        return this.position;
    };

    @Override
    public void destroy() {
        System.out.println("Destroying World Floor");
    }

}
