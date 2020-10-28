package com.example.beginnerandroidapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Thread spinnerThread;
    // initial speed is 0
    //then initializes in spinHandler method
    float rotationSpeed = 0;
    //speed in degrees per second
    final float degreesPerSecond = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void handleSpinning(View view) {
        while (spinnerThread != null) {
            spinnerThread.interrupt();
            if(spinnerThread.getState()== Thread.State.TERMINATED){
                spinnerThread = null;
            }
        }
        final ImageView ruslanImageView = view.findViewById(R.id.image_ruslan);
        rotationSpeed = rotationSpeed + degreesPerSecond;

        final float currentRotation = ruslanImageView.getRotation();
        spinnerThread = new Thread(() -> spin(ruslanImageView, currentRotation));
        spinnerThread.start();
    }

    private void spin(ImageView image, float currentRotation) {
        float braking = (float) 0.1;
        try {
            while (rotationSpeed > 0 && !spinnerThread.isInterrupted()) {
                currentRotation = rotationSpeed + currentRotation;
                if(currentRotation > 360){
                    currentRotation = currentRotation - 360;
                }
                image.setRotation(currentRotation);
                rotationSpeed = rotationSpeed - braking;
                Thread.sleep(10);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }


}
