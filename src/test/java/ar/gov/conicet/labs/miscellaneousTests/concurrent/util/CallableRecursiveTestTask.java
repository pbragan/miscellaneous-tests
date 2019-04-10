package ar.gov.conicet.labs.miscellaneousTests.concurrent.util;

import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;

public class CallableRecursiveTestTask<T> extends RecursiveTask<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5209038131258641824L;
	
	private Callable<T> callable;
	
	public CallableRecursiveTestTask(Callable<T> callable) {
		this.callable = callable;
	}

	@Override
	protected T compute() {
		try {
			return callable.call();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
