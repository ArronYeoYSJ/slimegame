package com.arronyeoman.engine;

import com.arronyeoman.maths.Vector4;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector4 position, rotation;
    private float moveSpeed = 0.5f;
    private double mouseSensitivity = 1;
    private double oldMouseX, oldMouseY, newMouseX, newMouseY = 0;


    public Camera(Vector4 position, Vector4 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {

        rotate(rotation, getMouseInput(mouseSensitivity));

        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            move(new Vector4(0, 0, moveSpeed));
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            move(new Vector4(0, 0, -moveSpeed));
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            move(new Vector4(-moveSpeed, 0, 0));
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            move(new Vector4(moveSpeed, 0, 0));
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            move(new Vector4(0, moveSpeed, 0));
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            move(new Vector4(0, -moveSpeed, 0));
        }
    }

    private Vector4 getMouseInput(double mouseSensitivity) {
        newMouseX = InputHandler.mouseX;
        newMouseY = InputHandler.mouseY;
        Vector4 mouseInput = new Vector4((float) (mouseSensitivity * (newMouseX - oldMouseX)),
         (float) (mouseSensitivity * (newMouseY - oldMouseY)));
        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
        return mouseInput;
    }

    public void move(Vector4 direction) {
        this.position = Vector4.add(position, direction);
    }

    public void rotate(Vector4 camRotation, Vector4 newRotation) {
        this.rotation = Vector4.add(camRotation, newRotation);
    }

    public Vector4 getPosition() {
        return position;
    }

    public Vector4 getRotation() {
        return rotation;
    }

    
}
