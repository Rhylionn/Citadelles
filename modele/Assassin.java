package modele;

import controleur.Interaction;

public class Assassin extends Personnage {
    
    public Assassin(){
        super("Assassin", 1, Caracteristiques.ASSASSIN);
    }

    public void utiliserPouvoir(){

            System.out.println("Quel personnage voulez vous assasiner ? ");

            // Affichage de tout les personnages.
            for(int i = 0; i < this.getPlateau().getNombreJoueurs(); i++)
                System.out.println("\t" + i + " " + this.getPlateau().getJoueur(i).getPersonnage().getNom());

            int choix;

            do{
                System.out.print("Votre choix > ");
                choix = Interaction.lireUnEntier(0, this.getPlateau().getNombreJoueurs()+1);
                
                if(this.getPlateau().getJoueur(choix).equals(this.getJoueur()))
                    System.out.println("/!\\ Vous ne pouvez pas vous assassiner.");
                else
                    this.getPlateau().getJoueur(choix).getPersonnage().setAssassine();

            } while (!this.getPlateau().getJoueur(choix).getPersonnage().getAssassine());

    }

    public void utiliserPouvoirAvatar(){
        int choix;

        do{
            choix = Interaction.randInt(0, this.getPlateau().getNombreJoueurs());
            
            if(!this.getPlateau().getJoueur(choix).equals(this.getJoueur()))
                this.getPlateau().getPersonnage(choix).setAssassine();

        } while (!this.getPlateau().getJoueur(choix).getPersonnage().getAssassine());
    }

}
