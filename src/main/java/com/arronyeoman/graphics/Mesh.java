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
    private  Vertex[] vertices;
    private  int[] indices;
    private float[] UVs;
    private  int  vao, pbo, ibo, cbo, tbo;
    private Vector4 offsets = new Vector4(0.0f, 0.0f, 0.0f, 0.0f);
    private boolean loop;
    private float offsetXFactor, offsetYFactor;

    public Mesh(Vertex[] vertices, int[] indices, float[] UVs, boolean loop, float offsetXFactor, float offsetYFactor) {
        this.vertices = vertices;
        this.indices = indices;
        this.UVs = UVs;
        this.loop = loop;
        this.offsetXFactor = offsetXFactor;
        this.offsetYFactor = offsetYFactor;
    }

    public void initMesh() {
        createMesh(vertices, indices, UVs);
    }

    public void createMesh(Vertex[] vertices, int[] indices, float[] UVs) {
        System.out.println("Creating Mesh");
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        bufferData(vertices, indices, UVs);
    }

    public void updateMesh() {
        if (loop) {
            loopOffset(offsetXFactor, offsetYFactor);
            bufferData(vertices, indices, UVs);
        }
    }

    public void updatePositions(Vertex[] vertices) {
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
        float [] positionData = new float[vertices.length * 4];
        for (int i = 0; i < vertices.length; i++) {
            Vertex position = vertices[i].getPosition();
            positionData[i * 4] = position.x + offsets.x;
            positionData[i * 4 + 1] = position.y + offsets.y;
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
            System.out.println("Colour: " + colour.r + " " + colour.g + " " + colour.b + " " + colour.a);
            colourData[i * 4] = colour.x;
            colourData[i * 4 + 1] = colour.y;
            colourData[i * 4 + 2] = colour.z;
            colourData[i * 4 + 3] = colour.w;
        }
        colourBuffer.put(colourData).flip();

        cbo = storeData(colourBuffer, 1, 4);
        System.out.println("CBO: " + cbo);
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

    public void loopOffset(float offsetXFactor, float offsetYFactor) {
        //loop through the vertices and offset them
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer offsetX = stack.callocFloat(1);
            FloatBuffer offsetY = stack.callocFloat(1);
            offsets = computePositionOffsets(offsetX, offsetY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public Vector4 computePositionOffsets(FloatBuffer fXOffset, FloatBuffer fYOffset){
        float fLoopDuration = 5.0f;
        float fScale = 3.14159f * 2.0f / fLoopDuration;
        float fElapsedTime = (float) glfwGetTime();
        float fCurrTimeThroughLoop = fElapsedTime % fLoopDuration;
        fXOffset.put(0, (float) Math.cos(fCurrTimeThroughLoop * fScale) * 0.5f);
        fYOffset.put(0, (float) Math.sin(fCurrTimeThroughLoop * fScale) * 0.5f);
        return new Vector4(fXOffset.get(0), fYOffset.get(0), 0.0f, 0.0f);
    }

    public void destroy() {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(cbo);
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
    
}
