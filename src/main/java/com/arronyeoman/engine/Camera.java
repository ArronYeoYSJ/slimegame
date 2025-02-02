package com.arronyeoman.engine;

import com.arronyeoman.maths.Vector4;

import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector4 position, rotation;
    private float moveSpeed = 0.1f;
    private float mouseSensitivity = 0.075f;
    private double oldMouseX, oldMouseY, newMouseX, newMouseY = 0;

    public boolean invertY = false;


    public Camera(Vector4 position, Vector4 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {
		newMouseX = InputHandler.getMouseX();
		newMouseY = InputHandler.getMouseY();
		
		float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
		float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;
		
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector4.add(position, new Vector4(-z, 0f, x));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector4.add(position, new Vector4(z, 0f, -x));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector4.add(position, new Vector4(-x, 0f, -z));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector4.add(position, new Vector4(x, 0f, z));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector4.add(position, new Vector4(0, moveSpeed, 0));
		if (InputHandler.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vector4.add(position, new Vector4(0, -moveSpeed, 0));
		
		float dx = (float) (newMouseX - oldMouseX);
		float dy = (float) (newMouseY - oldMouseY);
		
		rotation = Vector4.add(rotation, new Vector4(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));
		
		oldMouseX = newMouseX;
		oldMouseY = newMouseY;
	}

    public Vector4 getPosition() {
        return this.position;
    }

    public Vector4 getRotation() {
        return this.rotation;
    }

    
}
