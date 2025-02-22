package com.arronyeoman.graphics.shadows;

import com.arronyeoman.maths.Matrix4x4;
import com.arronyeoman.graphics.Shader;


public class ShadowShader extends Shader {

    private static String vertexFile  = "src/main/java/com/arronyeoman/graphics/shadows/shadowVertex.glsl";
    private static String fragmentFile = "src/main/java/com/arronyeoman/graphics/shadows/shadowFragment.glsl";
    private String vPath;
    public int vertexID, fragmentID, programID;

    private int location_mvpMatrix;

    private Shader shader;

    public ShadowShader(){
        super(vertexFile, fragmentFile);
        shader.create();
        shader.bind();
        location_mvpMatrix = shader.getUniformLocation("mvpMatrix");
        shader.unbind();
    }

    public void setMvpMatrix(Matrix4x4 mvpMatrix){
        shader.setUniform("mvpMatrix", location_mvpMatrix);
    }


}
