package com.att.test;

import org.testng.annotations.Test;

import com.att.keyword.engine.KeyWordEngine;

public class SecureTest {
	
	public KeyWordEngine keyWordEngine;
	@Test
	public void SecureTestT()
	{
		keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution("test1");
	}
	@Test
	public void SecureTestT2()
	{
		keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution("test2");
	}
	@Test
	public void SecureTestT3()
	{
		keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution("test3");
	}


}
