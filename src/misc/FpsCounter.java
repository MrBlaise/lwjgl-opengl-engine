package misc;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwGetTime;


/**
 * Created by Balázs on 4/20/2016.
 */
public class FpsCounter {

    private long window;
    private double lastFPS;
    private double fps;

    public FpsCounter(long window) {
        this.window = window;
    }

    private double getTime() {
        return glfwGetTime() * 1000;
    }

    public void start() {
        lastFPS = getTime();
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            GLFW.glfwSetWindowTitle(window, "FPS: " + Double.toString(fps));
            fps = 0; //reset the FPS counter
            lastFPS += 1000; //add one second
        }
        fps++;
    }


}
