package textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * Created by Balázs on 4/20/2016.
 */
public class ModelTexture {

    private int textureID;
    private float shineDamper = 1;
    private float reflectivity = 0;

    public ModelTexture(int id) {
        this.textureID = id;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }   public int getTextureID() {
        return this.textureID;
    }

    public static ModelTexture loadTexture(String fileName) {
        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        TextureData textureData = null;
        textureData = TextureData.decodeTextureFile("res/" + fileName);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());
        return new ModelTexture(textureID);
    }
}
