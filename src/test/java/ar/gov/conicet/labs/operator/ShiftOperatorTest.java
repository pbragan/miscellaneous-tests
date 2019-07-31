package ar.gov.conicet.labs.operator;

import ar.gov.conicet.labs.miscellaneousTests.number.NumberUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Es importante saber que todas las operaciones de shift son transformadas a 32 bits.
 * @author pbragan@conicet.gov.ar
 *
 */
public class ShiftOperatorTest extends TestCase{

	public ShiftOperatorTest(String name) {
		super(name);
	}
	
	public static Test suite() {
		return new TestSuite(ShiftOperatorTest.class);
	}

	/**
	 * Uno de los operadores poco conocidos en Java el shift. Parece un operador de bajo nivel
	 * pero es totalmente utilizable. <br>
	 * El shiftright lo que hace es mover cada bit para la derecha dejando en cero el bit primero que se mueve.
	 * En el caso de >> es un signed shift right ya que no mueve el primer bit que identifica el signo.
	 * <br>
	 * Es interesante ver que en el shift right 1 se obtiene siempre la mitad de un entero como entero truncado sin decimales.
	 * En este caso la mitad de 60 = 30, de 30 es 15, de 15 es 7, de 7 es 3, de 3 es 1 y luego de 1 ya es cero.
	 * <br>
	 * El signed shift right se utiliza en la implementacion de ArrayList para hacer crecer el array primitivo que se utiliza como estructura de datos interna. 
	 */
	public void sigendShiftRightOperatorTest() {
		int i = 60;
		//60 = 0000 0000 0000 0000 0000 0000 0011 1100 >> 1 => 0000 0000 0000 0000 0000 0001 1110 = 2pow(4)+2pow(3)+2pow(2)+2pow(1)=30
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
	/**
	 * En este caso se puede ver como el unsigned shift altera el signo del elemento
	 */
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
