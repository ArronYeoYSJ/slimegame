package com.arronyeoman.engine.gameobjects.shapes;

import com.arronyeoman.graphics.Mesh;
import com.arronyeoman.graphics.Material;
import com.arronyeoman.maths.*;
import com.beust.jcommander.Parameter;
import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.engine.io.InputHandler;

import static org.lwjgl.glfw.GLFW.*;

public class Cube implements GameObject{

    public Mesh mesh;
    @Parameter(names = {"-p", "--position"}, description = "Position of the cube")
    private Vector4 position = new Vector4(0, 0, 0, 1);
    @Parameter(names = {"-r", "--rotation"}, description = "Rotation of the cube")
    private Vector4 rotation = new Vector4(90, 0, 0, 1);
    @Parameter(names = {"-sc", "--scale"}, description = "Scale of the cube")
    private Vector4 scale = new Vector4(1, 1, 1, 1);
    @Parameter(names = {"-s", "--size"}, description = "Size of the cube")
    private Float size = 1.0f;
    private Float sizeHalf;
    private Vector4 uvBounds= new Vector4(0.01f, 0.99f);
    @Parameter(names = {"-t", "--texture"}, description = "Texture of the cube")
    private String textureName;
    private Vector4 colour = new Vector4(1f, 1f, 1f, 1f);
    
    private int[] indices = 
    {
        0,1,2,0,2,3, //front face
        4,5,6,4,6,7, //right face
        8,9,10,8,10,11, //back face
        12,13,14,12,14,15, //left face
        16,17,18,16,18,19, //top face
        20,21,22,20,22,23 //bottom face
    };
    private VertPN[] verts;

    public Cube(float size, Vector4 position, Vector4 rotation, Vector4 scale, String textureName){
        this.size = size;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.textureName = textureName;
        //System.out.println("Creating Cube");
        create(); 
    }

    public Cube(float size, Vector4 position, String textureName){
        this.size = size;
        this.position = position;
        this.textureName = textureName;
        create();
    }

    public Cube(float size, String textureName){
        this.size = size;
        this.textureName = textureName;
        create();
    }
    
    public void update() {
        if (InputHandler.isKeyDown(GLFW_KEY_R)) {
            //System.out.println("Rotating Cube");
            rotation = Vector4.add(rotation, new Vector4(0, 1f, 0));
        }
        if (InputHandler.isKeyDown(GLFW_KEY_Q)){
            //System.out.println("Rotating Cube");
            rotation = Vector4.add(rotation, new Vector4(0, -1f, 0));
        } 
    }
    
    public void create() {
        init();        
    }

    public void init() {
        this.sizeHalf = size / 2.0f;
        Vector4 v1 = new Vector4(-sizeHalf, sizeHalf, sizeHalf, 1.0f);
        Vector4 v2 = new Vector4(sizeHalf, sizeHalf, sizeHalf, 1.0f);
        Vector4 v3 = new Vector4(sizeHalf, -sizeHalf, sizeHalf, 1.0f);
        Vector4 v4 = new Vector4(-sizeHalf, -sizeHalf, sizeHalf, 1.0f);
        Vector4 v5 = new Vector4(-sizeHalf, sizeHalf, -sizeHalf, 1.0f);
        Vector4 v6 = new Vector4(sizeHalf, sizeHalf, -sizeHalf, 1.0f);
        Vector4 v7 = new Vector4(sizeHalf, -sizeHalf, -sizeHalf, 1.0f);
        Vector4 v8 = new Vector4(-sizeHalf, -sizeHalf, -sizeHalf, 1.0f);

        Vector4 n1 = new Vector4(0, 0, 1, 0);
        Vector4 n2 = new Vector4(1, 0, 0, 0);
        Vector4 n3 = new Vector4(0, 0, -1, 0);
        Vector4 n4 = new Vector4(-1, 0, 0, 0);
        Vector4 n5 = new Vector4(0, 1, 0, 0);
        Vector4 n6 = new Vector4(0, -1, 0, 0);

        Vector4 uv1 = new Vector4(uvBounds.getX(), uvBounds.getY());
        Vector4 uv2 = new Vector4(uvBounds.getY(), uvBounds.getY());
        Vector4 uv3 = new Vector4(uvBounds.getY(), uvBounds.getX());
        Vector4 uv4 = new Vector4(uvBounds.getX(), uvBounds.getX());


        this.verts = new VertPN[] {
        //front face
        new VertPN(v1, n1, uv4),    //0
        new VertPN(v2, n1, uv1),   //1
        new VertPN(v3, n1, uv2),    //2
        new VertPN(v4, n1, uv3),     //3

        //right face - 1/4 , 5, 6, 2/7
        new VertPN(v2, n2, uv4),   //4
        new VertPN(v6, n2, uv1),   //5
        new VertPN(v7, n2, uv2),    //6
        new VertPN(v3, n2, uv3),    //7
        //back face - 5/8, 9 , 10, 6/11
        new VertPN(v6, n3, uv4),   //8
        new VertPN(v5, n3, uv1),    //9
        new VertPN(v8, n3, uv2),     //10
        new VertPN(v7, n3, uv3),    //11
        //left face - 9/12, 13, 14, 10/15
        new VertPN(v5, n4, uv4),    //12
        new VertPN(v1, n4, uv1),    //13
        new VertPN(v4, n4, uv2),     //14
        new VertPN(v8, n4, uv3) ,    //15
        //top face - 8/16, 0, 3, 9/17
        new VertPN(v5, n5, new Vector4(0f, 1f)),     //16
        new VertPN(v6, n5, new Vector4(0.07f, 1f)),     //17
        new VertPN(v2, n5, new Vector4(0.07f, 0f)),    //18
        new VertPN(v1, n5, new Vector4(0f, 0f)),   //19
        //bottom face - 11/20, 7, 6, 15/21
        new VertPN(v4, n6, new Vector4(0.93f, 1f)),    //20
        new VertPN(v3, n6, new Vector4(1f, 1f)),     //21
        new VertPN(v7, n6, new Vector4(1f, 0.1f)),     //22
        new VertPN(v8, n6, new Vector4(0.93f, 0.1f)),    //23
        };
        //translate(position.getX(), position.getY(), position.getZ());
        //System.out.println("Vertexes created");
        //this.position = new Vector4(0f, 0f , -1f);
        this.mesh = new Mesh(verts, indices, new Material(textureName));
        mesh.initMesh();
    }

    public Mesh getMesh() {
        return this.mesh;
    }


    public Float getSize() {
        return this.size;
    }

    public Vector4 getPosition() {
        return this.position;
    }
    public Vector4 getRotation() {
        return this.rotation;
    }
    public Vector4 getScale() {
        return this.scale;
    }
    public void setScale (Vector4 scale) {
        this.scale = scale;
    }
    public void setRotation(Vector4 rotation) {
        this.rotation = rotation;
    }
    public void setPosition(Vector4 position) {
        this.position = position;
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


    public void destroy() {
        mesh.destroy();
        System.gc();
    }
}
