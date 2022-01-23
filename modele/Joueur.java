package modele;
import java.util.ArrayList;
import java.util.Random;

public class Joueur {
    
    private String nom;
    private int tresor;
    private Quartier[] cite;
    private int nbQuartier;
    private ArrayList<Quartier> main;
    private Boolean possedeCouronne;
    protected Personnage monPersonnage;

    public Joueur(String nom){
        this.nom = nom;
        this.tresor = 0;
        this.cite = new Quartier[8];
        this.nbQuartier = 0;
        this.main = new ArrayList<Quartier>();
        this.possedeCouronne = false;
        this.monPersonnage = null;
    }

    public String getNom(){
        return this.nom;
    }

    public int nbPieces(){
        return this.tresor;
    }

    public int nbQuartiersDansCite(){
        return this.nbQuartier;
    }


    public Quartier[] getCite(){
        return this.cite;
    }

    public ArrayList<Quartier> getMain(){
        return this.main;
    }

    public Boolean getPossedeCouronne(){
        return this.possedeCouronne;
    }

    public void setPossedeCouronne(Boolean state){
        this.possedeCouronne = state;
    }

    public Personnage getPersonnage(){
        return this.monPersonnage;
    }

    // Methods

    public void ajouterPieces(int pieces){
        if(pieces > 0){
            this.tresor += pieces;
        }
    }

    public void retirerPieces(int pieces){
        if(pieces > 0 && pieces <= this.tresor){
            this.tresor -= pieces;
        }
    }

    public void ajouterQuartierDansCite(Quartier quartier){
        if(this.nbQuartiersDansCite() < 8){
            this.cite[this.nbQuartiersDansCite()] = quartier;
            this.nbQuartier++;
        }
    }

    public Boolean quartierPresentDansCite(String nom){
        Boolean isPresent = false;

        for(Quartier qt : this.cite){
            if(qt instanceof Quartier && qt.getNom() == nom){
                isPresent = true;
                break;
            }
        }

        return isPresent;
    }

    
    public Quartier retirerQuartierDansCite(String nom){
        Quartier quartierTrouve = null;

        for(int i = 1; i < this.cite.length; i++){
            if(this.cite[i-1] instanceof Quartier && this.cite[i-1].getNom().equals(nom)){
                quartierTrouve = this.cite[i-1];
                this.cite[i-1] = null;
            }

            if(this.cite[i-1] == null){
                this.cite[i-1] = this.cite[i];
                this.cite[i] = null;
            }
        }

        this.nbQuartier--;
        return quartierTrouve != null ? quartierTrouve : null;
    }

    public void ajouterQuartierDansMain(Quartier quartier){
        this.main.add(quartier);
    }
    
    public int nbQuartiersDansMain(){
        return this.main.size();
    }

    public Quartier retirerQuartierDansMain(){
        if(nbQuartiersDansMain() > 0){
            Random generateur = new Random();
            int numeroHasard = generateur.nextInt(nbQuartiersDansMain());
            return this.main.remove(numeroHasard);
        } else {
            return null;
        }
    }

    public void reinitialiser(){
        this.main.clear();
        this.cite = new Quartier[8];
        this.nbQuartier = 0;
        this.tresor = 0;
    }

}
