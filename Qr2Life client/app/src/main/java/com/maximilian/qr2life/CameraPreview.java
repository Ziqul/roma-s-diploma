package com.maximilian.qr2life;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.io.IOException;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Maximilian on 01.06.2017.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private ImageScanner scanner;
    private Context context;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.context = context;
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3); //почему именно эти параметры нигде не указано
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        Log.i("Camera", "Constructor finished");
    }

    public CameraPreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.i("CAMERA", "Error setting camera preview: " + e.getMessage());
        }

        Log.i("CAMERA", "surfaceCreated");
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        Display display = ((WindowManager)context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        if(display.getRotation() == Surface.ROTATION_0)
        {
            parameters.setPreviewSize(size.height, size.width);
            mCamera.setDisplayOrientation(90);
        }

        if(display.getRotation() == Surface.ROTATION_90)
        {
            parameters.setPreviewSize(size.width, size.height);
        }

        if(display.getRotation() == Surface.ROTATION_180)
        {
            parameters.setPreviewSize(size.height, size.width);
        }

        if(display.getRotation() == Surface.ROTATION_270)
        {
            parameters.setPreviewSize(size.width, size.height);
            mCamera.setDisplayOrientation(180);
        }

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (Exception e){
            Log.d("CAMERA", "Error starting camera preview: " + e.getMessage());
        }

        Log.i("CAMERA", "surfaceChanged");
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = parameters.getPreviewSize();

        Image codeImage = new Image(size.width, size.height, "Y800");
        String lastScannedCode;
        codeImage.setData(data);
        int result = scanner.scanImage(codeImage);
        if (result != 0) {
            SymbolSet syms = scanner.getResults();
            for (Symbol sym : syms) {
                lastScannedCode = sym.getData();
                if(lastScannedCode.contains("qr4l")) {
                    Log.i("QRDATA", lastScannedCode);
                    Intent intent = new Intent(context, DisplayActivity.class);
                    intent.putExtra("qrData", lastScannedCode);
                    context.startActivity(intent);
                }
            }
        }
    }
}