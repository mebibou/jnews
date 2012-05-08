package com.jnews.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link ArticleBean} class, bean that defines the fields of an Article.
 * 
 * @author Guillaume Royer
 *
 * @version 0.1 created on May 3, 2012
 */
public class ArticleBean {
	private String url;
	private String title;
	private String summary;
	private String content;
	private String source;
	private String sourceUrl;
	private Date datePublished;
	private String datePublishedString;

	public ArticleBean(String url, String title, String summary, String content, String source, String sourceUrl, Date datePublished) {
		super();
		this.url = url;
		this.title = title;
		this.summary = summary;
		this.content = content;
		this.source = source;
		this.sourceUrl = sourceUrl;
		this.datePublished = datePublished;
		if(datePublished!=null) {
			SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.datePublishedString = datetime.format(datePublished);
		}
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public Date getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}
	public String getDatePublishedString() {
		return datePublishedString;
	}
	public void setDatePublishedString(String datePublishedString) {
		this.datePublishedString = datePublishedString;
	}
}
