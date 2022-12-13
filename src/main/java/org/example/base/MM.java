package org.example;

import java.sql.SQLOutput;
import java.util.*;
import java.lang.*;

public class MasterMindBase {

    //.........................................................................
    // OUTILS DE BASE
    //.........................................................................

    // fonctions classiques sur les tableaux

    /** pré-requis : nb >= 0
	résultat : un tableau de nb entiers égaux à val
    */
    public static int[] initTab(int nb, int val){
        int[] res = new int[nb];
        for(int i = 0; i < nb; i++)
            res[i] = val;
        return res;

    }

    //______________________________________________

    /** pré-requis : aucun
	résultat : une copie de tab
    */
    public static int[] copieTab(int[] tab){
        return  tab.clone();

    }

    //______________________________________________

    /** pré-requis : aucun
	résultat : la liste des éléments de t entre parenthèses et séparés par des virgules
    */
    public static String listElem(char[] t){
        String res = "(";
        for (char c : t)
            res += c;
        return res +')';

    }

    //______________________________________________

    /** pré-requis : aucun
	résultat : le plus grand indice d'une case de t contenant c s'il existe, -1 sinon
    */
    public static int plusGrandIndice(char[] t, char c){
        int indiceBon = -1;
        for (int i = 0; i < t.length; i++)
            if (t[i] == c)
                indiceBon = i;
        return indiceBon;
    }
    //______________________________________________

    /** pré-requis : aucun
	résultat : vrai ssi c est un élément de t
	stratégie : utilise la fonction plusGrandIndice
    */
    public static boolean estPresent(char[] t, char c){
        return plusGrandIndice(t, c) != -1;

    }

    //______________________________________________

    /** pré-requis : aucun
	action : affiche un doublon et 2 de ses indices dans t s'il en existe
	résultat : vrai ssi les éléments de t sont différents
	stratégie : utilise la fonction plusGrandIndice
    */
    public static boolean elemDiff(char[] t){
        for (int i = 0; i < t.length ; i++)
            if(plusGrandIndice(t, t[i]) != i)
                return false;
        return true;
    }

    //______________________________________________

    /** pré-requis : t1.length = t2.length
	résultat : vrai ssi t1 et t2 contiennent la même suite d'entiers
    */
    public static boolean sontEgaux(int[] t1, int[] t2){
        for (int i = 0; i < t1.length ; i++)
            if (t1[i] != t2[i])
                return false;
        return true;

    }

    //______________________________________________

    // Dans toutes les fonctions suivantes, on a comme pré-requis implicites sur les paramètres lgCode, nbCouleurs et tabCouleurs :
    // lgCode > 0, nbCouleurs > 0, tabCouleurs.length > 0 et les éléments de tabCouleurs sont différents

    // fonctions sur les codes pour la manche Humain

    /** pré-requis : aucun
	résultat : un tableau de lgCode entiers choisis aléatoirement entre 0 et nbCouleurs-1
    */
    public static int[] codeAleat(int lgCode, int nbCouleurs){
        int[] res = new int[lgCode];
        for(int i = 0; i < lgCode; i++)
            res[i] = new  Random().nextInt(nbCouleurs);
        return res;
    }

    //____________________________________________________________

    /** pré-requis : aucun
	action : si codMot n'est pas correct, affiche pourquoi
	résultat : vrai ssi codMot est correct, c'est-à-dire de longueur lgCode et ne contenant que des éléments de tabCouleurs
    */
    public static boolean codeCorrect(String codMot, int lgCode, char[] tabCouleurs){
        if (codMot.length() == lgCode)
            for (int i = 0; i < lgCode; i ++)
                for (int j = 0; j < tabCouleurs.length; j++)
                    if(codMot.charAt(i) != tabCouleurs[j])
                        return false;
            return true;

    }

    //____________________________________________________________

    /** pré-requis : les caractères de codMot sont des éléments de tabCouleurs
	résultat : le code codMot sous forme de tableau d'entiers en remplaçant chaque couleur par son indice dans tabCouleurs
    */
    public static int[] motVersEntiers(String codMot, char[] tabCouleurs){
        int[] idsCouls = new int[codMot.length()];
        for (int i = 0; i < codMot.length() ; i++)
            for(int j = 0; j < tabCouleurs.length ; i++)
                if (codMot.charAt(i) == tabCouleurs[j])
                    idsCouls[i] = tabCouleurs[j];
        return idsCouls;
    }

    //____________________________________________________________

