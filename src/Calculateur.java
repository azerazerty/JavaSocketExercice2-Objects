

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Calculateur {

	public static void main(String[] args) {
		
		ServerSocket serveur;
		Socket socketServeur;
		DataInputStream entree;
		DataOutputStream sortie;	
		
		ObjectInputStream entreeObjet;
		
		try {
			
			System.out.println("je suis le serveur, je suis à l'écoute ... ");
			
			serveur = new ServerSocket(2000);
			try {
				socketServeur = serveur.accept();
				try {
					entree = new DataInputStream(socketServeur.getInputStream());
					sortie = new DataOutputStream(socketServeur.getOutputStream());
					
					entreeObjet = new ObjectInputStream(socketServeur.getInputStream());
					
					int operation = entree.readInt();
										
					while(operation != 0) {
						float a=0, b=0;
						if(operation != 5) {
							a = entree.readFloat();
							b = entree.readFloat();	
						}
						
						float resultat = 0;
						
						switch(operation) {
							case 1://somme
								resultat = a+b;								
								break;
							case 2://Soustraction
								resultat = a-b;
								break;								
							case 3://Multiplication
								resultat = a*b;
								break;								
							case 4://Division 
								resultat = a/b;
								break;	
							case 5:
								try {
									Point p1 = (Point)entreeObjet.readObject();
									Point p2 = (Point)entreeObjet.readObject();
								
									resultat = (float)Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2)+Math.pow(p1.getY()-p2.getY(), 2));								
								
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}  
								
								break;
						}
						
						sortie.writeFloat(resultat);
						
						operation = entree.readInt();						
					}					
					
				}finally {
					socketServeur.close();
				}
				
			}finally {
				serveur.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
 