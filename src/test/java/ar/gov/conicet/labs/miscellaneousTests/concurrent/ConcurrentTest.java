package ar.gov.conicet.labs.miscellaneousTests.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import ar.gov.conicet.labs.miscellaneousTests.concurrent.util.CallableRecursiveTestTask;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ConcurrentTest extends TestCase {

	/**
	 * Create the test case
	 * @param name name of the test case
	 */
	public ConcurrentTest(String name) {
		super(name);
	}

	/**
	 * 
	 * @return the suited of the test being tested
	 */
	public static Test suite() {
		return new TestSuite(ConcurrentTest.class);
	}
	
	private void setTasksToConcurrentThreadPoolTest(List<Callable<Integer>> tasks,List<ForkJoinTask<Integer>> fjTasks,int maxTask) {
		for(int i = 0; i< maxTask;i++) {
			final Integer in = Integer.valueOf(i);
			final long l = Double.valueOf(Math.random()).longValue();
			Callable<Integer> callable = () ->  {
				return callMethod(in, l);
			};
			tasks.add(callable);
			fjTasks.add(new CallableRecursiveTestTask<>(callable));
		}
	}
	
	private Integer callMethod(final Integer in, final long l) {
		if(l % 3 == 0) {
			try {
				//System.out.println("Una que duerme...");
				TimeUnit.MILLISECONDS.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return in;
	}

	/**
	 * Se hace un test de performance por cada ThreadPool executor bajo ciertas condiciones.
	 */
	public void testThreadPoolExecutorTest() {
		int maxTask=100;
		List<Callable<Integer>> tasks = new LinkedList<>();
		List<ForkJoinTask<Integer>> fjTasks = new LinkedList<>();
		this.setTasksToConcurrentThreadPoolTest(tasks, fjTasks,maxTask);
		System.out.println("Son "+tasks.size()+" tareas por ejecutar");
		long start = System.currentTimeMillis();
		//newWorkStealingPool en realidad esta haciendo uso de ForkJoinPool que luego se hace un test. Es un
		//metodo factory que instancia ForkJoinPool con ciertas condiciones muy parecidas a las que iniciamos en el test posterior...
		ExecutorService executorService = Executors.newWorkStealingPool();
		try {
			executorService.invokeAll(tasks);
			System.out.println("Invokeall invocado!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
		long i = 0;
		int filtroMensaje = 50000;
		while(!executorService.isTerminated()) {
			i++;
			if(i%filtroMensaje == 0) {
				System.out.println("Pool Size: "+((ForkJoinPool)executorService).getPoolSize());
				System.out.println("Paralelismo: "+((ForkJoinPool)executorService).getPoolSize());
				System.out.println("Active ThreadCount: "+((ForkJoinPool)executorService).getActiveThreadCount());
			}
		}
		System.out.println(i+" iteraciones hasta que ExecutorService terminates");
		long end = System.currentTimeMillis();
		System.out.println("Termina ExecutorService.newWorkStealingPool.invokeAll en (ms): "+(end-start));
		
		
		start = System.currentTimeMillis();
		executorService = Executors.newWorkStealingPool();
		for (Callable<Integer> t : tasks) {
			executorService.submit(t);
		}
		executorService.shutdown();
		i = 0;
		while(!executorService.isTerminated()) {
			i++;
			if(i%filtroMensaje == 0) {
				System.out.println("Pool Size: "+((ForkJoinPool)executorService).getPoolSize());
				System.out.println("Paralelismo: "+((ForkJoinPool)executorService).getPoolSize());
				System.out.println("Active ThreadCount: "+((ForkJoinPool)executorService).getActiveThreadCount());
			}
		}
		System.out.println(i+" iteraciones hasta que ExecutorService terminates");
		end = System.currentTimeMillis();
		System.out.println("Termina ExecutorService.newWorkStealingPool.execute (una por una) en (ms): "+(end-start));
		//SIN STEALING ALGORITH
		start = System.currentTimeMillis();
		executorService = Executors.newCachedThreadPool();
		for (Callable<Integer> t : tasks) {
			executorService.submit(t);
		}
		executorService.shutdown();
		i = 0;
		while(!executorService.isTerminated()) {
			i++;
			if(i%filtroMensaje == 0) {
				System.out.println("Pool Size: "+((ThreadPoolExecutor)executorService).getPoolSize());
				System.out.println("Paralelismo: "+((ThreadPoolExecutor)executorService).getPoolSize());
				System.out.println("Active Count: "+((ThreadPoolExecutor)executorService).getActiveCount());
			}
		}
		System.out.println(i+" iteraciones hasta que ExecutorService terminates");
		end = System.currentTimeMillis();
		System.out.println("Termina ExecutorService.newCachedThreadPool.execute (una por una) en (ms): "+(end-start));
		
		start = System.currentTimeMillis();
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for (Callable<Integer> t : tasks) {
			executorService.submit(t);
		}
		executorService.shutdown();
		i = 0;
		while(!executorService.isTerminated()) {
			i++;
		}
		System.out.println(i+" iteraciones hasta que ExecutorService terminates");
		end = System.currentTimeMillis();
		System.out.println("Termina ExecutorService.newFixedThreadPool(availableProcessors).execute (una por una) en (ms): "+(end-start));
		
		start = System.currentTimeMillis();
		executorService = Executors.newSingleThreadExecutor();
		for (Callable<Integer> t : tasks) {
			executorService.submit(t);
		}
		executorService.shutdown();
		i = 0;
		while(!executorService.isTerminated()) {
			i++;
		}
		System.out.println(i+" iteraciones hasta que ExecutorService terminates");
		end = System.currentTimeMillis();
		System.out.println("Termina ExecutorService.newSingleThreadExecutor().execute (una por una) en (ms): "+(end-start));
		
		// FORKJOIN!!!
		start = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		IntStream.range(0, maxTask).parallel().forEach(j -> {
			forkJoinPool.execute(fjTasks.get(j));
		});
		System.out.println("Execute todos de forkJoinPool");
		forkJoinPool.shutdown();
		i = 0;
		while(!forkJoinPool.isTerminated()) {
			i++;
			if(i%filtroMensaje == 0) {
				System.out.println("Pool Size: "+forkJoinPool.getPoolSize());
				System.out.println("Paralelismo: "+forkJoinPool.getPoolSize());
				System.out.println("Active ThreadCount: "+forkJoinPool.getActiveThreadCount());
			}
		}
		System.out.println(i+" iteraciones hasta que ForkJoinPool terminates");
		end = System.currentTimeMillis();
		System.out.println("Termina ForkJoin en (ms): "+(end-start));
	}
	
}
