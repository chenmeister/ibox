package edu.csupomona.cs585.ibox.sync;

import static org.junit.Assert.*;

//import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.Delete;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.Drive.Files.Update;
import com.google.api.client.http.AbstractInputStreamContent;

public class GoogleDriveFileSyncManagerTest {
	
	//declare google drive objects
	Drive mockDrive;
	GoogleDriveFileSyncManager fileSync;
	
	//declare file objects
	java.io.File localFile;
	File body;
	Files getFiles;
	List getList;
	FileList listOfFiles;
	java.util.List<File> filesList;
	
	//declare google drive function objects
	Insert addInstance;
	Update updateInstance;
	Delete deleteInstance;
	
	@Before
	public void setUp() throws Exception {
		//initialize google drive objects
		mockDrive = Mockito.mock(Drive.class);
		fileSync = new GoogleDriveFileSyncManager(mockDrive);
		
		//initialize file objects
		localFile = Mockito.mock(java.io.File.class);
		body = new File();
		listOfFiles = new FileList();
		getFiles = Mockito.mock(Files.class);
		getList = Mockito.mock(List.class);
		filesList = new ArrayList<File>();
		
		//initialize google drive function objects
		addInstance = Mockito.mock(Insert.class);
		updateInstance = Mockito.mock(Update.class);
		deleteInstance = Mockito.mock(Delete.class);
	}

	@After
	public void tearDown() throws Exception {
		//set all objects to null after completion
		mockDrive = null;
		fileSync = null;
		localFile = null;
		body = null;
		listOfFiles = null;
		getFiles = null;
		getList = null;
		filesList = null;
		addInstance = null;
		updateInstance = null;
		deleteInstance = null;
	}
	
	public void initalizeFiles(){
		File testFile = new File();
		testFile.setId("TestFile");
		testFile.setTitle("test.txt");
		
		filesList.add(testFile);
		listOfFiles.setItems(filesList);
	}

	@Test
	public void testAddFile() throws IOException {
		//initalize all the variables
		Mockito.when(mockDrive.files()).thenReturn(getFiles);
		Mockito.when(getFiles.insert(Mockito.isA(File.class), 
				Mockito.isA(AbstractInputStreamContent.class))).thenReturn(addInstance);
		Mockito.when(addInstance.execute()).thenReturn(body);

		fileSync.addFile(localFile);
		
		//verify the variables
		Mockito.verify(mockDrive, Mockito.atLeastOnce()).files();
	    Mockito.verify(getFiles, Mockito.atLeast(0)).insert(Mockito.isA(File.class));
	    Mockito.verify(addInstance, Mockito.atLeastOnce()).execute();
	}

	@Test
	public void testUpdateFile() throws IOException {
		initalizeFiles();
		
		Mockito.when(localFile.getName()).thenReturn("test.txt");
		Mockito.when(mockDrive.files()).thenReturn(getFiles);
		Mockito.when(getFiles.list()).thenReturn(getList);
		Mockito.when(getList.execute()).thenReturn(listOfFiles);
		
		String fileId = fileSync.getFileId("test.txt");
		
		Mockito.when(getFiles.update(Mockito.eq(fileId),
				Mockito.isA(File.class), Mockito.isA(AbstractInputStreamContent.class))).thenReturn(updateInstance);

		Mockito.when(updateInstance.execute()).thenReturn(body);
		
		fileSync.updateFile(localFile);
	
		Mockito.verify(mockDrive, Mockito.atLeastOnce()).files();
	    Mockito.verify(getFiles, Mockito.atLeast(0)).update(Mockito.eq(fileId), Mockito.isA(File.class));
	    Mockito.verify(updateInstance, Mockito.atLeastOnce()).execute();
	}
	
	@Test(expected = Exception.class)
	public void testUpdateFileExecption() throws IOException {
		Mockito.when(localFile.getName()).thenReturn(null);
		fileSync.updateFile(localFile);
		assertNull(localFile);
	}
	
	
	@Test
	public void testDeleteFile() throws IOException {
		
		initalizeFiles();
		
		Mockito.when(localFile.getName()).thenReturn("test.txt");
		Mockito.when(mockDrive.files()).thenReturn(getFiles);
		Mockito.when(getFiles.list()).thenReturn(getList);
		Mockito.when(getList.execute()).thenReturn(listOfFiles);
		
		String fileId = fileSync.getFileId("test.txt");
		
		Mockito.when(getFiles.delete(Mockito.eq(fileId))).thenReturn(deleteInstance);
		Mockito.when(deleteInstance.execute()).thenReturn(null);
		
		fileSync.deleteFile(localFile);
		
		Mockito.verify(mockDrive, Mockito.atLeastOnce()).files();
	    Mockito.verify(getFiles, Mockito.atLeast(0)).delete(Mockito.eq(fileId));
	    Mockito.verify(deleteInstance, Mockito.atLeastOnce()).execute();		
	}
}
