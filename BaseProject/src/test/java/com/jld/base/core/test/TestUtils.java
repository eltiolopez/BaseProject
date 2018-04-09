package com.jld.base.core.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestUtils {

	@Test
	public void removeNotExistingDirectory() {
		
		String FAKE_PATH = "C:\\FakeDirectory";
		
		File dir = new File(FAKE_PATH);
		try {
			FileUtils.deleteDirectory(dir);
			assertTrue(true);
		} catch(IOException ioe) {
			System.out.println("IOException: " + ioe);
			fail();
		} catch(Exception e) {
			System.out.println("Other excepion: " + e);
			fail();
		}
	}
}
