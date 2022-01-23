package modele;

import controleur.Interaction;

public class Condottiere extends Personnage {
    public Condottiere(){
        super("Condottiere", 8, Caracteristiques.CONDOTTIERE);
    }

    public void utiliserPouvoir(){

        System.out.println("Voici la liste des joueurs et le contenu de leurs quartier:");

        for(int i = 0; i < this.getPlateau().getNombreJoueurs(); i++){

            Joueur currentPlayer = this.getPlateau().getJoueur(i);

            System.out.print("\n\t" + (i+1) + " - " + currentPlayer.getNom() + ": ");

            for(int j = 0; j < currentPlayer.nbQuartiersDansCite(); j++){
                Quartier currentQuartier = currentPlayer.getCite()[j];

                if(currentQuartier instanceof Quartier)
                    System.out.print((j+1) + ": " + currentQuartier.getNom() + " | " + (currentQuartier.getCout()-1) + " PO  ");
            }
           
        }

        System.out.println("\nVous possedez actuellement " + this.getJoueur().nbPieces() + " PO.");

        int selectedTarget;
        Boolean keepAsking = true;

        do{
            System.out.print("Quel joueur choisissez-vous ? (0 pour ne rien faire) > ");
            selectedTarget = Interaction.lireUnEntier(0, this.getPlateau().getNombreJoueurs()+1);

            if(selectedTarget == 0){
                keepAsking = false;
            } else {
                Joueur targetPlayer = this.getPlateau().getJoueur(selectedTarget-1);

                if(targetPlayer.getPersonnage() instanceof Eveque && !targetPlayer.getPersonnage().getAssassine()){
                    System.out.println("/!\\ Vous ne pouvez pas choisir un évêque vivant.");
                } else if(targetPlayer.nbQuartiersDansCite() <= 0){
                    System.out.println("/!\\ Le jour n'a pas de quartier dans sa cite.");
                    keepAsking = true;
                } else {
                    keepAsking = false;

                    int selectedQuartier;
                    Boolean keepAskingQuartier = true;

                    do{
                        System.out.print("Quel quartier choisissez vous ? > ");
                        selectedQuartier = Interaction.lireUnEntier(1, targetPlayer.nbQuartiersDansCite()+1);
                        Quartier targetQuartier = targetPlayer.getCite()[selectedQuartier-1];

                        if(targetQuartier.getNom().equals("Donjon")){
                            System.out.println("/!\\ La merveille Donjon ne peut être affectée par un pouvoir de rang 8.");
                            keepAsking = true;
                        } else {
                            if(targetQuartier.getCout()-1 > this.getJoueur().nbPieces()){
                                System.out.println("/!\\ Vous n'avez pas assez de pièces.");
                            } else {
                                keepAskingQuartier = false;
                                System.out.println("=> On retire " + targetQuartier.getNom() + " à " + targetPlayer.getNom());
                                targetPlayer.retirerQuartierDansCite(targetQuartier.getNom());
                                this.getPlateau().getPioche().ajouter(targetQuartier);
                            }

                        }

                    } while (keepAskingQuartier);

                }

            }

        } while (keepAsking);

    }

    public void utiliserPouvoirAvatar(){

        int selectedTarget;
        Boolean keepAsking = true;

        do{
            selectedTarget = Interaction.randInt(0, this.getPlateau().getNombreJoueurs()+1);

            if(selectedTarget == 0){
                keepAsking = false;
            } else {
                Joueur targetPlayer = this.getPlateau().getJoueur(selectedTarget-1);

                if(targetPlayer.getPersonnage() instanceof Eveque && !targetPlayer.getPersonnage().getAssassine()){
                    keepAsking = true;
                } else if(targetPlayer.nbQuartiersDansCite() <= 0) {
                    keepAsking = true;
                } else {
                    keepAsking = false;

                    int selectedQuartier;
                    Boolean keepAskingQuartier = true;

                    do{
                        selectedQuartier = Interaction.randInt(1, targetPlayer.nbQuartiersDansCite()+1);

                        Quartier targetQuartier = targetPlayer.getCite()[selectedQuartier-1];

                        if(targetQuartier.getNom().equals("Donjon")){ // gestion de la merveille Donjon
                            keepAsking = true;
                        } else {
                            if(targetQuartier.getCout()-1 < this.getJoueur().nbPieces()){
                                keepAskingQuartier = false;
                                System.out.println("=> On retire " + targetQuartier.getNom() + " à " + targetPlayer.getNom());
                                targetPlayer.retirerQuartierDansCite(targetQuartier.getNom());
                                this.getPlateau().getPioche().ajouter(targetQuartier);
                            } 
                        }
                        
                    } while (keepAskingQuartier);

                }

            }

        } while (keepAsking);

    }

    public void percevoirRessourcesSpecifiques(){
        if(this.getJoueur() instanceof Joueur && !this.getAssassine()){

            int sumMilitaire = 0;
            for(int i = 0; i < this.getJoueur().nbQuartiersDansCite(); i++){
                if(this.getJoueur().getCite()[i].getType().equals(Quartier.TYPE_QUARTIERS[1]))
                    sumMilitaire++;
            }

            if(this.getJoueur().quartierPresentDansCite("Ecole de Magie")){
                System.out.println(
                    "\t▀▀ MERVEILLE ECOLE DE MAGIE ▀▀\n"+
                    "De quel type doit être considérée cette carte ?\n"
                );

                for(int i = 0; i < Quartier.TYPE_QUARTIERS.length; i++)
                    System.out.println("\t" + (i+1) + " - " + Quartier.TYPE_QUARTIERS[i]);

                int choix;
                if(this.getJoueur().getNom().contains("PNJ"))
                    choix = Interaction.randInt(1, Quartier.TYPE_QUARTIERS.length+1);
                else
                    choix = Interaction.lireUnEntier(1, Quartier.TYPE_QUARTIERS.length+1);

                if(Quartier.TYPE_QUARTIERS[choix-1].equals(Quartier.TYPE_QUARTIERS[1]))
                    sumMilitaire++;
            }

            this.getJoueur().ajouterPieces(sumMilitaire);
            System.out.println("\nPerception de ressources spécifiques : " + sumMilitaire + " pièces ajoutés.\n");

        }
    }

}
