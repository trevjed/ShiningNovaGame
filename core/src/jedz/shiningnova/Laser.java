package jedz.shiningnova;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

class Laser {

    //laser physical characteristics
    float movementspeed; //world units per second

    //position and dimensions
    float xPosition, yPosition;// bottom center of the laser
    float width, height;

    //graphics
    TextureRegion textureRegion;

    public Laser(float movementspeed, float xPosition, float yPosition, float width, float height, TextureRegion textureRegion) {
        this.movementspeed = movementspeed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch){
        batch.draw(textureRegion,xPosition - width/2,yPosition,width,height);

    }

    public Rectangle getBoundingBox(){
        return new Rectangle(xPosition,yPosition,width,height);
    }

}
