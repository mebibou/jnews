package com.jnews.feedzilla;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jnews.BaseAPI;
import com.jnews.model.ArticleBean;

/**
 * {@link FeedzillaAPI} class, access to the Feedzilla API.<br>
 * For details and examples: http://code.google.com/p/feedzilla-api/wiki/RestApi
 * 
 * @author Guillaume Royer
 *
 * @version 0.1 created on May 3, 2012
 */
public class FeedzillaAPI extends BaseAPI {
	
	private final String baseUrl = "http://api.feedzilla.com/v1/";
	private final String articleUrl = "articles";
	private final String searchUrl = articleUrl+"/search";
	
	private final String jsonFormat = ".json";
	private final String xmlFormat = ".xml";

	/**
	 * Default constructor.
	 */
	public FeedzillaAPI() {
		super();
	}
	
	@Override
	public Object getLatestArticles(Method method) {
		try {
			if(method.equals(Method.BEAN)) {
				JSONObject object = this.getForJSON(articleUrl+jsonFormat);
				return this.parseJSONResults(object);
			}
			else if(method.equals(Method.JSON)) {
				return this.getForJSON(articleUrl+jsonFormat);
			}
			else {
				throw new UnsupportedOperationException("The selected method is not available.");
			}
		}
		catch(UnsupportedEncodingException e) {}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object searchArticles(String query, Map<Enum, String> params, Method method) {
		try {
			//add query to the params
			params = this.addKeyValueToParams(PrivateKeys.QUERY, query, params);
			
			if(method.equals(Method.BEAN)) {
				JSONObject object = this.getForJSON(searchUrl+jsonFormat, params);
				return this.parseJSONResults(object);
			}
			else if(method.equals(Method.JSON)) {
				return this.getForJSON(searchUrl+jsonFormat, params);
			}
			else {
				throw new UnsupportedOperationException("The selected method is not available.");
			}
		}
		catch(UnsupportedEncodingException e) {}
		return null;
	}

	/**
	 * Do not use this function here, there is no required parameters.
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	@Override
	protected Map<Enum, String> addRequiredKeyValueToParams(Map<Enum, String> params) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected List<ArticleBean> parseJSONResults(JSONObject results) {
		List<ArticleBean> list = null;
		JSONArray articles = results.getJSONArray("articles");
		if(articles!=null && articles.size()>0) {
			list = new ArrayList<ArticleBean>();
			Iterator<JSONObject> iterator = articles.iterator();
			while(iterator.hasNext()) {
				JSONObject article = iterator.next();
				String source = article.getString("source");
				String sourceUrl = article.getString("source_url");
				String summary = article.getString("summary");
				String title = article.getString("title");
				String url = article.getString("url");
				String publish_date = article.getString("publish_date");
				list.add(new ArticleBean(url, title, summary, null, source, sourceUrl, this.stringToDate(publish_date, "EEE, d MMM yyyy HH:mm:ss z")));
			}
		}
		return list;
	}

	@Override
	protected String getBaseUrl() {
		return this.baseUrl;
	}
	
	public static enum PrivateKeys {
		QUERY("q");

	    private String name;

	    private PrivateKeys(String name) {
	        this.name = name;
	    }

	    @Override
	    public String toString() {
	        return name;
	    }
	}
	
	public enum Keys {
		CULTURE_CODE("culture_code"),
		COUNT("count"),
		SINCE("since"),
		ORDER("order"),
		CLIENT_SOURCE("client_source"),
		TITLE_ONLY("title_only");

	    private String name;

	    private Keys(String name) {
	        this.name = name;
	    }

	    @Override
	    public String toString() {
	        return name;
	    }
	}

}
