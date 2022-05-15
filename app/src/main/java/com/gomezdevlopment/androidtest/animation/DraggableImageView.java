package com.gomezdevlopment.androidtest.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class DraggableImageView extends androidx.appcompat.widget.AppCompatImageView implements View.OnTouchListener {
    float dX;
    float dY;
    int lastAction;

    public DraggableImageView(Context context) {
        super(context);
    }

    public DraggableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public DraggableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//
//    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - motionEvent.getRawX();
                dY = view.getY() - motionEvent.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(motionEvent.getRawY() + dY);
                view.setX(motionEvent.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    //Toast.makeText(DraggableView.this, "Clicked!", Toast.LENGTH_SHORT).show();
                break;

            default:
                return false;
        }
        return true;
    }
}
