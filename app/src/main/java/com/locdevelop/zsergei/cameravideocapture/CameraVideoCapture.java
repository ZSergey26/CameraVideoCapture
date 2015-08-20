package com.locdevelop.zsergei.cameravideocapture;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Sergei Zarochentsev on 20.08.2015.
 * Класс который выводит видео с камеры
 */
@SuppressWarnings("deprecation")
public class CameraVideoCapture extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "Surface";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraVideoCapture(Context context, Camera camera) {
        super(context);
        mCamera = camera;


        // Чтобы отслеживать создание и уничтожение объекта Surface
        mHolder = getHolder();
        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public CameraVideoCapture(Context context) {
        super(context);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // После создания Surface выводим в него превью с камеры
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, "Error setting camera preview");
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    /**
     * метод вызывается при каких либо изменениях, например при повороте
     * @param holder
     * @param format
     * @param w
     * @param h
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (mHolder.getSurface() == null){
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "Error stopping camera preview: " + e.getMessage());
        }


        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
