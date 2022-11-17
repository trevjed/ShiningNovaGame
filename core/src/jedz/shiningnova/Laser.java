package jedz.shiningnova;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

class Laser {

    //laser physical characteristics
    float movementspeed; //world units per second

    //position and dimensions
    Rectangle boundingBox;

    //graphics
    TextureRegion textureRegion;

    public Laser(float movementspeed, float xCenter, float yBottom, float width, float height, TextureRegion textureRegion) {
        this.movementspeed = movementspeed;

        this.boundingBox = new Rectangle(xCenter - width/2,yBottom,width,height);

        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch){
        //batch.draw(textureRegion,boundingBox.x - boundingBox.width/2,boundingBox.y,boundingBox.width,boundingBox.height);
        batch.draw(textureRegion,boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);

    }

    /*public Rectangle getBoundingBox(){
        return new Rectangle(xPosition,yPosition,width,height);
    }*/

}
