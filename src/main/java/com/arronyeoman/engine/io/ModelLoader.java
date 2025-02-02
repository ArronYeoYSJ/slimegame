package com.arronyeoman.engine.io;

import com.arronyeoman.graphics.Mesh;
import com.arronyeoman.maths.*;
import com.arronyeoman.graphics.Material;

import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;

public class ModelLoader {

    public static Mesh loadModel(String filePath, String texturePath) {
        AIScene scene = Assimp.aiImportFile(filePath, Assimp.aiProcess_Triangulate | Assimp.aiProcess_JoinIdenticalVertices);
        if (scene == null) {
            System.err.println("Error loading model at " + filePath);
            return null;
        }
        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        int numVertices = mesh.mNumVertices();

        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();

        VertPN[] verts = new VertPN[numVertices];

        for (int i = 0; i < numVertices; i++) {
            AIVector3D vertex = vertices.get(i);
            Vector4 vertexVec = new Vector4(vertex.x(), vertex.y(), vertex.z(), 1);
            AIVector3D normal = normals.get(i);
            Vector4 normalVec = new Vector4(normal.x(), normal.y(), normal.z(), 1);

            //verts[i] = new VertPN(new Vector4(vertex.x(), vertex.y(), vertex.z(), 1), new Vector4(normal.x(), normal.y(), normal.z(), 0));
            Vector4 meshTexCoord = new Vector4(0, 0, 0, 1);

            if (mesh.mNumUVComponents().get(0) != 0){
                AIVector3D texture = mesh.mTextureCoords(0).get(i);
                meshTexCoord.setX(texture.x());
                meshTexCoord.setY(texture.y());
            }

        verts[i] = new VertPN(vertexVec, normalVec, meshTexCoord);
        }
        
        int numFaces = mesh.mNumFaces();
        AIFace.Buffer indicesBuffer = mesh.mFaces();
        int[] indices = new int[numFaces * 3];

        for (int i = 0; i < numFaces; i++) {
            AIFace face = indicesBuffer.get(i);
            indices[i * 3 + 2] = face.mIndices().get(0);
            indices[i * 3 + 1] = face.mIndices().get(1);
            indices[i * 3 + 0] = face.mIndices().get(2);
        }

        return new Mesh(verts, indices, new Material(texturePath));
    }
}
