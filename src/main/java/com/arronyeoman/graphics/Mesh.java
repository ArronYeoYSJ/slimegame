package com.arronyeoman.graphics;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.arronyeoman.maths.*;

public class Mesh {
    public boolean usesVertPN = false;
    private  Vertex[] vertices;
    private VertPN[] verticesPN;
    private Material material; 
    private  int[] indices;
    private float[] UVs;
    private Vector4[] normals;
    private  int  vao, pbo, ibo, cbo, tbo, nbo;
    public String textureName;

    public Mesh(Vertex[] vertices, int[] indices, float[] UVs) {
        this.vertices = vertices;
        this.indices = indices;
        this.UVs = UVs;
    }

    public Mesh(VertPN[] vertices, int[] indices, Material material) {
        this.verticesPN = vertices;
        this.indices = indices;
        this.material = material;

        float prevU, prevV;
        boolean seamNegationNeeded = false;

        float[] uvs = new float[vertices.length * 2];
        Vertex[] verts = new Vertex[vertices.length];
        Vector4[] norms = new Vector4[vertices.length];
        prevU = 1f;
        prevV = 1f;
        for (int i = 0; i < vertices.length; i++) {
            //System.out.println("Vertex: " + i);

            float u = vertices[i].getU();
            float v = vertices[i].getV();
            
            //sphere has a seam at the end of the texture, this is a hack to fix it
            // if (u > 0.1f && !seamNegationNeeded){ seamNegationNeeded = true;
            // System.out.println("Seam negation needed");}
            // if (prevV == v)
            // {   
            //     System.out.println("prev u: " + prevU + " u: " + u);
            //     if( u < prevU)
            //     { 
            //         seamNegationNeeded = false;
            //         System.out.println("Seam negation not needed");
            //     }
            // }
            // if (seamNegationNeeded && u == 0f){
            //     //System.out.println("Seam negation applied");
            //     u = 1f;
            // }

            uvs[i * 2] = u;
            uvs[i * 2 + 1] = v;
            verts[i] = vertices[i].getXYZW();
            norms[i] = vertices[i].getNormal();
            //System.out.println("vertex done : " + i);
            prevU = vertices[i].getU();
            prevV = v;
        }
        this.UVs = uvs;
        this.vertices = verts;
        this.normals = norms;
        usesVertPN = true;
    }

    public void initMesh() {
        createMesh(vertices, indices, UVs);
    }

    public void createMesh(Vertex[] vertices, int[] indices, float[] UVs) {
        System.out.println("Creating Mesh");
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        bufferData(vertices, indices, UVs);
        if (usesVertPN) {
            updateNormals(normals);
        }
    }


    public void updatePositions(Vertex[] vertices) {
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
        float [] positionData = new float[vertices.length * 4];
        for (int i = 0; i < vertices.length; i++) {
            Vertex position = vertices[i].getPosition();
            positionData[i * 4] = position.x;
            positionData[i * 4 + 1] = position.y;
            positionData[i * 4 + 2] = position.z;
            //@TODO review this, manually setting w to 1 here fixes a perspective bug but may break stuff later
            positionData[i * 4 + 3] = position.w;
        }
        positionBuffer.put(positionData).flip();
        pbo = storeData(positionBuffer, 0, 4);
        System.out.println("PBO: " + pbo);
    }

    private void updateColours(Vertex[] vertices) {
        //colour buffer
        FloatBuffer colourBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
        //@TODO: review if this can be replaced with func shown in 1st 2025 lecture
        float [] colourData = new float[vertices.length * 4];
        for (int i = 0; i < vertices.length; i++) {
            Vertex colour = vertices[i].getcolour();
           // System.out.println("Colour: " + colour.r + " " + colour.g + " " + colour.b + " " + colour.a);
            colourData[i * 4] = colour.x;
            colourData[i * 4 + 1] = colour.y;
            colourData[i * 4 + 2] = colour.z;
            colourData[i * 4 + 3] = colour.w;
        }
        colourBuffer.put(colourData).flip();

        cbo = storeData(colourBuffer, 1, 4);
        System.out.println("CBO: " + cbo);
    }

    private void updateNormals(Vector4[] normals) {
        FloatBuffer normalBuffer = MemoryUtil.memAllocFloat(normals.length * 4);
        float [] normalData = new float[normals.length * 4];
        for (int i = 0; i < normals.length; i++) {
            Vector4 normal = normals[i];
            normalData[i * 4] = normal.getX();
            normalData[i * 4 + 1] = normal.getY();
            normalData[i * 4 + 2] = normal.getZ();
            normalData[i * 4 + 3] = normal.getW();
        }
        normalBuffer.put(normalData).flip();
        nbo = storeData(normalBuffer, 3, 4);
    }

    private void bufferData(Vertex[] vertices, int[] indices, float[] UVs){
        
        updatePositions(vertices);
        updateColours(vertices);
        

        //texture buffer
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(UVs.length *2);
        textureBuffer.put(UVs).flip();
        tbo = storeData(textureBuffer, 2, 2);

        //index buffer

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        //static draw expects this data wont be changed.
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        //store data in the buffer
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        //static draw expects this data wont be changed.
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        //unbinding the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;

    }

    public void destroy() {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(cbo);
        GL15.glDeleteBuffers(tbo);
        GL15.glDeleteBuffers(nbo);
        GL30.glDeleteVertexArrays(vao);
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVAO() {
        return vao;
    }

    public int getVBO() {
        return pbo;
    }

    public int getIBO() {
        return ibo;
    }

    public int getCBO() {
        return cbo;
    }

    public int getTBO() {
        return tbo;
    }

    public int getTexture() {
        return material.getTextureID();
    }
    
}
