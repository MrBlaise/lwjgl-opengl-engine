package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import misc.FpsCounter;
import models.RawModel;
import models.TexturedModel;
import renderEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;
import util.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;
import static org.lwjgl.opengl.GL30.GL_MAJOR_VERSION;
import static org.lwjgl.opengl.GL30.GL_MINOR_VERSION;

/**
 * Created by Balázs on 4/20/2016.
 */
public class MainGameLoop {

    public static void main(String args[]) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);
        FpsCounter fpsCounter = new FpsCounter(DisplayManager.getWindow());


        RawModel model = OBJLoader.loadObjModel("/objects/dragon.obj", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("objects/white.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        System.out.println("GL Vendor    : " + glGetString(GL_VENDOR));
        System.out.println("GL Renderer  : " + glGetString(GL_RENDERER));
        System.out.println("GL Version (string)  : " + glGetString(GL_VERSION));
        int major = glGetInteger(GL_MAJOR_VERSION);
        int minor = glGetInteger(GL_MINOR_VERSION);
        System.out.println("GL Version (integer) : " + major + "." + minor);
        System.out.println("GLSL Version : " + glGetString(GL_SHADING_LANGUAGE_VERSION));


        Entity entity = new Entity(texturedModel, new Vector3f(0,-1,-30), 0,0,0,1);
        Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));

        Camera camera = new Camera();


        fpsCounter.start();
        double t_prev = glfwGetTime();
        while(glfwWindowShouldClose(DisplayManager.getWindow()) == GL_FALSE) {

            double t = glfwGetTime();
            double elapsed = t-t_prev;
            //entity.increaseRotation((float)Math.sin(((float)elapsed)*Math.PI*2),(float)Math.sin(((float)elapsed)*Math.PI*2),0);
            entity.increaseRotation(0,((float)elapsed/5.0f)*360,0);
            t_prev = t;

            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);

            fpsCounter.updateFPS();
            renderer.render(entity, shader);

            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.clean();
        loader.clean();
        DisplayManager.closeDisplay();
    }
}
