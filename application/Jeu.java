package application;

import modele.*;
import controleur.Interaction;

public class Jeu {
    
    private PlateauDeJeu plateauDeJeu;
    private int nuemroConfiguration;
    private int nbTour = 0;
    //private Random generateur;

    public Jeu(int nbConfig){
        this.plateauDeJeu = new PlateauDeJeu();
        this.nuemroConfiguration = nbConfig;
        //this.generateur = new Random();
    }

    public void jouer(){

        System.out.println(
            "\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n"+
            "\t\t  Bienvenue \n"+
            "\t\tsur Citadelles\n"+
            "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n"
        );

        System.out.print(
            "\nPar quoi voulez vous commencer ? \n\n" +
            "\t1 - Afficher les règles\n" +
            "\t2 - Jouer une partie\n" +
            "\t3 - Quitter\n" +
            "\nVotre choix > "
        );

        int choix = Interaction.lireUnEntier(1, 4);

        switch (choix) {
            case 1:
                this.afficherLesRegles();
                break;
            
            case 2:
                this.jouerPartie();
                break;
            case 3:
                System.out.println("Au revoir, à bientôt!");
                System.exit(0);
            default:
                break;
        }
    }

    private void afficherLesRegles(){
        System.out.println("Voici les règles");
    }

    private void jouerPartie(){
        this.initialisation();

        do{
            this.tourDeJeu();
            this.gestionCouronne();
            this.reinitialisationPersonnages();
        } while(!this.partieFinie());

    }

