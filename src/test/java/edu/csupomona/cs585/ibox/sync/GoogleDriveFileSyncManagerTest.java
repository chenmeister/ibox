package edu.csupomona.cs585.ibox.sync;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;

public class GoogleDriveFileSyncManagerTest {
	
	private static Drive mockDrive;
	private static GoogleDriveFileSyncManager fileSync;
	private File localFile;
	private Files getFiles;
	
	@Before
	public void setUp() throws Exception {
		mockDrive = Mockito.mock(Drive.class);
		fileSync = Mockito.mock(GoogleDriveFileSyncManager.class);
		localFile = Mockito.mock(File.class);
		getFiles = Mockito.mock(Files.class);
	}

	@After
	public void tearDown() throws Exception {
		mockDrive = null;
		fileSync = null;
		localFile = null;
		getFiles = null;
	}

	@Test
	public void testAddFile() throws IOException {
		getFiles = mockDrive.files();
		fileSync.addFile(localFile);
		//assertEquals(getFiles.get(localFile.getName()), localFile.getName());
	}

	@Test
	public void testUpdateFile() throws IOException {

		fileSync.updateFile(localFile);
		
	}

	@Test
	public void testDeleteFile() throws IOException {
		fileSync.deleteFile(localFile);
	}

}
