package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

	private final FloatBuffer vertexBuffer;
	private final ShortBuffer drawListBuffer;

	private static final String vertexShaderCode =
		// This matrix member variable provides a hook to manipulate
		// the coordinates of the objects that use this vertex shader
		"uniform mat4 uMVPMatrix;" +
			"attribute vec4 vPosition;" +
			"void main() {" +
			// the matrix must be included as a modifier of gl_Position
			// Note that the uMVPMatrix factor *must be first* in order
			// for the matrix multiplication product to be correct.
			"  gl_Position = uMVPMatrix * vPosition;" +
			"}";

	private static final String fragmentShaderCode =
		"precision mediump float;" +
			"uniform vec4 vColor;" +
			"void main() {" +
			"  gl_FragColor = vColor;" +
			"}";

	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 3;
	static float[] squareCoords = {
		-0.5f, 0.5f, 0.0f,   // top left
		-0.5f, -0.5f, 0.0f,   // bottom left
		0.5f, -0.5f, 0.0f,   // bottom right
		0.5f, 0.5f, 0.0f}; // top right

	private final short[] drawOrder = {0, 1, 2, 0, 2, 3}; // order to draw vertices

	// Set color with red, green, blue and alpha (opacity) values
	float[] color = {0.627f, 0.322f, 0.176f, 1.0f};

	private int mPositionHandle;
	private int mColorHandle;
	private int mMVPMatrixHandle;

	private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

	private final int mProgram;

	public Square() {
		// initialize vertex byte buffer for shape coordinates
		ByteBuffer bb = ByteBuffer.allocateDirect(
			// (# of coordinate values * 4 bytes per float)
			squareCoords.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(squareCoords);
		vertexBuffer.position(0);

		// initialize byte buffer for the draw list
		ByteBuffer dlb = ByteBuffer.allocateDirect(
			// (# of coordinate values * 2 bytes per short)
			drawOrder.length * 2);
		dlb.order(ByteOrder.nativeOrder());
		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);

		int vertexShader = GLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
			vertexShaderCode);
		int fragmentShader = GLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
			fragmentShaderCode);

		// create empty OpenGL ES Program
		mProgram = GLES20.glCreateProgram();

		// add the vertex shader to program
		GLES20.glAttachShader(mProgram, vertexShader);

		// add the fragment shader to program
		GLES20.glAttachShader(mProgram, fragmentShader);

		// creates OpenGL ES program executables
		GLES20.glLinkProgram(mProgram);
	}

	public void draw(float[] mvpMatrix) {
		// Add program to OpenGL ES environment
		GLES20.glUseProgram(mProgram);

		// get handle to vertex shader's vPosition member
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

		// Enable a handle to the square vertices
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		// Prepare the square coordinate data
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
			GLES20.GL_FLOAT, false,
			vertexStride, vertexBuffer);

		// get handle to fragment shader's vColor member
		mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

		// Set color for drawing the square
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);

		// get handle to shape's transformation matrix
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

		// Apply the projection and view transformation
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

		// Draw the square
		GLES20.glDrawElements(
			GLES20.GL_TRIANGLES, drawOrder.length,
			GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		// Disable vertex array
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}