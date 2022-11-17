package jedz.shiningnova;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {
     //ship characteristics
     float movementSpeed; //world units per second
     int shield;

     //position and dimension

     Rectangle boundingBox;

     //laser info
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

     //graphics
     TextureRegion shipTextureRegion, shieldTextureRegion, laserTextureRegion;

    public Ship(float movementSpeed, int shield,float laserWidth,float laserHeight,float laserMovementSpeed,float timeBetweenShots, float width, float height,float xCenter, float yCenter, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;

        this.boundingBox = new Rectangle(xCenter - width/2,yCenter - height/2,width,height);

        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.shipTextureRegion = shipTextureRegion;
        this.shieldTextureRegion = shieldTextureRegion;
        this.laserTextureRegion= laserTextureRegion;
    }
    public void update(float deltaTime){

        timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser(){
        return (timeSinceLastShot - timeBetweenShots >= 0) ;
    }

    public abstract Laser[] fireLasers();

    public boolean intersects(Rectangle otherRectangle){

        return boundingBox.overlaps(otherRectangle);
    }
    public void hit(Laser laser){
        if(shield > 0){
            shield --;
        }
    }

    public void draw(Batch batch){
        batch.draw(shipTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if(shield >0) {
            batch.draw(shieldTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);

        }
    }
}
