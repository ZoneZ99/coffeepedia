package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.opengl.CoffeePediaGLSurfaceView;

public class AnimationActivity extends AppCompatActivity {

	private GLSurfaceView mGLView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGLView = new CoffeePediaGLSurfaceView(this);
		setContentView(mGLView);
	}
}


