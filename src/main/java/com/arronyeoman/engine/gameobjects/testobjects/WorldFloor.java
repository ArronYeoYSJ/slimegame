package com.arronyeoman.engine.gameobjects.testobjects;

import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.graphics.Mesh;
import com.arronyeoman.maths.Vector4;
import com.arronyeoman.maths.VertPN;
import com.arronyeoman.graphics.Material;

public class WorldFloor implements GameObject {

    public Mesh mesh;
    private Vector4 position = new Vector4(0f, 0f, 0f, 1f);
    private Vector4 rotation = new Vector4(0f, 0f, 0f, 1f);
    private Vector4 colour = new Vector4(1f, 1f, 1f, 1f);
    private Float yDepth = 0.5f;
    private Vector4 scale = new Vector4(1f, 1f, 1f, 1f);
    private Float size = 1.0f;
    private Float sizeHalf;
    private Vector4 uvBounds= new Vector4(0f, 1f);
    private float[] UVs = { uvBounds.getX(), uvBounds.getX(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY()};
    private int[] indices = { 0, 1, 2, 2, 3, 0};
    private VertPN[] verts;
    private String textureName;

    public WorldFloor(float size, Vector4 position, Vector4 rotation, Vector4 scale, float yDepth, String texName){
        this.size = size;
        this.yDepth = yDepth = 5f;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.textureName = texName;
        create();
        //System.out.println("Creating Cube");
    }

    public WorldFloor(float size, Vector4 position, String texName){
        this.size = size;
        this.position = position;
        this.textureName = texName;
        create();
    }

    public WorldFloor(float size, String texName){
        this.size = size;
        this.textureName = texName;
        create();
    }

    public WorldFloor(float size, Vector4 position, float yDepth, String texName){
        this.size = size;
        this.position = position;
        this.yDepth = yDepth;
        this.textureName = texName;
        create();
    }

    public void create() {
        this.sizeHalf = size / 2.0f;
        this.verts = new VertPN[] {
        //front face
        new VertPN(new Vector4(-sizeHalf, -yDepth, -sizeHalf, 1.0f), new Vector4(0,1,0,0), new Vector4(0,1)),    //0
        new VertPN(new Vector4(sizeHalf, -yDepth, -sizeHalf, 1.0f), new Vector4(0,1,0,0), new Vector4(1,1)),    //1
        new VertPN(new Vector4(sizeHalf, -yDepth, sizeHalf, 1.0f), new Vector4(0,1,0,0), new Vector4(1,0)),   //2
        new VertPN(new Vector4(-sizeHalf, -yDepth, sizeHalf, 1.0f), new Vector4(0,1,0,0), new Vector4(0,0)),    //3
        };
        
        this.mesh = new Mesh(verts, indices, new Material(textureName));
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
