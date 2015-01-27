package edu.csupomona.cs585.ibox;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class WatchDirTest {
	
	private static WatchDir wDir;
	
	@Before
	public void setUp() throws Exception {
		wDir = Mockito.mock(WatchDir.class);
	}

	@After
	public void tearDown() throws Exception {
		wDir = null;
	}

	@Test
	public void testWatchDir() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessEvents() {
		fail("Not yet implemented");
	}

}
