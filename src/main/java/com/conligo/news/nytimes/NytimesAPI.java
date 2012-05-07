package com.conligo.news.nytimes;

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

/**
 * {@link NytimesAPI} class, access to the New York Times API.<br>
 * For details and examples: http://developer.nytimes.com/docs/article_search_api
 * 
 * @author Guillaume Royer
 *
 * @version 0.1 created on May 4, 2012
 */
public class NytimesAPI extends BaseAPI {
	
	private final String baseResource = "nytimes.";
	
	private final String baseUrl = "http://api.nytimes.com/svc/";
	private final String articleUrl = "search/v1/article";
	
	private final String searchUrl = articleUrl + "?";
	private final String mostpopularUrl = "mostpopular/v2/mostviewed/all-sections/7.json";
	private final String semanticUrl = "semantic/v2/concept/";

	/**
	 * Default constructor.
	 */
	public NytimesAPI() {
		super();
	}

	@Deprecated
	@Override
	public List<ArticleBean> getLatestArticles() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArticleBean> searchArticles(String query, Map<Enum, String> params) {
		try {
			//add query to the params
			params = this.addKeyValueToParams(PrivateKeys.QUERY, query, params);
			//add required fields
			params = this.addRequiredKeyValueToParams(params);
			
			JSONObject object = this.get(searchUrl, params);
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
		return params;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected List<ArticleBean> parseJSONResults(JSONObject results) {
		List<ArticleBean> list = null;
		JSONArray articles = results.getJSONArray("results");
		if(articles!=null && articles.size()>0) {
			list = new ArrayList<ArticleBean>();
			Iterator<JSONObject> iterator = articles.iterator();
			while(iterator.hasNext()) {
				JSONObject article = iterator.next();
				String source = "The New York Times";
				String sourceUrl = "http://www.nytimes.com/";
				String summary = article.getString("body");
				String title = article.getString("title");
				String url = article.getString("url");
				String publish_date = article.getString("date");
				list.add(new ArticleBean(url, title, summary, null, source, sourceUrl, this.stringToDate(publish_date, "yyyyMMdd")));
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
	private String getBreakingNewsAPIKey() {
		return NewsProperties.getInstance().getProperty(baseResource+"breaking_news"+baseApiKey);
	}
	private String getSemanticAPIKey() {
		return NewsProperties.getInstance().getProperty(baseResource+"snapshots"+baseApiKey);
	}
	
	public static enum PrivateKeys {
		API_KEY("api-key"),
		QUERY("query");

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
		BEGIN_DATE("begin_date"),
		END_DATE("end_date"),
		FACETS("facets"),
		FIELDS("fields"),
		FORMAT("format"),
		OFFSET("offset"),
		RANK("rank");

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
