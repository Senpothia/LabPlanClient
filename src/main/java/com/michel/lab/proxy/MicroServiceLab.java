package com.michel.lab.proxy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import com.michel.lab.model.DomaineAux;
import com.michel.lab.model.FormProcedure;
import com.michel.lab.model.FormQualif;
import com.michel.lab.model.Login;
import com.michel.lab.model.ProcedureAux;
import com.michel.lab.model.QualificationAux;
import com.michel.lab.model.UtilisateurAux;

import feign.Body;
import feign.Headers;


@FeignClient(name="lab-service", url="localhost:8081/lab-service")
public interface MicroServiceLab {
	/*
	@GetMapping("/ouvrage/liste")
	List<OuvrageAux> tousLesOuvrages(@RequestHeader("Authorization") String token);
	
	@GetMapping("/ouvrage/{id}")
	ResponseEntity<?> unOuvrage(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/ouvrage/rubriques")
	public List<String> toutesLesRubriques(@RequestHeader("Authorization") String token);
	
	@GetMapping("/exemplaire/disponibles")
	public List<Exemplaire> ListerExemplairesDisponibles(@RequestHeader("Authorization") String token);
	
	@GetMapping("/exemplaire/disponibles/{id}")
	public List<Exemplaire> ListerExemplairesDisponiblesParOuvrage(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token);

	@GetMapping("/ouvrage/liste/rubrique/{rubrique}")
	public List<OuvrageAux> tousLesOuvragesParRubrique(@PathVariable  String rubrique, @RequestHeader("Authorization") String token);
	
	@PutMapping("/emprunts/save")
	void enregistrerEmprunt(EmpruntAux empruntAux, @RequestHeader("Authorization") String token);
	
	@GetMapping("/ouvrage/emprunts/actifs/{id}")
	public List<LigneEmprunt> empruntsActifs(@PathVariable  Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/ouvrage/emprunts/hist/{id}")
	public List<LigneEmprunt> empruntsHist(@PathVariable  Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/prolonger/{id}")	
	void prolonger(@PathVariable  Integer id);
	*/
	
	@PutMapping("/modifier/compte/{id}")
	public void modifierCompte(@PathVariable  Integer id, @RequestHeader("Authorization") String token, @RequestBody UtilisateurAux utilisateurAux);
	
	@PostMapping("connexion/")
	public ResponseEntity<UtilisateurAux> generate(@RequestBody final Login login);
	
	
	@PostMapping("compte/")
	public void creerCompte (UtilisateurAux user);
	
	@PostMapping("/save/qualification")  // Enregistrement d'une qualification
	public void saveQualification(FormQualif formQualif);
	
	@PostMapping("/save/procedure")  // Enregistrement d'une procédure
	public void saveProcedure(FormProcedure formProcedure);
	
	@GetMapping("/private/domaines")       // récupération de la liste des domaines
	public List<String> tousLesDomaines();
	
	@GetMapping("/private/qualifications")       // récupération de la liste de toutes les qualifications
	public List<QualificationAux> toutesLesQualifications();
	
	@GetMapping("/private/historique/{id}")       // récupération de la liste de toutes les qualifications par utilisateur
	public List<QualificationAux> mesQualifications(@PathVariable (name = "id") Integer id);
	
	@GetMapping("/private/qualifications/{id}")   // récupération de la liste de toutes les qualifications en cours par utilisateur
	public List<QualificationAux> mesQualificationsEnCours(@PathVariable (name = "id") Integer id);
	
	@GetMapping("/private/qualification/{id}")   // récupération de la liste de toutes les qualifications en cours par utilisateur
	public QualificationAux obtenirQualification(@PathVariable (name = "id") Integer id);
	
	@GetMapping("/private/procedures") 				// Récupérer la liste des procédures
	public List<ProcedureAux> obtenirProcedures();
	
	@GetMapping("/private/liste/domaines") 			// Récupérer la liste des domaines
	public List<DomaineAux> obtenirDomaines();
	
	@GetMapping("/private/liste/domaine/{id}") 
	public List<ProcedureAux> obtenirProceduresParDomaine(@PathVariable(name = "id") Integer id);
	
	
	
}

