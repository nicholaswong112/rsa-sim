import java.math.BigInteger;

public class Simulation {
	
	public static void main(String[] args){
		Server myServer = new Server();
		Client someClient = new Client();
		
		System.out.println("//////////SSL Handshake Simulation//////////");
		
		someClient.connect(myServer);
		myServer.sendInfo();
		someClient.validate(myServer.VALID_CERTIFICATE);
		byte[] encryptedKey = someClient.generateEncrypt(myServer.getRSA().getPublicKey(), 
				myServer.getRSA().getModulus());
		myServer.receiveKey(encryptedKey);
		System.out.println("Now that both client and server have"
				+ " symmetric keys, they can freely communicate with it.\nEND PROGRAM");
		
	}
	
	static class Server {
		private String ip;
		private RSA rsa;
		private String VALID_CERTIFICATE = "VALID";
		
		public void sendInfo(){
			System.out.println("Sending public key and certificate:" 
					+ VALID_CERTIFICATE);
		}
		
		public void receiveKey(byte[] encryptedKey){
			byte[] key = rsa.decrypt(encryptedKey);
			System.out.println("Server decrypts to receive key: " + new String(key));
		}
		
		public RSA getRSA(){
			return rsa;
		}
		
		public Server(){
			ip = randomTriple() + "." 
					+ randomTriple() + "." 
					+ randomTriple() + "." 
					+ randomTriple();
			rsa = new RSA();
		}
		
		private String randomTriple(){
			return "" + (int)(Math.random() * 256);
		}
		
		@Override
		public String toString(){
			return ip;
		}
	}
	
	static class Client {
		public void connect(Server s){
			System.out.println("Trying to connect to " + s);
		}
		
		public void validate(String certificate){
			System.out.println("SSL Certificate is " + certificate);
		}
		
		public byte[] generateEncrypt(BigInteger E, BigInteger N){
			byte[] message = {'S','Y','M','M','E','T','R','I','C',' ','K','E','Y'};
			System.out.println("Client generates a key: 'SYMMETRIC KEY' and encrypts with the public key");
			return (new BigInteger(message)).modPow(E, N).toByteArray();
		}
	}
}
