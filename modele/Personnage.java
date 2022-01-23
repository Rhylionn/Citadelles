package modele;

public abstract class Personnage {
    
    private String nom;
    private int rang;
    private String caracteristiques;
    private Joueur joueur;
    private Boolean assassine;
    private Boolean vole;
    private PlateauDeJeu plateau;

    public Personnage(String nom, int rang, String caracteristiques){
        this.nom = nom;
        this.rang = rang;
        this.caracteristiques = caracteristiques;
        this.joueur = null;
        this.vole = false;
        this.assassine = false;
    }

    public String getNom(){
        return this.nom;
    }

    public int getRang(){
        return this.rang;
    }

    public String getCaracteristiques(){
        return this.caracteristiques;
    }

    public Joueur getJoueur(){
        return this.joueur;
    }

    public Boolean getAssassine(){
        return this.assassine;
    }

    public Boolean getVole(){
        return this.vole;
    }

    public PlateauDeJeu getPlateau(){
        return this.plateau;
    }

    public void setJoueur(Joueur j){
        this.joueur = j;
        this.joueur.monPersonnage = this;
    }

    public void setVole(){
        this.vole = true;
    }

    public void setAssassine(){
        this.assassine = true;
    }

    public void setPlateau(PlateauDeJeu plateau){
        this.plateau = plateau;
    }

    public void ajouterPieces(){
        if(this.joueur instanceof Joueur && !this.assassine){
            this.joueur.ajouterPieces(2);
        }
    }

    public void ajouterQuartier(Quartier nouveau){
        if(this.joueur instanceof Joueur && !this.assassine){
            this.joueur.ajouterQuartierDansMain(nouveau);
        }
    }

    public void construire(Quartier nouveau){
        if(this.joueur instanceof Joueur && !this.assassine){
            this.joueur.ajouterQuartierDansCite(nouveau);
        }
    }

    // ???
    public void percevoirRessourcesSpecifiques(){
        if(this.joueur instanceof Joueur && !this.assassine){
            System.out.println("Aucune ressource spécifique.");
        }
    }

    public abstract void utiliserPouvoir();

    public abstract void utiliserPouvoirAvatar();

    public void reinitialiser(){
        if(this.joueur != null)
            this.joueur.monPersonnage = null;

        this.joueur = null;
        this.vole = false;
        this.assassine = false;
    }

}
