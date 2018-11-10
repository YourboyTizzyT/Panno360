package com.msi.panno360;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private Context context;
    private int aPositionHandle;
    private FloatBuffer vertexBuffer;

    private GLRenderer glRenderer;

    private final float[] vertexData = {
            0f,0f,0f,
            1f,-1f,0f,
            1f,1f,0f
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glSurfaceView = (GLSurfaceView)findViewById(R.id.surface_view);

        glSurfaceView.setEGLContextClientVersion(2);
        glRenderer  = new GLRenderer(this);
        glSurfaceView.setRenderer((GLSurfaceView.Renderer) glRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        aPositionHandle= GLES20.glGetAttribLocation(glRenderer.getProgramId(),"aPosition");

        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);
        glRenderer.setVertexBuffer(vertexBuffer);

    }
}
