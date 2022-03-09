package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import java.util.ArrayList;
import java.util.List;

public class Participants {
  private final List<Joueur> dansLOrdre;

  public Participants(List<Joueur> dansLOrdre) {
    this.dansLOrdre = dansLOrdre;
  }

  public boolean aucun() {
    return dansLOrdre.isEmpty();
  }

  public boolean estUnique() {
    return dansLOrdre.size() == 1;
  }

  public Joueur premier() {
    return dansLOrdre.get(0);
  }
  public Joueur deuxieme() {
    return dansLOrdre.get(1);
  }
  public Joueur troisieme() {
    return dansLOrdre.get(2);
  }

 /*public Joueur adversaireDe(Joueur joueurParlant) {
    return joueurParlant == premier() ? dansLOrdre.get(1) : premier();

  }*/
 public Joueur adversaireDe(Joueur joueurParlant) {
    int indiceJoueur= dansLOrdre.lastIndexOf(joueurParlant);

    if(indiceJoueur==dansLOrdre.size()-1)//si le joueur qui parle est dernier
    {
        return  premier();
    }

    else{
        return dansLOrdre.get(indiceJoueur+1);

    }
 }
  public Iterable<Joueur> dansLOrdre() {
    return dansLOrdre;
  }

  public Participants retirer(Joueur joueur) {
    var joueurs = new ArrayList<>(dansLOrdre);
    joueurs.remove(joueur);
    return new Participants(joueurs);
  }
}
