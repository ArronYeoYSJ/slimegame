package com.arronyeoman;

import java.util.ArrayList;
import java.util.List;

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
    public Cube cube;
    public Cube cube2;
    public Cube cube3;
    public Cube dragon;
    public WorldFloor worldFloor;

    public Mesh model;

    public Camera camera = new Camera(new Vector4(0f, 0f, 2f, 1f), new Vector4(0f, 0f, 0f, 1f));

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
    public float far = 100.0f;
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
            //create renderer
            renderer = new Renderer(window, shader);
            System.out.println("Renderer created");
            window.setBackGroundColor(0.5f, 0.1f, 0.1f);
            window.create();
            shader.create();
            //System.out.println("Window created");


            //@ArronYeoYSJ @TODO: move model creation
            //create cube
            // cube = new Cube(1f, new Vector4(0.0f, 0.5f, -2f), new Vector4(0f, 0f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f));
            // cube2 = new Cube(3f, new Vector4(4.0f, 2f, -4f), new Vector4(0f, 45f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f));
            // cube3 = new Cube(2f, new Vector4(-2.0f, 1f, -1f), new Vector4(0f, 45f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f));
            // worldFloor = new WorldFloor(20f, new Vector4(0f, 0f, 0f, 1f),  0f);
            model = ModelLoader.loadModel("C:\\Users\\arron\\Desktop\\Development\\slimegame\\src\\resources\\models\\dragon.obj", "grass.png");
            if (model == null) {
                System.out.println("Model is null");
            }
            dragon = new Cube(model);

            //System.out.println("creatinf camera");
            //camera = new Camera(new Vector4(0f, 0f, 0f, 1f), new Vector4(0f, 0f, 0f, 1f));
            //System.out.println("Camera position: " + camera.getPosition().toString());
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
        

        //cube.update();
        camera.update();
    }
    private void render() {
        //System.out.println("Game Rendered");
        //renderer.render();


        // renderer.renderMesh(cube, "brickTexture.png", camera);
        // renderer.renderMesh(worldFloor, "grass.png", camera);
        // renderer.renderMesh(cube2, "brickTexture.png", camera);
        // renderer.renderMesh(cube3, "beautiful.png", camera);
        renderer.renderMesh(dragon, camera);



        window.swapBuffers();
    }

    private void close() {
        System.out.println("Game Closed");
        shader.destroy();
        
    }   
}