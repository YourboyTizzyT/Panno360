package com.msi.panno360;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by MSI on 18/11/09.
 */

class GLRenderer implements GLSurfaceView.Renderer{
    private Context context;

    private int aPositionHandle;
    private int programId;
    private FloatBuffer vertexBuffer;

    private int uMatrixHandle ;
    private final float[] projectionMatrix=new float[16];//投影矩阵


    public GLRenderer(Context context){
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexShader = ShaderUtils.readRawTextFile(context, R.raw.vertex_shader);
        String fragmentShader= ShaderUtils.readRawTextFile(context, R.raw.fragment_shader);
        programId=ShaderUtils.createProgram(vertexShader,fragmentShader);
        //生成正交矩阵
        uMatrixHandle = GLES20.glGetUniformLocation(programId,"uMatrix");  //获取顶点着色器中的矩阵地址

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //当页面改变时，正交投影相应改变
        float ratio= width>height?(float)width/height:(float)height/width;
        if(width>height){
            Matrix.orthoM(projectionMatrix,0,-ratio,ratio,-1f,1f,-1f,1f);
        }else {
            Matrix.orthoM(projectionMatrix,0,-1f,1f,-ratio,ratio,-1f,1f);
        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(programId);
        GLES20.glUniformMatrix4fv(uMatrixHandle,1,false,projectionMatrix,0);
        GLES20.glEnableVertexAttribArray(aPositionHandle);
        GLES20.glVertexAttribPointer(aPositionHandle, 3, GLES20.GL_FLOAT, false,
                12, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }

    /**
     * Get & Set 方法
     * @return
     */
    public int getaPositionHandle() {
        return aPositionHandle;
    }

    public int getProgramId() {
        return programId;
    }

    public void setVertexBuffer(FloatBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }

}
