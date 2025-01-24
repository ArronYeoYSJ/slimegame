package com.arronyeoman.graphics;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import com.arronyeoman.graphics.Texture;
import com.arronyeoman.maths.*;



public class Renderer {

    private Shader shader;

    private int texture;

    private double temp;
    private double cycleScale;
    private Vector4 scaleVector;

    public Renderer(Shader shader) {
        System.out.println("Renderer created");
        this.shader = shader;
    }

    public void renderMesh(Mesh mesh, String textureName) {
        //System.out.println("Rendering Mesh");
        temp += 0.02;
        cycleScale = Math.sin(temp);
        scaleVector = new Vector4((float)cycleScale, (float)cycleScale, 1.0f, 1.0f);
        //enable vertex array and indices array
        texture = Texture.loadTexture(textureName);
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glActiveTexture(GL15.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

        //@NOTE: use glUniform1i(glGetUniformLocation(program, "textureDataX"), X-1); to assign  specific texture units to uniforms when using multiple textures
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        //GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getCBO());

        shader.bind();
        shader.setUniform("scale", scaleVector);

        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
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
