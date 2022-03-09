package com.montaury.mus.jeu.joueur;

import com.montaury.mus.console.InterfaceJoueurHumain;
import com.montaury.mus.jeu.carte.Carte;
import java.util.List;
import com.montaury.mus.jeu.joueur.Equipe;

public class Joueur {
  public static Joueur humain(String nom,Equipe equipe) {
    return new Joueur(nom, new InterfaceJoueurHumain(),equipe);
  }

  public static Joueur ordinateur(Equipe equipe) {
    return new Joueur("Ordinateur"+equipe.nom, new InterfaceJoueurOrdinateur(),equipe);
  }

  private final String nom;
  public final InterfaceJoueur interfaceJoueur;
  private final Main main = Main.vide();
  public Equipe equipe;
  public boolean estCouche;

  public Joueur(String nom, InterfaceJoueur interfaceJoueur,Equipe equipe) {
    this.nom = nom;
    this.interfaceJoueur = interfaceJoueur;
    this.equipe=equipe;
  }

  public String nom() {
    return nom;
  }

  public Main main() {
    return main;
  }

  public void donnerCartes(List<Carte> cartes) {
    main.ajouter(cartes);
    interfaceJoueur.nouvelleMain(main);
  }
}
