package com.arronyeoman;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.arronyeoman.graphics.Renderer;
import com.beust.jcommander.Parameter;
import com.arronyeoman.engine.GameObject;
import com.arronyeoman.engine.InputHandler;
import com.arronyeoman.engine.Window;
import com.arronyeoman.graphics.*;
import com.arronyeoman.graphics.shapes.*;
import com.arronyeoman.maths.*;


public class Main implements Runnable {

    public Thread gameLoop;
    public static Window window;
    public static InputHandler inputHandler;
    public static Renderer renderer;
    public boolean switchingScreenMode = false;
    public Shader shader;
    public Mesh mesh = new Mesh(new Vertex[]{
        new Vertex(-0.5f, 0.5f, 0.1f, 1.0f),
        new Vertex(0.5f, 0.5f, 0.1f, 1.0f),
        new Vertex(0.5f, -0.5f, 0.1f, 1.0f),
        new Vertex(-0.5f, -0.5f, 0.1f, 1.0f)
    }, new int[] {
        0, 1, 2,
        0, 3, 2
    }, new float[] {
        0, 0,
        1, 0,
        1, 1,
        0, 1
    });
    public Mesh mesh2;

    public GameObject gameObject = new GameObject(mesh, new Vector4(0f,0f ,0f, 0f), new Vector4(1.0f, 1.0f, 1.0f, 1.0f), new Vector4(0f, 0f, 0f, 0f));

// Arguments class
    class Arguments {
    @Parameter
    private List<String> parameters = new ArrayList<>();
    @Parameter(names = {"--width", "-w"}, description = "Width of the screen")
    public int width = 1080;
    @Parameter(names = {"--height", "-h"}, description = "Height of the screen")
    public int height = 720;
}

    public void start() {
        gameLoop = new Thread(this, "Game Loop");
        gameLoop.start();
    }

    public void  init(Arguments arguments) {
            System.out.println("Game Initialized");
            //imstamce of window.java class
            window = new Window(arguments.width, arguments.height, "Game");
            shader = new Shader("src\\resources\\shaders\\exampleVert.vert", "src\\resources\\shaders\\exampleFrag.frag");
            renderer = new Renderer(shader);
            //create window and display
            window.setBackGroundColor(0.5f, 0.1f, 0.1f);
            window.create();
            //create mesh
            mesh.initMesh();

            // //create mesh2
            // Triangle triangle = new Triangle(new Vertex(-0.7f, -0.3f), new Vertex(0.7f, -0.3f), new Vertex(0.7f, 0.8f));
            // triangle.setAlpha(0.88f);
            // //System.out.println("tv1 alpha: " + triangle.vertices[0].a);
            // mesh2 = new Mesh (triangle.getVertices(), 
            // new int[] {
            //     0, 1, 2,
            // }, new float[] {
            //     0, 0,
            //     0.8f, 0,
            //     0.8f, 0.8f,
            //     0, 0
            // }, false, 0.1f, 0.1f);
            // mesh2.initMesh();
            //create shader
            shader.create();

             //test bezier curve
            // Curves curves = new Curves();
            // curves.test(10);


        }
// takes place of main in created threAD
    public void run()  {
        Arguments arguments = new Arguments();
        System.out.println("Game Started");
        init(arguments);
        while (!window.shouldClose()) {
            //System.out.println("Game Running");
            // try {
            //     Thread.sleep(100);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            //mesh.updatePositions(mesh.getVertices());
            update();
            render();
        } // loop ends when code is called in the input handler class
        close();
        window.destroy();
    }

    public static void main(String[] args) {
        new Main().start();
    }
// placeholder loops for later
    private void update() {
        //System.out.println("Game Updated");
        window.update();
        //test code to print mouse position on left click
        if (InputHandler.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("X: " + InputHandler.mouseX + " Y: " + InputHandler.mouseY);
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_F11 ) && !switchingScreenMode) {
            switchingScreenMode = true;
            if (window.getIsFullScreen()) {
                window.setWindowed();
            } else {
                window.setFullScreen();
            }
            switchingScreenMode = false;
        }

        gameObject.update();
    }
    private void render() {
        //System.out.println("Game Rendered");
        //renderer.render();
        renderer.renderMesh(gameObject, "brickTexture.png");
        //renderer.renderMesh(mesh2, "brickTexture.png");
        window.swapBuffers();
    }

    private void close() {
        System.out.println("Game Closed");
        mesh.destroy();
        shader.destroy();
        
    }

    // private void draw(){
    //     //System.out.println("Game Drawn");
        
    // }

    
}