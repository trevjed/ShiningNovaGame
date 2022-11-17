package jedz.shiningnova;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerShip extends Ship {


    public PlayerShip(float movementSpeed, int shield, float laserWidth, float laserHeight, float laserMovementSpeed,float timeBetweenShots, float width, float height, float xCenter, float yCenter, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        super(movementSpeed, shield, laserWidth, laserHeight, laserMovementSpeed,timeBetweenShots, width, height, xCenter, yCenter, shipTextureRegion, shieldTextureRegion, laserTextureRegion);
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(laserMovementSpeed, boundingBox.x +boundingBox.width*0.07f,boundingBox.y+boundingBox.height*0.45f,laserWidth,laserHeight,laserTextureRegion);
        laser[1] = new Laser(laserMovementSpeed,boundingBox.x+boundingBox.width*0.93f,boundingBox.y+boundingBox.height*0.45f,laserWidth,laserHeight,laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }
}
