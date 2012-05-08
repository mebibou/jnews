package com.jnews.feedzilla;

import java.util.List;

import org.junit.Test;

import com.jnews.BaseAPI.Method;
import com.jnews.feedzilla.FeedzillaAPI;
import com.jnews.model.ArticleBean;

public class FeedzillaAPITest {
	
	private FeedzillaAPI api;

	public FeedzillaAPITest() {
    	api = new FeedzillaAPI();
	}
    
    @Test(expected = UnsupportedOperationException.class)
    public void test_unsupportedOperation() {
    	api.getLatestArticles(Method.XML);
    }
    
    @Test
    public void test_listArticleBean() {
    	List<ArticleBean> list = (List<ArticleBean>) api.getLatestArticles(Method.BEAN);
    	assert(list!=null);
    	assert(list.size()!=0);
    	System.out.println("results size: "+list.size());
    }
}
