package ru.newyear2025;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1600;
    public static final float SCR_HEIGHT = 900;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;

    private BitmapFont font50;

    private Texture imgBackGround;
    private Texture imgFlake;
    private Music musicElochka1, musicElochka2, musicLisina;
    private int windSpeed = 0;
    SunButton btnRestart;

    SnowFlake[] flakes = new SnowFlake[600];

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        font50 = new BitmapFont(Gdx.files.internal("fonts/comic50.fnt"));
        font50.setColor(Color.BLUE);
        btnRestart = new SunButton("Stop", font50, 440, 345);
        imgBackGround = new Texture("bg.png");
        imgFlake = new Texture("snowflake.png");
        musicElochka1 = Gdx.audio.newMusic(Gdx.files.internal("v-lesu-rodilas-elochka1.mp3"));
        musicElochka2 = Gdx.audio.newMusic(Gdx.files.internal("v-lesu-rodilas-elochka2.mp3"));
        musicLisina = Gdx.audio.newMusic(Gdx.files.internal("lisina.mp3"));

        for (int i = 0; i < flakes.length; i++) {
            flakes[i] = new SnowFlake();
        }
    }


    @Override
    public void render() {
        // касания
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            for (SnowFlake f : flakes) {
                if (f.hit(touch.x, touch.y)) {
                    //this.windSpeed = this.windSpeed == 0 ? 3 : 0;
                    if (this.windSpeed == 0) {
                        this.windSpeed = 10;
                    } else this.windSpeed = 0;
                    SnowFlake.setWindSpeed(windSpeed);
                    f.respawn();
                }
            }

            //System.out.println(touch.x+" "+ touch.y);

            if (btnRestart.hit(touch.x, touch.y)) {
                //function();
                musicElochka1.stop();
                musicElochka2.stop();
                musicLisina.stop();
            }

            if (402 < touch.x && touch.x < 615 && 286 < touch.y && touch.y < 345) {
                //System.out.println("pipi");

            }

            if (520 < touch.x && touch.x < 850 && 585 < touch.y && touch.y < 650) {
                musicElochka1.stop();
                musicElochka2.play();
            }

            if (530 < touch.x && touch.x < 760 && 665 < touch.y && touch.y < 700) {
                musicElochka2.stop();
                musicElochka1.play();
            }
            if (1265 < touch.x && touch.x < 1300 && 534 < touch.y && touch.y < 562) {
                musicElochka1.stop();
                musicElochka2.stop();
                musicLisina.play();
            }
        }


        // события
        for (SnowFlake f : flakes) f.fly();

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (SnowFlake f : flakes) {
            batch.draw(imgFlake, f.x, f.y, f.width / 2, f.height / 2, f.width, f.height, 1, 1, f.rotation, 0, 0, imgFlake.getWidth(), imgFlake.getHeight(), false, false);
        }
        btnRestart.font.draw(batch, btnRestart.text, btnRestart.x, btnRestart.y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgBackGround.dispose();
        imgFlake.dispose();
        musicElochka1.dispose();
        musicElochka2.dispose();
        musicLisina.dispose();
        font50.dispose();
    }
}

