package ar.gov.conicet.labs.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CollectionTest extends TestCase {

	/**
	 * create el test case
	 * @param name nombre del test case
	 */
	public CollectionTest(String name) {
		super(name);
	}
	/**
	 * 
	 * @return the suited of the test being tested
	 */
	public static Test suite() {
		return new TestSuite(CollectionTest.class);
	}
	
	private void iniciarIntegerList(int maxElements, List<Integer> list) {
		for(int i = 0; i< maxElements;i++) {
			list.add(i);
		}
	}
	
	private <T> void doSomeGetsFromList(List<T> list) {
		int maxElements = list.size();
		list.get(maxElements-1);
		list.get(maxElements/2);
		list.get(maxElements/4);
		list.get(0);
	}
	/**
	 * Este test realiza una comparación entre LinkedList y ArrayList.<br>
	 * Da dos resultados importantes y representativos de la diferencia entre ambas implementaciones de List.<br>
	 * Es importante dos caracteristicas:
	 * 1- El rendimiento de LinkedList y ArrayList ante el agregado de elementos y su acceso.
	 * 2- El uso de recursos, en este caso memoria utilizada. (por ejemplo el caso del ArrayList con una capacidad inicial grande)
	 * <br>
	 * A su vez este tema invita al interesado a ver la implementaci&oacute;n real de cada caso y entender los resultados obtenidos.
	 */
	public void testAccessingElementsLinkedListVsArrayList() {
		List<Integer> linkedList = new LinkedList<>();
		List<Integer> arrayList = new ArrayList<>();
		int maxElements = Integer.MAX_VALUE/800;
		int capacity = maxElements+new Double(maxElements/4).intValue()+1;
		List<Integer> arrayList2 = new ArrayList<>(capacity);
		System.out.println("Testing se realiza con "+maxElements+" elementos enteros.");
		long start,end;
		start = System.currentTimeMillis();
		iniciarIntegerList(maxElements, linkedList);
		end = System.currentTimeMillis();
		System.out.println("LinkedList add "+maxElements+" en "+(end-start)+"ms");
		start = System.currentTimeMillis();
		iniciarIntegerList(maxElements, arrayList);
		end = System.currentTimeMillis();
		System.out.println("ArrayList add "+maxElements+" en "+(end-start)+"ms");
		start = System.currentTimeMillis();
		iniciarIntegerList(maxElements, arrayList2);
		end = System.currentTimeMillis();
		System.out.println("ArrayList con capacity de "+capacity+" add "+maxElements+" en "+(end-start)+"ms");
		start = System.currentTimeMillis();
		doSomeGetsFromList(linkedList);
		end = System.currentTimeMillis();
		System.out.println("LinkedList gests realizados en "+(end-start)+"ms");
		start = System.currentTimeMillis();
		doSomeGetsFromList(arrayList);
		end = System.currentTimeMillis();
		System.out.println("ArrayList gests realizados en "+(end-start)+"ms");
		start = System.currentTimeMillis();
		doSomeGetsFromList(arrayList2);
		end = System.currentTimeMillis();
		System.out.println("ArrayList con capacity de "+capacity+" gests realizados en "+(end-start)+"ms");
	}
	

}
