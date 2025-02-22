package com.arronyeoman.engine.gameobjects.shapes;

import com.arronyeoman.maths.Vector4;
import com.arronyeoman.graphics.Line;
import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.graphics.Mesh;

public class WireCube implements GameObject{
    private float size;
    private float lineWidth;
    private Vector4 position, rotation, scale, origin;
    private Line frame;
    private String textureName;
    private Vector4 colour = new Vector4(1f, 1f, 1f, 1f);

    public WireCube(float size, float lineWidth, Vector4 position, String textureName){
        this.size = size;
        this.lineWidth = lineWidth;
        this.position = position;
        this.rotation = new Vector4(0f, 0f, 0f, 1f);
        this.scale = new Vector4(1f, 1f, 1f, 1f);
        origin = new Vector4(0f, 0f, 0f, 1f);
        this.textureName = textureName;
    }
    public void create(){
        float half = size / 2.0f;
        Vector4[] points = new Vector4[]{
            new Vector4(-half, half, -half), //0
            new Vector4(half, half, -half), //1
            new Vector4(half, -half, -half), //2
            new Vector4(-half, -half, -half), //3


            new Vector4(-half, half, half), //4
            new Vector4(half, half, half), //5
            new Vector4(half, -half, half), //6
            new Vector4(-half, -half, half), //7
        };
        Vector4[] lines = new Vector4[]{
            points[0], points[1], points[2], points[3], points[0],
            points[4], points[5], points[1], points[0], points[4],
            points[5], points[6], points[2], points[1], points[5],
            points[6], points[7], points[3], points[2], points[6],
            points[7], points[4], points[0], points[3], points[7]
        };
        frame = new Line(position, lines, textureName);
        frame.initLine();
    }
    public Line getFrame(){
        return frame;
    }
    public Mesh getMesh(){
        System.err.println("Wireframes contain lines, not meshes, access the frame with getFrame");
        return null;
    }
    public void initCube(){
        create();
    }
    public Vector4 getPosition(){
        return this.position;
    }
    public Vector4 getScale(){
        return this.scale;
    }
    public Vector4 getRotation(){
        return this.rotation;
    }
    public Vector4 getColour() {
        return this.colour;
    }
    public void setColour(Vector4 colour) {
        this.colour = colour;
    }
    public void setAlpha(float alpha) {
        this.colour.setW(alpha);
    }
    public float getAlpha() {
        return this.colour.getW();
    }
    public void destroy(){
        frame.destroy();
        System.gc();
    }
}
