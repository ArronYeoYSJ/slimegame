package com.arronyeoman.graphics;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import com.arronyeoman.engine.Camera;
import com.arronyeoman.maths.*;
import com.arronyeoman.engine.Window;
import com.arronyeoman.engine.gameobjects.GameObject;



public class Renderer {

    private Shader shader;
    private int texture;
    private Window window;
    public Matrix4x4 testProjectionMatrix;

    public Renderer(Window window, Shader shader) {
        System.out.println("Renderer created");
        this.shader = shader;
        this.window = window;
        float[] testValues = new float[16];
        testValues = new float[] {
            1.0f, 0f, 0f, 0f,
            0f, 1.4f, 0f, 0f,
            0f, 0f, -1f, -2f,
            0f, 0f, -1f, 0f
        };
        testProjectionMatrix = new Matrix4x4(testValues);
        
    }

    public void renderMesh(GameObject gameObject, String textureName, Camera camera) {
        //System.out.println("Rendering Mesh");
        //enable vertex array and indices array
        texture = Texture.loadTexture(textureName);
        GL30.glBindVertexArray(gameObject.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        //@NOTE: use glUniform1i(glGetUniformLocation(program, "textureDataX"), X-1); to assign  specific texture units to uniforms when using multiple textures
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, gameObject.getMesh().getIBO());
        GL15.glActiveTexture(GL15.GL_TEXTURE0);
        GL11.glBindTexture(GL30.GL_TEXTURE_2D, texture);
        //GL11.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);
        //GL11.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE);
        
        
        //GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getCBO());

        shader.bind();
        //Matrix4x4.transform(gameObject.getPosition(), gameObject.getRotation(), gameObject.getScale()).logMatrix();
        shader.setUniform("model", Matrix4x4.transform(gameObject.getPosition(), gameObject.getRotation(), gameObject.getScale()));
        shader.setUniform("projection", window.getProjectionMatrix());
        //window.getProjectionMatrix().logMatrix();
        shader.setUniform("view", Matrix4x4.view(camera.getPosition(), camera.getRotation()));

        GL11.glDrawElements(GL11.GL_TRIANGLES, gameObject.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        //System.out.println("indices length: " + mesh.getIndices().length);
        //cleanup

        shader.unbind();

        GL15.glBindTexture(0, 0);
        GL15.glActiveTexture(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        

    }   
    
}
