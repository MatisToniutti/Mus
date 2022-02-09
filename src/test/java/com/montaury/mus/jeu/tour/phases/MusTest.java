package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.console.AffichageEvenements;
import com.montaury.mus.jeu.joueur.InterfaceJoueur;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.choix.Mintza;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.carte.Fixtures.paquetEntierCroissant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.montaury.mus.jeu.joueur.Equipe;

class MusTest {

  @BeforeEach
  void setUp() {

    var equipe1 =new Equipe("E1");
    var equipe2 =new Equipe("E2");
    defausse = new Defausse();
    mus = new Mus(paquetEntierCroissant(), defausse, new AffichageEvenements(joueurEsku));
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    interfaceJoueurNeutre1 = mock(InterfaceJoueur.class);
    interfaceJoueurNeutre2 = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku,equipe1);
    joueurZaku = new Joueur("J4", interfaceJoueurZaku,equipe2);
    joueurNeutre1 = new Joueur("J2", interfaceJoueurNeutre1,equipe1);
    joueurNeutre2 = new Joueur("J3", interfaceJoueurNeutre2,equipe2);
    opposants = new Opposants(joueurEsku,joueurNeutre1,joueurNeutre2, joueurZaku);
  }

  @Test
  void devrait_distribuer_quatre_cartes_a_chaque_joueur() {
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new Mintza());

    mus.jouer(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_COUPE, Carte.AS_EPEE, Carte.AS_PIECE);
    assertThat(joueurNeutre1.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_COUPE, Carte.DEUX_EPEE, Carte.DEUX_PIECE);
    assertThat(joueurNeutre2.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_COUPE, Carte.TROIS_EPEE, Carte.TROIS_PIECE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_COUPE, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE);
  }

  @Test
  void devrait_se_terminer_si_le_joueur_esku_veut_sortir() {
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new Mintza());

    mus.jouer(opposants);

    verify(interfaceJoueurZaku, times(0)).faireChoixParmi(anyList());
  }

  @Test
  void devrait_se_terminer_si_le_joueur_zaku_veut_sortir() {
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurZaku.faireChoixParmi(anyList())).thenReturn(new Mintza());
    when(interfaceJoueurNeutre1.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurNeutre2.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());


    mus.jouer(opposants);

    verify(interfaceJoueurEsku, times(0)).cartesAJeter();
  }

  @Test
  void devrait_demander_les_cartes_a_jeter_aux_joueurs_s_ils_vont_mus() {
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus(), new Mintza());
    when(interfaceJoueurZaku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurNeutre1.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurNeutre2.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    mus.jouer(opposants);

    verify(interfaceJoueurEsku, times(1)).cartesAJeter();
    verify(interfaceJoueurZaku, times(1)).cartesAJeter();
    verify(interfaceJoueurNeutre2, times(1)).cartesAJeter();
    verify(interfaceJoueurNeutre1, times(1)).cartesAJeter();
  }

  @Test
  void devrait_defausser_les_cartes_a_jeter_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus(), new Mintza());
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));



    mus.jouer(opposants);

    assertThat(defausse.reprendreCartes()).containsExactly(Carte.AS_COUPE, Carte.DEUX_COUPE);
  }

  @Test
  void devrait_distribuer_des_cartes_pour_remplacer_les_cartes_jetees_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus(), new Mintza());
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));
    when(interfaceJoueurZaku.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));
    when(interfaceJoueurNeutre1.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurNeutre1.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));
    when(interfaceJoueurNeutre2.faireChoixParmi(anyList())).thenReturn(new com.montaury.mus.jeu.tour.phases.dialogue.choix.Mus());
    when(interfaceJoueurNeutre2.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));


    mus.jouer(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_EPEE, Carte.AS_PIECE, Carte.CINQ_BATON);
    assertThat(joueurNeutre1.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_EPEE, Carte.DEUX_PIECE, Carte.CINQ_COUPE);
    assertThat(joueurNeutre2.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_EPEE, Carte.TROIS_PIECE, Carte.CINQ_EPEE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE, Carte.CINQ_PIECE);

  }

  private Mus mus;
  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurZaku;
  private InterfaceJoueur interfaceJoueurNeutre1;
  private InterfaceJoueur interfaceJoueurNeutre2;

  private Joueur joueurEsku;
  private Joueur joueurZaku;
  private Joueur joueurNeutre1;
  private Joueur joueurNeutre2;

  private Opposants opposants;
  private Defausse defausse;
}
