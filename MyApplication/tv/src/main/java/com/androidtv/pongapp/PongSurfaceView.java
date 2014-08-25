package com.androidtv.pongapp;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by mumotiwala on 8/20/14.
 */
public class PongSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private PongThread _thread;
    Context context;

    public PongSurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);

        //and instantiate the thread
        _thread = new PongThread(holder, context, new Handler());
    }

//    @Override
//    public boolean onGenericMotionEvent(MotionEvent event) {
//        int eventSource = event.getSource();
//        if ((((eventSource & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) ||
//                ((eventSource & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK))
//                && event.getAction() == MotionEvent.ACTION_MOVE) {
//            int id = event.getDeviceId();
//
//
//        }
//
//        return super.onGenericMotionEvent(event);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD)
                == InputDevice.SOURCE_GAMEPAD) {
            if(keyCode==KeyEvent.KEYCODE_BUTTON_B) {
                ((PongTvActivity)context).finish();
            }
             else
                return _thread.getGameState().keyPressed(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height)
    {
        Log.d("HEIGHT", "Height is : " +height);
        Log.d("WIDTH","Width is : "+width);
        _thread.getGameState().setSurfaceSize(width, height);
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        _thread.interrupt();
    }


    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        _thread.start();
    }
}
