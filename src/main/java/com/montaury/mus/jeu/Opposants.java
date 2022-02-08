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

  public void tourner() {
    var tmp = joueurEsku;
    var tmp2 = joueurNeutre1;
    joueurEsku = joueurZaku;
    joueurNeutre1=tmp;
    joueurZaku = joueurNeutre2;
    joueurNeutre2=tmp2;
  }

  public Joueur joueurEsku() {
    return joueurEsku;
  }

  public Joueur joueurZaku() {
    return joueurZaku;
  }

  public Joueur joueurNeutre1(){
    return joueurNeutre1;
  }
  public Joueur joueurNeutre2(){
    return joueurNeutre2;
  }

  public List<Joueur> dansLOrdre() {
    return List.of(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
  }
}
