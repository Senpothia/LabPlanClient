package com.michel.lab.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.bouncycastle.crypto.engines.SM2Engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName.Form;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.michel.lab.constants.Constants;
import com.michel.lab.model.EchantillonAux;
import com.michel.lab.model.EssaiAux;
import com.michel.lab.model.FormEchantillon;
import com.michel.lab.model.FormEssai;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.FormSequence;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.SequenceAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private")
public class Private {

	@Autowired
	private MicroServiceLab microServiceLab;

	@Autowired
	private UserConnexion userConnexion;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:MM");

	@GetMapping("/qualification/creation") // Accès au formulaire de création d'un qualification
	public String creer(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formQualif", new FormQualif());
		return Constants.CREATION_QUALIFICATION;
	}

	@PostMapping("/qualification/creation") // Enregistrement des éléments de création d'une qualification
	public String enregistrerQualification(Model model, HttpSession session, FormQualif formQualif) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		Integer createurId = utilisateur.getId();
		formQualif.setCreateurId(createurId);
		microServiceLab.saveQualification(formQualif);
		return Constants.ESPACE_PERSONEL;
	}

	@GetMapping("/qualifications")
	public String qualifications(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.toutesLesQualifications();
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "1");
		return Constants.QUALIFICATIONS;
	}

	@GetMapping("/historique/{id}") // récupération de la liste de toutes les qualifications
									// id = identifiant de l'utilisateur
	public String mesQualifications(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.mesQualifications(id);
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "2");
		return Constants.QUALIFICATIONS;
	}

	@GetMapping("/qualifications/{id}") // Récupérer les qualification d'un utilisateur, identifiant : id
										// id = identifiant de l'utilisateur
	public String qualificationsEnCours(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		List<QualificationAux> qualifications = microServiceLab.mesQualificationsEnCours(id);
		model.addAttribute("qualifications", qualifications);
		model.addAttribute("access", "3");
		return Constants.QUALIFICATIONS;
	}

	@GetMapping("/qualification/{id}") // Consulter une qualification
										// id = identifiant de l'utilisateur
	public String qualification(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualification(id);
		model.addAttribute("qualification", qualification);
		model.addAttribute("modification", false);
		System.out.println("Référence qualification: " + qualification.getReference());
		return Constants.QUALIFICATION;
	}

	@GetMapping("/essai/voir/{id}")
	public String visualiserEssais(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		QualificationAux qualification = microServiceLab.obtenirQualification(id);

		List<EssaiAux> essais = microServiceLab.obtenirEssaisParQualification(id);

		model.addAttribute("qualification", qualification);
		model.addAttribute("essais", essais);
		return Constants.PAGE_ESSAIS;
	}

	@GetMapping("/sequences/{id}/{num}/{domaine}") // id = numéro qualification, num = id essai
	public String voirSequencesParEssais(@PathVariable(name = "id") Integer id, 
			@PathVariable(name = "num") Integer num,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		EssaiAux essai = microServiceLab.obtenirEssaiParNumero(num);
		model.addAttribute("essai", essai);
		
		List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(id, num);
		model.addAttribute("sequences", sequences);
		QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(id);
		model.addAttribute("qualification", qualification);

		if (sequences.isEmpty()) {

			model.addAttribute("vide", true);

		} else {

			model.addAttribute("vide", false);
		}

		return Constants.LISTE_SEQUENCES;
	}

	@GetMapping("/sequence/creer/{id}/{qualification}/{domaine}")
	public String creerSequence(@PathVariable(name = "id") Integer id, // id = identifiant essai
			@PathVariable(name = "qualification") Integer qualification, 
			@PathVariable(name = "domaine") String domaine,
			Model model, HttpSession session) {

		System.out.println("Get: creerSequence");
		System.out.println("Valeur id récupéré: " + id);
		System.out.println("Valeur qualification récupéré: " + qualification);
		System.out.println("Valeur domaine récupéré: " + domaine);

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		FormSequence formSequence = new FormSequence();
		formSequence.setEssai(id);
		formSequence.setQualification(qualification);
		formSequence.setNomDomaine(domaine);
		System.out.println(formSequence.toString());
		model.addAttribute("formSequence", formSequence);
		model.addAttribute("id", id);
		model.addAttribute("qualification", qualification);
		model.addAttribute("domaine", domaine);

		return Constants.CREATION_SEQUENCE;

	}

	@PostMapping("/sequence/creer/{id}/{qualification}")
	public String enregistrerSequence(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification,
			Model model, HttpSession session, FormSequence formSequence, RedirectAttributes redirectAttributes) {

		System.out.println("méthode POST enregistrement sequence");
		System.out.println(formSequence.toString());

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		System.out.println("Identifiant essai récupéré: " + id);
		System.out.println("Identifiant qualification récupéré: " + qualification);
		
		formSequence.setEssai(id);
		microServiceLab.enregistrerSequence(formSequence);

		redirectAttributes.addAttribute("id", qualification);
		redirectAttributes.addAttribute("num", id);
		
		return "redirect:/labplan/private/sequences";

	}

	@GetMapping("/sequences") // id = id essai
	public String voirSequencesParEssais2(@RequestParam(name = "id") Integer qualification,
			@RequestParam(name = "num") Integer id,
			
			Model model, HttpSession session) {

		System.out.println(" *** entrée méthode voirSequencesParEssais2 ");
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		EssaiAux essai = microServiceLab.obtenirEssaiParNumero(id);
		model.addAttribute("essai", essai);
		
		List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(qualification, id);
		model.addAttribute("sequences", sequences);
		QualificationAux qualif = microServiceLab.obtenirQualificationParNumero(qualification);
		model.addAttribute("qualification", qualif);

		if (sequences.isEmpty()) {

			model.addAttribute("vide", true);

		} else {

			model.addAttribute("vide", false);
		}

		return Constants.LISTE_SEQUENCES;
	}

	@GetMapping("/sequences/voir/{id}/{num}/{sequence}")
	public String voirSequence(@PathVariable(name = "id") Integer id, // id = numéro de qualification
			@PathVariable(name = "num") Integer num, // num = id de l'essai
			@PathVariable(name = "sequence") Integer idSequence, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		SequenceAux sequence = microServiceLab.obtenirSequenceParId(idSequence);
		
		Duration duration = Duration.between(sequence.getDebut(), sequence.getFin());
		System.out.println("durée: "  + duration.toHours() + " hours");
		long duree = duration.toHours(); 
		sequence.setDuree(duree);
		
		model.addAttribute("sequence", sequence);
		QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(id);
		model.addAttribute("qualification", qualification);
		EssaiAux essai = microServiceLab.obtenirEssaiParNumero(num);
		model.addAttribute("essai", essai);
		List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(id);
		List<EchantillonAux> echSelection = microServiceLab.obtenirEchantillonSelectionParSequence(id, idSequence);
		//model.addAttribute("echantillons", echantillons);
		model.addAttribute("echantillons", echSelection);


		return Constants.SEQUENCE;
	}

	@GetMapping("/sequence/modifier/{id}/{qualification}/{sequence}")
	public String modifierSequence(@PathVariable(name = "id") Integer idEssai, // id = id de l'essai
			@PathVariable(name = "qualification") Integer numQualif, // numéro de la qualification
			@PathVariable(name = "sequence") Integer idSequence, // id de la séquence
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		System.out.println("id essai reçu par url: " + idEssai);
		System.out.println("num qualification reçu par url: " + numQualif);
		System.out.println("id sequence reçu par url: " + idSequence);

		SequenceAux sequence = microServiceLab.obtenirSequenceParId(idSequence);
		FormSequence formSequence = new FormSequence();
		formSequence.setId(sequence.getId());
		formSequence.setNom(sequence.getNom());
		formSequence.setNiveau(sequence.getNiveau());
		formSequence.setDomaine(sequence.getDomaine());
		
		formSequence.setDebut(sequence.getDebut());
		
		
		//formSequence.setDebutText("2020-08-22");   // valeur fonctionnelle : "2020-08-22"
		//System.out.println("debutText = " + formSequence.getDebutText());
		
		String debutText = sequence.getDebutText();
		System.out.println("valeur debutText: " + debutText);
		
		String[] tokensDebut = debutText.split("-");

		for (String t : tokensDebut) {  
			
			System.out.println(t); 
			
		}
		
		String[] anneeDebut = tokensDebut[2].split(" ");
		System.out.println("année: " + anneeDebut[0]);
		String dateDebutText = anneeDebut[0] + "-" + tokensDebut[1] + "-" + tokensDebut[0];
		System.out.println("dateFinText: " + dateDebutText);
		
		formSequence.setDebutText(dateDebutText);
		System.out.println("debutText = " + formSequence.getDebutText());
		
		String finText = sequence.getFinText();
		System.out.println("valeur finText: " + finText);
		
		String[] tokensFin = finText.split("-");

		for (String t : tokensFin) {  
			
			System.out.println(t); 
			
		}
		
		String[] anneeFin = tokensFin[2].split(" ");
		System.out.println("année: " + anneeFin[0]);
		String dateFinText = anneeFin[0] + "-" + tokensFin[1] + "-" + tokensFin[0];
		System.out.println("dateFinText: " + dateFinText);
		
		formSequence.setFinText(dateFinText);
		System.out.println("finText = " + formSequence.getFinText());
		
		// transfert des heures 
		
	
		System.out.println("date de debut prépa heure: " + debutText);
		System.out.println("date de fin prépa heure: " + finText);
		
		String segmentDebut[] = debutText.split(" ");
		String segmentFin[] = finText.split(" ");
		
		String debutHeureText =  segmentDebut[1];
		String finHeureText =  segmentFin[1];
		
		formSequence.setDebutHeureText(debutHeureText);
		formSequence.setFinHeureText(finHeureText);
		
		formSequence.setProfil(sequence.getProfil());
		formSequence.setCommentaire(sequence.getCommentaire());
		formSequence.setActif(sequence.getActif());
		formSequence.setAvis(sequence.getAvis());
		formSequence.setEssai(idEssai);

		model.addAttribute("formSequence", formSequence);
		model.addAttribute("essai", idEssai);
		model.addAttribute("qualification", numQualif);
		model.addAttribute("sequence", idSequence);

		return Constants.MODIFIER_SEQUENCE;
	}

	@PostMapping("/sequence/modifier/{essai}/{qualification}/{sequence}")
	public String enregistrerModificationSequence(
			@PathVariable(name = "essai") Integer idEssai,
			@PathVariable(name = "qualification") Integer num, 
			@PathVariable(name = "sequence") Integer idSequence,
			FormSequence formSequence, Model model, HttpSession session, 
			RedirectAttributes redirectAttributes) {
		
		System.out.println("*******Entrée méthode enregistrerModificationSequence()");
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		formSequence.setEssai(idEssai);
		formSequence.setId(idSequence);
		
		String debText = formSequence.getDebutText();
		String finText = formSequence.getFinText();
		//LocalDateTime dateTime = LocalDateTime.parse(deb);
		
		System.out.println("Date reçue: " + debText);
		LocalDateTime debut = LocalDateTime.parse(debText,
				DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
		       // DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
		formSequence.setDebut(debut);
		
		LocalDateTime fin = LocalDateTime.parse(finText,
				DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
		       // DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
		formSequence.setFin(fin);
		//System.out.println("conversion date: " + dateTime);

		microServiceLab.modifierSequence(formSequence);

		redirectAttributes.addAttribute("essai", idEssai);
		redirectAttributes.addAttribute("qualification", num);
		redirectAttributes.addAttribute("sequence", idSequence);

		return "redirect:/labplan/private/sequences/voir/retour";
		
	}

	@GetMapping("/sequences/voir/retour")
	public String voirSequence2(
			@RequestParam(name = "essai") Integer idEssai, // num = id de l'essai
			@RequestParam(name = "qualification") Integer num, // id = numéro de qualification
			@RequestParam(name = "sequence") Integer idSequence, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		System.out.println("redirect:/sequences/voir/{essai}/{qualification}/{sequence}");
		System.out.println("id sequence recu: " + idSequence);
		
		SequenceAux sequence = microServiceLab.obtenirSequenceParId(idSequence);
		
		Duration duration = Duration.between(sequence.getDebut(), sequence.getFin());
		System.out.println("durée: "  + duration.toHours() + " hours");
		long duree = duration.toHours(); 
		sequence.setDuree(duree);
		
		model.addAttribute("sequence", sequence);
		QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(num);
		model.addAttribute("qualification", qualification);
		EssaiAux essai = microServiceLab.obtenirEssaiParNumero(idEssai);
		model.addAttribute("essai", essai);
		List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(num);
		List<EchantillonAux> echSelection = microServiceLab.obtenirEchantillonSelectionParSequence(num, idSequence);
		
		model.addAttribute("echantillons", echSelection);

		return Constants.SEQUENCE;
	}
	
	@GetMapping("/echantillons/ajouter/{echantillon}/{qualification}/{sequence}/{essai}")
	public String selectionnerEchantillon(
			@PathVariable(name = "echantillon") Integer idEchantillon,
			@PathVariable(name = "qualification") Integer numQualification, 
			@PathVariable(name = "sequence") Integer idSequence, 
			@PathVariable(name = "essai") Integer idEssai, 
			 Model model, HttpSession session, RedirectAttributes redirectAttributes
			) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.ajouterEchantillon(idEchantillon, numQualification, idSequence);
	
		redirectAttributes.addAttribute("essai", idEssai);
		redirectAttributes.addAttribute("qualification", numQualification);
		redirectAttributes.addAttribute("sequence", idSequence);

		return "redirect:/labplan/private/sequences/voir/retour";
	}
	
	
	@GetMapping("/echantillons/retirer/{echantillon}/{qualification}/{sequence}/{essai}")
	public String retirerEchantillon(
			@PathVariable(name = "echantillon") Integer idEchantillon,
			@PathVariable(name = "qualification") Integer numQualification, 
			@PathVariable(name = "sequence") Integer idSequence, 
			@PathVariable(name = "essai") Integer idEssai, 
			 Model model, HttpSession session, RedirectAttributes redirectAttributes
			) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.retirerEchantillon(idEchantillon, numQualification, idSequence);
	
		redirectAttributes.addAttribute("essai", idEssai);
		redirectAttributes.addAttribute("qualification", numQualification);
		redirectAttributes.addAttribute("sequence", idSequence);

		return "redirect:/labplan/private/sequences/voir/retour";
	}
	
	@GetMapping("/qualification/modifier/statut/{id}")
	public String modifierStatutQualification(
			@PathVariable(name = "id") Integer numQualification,
			 Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.modifierStatutQualification(numQualification);
			
		QualificationAux qualification = microServiceLab.obtenirQualification(numQualification);
		model.addAttribute("qualification", qualification);
		model.addAttribute("modification", false);
		System.out.println("Référence qualification: " + qualification.getReference());
		return Constants.QUALIFICATION;
	}
	
	@GetMapping("/qualification/modifier/resultat/{id}")   // Non utilisée - 1ere version
	public String modifierResultatQualification(
			@PathVariable(name = "id") Integer numQualification,
			 Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		QualificationAux qualification = microServiceLab.obtenirQualification(numQualification);
		model.addAttribute("qualification", qualification);
		model.addAttribute("modification", true);

		System.out.println("Référence qualification: " + qualification.getReference());
		return Constants.QUALIFICATION;
		
	}
	
	@GetMapping("/qualification/modifier/{id}")
	public String modifierQualification(
			@PathVariable(name = "id") Integer numQualification,
			 Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
			
		QualificationAux qualification = microServiceLab.obtenirQualification(numQualification);
		model.addAttribute("qualification", qualification);
		
		FormQualif formQualif = new FormQualif();
		
		formQualif.setNumero(qualification.getNumero());
		formQualif.setReference(qualification.getReference());
		formQualif.setProduit(qualification.getProjet());
		formQualif.setProjet(qualification.getProjet());
		formQualif.setObjet(qualification.getObjet());
				
		model.addAttribute("formQualif", formQualif);
		
		System.out.println("Référence qualification: " + qualification.getReference());
		return Constants.MODIFIER_QUALIFICATION;
	
	}
	
	@PostMapping("/qualification/modification/{id}")
	public String enregistrerModificationQualification(
			@PathVariable(name = "id") Integer numQualification,
			 Model model, HttpSession session, FormQualif formQualif) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		formQualif.setNumero(numQualification);
		microServiceLab.modifierQualification(formQualif);
		
		QualificationAux qualification = microServiceLab.obtenirQualification(numQualification);
		model.addAttribute("qualification", qualification);
		model.addAttribute("modification", false);
		System.out.println("Référence qualification: " + qualification.getReference());
		return Constants.QUALIFICATION;
		
	}
	
	@GetMapping("/sequence/supprimer/{essai}/{qualification}/{sequence}")
	public String supprimerSequence(@PathVariable(name = "essai") Integer idEssai,
			@PathVariable(name = "qualification") Integer num, 
			@PathVariable(name = "sequence") Integer idSequence,
			FormSequence formSequence, Model model, HttpSession session, 
			RedirectAttributes redirectAttributes) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		microServiceLab.supprimerSequence(idSequence);
		
		EssaiAux essai = microServiceLab.obtenirEssaiParNumero(idEssai);
		model.addAttribute("essai", essai);
		
		List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(num, idEssai);
		model.addAttribute("sequences", sequences);
		QualificationAux qualif = microServiceLab.obtenirQualificationParNumero(num);
		model.addAttribute("qualification", qualif);

		if (sequences.isEmpty()) {

			model.addAttribute("vide", true);

		} else {

			model.addAttribute("vide", false);
		}

		return Constants.LISTE_SEQUENCES;
		
	}
	
	@GetMapping("/essai/modifier/{num}/{id}/{domaine}")
	public String modifierEssais(
			@PathVariable(name = "num") Integer numQualification,
			@PathVariable(name = "id") Integer idEssai, 
			@PathVariable(name = "domaine") String nomDomaine,
			Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		EssaiAux essai = microServiceLab.obtenirEssaiParNumero(idEssai);
		FormEssai formEssai = new FormEssai();
		formEssai.setNumero(essai.getNumero());
		formEssai.setNom(essai.getNom());
		formEssai.setVersion(essai.getVersion());
		formEssai.setVersion(essai.getVersion());
		formEssai.setDomaine(essai.getDomaine());
		formEssai.setStatut(essai.getStatut());
		formEssai.setResultat(essai.getResultat());
		
		model.addAttribute("formEssai", formEssai);
		model.addAttribute("num", numQualification);
		model.addAttribute("id", idEssai);
		model.addAttribute("domaine", nomDomaine);
		
		return Constants.MODIFIER_ESSAI;   
	}
	
	@PostMapping("/essai/modifier/{id}/{num}/{domaine}")
	public String enregistrerModidificationEssai(
			@PathVariable(name = "id") Integer idEssai, 
			@PathVariable(name = "num") Integer numQualification,
			@PathVariable(name = "domaine") String nomDomaine,
			Model model, HttpSession session,
			FormEssai formEssai) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		formEssai.setId(idEssai);
		microServiceLab.modifierEssai(formEssai);
		
		QualificationAux qualification = microServiceLab.obtenirQualification(numQualification);

		List<EssaiAux> essais = microServiceLab.obtenirEssaisParQualification(numQualification);

		model.addAttribute("qualification", qualification);
		model.addAttribute("essais", essais);
		return Constants.PAGE_ESSAIS;
		
	}
	
	@GetMapping("/qualification/rapport/{num}")
	public String rapport(
			@PathVariable(name = "num") Integer numQualification,
			Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		
		
		return "ok";
	}
	
	@PostMapping("/sequence/creer/test/{id}/{qualification}") 
	public String test(
			@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification,
			FormSequence formSequence) {
		
		System.out.println("valeur début récupérée: " + formSequence.getDebutText());
		System.out.println("valeur fin récupérée: " + formSequence.getFinText());
		System.out.println("valeur heure début récupérée: " + formSequence.getDebutHeureText());
		System.out.println("valeur heure fin récupérée: " + formSequence.getFinHeureText());
		String debutText = formSequence.getDebutText();
		String finText = formSequence.getFinText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		if (formSequence.getDebutText() != "") {
	//	String debutText = formSequence.getDebutText();
		String debutHeureText = formSequence.getDebutHeureText();
		
		String debutTextConv = debutText+ " " + debutHeureText;
		
		LocalDateTime debut = LocalDateTime.parse(debutTextConv, formatter);
		System.out.println("Date début convertie : " + debut);
		formSequence.setDebut(debut);
		
		if (formSequence.getFinText() == "") {  // sans date de fin définie: debut = fin
			
			LocalDateTime fin = debut;
			formSequence.setFin(debut);
		}
		
		}else {
			
			System.out.println("Aucune date de debut définie!");
			formSequence.setDebut(null);
		}
		
		
		if (formSequence.getFinText() != "") {
	//	String finText = formSequence.getFinText();
		String finHeureText = formSequence.getFinHeureText();
		
		String finTextConv = finText+ " " + finHeureText;
		LocalDateTime fin = LocalDateTime.parse(finTextConv, formatter);
		System.out.println("Date fin convertie : " + fin);
		formSequence.setFin(fin);
		
		}else {
			
			if (formSequence.getDebutText() == "") {
				
			System.out.println("Aucune date de fin définie!");
			formSequence.setFin(null);
			
			}
		}
		
		
		formSequence.setEssai(id);
		formSequence.setQualification(qualification);
		
		
		microServiceLab.enregistrerSequence(formSequence);
		return "ok";
	}

}
