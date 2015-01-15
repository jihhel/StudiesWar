package fr.iutinfo;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import fr.iutinfo.studiesWar.App;
import fr.iutinfo.studiesWar.models.Partie;
import fr.iutinfo.studiesWar.resources.ObjetTransfert;
import fr.iutinfo.studiesWar.resources.PartieRessource;


public class TestPartieRest extends JerseyTest {
	
	@Override
    protected Application configure() {
        return new App();
    }
	
	@Test 
	public void testCreerPartie() {
		String name = "Clavier";
		ObjetTransfert obj = target("/partie/"+name+"/creer").request().get(ObjetTransfert.class);
		
		assertTrue(obj != null);
		assertTrue(PartieRessource.parties.containsKey(obj.getIdPartie()));
		assertTrue(PartieRessource.joueurs.containsKey(obj.getIdJoueur()));
	}
	
	@Test
	public void testLancerPartie() {
		String name = "Clavier";
		ObjetTransfert obj = target("/partie/"+name+"/creer").request().get(ObjetTransfert.class);
		
		target("partie/" + obj.getIdPartie() + "/lancer").request().get();
		Partie p = PartieRessource.parties.get(obj.getIdPartie());
		assertTrue(p.getNumTour() > 0);
		assertEquals(Partie.NB_CONTROLES, p.getSemaineActuelle().size());
		assertEquals(Partie.NB_JOUEURS, p.getPersonnes().size());
	}
	
	@Test
	public void testObtenirControles() {
		String name = "Clavier";
		ObjetTransfert obj = target("/partie/"+name+"/creer").request().get(ObjetTransfert.class);
		
		ObjetTransfert obj2 = target("/partie/" + obj.getIdPartie() + "/controles").request().get(ObjetTransfert.class);
		
		assertTrue(obj2.getControles() != null);
		assertTrue(obj2.getControles().size() > 0);
		
	}
	@Test 
	public void testObtenirActions() {
		String name = "Clavier";
		//Creation de la partie
		ObjetTransfert obj = target("/partie/"+name+"/creer").request().get(ObjetTransfert.class);
		
		//Lancer la partie
		target("partie/" + obj.getIdPartie() + "/lancer").request().get();
		//Génération des contrôles
		target("/partie/" + obj.getIdPartie() + "/controles").request().get(ObjetTransfert.class);

		int idxDoNothing = 5 - Partie.NB_CONTROLES;
		for (int i = 1; i <= 5; i++) {
			//Obtention des actions pour un jour donné
			ObjetTransfert obj3 = target("/partie/"+obj.getIdPartie()+"/joueur/"+obj.getIdJoueur()
					+"/jour/" + i).request().get(ObjetTransfert.class);
			
			System.out.println(obj3.getActions());
			assertTrue(obj3 != null);
			assertTrue(obj3.getActions().size() > 0);
			if (obj3.getActions().size() == 1)
				idxDoNothing--;
			
			assertNotEquals(-1, idxDoNothing);
		}
	}
	
	@Test
	public void testObtenirCaracs() {
		String name = "Clavier";
		ObjetTransfert obj = target("/partie/"+name+"/creer").request().get(ObjetTransfert.class);
		
		ObjetTransfert obj2 = target("/partie/" + obj.getIdJoueur() + "/caracteristiques").request().get(ObjetTransfert.class);
		
		assertTrue(obj2 != null);
		assertTrue(obj2.getCaracs().size() > 0);
	}
	

}
