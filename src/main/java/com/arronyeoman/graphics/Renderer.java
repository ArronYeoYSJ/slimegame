package com.arronyeoman.graphics;


import org.lwjgl.opengl.GL;
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
              
    }
    public void renderMesh(GameObject gameObject, Camera camera) {
        //System.out.println("Rendering Mesh");
        //enable vertex array and indices array
        texture = gameObject.getMesh().getTexture();
        GL30.glBindVertexArray(gameObject.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL30.glEnableVertexAttribArray(3);
        //@NOTE: use glUniform1i(glGetUniformLocation(program, "textureDataX"), X-1); to assign  specific texture units to uniforms when using multiple textures
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, gameObject.getMesh().getIBO());
        GL15.glActiveTexture(GL15.GL_TEXTURE0);
        GL11.glBindTexture(GL30.GL_TEXTURE_2D, texture);

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
        GL30.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
    }   

    public void renderLine(Line line, Camera camera){
        texture = line.getTexture();
        GL30.glBindVertexArray(line.getVAO());

        GL30.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, line.getIBO());
        GL15.glActiveTexture(GL15.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

        shader.bind();
        Matrix4x4.transform(line.getPosition(), line.getRotation(), line.getScale()).logMatrix();
        //System.out.println("vert shader path: " + shader.getVPath());
        shader.setUniform("model", Matrix4x4.transform(line.getPosition(), line.getRotation(), line.getScale()));
        //shader.setUniform("model", Matrix4x4.identity());
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("view", Matrix4x4.view(camera.getPosition(), camera.getRotation()));
        //Matrix4x4.view(camera.getPosition(), camera.getRotation()).logMatrix();
        //System.out.println("indices: "  + line.getIndices().length);
        GL30.glDrawElements(GL11.GL_LINE_STRIP, line.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        //System.out.println("indices length: " + mesh.getIndices().length);
        //cleanup
        //System.out.println("Drawing line");
        shader.unbind();



        GL15.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL15.glActiveTexture(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);

        GL30.glBindVertexArray(0);
    }

    public void renderMesh(GameObject gameObject, String textureName, Camera camera) {
        //System.out.println("Rendering Mesh");
        //enable vertex array and indices array
        //System.out.println("this shouldnt be running rn");
        try {
            texture = (int) Texture.loadTexture(textureName)[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void setShader(Shader shader) {
        this.shader = shader;
    }
}
