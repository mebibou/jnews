package com.jnews.usatoday;

import java.util.List;

import org.junit.Test;

import com.jnews.BaseAPI.Method;
import com.jnews.model.ArticleBean;
import com.jnews.usatoday.USATodayAPI;

public class USATodayAPITest {
	
	private USATodayAPI api;

	public USATodayAPITest() {
    	api = new USATodayAPI();
	}
    
	@Test
    public void test_listArticleBean() {
    	List<ArticleBean> list = (List<ArticleBean>) api.getLatestArticles(Method.BEAN);
    	assert(list!=null);
    	assert(list.size()!=0);
    	System.out.println("results size: "+list.size());
    }
}
