package modele;

public class Quartier{

    private String nom;
    private String type;
    private int coutConstruction;
    private String caracteristiques;

    public static final String[] TYPE_QUARTIERS =  {"RELIGIEUX", "MILITAIRE", "NOBLE", "COMMERCANT", "MERVEILLE"};

    public Quartier(){
        this.setNom("");
        this.setType("");
        this.setCout(0);
        this.setCaracteristiques("");
    }

    public Quartier(String nom, String type, int cout){
        this.setNom(nom);
        this.setType(type);
        this.setCout(cout);
        this.setCaracteristiques("");
    }

    public Quartier(String nom, String type, int cout, String caracteristiques){
        this.setNom(nom);
        this.setType(type);
        this.setCout(cout);
        this.setCaracteristiques(caracteristiques);
    }

    public String getNom(){
        return this.nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        for(String qtType : Quartier.TYPE_QUARTIERS){
            if(qtType == type){
                this.type = qtType;
                break;
            }
            else
                this.type = "";
        }
    }

    public int getCout(){
        return this.coutConstruction;
    }

    public void setCout(int cout){
        if(cout >= 1 && cout <= 6){
            this.coutConstruction = cout;
        } else {
            this.coutConstruction = 0;
        }
    }

    public String getCaracteristiques(){
        return this.caracteristiques;
    }

    public void setCaracteristiques(String caracteristiques){
        this.caracteristiques = caracteristiques;
    }

}