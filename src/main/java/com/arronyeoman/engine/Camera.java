package com.arronyeoman.engine;

import com.arronyeoman.maths.Vector4;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector4 position, rotation;
    private float moveSpeed = 0.1f;
    private double mouseSensitivity = 0.075f;
    private double oldMouseX, oldMouseY, newMouseX, newMouseY = 0;

    public boolean invertY = false;


    public Camera(Vector4 position, Vector4 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {

        rotate(rotation, mouseMoveToLook(getMouseInput(mouseSensitivity), invertY));

        float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
		float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;
		
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vector4.add(position, new Vector4(0, -moveSpeed, 0));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector4.add(position, new Vector4(-z, 0, x));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector4.add(position, new Vector4(z, 0, -x));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector4.add(position, new Vector4(-x, 0, -z));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector4.add(position, new Vector4(x, 0, z));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector4.add(position, new Vector4(0, moveSpeed, 0));
    }
    private Vector4 mouseMoveToLook(Vector4 mouseMovement, Boolean invertY){
        if(invertY){
            mouseMovement.setY(-mouseMovement.getY());
        }
        Vector4 look = new Vector4( mouseMovement.getY(), mouseMovement.getX());
        return look;
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
