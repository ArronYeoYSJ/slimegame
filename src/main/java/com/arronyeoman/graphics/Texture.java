package com.arronyeoman.graphics;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import com.arronyeoman.engine.io.ResourceLoader;

public class Texture {
    private static HashMap<String, float[]> idMap = new HashMap<>();

    public static float[] loadTexture(String textureName) throws IOException {
        int width;
        int height;
        ByteBuffer buffer;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            
            if (idMap.containsKey(textureName)) {
                return idMap.get(textureName);
            }
            
            InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(textureName);
            if (inputStream == null) {
                throw new IOException("Error: Could not find texture: " + textureName);
            }
            
            buffer = readInputStreamToByteBuffer(inputStream);
            buffer.flip();
            
            buffer = STBImage.stbi_load_from_memory(buffer, w, h, channels, 4);
            if (buffer == null) {
                throw new IOException("Can't load texture " + textureName + " " + STBImage.stbi_failure_reason());
            }
            
            width = w.get();
            height = h.get();
            
            int id = GL11.glGenTextures();
            idMap.put(textureName, new float[]{id, width, height});
            
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
            
            STBImage.stbi_image_free(buffer);
            return new float[]{id, width, height};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static ByteBuffer readInputStreamToByteBuffer(InputStream inputStream) throws IOException {
        ReadableByteChannel channel = Channels.newChannel(inputStream);
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * 1024 * 1024);
        while (channel.read(buffer) != -1) {}
        return buffer;
    }
}