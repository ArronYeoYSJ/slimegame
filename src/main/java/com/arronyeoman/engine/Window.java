package com.arronyeoman.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import com.arronyeoman.engine.io.InputHandler;
import com.arronyeoman.maths.Matrix4x4;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

import java.nio.IntBuffer;

public class Window {
    private static int width, height;
    private String title;
    private long window;
    private boolean resized;
    public boolean fullScreen;

    public int frames;
    public long time;
    public InputHandler inputHandler;
    private GLFWWindowSizeCallback windowSizeCallback;
    private Matrix4x4 projectionMatrix;
    private int minHeight = 300;
    private int minWidth;

    //create floats for background color
    public float bgRed, bgGreen, bgBlue;
    //create floats for camera settings
    private float fov, near, far, aspectRatio;

    private IntBuffer currentHeight = MemoryUtil.memAllocInt(1);
    private IntBuffer currentWidth = MemoryUtil.memAllocInt(1);
    private float prevHeight, prevWidth;

    public boolean isMouseLocked;

//constructor for windwo
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        time = System.currentTimeMillis();
        
    }

    public void create() {
        System.out.println("creatinf window");
        System.out.println("far: " + far);
        if (near <= 0 || far <= near) {
            throw new IllegalArgumentException("Invalid near/far plane values.");
        }
        this.projectionMatrix = Matrix4x4.projection(fov, aspectRatio, near, far);
        //projectionMatrix.logMatrix();

        if (!GLFW.glfwInit()) {
            System.err.println("Error: GLFW initialization failed.");
            System.exit(1);
        }
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);

        //GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        inputHandler = new InputHandler();

        if (window == 0) {
            System.err.println("Error: Window creation failed.");
            System.exit(1);
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        // centre the created window
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        
        // GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        // GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        // GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        // GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE); 
        GLFW.glfwSetWindowSizeLimits(window, ((int)Math.floor(minHeight*aspectRatio)), minHeight, GLFW_DONT_CARE, GLFW_DONT_CARE);

        //create callbacks
        createCallbacks();
        //enable vsync
        GLFW.glfwSwapInterval(1);
        //enable rendering with gl.createCapabilities()
        GL.createCapabilities();
        
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //basic anti-alliasing
        GL11.glEnable(GL13.GL_MULTISAMPLE);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glFrontFace(GL11.GL_CW);

        GLFW.glfwGetWindowSize(window, currentWidth, currentHeight);
        prevHeight = currentHeight.get(0);
        prevWidth = currentWidth.get(0);

        lockMouse();
        
    }

    public void update() {
        GL11.glClearColor(bgRed, bgGreen, bgBlue, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        //poll for any callbacks
        GLFW.glfwPollEvents();
        countFrames();
    }

    private void countFrames() {
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            System.out.println("FPS: " + frames);
            frames = 0;
            time = System.currentTimeMillis();
        }
    }

    private void createCallbacks() {
                //enable input handling
                GLFW.glfwSetKeyCallback(window, inputHandler.getKeyCallback());
                //enable mouse input handling
                GLFW.glfwSetCursorPosCallback(window, inputHandler.getCursorPosCallback());
                //  enable mouse button input handling
                GLFW.glfwSetMouseButtonCallback(window, inputHandler.getMouseButtonCallback());
                //enable window resize input handling
                windowSizeCallback = new GLFWWindowSizeCallback() {
                    @Override
                    public void invoke(long window, int width, int height) {
                        Window.this.width = width;
                        Window.this.height = height;
                        resized = true;
                        preserveAspectRatio(window, width, height);
                    }
                };
                GLFW.glfwSetWindowSizeCallback(window, windowSizeCallback);
        }
    private void preserveAspectRatio(long window, int width, int height) {
        //preserve aspect ratio
        if (width != prevWidth && height != prevHeight) {
            if (width > (int) Math.floor(height * aspectRatio)) {
                GLFW.glfwSetWindowSize(window, width, (int) Math.floor(width / aspectRatio));
            } else {
                GLFW.glfwSetWindowSize(window, (int) Math.floor(height * aspectRatio), height);
            }
        } else if (width != prevWidth && height == prevHeight) {
            GLFW.glfwSetWindowSize(window, width, (int) Math.floor(width / aspectRatio));
        } else if (width == prevWidth && height != prevHeight) {
            GLFW.glfwSetWindowSize(window, (int) Math.floor(height * aspectRatio), height);
        }
        GLFW.glfwGetWindowSize(window, currentWidth, currentHeight);
        resizeViewport();
        //GL11.glViewport(0, 0, currentWidth.get(0), currentHeight.get(0)); 
        prevHeight = currentHeight.get(0);
        prevWidth = currentWidth.get(0);
        resized = false;
    }

    public void setBackGroundColor(float red, float green, float blue) {
        bgRed = red;
        bgGreen = green;
        bgBlue = blue;
    }

    public void swapBuffers() {
        //swaps to the next buffer.
        GLFW.glfwSwapBuffers(window);
    }
    //enable closing of the window
    public boolean shouldClose() {
        //returns true if the window should close, allows closing via window decoration
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        unlockMouse();
        inputHandler.destroy();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
// toggle mouse lock state
    public void toggleMouseLockState(){
        if (isMouseLocked) {
            unlockMouse();
        } else {
            lockMouse();
        }
    }

    private void resizeViewport() {
        GL11.glViewport(0, 0, width, height);
    }
    private void lockMouse() {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        isMouseLocked = true;
    }
    private void unlockMouse() {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
        isMouseLocked = false;
    }

    public void setFullScreen() {
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, videoMode.width(), videoMode.height(), 0);
        fullScreen = true;
        resizeViewport();
    }

    public void setWindowed() {
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowMonitor(window, 0, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2, width, height, 0);
        fullScreen = false;
        resizeViewport();
    }

    public boolean getIsFullScreen() {
        return fullScreen;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }

    public boolean isResized() {
        return resized;
    }

    public int getFrames() {
        return frames;
    }

    public long getTime() {
        return time;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBgRed(float bgRed) {
        this.bgRed = bgRed;
    }
    
    public void setBgGreen(float bgGreen) {
        this.bgGreen = bgGreen;
    }

    public void setBgBlue(float bgBlue) {
        this.bgBlue = bgBlue;
    }

    public Matrix4x4 getProjectionMatrix() {
        return projectionMatrix;
    }
    
}
