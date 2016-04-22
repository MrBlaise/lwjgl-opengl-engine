package engineTester;

import entities.Camera;
import entities.Entity;
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

        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] textureCoords = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0


        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("wood/bark/bark_10.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        System.out.println("GL Vendor    : " + glGetString(GL_VENDOR));
        System.out.println("GL Renderer  : " + glGetString(GL_RENDERER));
        System.out.println("GL Version (string)  : " + glGetString(GL_VERSION));
        int major = glGetInteger(GL_MAJOR_VERSION);
        int minor = glGetInteger(GL_MINOR_VERSION);
        System.out.println("GL Version (integer) : " + major + "." + minor);
        System.out.println("GLSL Version : " + glGetString(GL_SHADING_LANGUAGE_VERSION));


        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-5), 0,0,0,1);

        Camera camera = new Camera();


        fpsCounter.start();
        double t_prev = glfwGetTime();
        while(glfwWindowShouldClose(DisplayManager.getWindow()) == GL_FALSE) {

            double t = glfwGetTime();
            double elapsed = t-t_prev;
            //entity.increaseRotation((float)Math.sin(((float)elapsed)*Math.PI*2),(float)Math.sin(((float)elapsed)*Math.PI*2),0);
            entity.increaseRotation(((float)elapsed/5.0f)*360,((float)elapsed/5.0f)*360,0);
            t_prev = t;

            camera.move();
            renderer.prepare();
            shader.start();
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
