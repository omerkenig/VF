package vfro.framework;

import java.util.Base64;
import java.util.Scanner;

public class EncryDecry 
{

	//----------------------------------------------------------------------------------------
	// This method is used for encrypted the data
	//----------------------------------------------------------------------------------------

	public static String Encryption(String password)
	{
		byte[] encry = password.getBytes();
		String encrypted =Base64.getEncoder().encodeToString(encry);
		//System.out.println("Encrypted string is "+encrypted);
		return encrypted;
	}

	//----------------------------------------------------------------------------------------
	// This method is used for decrypted the data
	//----------------------------------------------------------------------------------------

	public static String Decrypted(String password)
	{
		byte [] decry = Base64.getDecoder().decode(password);
		String decrypted = new String(decry); 
		//System.out.println("Decrypted string is :"+decrypted);
		return decrypted;
	}

	public static void main(String[] args) 
	{

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your password for Encryption : ");
		String password = sc.next();
		String encryptedpassword = EncryDecry.Encryption(password);
		System.out.println("Encrypted password"+" "+encryptedpassword);
		//String decrypted = EncryDecry.Decrypted(encryptedpassword);
		//System.out.println("decrypted password"+"   "+decrypted);
	}
}
