package com.example.askel.balltest;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Example activity that contains a view that reads accelerometer sensor input and
 * translates a circle based on the changes.
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameView gameView = null;
    private Vibrator vb;
    private  MediaPlayer mp = null;
    private static final int SQUARE = 50;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        gameView = new GameView(this);

        //Set our content to a view, not like the traditional setting to a layout
        setContentView(gameView);
        mp = MediaPlayer.create(this, R.raw.yee);


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gameView.onSensorEvent(event);
        }
    }

    public class GameView extends View {

        private static final int CIRCLE_RADIUS = 25; //pixels

        private final Paint black;
        private final Paint cyan;

        private int viewWidth;
        private int viewHeight;

        private final Ball ball;
        private final Rectangle rect;


        public GameView(Context context) {
            super(context);


            black = new Paint();
            black.setColor(Color.BLACK);
            cyan = new Paint();
            cyan.setColor(Color.CYAN);

            ball = new Ball(viewWidth/2, viewHeight/2, CIRCLE_RADIUS, black);
            rect = new Rectangle(SQUARE, SQUARE, viewWidth-SQUARE, viewHeight-SQUARE, cyan);


        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
            ball.x = viewWidth/2;
            ball.y = viewHeight/2;
            rect.height = viewHeight-SQUARE;
            rect.width = viewWidth-SQUARE;
        }

        public void playSound(){
            if(!mp.isPlaying()) {
                mp.start();
                vb.vibrate(50);
            }// else mp = MediaPlayer.create(getApplicationContext(), R.raw.yee);

        }


        public void onSensorEvent (SensorEvent event) {

            ball.x = ball.x - (int) event.values[0];
            ball.y = ball.y + (int) event.values[1];


            //Make sure we do not draw outside the bounds of the view.
            //So the max values we can draw to are the bounds + the size of the circle
            if (ball.x <= rect.x + ball.radius) {
                ball.x = rect.x + 5*ball.radius;

                playSound();

            }
            if (ball.x >= rect.width - ball.radius) {
                ball. x = rect.width - 5*ball.radius;
                playSound();


            }
            if (ball.y <= rect.y + ball.radius) {
                ball.y = rect.y + 5*ball.radius;
                playSound();

            }
            if (ball.y >= rect.height - ball.radius) {
                ball.y = rect.height - 5*ball.radius;
                playSound();

            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawRect(rect.x, rect.y, rect.width, rect.height, rect.colour);
            canvas.drawCircle(ball.x, ball.y, ball.radius, ball.colour);
            //We need to call invalidate each time, so that the view continuously draws
            invalidate();
        }
    }
}