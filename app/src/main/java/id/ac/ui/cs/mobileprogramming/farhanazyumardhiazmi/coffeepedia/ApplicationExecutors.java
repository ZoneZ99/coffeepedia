package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApplicationExecutors {

	private final Executor mDiskIO;

	private final Executor mNetworkIO;

	private final Executor mMainThread;

	private ApplicationExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
		mDiskIO = diskIO;
		mNetworkIO = networkIO;
		mMainThread = mainThread;
	}

	public ApplicationExecutors() {
		this(
			Executors.newSingleThreadExecutor(),
			Executors.newFixedThreadPool(4),
			new MainThreadExecutor()
		);
	}

	public Executor getDiskIO() {
		return mDiskIO;
	}

	public Executor getNetworkIO() {
		return mNetworkIO;
	}

	public Executor getMainThread() {
		return mMainThread;
	}

	private static class MainThreadExecutor implements Executor {

		private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

		@Override
		public void execute(Runnable command) {
			mainThreadHandler.post(command);
		}
	}
}
