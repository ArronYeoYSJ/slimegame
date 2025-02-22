package com.arronyeoman.engine.gameobjects.testobjects;

import com.arronyeoman.maths.Vector4;

public class PointLight {

    public static final int SIZE = 8;

    private Vector4 position;
    private Vector4 colour;
    private float intensity;
    private float range;
    private boolean isSun = false;
    private float timeDegrees = 0f;

    public PointLight(Vector4 position, Vector4 colour, float intensity, float range) {
        this.position = position;
        this.colour = colour;
        this.intensity = intensity;
        this.range = range;
    }

    public PointLight(Vector4 position, Vector4 colour, float intensity, float range, boolean isSun, float timeDegrees) {
        this.position = position;
        this.colour = colour;
        this.intensity = intensity;
        this.range = range;
        this.isSun = isSun;
        this.timeDegrees = timeDegrees;
    }

    public void update() {
        if (isSun) {
            // 0.1f gives the sun a 60 second "day"
            timeDegrees += 0.05f;
            sunCycle(timeDegrees);
        }
    }

    public void sunCycle(float timeDegrees) {
        if (timeDegrees > 360) {
            timeDegrees = 0;
        }
        float x = (float) Math.cos(Math.toRadians(timeDegrees));
        float y = (float) Math.sin(Math.toRadians(timeDegrees));
        //offset so the sun arcs in the sky
        float z = 0.1f * y;
        float sunsetFactor = 1.0f;
        setPosition(new Vector4(x, y, z, 1.0f));
        if (timeDegrees <15f || (timeDegrees < 180f && timeDegrees > 165f)){
            sunsetFactor = 0.74f + y;
            setIntensity(sunsetFactor);
        } else if (timeDegrees > 15f && timeDegrees < 165f){
            setIntensity(1.0f);
            sunsetFactor = 1.0f;
        }
        else {
            setIntensity(0.0f);
        }
        float sunsetIntensity = getIntensity() * sunsetFactor;
        setIntensity(sunsetIntensity);
        Vector4 sunsetColour = new Vector4(1.0f, 1.0f*sunsetFactor, 1.0f*sunsetFactor, 1.0f);
        setColor(sunsetColour);

    }

    public Vector4 getPosition() {
        return position;
    }

    public Vector4 getColor() {
        return colour;
    }

    public void setPosition(Vector4 position) {
        this.position = position;
    }

    public void setColor(Vector4 colour) {
        this.colour = colour;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getIntensity() {
        return intensity;
    }

    public float getRange() {
        return range;
    }

    public float[] getLightData() {
        return new float[] {position.getX(), position.getY(), position.getZ(), colour.getX(), colour.getY(), colour.getZ(), intensity, range};
    }

}