    private void initialisation(){
        Pioche pioche = Configuration.nouvellePioche();

        switch (this.nuemroConfiguration) {
            case 1:
                this.plateauDeJeu = Configuration.configurationDeBase(pioche);
                break;
            default:
                this.plateauDeJeu = Configuration.configurationDeBase(pioche);
                break;
        }

        /*  FOR TESTING PURPOSES ONLY
            this.plateauDeJeu.getJoueur(0).ajouterPieces(1000); // Add 1000 PO to main player
            this.plateauDeJeu.getJoueur(0).ajouterQuartierDansMain(this.plateauDeJeu.getPioche().piocherCheat("Tripot")); // Add selected quarter to the player
        */


        // mélange de pioche

        pioche.melanger();

        // On remet les personnages dans l'ordre croissant des rangs

        Personnage tempPersonnage;
        for(int i = 1; i < this.plateauDeJeu.getNombrePersonnages(); i++){
            if(this.plateauDeJeu.getPersonnage(i-1).getRang() > this.plateauDeJeu.getPersonnage(i).getRang()){
                tempPersonnage = this.plateauDeJeu.getPersonnage(i-1);
                this.plateauDeJeu.getListePersonnages()[i-1] = this.plateauDeJeu.getPersonnage(i);
                this.plateauDeJeu.getListePersonnages()[i] = tempPersonnage;
            }
        }


        // Génération de la couronne
        int crownPlayerId = Interaction.randInt(0, this.plateauDeJeu.getNombreJoueurs());
        this.plateauDeJeu.getJoueur(crownPlayerId).setPossedeCouronne(true);

        // Ajout des ressources de base aux personnages
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){
            this.plateauDeJeu.getJoueur(i).ajouterPieces(2);

            for(int j = 0; j < 4; j++)
                this.plateauDeJeu.getJoueur(i).ajouterQuartierDansMain(pioche.piocher());
        }
    }

    private void gestionCouronne(){

        // Gestion de la couronne
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){
            if(this.plateauDeJeu.getJoueur(i).getPersonnage() instanceof Roi)
                this.plateauDeJeu.getJoueur(i).setPossedeCouronne(true);    
        }
    }

    private void reinitialisationPersonnages(){
        
        // réinitialisation des personnages
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++)
            this.plateauDeJeu.getJoueur(i).getPersonnage().reinitialiser();
    }

    private Boolean partieFinie(){
        Boolean end = false;
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){

            if(this.plateauDeJeu.getJoueur(i).nbQuartiersDansCite() >= 7){
                end = true;
                break;
            }

        }
        return end;
    }

    private void tourDeJeu(){
        this.nbTour++;
        this.choixPersonnages();

        Joueur winner = null;
        for(int i = 0; i < this.plateauDeJeu.getNombrePersonnages(); i++){
            if(!(this.plateauDeJeu.getPersonnage(i).getJoueur() instanceof Joueur))
                continue;

            Personnage personnage = this.plateauDeJeu.getPersonnage(i);
            Joueur joueur = personnage.getJoueur();

            System.out.println(
                "\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀" + this.nbTour + "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n"+
                "\tTOUR DU PERSONNAGE " + personnage.getNom() + "\n"
            );

            if(!joueur.getPersonnage().getAssassine()){

                // On retire les pièces du joueur volé et on les ajoutes au voleur
                if(joueur.getPersonnage().getVole()){

                    for(int k = 0; k < this.plateauDeJeu.getNombreJoueurs(); k++){
                        Joueur j = this.plateauDeJeu.getJoueur(k);
                        if(j.getPersonnage() instanceof Voleur)
                            j.ajouterPieces(joueur.nbPieces());
                    }
                    joueur.retirerPieces(joueur.nbPieces());
                }

                this.percevoirRessource(joueur);
                System.out.println("\n   ▀▀ PERCEPTION RESSOURCES SPECIFIQUES ▀▀\n");
                
                joueur.getPersonnage().percevoirRessourcesSpecifiques();

                // Gestion de la merveille forge
                if(joueur.quartierPresentDansCite("Forge")){
                    System.out.println(
                        "\t▀▀ MERVEILLE FORGE ▀▀\n"+
                        "\tVoulez vous échanger 2 PO pour 3 cartes ?\n"
                    );

                    Boolean exchangeGold;
                    if(joueur.getNom().contains("PNJ"))
                        exchangeGold = Interaction.randBool();
                    else
                        exchangeGold = Interaction.lireOuiOuNon();

                    if(exchangeGold){
                        joueur.retirerPieces(2);
                        for(int p = 0; p < 3; p++){
                            Quartier quartierAjoute = this.plateauDeJeu.getPioche().piocher();
                            joueur.ajouterQuartierDansMain(quartierAjoute);
                            System.out.println(quartierAjoute.getNom() + " ajouté à votre main.");
                        }
                    }
                }

                // Gestion de la merveille laboratoire
                if(joueur.quartierPresentDansCite("Laboratoire")){
                    System.out.print("Vous bénéficiez de l'effet de la merveille Laboratoire.\nVoulez vous défausser une carte pour 1 or ? (o/n) ");

                    System.out.println(
                        "\t▀▀ MERVEILLE LABORATOIRE ▀▀\n"+
                        "\tVoulez vous défausser une carte pour 1 PO?\n"
                    );

                    Boolean exchangeCard;
                    if(joueur.getNom().contains("PNJ"))
                        exchangeCard = Interaction.randBool();
                    else
                        exchangeCard = Interaction.lireOuiOuNon();

                    if(exchangeCard){
                        System.out.println("Liste des cartes défaussables: ");

                        for(int p = 0; p < joueur.nbQuartiersDansMain(); p++)
                            System.out.println("\t" + (p+1) + " - " + joueur.getMain().get(p).getNom());
                        
                        int choixExchange;
                        if(joueur.getNom().contains("PNJ"))
                            choixExchange = Interaction.randInt(1, joueur.nbQuartiersDansMain()+1);
                        else
                            choixExchange = Interaction.lireUnEntier(1, joueur.nbQuartiersDansMain()+1);

                        joueur.getMain().remove(joueur.getMain().get(choixExchange-1));
                        joueur.ajouterPieces(1);
                    }
                }


                System.out.print("Voulez vous utiliser votre pouvoir ? (o/n) > ");

                Boolean usePower;
                if(joueur.getNom().contains("PNJ"))
                    usePower = Interaction.randBool();
                else
                    usePower = Interaction.lireOuiOuNon();
    
                if(usePower){
                    System.out.println(
                        "\n▀▀                                          ▀▀\n"+
                        "\t" +joueur.getPersonnage().getNom() + " UTILISE SON POUVOIR\n"
                    );
                    if(joueur.getNom().contains("PNJ"))
                        joueur.getPersonnage().utiliserPouvoirAvatar();
                    else
                        joueur.getPersonnage().utiliserPouvoir();

                    System.out.println("▄▄                                          ▄▄\n");  
                }
                
                System.out.print("Voulez vous constuire ? (o/n) > ");
    
                Boolean build;
    
                if(joueur.getNom().contains("PNJ"))
                    build = Interaction.randBool();
                else
                    build = Interaction.lireOuiOuNon();
                
                if(build){
                    System.out.println(
                        "\n▀▀                                          ▀▀\n"+
                        "\t    " + joueur.getPersonnage().getNom() + " CONSTRUIT\n"+
                        "\nVous avez " + joueur.nbPieces() + " PO\n"+
                        (joueur.nbQuartiersDansCite() == 0 ? "Votre quartier est vide\n" : "Votre quartier est composé de :")
                    );

                    // Affichage des quartiers de la cite: 
                    if(joueur.nbQuartiersDansCite() > 0){
                        for(int k = 0; k < joueur.nbQuartiersDansCite(); k++)
                            System.out.println("\t- " + joueur.getCite()[k].getNom()); 
                    }

                    int nbConstruction = joueur.getPersonnage() instanceof Architecte ? 3 : 1;
                    for(int n = 1; n <= nbConstruction; n++){
                        System.out.println("\nVotre main est composée de :");

                        for(int k = 0; k < joueur.nbQuartiersDansMain(); k++){
                            Quartier quartier = joueur.getMain().get(k);
                            System.out.println("\t" + (k+1) + " - " + quartier.getNom() + " | " + quartier.getType() + " | " + quartier.getCout() + " PO");
                        }
                        
                        int constructionChoix;
                        Boolean keepAsking = false;

                        do {
                            System.out.print("\nConstruction n°" + n + ": Que voulez vous construire ? (0: ne rien faire) > ");
                            
                            if(joueur.getNom().contains("PNJ"))
                                constructionChoix = Interaction.randInt(0, joueur.nbQuartiersDansMain()+1);
                            else
                                constructionChoix = Interaction.lireUnEntier(0, joueur.nbQuartiersDansMain()+1);

                            if(constructionChoix == 0){
                                System.out.println("/!\\ Construction annulée");
                                keepAsking = false;
                            } else {
                                Quartier quartierChoisi = joueur.getMain().get(constructionChoix-1);

                                if(quartierChoisi.getNom().equals("Tripot")){
                                    if((joueur.nbQuartiersDansMain()-1) + joueur.nbPieces() < quartierChoisi.getCout()){
                                        System.out.println("Vous ne possèderez pas assez pour l'achat du tripot!");
                                    } else {

                                        System.out.println(
                                            "\t▀▀ MERVEILLE TRIPOT ▀▀\n"+
                                            "Combien de cartes voulez vous échanger pour payer le tripot ?\n"+
                                            "Vous possèdez " + (joueur.nbQuartiersDansMain()-1)+ " cartes échangeables dans votre main \n"
                                        );
                                        
                                        Boolean loopContinue = true;
                                        do{
                                            System.out.print("Votre choix > ");
                                            int nbCartes;
                                            if(joueur.getNom().contains("PNJ"))
                                                nbCartes = Interaction.randInt(0, joueur.nbQuartiersDansMain());
                                            else
                                                nbCartes = Interaction.lireUnEntier(0, joueur.nbQuartiersDansMain());
    
                                            if(nbCartes > joueur.nbQuartiersDansMain()-1){
                                                System.out.println("/!\\ Vous n'avez pas assez de cartes en main.");
                                                loopContinue = true;
                                            } else if (joueur.nbPieces() < quartierChoisi.getCout()-nbCartes){
                                                System.out.println("Vous ne possèdez pas le montant restant.");
                                                loopContinue = true;
                                            } else {
                                                System.out.println("Il vous reste " + (quartierChoisi.getCout()-nbCartes) + " PO à payer.");
                                                loopContinue = false;
                                                keepAsking = false;
                                                joueur.ajouterQuartierDansCite(quartierChoisi);
                                                joueur.getMain().remove(quartierChoisi);
                                                joueur.retirerPieces(quartierChoisi.getCout()-nbCartes);
                                                System.out.println("Le quartier " + quartierChoisi.getNom() + " à été ajouté à votre cité!\n");
                                            }
                                            
                                        } while(loopContinue);

                                    }

                                } else {

                                    if(quartierChoisi.getCout() > joueur.nbPieces()){
                                        System.out.println("/!\\ Vous n'avez pas assez de pièces.");
                                        keepAsking = true;
                                    } else if(joueur.quartierPresentDansCite(quartierChoisi.getNom()) && !joueur.quartierPresentDansCite("Carrière")){
                                        System.out.println("/!\\ Ce quartier est déjà dans votre cité.");
                                        keepAsking = true;
                                    } else {
                                        if(joueur.quartierPresentDansCite(quartierChoisi.getNom()) && joueur.quartierPresentDansCite("Carrière"))
                                            System.out.println("▀▀ MERVEILLE CARRIERE ▀▀");
    
                                        if(joueur.quartierPresentDansCite("Manufacture") && quartierChoisi.getType().equals(Quartier.TYPE_QUARTIERS[4])){
                                            System.out.println("▀▀ MERVEILLE MANUFACTURE ▀▀");
                                            joueur.retirerPieces(quartierChoisi.getCout()-1);
                                        } else {
                                            joueur.retirerPieces(quartierChoisi.getCout());
                                        }
                                        // Gestion de la Merveille : cour des miracles
                                        if(quartierChoisi.getNom().equals("Cour des Miracles")){
                                            System.out.println("\n▀▀ MERVEILLE COUR DES MIRACLES ▀▀\nDe quel type doit être considéré cette carte ?");
                                            for(int x = 0; x < Quartier.TYPE_QUARTIERS.length; x++)
                                                System.out.println("\t" + (x+1) + " - " + Quartier.TYPE_QUARTIERS[x]);
    
                                            System.out.println("Votre choix > ");
                                            int choixMiracle;
                                            if(joueur.getNom().equals("PNJ"))
                                                choixMiracle = Interaction.randInt(1, Quartier.TYPE_QUARTIERS.length+1);
                                            else
                                                choixMiracle = Interaction.lireUnEntier(1, Quartier.TYPE_QUARTIERS.length+1);
    
                                            quartierChoisi.setType(Quartier.TYPE_QUARTIERS[choixMiracle-1]);
                                        }
    
                                        joueur.ajouterQuartierDansCite(quartierChoisi);
                                        joueur.getMain().remove(quartierChoisi);
                                        System.out.println("Le quartier " + quartierChoisi.getNom() + " à été ajouté à votre cité!\n");
    
                                        keepAsking = false;
    
                                        if(joueur.nbQuartiersDansCite() >= 7)
                                            winner = joueur;
                                    }

                                }
                            }
                        } while(keepAsking);
                    }
                }

                System.out.println("\n▄▄                                          ▄▄\n");  
            }

            System.out.println(
                "\tTOUR DE " + personnage.getNom() + " TERMMINE\n"+
                "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n"
            );
        }
        
        if(this.partieFinie())
            this.calculDesPoints(winner);
    }

    private void choixPersonnages(){
        System.out.println(
            "\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n"+
            "\t    CHOIX DES PERSONNAGES\n"
        );

        int carteVisible1 = 0;
        int carteVisible2 = 0;
        int carteCachee1 = 0;

        do{
            carteVisible1 = Interaction.randInt(0, this.plateauDeJeu.getNombrePersonnages());
            carteVisible2 = Interaction.randInt(0, this.plateauDeJeu.getNombrePersonnages());
            carteCachee1 = Interaction.randInt(0, this.plateauDeJeu.getNombrePersonnages());
        } while(carteVisible1 == carteVisible2 || carteVisible2 == carteCachee1 || carteVisible1 == carteCachee1);

        System.out.println("Le personnage " + this.plateauDeJeu.getPersonnage(carteVisible1).getNom() + " est écarté face visible.");
        System.out.println("Le personnage " + this.plateauDeJeu.getPersonnage(carteVisible2).getNom() + " est écarté face visible.");
        System.out.println("Un personnage est écarté face caché");

        // ^ a refaire

        Personnage[] availableCharacters = this.plateauDeJeu.getListePersonnages().clone();
        availableCharacters[carteVisible1] = null;
        availableCharacters[carteVisible2] = null;
        availableCharacters[carteCachee1] = null;

        int crownPlayerId = 0;

        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++)
            crownPlayerId = this.plateauDeJeu.getJoueur(i).getPossedeCouronne() ? i : crownPlayerId;

        int playerIteration = 0;
        do{
            int currentPlayer = (crownPlayerId + playerIteration) % this.plateauDeJeu.getNombreJoueurs();

            System.out.println(
                "\n▀▀                                          ▀▀\n"+
                "\t\t  " + this.plateauDeJeu.getJoueur(currentPlayer).getNom() + " " + (this.plateauDeJeu.getJoueur(currentPlayer).getPossedeCouronne() == true ? "👑" : "") + "\n"+
                "     Quel personnage choisissez vous ?\n"+
                "▀▀                                          ▀▀\n"
            );

            System.out.print("Liste des personnages disponibles:\n");
            for(int i = 0; i < this.plateauDeJeu.getNombrePersonnages(); i++){
                if(availableCharacters[i] instanceof Personnage)
                    System.out.println("\t" + i + " - " + this.plateauDeJeu.getPersonnage(i).getNom());
            }
            
            int choix;
            do{
                System.out.print("\nVotre choix > ");
                if(this.plateauDeJeu.getJoueur(currentPlayer).getNom().contains("PNJ"))
                    choix = Interaction.randInt(0, this.plateauDeJeu.getNombrePersonnages());
                else
                    choix = Interaction.lireUnEntier(0, this.plateauDeJeu.getNombrePersonnages());


                if(!(availableCharacters[choix] instanceof Personnage))
                    System.out.println("/!\\ Attention! Ce choix n'est pas disponible!");
                
            } while(!(availableCharacters[choix] instanceof Personnage));

            this.plateauDeJeu.getPersonnage(choix).setJoueur(this.plateauDeJeu.getJoueur(currentPlayer));
            availableCharacters[choix] = null;
            
            playerIteration++;

        } while(playerIteration <= this.plateauDeJeu.getNombreJoueurs()-1);

        System.out.println(
            "\n\tCHOIX DES PERSONNAGES TERMINE\n"+
            "\t Le tour peut commencer!\n"+
            "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n"
        );

    }

    private void percevoirRessource(Joueur j){
        System.out.println("\n       ▄▄ PERCEPTION DES RESSOURCES ▄▄\n");

        System.out.println(
            "Vous disposez de :\n" +
            "\t" + j.nbPieces() + " pièces d'or\n"+
            "\t" + j.nbQuartiersDansMain() + " quartiers en main\n"
        );

        System.out.print(
            "\nQue souhaitez vous percevoir ? \n\n" +
            "\t1 - 2 pièces d'or\n" +
            "\t2 - Piocher \n" + 
            "\nVotre choix > "
        );

        int choixPerception;
        if(j.getNom().contains("PNJ"))
            choixPerception = Interaction.randInt(1, 3);
        else
            choixPerception = Interaction.lireUnEntier(1, 3);

        if(choixPerception == 1){
            j.getPersonnage().ajouterPieces();
            System.out.println("\n2 PO ajoutés à votre trésor.");
        } else if(choixPerception == 2) {
            Quartier[] choixQuartiers = new Quartier[2];

            System.out.println(
                "\nCartes piochées disponibles :\n"+
                "\t n° - nom | type | coût \n"
            );

            for(int i = 0; i < choixQuartiers.length; i++){
                choixQuartiers[i] = this.plateauDeJeu.getPioche().piocher();
                System.out.println("\t" + (i+1) + " - " + choixQuartiers[i].getNom() + " | " + choixQuartiers[i].getType() + " | " + choixQuartiers[i].getCout() + " PO");
            }

            // Gestion de la merveille bibliothque.
            if(j.quartierPresentDansCite("Bibliotheque")){
                System.out.println("\t▀▀ MERVEILLE BIBLIOTHEQUE ▀▀\n"+
                    "Les deux cartes ont été ajoutés à votre main"
                );

                for(int i = 0; i < choixQuartiers.length; i++)
                j.ajouterQuartierDansCite(choixQuartiers[i]);
            
            } else {
                System.out.print("Votre choix > ");
                int choixPioche;
                if(j.getNom().contains("PNJ"))
                    choixPioche = Interaction.randInt(1, 3);
                else 
                    choixPioche = Interaction.lireUnEntier(1, 3);
    
                j.ajouterQuartierDansMain(choixQuartiers[choixPioche-1]);
                this.plateauDeJeu.getPioche().ajouter(choixQuartiers[choixPioche % 2]);
    
                System.out.println("\n" + choixQuartiers[choixPioche-1].getNom() + " ajoutée à votre main.\n");
            }

        }

    }

    private void calculDesPoints(Joueur winner){
        System.out.println(
            "\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n"+
            "\tFIN DE LA PARTIE\n"+
            "\t" + this.nbTour + " TOURS COMPLETES\n"+
            "\t" + winner.getNom() + " est le premier à possèder une cité complète\n"
        );

        System.out.println("\t▀▀ SCORE FINAUX ▀▀");
        for(int i = 0; i < this.plateauDeJeu.getNombreJoueurs(); i++){
            Joueur joueur = this.plateauDeJeu.getJoueur(i);
            String[] typeQuartierCopy = Quartier.TYPE_QUARTIERS.clone();
            Boolean typeBonus = true;

            int points = 0;
            if(joueur.equals(winner))
                points += 4;
            else if(joueur.nbQuartiersDansCite() >= 7)
                points += 2;

            for(int j = 0; j < joueur.nbQuartiersDansCite(); j++){
                Quartier quartier = joueur.getCite()[i];
                if(quartier instanceof Quartier)
                    points += quartier.getCout();

                if(joueur.quartierPresentDansCite("Fontaine aux Souhaits") && quartier.getType().equals(Quartier.TYPE_QUARTIERS[4]))
                    points += 1;
                
                for(int k = 0; k < typeQuartierCopy.length; k++){
                    if(quartier.getType().equals(typeQuartierCopy[k]))
                        typeQuartierCopy[k] = null;
                }
            }

            for(String type : typeQuartierCopy){
                if(type != null){
                    typeBonus = false;
                    break;
                }
            }

            if(typeBonus)
                points += 3;

            // gestion des merveilles

            if(joueur.quartierPresentDansCite("Dracoport"))
                points += 2;

            if(joueur.quartierPresentDansCite("Salle des cartes"))
                points += joueur.nbQuartiersDansMain();
            
            if(joueur.quartierPresentDansCite("Statue Equestre") && joueur.getPossedeCouronne())
                points += 5;
            
            if(joueur.quartierPresentDansCite("Trésor Impérial"))
                points += joueur.nbPieces();
            
            System.out.println("\t" + joueur.getNom() + " - " + points + " points.");

        }
    }

}
