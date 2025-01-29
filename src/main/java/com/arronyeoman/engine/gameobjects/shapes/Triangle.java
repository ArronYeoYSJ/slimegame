package com.arronyeoman.engine.gameobjects.shapes;

import com.arronyeoman.maths.Vertex;

public class Triangle {
    public Vertex[] vertices;

    public Triangle(Vertex a, Vertex b, Vertex c) {
        vertices = new Vertex[] {a, b, c};
    }

    public void setColour(float r, float g, float b, float a){
        for (Vertex v : vertices) {
            v.setColour(r, g, b, a);
        }
    };

    public void setColour(float r, float g, float b){
        for (Vertex v : vertices) {
            v.setColour(r, g, b, 1.0f);
        }
    };

    public void setVertexColour(int i, float r, float g, float b, float a){
        vertices[i].setColour(r, g, b, a);
    };

    public void setAlpha(float a){
        for (Vertex v : vertices) {
            v.setColour(v.r, v.g, v.b, a);
        }
    };

    public Vertex[] getVertices() {
        return vertices;
    }
}
