package com.agh.eis.mralucp.beacontrilateration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.agh.eis.mralucp.beacontrilateration.model.Beacon;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    List<DrawPoint> points = new ArrayList<DrawPoint>();
    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
    }

    void addPoint(float x, float y) {
        DrawPoint point = new DrawPoint();
        point.x = x;
        point.y = y;
        points.add(point);
        invalidate();
    }

    void addBeacon(float x, float y) {
        paint.setColor(Color.BLACK);
        DrawPoint point = new DrawPoint();
        point.x = x;
        point.y = y;
        points.add(point);
        invalidate();
    }
    @Override
    public void onDraw(Canvas canvas) {
        for (DrawPoint point : points) {
            canvas.drawCircle(point.x, point.y, 5, paint);
        }
    }

//    public boolean onTouch(View view, MotionEvent event) {
//        // if(event.getAction() != MotionEvent.ACTION_DOWN)
//        // return super.onTouchEvent(event);
//        Point point = new Point();
//        point.x = event.getX();
//        point.y = event.getY();
//        points.add(point);
//        invalidate();
//        Log.d(TAG, "point: " + point);
//        return true;
//    }
}

class DrawPoint {
    float x, y;

    @Override
    public String toString() {
        return x + ", " + y;
    }
}