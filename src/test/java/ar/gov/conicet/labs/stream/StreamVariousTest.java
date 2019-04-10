package ar.gov.conicet.labs.stream;

import java.util.stream.IntStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StreamVariousTest extends TestCase {
	/**
	 * Create the test case
	 * @param name name of the test case
	 */
	public StreamVariousTest(String name) {
		super(name);
	}

	/**
	 * 
	 * @return the suited of the test being tested
	 */
	public static Test suite() {
		return new TestSuite(StreamVariousTest.class);
	}
	private static final String MENSAJE_DE_ITERACION = "indice %d de iteracion...";
	private static final int ITERACION_FILTER = Integer.MAX_VALUE/4;
	
	public void testIteracionesPrimitivasVsStreamRange() {
		long start = System.currentTimeMillis();
		for(int i = Integer.MIN_VALUE+1;i<Integer.MAX_VALUE;i++) {
			if(i % ITERACION_FILTER == 0) System.out.println(String.format(MENSAJE_DE_ITERACION, i));
		}
		long end = System.currentTimeMillis();
		System.out.println("For primitivo (ms): "+(end-start));
		start = System.currentTimeMillis();
		IntStream.range(Integer.MIN_VALUE+1, Integer.MAX_VALUE-1).forEach(
				j -> { if(j % ITERACION_FILTER == 0) System.out.println(String.format(MENSAJE_DE_ITERACION, j));}
		);
		end = System.currentTimeMillis();
		System.out.println("For IntStream.range (ms): "+(end-start));
		start = System.currentTimeMillis();
		IntStream.range(Integer.MIN_VALUE+1, Integer.MAX_VALUE-1).parallel().forEach(
				j -> { if(j % ITERACION_FILTER == 0) System.out.println(String.format(MENSAJE_DE_ITERACION, j));}
		);
		end = System.currentTimeMillis();
		System.out.println("For IntStream.range.parallel (ms): "+(end-start));
	}
	
}
