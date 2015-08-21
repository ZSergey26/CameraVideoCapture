package com.locdevelop.zsergei.cameravideocapture;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    private static final String TAG = "Camera video capture";

    private Camera mCamera;
    private CameraVideoCapture mPreview;
    private ImageButton recButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);
    }

    /** Безопасное создание объекта камеры
     *
     * @return Camera object. Null if camera is unavailable
     */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "Camera open error");
        }
        return c;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = getCameraInstance();
    }

    public static boolean screenCaptured = false;
    public static boolean firstLaunch = true;

    /**
     * Обработчик нажатия на кнопку
     * @param view Объект на который нажали
     */
    public void showCaptureFromCamera(View view) {

        recButton = (ImageButton) view;

        if (!screenCaptured) {

                if(firstLaunch) {
                    // Вставляем созданный объект в разметку
                    mPreview = new CameraVideoCapture(this, mCamera);
                    FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                    preview.addView(mPreview);
                    screenCaptured = true;
                    firstLaunch = false;

                    recButton.setImageResource(R.drawable.ic_videocam_off_white_24dp);
                } else {
                    mPreview.startCapturing();
                    screenCaptured = true;
                    recButton.setImageResource(R.drawable.ic_videocam_off_white_24dp);

                }
        }
        else
        {
            mPreview.stopCapturing();
            screenCaptured = false;

            recButton.setImageResource(R.drawable.ic_videocam_white_24dp);

        }
    }

}
