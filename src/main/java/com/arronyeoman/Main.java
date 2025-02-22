package com.arronyeoman;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import com.arronyeoman.engine.Camera;
import com.arronyeoman.engine.Window;
import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.engine.gameobjects.shapes.Cube;
import com.arronyeoman.engine.gameobjects.shapes.Ngon;
import com.arronyeoman.engine.gameobjects.shapes.Sphere;
import com.arronyeoman.engine.gameobjects.shapes.WireCube;
import com.arronyeoman.engine.gameobjects.testobjects.PointLight;
import com.arronyeoman.engine.gameobjects.testobjects.WorldFloor;
import com.arronyeoman.engine.io.InputHandler;
import com.arronyeoman.graphics.Line;
import com.arronyeoman.graphics.Mesh;
import com.arronyeoman.graphics.Renderer;
import com.arronyeoman.graphics.Shader;
import com.arronyeoman.maths.B_Spline;
import com.arronyeoman.maths.BezierCurve;
import com.arronyeoman.maths.Vector4;
import com.beust.jcommander.Parameter;


public class Main implements Runnable {

    public Thread gameLoop;
    public int mode = 0;
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
    public Ngon ngon;
    public WorldFloor worldFloor;
    private GameObject target;
    private float[] lightData;
    private PointLight[] pointLights;
    private PointLight sunLight;

    public Sphere sphere;
    public Sphere sphere2;
    public Sphere sphere3;
    public Line line;
    public Line line2;
    public WireCube wireCube;

    public BezierCurve bCurve;
    public BezierCurve bCurve2;
    public Vector4[] bCurvePoints;
    public int bCurveIndex = 0;
    public B_Spline bSpline;
    public Line bLine;

    public Mesh model;

    public Camera camera = new Camera(new Vector4(0f, 0f, 5f, 1f), new Vector4(-0f, 0f, 0f, 1f));

// Arguments class
    class Arguments {
    @Parameter
    private List<String> parameters = new ArrayList<>();
    @Parameter(names = {"--width", "-w"}, description = "Width of the screen")
    public int width = 1080;
    @Parameter(names = {"--height", "-h"}, description = "Height of the screen")
    public int height = 720;
    @Parameter(names = {"--fov", "-f"}, description = "Field of view")
    public float fov = 45.0f;
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
            // System.out.println("Game Initialized");
            // System.out.println("Please select a mode: 0 for 3D, 1 for wireframe, 2 for bezier curve");
            // Scanner scanner = new Scanner(System.in);
            // mode = scanner.nextInt();
            //imstamce of window.java class
            window = new Window(arguments.width, arguments.height, "Game");
            window.setFov(arguments.fov);
            window.setNear(arguments.near);
            window.setFar(arguments.far);
            window.setAspectRatio(arguments.aspectRatio);

            //create shader
            shader = new Shader("shaders/exampleVert.vert", "shaders/exampleFrag.frag");
            lineShader = new Shader("shaders/lineShaderVert.vert", "shaders/lineShaderFrag.frag");
            //create renderer
            renderer = new Renderer(window, shader);
            System.out.println("Renderer created");
            window.setBackGroundColor(0.1f, 0.1f, 0.1f);
            window.create();
            shader.create();
            lineShader.create();


            //@ArronYeoYSJ @TODO: move model creation
            //create cube
            cube = new Cube(2.5f, new Vector4(-2.0f, 1f, 2f), new Vector4(0f, 45f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f), "textures/brickTexture.png");
            cube2 = new Cube(4f, new Vector4(2.0f, 3f, -4f), new Vector4(0f, 5f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f), "textures/multiColor.jpg");
            worldFloor = new WorldFloor(50f, new Vector4(0f, 0f, 0f, 1f),  0.5f, "textures/checkerboard.png");
            
            ngon = new Ngon(12, 2f, "textures/multiColor.jpg", new Vector4(5f, 8f, -3f));

            sphere = new Sphere(1.5f, new Vector4(1f, 2f, 0f), 32, "textures/mercator.jpg");
            sphere2 = new Sphere(2.5f, new Vector4(-6f, 3f, 0f), 32, "textures/grass.png");
            sphere3 = new Sphere(5.5f, new Vector4(10f, 7f, 0f), 40, "textures/multiColor.jpg");

            sunLight = new PointLight(new Vector4(0f, 10f, 0f, 1f), new Vector4(1f, 1f, 1f, 1f), 1f, 100f, true, 0f);
            pointLights = new PointLight[]{sunLight};
            lightData = new float[PointLight.SIZE * pointLights.length];
            //target = wireCube;
            
