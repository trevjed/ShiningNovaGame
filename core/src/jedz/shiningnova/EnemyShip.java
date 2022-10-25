package jedz.shiningnova;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class EnemyShip extends Ship {


    public EnemyShip(float movementSpeed, int shield, float laserWidth, float laserHeight, float laserMovementSpeed,float timeBetweenShots, float width, float height, float xCenter, float yCenter, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        super(movementSpeed, shield, laserWidth, laserHeight, laserMovementSpeed,timeBetweenShots, width, height, xCenter, yCenter, shipTextureRegion, shieldTextureRegion, laserTextureRegion);
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(laserMovementSpeed,xPosition+width*0.18f,yPosition-laserHeight,laserWidth,laserHeight,laserTextureRegion);
        laser[1] = new Laser(laserMovementSpeed,xPosition+width*0.82f,yPosition-laserHeight,laserWidth,laserHeight,laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }
    @Override
    public void draw(Batch batch){
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
        if(shield >0) {
            batch.draw(shieldTextureRegion, xPosition,yPosition-height*0.2f, width,height);

        }
    }
}
