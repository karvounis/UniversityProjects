import java.io.*;

import FormatIO.*;

public class Cyber{

	/**Our keyspace.16bit*/
	private static final int MAX16VALUE = 65536;
	
	private static void CipherTextOnlyAttack(){
		FileIn fin = null;
		FileWriter fout = null;
		StringBuilder str = null;
		try{
			fout = new FileWriter("secondy.txt");
			str = new StringBuilder("");
			for(int i = 0; i < MAX16VALUE; i++){
				str.append("");
				fin = new FileIn("CyberSecurity/secondCipher.txt");
				//System.out.println("" + i );
				for(;;){
					try {
						String block = fin.readWord();
						int	blockInt = Hex16.convert(block);
						int	decrypted = Coder.decrypt(i, blockInt);
						/*if(decrypted<31 || decrypted>127){
							fout.append(str.toString());
							str.setLength(0);
							fin.close();
							break;
						}*/
						int	c0 = decrypted / 256;
						int	c1 = decrypted % 256;
						if(((c0 >= 32 && c0 <= 126) || c0 == 255 || c0 == 10 ) && (((c1 >= 32 && c1 <= 126) || c1 == 255 || c1 == 10)) ){
							str.append((char) c0);
							str.append((char) c1);
						}else{
							str.setLength(0);
							//fout.append("\n");
							fin.close();
							break;
						}
						/*if(c0<32 || c0>126 || c1 <32 || c1 > 126){
							str.setLength(0);
							//fout.append("\n");
							fin.close();
							break;
						}
						System.out.println("At key: " + i +" found: " + (char) c0 + "" + (char) c1);*/
						//str.append((char) c0);
						
						/*fout.append((char) c0);
						if(c1 != 0){
							fout.append((char) c1);
						}*/

					} catch (EofX e) {
						str.append("\n");
						//System.err.println(str.toString());
						fout.append("Key number: " + i + " string: " + str.toString());
						str.setLength(0);
						//fout.append("\n");
						fin.close();
						break;
					}
				}
			}
			fout.close();
		}catch(IOException e){}
	}

	public static void main(String[] args){

		//.out.println(KnownPlaintextAttack());
		//decryptFirstCipher(KnownPlaintextAttack());
		//Key:20405
		//string: A lie told often enough becomes the truth.  Lenin
		//        µ3˜z‘3€|˜wÔ|’g‘}Ôvš|�tœ3–v—|™v‡3€{‘3€a�gœ=Ô3¸všzš
		CipherTextOnlyAttack();
		System.out.print("ok");

	}
}