package com.michel.lab.proxy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


import com.michel.lab.model.NoteAux;
import com.michel.lab.model.DemandeAux;
import com.michel.lab.model.DomaineAux;
import com.michel.lab.model.EchantillonAux;
import com.michel.lab.model.FormProcedure;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.FormSequence;
import com.michel.lab.model.Groupe;
import com.michel.lab.model.GroupeRapport;
import com.michel.lab.model.Login;
import com.michel.lab.model.ProcedureAux;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.RapportAux;
import com.michel.lab.model.SequenceAux;
import com.michel.lab.model.Upload;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.model.UtilisateurAux;
import com.michel.lab.model.EssaiAux;
import com.michel.lab.model.FormDemande;
import com.michel.lab.model.FormEchantillon;
import com.michel.lab.model.FormEssai;
import com.michel.lab.model.FormInitRapport;
import com.michel.lab.model.FormNote;

import feign.Body;
import feign.Headers;

@FeignClient(name = "lab-service", url = "localhost:8081/lab-service")
public interface MicroServiceLab {
	/*
	 * @GetMapping("/ouvrage/liste") List<OuvrageAux>
	 * tousLesOuvrages(@RequestHeader("Authorization") String token);
	 * 
	 * @GetMapping("/ouvrage/{id}") ResponseEntity<?> unOuvrage(@PathVariable("id")
	 * Integer id, @RequestHeader("Authorization") String token);
	 * 
	 * @GetMapping("/ouvrage/rubriques") public List<String>
	 * toutesLesRubriques(@RequestHeader("Authorization") String token);
	 * 
	 * @GetMapping("/exemplaire/disponibles") public List<Exemplaire>
	 * ListerExemplairesDisponibles(@RequestHeader("Authorization") String token);
	 * 
	 * @GetMapping("/exemplaire/disponibles/{id}") public List<Exemplaire>
	 * ListerExemplairesDisponiblesParOuvrage(@PathVariable("id") Integer
	 * id, @RequestHeader("Authorization") String token);
	 * 
	 * @GetMapping("/ouvrage/liste/rubrique/{rubrique}") public List<OuvrageAux>
	 * tousLesOuvragesParRubrique(@PathVariable String
	 * rubrique, @RequestHeader("Authorization") String token);
	 * 
	 * @PutMapping("/emprunts/save") void enregistrerEmprunt(EmpruntAux
	 * empruntAux, @RequestHeader("Authorization") String token);
	 * 
	 * @GetMapping("/ouvrage/emprunts/actifs/{id}") public List<LigneEmprunt>
	 * empruntsActifs(@PathVariable Integer id, @RequestHeader("Authorization")
	 * String token);
	 * 
	 * @GetMapping("/ouvrage/emprunts/hist/{id}") public List<LigneEmprunt>
	 * empruntsHist(@PathVariable Integer id, @RequestHeader("Authorization") String
	 * token);
	 * 
	 * @GetMapping("/prolonger/{id}") void prolonger(@PathVariable Integer id);
	 */

	@PutMapping("/modifier/compte/{id}")
	public void modifierCompte(@PathVariable Integer id, @RequestHeader("Authorization") String token,
			@RequestBody UtilisateurAux utilisateurAux);

	@PostMapping("connexion/")
	public ResponseEntity<UtilisateurAux> generate(@RequestBody final Login login);

	@PostMapping("compte/")
	public void creerCompte(UtilisateurAux user);

	@PostMapping("/save/qualification") // Enregistrement d'une qualification
	public void saveQualification(FormQualif formQualif);

	@PostMapping("/save/procedure") // Enregistrement d'une procédure
	public void saveProcedure(FormProcedure formProcedure);

	@GetMapping("/private/domaines") // récupération de la liste des domaines
	public List<String> tousLesDomaines();

	@GetMapping("/private/qualifications") // récupération de la liste de toutes les qualifications
	public List<QualificationAux> toutesLesQualifications();

	@GetMapping("/private/historique/{id}") // récupération de la liste de toutes les qualifications par utilisateur
	public List<QualificationAux> mesQualifications(@PathVariable(name = "id") Integer id);

