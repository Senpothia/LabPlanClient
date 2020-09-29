package com.michel.lab.controller;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.michel.lab.model.EchantillonAux;
import com.michel.lab.model.EssaiAux;
import com.michel.lab.model.RapportAux;
import com.michel.lab.model.Utilisateur;
import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/private/rapport")
public class RapportController {
	
	@Autowired
	private UserConnexion userConnexion;
	
	@Autowired
	private MicroServiceLab microServiceLab;
	
	
	@GetMapping("/pdf/{id}")
	public String genererPDF (@PathVariable("id") Integer idRapport
			, Model model, HttpSession session) {
		
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		
		RapportAux rapport = microServiceLab.obtenirRapportsParId(token, idRapport);
		System.out.println("id rapport récupéré: " + rapport.getId());
		System.out.println("id/num qualification du rapport: " + rapport.getQualification());
		
		List<EchantillonAux> echantillons = microServiceLab.obtenirEchantillonsParRapportId(token, idRapport);
		
		List<EssaiAux> essais = microServiceLab.obtenirEssaisParRapportId(token, idRapport);
		
		//*******************************************************************
		
		final String CREATED_PDF = "C:\\labplan";
		
		 Document document = new Document();
		    try {
		      PdfWriter.getInstance(document,new FileOutputStream(CREATED_PDF));
		      document.open();
		      // font and color settings
		      Font font = new Font(Font.TIMES_ROMAN, 18, Font.NORMAL, Color.MAGENTA);
		      Paragraph para = new Paragraph("Hello World PDF created using OpenPDF", font);
		      document.add(para);     
		    } catch (Exception de) {
		      System.err.println(de.getMessage());
		    }
		    document.close();
		
		return "ok";
	}

}
