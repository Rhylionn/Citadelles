package controleur;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Interaction {
	private static Scanner sc = new Scanner(System.in);

	public static int lireUnEntier() {
		int i = 0;
		boolean continu = true;
		do {
			try {
				i = sc.nextInt();
				continu = false;
			} catch (InputMismatchException e) {
				System.out.print("Veuillez rentrer un chiffre : ");
				sc.next(); // passe l'entier pour �viter de boucler
			}
		} while(continu);
		return i;
	}

	// renvoie un entier lu au clavier compris dans l'intervalle
	//     [borneMin, borneMax[
	public static int lireUnEntier(int borneMin, int borneMax) {
		int i = 0;
		boolean continu = true;
		
		do{
			try{
				i = sc.nextInt();
				if(i >= borneMin && i < borneMax)
					continu = false;

			} catch(InputMismatchException e){
				continu = true;
				System.out.print("Veuillez rentrer un chiffre compris entre: " + borneMin + " et " + borneMax);
				sc.next();
			}
		} while (continu);

		return i;
	}

	// lit les r�ponses "oui", "non", "o" ou "n" et renvoie un bool�en
	public static boolean lireOuiOuNon() {
		boolean retour = true;
		boolean continu = true;

		do{
			try{

				String input = sc.next().trim();

				if(input.equals("oui") || input.equals("o")){
					retour = true;
					continu = false;
				} else if(input.equals("non") || input.equals("n")){
					retour = false;
					continu = false;
				} else {
					System.out.print("Veuillez rentrer une réponse (oui, o, non, n): ");
					sc.next();
				}


			} catch(InputMismatchException e){
				System.out.print("Veuillez rentrer une réponse (oui, o, non, n): ");
				sc.next();
			}

		} while(continu);

		return retour;
	}

	// renvoie une cha�ne de caract�re lue au clavier:
	public static String lireUneChaine() {
		String retour = "";
		boolean continu = true;

		do{

			try{

				retour = sc.next();
				continu = false;
				
			} catch(InputMismatchException e){
				System.out.print("Veuillez rentrer une chaine de caracère: ");
				sc.next();
			}

		} while(continu);

		return retour;
	}

	public static int randInt(int a, int b){
		Random rand = new Random();
        return rand.ints(a, b).findFirst().getAsInt();
	}

	public static Boolean randBool(){
		Random rand = new Random();
		return rand.nextBoolean();
	}
	
}