    /** pré-requis : aucun
	action : demande au joueur humain de saisir la (nbCoups + 1)ème proposition de code sous forme de mot, avec re-saisie éventuelle jusqu'à ce
	qu'elle soit correcte (le paramètre nbCoups ne sert que pour l'affichage)
	résultat : le code saisi sous forme de tableau d'entiers
    */
    public static int[] propositionCodeHumain(int nbCoups, int lgCode, char[] tabCouleurs) {
        Scanner scanner = new Scanner(System.in);
        String input;
        do{
            System.out.println("Saisir le "+(nbCoups+1)+"eme coups :");
            input = scanner.next();
        }while (input.length() !=lgCode);

        int[] res = new int[lgCode];
        for (int i = 0; i < lgCode; i++){
                if (String.valueOf(tabCouleurs).contains(String.valueOf(input.charAt(i))))
                    res[i] = String.valueOf(tabCouleurs).indexOf(String.valueOf(input.charAt(i)));
                else{
                    return propositionCodeHumain(nbCoups, lgCode, tabCouleurs);
                }

        }
        return res;


    }


    //____________________________________________________________

    /** pré-requis : cod1.length = cod2.length
	résultat : le nombre d'éléments communs de cod1 et cod2 se trouvant au même indice
	Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne 1 (le "0" à l'indice 3)
    */
    public static int nbBienPlaces(int[] cod1,int[] cod2){
        int counter = 0;
        for(int i = 0; i < cod1.length; i++){
            if(cod1[i] == cod2[i])
                counter ++;
        }
        return counter;
    }

    //____________________________________________________________

    /** pré-requis : les éléments de cod sont des entiers de 0 à nbCouleurs-1
	résultat : un tableau de longueur nbCouleurs contenant à chaque indice i le nombre d'occurrences de i dans cod
	Par exemple, si cod = (1,0,2,0) et nbCouleurs = 6 la fonction retourne (2,1,1,0,0,0)
    */
    public static int[] tabFrequence(int[] cod, int nbCouleurs){
        int[] res = new int[nbCouleurs];
        for(int i = 0; i < res.length; i++)
            res[i] = 0;
        for (int i = 0; i < cod.length; i++){
            res[cod[i]] ++;
        }
        return res;
    }

    //____________________________________________________________

    /** pré-requis : les éléments de cod1 et cod2 sont des entiers de 0 à nbCouleurs-1
	résultat : le nombre d'éléments communs de cod1 et cod2, indépendamment de leur position
	Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne 3 (2 "0" et 1 "1")
    */
    public static int nbCommuns(int[] cod1,int[] cod2, int nbCouleurs){
        int counter = 0;
        int[] freq1 = tabFrequence(cod1, nbCouleurs);
        int[] freq2 = tabFrequence(cod2, nbCouleurs);
        int length = freq1.length<=freq2.length?freq1.length:freq2.length;
        for (int i = 0; i < length; i++)
            if (freq1[i] <= freq2[i])
                counter += freq1[i];
            else{
                counter += freq2[i];
                }
        return counter;

    }

    //____________________________________________________________

    /** pré-requis : cod1.length = cod2.length et les éléments de cod1 et cod2 sont des entiers de 0 à nbCouleurs-1
	résultat : un tableau de 2 entiers contenant à l'indice 0 (resp. 1) le nombre d'éléments communs de cod1 et cod2
	se trouvant  (resp. ne se trouvant pas) au même indice
	Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne (1,2) : 1 bien placé (le "0" à l'indice 3)
	et 2 mal placés (1 "0" et 1 "1")
    */
    public static int[] nbBienMalPlaces(int[] cod1,int[] cod2, int nbCouleurs){
        int[] res = new int[2];
        for(int i = 0; i < cod1.length; i++){
            if (cod1[i] == cod2[i])
                res[0] ++;
        }
        res[1] = nbCommuns(cod1, cod2, nbCouleurs) - res[0];
        return res;

    }

    //____________________________________________________________

    //.........................................................................
    // MANCHEHUMAIN
    //.........................................................................

