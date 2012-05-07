package com.conligo.news;

import java.io.IOException;
import java.util.Properties;

/**
 * {@link NewsProperties} class, access to the properties in the news.properties file.
 * 
 * @author Guillaume Royer
 *
 * @version 0.1 created on May 7, 2012
 */
public class NewsProperties {
	
	private static Properties props;
	
	private NewsProperties() {
		props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/news.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a hold of the instance to access properties.
	 * @return
	 */
	public static NewsProperties getInstance() {
		return Holder.instance;
	}
	private static class Holder {
		static NewsProperties instance = new NewsProperties();
	}

	public String getProperty(String key) {
		return props.getProperty(key);
	}
	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

}
