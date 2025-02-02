package com.arronyeoman.engine.gameobjects.shapes;

import com.arronyeoman.graphics.Mesh;
import com.arronyeoman.maths.Vector4;
import com.arronyeoman.maths.Vertex;
import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.engine.io.InputHandler;

import static org.lwjgl.glfw.GLFW.*;

public class Cube implements GameObject{

    public Mesh mesh;
    private Vector4 position = new Vector4(0, 0, 0, 1);
    private Vector4 rotation = new Vector4(0, 0, 0, 1);
    private Vector4 scale = new Vector4(1, 1, 1, 1);
    private Float size = 1.0f;
    private Float sizeHalf;
    private Vector4 uvBounds= new Vector4(0.1f, 0.85f);
    private float[] UVs = 
    {
        uvBounds.getX(), uvBounds.getX(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(),  //2
        uvBounds.getX(), uvBounds.getX(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(),  //1
        uvBounds.getX(), uvBounds.getX(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(),  //3
        uvBounds.getX(), uvBounds.getX(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(),  //4
        uvBounds.getX(), uvBounds.getX(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(),  //5
        uvBounds.getX(), uvBounds.getX(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY(), uvBounds.getY(), uvBounds.getX(), uvBounds.getY()   //6
    };
    private int[] indices = 
    {
        0, 1, 2, 2, 3, 0,  //1
        4, 5, 6, 6, 7, 4,  //2
        8, 9, 10, 10, 11, 8,  //3
        12, 13, 14, 14, 15, 12,  //4
        16, 17, 18, 18, 19, 16,  //5
        20, 21, 22, 22, 23, 20   //6
    };
    private Vertex[] verts;

    public Cube(float size, Vector4 position, Vector4 rotation, Vector4 scale) {
        this.size = size;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        //System.out.println("Creating Cube");
        create();
    }

    public Cube(float size, Vector4 position){
        this.size = size;
        this.position = position;
        create();
    }

    public Cube(float size){
        this.size = size;
        create();
    }

    public Cube() {
        create();
    }

    public Cube(Mesh mesh) {
        this.mesh = mesh;
        mesh.initMesh();
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
        this.verts = new Vertex[] {
        //front face
        new Vertex(-sizeHalf, sizeHalf, sizeHalf, 1.0f),    //0
        new Vertex(sizeHalf, sizeHalf, sizeHalf, 1.0f),   //1
        new Vertex(sizeHalf, -sizeHalf, sizeHalf, 1.0f),    //2
        new Vertex(-sizeHalf, -sizeHalf, sizeHalf, 1.0f),     //3

        //right face - 1/4 , 5, 6, 2/7
        new Vertex(sizeHalf, sizeHalf, sizeHalf, 1.0f),   //4
        new Vertex(sizeHalf, sizeHalf, -sizeHalf, 1.0f),   //5
        new Vertex(sizeHalf, -sizeHalf, -sizeHalf, 1.0f),    //6
        new Vertex(sizeHalf, -sizeHalf, sizeHalf, 1.0f),    //7
        //back face - 5/8, 9 , 10, 6/11
        new Vertex(sizeHalf, sizeHalf, -sizeHalf, 1.0f),   //8
        new Vertex(-sizeHalf, sizeHalf, -sizeHalf, 1.0f),    //9
        new Vertex(-sizeHalf, -sizeHalf, -sizeHalf, 1.0f),     //10
        new Vertex(sizeHalf, -sizeHalf, -sizeHalf, 1.0f),    //11
        //left face - 9/12, 13, 14, 10/15
        new Vertex(-sizeHalf, sizeHalf, -sizeHalf, 1.0f),    //12
        new Vertex(-sizeHalf, sizeHalf, sizeHalf, 1.0f),    //13
        new Vertex(-sizeHalf, -sizeHalf, sizeHalf, 1.0f),     //14
        new Vertex(-sizeHalf, -sizeHalf, -sizeHalf, 1.0f),     //15
        //top face - 8/16, 0, 3, 9/17
        new Vertex(-sizeHalf, sizeHalf, sizeHalf, 1.0f),     //16
        new Vertex(-sizeHalf, sizeHalf, -sizeHalf, 1.0f),     //17
        new Vertex(sizeHalf, sizeHalf, -sizeHalf, 1.0f),    //18
        new Vertex(sizeHalf, sizeHalf, sizeHalf, 1.0f),   //19
        //bottom face - 11/20, 7, 6, 15/21
        new Vertex(sizeHalf, -sizeHalf, -sizeHalf, 1.0f),    //20
        new Vertex(-sizeHalf, -sizeHalf, -sizeHalf, 1.0f),     //21
        new Vertex(-sizeHalf, -sizeHalf, sizeHalf, 1.0f),     //22
        new Vertex(sizeHalf, -sizeHalf, sizeHalf, 1.0f),    //23
        };
        //translate(position.getX(), position.getY(), position.getZ());
        //System.out.println("Vertexes created");
        //this.position = new Vector4(0f, 0f , -1f);
        this.mesh = new Mesh(verts, indices, UVs);
        mesh.initMesh();
    }

    public Mesh getMesh() {
        return this.mesh;
    }


    public void translate(float x, float y, float z) {
        for (Vertex vert : verts) {
            vert.setVertex(vert.getX() + x, vert.getY() + y, vert.getZ() + z);
        }
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

    public void destroy() {
        mesh.destroy();
        System.gc();
    }
}
