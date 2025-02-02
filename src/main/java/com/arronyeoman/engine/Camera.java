package com.arronyeoman.engine;

import com.arronyeoman.engine.gameobjects.GameObject;
import com.arronyeoman.engine.io.InputHandler;
import com.arronyeoman.maths.Vector4;

import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector4 position, rotation;
    private float moveSpeed = 0.1f;
    private float mouseSensitivity = 0.075f;
    private double oldMouseX, oldMouseY, newMouseX, newMouseY = 0;

    //3rd person related variables
    public GameObject target;
    public float distanceFromTarget = 4;
    public float angleAroundTarget = 0;
    public float pitch = 20;
    public float yaw = 0;
    public float roll = 0;
    public float maxPitch = 60;
    public float minPitch = 0;


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

    //3rd person camera
    public void update(GameObject target){
        newMouseX = InputHandler.getMouseX();
        newMouseY = InputHandler.getMouseY();

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        if (angleAroundTarget > 360 || angleAroundTarget < -360) {
            angleAroundTarget = 0;
        }
        if (pitch > maxPitch) {
            pitch = maxPitch;
        }
        if (pitch < minPitch) {
            pitch = minPitch;
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            angleAroundTarget += 10 * mouseSensitivity;
            yaw += 10 * mouseSensitivity;
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            angleAroundTarget -= 10 * mouseSensitivity;
            yaw -= 10 * mouseSensitivity;
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            pitch += 10 * mouseSensitivity;
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            pitch -= 10 * mouseSensitivity;
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_G) && distanceFromTarget > 1) {
            distanceFromTarget -= 0.1f;
        }
        if (InputHandler.isKeyDown(GLFW.GLFW_KEY_H) && distanceFromTarget < 10) {
            distanceFromTarget += 0.1f;
        }


        float horizontalDistance = (float) (distanceFromTarget * Math.cos(Math.toRadians(pitch)));
        float verticalDistance = (float) (distanceFromTarget * Math.sin(Math.toRadians(pitch)));
    
        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-yaw)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-yaw)));

        // float rollX = (float) (horizontalDistance * Math.sin(Math.toRadians(roll)));
        // float rollZ = (float) (horizontalDistance * Math.cos(Math.toRadians(roll)));

        
        position.setX(target.getPosition().getX() - xOffset);
        position.setY(target.getPosition().getY() + verticalDistance);
        position.setZ(target.getPosition().getZ() - zOffset);
        rotation.setXYZ(- pitch, 180 -yaw , 0);

        
    }

    public Vector4 getPosition() {
        return this.position;
    }

    public Vector4 getRotation() {
        return this.rotation;
    }

    
}
