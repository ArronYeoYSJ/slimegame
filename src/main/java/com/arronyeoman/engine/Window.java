package com.arronyeoman.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Window {
    private int width, height;
    private String title;
    private long window;
    private boolean resized;
    public boolean fullScreen;

    public int frames;
    public long time;
    public InputHandler inputHandler;
    private GLFWWindowSizeCallback windowSizeCallback;

    //create floats for background color
    public float bgRed, bgGreen, bgBlue;

//constructor for windwo
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        time = System.currentTimeMillis();
    }

    public void create() {
        System.out.println("creatinf window");
        if (!GLFW.glfwInit()) {
            System.err.println("Error: GLFW initialization failed.");
            System.exit(1);
        }
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);

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

        //create callbacks
        createCallbacks();
        //enable vsync
        GLFW.glfwSwapInterval(1);
        //enable rendering with gl.createCapabilities()
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void update() {
        GL11.glClearColor(bgRed, bgGreen, bgBlue, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        //poll for any callbacks
        GLFW.glfwPollEvents();
        countFrames();

        if (resized) {
            GL11.glViewport(0, 0, width, height);
            resized = false;
        }
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
                GLFW.glfwSetWindowSizeCallback(window, windowSizeCallback);
                windowSizeCallback = new GLFWWindowSizeCallback() {
                    @Override
                    public void invoke(long window, int width, int height) {
                        Window.this.width = width;
                        Window.this.height = height;
                        resized = true;
                    }
                };
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
        inputHandler.destroy();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setFullScreen() {
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, videoMode.width(), videoMode.height(), 0);
        fullScreen = true;
    }

    public void setWindowed() {
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowMonitor(window, 0, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2, width, height, 0);
        fullScreen = false;
    }

    public boolean getIsFullScreen() {
        return fullScreen;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
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


    public float getBgRed() {
        return bgRed;
    }

    public float getBgGreen() {
        return bgGreen;
    }

    public float getBgBlue() {
        return bgBlue;
    }

    public int getFrames() {
        return frames;
    }

    public long getTime() {
        return time;
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
    
}
