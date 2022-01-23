package modele;

import controleur.Interaction;

public class Voleur extends Personnage {
    
    public Voleur(){
        super("Voleur", 2, Caracteristiques.VOLEUR);
    }

    public void utiliserPouvoir(){

            System.out.println("Quel personnage voulez vous voler ? > ");

            // Affichage de tout les personnages.

            for(int i = 0; i < this.getPlateau().getNombreJoueurs(); i++)
                System.out.println("\t" + i + " - " + this.getPlateau().getJoueur(i).getPersonnage().getNom());

            int choix;
            Boolean continuer = true;

            do{
                System.out.print("Votre choix > ");
                choix = Interaction.lireUnEntier(0, this.getPlateau().getNombreJoueurs()+1);
                
                if(this.getPlateau().getJoueur(choix).equals(this.getJoueur())){
                    System.out.println("/!\\ Vous ne pouvez pas vous voler.");
                } else if(this.getPlateau().getJoueur(choix).getPersonnage().getRang() == 1){
                    System.out.println("/!\\ Vous ne pouvez pas choisir un personnage de rang 1.");
                } else if(this.getPlateau().getJoueur(choix).getPersonnage().getAssassine()){
                    System.out.println("/!\\ Vous ne pouvez pas voler un joueur assassiné.");
                } else {
                    this.getPlateau().getJoueur(choix).getPersonnage().setVole();
                    continuer = false;
                }

            } while (continuer);

    }

    public void utiliserPouvoirAvatar(){

        int choix;

        do{
            choix = Interaction.randInt(0, this.getPlateau().getNombreJoueurs());
            Personnage target = this.getPlateau().getJoueur(choix).getPersonnage();

            if(!target.getNom().equals(this.getNom()) && target.getRang() != 1 && !target.getAssassine())
                target.setVole();

        } while (!this.getPlateau().getJoueur(choix).getPersonnage().getVole());
    }

}
