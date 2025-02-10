package com.arronyeoman.engine.gameobjects.shapes;

import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.graphics.Mesh;
import com.arronyeoman.maths.*;
import com.arronyeoman.graphics.*;


public class Sphere implements GameObject{

    public Mesh mesh;
    public String textureName;
    private float radius;
    private Vector4  position;
    private int slices, stacks;
    private Vector4 scale, rotation;

    public Sphere(float radius, Vector4 position, int stacks, String textureName) {
        this.radius = radius;
        this.position = position;
        this.stacks = stacks;
        if (stacks < 3) 
        {this.stacks = 3;
        System.err.println("spheres must consist of at least 3 stacks");
            return; 
        }
        if (stacks > 50){
            this.stacks = 50;
            System.err.println("spheres must consist of at most 50 stacks");
            return; 
        }
        this.slices = stacks * 2;
        this.textureName = textureName;
        this.rotation = new Vector4(0f, 0f, 0f, 1f);
        this.scale = new Vector4(1f, 1f, 1f, 1f);
        create();
    }

    public void update() {
        rotation = new Vector4(rotation.getX(), rotation.getY() + 0.1f, rotation.getZ(), 1f);
    }

    public void create(){
        double phi, theta;
        int index = 0;
        int length = ((slices + 1) * (stacks - 1)) + 2 * (slices + 1);
        float step = 1f / slices;
        float halfStep = step / 2;
        System.out.println("Creating sphere with " + length + " vertices");
        VertPN[] verts = new VertPN[length];
        float u = 0f, v = 0f;
        //create vertexes at north pole
        for ( int i = 0; i < slices + 1 ; i++){
            u = Math.clamp(1f - i * step - halfStep, 0f, 1f);
            verts[index++] = new VertPN(new Vector4(0f, radius, 0f), new Vector4(0f, 1f, 0f, 0f), new Vector4(u, 0f));
        }
        //create vertexes at middle stacks
        for (int i = 1; i < stacks; i++) {
            phi = 3.142 * i / stacks;
            for (int j = 0; j <= slices; j++) {
                theta = 2 * Math.PI * j / slices;
                float x = (float) (radius * Math.sin(phi) * Math.cos(theta));
                //System.out.println("x: " + x);
                float y = (float) (radius * Math.cos(phi));
                //System.out.println("y: " + y);
                float z = (float) (radius * Math.sin(phi) * Math.sin(theta));
                //System.out.println("z: " + z);
                float nx = x / radius;
                float ny = y / radius;
                float nz = z / radius;
                u = (float) (0.5 +  Math.atan2(nz, -nx) / (2 * 3.142));
                v = (float) (0.5 + Math.asin(-ny) / 3.142);
                //System.out.println("u: " + u + " v: " + v);

                verts[index++] =  new VertPN(new Vector4(x, y, z, 1f), new Vector4(nx, ny, nz, 0f), new Vector4(u, v));
            }
        }
        //create vertexes at south pole
        for ( int i = 0; i < slices + 1; i++){
            u = Math.clamp(1f - i * step - halfStep, 0f, 1f);
            verts[index++] = new VertPN(new Vector4(0f,  -radius, 0f), new Vector4(0f, -1f, 0f, 0f), new Vector4(u, 1f));
            //System.out.println(" i: " + i + " u: " + u);
        }

        //calculate total indices, 1 triangle per slice on caps, 2 triangles per stack,
        // 3 indices per triangle, total doubled to store coordinates to address verts with
        int indicesLength = slices * 6 + 6 * slices * (stacks-1);
        int layerInterval = slices + 1;
        
        int [] indices = new int[indicesLength];

        index = 0;
        //construct top cap
        for (int i = 0; i < slices; i++) {
            indices[index++] = i;
            indices[index++] = (i + layerInterval);
            indices[index++] = (i + layerInterval + 1);
            // System.out.println(" - 0: " + verts[indices[index - 3]].getU() + " , " + verts[indices[index - 3]].getV()  + 
            //                     " -  1: " + verts[indices[index - 2]].getU() + " , " + verts[indices[index - 2]].getV() + 
            //                     " - 2: " + verts[indices[index - 1]].getU() + " , " + verts[indices[index - 1]].getV());
        }
        //construct middle stacks
        for (int i = 1; i < stacks - 1; i++) {
            int offset = 1 + (i * layerInterval);
            int nextOffset = offset + layerInterval;
            for (int j = 0; j <= slices; j++) {
                
                indices[index++] = i * layerInterval + j;
                indices[index++] = (i + 1) * layerInterval + j;
                indices[index++] = i * layerInterval + j + 1;
                indices[index++] = i * layerInterval + j + 1;
                indices[index++] = (i + 1) * layerInterval + j;
                indices[index++] = (i + 1) * layerInterval + j + 1;

            }
        }
        //construct bottom cap
        for (int i = 0; i <= slices; i++) {
            //System.out.println("Bottom Index: " + index + " i: " + i);
            indices[index++] = (stacks-1) * layerInterval + i;
            indices[index++] = stacks * layerInterval + i;
            indices[index++] = (stacks-1) * layerInterval  + 1 + i;
            // System.out.println("0: " + verts[indices[index - 3]].getU() + "," + verts[indices[index - 3]].getV()  + 
            //                     "1: " + verts[indices[index - 2]].getU() + "," + verts[indices[index - 2]].getV() + 
            //                     "2: " + verts[indices[index - 1]].getU() + "," + verts[indices[index - 1]].getV());
        }
        this.mesh = new Mesh(verts, indices, new Material(textureName));
        mesh.initMesh();
    }

    public void destroy() {
        mesh.destroy();
        System.gc(); 
    }

    public Vector4 getPosition() {
        return position;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public float getRadius() {
        return radius;
    }

    public int getSlices() {
        return slices;
    }

    public int getStacks() {
        return stacks;
    }

    public Vector4 getScale() {
        return scale;
    }

    public Vector4 getRotation() {
        return rotation;
    }

}