    /** pré-requis : numMache >= 1
	action : effectue la (numManche)ème manche où l'ordinateur est le codeur et l'humain le décodeur
	(le paramètre numManche ne sert que pour l'affichage)
	résultat :
            - un nombre supérieur à nbEssaisMax, calculé à partir du dernier essai du joueur humain (cf. sujet),
              s'il n'a toujours pas trouvé au bout du nombre maximum d'essais
            - sinon le nombre de codes proposés par le joueur humain
    */
    public static int mancheHumain(int lgCode, char[] tabCouleurs, int numManche, int nbEssaisMax){
        int essais = 0;
        Random random = new Random();
        int[] cod1 = new  int[lgCode];
        int[][] cod = new int[nbEssaisMax][lgCode];
        int[][] reps = new int[nbEssaisMax][2];
        for(int i = 0; i < cod1.length; i++){
            cod1[i] = random.nextInt(0, tabCouleurs.length);
        }
        int[] cod2;
        System.out.println("manche "+ numManche + ", Le joueur decode");
        do {
            cod2 = propositionCodeHumain(essais, lgCode, tabCouleurs);
            reps[essais] = nbBienMalPlaces(cod1, cod2, tabCouleurs.length);
            cod[essais] = cod2;

            System.out.println("Il y a "+ reps[essais][0] + " reponse bien placé");
            System.out.println("Et "+ reps[essais][0] + " reponse juste mais mal placé");
            essais ++;
        }while (essais < nbEssaisMax && !sontEgaux(cod1, cod2));
        return nbEssaisMax >= essais?nbEssaisMax+ (nbBienMalPlaces(cod1, cod2, tabCouleurs.length)[1] + 2 *(lgCode-(nbBienMalPlaces(cod1, cod2, tabCouleurs.length)[0]-nbBienMalPlaces(cod1, cod2, tabCouleurs.length)[1]))):essais;
    }

    //____________________________________________________________

    //...................................................................
    // FONCTIONS COMPLÉMENTAIRES SUR LES CODES POUR LA MANCHE ORDINATEUR
    //...................................................................

    /** pré-requis : les éléments de cod sont des entiers de 0 à tabCouleurs.length-1
	résultat : le code cod sous forme de mot d'après le tableau tabCouleurs
    */
    public static String entiersVersMot(int[] cod, char[] tabCouleurs){
        String res = new String();
        for (int part : cod)
                res += tabCouleurs[part];

        return res;
    }

    //___________________________________________________________________
    
    /** pré-requis : rep.length = 2
	action : si rep n'est pas  correcte, affiche pourquoi, sachant que rep[0] et rep[1] sont 
	         les nombres de bien et mal placés resp.
	résultat : vrai ssi rep est correct, c'est-à-dire rep[0] et rep[1] sont >= 0 et leur somme est <= lgCode
    */
    public static boolean repCorrecte(int[] rep, int lgCode){
        return rep[0] >= 0 && rep[1] >= 0 && rep[0]+rep[1] <= lgCode;
    }

    //___________________________________________________________________
    
    /** pré-requis : aucun
	action : demande au joueur humain de saisir les nombres de bien et mal placés, 
                 avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
	résultat : les réponses du joueur humain dans un tableau à 2 entiers
    */
    public static int[] reponseHumain(int lgCode){
        Scanner scanner = new Scanner(System.in);
        int[] rep = new int[2];
        boolean repeate = false;
        boolean isInt;
        String[] questions = new String[] {"Veuillez saisir le nombre de couleur bien placé : ", "Veuillez saisir le nombre de couleur bonne mais mal placé : "};
        do {
        for (int i =0; i <questions.length; i++){
            repeate = false;
            System.out.println(questions[i]);
            rep[i] = -1;

            do{
                if(repeate) {
                    System.out.println("Veuillez saisir un nombre entier positif:");
                    scanner.nextLine();
                }
                isInt = scanner.hasNextInt();
                if (isInt)
                    rep[i] = scanner.nextInt();
                repeate =true;
            }while (rep[i] < 0);
        }
        }while (!repCorrecte(rep, lgCode));
        return rep;
    }

    //___________________________________________________________________
    
    /** pré-requis : les éléments de cod1 sont des entiers de 0 à nbCouleurs-1
	action : met dans cod1 le code qui le suit selon l'ordre lexicographique dans l'ensemble 
	         des codes de longueur cod1.length à valeurs de 0 à nbCouleurs-1, si ce code existe 
	résultat : vrai ssi l'action a pu être effectuée
    */
    public static boolean passeCodeSuivantLexico(int[] cod1, int  nbCouleurs){
        boolean res = false;
        int i = cod1.length -1;
        while (i >= 0 && !res){
            if (cod1[i] < nbCouleurs-1){
                cod1[i] ++;
                res = true;
            }else{
                cod1[i] =0;
            }
            i--;
        }
        return res;

    }

    //___________________________________________________________________

