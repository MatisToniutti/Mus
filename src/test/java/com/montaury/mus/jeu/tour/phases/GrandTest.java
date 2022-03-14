package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.joueur.Main;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.main;
import static org.assertj.core.api.Assertions.assertThat;

class GrandTest {

  @Test
  void devrait_faire_gagner_le_joueur1_s_il_a_la_plus_grande_carte() {
    Main mainJoueur1 = main(Carte.AS_BATON, Carte.CINQ_PIECE, Carte.CAVALIER_BATON, Carte.SIX_COUPE);
    Main mainJoueur2 = main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.SEPT_BATON, Carte.AS_PIECE);

    boolean main1EstMeilleure = grand.mainEskuEstMeilleure(mainJoueur1, mainJoueur2);

    assertThat(main1EstMeilleure).isTrue();
  }
  @Test
  void devrait_faire_gagner_le_joueur2_s_il_a_la_plus_grande_carte() {
    Main mainJoueur1 = main(Carte.AS_BATON, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.SIX_COUPE);
    Main mainJoueur2 = main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.AS_PIECE);

    boolean main1EstMeilleure = grand.mainEskuEstMeilleure(mainJoueur1, mainJoueur2);

    assertThat(main1EstMeilleure).isFalse();
  }

  @Test
  void devrait_faire_gagner_le_joueur_qui_a_la_seconde_plus_grande_carte_si_la_premiere_est_egale() {
    Main mainJoueur1 = main(Carte.AS_BATON, Carte.CINQ_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE);
    Main mainJoueur2 = main(Carte.DEUX_BATON, Carte.VALET_PIECE, Carte.QUATRE_BATON, Carte.SEPT_PIECE);

    boolean main1EstMeilleure = grand.mainEskuEstMeilleure(mainJoueur1, mainJoueur2);

    assertThat(main1EstMeilleure).isFalse();
  }

  @Test
  void devrait_faire_gagner_le_joueur1_si_les_deux_mains_sont_egales() {
    Main mainJoueur1 = main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE);
    Main mainJoueur2 = main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.QUATRE_BATON, Carte.AS_PIECE);

    boolean main1EstMeilleure = grand.mainEskuEstMeilleure(mainJoueur1, mainJoueur2);

    assertThat(main1EstMeilleure).isTrue();
  }

  private final Grand grand = new Grand();
}