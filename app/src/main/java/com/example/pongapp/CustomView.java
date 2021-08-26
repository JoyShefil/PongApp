package com.example.pongapp;

//import android.app.Application;
import android.content.Context;
//import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
//import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class CustomView extends View {

    private Rect lRect,rRect,bRect,tRect;
    private Paint rectPaint, bPaint, textPaint, textPaint1;
    private float bCircleX;
    private float bCircleY;
    //private float bRectLeft, bRectRight;
    //public Timer bTimer;
    //public TimerTask bTime;
    int directionY=1, directionX, max,score, sScore, rDirection,directionXTemp,DimenX,DimenY;
    float x,y,speed=1,xCoordinate,xCoordinateTemp;
    public boolean toStart=true;
    CustomView CustomView;
    Timer bTimer = new Timer();
   Timer hardTimer = new Timer();
    //Timer pTimer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            moveBallCases();

        }
    };
    TimerTask task1 = new TimerTask() {
        @Override
        public void run() {
            speed = (speed*5)/4;
        }
    };
    /*TimerTask pTask = new TimerTask() {
        @Override
        public void run() {
            if (pRect.bottom<= getHeight()-178) {
                pRect.top = pRect.top + (int) speed;
                pRect.bottom = pRect.bottom + (int) speed;
                postInvalidate();
                powerUps();
            }
        }
    };*/
    MediaPlayer ballHit,win;
    public MediaPlayer lose;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int highScoreEP, highScoreEC, highScoreHP, highScoreHC;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

   /* public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    } */
    private void init(){
        lRect = new Rect();
        rRect = new Rect();
        bRect = new Rect();
        tRect = new Rect();
        //bScore = new Text();
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
       // bTimer = new Timer();
        CustomView = findViewById(R.id.CustomView);
        //bTimer.scheduleAtFixedRate(task,0,2);

        ballHit = MediaPlayer.create(getContext() ,R.raw.shefil);
        win = MediaPlayer.create(getContext(),R.raw.shefil_win);
        lose = MediaPlayer.create(getContext(),R.raw.shefil_lose);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();
        highScoreEP= sharedPreferences.getInt("highScoreEP",0);
        highScoreEC= sharedPreferences.getInt("highScoreEC",0);
        highScoreHP= sharedPreferences.getInt("highScoreHP",0);
        highScoreHC= sharedPreferences.getInt("highScoreHC",0);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (toStart) {
            directionX = -1 + 2 * ((int) Math.round(Math.random() * (1 - 0)));
            if (MainActivity.order.equals("Hard")) {
                if (MainActivity.mode.equals("CM")) {
                    tRect.left = getWidth() / 2 - 100;
                    tRect.right = getWidth() / 2 + 100;
                    //if (time%15000 ==0){
                    // speed = (speed*5)/4;

                    hardTimer.scheduleAtFixedRate(task1, 15000, 15000);
                }
                if (MainActivity.mode.equals("Practice")) {
                    tRect.left = 15;
                    tRect.right = getWidth() - 15;
                    //if (time%15000 ==0){
                    // speed = (speed*5)/4;

                     hardTimer.scheduleAtFixedRate(task1, 10000, 15000);
                }
            }

            //hardTimer.scheduleAtFixedRate(task1,10000,15000);


            if (MainActivity.order.equals("Easy")) {
                if (MainActivity.mode.equals("CM")) {
                    tRect.left = getWidth() / 2 - 100;
                    tRect.right = getWidth() / 2 + 100;
                    max = randBetween(3, 8);
                }
                if (MainActivity.mode.equals("Practice")) {
                    tRect.left = 15;
                    tRect.right = getWidth() - 15;
                }

           }
          bTimer.scheduleAtFixedRate(task, 0, 2);
          toStart = false;
          bCircleX = randBetween(135, getWidth() - 135);
          bCircleY = getHeight() / 2f;
          //tRect.left = getWidth()/2 - 100;
          //tRect.right = getWidth()/2 + 100;
          bRect.left = getWidth() / 2 - 100;
          bRect.right = getWidth() / 2 + 100;
        }


        //canvas.drawColor(Color.parseColor("#4E4F50"));

        if (bTimer == null && !toStart) {
            if (bCircleY  <= 260) {
                textPaint1.setTextSize(150);
                textPaint1.setColor(Color.parseColor("#ffffff"));
                canvas.drawText("You Won!", getWidth() / 2f - 290, getHeight() / 2f - 50, textPaint1);
                canvas.drawText("Score: " + score, getWidth() / 2f - 270, getHeight() / 2f + 110, textPaint1);
            } else {
                textPaint1.setTextSize(150);
                textPaint1.setColor(Color.parseColor("#ffffff"));
                canvas.drawText("You Lost!", getWidth() / 2f - 295, getHeight() / 2f - 50, textPaint1);
                canvas.drawText("Score: " + score, getWidth() / 2f - 270, getHeight() / 2f + 110, textPaint1);
            }


        } else {
            lRect.left = 0;
            //lRect.top = 220;
            lRect.right = 15;
            lRect.bottom = getHeight() - 170;

            textPaint.setTextSize(70);
            textPaint.setColor(Color.WHITE);
            rectPaint.setColor(Color.WHITE);
           // pRectPaint.setColor(Color.GREEN);

            bPaint.setColor(Color.parseColor("#e7d2cc"));

            canvas.drawRect(lRect, rectPaint);
            //canvas.drawRect(pRect,pRectPaint);
           /* if (MainActivity.mode.equals("CM") && MainActivity.order.equals("Hard")){
                if (time%15000 == 0 && time!=0){
                    speed = (speed*5)/4;
                    type = randBetween(1, 2);
                    if (type == 1) {
                        pRectPaint.setColor(Color.GREEN);
                    }
                    if (type == 2) {
                        pRectPaint.setColor(Color.YELLOW);
                    }
                    pRect.left = tRect.left + 90;
                    pRect.right = tRect.left + 110;
                    pRect.top = 230;
                    pRect.bottom = 250;
                    canvas.drawRect(pRect,pRectPaint);
                }
                if (pRect.bottom>0 && pRect.bottom<= getHeight()-178){
                    pRect.top = pRect.top + 5;
                    pRect.bottom = pRect.bottom + 5;
                    postInvalidate();
                    canvas.drawRect(pRect,pRectPaint);
                }
                if (pRect.bottom>= getHeight()-178){
                    powerUps();
                }
            }*/

          /*  if (MainActivity.order.equals("Hard") && MainActivity.mode.equals("CM")){
                if (score%5 == 0 && score !=0) {
                    canvas.drawRect(pRect,pRectPaint);

                   /* if ((getHeight() - 200) >= bCircleY && bCircleY >= (getHeight() - 220)) {
                        if ((bCircleX + 14) >= bRect.left && (bCircleX - 14) <= bRect.right) {
                            type = randBetween(1, 2);
                            if (type == 1) {
                                pRectPaint.setColor(Color.GREEN);
                            }
                            if (type == 2) {
                                pRectPaint.setColor(Color.YELLOW);
                            }
                            power = tRect.left + 100;
                            pRect.left = power - 10;
                            pRect.right = power + 10;
                            pRect.top = 230;
                            pRect.bottom = 250;
                            //pTimer.scheduleAtFixedRate(pTask,0,1);
                        }
                    }
                }
            } */


            rRect.left = getWidth() - 15;
            // rRect.top = 220;
            rRect.right = getWidth();
            rRect.bottom = getHeight() - 170;

            canvas.drawRect(rRect, rectPaint);

            bRect.top = getHeight() - 200;
            bRect.bottom = getHeight() - 170;

        /*if (bRect.right == getWidth() || bRect.left == 0){
            bRect.left = getWidth()/2 - 100;
            bRect.right = getWidth()/2 + 100;

        } */
            if (bRect.right >= (getWidth() - 15)) {
                bRect.right = (getWidth() - 15);
                bRect.left = getWidth() - 215;
            }
            if (bRect.left <= 15) {
                bRect.left = 15;
                bRect.right = 215;
            }
            //bRectLeft = bRect.left;
            //bRectRight = bRect.right;

            canvas.drawRect(bRect, rectPaint);
            if (MainActivity.mode.equals("Practice")) {
                tRect.top = 235;
                rRect.top = 235;
                lRect.top = 235;
            } else {
                tRect.top = 220;
                rRect.top = 220;
                lRect.top = 220;
            }
            tRect.bottom = 250;

        /*if (tRect.right == 0 || tRect.left == 0){
            tRect.left = getWidth()/2 - 100;
            tRect.right = getWidth()/2 + 100;

        } */

            canvas.drawRect(tRect, rectPaint);

       /* if (bCircleX == 0f || bCircleY == 0f){
            bCircleX = randBetween(135, getWidth() - 135);
            bCircleY = getHeight() / 2f;
           // bRect.left = getWidth()/2 - 100;
            //bRect.right = getWidth()/2 + 100;
        } */
            float bRadius = 20f;
            canvas.drawCircle(bCircleX, bCircleY, bRadius, bPaint);

            canvas.drawText("Your Score: " + score, getWidth() - 550, getHeight() - 60, textPaint);

            if (MainActivity.mode.equals("CM")) {
                textPaint1.setTextSize(80);
                textPaint1.setColor(Color.parseColor("#e7d2cc"));
                canvas.drawText("Computer Score:" + sScore, getWidth() - 630, 190, textPaint);
                canvas.drawText("COMPUTER MODE", getWidth() / 2f - 340, 100, textPaint1);
            }
            if (MainActivity.mode.equals("Practice")) {
                canvas.drawText("PRACTICE MODE", getWidth() / 2f - 350, 150, textPaint1);
                textPaint1.setTextSize(100);
                textPaint1.setColor(Color.parseColor("#e7d2cc"));
            }

        }



        /*lRect.left = 0;
        //lRect.top = 220;
        lRect.right=15;
        lRect.bottom = getHeight() - 170;

        textPaint.setTextSize(70);
        textPaint.setColor(Color.WHITE);
        rectPaint.setColor(Color.WHITE);

        bPaint.setColor(Color.parseColor("#e7d2cc"));

        canvas.drawRect(lRect,rectPaint);


        rRect.left = getWidth() - 15;
       // rRect.top = 220;
        rRect.right=getWidth();
        rRect.bottom = getHeight() - 170;

        canvas.drawRect(rRect,rectPaint);

        bRect.top = getHeight() - 200;
        bRect.bottom = getHeight() - 170;

        //if (bRect.right == getWidth() || bRect.left == 0){
            //bRect.left = getWidth()/2 - 100;
           // bRect.right = getWidth()/2 + 100;

        //}
        if (bRect.right >= (getWidth()-15)){
            bRect.right = (getWidth() - 15);
            bRect.left = getWidth() - 215;
        }
        if (bRect.left <= 15){
            bRect.left = 15;
            bRect.right = 215;
        }
        //bRectLeft = bRect.left;
        //bRectRight = bRect.right;

        canvas.drawRect(bRect,rectPaint);
        if (MainActivity.mode.equals("Practice")){
            tRect.top = 235;
            rRect.top = 235;
            lRect.top = 235;
        }else {
            tRect.top = 220;
            rRect.top = 220;
            lRect.top = 220;
        }
        tRect.bottom = 250;

        /*if (tRect.right == 0 || tRect.left == 0){
            tRect.left = getWidth()/2 - 100;
            tRect.right = getWidth()/2 + 100;

        }

        canvas.drawRect(tRect,rectPaint);

       // if (bCircleX == 0f || bCircleY == 0f){
           // bCircleX = randBetween(135, getWidth() - 135);
           // bCircleY = getHeight() / 2f;
           // bRect.left = getWidth()/2 - 100;
            //bRect.right = getWidth()/2 + 100;
        }
        canvas.drawCircle(bCircleX, bCircleY, bRadius, bPaint);

        canvas.drawText("Your Score: " + score, getWidth() - 550, getHeight() - 60, textPaint);

        if (MainActivity.mode.equals("CM")) {
            textPaint1.setTextSize(80);
            textPaint1.setColor(Color.parseColor("#e7d2cc"));
            canvas.drawText("Computer Score:" + sScore, getWidth() - 630, 190, textPaint);
            canvas.drawText("COMPUTER MODE",getWidth()/2f - 340,100,textPaint1);
        }
        if (MainActivity.mode.equals("Practice")){
            canvas.drawText("PRACTICE MODE",getWidth()/2f - 350,150,textPaint1);
            textPaint1.setTextSize(100);
            textPaint1.setColor(Color.parseColor("#e7d2cc"));
        }
    } */
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                return true;
            }

            case MotionEvent.ACTION_MOVE:{
                x = event.getX();
                y = event.getY();

                if ((bRect.left<= x && bRect.right>= x) && ((getHeight() - 450) <= y && y<= getHeight())) {
                    bRect.left = (int) (x - 100);
                    bRect.right = (int) (x + 100);

                    postInvalidate();

                    return true;
                }

                return value;
            }
        }

        return value;
    }

    private void moveBallCases(){
        if (directionY==1){
            if (bCircleX >= (getWidth() - 35) && bCircleY <= (getHeight() - 220)){
                directionX = -1;
                moveBall();
                //ballHit.stop();
                //ballHit.reset();
                ballHit.seekTo(0);
                ballHit.start();
            }
            else if (bCircleX <= 35 && bCircleY <= (getHeight() - 220)){
                directionX = 1;
                moveBall();
                //ballHit.stop();
                //ballHit.reset();
                ballHit.seekTo(0);
                ballHit.start();
            }
            else if ((getHeight()-200) >= bCircleY && bCircleY >= (getHeight()-220)){
                if ((bCircleX + 14) >=bRect.left && (bCircleX - 14) <= bRect.right) {
                    /*if (score%5 == 0 && score !=0) {
                        type = randBetween(1, 2);
                        if (type == 1) {
                            pRectPaint.setColor(Color.GREEN);
                        }
                        if (type == 2) {
                            pRectPaint.setColor(Color.YELLOW);
                        }
                        power = tRect.left + 100;
                        pRect.left = power - 10;
                        pRect.right = power + 10;
                        pRect.top = 230;
                        pRect.bottom = 250;
                        pTimer.scheduleAtFixedRate(pTask,0,3);
                    }*/
                    directionY = -1;
                    score += 1;
                    xCoordinateTemp = bCircleX-15;
                    //score = (int) xCoordinateTemp;
                    directionXTemp = directionX;
                    //ballHit.getAudioSessionId();
                    //moveBall();
                    //score = (int)bCircleY;
                   // ballHit.stop();
                    //ballHit.reset();
                    ballHit.seekTo(0);
                    ballHit.start();
                } moveBall();
            }
            else if ((bCircleX >= (getWidth() +15) || bCircleX + 15 <= 0) && bCircleY >= (getHeight() - 220)){
                //CustomView.setVisibility(INVISIBLE);
                //bTimer = null;
                bTimer.cancel();
                bTimer = null;
                lose.start();
                highScore();
            } else if (bCircleY - 15 >= getHeight()){
               if (MainActivity.order.equals("Hard")){
                 hardTimer.cancel();
                }
                //bTimer = null;
                bTimer.cancel();
                bTimer = null;
                lose.start();
                highScore();

                //bRect.setEmpty();
            } else moveBall();

        }
        else if (directionY == -1) {
            if (bCircleX >= (getWidth() - 35) && bCircleY != 270) {
                directionX = -1;
                //ballHit.stop();
                //ballHit.reset();
                moveBall();
                ballHit.seekTo(0);
                ballHit.start();
            } else if (bCircleX <= 35 && bCircleY != 270) {
                directionX = 1;
                moveBall();
               // ballHit.stop();
                //ballHit.reset();
                ballHit.seekTo(0);
                ballHit.start();
            } else if (bCircleY <= 270 && bCircleX != (getWidth() - 35) && bCircleX != 35) {

                if ((MainActivity.order.equals("Easy") && MainActivity.mode.equals("CM")) && score == max){
                    directionY = -1;
                    if (bCircleY <= 100) {
                        win.start();
                        directionY=0;
                        directionX=0;
                        bTimer.cancel();
                        bTimer = null;
                        highScore();
                    }
                }else {
                    directionY = 1;
                    sScore += 1;
                    //ballHit.stop();
                    //ballHit.reset();
                    ballHit.seekTo(0);
                    ballHit.start();
                }
                moveBall();
            } else if (bCircleX >= (getWidth() - 35) && bCircleY <= 270) {
               // score = (int) bCircleX;
                directionX = -1;
                directionY = 1;
                moveBall();
                sScore += 1;
                //ballHit.stop();
                //ballHit.reset();
                ballHit.seekTo(0);
                ballHit.start();
                //bRect.setEmpty();
            } else if (bCircleX <= 35 && bCircleY <= 270) {
                directionX = 1;
                directionY = 1;
                sScore += 1;
                moveBall();
                //ballHit.stop();
                //ballHit.reset();
                ballHit.seekTo(0);
                ballHit.start();
            } else {
                moveBall();
            }
            if (MainActivity.mode.equals("CM")){
            moveBarCases();
            }
        }
       //score = DimenY;
    }

    private void moveBall(){
        bCircleX = bCircleX + speed*directionX;
        bCircleY = bCircleY + speed*directionY;
        invalidate();

    }
    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));

    }

    private void moveBarCases(){
        DimenX = getWidth() - 30;
        DimenY = getHeight() - 450;
        if (directionXTemp == 1){
            if ((DimenY - (DimenX -xCoordinateTemp)) > DimenX){
                xCoordinate = ((DimenY- (DimenX -xCoordinateTemp))% DimenX)+30;
                //score = (int)xCoordinate;
            } else {
                xCoordinate = (DimenX - (DimenY - (DimenX -xCoordinateTemp))) + 15 ;
                //score = (int)xCoordinate;
            }
        }
        if(directionXTemp == -1) {
            if ((DimenY - xCoordinateTemp) < DimenX){
                xCoordinate = (DimenY - xCoordinateTemp) + 15;
                //score = (int)xCoordinate;
            } else {
                xCoordinate = (DimenX - ((DimenY-xCoordinateTemp)%DimenX));
                //score = (int)xCoordinate;
            }
        }
        easyCase();
    }

    private void  moveBar(){
        if (xCoordinate<=115){
            if (tRect.left>15){
                rDirection = -1;
                barSpeed();
            } else rDirection = 0;
        }
        else if (xCoordinate>= getWidth() - 115){
                if (tRect.right< getWidth() - 15){
                    rDirection = 1;
                    barSpeed();
                } else rDirection = 0;
            }
        else if (xCoordinate> 115 && xCoordinate < getWidth() - 115){
                  if (xCoordinate < tRect.left + 100){
                rDirection = -1;
                barSpeed();
                  }
                  else if (xCoordinate > tRect.left + 100){
                rDirection = 1;
                barSpeed();
                  } else rDirection = 0;
        }
    }
    private void barSpeed(){
        tRect.left = tRect.left + (((int) speed))*rDirection;
        tRect.right = tRect.right + ((int) speed)*rDirection;
        postInvalidate();
    }
    public void easyCase(){
        if (MainActivity.order.equals("Easy") && MainActivity.mode.equals("CM")) {
            if (score == max && (xCoordinate < (tRect.right + 10) && xCoordinate > (tRect.left - 10))) {
                if (xCoordinate < getWidth() / 2f) {
                    rDirection = 1;
                    barSpeed();
                }else if (xCoordinate > getWidth() / 2f) {
                    rDirection = -1;
                    barSpeed();
                }
            }else if (score == max && (xCoordinate > (tRect.right + 10) || xCoordinate < (tRect.left - 10))) {
                rDirection = 0;
                barSpeed();
            }
            else moveBar();
        }
        else moveBar();
    }

    public void highScore(){
        if (MainActivity.mode.equals("Practice")){
            if (MainActivity.order.equals("Easy")){
                if (score>highScoreEP){
                    editor.putInt("highScoreEP",score);
                    editor.commit();
                }
            }
            if (MainActivity.order.equals("Hard")){
                if (score>highScoreHP){
                    editor.putInt("highScoreHP",score);
                    editor.commit();
                }
            }
        }
        if (MainActivity.mode.equals("CM")){
            if (MainActivity.order.equals("Easy")){
                if (score>highScoreEC){
                    editor.putInt("highScoreEC",score);
                    editor.commit();
                }
            }
            if (MainActivity.order.equals("Hard")){
                if (score>highScoreHC){
                    editor.putInt("highScoreHC",score);
                    editor.commit();
                }
            }
        }
    }
   /* public void powerUps(){
        if (pRect.bottom >= getHeight() - 180 && pRect.bottom <= getHeight() - 175){
            if (bRect.left<=pRect.left && bRect.right >=pRect.right){
                //size+=20;
                if (type == 1){
                    size+=20;
                    bRect.left = bRect.left - size;
                    bRect.right = bRect.right + size;
                }
                if (type == 2){
                    speed = speed/2;
                }
            }
            pRect.left = 0;
            pRect.right = 0;
            pRect.top = 0;
            pRect.bottom = 0;
            //pTimer.cancel();
        }

    } */
}
