package org.jfree.data.test;

import static org.junit.Assert.*;

import java.util.List;
import java.security.InvalidParameterException;
import java.util.Arrays;

import static org.jfree.data.DataUtilities.getCumulativePercentages;
import org.jfree.data.KeyedValues;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

public class DataUtilitiesGetCumulativePercentagesTest {

	
	
	/**
	 * Creates a mock object simulating a KeyedValues object
	 * @param keys the keys that each value would be mapped to
	 * @param outputs the value that each key is mapped to
	 * @return a KeyedValues mockery which has each key mapped to each output
	 */
	public KeyedValues createMockery(List<String> keys, List<Double> outputs) {
		Mockery mockingContext = new Mockery();
		KeyedValues values = mockingContext.mock(KeyedValues.class);
		mockingContext.checking(new Expectations() {
		{
			//set the getIndex for each key to be its index
			for(int i=0;i<keys.size();i++) {
				allowing(values).getIndex(keys.get(i));
				will(returnValue(i));
			}
			
			//set the getKey for each index to be the respective key
			for(int i=0;i<keys.size();i++) {
				allowing(values).getKey(i);
				will(returnValue(keys.get(i)));
			}
			
			//set the getKeys to return the set of keys it has
			allowing(values).getKeys();
			will(returnValue(keys));
			
			//set the getValue for each key to return the respective value
			for(int i=0;i<outputs.size();i++) {
				allowing(values).getValue(keys.get(i));
				will(returnValue(outputs.get(i)));
			}
			
			//set the getValue for each index to return the respective value
			for(int i=0;i<outputs.size();i++) {
				allowing(values).getValue(i);
				will(returnValue(outputs.get(i)));
			}
			
			//set getItemCount to return the number of items
			allowing(values).getItemCount();
				will(returnValue(outputs.size()));
		}
		});
		return values;
	}
	/**
	 * tests if you input null as the parameter, it throws an InvalidParameterException
	 */
	@Test(expected = InvalidParameterException.class)
	public void nullValues() {
		try {
		getCumulativePercentages(null);
		} catch(InvalidParameterException ex) {
			throw ex;
		} catch(Exception ex) {
		}
	}
	
	/**
	 * tests if it handles an empty KeyedValues object
	 */
	@Test
	public void emptyValues() {
		List<String> keys= Arrays.asList();
		List<Double> doubleValues=Arrays.asList();
		KeyedValues values=createMockery(keys, doubleValues);

		
		
		KeyedValues output= getCumulativePercentages(values);
		int expected=0;
		int actual=output.getItemCount();
		
		assertEquals("entering an emtpy KeyedValues should give back an empty keyedValues", expected,actual);
	}
	/**
	 * tests if it throws an InvalidParameterException when you input an KeyedValues object that has
	 * 0 as the sum of its valeus
	 */
	@Test(expected = InvalidParameterException.class)
public void zeroValues() {	
	List<String> keys= Arrays.asList("key0");
	List<Double> doubleValues=Arrays.asList(0.0);
	KeyedValues mock=createMockery(keys, doubleValues);
 	
	
	KeyedValues output= getCumulativePercentages(mock);
}
	/**
	 * Tests that when a normal set of values is inputed, that it can calculate the percent of each correctly.
	 * It does this by testing each value in a loop.
	 *  
	 * @return a series of tests that each calculate if 1 value is inputed correctly. 
	 */
@Test
public void fullValues() {	
	
	List<String> fullKeyedKeys= Arrays.asList("key1","key2","key3","key4","key5");
	List<Double> fullKeyedDoubleValues=Arrays.asList(1.0,2.0,3.0,4.0,5.0);
	List<Double> fullKeyedExpectedValues=Arrays.asList(.066666666667,.2,.4,.6666666666,1.0);

	
		KeyedValues values=createMockery(fullKeyedKeys, fullKeyedDoubleValues);
		KeyedValues output= getCumulativePercentages(values);

		
		for(int i=0;i<fullKeyedKeys.size();i++) {
			String keyTested=fullKeyedKeys.get(i);
			double expected=fullKeyedExpectedValues.get(i);
			double actual=output.getValue(keyTested).doubleValue();
			assertEquals("Outputed percentages are inaccurate", expected,actual,.000000001d);
			System.out.println(actual);
		}
	}


/**
 * tests if an exception is thrown when you have a null as one of the values*/
@Test(expected = InvalidParameterException.class)
public void nullValueWithinInputedKeyedValues() {	
	List<String> keys= Arrays.asList("key0","key1");
	List<Double> doubleValues=Arrays.asList(null,1.0);
	KeyedValues mock=createMockery(keys, doubleValues);
	KeyedValues output= getCumulativePercentages(mock);
}

@Test
public void negativeValue() {	
	List<String> keys= Arrays.asList("key0");
	List<Double> doubleValues=Arrays.asList(-1.0);

	
	KeyedValues mock=createMockery(keys, doubleValues);
	KeyedValues output= getCumulativePercentages(mock);
	double expected=1.0;
	double actual=output.getValue("key0").doubleValue();
	assertEquals("list with negative value should still calculate as portion",expected, actual,.000000001d);
}
/**tests if an InvalidParameterException is thrown when a cumulative value >1 is forced by 
 * having a negative value  in the KeyedValues*/
@Test(expected = InvalidParameterException.class)
public void outOfCumulativeRange() {	
	List<String> keys= Arrays.asList("key0","key1");
	List<Double> doubleValues=Arrays.asList(2.0,-1.0);
	
	KeyedValues mock=createMockery(keys, doubleValues);
	KeyedValues output= getCumulativePercentages(mock);
}

@Test(expected = InvalidParameterException.class)
public void nullNumber() {
	List<String> keys= Arrays.asList("key0","key1");
	List<Double> doubleValues=Arrays.asList(2.0,null);
	
	KeyedValues mock=createMockery(keys, doubleValues);
	KeyedValues output= getCumulativePercentages(mock);
}

}