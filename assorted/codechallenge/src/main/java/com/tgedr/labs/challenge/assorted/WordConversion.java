package com.tgedr.labs.challenge.assorted;

import static java.lang.String.format;

public class WordConversion {
    
    // state this variables just for code readability purposes
    private static final int MIN = 0, MAX = 999999999, 
	    THIRD_DEGREE_FACTOR_MAX = 999;
    private static final int BASE = 10;
    private static final int FIRST_DEGREE_FACTOR = 1, SECOND_DEGREE_FACTOR = 2, THIRD_DEGREE_FACTOR = 3;
    
    
    /**
     * translate int number between 0 and 999999999
     * in British English words
     * 
     * @param n number to translate in British English words
     * @return number translation in British English words
     */
    public String int2String(int n){
	StringBuffer result = new StringBuffer();
	
	if(n < MIN || n > MAX)
	    throw new IllegalArgumentException(format("only allowed values between %d and %d", MIN, MAX));

	int factors = (int) ((Math.log10(n) < 0 ? 0 : Math.log10(n))) ;
	int maxFactorIndex = factors/THIRD_DEGREE_FACTOR;
	int factorIndex = maxFactorIndex;
	
	// we'll factorise the complete number in  third degree factors, i.e., thousands,
	// and process each one at the time, starting from the upper factor,
	// say 1 290 347, will see us process and translate the numbers 1, 290 and 347, 
	// each factor will then have its proper translation whether it is millions or thousands
	// so on '1' we'll know that 2nd degree factor 2 is 'million' so -> one million
	// on '290' we'll know that 1st degree factor is 'thousand' so -> one million two hundred and ninety thousand
	// and finally on '347' we'll know the 0th degree factor is a number - > one million two hundred and ninety thousand three hundred and forty seven
	while(factorIndex >= 0){
	    
	    int y = getFactorNumber(n, factorIndex);
	    
	    String factorNumberTranslation = factorNumberTranslation(y, (factorIndex == maxFactorIndex));
	    if(0 < factorNumberTranslation.length()){
		result.append(factorNumberTranslation);
		String factorTranslation = translateFactorDegree(factorIndex);
		    if(0 < factorTranslation.length()){
			result.append(factorTranslation);
		    }
	    }

	    factorIndex--;
	}

	return result.toString().trim();
    }
    
    
    /**
     * get the number from a third degree factor number using the factor index, 
     * say we have  1 235 320 as a third degree factor number,
     * the factor index 1 (zero based) will be 235
     * 
     * @param n the number
     * @param factor index
     * @return int number correspondent to the factor index in the whole number
     */
    private int getFactorNumber(int n, int decimalIndex){
	int result = 0;
	
	int lowerDegreeFactor = ( n / ( (int)Math.pow(BASE, (decimalIndex*THIRD_DEGREE_FACTOR))) ) * (int)Math.pow(BASE, (decimalIndex*THIRD_DEGREE_FACTOR));
	int upperDegreeFactor = ( n / ( (int)Math.pow(BASE, (decimalIndex + 1) * THIRD_DEGREE_FACTOR) ) ) * (int)Math.pow(BASE, (decimalIndex + 1 )*THIRD_DEGREE_FACTOR);
	
	result = (lowerDegreeFactor - upperDegreeFactor)/ (int)Math.pow(BASE, (decimalIndex*THIRD_DEGREE_FACTOR));
	
	return result;
    }
    
