package com.androidtv.pongapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.widget.ImageView;

/**
 * Created by mumotiwala on 8/20/14.
 */
public class GameState {

    Context context;
    private final SurfaceHolder surfaceHolder;
    private int _screenWidth;
    private int _screenHeight;

    Bitmap bitmap;
    Bitmap backgroundImage;
    AnimationDrawable animDrawable;
    ImageView imageView;

    public GameState(Context context,SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat);
        backgroundImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.background2);
        imageView = new ImageView(context);
        imageView.setBackgroundResource(R.drawable.ball_animator);
    }

    //The ball
    final int _ballSize = 40;
    int _ballX = 100;
    int _ballY = 100;
    int _ballVelocityX = 4;
    int _ballVelocityY = 4;

    //The bats
    final int _batLength = 275;
    final int _batHeight = 50;
    final int _topBatY = 20;

    final int _batSpeed = 20;

//    DisplayMetrics display = context.getResources().getDisplayMetrics();
//    _screenWidth = display.widthPixels - 100;
//    _screenHeight = display.heightPixels - 100;
    int  _topBatX;
    int  _bottomBatX;
    int _bottomBatY;

    //The update method
    public void update() {

        _ballX += _ballVelocityX;
        _ballY += _ballVelocityY;

  //DEATH!
        if(_ballY > _screenHeight || _ballY < 0)
        {
           // _ballVelocityY *=-1;
            restartScore();
        }
        //Collisions with the sides

        if(_ballX > _screenWidth || _ballX < 0){
            _ballVelocityX *= -1;
            //restartScore();
        }


  //Collisions with the bats

        if(_ballX > _topBatX && _ballX < _topBatX+_batLength && _ballY < _topBatY)
            _ballVelocityY *= -1;  //Collisions with the bats

        if(_ballX > _bottomBatX && _ballX < _bottomBatX+_batLength
                && _ballY > _bottomBatY)
            _ballVelocityY *= -1;
    }

    public void setInitialPosition() {
        _topBatX = (_screenWidth/2) - (_batLength / 2);
         _bottomBatX = (_screenWidth/2) - (_batLength / 2);
        _bottomBatY = _screenHeight - (2 * _batHeight);
        Log.d("Bottom bat Y is at :","Here is bottom Bat Y "+_bottomBatY);
    }

    public void restartScore(){
        _ballX = _screenWidth/2;
        _ballY = _screenHeight/2;
        setInitialPosition();
    }

    public boolean keyPressed(int keyCode, KeyEvent msg)
    {
        if(keyCode == KeyEvent.KEYCODE_BUTTON_X) //left
        {
            _topBatX += _batSpeed;
            _bottomBatX -= _batSpeed;
        }

        if(keyCode == KeyEvent.KEYCODE_BUTTON_A) //right
        {
            _topBatX -= _batSpeed;
            _bottomBatX += _batSpeed;
        }
        return true;
    }


    public void setSurfaceSize(int width, int height)
    {
        synchronized (surfaceHolder)
        {
            _screenWidth = width;
            _screenHeight = height;
            backgroundImage = Bitmap.createScaledBitmap(backgroundImage,width,height,false);
            setInitialPosition();
        }
    }



    public void draw(Canvas canvas, Paint paint) {


    paint.setARGB(200, 0, 200, 0);



        //draw background image on the screen
        canvas.drawBitmap(backgroundImage,0,0,null);

//draw the ball

        canvas.drawRect(new Rect(_ballX,_ballY,_ballX + _ballSize,_ballY + _ballSize),
                paint);

//draw the bats
        Rect topBatRect = new Rect(_topBatX, _topBatY, _topBatX + _batLength,_topBatY + _batHeight);

        canvas.drawBitmap(bitmap,null,topBatRect,null);

//        canvas.drawRect(new Rect(_topBatX, _topBatY, _topBatX + _batLength,
//                _topBatY + _batHeight), paint); //top bat

        Rect bottomBatRect = new Rect(_bottomBatX,_bottomBatY, _bottomBatX + _batLength,_bottomBatY + _batHeight);

        canvas.drawBitmap(bitmap,null,bottomBatRect,null);

//        canvas.drawRect(new Rect(_bottomBatX,_bottomBatY, _bottomBatX + _batLength,
//               _bottomBatY + _batHeight), paint); //bottom bat

    }
}
