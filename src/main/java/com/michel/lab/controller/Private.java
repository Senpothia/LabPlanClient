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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.michel.lab.constants.Constants;
import com.michel.lab.model.EchantillonAux;
import com.michel.lab.model.EssaiAux;
import com.michel.lab.model.FormEchantillon;
import com.michel.lab.model.FormEssai;
import com.michel.lab.model.FormInitRapport;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.FormSequence;
import com.michel.lab.model.GroupeRapport;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.RapportAux;
import com.michel.lab.model.SequenceAux;
import com.michel.lab.model.Upload;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;
import com.michel.lab.utils.UploadImage;

@Controller
@RequestMapping("/private")
public class Private {

	@Autowired
	private MicroServiceLab microServiceLab;

	@Autowired
	private UserConnexion userConnexion;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	private  DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
	@GetMapping("/qualifications/access")
	public String accessQualifications(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			return "accueil_qualification";

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualification/creation") // Accès au formulaire de création d'un qualification
	public String creer(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("formQualif", new FormQualif());

		if (testUser(utilisateur)) {

			return Constants.CREATION_QUALIFICATION;

		} else {

			return "redirect:/connexion";
		}

	}

	@PostMapping("/qualification/creation") // Enregistrement des éléments de création d'une qualification
	public String enregistrerQualification(Model model, HttpSession session, FormQualif formQualif) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			Integer createurId = utilisateur.getId();
			formQualif.setCreateurId(createurId);
			microServiceLab.saveQualification(token, formQualif);

			return Constants.ESPACE_PERSONEL;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualifications")
	public String qualifications(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			List<QualificationAux> qualifications = microServiceLab.toutesLesQualifications(token);
			model.addAttribute("qualifications", qualifications);
			model.addAttribute("access", "1");
			return Constants.QUALIFICATIONS;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/historique/{id}") // récupération de la liste de toutes les qualifications
									// id = identifiant de l'utilisateur
	public String mesQualifications(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			List<QualificationAux> qualifications = microServiceLab.mesQualifications(token, id);
			model.addAttribute("qualifications", qualifications);
			model.addAttribute("access", "2");
			return Constants.QUALIFICATIONS;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualifications/{id}") // Récupérer les qualification d'un utilisateur, identifiant : id
										// id = identifiant de l'utilisateur
	public String qualificationsEnCours(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			List<QualificationAux> qualifications = microServiceLab.mesQualificationsEnCours(token, id);
			model.addAttribute("qualifications", qualifications);
			model.addAttribute("access", "3");
			return Constants.QUALIFICATIONS;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualification/{id}") // Consulter une qualification
										// id = identifiant de l'utilisateur
	public String qualification(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			QualificationAux qualification = microServiceLab.obtenirQualification(token, id);
			model.addAttribute("qualification", qualification);
			model.addAttribute("modification", false);

			return Constants.QUALIFICATION;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/essai/voir/{id}")
	public String visualiserEssais(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			QualificationAux qualification = microServiceLab.obtenirQualification(token, id);

			List<EssaiAux> essais = microServiceLab.obtenirEssaisParQualification(token, id);

			model.addAttribute("qualification", qualification);
			model.addAttribute("essais", essais);
			return Constants.PAGE_ESSAIS;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/sequences/{id}/{num}/{domaine}") // id = numéro qualification, num = id essai
	public String voirSequencesParEssais(@PathVariable(name = "id") Integer id, @PathVariable(name = "num") Integer num,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			EssaiAux essai = microServiceLab.obtenirEssaiParNumero(token, num);
			model.addAttribute("essai", essai);

			List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(token, id, num);
			model.addAttribute("sequences", sequences);
			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, id);
			model.addAttribute("qualification", qualification);

			if (sequences.isEmpty()) {

				model.addAttribute("vide", true);

			} else {

				model.addAttribute("vide", false);
			}
			return Constants.LISTE_SEQUENCES;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/sequence/creer/{id}/{qualification}/{domaine}")
	public String creerSequence(@PathVariable(name = "id") Integer id, // id = identifiant essai
			@PathVariable(name = "qualification") Integer qualification, @PathVariable(name = "domaine") String domaine,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			FormSequence formSequence = new FormSequence();
			formSequence.setEssai(id);
			formSequence.setQualification(qualification);
			formSequence.setNomDomaine(domaine);

			model.addAttribute("formSequence", formSequence);
			model.addAttribute("id", id);
			model.addAttribute("qualification", qualification);
			model.addAttribute("domaine", domaine);
			return Constants.CREATION_SEQUENCE;

		} else {

			return "redirect:/connexion";
		}

	}

	@PostMapping("/sequence/creer/{id}/{qualification}")
	public String enregistrerSequence(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "qualification") Integer qualification, Model model, HttpSession session,
			FormSequence formSequence, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			formSequence.setEssai(id);

			microServiceLab.enregistrerSequence(token, formSequence);

			redirectAttributes.addAttribute("id", qualification);
			redirectAttributes.addAttribute("num", id);

			return "redirect:/private/sequences";

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/sequences")
	public String voirSequencesParEssais2(@RequestParam(name = "id") Integer qualification,
			@RequestParam(name = "num") Integer id, // id = id essai

			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			EssaiAux essai = microServiceLab.obtenirEssaiParNumero(token, id);
			model.addAttribute("essai", essai);

			List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(token, qualification, id);
			model.addAttribute("sequences", sequences);
			QualificationAux qualif = microServiceLab.obtenirQualificationParNumero(token, qualification);
			model.addAttribute("qualification", qualif);

			if (sequences.isEmpty()) {

				model.addAttribute("vide", true);

			} else {

				model.addAttribute("vide", false);
			}
			return Constants.LISTE_SEQUENCES;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/sequences/voir/{id}/{num}/{sequence}")
	public String voirSequence(@PathVariable(name = "id") Integer id, // id = numéro de qualification
			@PathVariable(name = "num") Integer num, // num = id de l'essai
			@PathVariable(name = "sequence") Integer idSequence, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			SequenceAux sequence = microServiceLab.obtenirSequenceParId(token, idSequence);
			
		
			model.addAttribute("sequence", sequence);
			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, id);
			model.addAttribute("qualification", qualification);
			EssaiAux essai = microServiceLab.obtenirEssaiParNumero(token, num);
			model.addAttribute("essai", essai);
		
			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(token, id);
			List<EchantillonAux> echSelection = microServiceLab.obtenirEchantillonSelectionParSequence(token, id,
					idSequence);
			
			model.addAttribute("echantillons", echSelection);
			return Constants.SEQUENCE;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/sequence/modifier/{id}/{qualification}/{sequence}")
	public String modifierSequence(@PathVariable(name = "id") Integer idEssai, // id = id de l'essai
			@PathVariable(name = "qualification") Integer numQualif, // numéro de la qualification
			@PathVariable(name = "sequence") Integer idSequence, // id de la séquence
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			SequenceAux sequence = microServiceLab.obtenirSequenceParId(token, idSequence);
			FormSequence formSequence = new FormSequence();
			formSequence.setId(sequence.getId());
			formSequence.setNumero(sequence.getNumero());
			formSequence.setNom(sequence.getNom());
			formSequence.setNiveau(sequence.getNiveau());
			formSequence.setDomaine(sequence.getDomaine());

			formSequence.setDebut(sequence.getDebut());
			formSequence.setFin(sequence.getFin());
			formSequence.setDebutText(sequence.getDebutText());
			formSequence.setFinText(sequence.getFinText());
			formSequence.setDebutHeureText(getHourFormDate(sequence.getDebut()));
			formSequence.setFinHeureText(getHourFormDate(sequence.getFin()));
		
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

		} else {

			return "redirect:/connexion";
		}

	}

	@PostMapping("/sequence/modifier/{essai}/{qualification}/{sequence}")
	public String enregistrerModificationSequence(@PathVariable(name = "essai") Integer idEssai,
			@PathVariable(name = "qualification") Integer num, @PathVariable(name = "sequence") Integer idSequence,
			FormSequence formSequence, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			formSequence.setEssai(idEssai);
			formSequence.setId(idSequence);

			String debutText = formSequence.getDebutText();
			String debutHeureText = formSequence.getDebutHeureText();
			String finText = formSequence.getFinText();
			String finHeureText = formSequence.getFinHeureText();
			
			microServiceLab.modifierSequence(token, formSequence);

			redirectAttributes.addAttribute("essai", idEssai);
			redirectAttributes.addAttribute("qualification", num);
			redirectAttributes.addAttribute("sequence", idSequence);
			return "redirect:/private/sequences/voir/retour";

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/sequences/voir/retour")
	public String voirSequence2(@RequestParam(name = "essai") Integer idEssai, // num = id de l'essai
			@RequestParam(name = "qualification") Integer num, // id = numéro de qualification
			@RequestParam(name = "sequence") Integer idSequence, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			SequenceAux sequence = microServiceLab.obtenirSequenceParId(token, idSequence);
				
			model.addAttribute("sequence", sequence);
			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, num);
			model.addAttribute("qualification", qualification);
			EssaiAux essai = microServiceLab.obtenirEssaiParNumero(token, idEssai);
			model.addAttribute("essai", essai);
			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(token, num);
			List<EchantillonAux> echSelection = microServiceLab.obtenirEchantillonSelectionParSequence(token, num,
					idSequence);

			model.addAttribute("echantillons", echSelection);

			return Constants.SEQUENCE;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/echantillons/ajouter/{echantillon}/{qualification}/{sequence}/{essai}")
	public String selectionnerEchantillon(@PathVariable(name = "echantillon") Integer idEchantillon,
			@PathVariable(name = "qualification") Integer numQualification,
			@PathVariable(name = "sequence") Integer idSequence, @PathVariable(name = "essai") Integer idEssai,
			Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			microServiceLab.ajouterEchantillon(token, idEchantillon, numQualification, idSequence);

			redirectAttributes.addAttribute("essai", idEssai);
			redirectAttributes.addAttribute("qualification", numQualification);
			redirectAttributes.addAttribute("sequence", idSequence);
			return "redirect:/private/sequences/voir/retour";

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/echantillons/retirer/{echantillon}/{qualification}/{sequence}/{essai}")
	public String retirerEchantillon(@PathVariable(name = "echantillon") Integer idEchantillon,
			@PathVariable(name = "qualification") Integer numQualification,
			@PathVariable(name = "sequence") Integer idSequence, @PathVariable(name = "essai") Integer idEssai,
			Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			microServiceLab.retirerEchantillon(token, idEchantillon, numQualification, idSequence);

			redirectAttributes.addAttribute("essai", idEssai);
			redirectAttributes.addAttribute("qualification", numQualification);
			redirectAttributes.addAttribute("sequence", idSequence);

			return "redirect:/private/sequences/voir/retour";

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualification/modifier/statut/{id}")
	public String modifierStatutQualification(@PathVariable(name = "id") Integer numQualification, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			microServiceLab.modifierStatutQualification(token, numQualification);

			QualificationAux qualification = microServiceLab.obtenirQualification(token, numQualification);
			model.addAttribute("qualification", qualification);
			model.addAttribute("modification", false);

			return Constants.QUALIFICATION;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualification/modifier/resultat/{id}") // Non utilisée - 1ere version
	public String modifierResultatQualification(@PathVariable(name = "id") Integer numQualification, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			QualificationAux qualification = microServiceLab.obtenirQualification(token, numQualification);
			model.addAttribute("qualification", qualification);
			model.addAttribute("modification", true);

			return Constants.QUALIFICATION;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualification/modifier/{id}")
	public String modifierQualification(@PathVariable(name = "id") Integer numQualification, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			QualificationAux qualification = microServiceLab.obtenirQualification(token, numQualification);
			model.addAttribute("qualification", qualification);

			FormQualif formQualif = new FormQualif();

			formQualif.setNumero(qualification.getNumero());
			formQualif.setReference(qualification.getReference());
			formQualif.setProduit(qualification.getProjet());
			formQualif.setProjet(qualification.getProjet());
			formQualif.setObjet(qualification.getObjet());

			model.addAttribute("formQualif", formQualif);

			return Constants.MODIFIER_QUALIFICATION;

		} else {

			return "redirect:/connexion";
		}

	}

	@PostMapping("/qualification/modification/{id}")
	public String enregistrerModificationQualification(@PathVariable(name = "id") Integer numQualification, Model model,
			HttpSession session, FormQualif formQualif) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			formQualif.setNumero(numQualification);
			microServiceLab.modifierQualification(token, formQualif);

			QualificationAux qualification = microServiceLab.obtenirQualification(token, numQualification);
			model.addAttribute("qualification", qualification);
			model.addAttribute("modification", false);

			return Constants.QUALIFICATION;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/sequence/supprimer/{essai}/{qualification}/{sequence}")
	public String supprimerSequence(@PathVariable(name = "essai") Integer idEssai,
			@PathVariable(name = "qualification") Integer num, @PathVariable(name = "sequence") Integer idSequence,
			FormSequence formSequence, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			microServiceLab.supprimerSequence(token, idSequence);

			EssaiAux essai = microServiceLab.obtenirEssaiParNumero(token, idEssai);
			model.addAttribute("essai", essai);

			List<SequenceAux> sequences = microServiceLab.obtenirSequencesParEssai(token, num, idEssai);
			model.addAttribute("sequences", sequences);
			QualificationAux qualif = microServiceLab.obtenirQualificationParNumero(token, num);
			model.addAttribute("qualification", qualif);

			if (sequences.isEmpty()) {

				model.addAttribute("vide", true);

			} else {

				model.addAttribute("vide", false);
			}
			return Constants.LISTE_SEQUENCES;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/essai/modifier/{num}/{id}/{domaine}")
	public String modifierEssais(@PathVariable(name = "num") Integer numQualification,
			@PathVariable(name = "id") Integer idEssai, @PathVariable(name = "domaine") String nomDomaine, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			EssaiAux essai = microServiceLab.obtenirEssaiParNumero(token, idEssai);
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

		} else {

			return "redirect:/connexion";
		}

	}

	@PostMapping("/essai/modifier/{id}/{num}/{domaine}")
	public String enregistrerModidificationEssai(@PathVariable(name = "id") Integer idEssai,
			@PathVariable(name = "num") Integer numQualification, @PathVariable(name = "domaine") String nomDomaine,
			Model model, HttpSession session, FormEssai formEssai) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			formEssai.setId(idEssai);
			microServiceLab.modifierEssai(token, formEssai);

			QualificationAux qualification = microServiceLab.obtenirQualification(token, numQualification);

			List<EssaiAux> essais = microServiceLab.obtenirEssaisParQualification(token, numQualification);

			model.addAttribute("qualification", qualification);
			model.addAttribute("essais", essais);
			return Constants.PAGE_ESSAIS;

		} else {

			return "redirect:/connexion";
		}

	}

	@PostMapping("/sequence/creer/enregistrer/{id}/{qualification}")
	public String test(@PathVariable(name = "id") Integer idEssai,
			@PathVariable(name = "qualification") Integer numQualification, FormSequence formSequence,
			HttpSession session, RedirectAttributes redirectAttributes) {

		// Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;

		String debutText = formSequence.getDebutText();
		String finText = formSequence.getFinText();

		formSequence.setEssai(idEssai);
		formSequence.setQualification(numQualification);

		microServiceLab.enregistrerSequence(token, formSequence);

		redirectAttributes.addAttribute("id", numQualification);
		redirectAttributes.addAttribute("num", idEssai);

		return "redirect:/private/sequences";

	}

	@GetMapping("/qualification/rapport/liste/{num}") // accès à la liste de tous les rapports d'une qualification
	public String rapportListe(@PathVariable(name = "num") Integer numQualification, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			List<RapportAux> rapports = microServiceLab.obtenirRapportsParQualification(token, numQualification);

			if (!rapports.isEmpty()) {

				model.addAttribute("vide", false);
				model.addAttribute("rapports", rapports);
				model.addAttribute("qualification", numQualification);

			} else {

				model.addAttribute("rapports", rapports);
				model.addAttribute("qualification", numQualification);
				model.addAttribute("vide", true);

			}
			return Constants.RAPPORTS;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualification/rapport/{num}") // création d'un nouveau rapport pour une qualification
	public String rapport(@PathVariable(name = "num") Integer numQualification, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, numQualification);

			FormInitRapport formInitRapport = new FormInitRapport();
			formInitRapport.setQualification(numQualification);
			formInitRapport.setProjet(qualification.getProjet());
			model.addAttribute("formInitRapport", formInitRapport);
			return Constants.INIT_RAPPORT;

		} else {

			return "redirect:/connexion";
		}

	}

	@PostMapping("/qualification/rapport/{qualification}") // Enregistrement des données de rapport
	public String enregistrementInitRapport(@PathVariable("qualification") Integer numQualification,
			FormInitRapport formInitRapport, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			Integer auteur = utilisateur.getId();

			formInitRapport.setAuteur(auteur);
			Integer idRapport = microServiceLab.enregistrerInitRapport(token, formInitRapport); // changé en 2 pour
																								// test!

			RapportAux rapport = microServiceLab.obtenirRapportsParId(token, idRapport);

			model.addAttribute("rapport", rapport);

			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParRapportId(token, idRapport);
			model.addAttribute("echantillons", echantillons);

			List<EssaiAux> essais = microServiceLab.obtenirEssaisParRapportId(token, idRapport);
			model.addAttribute("essais", essais);
			return Constants.RAPPORT;

		} else {

			return "redirect:/connexion";
		}

	}

	@PostMapping(value = "/qualification/rapport/upload/{qualification}") // Upload des images de test
	public String guardar(@RequestParam("repertoireImages") MultipartFile multiPart, Model model, HttpSession session,
			Upload upload) {

		if (!multiPart.isEmpty()) {
			// String path = "/labplan/images/"; // Linux/MAC
			String path = "C:/labplan/images/"; // Windows
			String nomImage = UploadImage.guardarArchivo(multiPart, path);
			if (nomImage != null) {

				upload.setImage(nomImage);
			}
		}

		// implémenter l'enregistrement de l'avis de confomité du rapport

		return "ok";
	}

	@GetMapping("/qualification/rapport/supprimer/{id}")
	public String supprimerRapport(@PathVariable("id") Integer IdRapport, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			microServiceLab.supprimerRapportsParId(token, IdRapport);
			return Constants.ESPACE_PERSONEL;

		} else {

			return "redirect:/connexion";
		}

	}

	@GetMapping("/qualification/rapport/voir/{id}") // Visualisation d'un rapport - version 2
	public String visualiserRapport(@PathVariable("id") Integer idRapport, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;

			RapportAux rapport = microServiceLab.obtenirRapportsParId(token, idRapport);

			model.addAttribute("rapport", rapport);

			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParRapportId(token, idRapport);
			model.addAttribute("echantillons", echantillons);

			List<EssaiAux> essais = microServiceLab.obtenirEssaisParRapportId(token, idRapport);
			model.addAttribute("essais", essais);
			return Constants.RAPPORT;

		} else {

			return "redirect:/connexion";
		}

	}

	public boolean testUser(Utilisateur utilisateur) {

		if (utilisateur == null) {

			return false;

		} else

			return true;
	}

	private String convertDateToString(LocalDateTime date) {

		String convertedDate = date.format(formatter);
		return convertedDate;
	}

	private String getHourFormDate(LocalDateTime debut) {

		String heure = debut.format(formatter2);
		return heure;
	}
	
	@GetMapping("/sequence/details/{num}/{id}/{sequence}")
	public String voirSequenceDetails(@PathVariable(name = "id") Integer id, // id = numéro de qualification
			@PathVariable(name = "num") Integer num, // num = id de l'essai
			@PathVariable(name = "sequence") Integer idSequence, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);

		if (testUser(utilisateur)) {

			String token = (String) session.getAttribute("TOKEN");
			token = "Bearer " + token;
			SequenceAux sequence = microServiceLab.obtenirSequenceParId(token, idSequence);
			
		
			model.addAttribute("sequence", sequence);
			QualificationAux qualification = microServiceLab.obtenirQualificationParNumero(token, id);
			model.addAttribute("qualification", qualification);
			EssaiAux essai = microServiceLab.obtenirEssaiParNumero(token, num);
			model.addAttribute("essai", essai);
		
			List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParQualification(token, id);
			List<EchantillonAux> echSelection = microServiceLab.obtenirEchantillonSelectionParSequence(token, id,
					idSequence);
			
			model.addAttribute("echantillons", echSelection);
			return Constants.SEQUENCE_DETAILS;

		} else {

			return "redirect:/connexion";
		}

	}

	

}
