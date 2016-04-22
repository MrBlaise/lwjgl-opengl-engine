package entities;

import util.joml.Vector3f;

/**
 * Created by Balázs on 4/22/2016.
 */
public class Light {

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    private Vector3f position;
    private Vector3f color;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