    /**CHANGE : ajout du paramètre cod1 et modification des spécifications
     *********************************************************************
     pré-requis : cod est une matrice à cod1.length colonnes, rep est une matrice à 2 colonnes, 0 <= nbCoups < cod.length,
     nbCoups < rep.length et les éléments de cod1 et de cod sont des entiers de 0 à nbCouleurs-1
     résultat : vrai ssi cod1 est compatible avec les nbCoups premières lignes de cod et de rep,
     c'est-à-dire que si cod1 était le code secret, les réponses aux nbCoups premières
     propositions de cod seraient les nbCoups premières réponses de rep resp.
     */
    public static boolean estCompat(int [] cod1, int [][] cod,int [][] rep, int nbCoups, int  nbCouleurs){
        boolean res = true;
        for(int i = 0; i <= nbCoups; i++){
            if (!(rep[i][0] == nbBienPlaces(cod1, cod[i]) && rep[i][1] == (nbCommuns(cod1, cod[i], nbCouleurs) - nbBienPlaces(cod1, cod[i])))){
                res = false;
            }
        }
        return res;
    }

    //___________________________________________________________________

    /**CHANGE : renommage de passePropSuivante en passeCodeSuivantLexicoCompat,
     ajout du paramètre cod1 et modification des spécifications
     **************************************************************************
     pré-requis : cod est une matrice à cod1.length colonnes, rep est une matrice à 2 colonnes, 0 <= nbCoups < cod.length,
     nbCoups < rep.length et les éléments de cod1 et de cod sont des entiers de 0 à nbCouleurs-1
     action/résultat : met dans cod1 le plus petit code (selon l'ordre lexicographique (dans l'ensemble
     des codes à valeurs  de 0 à nbCouleurs-1) qui est à la fois plus grand que
     cod1 selon cet ordre et compatible avec les nbCoups premières lignes de cod et rep si ce code existe,
     sinon met dans cod1 le code ne contenant que des "0" et retourne faux
     */
    public static boolean passeCodeSuivantLexicoCompat(int [] cod1, int [][] cod,int [][] rep, int nbCoups, int  nbCouleurs){
        boolean res;
        do{
            res = passeCodeSuivantLexico(cod1, nbCouleurs);

        } while (!estCompat(cod1, cod, rep, nbCoups, nbCouleurs) && res  );
        return estCompat(cod1, cod, rep, nbCoups, nbCouleurs);
    }

    //___________________________________________________________________

    // manche Ordinateur

    /** pré-requis : numManche >= 2
     action : effectue la (numManche)ème  manche où l'humain est le codeur et l'ordinateur le décodeur
     (le paramètre numManche ne sert que pour l'affichage)
     résultat :
     - 0 si le programme détecte une erreur dans les réponses du joueur humain
     - un nombre supérieur à nbEssaisMax, calculé à partir du dernier essai de l'ordinateur (cf. sujet),
     s'il n'a toujours pas trouvé au bout du nombre maximum d'essais
     - sinon le nombre de codes proposés par l'ordinateur
     */
    public static int mancheOrdinateur(int lgCode,char[] tabCouleurs, int numManche, int nbEssaisMax) {
        System.out.println("manche " + numManche + ", l'ordinateur décode");
        int[] codeInt = initTab(lgCode, 0);
        int[][] reps = new int[nbEssaisMax][2];
        int[][] codes = new int[nbEssaisMax][lgCode];
        int essai = 0;
        int[] rep;
        boolean codeCompat;
        do {
            System.out.println("ESSAIS "+(essai+1));
            System.out.println("L'ordi a choisit le code : "+ entiersVersMot(codeInt, tabCouleurs));
            rep = reponseHumain(lgCode);
            codes[essai]= codeInt.clone();
            reps[essai] = rep.clone();

            System.out.println("Il y a "+ reps[essai][0] + " reponse bien placé");
            System.out.println("Et "+ reps[essai][0] + " reponse juste mais mal placé");
            codeCompat = passeCodeSuivantLexicoCompat(codeInt, codes, reps, essai, tabCouleurs.length);
            essai ++;
        }while (codeCompat && !(rep[0] == lgCode) && essai < nbEssaisMax );
        if (essai >= nbEssaisMax)
            return nbEssaisMax + (rep[1] + 2 *(lgCode-(rep[0]-rep[1])));
/*
        malus = nbMalPlaces + 2 * (lgCode − (nbBienPlaces + nbMalPlaces))*/
        else if(!codeCompat)
            return 0;
        else
            return essai;
    }

    //___________________________________________________________________

    //.........................................................................
    // FONCTIONS DE SAISIE POUR LE PROGRAMME PRINCIPAL
    //.........................................................................


