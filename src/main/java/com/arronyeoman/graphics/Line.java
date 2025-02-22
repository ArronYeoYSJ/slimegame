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

public class Line{
    private VertPN[] vertices;
    private Vector4[] vecs;
    private int[] indices;
    //Stupid name to avoid capitalization issues and incorect linting
    // youvees = UVs
    private float[] youvees;
    private Vector4[] normals;
    private int vao, pbo, ibo, cbo, tbo, nbo;
    private int textureID;
    private float[] textureData;
    private Vector4 position;
    private Vector4 scale;
    private Vector4 rotation;
    private Vector4 origin;



    public Line(Vector4 origin, Vector4[] vecs, String textureName) {
        this.vecs = vecs;
        vertices = new VertPN[vecs.length];
        //@TODO calculat midpoint? 
        try{
            textureData = Texture.loadTexture(textureName);
            textureID = (int) textureData[0];
        } catch (Exception e){
            e.printStackTrace();
        }
        youvees = new float[vertices.length * 2];
        indices = new int[vertices.length];
        this.position = origin;
        this.scale = new Vector4(1f, 1f, 1f, 1f);
        this.rotation = new Vector4(0f, 0f, 0f, 1f);

    }

    public void initLine() {
        createLine(origin, vecs);
    }

    private void createLine(Vector4 origin, Vector4[] vecs) {
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        //System.out.println("Creating line with " + vecs.length + " vertices");

        for (int i = 0; i < vecs.length; i++) {
            vertices[i] = new VertPN(
                new Vector4( vecs[i].getX(), vecs[i].getY(), vecs[i].getZ(), 1f), 
                            new Vector4(0, 0, 0, 1),
                            new Vector4((float)(i/vecs.length), 0.5f));
            }
        
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
        float[] positionData = new float[vertices.length * 4];
        for (int i = 0; i < vertices.length; i++) {
            positionData[i * 4] =  vertices[i].getX();
            positionData[i * 4 + 1] = vertices[i].getY();
            positionData[i * 4 + 2] = vertices[i].getZ();
            positionData[i * 4 + 3] = vertices[i].getW();
            //System.out.println("Vertex: " + i);
            //System.out.println("X: " + vertices[i].getX() +  " Y: " + vertices[i].getY()+  " Z: " + vertices[i].getZ()+ " W: " + vertices[i].getW());

            indices[i] = i;

            youvees[i * 2] = vertices[i].getU();
            youvees[(i * 2) + 1] = vertices[i].getV();
        }
        positionBuffer.put(positionData).flip();
        positionBuffer.position(0);

        pbo = storeData(positionBuffer, 0, 4);

        FloatBuffer uvBuffer = MemoryUtil.memAllocFloat(youvees.length);
        uvBuffer.put(youvees).flip();
        uvBuffer.position(0);
        tbo = storeData(uvBuffer, 1, 2);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        indicesBuffer.position(0);
        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
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

    public VertPN[] getVertices() {
        return vertices;
    } 
    public int getVAO() {
        return vao;
    }
    public int getPBO() {
        return pbo;
    }
    public int getIBO() {
        return ibo;
    }
    public int getTexture() {
        return (int) textureData[0];
    }
    public int[] getIndices() {
        return indices;
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