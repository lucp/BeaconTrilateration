package com.agh.eis.mralucp.beacontrilateration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    private List<DrawPoint> points = new ArrayList<DrawPoint>();
    private Paint paint = new Paint();

    private Context context;

    private int width;
    private int height;

    public DrawView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.context = context;

        WindowManager wm = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.width = size.x;
        this.height = size.y;

//        this.setOnTouchListener(this);
//        addPoint(100,50);
//        addPoint(200,50);
//        addPoint(300,50);
//        addPoint(400,50);
//        addPoint(500,50);
//        addPoint(600,50);
//        addPoint(700,50);
//        addPoint(50,100);
//        addPoint(50,200);
//        addPoint(50,300);
//        addPoint(50,400);
//        addPoint(50,500);
//        addPoint(50,600);
//        addPoint(50,700);
//        addPoint(50,800);
//        addPoint(50,900);
//        addPoint(50,1000);
//        addPoint(50,1100);
//        addPoint(50,1200);

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

    @Override
    public void onDraw(Canvas canvas) {
        for (DrawPoint point : points) {
            canvas.drawCircle(point.x, point.y, 5, paint);
        }
        if (!this.points.isEmpty()) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            DrawPoint last = this.points.get(this.points.size() - 1);
            canvas.drawText("x:" + last.x + " y:" + last.y, 20, this.height - 20, paint);
            paint.setColor(Color.RED);
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