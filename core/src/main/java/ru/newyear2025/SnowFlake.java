package ru.newyear2025;

import static ru.newyear2025.Main.*;

import com.badlogic.gdx.math.MathUtils;

public class SnowFlake {
    public float x;
    public float y;
    public float width;
    public float height;
    float stepX;
    float stepY;
    float rotation;
    float speedRotation;

    static int windSpeed;
    private final static float windSpeedDivider = 5.0f;

    public SnowFlake(){
        respawn();
        this.y = MathUtils.random(0, SCR_HEIGHT);
        speedRotation = MathUtils.random(-0.1f, 0.1f);
        setWindSpeed(0);
    }

    public SnowFlake(int windSpeed){
        this();
        setWindSpeed(windSpeed);
    }

    public static void setWindSpeed(int windSpeed){
        SnowFlake.windSpeed = windSpeed;
    }

    public void fly(){
        x += (stepX + windSpeed / windSpeedDivider); // * на коэф скорости ветра
        y += stepY;
        if (y < - height) respawn();
        rotation += speedRotation;
    }

    void respawn(){
        width = height = MathUtils.random(10, 30);
        this.x = MathUtils.random(-SCR_WIDTH, SCR_WIDTH);
        this.y = MathUtils.random(SCR_HEIGHT+height, SCR_HEIGHT*5/3);
        stepX = MathUtils.random(-0.2f, 0.2f);
        stepY = MathUtils.random(-1f, -0.5f);
    }

    public void event(){

    }

    boolean hit(float tx, float ty){
        return x<tx && tx<x+width && y<ty && ty<y+height;
    }


}
