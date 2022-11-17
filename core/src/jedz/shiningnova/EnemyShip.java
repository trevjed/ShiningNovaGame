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
        laser[0] = new Laser(laserMovementSpeed,boundingBox.x +boundingBox.width*0.18f, boundingBox.y-laserHeight,laserWidth,laserHeight,laserTextureRegion);
        laser[1] = new Laser(laserMovementSpeed,boundingBox.x +boundingBox.width*0.82f,boundingBox.y-laserHeight,laserWidth,laserHeight,laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }
    @Override
    public void draw(Batch batch){
        batch.draw(shipTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if(shield >0) {
            batch.draw(shieldTextureRegion, boundingBox.x,boundingBox.y-boundingBox.height*0.2f, boundingBox.width,boundingBox.height);

        }
    }
}
