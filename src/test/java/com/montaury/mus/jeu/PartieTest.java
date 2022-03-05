package com.montaury.mus.jeu;

import com.montaury.mus.jeu.evenements.Evenements;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Mintza;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Paso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.unJoueurFaisantChoix;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PartieTest {
  Equipe equipe1 = new Equipe("equipe1");
  Equipe equipe2 = new Equipe("equipe2");
  @BeforeEach
  void setUp() {
    partie = new Partie(mock(Evenements.class));
  }

  @Test
  void devrait_faire_gagner_le_premier_joueur_a_3_manches() {
    var opposants = new Opposants(
            /*unJoueurFaisantChoix(new Mintza(), new Hordago(), new Mintza(), new Hordago(), new Mintza(), new Hordago(),, new Mintza(), new Hordago(), new Mintza(), new Hordago()),
            unJoueurFaisantChoix(new Kanta(), new Mus(), new Paso(), new Kanta(), new Kanta(),, new Mus(), new Paso(), new Kanta(),, new Kanta()),
            */
             /*unJoueurFaisantChoix(  ),
            unJoueurFaisantChoix(  , new Mus(), new Paso(), new Kanta(),, new Kanta()),
            */

     //       unJoueurFaisantChoix(equipe1,new Mintza(), new Hordago(),new Mintza(), new Hordago(),new Mintza(), new Hordago()),
      //      unJoueurFaisantChoix(equipe2,new Kanta(),new Mus(), new Paso(), new Kanta(),new Kanta()),
     //       unJoueurFaisantChoix(equipe1,new Mintza(), new Hordago(),new Mintza(), new Hordago()),
     //       unJoueurFaisantChoix(equipe2,new Mus(), new Paso(), new Kanta(),new Kanta())

            unJoueurFaisantChoix(equipe1,new Mintza(), new Hordago(),new Kanta(),new Mintza(), new Hordago()),
            unJoueurFaisantChoix(equipe2,new Kanta(),new Mus(), new Paso(),new Kanta() ),
            unJoueurFaisantChoix(equipe1,new Mintza(), new Hordago(),new Mintza(), new Hordago()),
            unJoueurFaisantChoix(equipe2,new Kanta(),new Kanta(),new Mintza(), new Hordago())

    );

    Partie.Resultat resultat = partie.jouer(opposants);

    assertThat(resultat.vainqueur()).isNotNull();
    assertThat(resultat.score().resultatManches()).hasSizeGreaterThanOrEqualTo(3);
  }

  private Partie partie;
}

