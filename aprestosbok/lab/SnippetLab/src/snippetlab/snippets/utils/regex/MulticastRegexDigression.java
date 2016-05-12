/*
 * MulticastRegexDigression.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.utils.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import snippetlab.snippets.AbstractSnippet;

/**
 * 
 */
public class MulticastRegexDigression extends AbstractSnippet
{
//	snippetlab.snippets.utils.regex.MulticastRegexDigression
	
	private String[] ips= new String[]{"223.255.255.255",
	                                   "224.0.0.0",
	                                   "224.1.0.0",
	                                   "224.0.0.2",
	                                   "dummy",
	                                   "224.0.0.0",
	                                   "224.0.0.255",
	                                   "239.255.255.255",
	                                   "252.12.12.12"};
	
	private List<String> octets= new ArrayList<String>();
	
	private Pattern pattern_ip;
	private Pattern pattern_fcn;
	private Pattern pattern_allowed_mc;
	private Pattern pattern_restricted_mc;
	private Pattern pattern_oc;

	
	public MulticastRegexDigression()
	{
		String octet = null;
		
		for(int i=0 ; i < 2345 ; i++)
		{
			octet = Integer.toString(i);
			
			octets.add(new String(octet));
			
			if(octet.length() == 1)
			{
				octet = "0" + octet;
				octets.add(new String(octet));
			}
			
			if(octet.length() == 2)
			{
				octet = "0" + octet;
				octets.add(new String(octet));
			}
		}

		String octetPattern = "(" +
			"0{0,2}[0-9]" +	//0-9 | 00-09 | 000-009
		"|" +
			"0?([1-9][0-9])" +  //  10-99 | 010-099 
		"|" +
			"1[0-9][0-9]" +		//100-199
		"|" +			
			"2[0-4][0-9]" +		//200-249
		"|" +
			"25[0-5]"  +		//250-255
		")" 
		;
		String fcnPattern = "^([a-zA-Z](([\\w]*)|([\\.][\\w])|([-][\\w]))*)$";

		String ipPattern = "^(" +
								"(" + octetPattern +
								"\\." +
								"){3}" + octetPattern + 
							")$";
		
		String multicastFirstOctectPattern = "(" +
			"2" +			//2**
			"(" +
				"[2][4-9]" +		//24 -> 29
				"|" +
				"[3][0-9]" +		//30 -> 39
			")" +
			")";
		
		
		String multicastAllowedPattern = "^(" +
			multicastFirstOctectPattern + 
				"(" +
					"\\." +
					octetPattern +
				"){3}" +
				")$" ;

		String multicastExcludedPattern = "^(" + 
			multicastFirstOctectPattern +
			"\\.[0]\\.[0]\\." + octetPattern + ")$";

		
		pattern_ip = Pattern.compile(ipPattern);
		pattern_fcn = Pattern.compile(fcnPattern);
		
		pattern_allowed_mc = Pattern.compile(multicastAllowedPattern);
		pattern_restricted_mc = Pattern.compile(multicastExcludedPattern);
		
		pattern_oc = Pattern.compile("^(" + octetPattern + ")$");

	}

	@Override
	public void method()
	{
		checkOctectPattern();
		doMatch();
	}

	private void checkOctectPattern()
	{
		for(String oc:octets)
		{
			
			Matcher ocmatcher = pattern_oc.matcher(oc);
			boolean ocmatchFound = ocmatcher.matches();
			if(ocmatchFound)
				System.out.println("does octect pattern matches " + oc + " ? " + ocmatchFound);

		}
			
	}
	
	
	private void doMatch()
	{
		
		for(String s : ips)
		{
			Matcher ipmatcher = pattern_ip.matcher(s);
			boolean ipmatchFound = ipmatcher.matches();
			System.out.println("\ndoes ip pattern matches " + s + " ? " + ipmatchFound);
			
			Matcher fcnmatcher = pattern_fcn.matcher(s);
			boolean fcnmatchFound = fcnmatcher.matches();
			System.out.println("\ndoes fcn pattern matches " + s + " ? " + fcnmatchFound);
			
			Matcher mc_allowed_matcher = pattern_allowed_mc.matcher(s);
			boolean mc_allowed_matchFound = mc_allowed_matcher.matches();
			Matcher mc_restricted_matcher = pattern_restricted_mc.matcher(s);
			boolean mc_restricted_matchFound = mc_restricted_matcher.matches();
					
			boolean	mcmatchFound = mc_allowed_matchFound && (!mc_restricted_matchFound);
					
			System.out.println("\ndoes multicast pattern matches " + s + " ? " + mcmatchFound);
		}
	}
	
}
