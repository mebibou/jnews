package com.conligo.news.usatoday;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.conligo.news.BaseAPI;
import com.conligo.news.NewsProperties;
import com.conligo.news.model.ArticleBean;
import com.conligo.news.nytimes.NytimesAPI.PrivateKeys;

/**
 * {@link USATodayAPI} class, access to the New York Times API.<br>
 * For details and examples: http://developer.nytimes.com/docs/article_search_api
 * 
 * @author Guillaume Royer
 *
 * @version 0.1 created on May 4, 2012
 */
public class USATodayAPI extends BaseAPI {
	
	private final String baseResource = "usatoday.";
	
	private final String baseUrl = "http://api.usatoday.com/open/";
	private final String articleUrl = "articles";
	
	private final String topnewsUrl = articleUrl + "/topnews?";
	private final String searchUrl = articleUrl + "?";

	/**
	 * Default constructor.
	 */
	public USATodayAPI() {
		super();
	}

	@Override
	public List<ArticleBean> getLatestArticles() {
		try {
			//add required fields
			Map<Enum, String> params = this.addRequiredKeyValueToParams(null);
			
			JSONObject object = this.get(topnewsUrl, params);
			return this.parseJSONResults(object);
		}
		catch(UnsupportedEncodingException e) {}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArticleBean> searchArticles(String query, Map<Enum, String> params) {
		try {
			//add query to the params
			params = this.addKeyValueToParams(PrivateKeys.QUERY, query, params);
			//add required fields
			params = this.addRequiredKeyValueToParams(params);
			
			JSONObject object = this.get(articleUrl, params);
			return this.parseJSONResults(object);
		}
		catch(UnsupportedEncodingException e) {}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Map<Enum, String> addRequiredKeyValueToParams(Map<Enum, String> params) {
		//add apiKey to params
		params = this.addKeyValueToParams(PrivateKeys.API_KEY, this.getArticleAPIKey(), params);
		//add encoding of JSON to params
		params = this.addKeyValueToParams(PrivateKeys.ENCODING, "json", params);
		return params;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected List<ArticleBean> parseJSONResults(JSONObject results) {
		List<ArticleBean> list = null;
		JSONArray articles = results.getJSONArray("stories");
		if(articles!=null && articles.size()>0) {
			list = new ArrayList<ArticleBean>();
			Iterator<JSONObject> iterator = articles.iterator();
			while(iterator.hasNext()) {
				JSONObject article = iterator.next();
				String source = "USA Today";
				String sourceUrl = "http://www.usatoday.com/";
				String summary = article.getString("description");
				String title = article.getString("title");
				String url = article.getString("link");
				String publish_date = article.getString("pubDate");
				list.add(new ArticleBean(url, title, summary, null, source, sourceUrl, this.stringToDate(publish_date, "EEE, d MMM yyyy HH:mm:ss z")));
			}
		}
		return list;
	}

	@Override
	protected String getBaseUrl() {
		return this.baseUrl;
	}
	
	private String getArticleAPIKey() {
		return NewsProperties.getInstance().getProperty(baseResource+"articles"+baseApiKey);
	}
	private String getMostPopularAPIKey() {
		return NewsProperties.getInstance().getProperty(baseResource+"most_popular"+baseApiKey);
	}
	private String getSemanticAPIKey() {
		return NewsProperties.getInstance().getProperty(baseResource+"semantic"+baseApiKey);
	}
	
	public static enum PrivateKeys {
		API_KEY("api_key"),
		QUERY("search"),
		TAG("tag"),
		ENCODING("encoding");

	    private String name;

	    private PrivateKeys(String name) {
	        this.name = name;
	    }

	    @Override
	    public String toString() {
	        return name;
	    }
	}
	
	public static enum Keys {
		COUNT("count"),
		DAYS("days"),
		PAGE("page"),
		REPORTER("reporter"),
		TICKERS_ONLY("tickersonly"),
		FROM_DATE("fromdate"),
		TO_DATE("todate"),
		MOST("most");

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
