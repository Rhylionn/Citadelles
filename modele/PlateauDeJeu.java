package modele;

public class PlateauDeJeu {
    
    private Personnage[] listePersonnages;
    private Joueur[] listeJoueurs;
    private Pioche pioche;
    private int nombrePersonnages;
    private int nombreJoueurs;

    public PlateauDeJeu(){
        this.listeJoueurs = new Joueur[9];
        this.listePersonnages = new Personnage[9];
        this.pioche = new Pioche();
        this.nombrePersonnages = 0;
        this.nombreJoueurs = 0;
    }

    public int getNombrePersonnages(){
        return this.nombrePersonnages;
    }

    public int getNombreJoueurs(){
        return this.nombreJoueurs;
    }

    public Pioche getPioche(){
        return this.pioche;
    }

    public void setPioche(Pioche pioche){
        this.pioche = pioche;
    }

    public Personnage[] getListePersonnages(){
        return this.listePersonnages;
    }

    public Personnage getPersonnage(int i){
        if(i >= 0 && i < this.listePersonnages.length-1)
            return this.listePersonnages[i];
        else
            return null;
    }

    public Joueur[] getListeJoueurs(){
        return this.listeJoueurs;
    }

    public Joueur getJoueur(int i){
        if(i >= 0 && i < this.listeJoueurs.length-1) 
            return this.listeJoueurs[i];
        else
            return null;
    }

    public void ajouterPersonnage(Personnage personnage){
        if(personnage != null && this.getNombrePersonnages() != this.listePersonnages.length){
            this.listePersonnages[this.getNombrePersonnages()] = personnage;
            personnage.setPlateau(this);
            this.nombrePersonnages += 1;
        }
    }

    public void ajouterJoueur(Joueur joueur){
        if(joueur != null && this.getNombreJoueurs() != this.listeJoueurs.length){
            this.listeJoueurs[this.getNombreJoueurs()] = joueur;
            this.nombreJoueurs += 1;
        }
    }

}
