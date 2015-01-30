package edu.csupomona.cs585.ibox;

import static org.junit.Assert.*;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.api.services.drive.Drive;

import edu.csupomona.cs585.ibox.sync.FileSyncManager;
import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;

public class WatchDirTest {

	WatchDir wd;
	Path set;
	FileSyncManager fsm;
	Drive mockDrive;
	Toolkit toolkit;
	Timer timer;
	
	@Before
	public void setUp() throws Exception {
		mockDrive = Mockito.mock(Drive.class);
		fsm = new GoogleDriveFileSyncManager(mockDrive);
		set = Mockito.mock(Path.class);
		wd = new WatchDir(set, fsm);
		
		toolkit = Toolkit.getDefaultToolkit();
	    timer = new Timer();

	}

	@After
	public void tearDown() throws Exception {
		mockDrive = null;
		fsm = null;
		set = null;
		wd = null;
	}

	@Test
	public void testProcessEvents() {
		timer.schedule(new RunProcessEvents(), 100000);
	}
	
	class RunProcessEvents extends TimerTask {
		public void run() {
			wd.processEvents();
			System.exit(0);
		}	
	}

}
