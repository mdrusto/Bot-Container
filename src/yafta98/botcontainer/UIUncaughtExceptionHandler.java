package yafta98.botcontainer;

import java.lang.Thread.UncaughtExceptionHandler;

public class UIUncaughtExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		//TODO: Set bot status to error
	}

}
