package com.conligo.news.feedzilla;

import java.util.List;

import com.conligo.news.model.ArticleBean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * {@link FeedzillaAPITest} class, TODO kesako?
 * 
 * @author Guillaume Royer
 *
 * @version 0.1 created on May 3, 2012
 */
public class FeedzillaAPITest extends TestCase {
	
	private FeedzillaAPI api;

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
	public FeedzillaAPITest(String testName) {
        super(testName);
	}

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(FeedzillaAPITest.class);
    }
    
    /**
     * Run the tests.
     */
    public void test() {
    	api = new FeedzillaAPI();
    	List<ArticleBean> list = api.getLatestArticles();
    	assertNotNull(list);
    	assertFalse(list.isEmpty());
    	System.out.println("results size: "+list.size());
    }
}