	@GetMapping("/private/qualifications/{id}") // récupération de la liste de toutes les qualifications en cours par
												// utilisateur
	public List<QualificationAux> mesQualificationsEnCours(@PathVariable(name = "id") Integer id);

	@GetMapping("/private/qualification/{id}") // récupération de la liste de toutes les qualifications en cours par
												// utilisateur
	public QualificationAux obtenirQualification(@PathVariable(name = "id") Integer id);

	@GetMapping("/private/procedures") // Récupérer la liste des procédures
	public List<ProcedureAux> obtenirProcedures();

	@GetMapping("/private/liste/domaines") // Récupérer la liste des domaines
	public List<DomaineAux> obtenirDomaines();

	@GetMapping("/private/liste/domaine/{id}") // Récupérer une liste de procédure pour un domaine
												// id = identifiant du domaine concerné
	public List<ProcedureAux> obtenirProceduresParDomaine(@PathVariable(name = "id") Integer id);

	@PostMapping("/essai/ajouter/procedure/{id}/{qualification}/{idUser}")
	public void ajouterProcedure(@PathVariable(name = "id") Integer id // id = identifiant procedure
			, @PathVariable(name = "qualification") Integer qualification // qualification = numéro de qualification
			, @PathVariable(name = "idUser") Integer idUser); // utilisateur = identifiant utilisateur

	// Récuperer l'ensemble des procédure sélectionnées pour une qualification
	@PostMapping("/private/liste/procedure/selection") // donnée et un domaine donné
	public List<Integer> obtenirSelectionProcedure(Groupe groupe); // Qualification = numéro de la qualification

	@GetMapping("/private/liste/essais/{id}")
	public List<EssaiAux> obtenirEssaisParQualification(@PathVariable(name = "id") Integer id);

	@PostMapping("private/echantillon/save") // Enregistrement d'une procédure
	public void saveEchantillon(FormEchantillon formEchantillon);

	@GetMapping("/private/echantillon/voir/{id}")
	public List<EchantillonAux> obtenirEchantillonsParQualification(@PathVariable(name = "id") Integer id);

	@GetMapping("/private/echantillon/desactiver/{id}/{qualification}")
	public void desactiverEchantillon(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification);

	@GetMapping("/private/echantillon/voir")
	public List<EchantillonAux> obtenirEchantillonsParQualification2(@RequestParam(name = "id") Integer id);

	@GetMapping("/private/echantillon/activer/{id}/{qualification}")
	public void activerEchantillon(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification);

