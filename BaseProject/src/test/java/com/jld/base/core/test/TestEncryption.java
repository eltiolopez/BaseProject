package com.jld.base.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jld.base.core.utils.AESencrypter;

public class TestEncryption {
	
	@Test
	public void checkAESEncryptAndDecrypt() {
		
		String RAW_PWD = "p@ssw0rd#1234";
		
		try {
			String enc = AESencrypter.encrypt(RAW_PWD);
			String dec = AESencrypter.decrypt(enc);
			
			assertEquals(RAW_PWD, dec);
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void checkBCryptEncryptAndDecrypt() {
		
		String RAW_PWD = "p@ssw0rd#1234";
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		try {
			String ENC_PWD = encoder.encode(RAW_PWD);
			
			assertEquals(true, encoder.matches(RAW_PWD, ENC_PWD));
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void checkDBPassword() {
		
		String RAW_PWD = "password";
		String ENC_PWD = "$2a$10$kDe7xj7L8TNtytPr5AU1k.mfpSQZl2S2iG/iuugmoCjAkYtdPCnha";
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		try {			
			assertEquals(true, encoder.matches(RAW_PWD, ENC_PWD));
		} catch(Exception e) {
			fail();
		}
	}

}
