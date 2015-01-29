package edu.csupomona.cs585.ibox.sync;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

public class IntegrationTest {

	Drive googleDriveClient;
	GoogleDriveFileSyncManager fileSync;
	
	@Before
	public void setUp() throws Exception {
		initGoogleDriveServices();
		fileSync = new GoogleDriveFileSyncManager(googleDriveClient);
	}

	@After
	public void tearDown() throws Exception {
		fileSync = null;
	}

	@Test
	public void initGoogleDriveServices() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        try{
            GoogleCredential credential = new  GoogleCredential.Builder()
              .setTransport(httpTransport)
              .setJsonFactory(jsonFactory)
              .setServiceAccountId("936890365418-r1gns1if70m5fc44i4a6e3oumi8p5vp8@developer.gserviceaccount.com")
              .setServiceAccountScopes(Collections.singleton(DriveScopes.DRIVE))
              .setServiceAccountPrivateKeyFromP12File(new java.io.File("My Project-8e678d0d2682.p12"))
              .build();

            googleDriveClient = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName("ibox").build();
        }catch(GeneralSecurityException e){
            e.printStackTrace();
        }

    }
	
	@Test
	public void testAddFile() throws IOException {
		java.io.File localFile = new java.io.File("test.txt");
		localFile.createNewFile();
		fileSync.addFile(localFile);
		assertNotNull(fileSync.getFileId(localFile.getName()));
	}
	
	@Test
	public void testUpdateFile() throws IOException {
		java.io.File localFile = new java.io.File("test.txt");
		fileSync.updateFile(localFile);
		assertNotNull(fileSync.getFileId(localFile.getName()));
	}
	
	@Test
	public void testDeleteFile() throws IOException {
		java.io.File localFile = new java.io.File("test.txt");
		fileSync.deleteFile(localFile);
		assertNotNull(fileSync.getFileId(localFile.getName()));
	}

}
