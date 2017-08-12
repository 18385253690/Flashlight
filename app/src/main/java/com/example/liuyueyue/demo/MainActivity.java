package com.example.liuyueyue.demo;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ToggleButton toggleButton;
    private Camera m_Camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        toggleButton.setOnClickListener(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onClick(View view) {

        File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ToggleButton tb = (ToggleButton)view;
        if(!tb.isChecked()){
            PackageManager pm= this.getPackageManager();
            FeatureInfo[]  features=pm.getSystemAvailableFeatures();
            for(FeatureInfo f : features)
            {
                //判断设备是否支持闪光灯
                if(PackageManager.FEATURE_CAMERA_FLASH.equals(f.name))
                {
                    if ( null == m_Camera )
                    {
                        m_Camera = Camera.open();
                    }

                    Camera.Parameters parameters = m_Camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    m_Camera.setParameters( parameters );
                    m_Camera.startPreview();
                    toggleButton.setBackgroundColor(0x30ffffff);
                }
            }
        }else{
            if ( m_Camera != null )
            {
                m_Camera.stopPreview();
                m_Camera.release();
                m_Camera = null;
            }
            toggleButton.setBackgroundColor(0xffffffff);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    }

