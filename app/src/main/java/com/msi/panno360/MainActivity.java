package com.msi.panno360;

import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private GLRenderer glRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glSurfaceView= (GLSurfaceView) findViewById(R.id.surface_view);
        glSurfaceView.setEGLContextClientVersion(2);
        glRenderer=new GLRenderer(this, Environment.getExternalStorageDirectory().getPath()+"/360Video/video.mp4");
        glSurfaceView.setRenderer(glRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        glRenderer.getMediaPlayer().release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
        glRenderer.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}