	// @GetMapping("/private/sequences/voir/{id}/{num}/{domaine}")
	@GetMapping("/private/sequences/voir/{id}/{num}")
	public List<SequenceAux> obtenirSequencesParEssai(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "num") Integer num);
	// @PathVariable(name="domaine") String domaine);

	@GetMapping("/private/essai/{num}")
	public EssaiAux obtenirEssaiParNumero(@PathVariable(name = "num") Integer num);

	@GetMapping("/private/qualification/numero/{id}")
	public QualificationAux obtenirQualificationParNumero(@PathVariable(name = "id") Integer id);

	@PostMapping("private/sequence/save")
	public void enregistrerSequence(FormSequence formSequence);

	@GetMapping("private/sequence/{id}")
	public SequenceAux obtenirSequenceParId(@PathVariable(name = "id") Integer id);

	@PostMapping("private/sequence/modifier")
	public void modifierSequence(FormSequence formSequence);

	@PostMapping("private/echantillon/ajouter/{echantillon}/{qualification}/{sequence}")
	public void ajouterEchantillon(
			@PathVariable(name = "echantillon") Integer idEchantillon,
			@PathVariable(name = "qualification") Integer numQualification,
			@PathVariable(name = "sequence") Integer idSequence
	);
	
	@PostMapping("private/echantillon/retirer/{echantillon}/{qualification}/{sequence}")
	public void retirerEchantillon(@PathVariable(name = "echantillon") Integer idEchantillon,
			@PathVariable(name = "qualification") Integer numQualification,
			@PathVariable(name = "sequence") Integer idSequence
	);
	
	@GetMapping("/private/echantillon/sequence/selection/{qualification}/{sequence}")
	public List<EchantillonAux> obtenirEchantillonSelectionParSequence(
			@PathVariable(name = "qualification") Integer num,
			@PathVariable(name = "sequence") Integer idSequence);
	
	@GetMapping("/private/echantillon/modifier/{id}/{qualification}")
	public EchantillonAux obtenirEchantillon(
			@PathVariable(name = "id") Integer id);
	
	@PostMapping("/private/echantillon/modifier")
	public void modifierEchantillon(FormEchantillon formEchantillon);
	
	@GetMapping("private/qualification/modifier/statut/{id}")
	public void modifierStatutQualification(@PathVariable(name = "id") Integer numQualification);
	
	@GetMapping("private/qualification/modifier/resultat/{id}")
	public void modifierResultatQualification(@PathVariable(name = "id") Integer numQualification);
	
	@PostMapping("/private/qualification/modifier")
	public void modifierQualification(
			FormQualif formQualif);
	
	@PostMapping("/private/sequence/supprimer/{id}")
	public void supprimerSequence(@PathVariable(name = "id") Integer idSequence);

	
	@PostMapping("/private/essai/modifier")
	public void modifierEssai(FormEssai formEssai);
	
	
	@PostMapping("/private/rapport/enregistrer")   // ajout en version pour test!
	public Integer enregistrerInitRapport(FormInitRapport formInitRapport);
	
	@GetMapping("/private/rapport/liste/{num}")
	public List<RapportAux> obtenirRapportsParQualification(@PathVariable(name = "num") Integer numQualification);
	
	@GetMapping("/private/rapport/{id}")
	public RapportAux obtenirRapportsParId(@PathVariable(name = "id") Integer idRapport);
	
	@GetMapping("/private/qualification/id/{id}") 
	public List<EssaiAux> obtenirEssaisParQualificationId(@PathVariable(name = "id") Integer qualification);
	
	@GetMapping("/private/echantillons/qualification/id/{qualification}")
	public List<EchantillonAux> obtenirEchantillonParIdQualification(@PathVariable(name = "qualification") Integer qualification);
	
	@PostMapping("/private/rapport/data/enregistrer")
	public void enregistrerDataRapport(GroupeRapport groupeRapport);
	
	@GetMapping("/private/rapport/supprimer/{id}")
	public void supprimerRapportsParId(@PathVariable("id") Integer idRapport);
	
	@GetMapping("/private/rapport/echantillons/{id}")
	public List<EchantillonAux> obtenirEchantillonsParRapportId(@PathVariable(name = "id")Integer idRapport);
	
	@GetMapping("/private/rapport/essais/{id}")
	public List<EssaiAux> obtenirEssaisParRapportId(@PathVariable(name = "id")Integer idRapport);
	
	@GetMapping("/private/note/liste/{id}")
	public List<NoteAux> obtenirListeNotesParQualification(@PathVariable(name = "id") Integer numQualification);
	
	@PostMapping("/private/note/enregistrer")
	public void ajouterNote(FormNote formNote);
	
	@GetMapping("/private/note/voir/{id}")
	public NoteAux obtenirNote(@PathVariable(name = "id") Integer idNote);
	
	@GetMapping("/private/note/supprimer/{id}")
	public void supprimerNote(@PathVariable(name = "id") Integer idNote);
	
	@PostMapping("/private/note/modifier")
	public void modifierNote(FormNote formNote);
	
	@GetMapping("/private/procedure/liste/domaine/{domaine}")
	public List<ProcedureAux> obtenirProceduresParDomaine(@PathVariable(name = "domaine") String domaine);
	
	@GetMapping("/private/procedure/obtenir/{id}")
	public ProcedureAux obtenirUneProcedure(@PathVariable(name = "id") Integer id);
	
	@PostMapping("/private/procedure/modifier")
	public void modifierProcedure(FormProcedure formProcedure);
	
	@PostMapping("/private/demande/enregistrer")
	public void enregistrerDemande(FormDemande formDemande);
	
	@GetMapping("/private/demande/liste")
	public List<DemandeAux> listeDemandes();
	
	@GetMapping("/private/demande/voir/{id}")
	public DemandeAux voirDemande(@PathVariable(name = "id") Integer id);
	
	@GetMapping("/private/demande/supprimer/{id}")
	public void supprimerDemande(@PathVariable(name = "id") Integer id);
	
	
	
	
	
	
	
	
	
	
	
}
