package modele;

import controleur.Interaction;

public class Eveque extends Personnage{
    public Eveque(){
        super("Eveque", 5, Caracteristiques.EVEQUE);
    }

    public void utiliserPouvoir(){}
    public void utiliserPouvoirAvatar(){}

    public void percevoirRessourcesSpecifiques(){

        if(this.getJoueur() instanceof Joueur && !this.getAssassine()){
            int sumReligieux = 0;

            for(int i = 0; i < this.getJoueur().nbQuartiersDansCite(); i++){
                if(this.getJoueur().getCite()[i].getType() == Quartier.TYPE_QUARTIERS[0])
                sumReligieux++;
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

                if(Quartier.TYPE_QUARTIERS[choix-1] == Quartier.TYPE_QUARTIERS[0])
                    sumReligieux++;
            }
            
            this.getJoueur().ajouterPieces(sumReligieux);
            System.out.println("Perception de ressources spécifiques : " + sumReligieux + " pièces ajoutés.");
        }

    }
}
