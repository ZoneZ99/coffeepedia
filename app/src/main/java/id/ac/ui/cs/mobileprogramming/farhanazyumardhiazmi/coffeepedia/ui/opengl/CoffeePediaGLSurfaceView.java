package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class CoffeePediaGLSurfaceView extends GLSurfaceView {

	private final GLRenderer mRenderer;

	private static final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private float previousX;
	private float previousY;

	public CoffeePediaGLSurfaceView(Context context) {
		super(context);
		setEGLContextClientVersion(2);
		mRenderer = new GLRenderer();
		setRenderer(mRenderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float dx = x - previousX;
			float dy = y - previousY;

			if (y > (float) getHeight() / 2) {
				dx = dx * -1;
			}

			if (x < (float) getWidth() / 2) {
				dy = dy * -1;
			}

			mRenderer.setAngle(
				mRenderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR)
			);

			requestRender();
		}

		previousX = x;
		previousY = y;
		return true;
	}
}
