package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApplicationExecutors {

	private final Executor mDiskIO;

	private ApplicationExecutors(Executor diskIO) {
		mDiskIO = diskIO;
	}

	public ApplicationExecutors() {
		this(Executors.newSingleThreadExecutor());
	}

	public Executor getDiskIO() {
		return mDiskIO;
	}

}
