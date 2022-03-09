package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.Joueur;
import java.util.Iterator;
import java.util.List;

public class Opposants {
  private Joueur joueurEsku;
  private Joueur joueurNeutre1;
  private Joueur joueurNeutre2;
  private Joueur joueurZaku;

  public Opposants(Joueur joueurEsku, Joueur joueurNeutre1, Joueur joueurNeutre2, Joueur joueurZaku) {
    this.joueurEsku = joueurEsku;
    this.joueurNeutre1 = joueurNeutre1;
    this.joueurNeutre2 = joueurNeutre2;
    this.joueurZaku = joueurZaku;
  }
  public Opposants(Joueur joueurEsku,Joueur joueurZaku) {
    this.joueurEsku = joueurEsku;
    this.joueurZaku = joueurZaku;
  }

  public void tourner() {
    if(joueurNeutre1!=null) {
      var tmp = joueurNeutre1;
      var tmp2 = joueurNeutre2;
      var tmp3 = joueurZaku;
      var tmp4 = joueurEsku;
      joueurZaku = tmp4;
      joueurEsku = tmp;
      joueurNeutre1 = tmp2;
      joueurNeutre2 = tmp3;
    }else{
      var tmp = joueurEsku;
      joueurEsku = joueurZaku;
      joueurZaku = tmp;
    }
  }

  public Joueur joueurEsku() {
    return joueurEsku;
  }

  public Joueur joueurZaku() {
    return joueurZaku;
  }

  public List<Joueur> dansLOrdre() {
    if(joueurNeutre1!=null) {
      return List.of(joueurEsku, joueurNeutre1, joueurNeutre2, joueurZaku);
    }else{
      return List.of(joueurEsku,joueurZaku);
    }
  }
}
