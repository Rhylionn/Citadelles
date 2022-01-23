package modele;

import controleur.Interaction;

public class Marchande extends Personnage{

    public Marchande(){
        super("Marchande", 6, Caracteristiques.MARCHANDE);
    }

    public void utiliserPouvoir(){
        this.getJoueur().ajouterPieces(1);
    }

    public void utiliserPouvoirAvatar(){
        this.utiliserPouvoir();
    }

    public void percevoirRessourcesSpecifiques(){
        if(this.getJoueur() instanceof Joueur && !this.getAssassine()){

            int sumCommercant = 0;
            for(int i = 0; i < this.getJoueur().nbQuartiersDansCite(); i++){
                if(this.getJoueur().getCite()[i].getType().equals(Quartier.TYPE_QUARTIERS[3])){
                    sumCommercant++;
                }
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

                if(Quartier.TYPE_QUARTIERS[choix-1] == Quartier.TYPE_QUARTIERS[3])
                    sumCommercant++;
            }

            this.getJoueur().ajouterPieces(sumCommercant);
            System.out.println("\nPerception de ressources spécifiques : " + sumCommercant + " pièces ajoutés.\n");

        }


    }

}
