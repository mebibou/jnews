package com.conligo.news.nytimes;

import java.util.List;

import com.conligo.news.model.ArticleBean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * {@link NytimesAPITest} class, TODO kesako?
 * 
 * @author Guillaume Royer
 *
 * @version 0.1 created on May 3, 2012
 */
public class NytimesAPITest extends TestCase {
	
	private NytimesAPI api;

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
	public NytimesAPITest(String testName) {
        super(testName);
	}

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(NytimesAPITest.class);
    }
    
    /**
     * Run the tests.
     */
    public void test() {
    	api = new NytimesAPI();
    	List<ArticleBean> list = api.searchArticles("Apple", null);
    	assertNotNull(list);
    	assertFalse(list.isEmpty());
    	System.out.println("results size: "+list.size());
    }
}
