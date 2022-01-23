package modele;

import java.util.ArrayList;
import java.util.Collections;

public class Pioche {
    
    private ArrayList<Quartier> liste;

    public Pioche(){
        this.liste = new ArrayList<Quartier>();
    }

    
    public Quartier piocher(){
        return this.liste.size() > 0 ? this.liste.remove(0) : null;
    }

    public Quartier piocherCheat(String name){
        Quartier trouve = null;

        for(int i = 0; i < this.liste.size(); i++){

            if(this.liste.get(i).getNom().equals(name)){
                trouve = this.liste.remove(i);
                break;
            } else {
                trouve = null;
            }
        }

        return trouve;
    }

    public void ajouter(Quartier quartier){
        this.liste.add(quartier);
    }

    public int nombreElements(){
        return this.liste.size();
    }

    
    public void melanger(){
        for(int i = 0; i < 10; i++)
            Collections.shuffle(this.liste);
    }

}
