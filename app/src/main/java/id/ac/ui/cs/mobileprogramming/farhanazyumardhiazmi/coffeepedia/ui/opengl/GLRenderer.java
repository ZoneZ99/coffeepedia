package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer {

	private Square mSquare;

	private final float[] rotationMatrix = new float[16];

	private final float[] vPMatrix = new float[16];
	private final float[] projectionMatrix = new float[16];
	private final float[] viewMatrix = new float[16];

	public volatile float mAngle;

	public float getAngle() {
		return mAngle;
	}

	public void setAngle(float angle) {
		mAngle = angle;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		mSquare = new Square();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		float[] scratch = new float[16];

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		// Set the camera position (View matrix)
		Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

		// Calculate the projection and view transformation
		Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

		// Create a rotation transformation for the square
		Matrix.setRotateM(rotationMatrix, 0, mAngle, 0, 0, -1.0f);

		// Combine the rotation matrix with the projection and camera view
		// Note that the vPMatrix factor *must be first* in order
		// for the matrix multiplication product to be correct.
		Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);

		mSquare.draw(scratch);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		float ratio = (float) width / height;

		// this projection matrix is applied to object coordinates
		// in the onDrawFrame() method
		Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}

	public static int loadShader(int type, String shaderCode) {
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		return shader;
	}
}