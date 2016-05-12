package org.aprestos.labs.snippets.impl.web.auth.twitter.twitter4j;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterEngine {

	private static String oAuthConsumerKey = "0hH6ntFnAjA7nhpHBhIRA";
	private static String oAuthConsumerSecret = "K56dcSIOFoWrKEx14CvoCFrcXzzDHo4raA9BcOeVg";
	private static String oAuthAccessToken = "52205623-qF6Cr429crkMDpDyZCWj7bVWMIVutCAobOWyFOvIO";
	private static String oAuthAccessTokenSecret = "OaZJZUBiQYwzi6gxjAL2svlWRbW13IhcYDurN56Ho22Bo";
	
	private static TwitterEngine instance;
	
	private Twitter twitter;
	
	public static TwitterEngine getInstance(){
		if(null == instance)
			instance = new TwitterEngine();
		
		return instance;
	}
	
	
	private TwitterEngine() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(oAuthConsumerKey)
		  .setOAuthConsumerSecret(oAuthConsumerSecret)
		  .setOAuthAccessToken(oAuthAccessToken)
		  .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	public Twitter twitter() {
		return twitter;
	}


	@Override
	protected final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	

}
