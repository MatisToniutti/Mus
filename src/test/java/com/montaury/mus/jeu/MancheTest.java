package com.montaury.mus.jeu;

import com.montaury.mus.jeu.evenements.Evenements;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.unJoueurFaisantChoix;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.montaury.mus.jeu.joueur.Equipe;

class MancheTest {
  Equipe equipe1 = new Equipe("equipe1");
  Equipe equipe2 = new Equipe("equipe2");

  @BeforeEach
  void setUp() {
    manche = new Manche(mock(Evenements.class));
  }

  @Test

  void devrait_terminer_la_manche_si_hordago_au_grand() {

    var joueurEsku = unJoueurFaisantChoix(equipe1,new Mintza(), new Hordago(),new Kanta());
    var joueurZaku = unJoueurFaisantChoix(equipe2,new Kanta(),new Mintza(), new Hordago());
    var joueurNeutre1 = unJoueurFaisantChoix(equipe1,new Kanta(),new Mintza(), new Hordago());
    var joueurNeutre2 = unJoueurFaisantChoix(equipe2,new Kanta(),new Mintza(), new Hordago());

    var resultat = manche.jouer(new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2,joueurZaku));

    assertThat(resultat.vainqueur()).isNotNull();
    assertThat(resultat.pointsVaincu()).isZero();
  }

  @Test
  void devrait_terminer_la_manche_si_un_joueur_a_40_points() {
    var joueurEsku = unJoueurFaisantChoix(equipe1,new Mintza(), new Imido(), new Gehiago(2));
    var joueurZaku = unJoueurFaisantChoix(equipe2,new Gehiago(40), new Tira());
    var joueurNeutre1 = unJoueurFaisantChoix(equipe1,new Gehiago(40), new Tira());
    var joueurNeutre2 = unJoueurFaisantChoix(equipe2,new Gehiago(40), new Tira());

    var resultat = manche.jouer(new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku));

    assertThat(resultat.vainqueur()).isEqualTo(joueurEsku.equipe);
    assertThat(resultat.pointsVaincu()).isZero();
  }

  @Test
  void devrait_changer_l_ordre_des_opposants_a_la_fin_du_tour() {
    var joueurEsku = unJoueurFaisantChoix(equipe1,new Mintza(), new Hordago());
    var joueurZaku = unJoueurFaisantChoix(equipe2);
    var joueurNeutre1 = unJoueurFaisantChoix(equipe1,new Kanta());
    var joueurNeutre2 = unJoueurFaisantChoix(equipe2);

    var opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);

    manche.jouer(opposants);

    assertThat(opposants.dansLOrdre()).containsExactly(joueurNeutre1,joueurNeutre2,joueurZaku,joueurEsku);
  }

  private Manche manche;
}
