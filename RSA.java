import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class RSA {

	private BigInteger P, Q, N, PHI, E, D;
	private int bitLength = 1024;
	private Random r;

	// generating public key E and private key D and the modulus N
	public RSA() {
		// seed to generate random primes
		r = new Random();

		// generating random primes P and Q of length bitLength
		P = BigInteger.probablePrime(bitLength, r);
		Q = BigInteger.probablePrime(bitLength, r);

		// calculating N = P * Q
		N = P.multiply(Q);

		// calculating totient of N, equal to (P-1) * (Q-1)
		PHI = P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE));

		// generating E such that 1 < E < PHI
		E = BigInteger.probablePrime(bitLength / 2, r);

		// while PHI and E have common factors and E is less than PHI
		while (PHI.gcd(E).compareTo(BigInteger.ONE) > 0 && E.compareTo(PHI) < 0) {
			E.add(BigInteger.ONE);
		}

		// find D such that DE congruent to 1 (mod PHI)
		D = E.modInverse(PHI);
	}

	// if E, D, and N have already been set
	public RSA(BigInteger e, BigInteger d, BigInteger n) {
		E = e;
		D = d;
		N = n;
	}

	// turns Byte array into a String of the Byte values
	public static String bytesToString(byte[] encrypted) {
		String str = "";
		for (byte b : encrypted)
			str += Byte.toString(b);
		return str;
	}

	// encrypt message
	public byte[] encrypt(byte[] message) {
		return (new BigInteger(message)).modPow(E, N).toByteArray();
	}

	// decrypt message
	public byte[] decrypt(byte[] message) {
		return (new BigInteger(message)).modPow(D, N).toByteArray();
	}
	
	// functions to get public key information
	public BigInteger getPublicKey(){
		return E;
	}
	public BigInteger getModulus(){
		return N;
	}

	//demo purposes
//	@SuppressWarnings("deprecation") 
//	public static void main(String[] args) throws IOException{ 
//		//create new instance of encryption/decryption object
//		RSA rsa = new RSA();
//		
//		//get in a message 
//		DataInputStream in = new DataInputStream(System.in);
//		System.out.println("Enter your message"); 
//		String input = in.readLine();
//		
//		//display the "before" 
//		System.out.println("Original message: " + input);
//		System.out.println("Message as bytes: " +
//		bytesToString(input.getBytes()));
//		
//		//encrypt and display 
//		byte[] encrypted = rsa.encrypt(input.getBytes());
//		System.out.println("Encrypted bytes: " + bytesToString(encrypted));
//		
//		//decrypt and display 
//		byte[] decrypted = rsa.decrypt(encrypted);
//		System.out.println("Decrypted bytes: " + bytesToString(decrypted));
//		
//		System.out.println("Decrypted message: " + new String(decrypted));
//	}
}
