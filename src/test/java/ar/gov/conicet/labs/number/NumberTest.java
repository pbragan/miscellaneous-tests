package ar.gov.conicet.labs.number;



import org.junit.Assert;
import org.junit.Test;

import ar.gov.conicet.labs.miscellaneousTests.number.NumberUtil;


public class NumberTest {

	/**
	 * Se realizo un NumberUtil para operar, entre otras cosas, con binarios. El motivo del test es ver
	 * que se haya realizado correctamente uno de los metodos.
	 */
	@Test
	public void testSubstractBitSigned() {
		Assert.assertEquals("011011",NumberUtil.substractOneToBinarySigned("011100"));
		
	}
}
