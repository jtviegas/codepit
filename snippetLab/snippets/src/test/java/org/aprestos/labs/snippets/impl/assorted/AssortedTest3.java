package org.aprestos.labs.snippets.impl.assorted;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class AssortedTest3 {

	List<Integer> numbers = Arrays.asList(8,6);


	@Test
	public void test2() {

		List<Integer> o = Arrays.asList(7,9,2,3,4,5,6,78,90,12);

		o.subList(2,4);

	}


    @Test
    public void test() {

		List<Integer> numbers2 = Arrays.asList(7,9);

		Changer changer = new Changer();

        changer.changeNumbersCreatingAList(numbers);

        Assert.assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(8,6), numbers));


		changer.changeNumbers(numbers2);

		Assert.assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(8,10), numbers2));


		changer.changeNumbers(numbers);

		Assert.assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(9,7), numbers));


		int number = 5;
		changer.changeNumber(number);
		Assert.assertEquals(5, number);

    }


    class Changer {

    	void changeNumber(int number){
			number++;
		}

		void changeNumbers(List<Integer> numbers){
			for( int i=0; i<numbers.size(); i++ ){
				numbers.set(i, numbers.get(i)+1);
			}
		}

        void changeNumbersCreatingAList(List<Integer> numbers){
    	    numbers = new ArrayList<>(numbers);
            for( int i=0; i<numbers.size(); i++ ){
                numbers.set(i, numbers.get(i)+1);
            }
        }

    }

}
