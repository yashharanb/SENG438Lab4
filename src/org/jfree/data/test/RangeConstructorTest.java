package org.jfree.data.test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jfree.data.Range;
import org.junit.Test;

public class RangeConstructorTest {

	@Test
	public void testRegularConstruct() {
		Range r=new Range(1,3);
		assertEquals("invalid constructor upper",3, r.getUpperBound(),.000000001d);
	}
	@Test(expected = InvalidParameterException.class)
	public void testIllegalConstruct() {
		new Range(3,1);
	}
}
