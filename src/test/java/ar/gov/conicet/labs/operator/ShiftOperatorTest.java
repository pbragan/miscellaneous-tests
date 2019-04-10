package ar.gov.conicet.labs.operator;

import ar.gov.conicet.labs.miscellaneousTests.number.NumberUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ShiftOperatorTest extends TestCase{

	public ShiftOperatorTest(String name) {
		super(name);
	}
	
	public static Test suite() {
		return new TestSuite(ShiftOperatorTest.class);
	}

	public void sigendShiftRightOperatorTest() {
		int i = 60;
		assertEquals(30, i >> 1);
		assertEquals(15, i >> 2);
		assertEquals(7, i >> 3);
		assertEquals(3, i >> 4);
		assertEquals(1, i >> 5);
		assertEquals(0, i >> 6);
		assertEquals(0, i >> 7);
		i = -60;
		//se representa con two's complement. Quiere decir que el bit de la izquierda tiene que ser uno y
		//ademas se ponen todos los bits en negados de lo que seria el valor positivo y luego se le suma 1
		//en este caso, 60 = 0000 0000 0000 0000 0000 0000 0011 1100
		//por lo tanto -60 = 1111 1111 1111 1111 1111 1111 1100 0100
		//al ser signed mantiene el signo => 1111 1111 1111 1111 1111 1111 1110 0010
		//que es igual a 
		assertEquals(NumberUtil.fromStringBinary("11111111111111111111111111100010").intValue(), i >> 1);
	}
	
	public void unsigendShiftRightOperatorTest() {
		int i = 60;
		assertEquals(30, i >>> 1);
		assertEquals(15, i >>> 2);
		assertEquals(7, i >>> 3);
		assertEquals(3, i >>> 4);
		assertEquals(1, i >>> 5);
		assertEquals(0, i >>> 6);
		assertEquals(0, i >>> 7);
		i = -60;
		//se representa con two's complement. Quiere decir que el bit de la izquierda tiene que ser uno y
		//ademas se ponen todos los bits en negados de lo que seria el valor positivo y luego se le suma 1
		//en este caso, 60 = 0000 0000 0000 0000 0000 0000 0011 1100
		//por lo tanto -60 = 1111 1111 1111 1111 1111 1111 1100 0100
		//entonces el shift va a transformar a positivo ya que es unsigned => 0111 1111 1111 1111 1111 1111 1110 0010
		//que es igual a 
		assertEquals(Integer.parseInt("01111111111111111111111111100010", 2), i >>> 1);
	}
}
