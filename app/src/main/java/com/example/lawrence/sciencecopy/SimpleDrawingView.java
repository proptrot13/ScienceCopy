package com.example.lawrence.sciencecopy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class SimpleDrawingView extends View {

    Bitmap image;
    TransformCompare compare;
    Paint drawPaint;

    public SimpleDrawingView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        image = (BitmapFactory.decodeResource(getResources(), R.drawable.pixelgun));
        compare = new TransformCompare(image, image, 10);
        drawPaint = new Paint();

        if(compare.rotation(image, 89.9999999).sameAs(compare.rotation(image, 90))) {
            Log.d("MainActivity", "True");
        }
        else {
            Log.d("MainActivity", "False");
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(compare.translation(image, 100, 45) , 0, 0, drawPaint);
    }

}