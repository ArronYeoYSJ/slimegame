package com.arronyeoman.graphics.shadows;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import com.arronyeoman.engine.Camera;
import com.arronyeoman.engine.Window;
import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.engine.gameobjects.testobjects.PointLight;
import com.arronyeoman.graphics.Shader;
import com.arronyeoman.maths.Matrix4x4;
import com.arronyeoman.maths.Vector4;

public class ShadowRenderer {

    private Shader shader;
    private int depthMap, fbo, shadowProgram;
    private Window window;
    private int width, height = 2048;
    private PointLight light;

    public ShadowRenderer(PointLight light){
        System.out.println("Shadow Renderer created");
        this.shadowProgram = GL30.glCreateProgram();
        this.depthMap = createDepthTex();
        this.fbo = createFrameBuffer();
        this.shader = new ShadowShader();
        this.light = light;
    }

    public void renderShadows(){
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
        GL11.glViewport(0, 0, width, height);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL30.glUseProgram(shadowProgram);
        shader.setUniform("lightSpaceMatrix", calculateLightSpaceMatrix());

        
    }

    private int createDepthTex(){
        int depthMap = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthMap);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, 0);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        return depthMap;
    }

    private int createFrameBuffer(){
        int fbo = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthMap, 0);
        GL11.glDrawBuffer(GL11.GL_NONE);
        GL11.glReadBuffer(GL11.GL_NONE);
        
        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("Error: Shadow Renderer Framebuffer is not complete");
        }

        return fbo;
    }
    private void unbindFrameBuffer(){
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    private Matrix4x4 calculateLightSpaceMatrix(){
        float near_plane = 1.0f, far_plane = 7.5f;
        Matrix4x4 lightProjection = Matrix4x4.projection(90.0f, 1.0f, near_plane, far_plane);
        Vector4 lightPos = new Vector4(light.getPosition().getX(), light.getPosition().getY(), light.getPosition().getZ());
        Vector4 lightRotation = new Vector4((float)Math.atan(light.getPosition().getY()/light.getPosition().getZ()), (float) Math.atan(light.getPosition().getZ()/light.getPosition().getX()), (float) Math.atan(light.getPosition().getY()/light.getPosition().getX()));
        Matrix4x4 lightView = Matrix4x4.view(lightPos, lightRotation);
        Matrix4x4 lightSpaceMatrix = Matrix4x4.multiply(lightProjection, lightView);
        return lightSpaceMatrix;
    }
}
