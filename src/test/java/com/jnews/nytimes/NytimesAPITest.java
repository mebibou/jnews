package com.jnews.nytimes;

import java.util.List;

import org.junit.Test;

import com.jnews.BaseAPI.Method;
import com.jnews.model.ArticleBean;
import com.jnews.nytimes.NytimesAPI;

public class NytimesAPITest {
	
	private NytimesAPI api;

	public NytimesAPITest() {
    	api = new NytimesAPI();
	}
    
    @Test(expected = UnsupportedOperationException.class)
    public void test_unsupportedOperation() {
    	api.getLatestArticles(Method.BEAN);
    }
    
	@Test
    public void test_listArticleBean() {
    	List<ArticleBean> list = (List<ArticleBean>) api.searchArticles("Apple", null, Method.BEAN);
    	assert(list!=null);
    	assert(list.size()!=0);
    	System.out.println("results size: "+list.size());
    }
}
