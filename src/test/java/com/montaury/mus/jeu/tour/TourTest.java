package com.montaury.mus.jeu.tour;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.Opposants;
import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.evenements.Evenements;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Gehiago;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Idoki;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Imido;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Mintza;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Paso;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Tira;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.carte.Fixtures.paquetAvec;
import static com.montaury.mus.jeu.carte.Fixtures.paquetEntierCroissant;
import static com.montaury.mus.jeu.joueur.Fixtures.unJoueurFaisantChoix;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TourTest {

  Equipe equipe1 = new Equipe("equipe1");
  Equipe equipe2 = new Equipe("equipe2");

  @BeforeEach
  void setUp() {

    evenementsDeJeu = mock(Evenements.class);
    tour = new Tour(evenementsDeJeu, paquetEntierCroissant(), new Defausse());
  }

  @Test
  void devrait_donner_tous_les_points_au_joueur_esku_si_le_joueur_zaku_fait_tira_un_contre_un() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Imido(), new Imido(), new Imido(), new Imido());
    var joueurZaku = unJoueurFaisantChoix(new Tira());
    var opposants = new Opposants(joueurEsku, joueurZaku);
    var score = new Manche.Score(opposants);
    opposants.setNbJoueur(2);
    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 8);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 0);
  }

  @Test
  void devrait_donner_tous_les_points_a_equipe1_si_le_joueur2_fait_tira() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Imido(), new Imido(), new Imido(), new Imido());
    var joueurZaku = unJoueurFaisantChoix(new Tira());
    var joueurNeutre1 = unJoueurFaisantChoix(new Tira());
    var joueurNeutre2 = unJoueurFaisantChoix(new Tira());
    var opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
    var score = new Manche.Score(opposants);

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 8);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 0);
  }

  @Test
  void devrait_repartir_les_points_si_tout_est_paso_un_contre_un() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Paso(), new Paso(), new Paso(), new Paso());
    var joueurZaku = unJoueurFaisantChoix(new Paso());
    var opposants = new Opposants(joueurEsku, joueurZaku);
    var score = new Manche.Score(opposants);
    opposants.setNbJoueur(2);

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 1);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 5);
  }

  @Test
  void devrait_repartir_les_points_si_tout_est_paso_deux_contre_deux() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Paso(), new Paso(), new Paso(), new Paso());
    var joueurZaku = unJoueurFaisantChoix(new Paso());
    var joueurNeutre1 = unJoueurFaisantChoix(new Paso());
    var joueurNeutre2 = unJoueurFaisantChoix(new Paso());
    var opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
    var score = new Manche.Score(opposants);

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 1);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 5);
  }

  @Test
  void devrait_faire_gagner_le_joueur_zaku_si_hordago_au_grand_un_contre_un() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Hordago());
    var joueurZaku = unJoueurFaisantChoix(new Kanta());
    var opposants = new Opposants(joueurEsku, joueurZaku);
    var score = new Manche.Score(opposants);
    opposants.setNbJoueur(2);
    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).contains(joueurZaku.equipe);
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 0);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 40);
  }

  @Test
  void devrait_faire_gagner_le_joueur_zaku_si_hordago_au_grand_deux_contre_deux() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Hordago());
    var joueurZaku = unJoueurFaisantChoix(new Kanta());
    var joueurNeutre1 = unJoueurFaisantChoix(new Kanta());
    var joueurNeutre2 = unJoueurFaisantChoix(new Kanta());
    var opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
    var score = new Manche.Score(opposants);

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).contains(joueurZaku.equipe);
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 0);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 40);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_idoki_un_contre_un() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Imido(), new Imido(), new Imido(), new Imido());
    var joueurZaku = unJoueurFaisantChoix(new Idoki());
    var opposants = new Opposants(joueurEsku, joueurZaku);
    var score = new Manche.Score(opposants);
    opposants.setNbJoueur(2);
    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 2);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 10);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_idoki_deux_contre_deux() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Imido(), new Imido(), new Imido(), new Imido());
    var joueurZaku = unJoueurFaisantChoix(new Idoki());
    var joueurNeutre1 = unJoueurFaisantChoix(new Idoki());
    var joueurNeutre2 = unJoueurFaisantChoix(new Idoki());
    var opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
    var score = new Manche.Score(opposants);

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 2);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 10);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_gehiago_puis_idoki_un_contre_un() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Imido(), new Idoki(), new Imido(), new Idoki(), new Imido(), new Idoki(), new Imido(), new Idoki());
    var joueurZaku = unJoueurFaisantChoix(new Gehiago(2));
    var opposants = new Opposants(joueurEsku, joueurZaku);
    var score = new Manche.Score(opposants);
    opposants.setNbJoueur(2);
    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 4);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 16);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_gehiago_puis_idoki_deux_contre_deux() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Imido(), new Idoki(), new Imido(), new Idoki(), new Imido(), new Idoki(), new Imido(), new Idoki());
    var joueurZaku = unJoueurFaisantChoix(new Gehiago(2));
    var joueurNeutre1 = unJoueurFaisantChoix(new Gehiago(2));
    var joueurNeutre2 = unJoueurFaisantChoix(new Gehiago(2));
    var opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
    var score = new Manche.Score(opposants);

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 8);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 28);
  }

  @Test
  void devrait_privilegier_le_joueur_esku_si_les_mains_sont_identiques_un_contre_un() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Imido(), new Imido(), new Imido(), new Imido());
    var joueurZaku = unJoueurFaisantChoix(new Idoki());
    var opposants = new Opposants(joueurEsku, joueurZaku);
    var score = new Manche.Score(opposants);

    Tour tour = new Tour(evenementsDeJeu, paquetAvec(Carte.AS_BATON, Carte.DEUX_BATON, Carte.TROIS_BATON, Carte.QUATRE_BATON, Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE), new Defausse());
    opposants.setNbJoueur(2);

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 7);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 0);
  }

  @Test
  void devrait_privilegier_le_joueur_esku_si_les_mains_sont_identiques_deux_contre_deux() {
    var joueurEsku = unJoueurFaisantChoix(equipe1,new Mintza(), new Imido(), new Imido(), new Imido(), new Imido());
    var joueurZaku = unJoueurFaisantChoix(equipe2,new Idoki());
    var joueurNeutre1 = unJoueurFaisantChoix(equipe1,new Idoki());
    var joueurNeutre2 = unJoueurFaisantChoix(equipe2,new Idoki());
    var opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
    var score = new Manche.Score(opposants);

    Tour tour = new Tour(evenementsDeJeu, paquetAvec(Carte.AS_BATON, Carte.DEUX_BATON, Carte.TROIS_BATON, Carte.QUATRE_BATON, Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE, Carte.AS_EPEE, Carte.DEUX_EPEE, Carte.TROIS_EPEE, Carte.QUATRE_EPEE, Carte.AS_PIECE, Carte.DEUX_PIECE, Carte.TROIS_PIECE, Carte.QUATRE_PIECE), new Defausse());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 7);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 0);
  }

  @Test
  void devrait_attribuer_les_bonus_au_joueur_ayant_la_meilleure_main_pour_chaque_phase_un_contre_un() {
    var joueurEsku = unJoueurFaisantChoix(new Mintza(), new Paso(), new Paso());
    var joueurZaku = unJoueurFaisantChoix(new Paso(), new Paso());
    var opposants = new Opposants(joueurEsku, joueurZaku);
    var score = new Manche.Score(opposants);

    Tour tour = new Tour(evenementsDeJeu, paquetAvec(Carte.ROI_BATON, Carte.ROI_COUPE, Carte.VALET_BATON, Carte.AS_EPEE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE, Carte.CINQ_COUPE), new Defausse());
    opposants.setNbJoueur(2);
    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 6);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 0);
  }

  @Test
  void devrait_attribuer_les_bonus_au_joueur_ayant_la_meilleure_main_pour_chaque_phase_deux_contre_deux() {
    var joueurEsku = unJoueurFaisantChoix(equipe1,new Mintza(), new Paso(), new Paso(),new Paso(),new Paso());
    var joueurZaku = unJoueurFaisantChoix(equipe2,new Paso());
    var joueurNeutre1 = unJoueurFaisantChoix(equipe2,new Paso());
    var joueurNeutre2 = unJoueurFaisantChoix(equipe1,new Paso());
    var opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
    var score = new Manche.Score(opposants);

    Tour tour = new Tour(evenementsDeJeu, paquetAvec(Carte.ROI_BATON, Carte.ROI_COUPE, Carte.VALET_BATON, Carte.AS_EPEE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE, Carte.CINQ_COUPE,Carte.ROI_EPEE, Carte.ROI_PIECE, Carte.VALET_PIECE, Carte.AS_PIECE, Carte.DEUX_PIECE, Carte.TROIS_PIECE, Carte.QUATRE_PIECE, Carte.CINQ_PIECE), new Defausse());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParJoueur()).containsEntry(joueurEsku.equipe, 6);
    assertThat(score.scoreParJoueur()).containsEntry(joueurZaku.equipe, 0);
  }

  private Evenements evenementsDeJeu;
  private Tour tour;
}
