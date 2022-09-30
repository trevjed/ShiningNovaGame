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
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue3");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed03");


         //setup game objects
         playerShip = new Ship(2,3,10,10,WORLD_WIDTH/2,WORLD_HEIGHT/4, playerShipTextureRegion,playerShieldTextureRegion);
         enemyShip = new Ship(2,1,10,10,WORLD_WIDTH/2,WORLD_HEIGHT*3/4, enemyShipTextureRegion,enemyShieldTextureRegion);



        batch = new SpriteBatch();
    }


     @Override
     public void render(float deltaTime) {
        batch.begin();

        //scrolling background
         renderBackground(deltaTime);

         //enemy ships
         enemyShip.draw(batch);

         //player ship
         playerShip.draw(batch);

         //lasers

         //explosions



        batch.end();


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
