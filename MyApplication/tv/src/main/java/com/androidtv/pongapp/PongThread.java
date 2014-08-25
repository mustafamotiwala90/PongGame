package com.androidtv.pongapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

/**
 * Created by mumotiwala on 8/20/14.
 */
public class PongThread extends Thread {

    private SurfaceHolder _surfaceHolder;
    private Paint _paint;
    private GameState _state;

    public PongThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {

        _surfaceHolder = surfaceHolder;
        _paint = new Paint();
        _state = new GameState(context,_surfaceHolder);
    }

    @Override
    public void run() {
        while(true)
        {
            Canvas canvas = _surfaceHolder.lockCanvas();
            _state.update();
            _state.draw(canvas,_paint);
            _surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public GameState getGameState()
    {
        return _state;
    }


}