    /**
     * translate numbers between 0 and 999
     * @param n number between 0 and 999
     * @return String with the number translation
     */
    public String factorNumberTranslation(int n, boolean isHigherFactor){
	
	if(n < MIN || n > THIRD_DEGREE_FACTOR_MAX)
	    throw new IllegalArgumentException(format("@intraThousandFactorsTranslation: only allowed values between %d and %d", MIN, THIRD_DEGREE_FACTOR_MAX));
	
	StringBuffer result = new StringBuffer();
	
	int maxDecimalFactor = (int) ((Math.log10(n) < 0 ? 0 : Math.log10(n))) ;
	int decimalFactor = 0;
	while(decimalFactor <= maxDecimalFactor){
	    int decimalFactor2TheLeftNumber =  n / ((int) Math.pow(BASE, decimalFactor) );    
	    int decimalFactorNumber = decimalFactor2TheLeftNumber - ( (decimalFactor2TheLeftNumber / BASE) * BASE );
	    
	    if(FIRST_DEGREE_FACTOR == decimalFactor ){
		if(0 < decimalFactorNumber){ //if not numbers
		    if(2 > decimalFactorNumber){ //handle teens
			//get the former multiplier (number)
			    int f = (int) Math.pow(BASE, (decimalFactor + FIRST_DEGREE_FACTOR));
			    decimalFactorNumber = n % f ;
			    result = new StringBuffer(translateTeens(decimalFactorNumber));
			}
			else { // handle tens
			    result.insert(0, " ");
			    result.insert(0, translateTens(decimalFactorNumber));
			}
		}
		
		if(0 < result.length()){
		    if(maxDecimalFactor == SECOND_DEGREE_FACTOR || (!isHigherFactor))
			result.insert(0, " and ");
		}
		   
		    
	    }
	    else if( SECOND_DEGREE_FACTOR == decimalFactor ){
		    result.insert(0, " hundred");
		    result.insert(0, translateNumber(decimalFactorNumber));
	    }
	    else { // 0 degree factor = numbers
		
		if(0 == decimalFactorNumber){ //special case fro zero
		    //only translate zero if only factor number and only number in factor number
		    if( 0 == maxDecimalFactor && isHigherFactor )
			result.insert(0, translateNumber(decimalFactorNumber));
		}
		else {
		    result.insert(0, translateNumber(decimalFactorNumber));
		    // add 'and' if there is no other decimalFactorNumber in this whole factor and 
		    // if this is not the higher factor of the number ex: 1 001
		    if(0 == maxDecimalFactor && 0 < result.length() && (!isHigherFactor))
			    result.insert(0, " and ");
		}

	    }
	    decimalFactor++;
	}
	
	
	
	return result.toString().trim();
    }
    
    private String translateFactorDegree(int n){
	String result = null;
	switch (n) {
        	case 1:
        	    result = " thousand ";
        	    break;
        	case 2:
        	    result = " million ";
        	    break;
          	default:
        	    result = "";
        	    break;
        }
	return result;
    }
    
    private String translateNumber(int n){
	String result = null;
	switch (n) {
        	case 0:
        	    result = "zero";
        	    break;
        	case 1:
        	    result = "one";
        	    break;
        	case 2:
        	    result = "two";
        	    break;
        	case 3:
        	    result = "three";
        	    break;
        	case 4:
        	    result = "four";
        	    break;
        	case 5:
        	    result = "five";
        	    break;
        	case 6:
        	    result = "six";
        	    break;
        	case 7:
        	    result = "seven";
        	    break;
        	case 8:
        	    result = "eight";
        	    break;
        	case 9:
        	    result = "nine";
        	    break;
        }
	return result;
    }

    private String translateTeens(int n) {
	String result = null;
	switch (n) {
        	case 10:
        	    result = "ten";
        	    break;
        	case 11:
        	    result = "eleven";
        	    break;
        	case 12:
        	    result = "twelve";
        	    break;
        	case 13:
        	    result = "thirteen";
        	    break;
        	case 14:
        	    result = "fourteen";
        	    break;
        	case 15:
        	    result = "fifteen";
        	    break;
        	case 16:
        	    result = "sixteen";
        	    break;
        	case 17:
        	    result = "seventeen";
        	    break;
        	case 18:
        	    result = "eighteen";
        	    break;
        	case 19:
        	    result = "nineteen";
        	    break;
	}
	return result;
    }
    
    private String translateTens(int n) {
	String result = null;
	switch (n) {
		
        	case 2:
        	    result = "twenty";
        	    break;
        	case 3:
        	    result = "thirty";
        	    break;
        	case 4:
        	    result = "forty";
        	    break;
        	case 5:
        	    result = "fifty";
        	    break;
        	case 6:
        	    result = "sixty";
        	    break;
        	case 7:
        	    result = "seventy";
        	    break;
        	case 8:
        	    result = "eighty";
        	    break;
        	case 9:
        	    result = "ninety";
        	    break;
	}
	return result;
    }

}
