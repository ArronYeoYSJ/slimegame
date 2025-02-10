package com.arronyeoman;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.lwjgl.glfw.GLFW;

import com.arronyeoman.graphics.Renderer;
import com.beust.jcommander.Parameter;
import com.arronyeoman.engine.Camera;
import com.arronyeoman.engine.Window;
import com.arronyeoman.engine.gameobjects.shapes.*;
import com.arronyeoman.engine.gameobjects.testobjects.WorldFloor;
import com.arronyeoman.engine.io.InputHandler;
import com.arronyeoman.engine.io.ModelLoader;
import com.arronyeoman.graphics.*;
import com.arronyeoman.maths.*;


public class Main implements Runnable {

    public Thread gameLoop;
    public static Window window;
    public static InputHandler inputHandler;
    public static Renderer renderer;
    public boolean switchingScreenMode = false;
    public Shader shader;
    public Shader lineShader;
    public Cube cube;
    public Cube cube2;
    public Cube cube3;
    public Cube dragon;
    public WorldFloor worldFloor;

    public Sphere sphere;
    public Sphere sphere2;
    public Line line;

    public Mesh model;

    public Camera camera = new Camera(new Vector4(0f, 6f, 4f, 1f), new Vector4(-45f, 0f, 0f, 1f));

// Arguments class
    class Arguments {
    @Parameter
    private List<String> parameters = new ArrayList<>();
    @Parameter(names = {"--width", "-w"}, description = "Width of the screen")
    public int width = 1080;
    @Parameter(names = {"--height", "-h"}, description = "Height of the screen")
    public int height = 720;
    @Parameter(names = {"--fov", "-f"}, description = "Field of view")
    public float fov = 70.0f;
    @Parameter(names = {"--near", "-n"}, description = "Near plane")
    public float near = 0.1f;
    @Parameter(names = {"--far", "-r"}, description = "Far plane")
    public float far = 1000.0f;
    @Parameter(names = {"--aspectRatio", "-a"}, description = "Aspect ratio")
    public float aspectRatio = 16.0f / 9.0f;
}

    public void start() {
        gameLoop = new Thread(this, "Game Loop");
        gameLoop.start();
    }

    public void  init(Arguments arguments) {
            System.out.println("Game Initialized");
            //imstamce of window.java class
            window = new Window(arguments.width, arguments.height, "Game");
            window.setFov(arguments.fov);
            window.setNear(arguments.near);
            window.setFar(arguments.far);
            window.setAspectRatio(arguments.aspectRatio);

            //create shader
            shader = new Shader("src\\resources\\shaders\\exampleVert.vert", "src\\resources\\shaders\\exampleFrag.frag");
            lineShader = new Shader("src\\resources\\shaders\\lineShaderVert.vert", "src\\resources\\shaders\\lineShaderFrag.frag");
            //create renderer
            renderer = new Renderer(window, shader);
            System.out.println("Renderer created");
            window.setBackGroundColor(0.5f, 0.1f, 0.1f);
            window.create();
            shader.create();
            lineShader.create();
            //System.out.println("Window created");


            //@ArronYeoYSJ @TODO: move model creation
            //create cube
            cube = new Cube(1f, new Vector4(0.0f, 0.5f, -2f), new Vector4(0f, 0f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f));
            // cube2 = new Cube(3f, new Vector4(4.0f, 2f, -4f), new Vector4(0f, 45f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f));
            // cube3 = new Cube(2f, new Vector4(-2.0f, 1f, -1f), new Vector4(0f, 45f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f));
            worldFloor = new WorldFloor(20f, new Vector4(0f, 0f, 0f, 1f),  0f);
            
            line = new Line(new Vector4(0.321f, 0.123f, -1.01f),
            new Vector4[]{
                new Vector4(0.52f,0.2f,0.20f),
                new Vector4(-1f,-0.0f,1.0f),
                new Vector4(-0.2f,-0.0f,-0f),
                
            }, "multiColor.jpg");
            line.initLine();
            
            sphere = new Sphere(1.5f,  new Vector4(1f, 2f, 0f), 32, "mercator.jpg");
            sphere2 = new Sphere(2.5f,  new Vector4(-6f, 2f, 0f), 32, "grass.png");

        }
// takes place of main in created threAD
    public void run()  {
        Arguments arguments = new Arguments();
        System.out.println("Game Started");
        init(arguments);
        while (!window.shouldClose()) {
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
        if (InputHandler.isKeyJustPressed(GLFW.GLFW_KEY_M)) {
            window.toggleMouseLockState();
            System.out.println("Mouse locked: " + window.isMouseLocked);
        }
        

        cube.update();
        sphere.update();
        sphere2.update();
        //dragon.update();
        camera.update();
    }
    private void render() {

       // renderer.renderMesh(cube, "brickTexture.png", camera);
        //renderer.renderMesh(worldFloor, "grass.png", camera);
        // renderer.renderMesh(cube2, "brickTexture.png", camera);
        // renderer.renderMesh(cube3, "beautiful.png", camera);
        //renderer.renderMesh(dragon, camera);
        renderer.setShader(shader);
        renderer.renderMesh(sphere, camera);
        renderer.renderMesh(sphere2, camera);
        //System.out.println("Rendering Line");
        renderer.setShader(lineShader);
        renderer.renderLine(line, camera);
        window.swapBuffers();
    }

    private void close() {
        System.out.println("Game Closed");
        shader.destroy();
        
    }   
}