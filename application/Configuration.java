package application;

import modele.*;

public class Configuration {
    
    private static Pioche creerEtAjouterQuartier(String nom, String type, int cout, String caracteristiques, int nombreQuartiers, Pioche pioche){

        for(int i = 0; i < nombreQuartiers; i++){
            Quartier quartier = new Quartier(nom, type, cout, caracteristiques);
            pioche.ajouter(quartier);
        }

        return pioche;

    }

    public static Pioche nouvellePioche(){
        Pioche pioche = new Pioche();

        // RELIGIEUX
        creerEtAjouterQuartier("temple", Quartier.TYPE_QUARTIERS[0], 1, "", 3, pioche);
        creerEtAjouterQuartier("église", Quartier.TYPE_QUARTIERS[0], 2, "", 3, pioche);
        creerEtAjouterQuartier("monastère", Quartier.TYPE_QUARTIERS[0], 3, "", 3, pioche);
        creerEtAjouterQuartier("cathédrale", Quartier.TYPE_QUARTIERS[0], 5, "", 2, pioche);

        // Militaires
        creerEtAjouterQuartier("tour de guet", Quartier.TYPE_QUARTIERS[1], 1, "", 3, pioche);
        creerEtAjouterQuartier("prison", Quartier.TYPE_QUARTIERS[1], 2, "", 3, pioche);
        creerEtAjouterQuartier("caserne", Quartier.TYPE_QUARTIERS[1], 3, "", 3, pioche);
        creerEtAjouterQuartier("forteresse", Quartier.TYPE_QUARTIERS[1], 5, "", 2, pioche);

        // NOBLES
        creerEtAjouterQuartier("manoir", Quartier.TYPE_QUARTIERS[2], 3, "", 5, pioche);
        creerEtAjouterQuartier("château", Quartier.TYPE_QUARTIERS[2], 4, "", 4, pioche);
        creerEtAjouterQuartier("palais", Quartier.TYPE_QUARTIERS[2], 5, "", 3, pioche);

        // COMMERCANTS
        creerEtAjouterQuartier("taverne", Quartier.TYPE_QUARTIERS[3], 1, "", 5, pioche);
        creerEtAjouterQuartier("échoppe", Quartier.TYPE_QUARTIERS[3], 2, "", 3, pioche);
        creerEtAjouterQuartier("marché", Quartier.TYPE_QUARTIERS[3], 2, "", 4, pioche);
        creerEtAjouterQuartier("comptoir", Quartier.TYPE_QUARTIERS[3], 3, "", 3, pioche);
        creerEtAjouterQuartier("port", Quartier.TYPE_QUARTIERS[3], 4, "", 3, pioche);
        creerEtAjouterQuartier("hôtel de ville", Quartier.TYPE_QUARTIERS[3], 5, "", 2, pioche);

        return pioche;

    }

    public static PlateauDeJeu configurationDeBase(Pioche p){
        PlateauDeJeu plateau = new PlateauDeJeu();

        // Instantiation

        Joueur joueur1 = new Joueur("Jean");
        plateau.ajouterJoueur(joueur1);

        Joueur joueur2 = new Joueur("PNJ1");
        plateau.ajouterJoueur(joueur2);

        Joueur joueur3 = new Joueur("PNJ3");
        plateau.ajouterJoueur(joueur3);

        Joueur joueur4 = new Joueur("PNJ4");
        plateau.ajouterJoueur(joueur4);


        // Ajout des personnages

        Assassin assassin = new Assassin();
        Voleur voleur = new Voleur();
        Magicienne magicienne = new Magicienne();
        Roi roi = new Roi();
        Eveque eveque = new Eveque();
        Marchande marchande = new Marchande();
        Architecte architecte = new Architecte();
        Condottiere condottiere = new Condottiere();

        // Ajout au plateau

        plateau.ajouterPersonnage(assassin);
        plateau.ajouterPersonnage(voleur);
        plateau.ajouterPersonnage(magicienne);
        plateau.ajouterPersonnage(roi);
        plateau.ajouterPersonnage(eveque);
        plateau.ajouterPersonnage(marchande);
        plateau.ajouterPersonnage(architecte);
        plateau.ajouterPersonnage(condottiere);

        // Les merveilles

        creerEtAjouterQuartier("Bibliothèque", Quartier.TYPE_QUARTIERS[4], 6, CaracteristiquesMerveilles.BIBLIOTHEQUE, 1, p);
        creerEtAjouterQuartier("Carrière", Quartier.TYPE_QUARTIERS[4], 5, CaracteristiquesMerveilles.CARRIERE, 1, p);
        creerEtAjouterQuartier("Cour des Miracles", Quartier.TYPE_QUARTIERS[4], 2, CaracteristiquesMerveilles.COUR_DES_MIRACLES, 1, p);
        creerEtAjouterQuartier("Donjon", Quartier.TYPE_QUARTIERS[4], 3, CaracteristiquesMerveilles.DONJON, 1, p);
        creerEtAjouterQuartier("Dracoport", Quartier.TYPE_QUARTIERS[4], 6, CaracteristiquesMerveilles.DRACOPORT, 1, p);
        creerEtAjouterQuartier("Ecole de Magie", Quartier.TYPE_QUARTIERS[4], 6, CaracteristiquesMerveilles.ECOLE_DE_MAGIE, 1, p);
        creerEtAjouterQuartier("Fontaine aux Souhaits", Quartier.TYPE_QUARTIERS[4], 5, CaracteristiquesMerveilles.FONTAINE_AUX_SOUHAITS, 1, p);
        creerEtAjouterQuartier("Forge", Quartier.TYPE_QUARTIERS[4], 5, CaracteristiquesMerveilles.FORGE, 1, p);
        creerEtAjouterQuartier("Laboratoire", Quartier.TYPE_QUARTIERS[4], 5, CaracteristiquesMerveilles.LABORATOIRE, 1, p);
        creerEtAjouterQuartier("Manufacture", Quartier.TYPE_QUARTIERS[4], 5, CaracteristiquesMerveilles.MANUFACTURE, 1, p);
        creerEtAjouterQuartier("Salle des cartes", Quartier.TYPE_QUARTIERS[4], 5, CaracteristiquesMerveilles.SALLE_DES_CARTES, 1, p);
        creerEtAjouterQuartier("Statue Equestre", Quartier.TYPE_QUARTIERS[4], 3, CaracteristiquesMerveilles.STATUE_EQUESTRE, 1, p);
        creerEtAjouterQuartier("Trésor Impérial", Quartier.TYPE_QUARTIERS[4], 5, CaracteristiquesMerveilles.TRESOR_IMPERIAL, 1, p);
        creerEtAjouterQuartier("Tripot", Quartier.TYPE_QUARTIERS[4], 6, CaracteristiquesMerveilles.TRIPOT, 1, p);
        
        plateau.setPioche(p);
        return plateau;
    }

}
