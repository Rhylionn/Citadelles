package modele;

import controleur.Interaction;

public class Roi extends Personnage {
    
    public Roi(){
        super("Roi", 4, Caracteristiques.ROI);
    }

    public void utiliserPouvoir(){
        if(this.getJoueur() instanceof Joueur && !this.getAssassine()){

            for(int i = 0; i < this.getPlateau().getNombreJoueurs(); i++){
                if(this.getPlateau().getJoueur(i).getPossedeCouronne())
                    this.getPlateau().getJoueur(i).setPossedeCouronne(false);
            }

            this.getJoueur().setPossedeCouronne(true);
            System.out.println("Je prends la couronne.");
        }
    }

    public void utiliserPouvoirAvatar(){
        this.utiliserPouvoir();
    }

    public void percevoirRessourcesSpecifiques(){
        if(this.getJoueur() instanceof Joueur && !this.getAssassine()){
            int sumNoble = 0;

            for(int i = 0; i < this.getJoueur().nbQuartiersDansCite(); i++){
                if(this.getJoueur().getCite()[i].getType() == Quartier.TYPE_QUARTIERS[2])
                    sumNoble++;
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

                if(Quartier.TYPE_QUARTIERS[choix-1] == Quartier.TYPE_QUARTIERS[2])
                    sumNoble++;
            }
            
            this.getJoueur().ajouterPieces(sumNoble);
            System.out.println("\nPerception de ressources spécifiques : " + sumNoble + " pièces ajoutés.\n");
        }
    }

}
