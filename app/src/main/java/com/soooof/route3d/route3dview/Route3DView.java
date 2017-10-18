package com.soooof.route3d.route3dview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.soooof.route3d.R;

/**
 * Created by soooof on 17/10/17.
 */
public class Route3DView extends View {

    private static int DEFAULT_IMG_RES = R.drawable.orange_cut;

    private SplitLine splitLine;
    private Paint paint;
    private Bitmap bitmap;
    private Matrix matrix;
    private Camera camera;
    private int imgSize;

    public Route3DView(Context context) {
        super(context);
        init();
    }

    public Route3DView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Route3DView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(),DEFAULT_IMG_RES);
        matrix = new Matrix();
        splitLine = new SplitLine();
        camera = new Camera();
        imgSize = bitmap.getWidth();
    }


    public void setSplitLine(float angle,float leftFoldAngle,float rightFoldAngle) {
        splitLine.set(angle, leftFoldAngle,rightFoldAngle);
        invalidate();
    }

    public void setRightFoldAngle(float rightFoldAngle) {
        splitLine.rightFoldAngle = rightFoldAngle;
        invalidate();
    }

    public void setLeftFoldAngle(float leftFoldAngle) {
        splitLine.leftFoldAngle = leftFoldAngle;
        invalidate();
    }

    public void setAngle(float angle) {
        splitLine.angle = angle;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        drawRight(canvas,centerX,centerY,imgSize);
        drawLeft(canvas,centerX,centerY,imgSize);
    }

    private void drawRight(Canvas canvas, int centerX, int centerY, int imgSize) {

        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);

        camera.rotateZ(splitLine.angle);
        camera.rotateX(splitLine.rightFoldAngle);
        camera.applyToCanvas(canvas);

        matrix.reset();
        matrix.preRotate(splitLine.angle);
        matrix.preTranslate(-imgSize / 2,-imgSize / 2);
        canvas.save();
        canvas.clipRect(-500, 500, 500, 0);
        canvas.drawBitmap(bitmap,matrix,paint);
        canvas.restore();

        canvas.restore();
        camera.restore();
    }

    private void drawLeft(Canvas canvas, int centerX, int centerY, int imgSize) {
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);

        //旋转至折叠的角度
        camera.rotateZ(splitLine.angle);
        camera.rotateX(splitLine.leftFoldAngle);
        camera.applyToCanvas(canvas);
//        辅助线
//        canvas.drawLine(-500,0,500,0,paint);
//        canvas.drawLine(0,-500,0,500,paint);

        //通过matrix的位移和旋转和clipRect 摆正bitmap并且进行裁剪
        matrix.reset();
        matrix.preRotate(splitLine.angle);
        matrix.preTranslate(-imgSize / 2,-imgSize / 2);
        canvas.save();
        canvas.clipRect(-500, 0, 500, -500);
        canvas.drawBitmap(bitmap,matrix,paint);
        canvas.restore();

        canvas.restore();
        camera.restore();
    }
}
