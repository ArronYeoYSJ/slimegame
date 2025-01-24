package com.arronyeoman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.lwjgl.stb.*;
import java.nio.ByteBuffer;

import com.arronyeoman.graphics.Texture;


public class ResourceLoader {

    public static String loadResourceAsString(String filepath){

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            String line ="";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading resource at: " + filepath);
        }
        
        return output.toString();
        
        }
    

    // public static Texture loadTexture(String filepath) {
    //     STBImage.stbi_set_flip_vertically_on_load(true);
    //     int[] width = new int[1];
    //     int[] height = new int[1];
    //     int[] channels = new int[1];
    //     ByteBuffer image = STBImage.stbi_load(filepath, width, height, channels, 0);
    //     if (image == null) {
    //         System.err.println("Error loading texture: " + filepath);
    //     }
    //     Texture texture = new Texture(width[0], height[0], image);
    //     STBImage.stbi_image_free(image);
    //     return texture;
    // }

    //END
}

// public ObjectOutputStream output;

//     public static ObjectOutputStream loadGroup0() {

//         return null;
//     }

//     public static ObjectOutputStream loadGroup1() {

//         return null;
//     }

//     int createShader(int type, String shaderCode) {
//         int shader = glCreateShader(type);
//         try (MemoryStack stack = MemoryStack.stackPush())
//         {
//             glShaderSource(shader, shaderCode);
            
//             glCompileShader(shader);
            
//             IntBuffer status = stack.mallocInt(1);
//             glGetShaderiv(shader, GL_COMPILE_STATUS, status);
//             if (status.get(0) == GL_FALSE)
//             {
//                 IntBuffer infoLogLength = stack.mallocInt(1);
//                 glGetShaderiv(shader, GL_INFO_LOG_LENGTH, infoLogLength);
                
//                 ByteBuffer infoLog = stack.malloc(infoLogLength.get(0) + 1);
//                 glGetShaderInfoLog(shader, infoLogLength, infoLog);
                
//                 String strShaderType = "";
//                 switch(type)
//                 {
//                     case GL_VERTEX_SHADER: strShaderType = "vertex"; break;
//                     case GL_GEOMETRY_SHADER: strShaderType = "geometry"; break;
//                     case GL_FRAGMENT_SHADER: strShaderType = "fragment"; break;
//                 }
                
//                 System.err.format("Compile failure in %s shader:%s", strShaderType,_bufferToString(infoLog));
//             }
//         }

//         return shader;
//     }

    // private String _bufferToString(ByteBuffer buffer)
    // {
    //     return StandardCharsets.UTF_8.decode(buffer).toString();
    // }

    //  private void _printContentsOfBuffer(FloatBuffer buffer)
    // {
    //     buffer.mark();
    //     System.out.println(buffer.remaining());
    //     float[] floatsA = new float[buffer.capacity()];
    //     buffer.get(floatsA);
    //     buffer.reset();
    //     System.out.println(Arrays.toString(floatsA));
    // }

    // private void _printContentsOfGLArrayBuffer(int bufferObjectIndex)
    // {
    //     try (MemoryStack stack = MemoryStack.stackPush()) {
    //         FloatBuffer fb = stack.callocFloat(12);
    //         glBindBuffer(GL_ARRAY_BUFFER, bufferObjectIndex);
    //         glGetBufferSubData(GL_ARRAY_BUFFER, 0, fb);
    //         float[] floats = new float[fb.capacity()];
    //         fb.get(floats);
    //         System.out.println(Arrays.toString(floats));
    //     }
    // }

