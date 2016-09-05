package com.zac.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeforeAfterTest {
	
	private static final Logger logger = LoggerFactory.getLogger(BeforeAfterTest.class);
//    private  Logger logger = LoggerFactory.getLogger(getClass());

	@BeforeClass
	public static void execBeforeClass() {
    	logger.info(""+ logger.getName());
		System.out.println("*** BeforeClass.");
	}
	@Before
	public void execBefore() {
    	logger.debug(""+ logger.getName());
		System.out.println("*.Before.");
	}

	@Test
	public void hogeHogeTest() {
    	logger.error(""+ logger.getName());
		System.out.println("  hogehoge");
	}
	@Test
	public void fugaFugaTest() {
		System.out.println("  fugafuga");
	}

	@Test
	public void piyoPiyoTest() {
		System.out.println("  piyopiyo");
	}

	@After
	public void execAfter() {
		System.out.println("@.After.");
	}

	@AfterClass
	public static void execAfterClass() {
    	logger.debug(""+ logger.getName());
		System.out.println("*** AfterClass.");
	}
}
