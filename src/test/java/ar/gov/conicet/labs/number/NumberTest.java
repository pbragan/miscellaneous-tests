package ar.gov.conicet.labs.number;



import org.junit.Assert;
import org.junit.Test;

import ar.gov.conicet.labs.miscellaneousTests.number.NumberUtil;


public class NumberTest {

	@Test
	public void testSubstractBitSigned() {
		Assert.assertEquals("011011",NumberUtil.substractOneToBinarySigned("011100"));
		
	}
}
