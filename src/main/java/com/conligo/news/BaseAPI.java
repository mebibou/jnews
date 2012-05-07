package com.conligo.news;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * {@link BaseAPI} class, base class containing default functions to help other API classes.
 * 
 * @author Guillaume Royer
 *
 * @version 0.1 created on May 3, 2012
 */
public abstract class BaseAPI {
	private static final Logger logger = Logger.getLogger(BaseAPI.class);
	
	protected final String baseApiKey = ".apiKey";
	protected final String baseApiSecret = ".apiSecret";
	
	private Client client;
	
	public BaseAPI() {
		this.client = Client.create();
	}
	
	/**
	 * Get a list of the latest articles.
	 * @return {List<?>} a list of elements
	 */
	public abstract List<?> getLatestArticles();
	
	/**
	 * Search articles by keywords.
	 * @param query {String} the query
	 * @param params {Map<Enum, String>} a map of key/values. The key must be coming from an Enum, the value a string.<br>
	 * Example: if requesting articles from the NytimesAPI, please use the NytimesAPI.Keys enum, etc.
	 * @return {List<?>} a list of elements
	 */
	@SuppressWarnings("rawtypes")
	public abstract List<?> searchArticles(String query, Map<Enum, String> params);
	
	/**
	 * Get request with a list of parameters.
	 * @param requestUrl {String} the request url before the parameters
	 * @param params {Map<String, Object>} set of key/value parameters
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected JSONObject get(String requestUrl, Map<Enum, String> params) throws UnsupportedEncodingException {
		String url = "";
		//add all parameters
		Iterator<Entry<Enum, String>> iterator = params.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Enum, String> entry = iterator.next();
			
			if(!url.isEmpty()) url += "&";
			
			url += this.encodeURL(entry.getKey().toString())+"="+this.encodeURL(entry.getValue());
		}
		//now sends request
		return this.get(requestUrl+url);
	}
	
	/**
	 * Get request to the provided url.
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected JSONObject get(String url) throws UnsupportedEncodingException {
		logger.debug("GET: "+this.getBaseUrl()+url);
		WebResource resource = client.resource(this.getBaseUrl()+url);
		long start = System.currentTimeMillis();
		ClientResponse clientResponse =  resource.accept("application/json").get(ClientResponse.class);
		String response = clientResponse.getEntity(String.class);
		logger.debug("TIME USED: "+(System.currentTimeMillis()-start)+" ms, RESPONSE: "+response);
		return JSONObject.fromObject(response);
	}
	
	/**
	 * URL encoding of a value
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	protected String encodeURL(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "UTF-8");
	}
	
	/**
	 * Parse a string to a date.
	 * @param dateString
	 * @param format
	 * @return
	 */
	protected Date stringToDate(String dateString, String format) {
		if(dateString==null) return null;
		DateFormat formater = new SimpleDateFormat(format);
		try {
			return formater.parse(dateString);
		} catch(ParseException e){
			//no need to log this exception, it is probably because the format is wrong.
		}
		return null;
	}
	
	/**
	 * Add a pair of key/value to a map of key/values.
	 * @param key {Enum} key as an enum
	 * @param value
	 * @param params {Map<Enum, String>}
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map<Enum, String> addKeyValueToParams(Enum key, String value, Map<Enum, String> params) {
		//first, make sure the params are not null
		if(params==null) params = new HashMap<Enum, String>();
		params.put(key, value);
		return params;
	}
	
	/**
	 * Add the required key/values pairs to a map of key/values.<br>
	 * This should be called before doing any query to the APIs.
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected abstract Map<Enum, String> addRequiredKeyValueToParams(Map<Enum, String> params);
	
	/**
	 * Parse JSONObject to a list of elements.
	 * @param results
	 * @return
	 */
	protected abstract List<?> parseJSONResults(JSONObject results);
	
	/**
	 * Get the base url to construct the calls.
	 * @return
	 */
	protected abstract String getBaseUrl();

}
