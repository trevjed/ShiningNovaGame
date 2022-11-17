package jedz.shiningnova;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

class GameScreen implements Screen {

     //screen
     private Camera camera;
     private Viewport viewport;

     //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    //private Texture background;
    private TextureRegion[] backgrounds;
    private float backgroundHeight; //height of background in world units

    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion, enemyShipTextureRegion, enemyShieldTextureRegion, playerLaserTextureRegion, enemyLaserTextureRegion;

     //timing
    //private int backgroundOffset;
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;

    //world parameters
    private final int WORLD_WIDTH = 72;
    private  final int WORLD_HEIGHT = 128;

    //gameobjects
    private Ship playerShip;
    private Ship enemyShip;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;


    GameScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up the texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        //setting up the ground
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundHeight = WORLD_HEIGHT * 2;
         backgroundMaxScrollingSpeed = (float)(WORLD_HEIGHT)/ 4;
         //initialize texture regions
        playerShipTextureRegion = textureAtlas.findRegion("playerShip2_blue");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyRed3");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false,true);

        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed03");


         //setup game objects
         playerShip = new PlayerShip(2,6,0.4f,4,45, 0.5f,10,10,WORLD_WIDTH/2,WORLD_HEIGHT/4, playerShipTextureRegion,playerShieldTextureRegion,playerLaserTextureRegion);
         enemyShip = new EnemyShip(2,5,0.3f,5,50, 0.8f,10,10,WORLD_WIDTH/2,WORLD_HEIGHT* 3/4, enemyShipTextureRegion,enemyShieldTextureRegion,enemyLaserTextureRegion);

         playerLaserList = new LinkedList<>();
         enemyLaserList = new LinkedList<>();



        batch = new SpriteBatch();
    }


     @Override
     public void render(float deltaTime) {
        batch.begin();

        playerShip.update(deltaTime);
        enemyShip.update(deltaTime);

        //scrolling background
         renderBackground(deltaTime);

         //enemy ships
         enemyShip.draw(batch);

         //player ship
         playerShip.draw(batch);

         //lasers
         renderLasers( deltaTime);

         //detect collisions between lasers and ships
         detectCollisions();

         //explosions
         renderExplosions(deltaTime);



        batch.end();
     }
     private void detectCollisions(){
         //for each player laser, check whether it intersects an enemy ship
         ListIterator<Laser> iterator = playerLaserList.listIterator();
         while (iterator.hasNext()){
             Laser laser = iterator.next();
             if(enemyShip.intersects(laser.boundingBox)){
                 //contact with enemy ship
                 enemyShip.hit(laser);
                 iterator.remove();
             }
         }


         //for each enemy laser, check whether it intersects a player ship
         iterator = enemyLaserList.listIterator();
         while (iterator.hasNext()){
             Laser laser = iterator.next();
             if(playerShip.intersects(laser.boundingBox)){
                 //contact with player ship
                 playerShip.hit(laser);
                 iterator.remove();
             }
         }

     }

     private void renderExplosions(float deltaTime){

     }
     private void renderLasers(float deltaTime){
         //lasers
         //create new lasers
         //player lasers
         if(playerShip.canFireLaser()){
             Laser[] lasers = playerShip.fireLasers();

             for (Laser laser: lasers){
                 playerLaserList.add(laser);
             }
         }
         //enemy lasers
         if(enemyShip.canFireLaser()){
             Laser[] lasers = enemyShip.fireLasers();

             for (Laser laser: lasers){
                 enemyLaserList.add(laser);
             }
         }

         //draw lasers
         //remove old lasers
         ListIterator<Laser> iterator = playerLaserList.listIterator();
         while(iterator.hasNext()){
             Laser laser = iterator.next();
             laser.draw(batch);
             laser.boundingBox.y += laser.movementspeed*deltaTime;
             if(laser.boundingBox.y > WORLD_HEIGHT){
                 iterator.remove();
             }
         }

         iterator = enemyLaserList.listIterator();
         while(iterator.hasNext()){
             Laser laser = iterator.next();
             laser.draw(batch);
             laser.boundingBox.y -= laser.movementspeed*deltaTime;
             if(laser.boundingBox.y + laser.boundingBox.height < 0){
                 iterator.remove();
             }
         }

     }
     private void renderBackground(float deltaTime){
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed/ 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed/ 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed/ 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for (int layer = 0; layer < backgroundOffsets.length; layer++){
            if(backgroundOffsets[layer]> WORLD_HEIGHT){
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer]+ WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);

        }

     }

     @Override
     public void resize(int width, int height) {
        viewport.update(width, height,true);
        batch.setProjectionMatrix(camera.combined);

     }

     @Override
     public void pause() {

     }

     @Override
     public void resume() {

     }

     @Override
     public void hide() {

     }

     @Override
     public void dispose() {

     }
     @Override
     public void show() {

     }
 }
