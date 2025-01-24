package com.arronyeoman.graphics;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {
    private static HashMap<String, Integer> idMap = new HashMap<String, Integer>();

    public static int loadTexture(String textureName){
        int width;
        int height;
        ByteBuffer buffer;
        //System.out.println("Loading texture: "+textureName);
        try (MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            if(idMap.containsKey(textureName)){
                return idMap.get(textureName);
            }    
           // URL url = Texture.class.getResource("resources/textures/"+textureName);
            File file = new File("src\\resources\\textures\\"+textureName);
            //String filePath = "C:\\Users\\arron\\Desktop\\Development\\slimegame\\src\\resources\\textures\\" +textureName;
            String filePath = file.getAbsolutePath();
            buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
            if(buffer ==null) {
                throw new Exception("Can't load file "+textureName+" "+STBImage.stbi_failure_reason());
            }
            width = w.get();
            height = h.get(); 
                
            int id = GL11.glGenTextures();
            idMap.put(textureName, id);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
                
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
            STBImage.stbi_image_free(buffer);
            return id;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
