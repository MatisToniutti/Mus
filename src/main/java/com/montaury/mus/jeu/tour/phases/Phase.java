package com.montaury.mus.jeu.tour.phases;
import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.evenements.Evenements;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Main;
import com.montaury.mus.jeu.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.Dialogue;
import java.util.Optional;
import java.util.stream.Collectors;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.TypeChoix;
import java.util.ArrayList;
import java.util.Collections;
import static com.montaury.mus.jeu.tour.phases.dialogue.choix.TypeChoix.KANTA;
import static com.montaury.mus.jeu.tour.phases.dialogue.choix.TypeChoix.TIRA;
import static com.montaury.mus.jeu.tour.phases.dialogue.choix.TypeChoix.GEHIAGO;
import static com.montaury.mus.jeu.tour.phases.dialogue.choix.TypeChoix.HORDAGO;
import static com.montaury.mus.jeu.tour.phases.dialogue.choix.TypeChoix.PASO;

public abstract class Phase {
  private final String nom;

  protected Phase(String nom) {
    this.nom = nom;
  }

  public String nom() {
    return nom;
  }

  public final Resultat jouer(Evenements affichage, Opposants opposants) {
    affichage.nouvellePhase(this);
    var participants = participantsParmi(opposants);
    if (participants.aucun()) {
      return Phase.Resultat.nonJouable();
    }
    if (participants.estUnique()) {
      Joueur premier = participants.premier();
      return Phase.Resultat.termine(premier, 0, pointsBonus(premier));
    }
    var resultat = new Dialogue(affichage).derouler(participants);
    return conclure(resultat, participants,opposants.getNbJoueur());
  }

  private Resultat conclure(Dialogue.Recapitulatif dialogue, Participants participants,int nbJoueur) {
    if (nbJoueur!=4 && dialogue.terminePar(TIRA)) {
      var joueurEmportantLaMise = dialogue.dernierJoueurAyantMise();
      return Phase.Resultat.termine(joueurEmportantLaMise, dialogue.pointsEngages(), pointsBonus(joueurEmportantLaMise));
    }
    if (nbJoueur==4 && dialogue.terminePar(TIRA)){
      var joueurs = participants.dansLOrdre;
      var coequipier = joueurs.get(2);
      var choix = new ArrayList<TypeChoix>(Collections.singleton(TIRA));
      coequipier.interfaceJoueur.faireChoixParmi(choix);
/*
      Evenements affichage = new Evenements() ;
      affichage.nouvellePhase(this);
      new Dialogue(affichage).deroulerSuiteTira(participants);

     /* if reponse = TIRA {conclure(TIRA,participants,2}

      derouler(suite);*/



      var joueurEmportantLaMise = dialogue.dernierJoueurAyantMise();
      return Phase.Resultat.termine(joueurEmportantLaMise, dialogue.pointsEngages(), pointsBonus(joueurEmportantLaMise));

    }

    var vainqueur = meilleurParmi(participants);
    if (dialogue.terminePar(KANTA)) {
      return Phase.Resultat.termine(vainqueur, Manche.Score.POINTS_POUR_TERMINER_MANCHE, 0);
    }
    var bonus = pointsBonus(vainqueur);
    return Phase.Resultat.termine(vainqueur, 0, bonus + (dialogue.terminePar(PASO) && bonus != 0 ? 0 : dialogue.pointsEngages()));
  }

  public Participants participantsParmi(Opposants opposants) {
    return new Participants(opposants.dansLOrdre().stream()
      .filter(joueur -> peutParticiper(joueur.main()))
      .collect(Collectors.toList()));
  }

  protected boolean peutParticiper(Main main) {
    return true;
  }

  private Joueur meilleurParmi(Participants participants) {
    Joueur meilleur = null;
    for (Joueur joueur : participants.dansLOrdre()) {
      meilleur = meilleur == null ? joueur : meilleurEntre(meilleur, joueur);
    }
    return meilleur;
  }

  private Joueur meilleurEntre(Joueur joueurEsku, Joueur joueurZaku) {
    return mainEskuEstMeilleure(joueurEsku.main(), joueurZaku.main()) ? joueurEsku : joueurZaku;
  }

  protected abstract boolean mainEskuEstMeilleure(Main mainJoueurEsku, Main mainJoueurZaku);

  protected int pointsBonus(Joueur vainqueur) {
    return 0;
  }

  public static class Resultat {
    public static Resultat nonJouable() {
      return new Resultat(null, 0, 0);
    }

    public static Resultat termine(Joueur vainqueur, int pointsImmediats, int pointsFinDuTour) {
      return new Resultat(vainqueur, pointsImmediats, pointsFinDuTour);
    }

    private final Joueur vainqueur;
    public final int pointsImmediats;
    public final int pointsFinDuTour;

    private Resultat(Joueur vainqueur, int pointsImmediats, int pointsFinDuTour) {
      this.pointsImmediats = pointsImmediats;
      this.vainqueur = vainqueur;
      this.pointsFinDuTour = pointsFinDuTour;
    }

    public Optional<Joueur> vainqueur() {
      return Optional.ofNullable(vainqueur);
    }
  }
}
