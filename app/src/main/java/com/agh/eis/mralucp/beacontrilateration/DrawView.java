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

import com.agh.eis.mralucp.beacontrilateration.model.Beacon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    private List<DrawPoint> points = new ArrayList<DrawPoint>();
    private List<DrawPoint> beaconPoints = new ArrayList<DrawPoint>();
    private Paint paint = new Paint();

    private Context context;

    private int width;
    private int height;

    private double widthRatio;
    private double heightRatio;

    private final static int MARGIN = 100;

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

    void addBeacons(LinkedList<Beacon> beacons) {
        double minWidth = beacons.getFirst().getPositionX();
        double maxWidth = beacons.getFirst().getPositionX();
        double minHeight = beacons.getFirst().getPositionY();
        double maxHeight = beacons.getFirst().getPositionY();

        for (Beacon beacon : beacons) {
            if (beacon.getPositionX() < minWidth) minWidth = beacon.getPositionX();
            if (beacon.getPositionX() > maxWidth) maxWidth = beacon.getPositionX();
            if (beacon.getPositionY() < minHeight) minHeight = beacon.getPositionY();
            if (beacon.getPositionY() > maxHeight) maxHeight = beacon.getPositionY();
        }

        this.widthRatio = (double)(width - 2 * MARGIN) / (maxWidth - minWidth);
        this.heightRatio = (double)(height - 2 * MARGIN) / (maxHeight - minHeight);

        for (Beacon beacon : beacons) {
            DrawPoint point = new DrawPoint();
            point.x = (float)beacon.getPositionX();
            point.y = (float)beacon.getPositionY();
            this.beaconPoints.add(point);
        }
        invalidate();
    }

    void addBeacon(float x, float y) {
        paint.setColor(Color.BLACK);
        DrawPoint point = new DrawPoint();
        point.x = (float)(x * this.widthRatio + MARGIN);
        point.y = (float)(y * this.heightRatio + MARGIN);
        points.add(point);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        double ratio;
        if (this.widthRatio < this.heightRatio) ratio = this.widthRatio;
        else ratio = this.heightRatio;
        paint.setColor(Color.BLACK);
        for (DrawPoint point : this.beaconPoints) {
            canvas.drawCircle((float)(point.x * ratio + MARGIN), (float)(point.y * ratio + MARGIN), 10, paint);
        }
        paint.setColor(Color.RED);
        for (DrawPoint point : this.points) {
            canvas.drawCircle((float)(point.x * ratio + MARGIN), (float)(point.y * ratio + MARGIN), 5, paint);
        }
        if (!this.points.isEmpty()) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            DrawPoint last = this.points.get(this.points.size() - 1);
            canvas.drawText("x:" + last.x + " y:" + last.y, 20, this.height - 20, paint);
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