            bCurvePoints = new Vector4[]{
                new Vector4(-5f, 10f),
                new Vector4(-1f, 21f),
                new Vector4(0.2f, 7.4f),
                new Vector4(4f, 10f)
            };
            bCurve = new BezierCurve(500, bCurvePoints);

            bCurvePoints = new Vector4[]{
                new Vector4(-10f, 10f),
                new Vector4(-16f, 21f),
                new Vector4(-4.8f, 7.4f),
                new Vector4(-1f, 10f)
            };
            bCurve2 = new BezierCurve(25, bCurvePoints);



            Vector4[] curveForDraw = bCurve.getOutput();
            line = new Line(bCurvePoints[0], curveForDraw, "textures/multiColor.jpg");
            line.initLine();

            Vector4[] curveForDraw2 = bCurve2.getOutput();
            line2 = new Line(bCurvePoints[0], curveForDraw2, "textures/multiColor.jpg");
            line2.initLine();

            Vector4[] bControlPoints = new Vector4[] {
                new Vector4(-2f, 3f, 0f, 1f),
                new Vector4(-2.91f, 2.7f, 0f, 1f),
                new Vector4(-2f, 1.3f, 0f, 1f),
                new Vector4(-1.1f, -3.1f, 0f, 1f),
                new Vector4(-2.1f, -2.7f, 0f, 1f),
                new Vector4(-1.9f, 1f, 0f, 1f),
                new Vector4(-2f, 3f, 10f, 1f),
                new Vector4(-2f, 2.9f, 0f, 1f),
                new Vector4(-1.6f, 2.48f, 0f, 1f),
                new Vector4(-0.7f, 2.1f, 0f, 1f),
                new Vector4(-0.8f, 0.6f, 0f, 1f),
                new Vector4(-1.6f, -.88f, 0f, 1f),

            };
            bSpline = new B_Spline(bControlPoints, 2);
            Vector4[] curvePoints = bSpline.sampleCurve(500);
            bLine = new Line(bControlPoints[0], curvePoints, "textures/grass.png");
            bLine.initLine();

        }
// takes place of main in created threAD
    public void run()  {
        Arguments arguments = new Arguments();
        init(arguments);
        mode = 0;
        switch (mode) {
            case 0:
                threeD();
                break;
            case 1:
                wireFrame();
                break;
            case 2:
                break;
            default:
                break;
        }
         // loop ends when code is called in the input handler class
        close();
        window.destroy();
    }

    public static void main(String[] args) {
        new Main().start();
    }

    private void update() {

        //test code to print mouse position on left click
        if (InputHandler.isMouseButtonJustPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
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
        
        FloatBuffer lightBuffer = BufferUtils.createFloatBuffer(PointLight.SIZE);
        lightBuffer.put(sunLight.getLightData());
        lightBuffer.rewind();
        lightBuffer.get(lightData);
        //System.out.println("Light Data: " + lightData[0] + " " + lightData[1] + " " + lightData[2] + " " + lightData[3] + " " + lightData[4] +  " " +lightData[5] + " " + lightData[6] +  " " +lightData[7]);
        
        window.update();
        sunLight.update();

        cube.update();
        cube2.update();
        sphere.update();
        sphere2.update();
        sphere3.update();
        //dragon.update();
        if (target != null) {
            camera.update(target);
        } else {
        camera.update();
        }
        InputHandler.update();
    }

    private void wireFrame() {
        
        wireCube = new WireCube(1f, 0.1f, new Vector4(0f, 0f, -2f), "textures/multiColor.jpg");
        wireCube.initCube();
        renderer.setShader(lineShader);
        ///camera.lockCameraRotation(true);
        //target = wireCube;
        while (!window.shouldClose()) {
            update();
            //renderer.renderLine(wireCube.getFrame(), camera);
            renderer.renderLine(line, camera);
            renderer.renderLine(line2, camera);
            renderer.renderLine(bLine, camera);
            window.swapBuffers();
        }
    }

    private void threeD() {
        while(!window.shouldClose()) {
            update();
            renderer.setShader(shader);
            renderer.renderMesh(worldFloor, camera, lightData);
            renderer.renderMesh(cube, camera, lightData);
            renderer.renderMesh(cube2, camera, lightData);
           // renderer.renderMesh(cube3, camera, lightData);
            //renderer.renderMesh(cube3, camera);
            //renderer.renderMesh(dragon, camera);
            renderer.renderMesh(sphere, camera, lightData);
            renderer.renderMesh(sphere2, camera, lightData);
            renderer.renderMesh(sphere3, camera, lightData);
            renderer.renderMesh(ngon, camera, lightData);
            window.swapBuffers();
        }
    }

    private void close() {
        System.out.println("Game Closed");
        shader.destroy();
        lineShader.destroy();
        
    }   
}