    /** pré-requis : aucun
     action : demande au joueur humain de saisir un entier strictement positif,
     avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
     résultat : l'entier strictement positif saisi
     */
    public static int saisirEntierPositif(){
        Scanner scanner = new Scanner(System.in);
        boolean repeate = false;
        boolean isInt;
        int nb = 0;
        do{
            if(repeate) {
                System.out.println("Veuillez saisir un nombre entier positif:");
                scanner.nextLine();
            }
            isInt = scanner.hasNextInt();
            if (isInt)
                nb = scanner.nextInt();
            repeate =true;
        }while (nb <= 0);
        return nb;
    }

    //___________________________________________________________________

    /** pré-requis : aucun
     action : demande au joueur humain de saisir un entier pair strictement positif,
     avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
     résultat : l'entier pair strictement positif saisi
     */
    public static int saisirEntierPairPositif(){
        int nb;
        boolean repeate = false;
        do {
            if (repeate)
                System.out.println("Veuillez saisir un nombre entier positif et pair:");
            nb = saisirEntierPositif();
            repeate = true;
        }while (nb%2!=0);
        return nb;
    }

    //___________________________________________________________________

    /** pré-requis : aucun
     action : demande au joueur humain de saisir le nombre de couleurs (stricement positif),
     puis les noms de couleurs aux initiales différentes,
     avec re-saisie éventuelle jusqu'à ce qu'elle soit correcte
     résultat : le tableau des initiales des noms de couleurs saisis
     */
    public static char[] saisirCouleurs(){
        Scanner scanner = new Scanner(System.in);
        int nbCoul = saisirEntierPositif();
        String chain;
        char[] tabCoul = new char[nbCoul];
        boolean repeate;
        for (int i = 0 ; i < nbCoul; i++){
            repeate = false;
            do{
                if (repeate)
                    System.out.println("1 seule caractere doit etre saisis");
                System.out.println("Veuillez saisir le premier lettre de la couleur("+i+"): ");
                chain = scanner.next();
                repeate = true;
            }while (chain.length() < 1);
            tabCoul[i] = chain.charAt(0);

        }
        return tabCoul;
    }

    //___________________________________________________________________

    //.........................................................................
    // PROGRAMME PRINCIPAL
    //.........................................................................


    /**CHANGE : ajout de : le nombre d'essais maximum doit être strictement positif
     ******************************************************************************
     action : demande à l'utilisateur de saisir les paramètres de la partie (lgCode, tabCouleurs,
     nbManches, nbEssaisMax),
     effectue la partie et affiche le résultat (identité du gagnant ou match nul).
     La longueur d'un code, le nombre de couleurs et le nombre d'essais maximum doivent être strictement positifs.
     Le nombre de manches doit être un nombre pair strictement positif.
     Les initiales des noms de couleurs doivent être différentes.
     Toute donnée incorrecte doit être re-saisie jusqu'à ce qu'elle soit correcte.
     */
    public static void main (String[] args){
        int[] score = new int[2];
        for(int i = 0; i < score.length; i++)
            score[i] = 0;
        Random random = new Random();
        int manche = 0;
        System.out.println("Veuillez saisir la longueur du code a trouver");
        int lgCode = saisirEntierPositif();
        System.out.println("Veuillez saisir les couleurs possible");
        char[] couls = saisirCouleurs();
        System.out.println("Veuillez saisir le nombre de manche");
        int nbMancheMax = saisirEntierPairPositif();
        System.out.println("Veuillez saisir le nombre maximum d'essais");
        int essaisMax = saisirEntierPositif();
        boolean playDecodeNow =  random.nextBoolean();
        int res = 0;
        while (manche < nbMancheMax){
            if (playDecodeNow){
                res = mancheHumain(lgCode, couls, manche, essaisMax);
                if (res < essaisMax){
                    System.out.println("le decodeur a gagne la manche");
                    score[0] += res;
                }else{
                    System.out.println("le decodeur a perdu la manche");
                    score[0] -= res  - essaisMax;

                }
            }else{
                res = mancheOrdinateur(lgCode, couls, manche, essaisMax);
                if (0 == res){
                    System.out.println("Triche Humumaine détecter");
                }else if (res > essaisMax){
                    System.out.println("le decodeur a perdu la manche");
                    score[1] -= res  - essaisMax;
                }if (-1 < res && res <= essaisMax){
                    System.out.println("le decodeur a gagne la manche");
                    score[1] += res;
                }
            }
            playDecodeNow = !playDecodeNow;
            manche ++;
            System.out.println("SCORE : Joueur " +score[0] + " bot "+ score[1]);
        }
        if (score[0]<score[1])
            System.out.println("Ordinateur gagnant");
        else if(score[0]>score[1])
            System.out.println("Joueur gagnant");
        else
            System.out.println("Egalié");

    } // fin main

    //___________________________________________________________________

} // fin MasterMindBase
