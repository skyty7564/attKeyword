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

}
