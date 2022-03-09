package com.montaury.mus;

import com.montaury.mus.jeu.Partie;
import com.montaury.mus.console.AffichageEvenements;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.Opposants;
import java.util.Scanner;

public class JeuDeMus {
  public static void main(String[] args) {
    System.out.print("Entrez votre nom: ");
    var nomJoueur = new Scanner(System.in).next();
    System.out.print("Entrez le nombre de joueur(s) par équipe (1 ou 2): ");
    var nbJoueur = new Scanner(System.in).next();
    System.out.print("Entrez le nom de l'équipe 1: ");
    var nomEquipe1 = new Scanner(System.in).next();
    System.out.print("Entrez le nom de l'équipe 2: ");
    var nomEquipe2 = new Scanner(System.in).next();
    var equipe1 = new Equipe(nomEquipe1);
    var equipe2 = new Equipe(nomEquipe2);
    var joueurHumain = Joueur.humain(nomJoueur,equipe1);

    var partie = new Partie(new AffichageEvenements(joueurHumain));
    Partie.Resultat resultat;
    if(nbJoueur.equals("2")) {
      resultat = partie.jouer(new Opposants(joueurHumain, Joueur.ordinateur(equipe2), Joueur.ordinateur(equipe1), Joueur.ordinateur(equipe2)));
    }else{
      resultat = partie.jouer(new Opposants(joueurHumain, Joueur.ordinateur(equipe2)));
    }
    System.out.println("Le vainqueur de la partie est " + resultat.vainqueur().nom());
  }
}
