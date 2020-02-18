package com.psi.wallet.branch.c;

import org.apache.commons.codec.binary.Base64;

public class Main {

	public static void main(String[] args) {
		 byte[] encodedToken = Base64.encodeBase64("1234567890".getBytes());
         String uPPasswordString = new String(encodedToken);
         System.out.print(uPPasswordString);
	}

}
