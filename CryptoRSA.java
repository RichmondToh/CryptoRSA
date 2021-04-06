import java.math.BigInteger;




public class CryptoRSA {

	public static void main(String[] args) {
		BigInteger Contains0 =  new BigInteger("0"); //This is public Key
		// BOBs Stuff
		BigInteger p1 = new BigInteger("12217081"); //The First Prime Number
		BigInteger q1 = new BigInteger("6758331"); //The Second Prime Number
		BigInteger e1 =  new BigInteger("7"); //This is public Key
		BigInteger N1 = p1.multiply(q1);         //Other public Key
		BigInteger Phi1 = EulerPhi(p1,q1);
		BigInteger d1 = new BigInteger("1842874"); // This is PrivateKey
		// BOBs end

		// ALice Stuff
		BigInteger p2 = new BigInteger("0"); //The First Prime Number
		BigInteger q2 = new BigInteger("0"); //The Second Prime Number
		BigInteger e2 =  new BigInteger("0"); //This is public Key
		BigInteger N2 = p2.multiply(q2);         //Other public Key
		BigInteger Phi2 = EulerPhi(p2,q2);
		BigInteger d2 = new BigInteger("0"); // This is privateKey
		// ALice end

		//
		BigInteger PlainText = new BigInteger("4345666"); //Manually 
		BigInteger Ciphertext = new BigInteger("4255773"); 
		//

		System.out.println(decrypt(Ciphertext,q1,p1));
		//System.out.println(CalculatePrivateKey(p2,q2));
		
		//Alice use's Bob's parameters to encrypt
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//If Bob's Public Key Not given or Private Key Not given
		if(e1.equals(Contains0)){
			e1 = CalculatePublicKey(d1,Phi1);
		} else {
			d1 = CalculatePrivateKey(e1,Phi1);
		}
		

		System.out.println(CalculatePublicKey(d1,Phi1)+" This is Bob's Public Key");
		System.out.println(CalculatePrivateKey(e1,Phi1)+" This is Bob's Private key");
		System.out.println(PlainText+" This is Plaintxt Given");


		//Alice Encrypts using Bob's Public key (e1,N1)
		System.out.println(encrypt(PlainText,e1,N1)+" This is Encrypted Plaintxt using Bob's E1 and N1");
		Ciphertext = encrypt(PlainText,e1,N1);

		//Bob Decrypts using his Private key (Ciphertext.modPow(e1,N1)
		System.out.println(decrypt(Ciphertext,d1,N1)+" This is Decrypted Plaintxt using Bob's D1 and N1");
		////////////////////////////////////////////////////////////////////////////////////////////////////

		
		
		
		
		
		/*
	
		//Bob use's Alice's parameters to encrypt
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//If Alice's Public Key is Not given or Private Key is Not given
		if(e2.equals(Contains0)){
			e2 = CalculatePublicKey(d2,Phi2);
		} else {
			d2 = CalculatePrivateKey(e2,Phi2);
		}

		System.out.println(CalculatePublicKey(d2,Phi2)+" This is Alice's Public Key");
		System.out.println(CalculatePrivateKey(e2,Phi2)+" This is Alice's Private key");
		System.out.println(PlainText+" This is Plaintxt Given");


		//Bob Encrypts using Alice's Public key (e2,N2)
		System.out.println(encrypt(PlainText,e2,N2)+" This is Encrypted Plaintxt using Alice's E2 and N2");
		Ciphertext = encrypt(PlainText,e2,N2);

		//Alice Decrypts using her Private key (Ciphertext.modPow(e2,N2)
		System.out.println(decrypt(Ciphertext,d2,N2)+" This is Decrypted Plaintxt using Alice's D2 and N2");
		////////////////////////////////////////////////////////////////////////////////////////////////////
		 
		 */
	}

	public static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger N) {
		return message.modPow(e, N);
	}

	public static BigInteger decrypt(BigInteger encrypted, BigInteger d, BigInteger N) {
		return encrypted.modPow(d, N);
	}

	public static BigInteger EulerPhi(BigInteger p, BigInteger q) {
		BigInteger eulerFormula = new BigInteger("1"); //Value is static used to calculate eulerPhi
		BigInteger EulerPhi = null;
		EulerPhi=(p.subtract(eulerFormula)).multiply(q.subtract(eulerFormula)); // Phi = (p-1)(q-1)
		return EulerPhi;
	}

	public static BigInteger CalculatePublicKey(BigInteger PrivateKey, BigInteger Phi) {

		BigInteger PublicKey = null;
		PublicKey = (PrivateKey.modInverse(Phi));
		return PublicKey;
	}
	public static BigInteger CalculatePrivateKey(BigInteger PublicKey,BigInteger Phi) {
		BigIntEuclidean temp = new BigIntEuclidean();
		temp = gcdExt(PublicKey,Phi);
		return temp.x.mod(Phi);
	}

	public static BigIntEuclidean gcdExt(BigInteger PublicKey,BigInteger Phi) {
		BigInteger x = BigInteger.ZERO;
		BigInteger lastx = BigInteger.ONE;
		BigInteger y = BigInteger.ONE;
		BigInteger lasty = BigInteger.ZERO;
		while (!Phi.equals(BigInteger.ZERO))
		{
			BigInteger[] quotientAndRemainder = PublicKey.divideAndRemainder(Phi);
			BigInteger quotient = quotientAndRemainder[0];

			BigInteger temp = PublicKey;
			PublicKey = Phi;
			Phi = quotientAndRemainder[1];

			temp = x;
			x = lastx.subtract(quotient.multiply(x));
			lastx = temp;

			temp = y;
			y = lasty.subtract(quotient.multiply(y));
			lasty = temp;
		}

		BigIntEuclidean result = new BigIntEuclidean();
		result.x = lastx;
		result.y = lasty;
		result.gcd = PublicKey;
		return result;
	}

}
