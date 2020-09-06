package com.michel.lab.view.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Phaser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.michel.lab.constants.Constants;
import com.michel.lab.model.EchantillonAux;
import com.michel.lab.model.EssaiAux;
import com.michel.lab.model.RapportAux;
import com.michel.lab.model.SequenceAux;

import rx.annotations.Beta;

@Component("rapport")
public class RapportViewPdf extends AbstractPdfView {

	private static final Phrase HeaderFooter = null;

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {

		super.buildPdfMetadata(model, document, request);

		RapportAux rapport = (RapportAux) model.get("rapport");

		try {
			Image entete = Image.getInstance("src\\main\\resources\\static\\images\\Bandeausup1.jpg");
			entete.scaleAbsolute(523, 100);
			HeaderFooter header = new HeaderFooter(new Phrase(new Chunk(entete, 0, -35)), false);
			HeaderFooter footer = new HeaderFooter(
					new Phrase(rapport.getIdentifiant(), FontFactory.getFont(FontFactory.TIMES)), false);
			document.setHeader(header);
			document.setFooter(footer);

		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		RapportAux rapport = (RapportAux) model.get("rapport");
		List<EchantillonAux> echantillons = (List<EchantillonAux>) model.get("echantillons");
		List<EssaiAux> essais = (List<EssaiAux>) model.get("essais");

		Paragraph margeSup = new Paragraph(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		margeSup.setAlignment(Element.ALIGN_CENTER);
		margeSup.setSpacingAfter(80);
		document.add(margeSup);

		Paragraph titre = new Paragraph(
				new Chunk(rapport.getTitre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		titre.setAlignment(Element.ALIGN_CENTER);
		titre.setSpacingAfter(40);
		document.add(titre);

		Paragraph auteur = new Paragraph(
				new Chunk(rapport.getAuteur(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		auteur.setAlignment(Element.ALIGN_CENTER);
		auteur.setSpacingAfter(10);
		document.add(auteur);

		Paragraph date = new Paragraph(new Chunk(rapport.getDate(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		date.setAlignment(Element.ALIGN_CENTER);
		date.setSpacingAfter(10);
		document.add(date);

		Paragraph edition = new Paragraph(new Chunk("Edition: " + rapport.getVersion().toString(),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		edition.setAlignment(Element.ALIGN_CENTER);
		edition.setSpacingAfter(10);
		document.add(edition);

		Paragraph projet = new Paragraph(
				new Chunk("Projet: " + rapport.getProjet(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		projet.setAlignment(Element.ALIGN_CENTER);
		projet.setSpacingAfter(10);
		document.add(projet);

		Paragraph demande = new Paragraph(
				new Chunk("Demande: " + rapport.getDemande(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		demande.setAlignment(Element.ALIGN_CENTER);
		demande.setSpacingAfter(10);
		document.add(demande);

		Paragraph reference = new Paragraph(
				new Chunk("Référence: " + rapport.getIdentifiant(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		reference.setAlignment(Element.ALIGN_CENTER);
		reference.setSpacingAfter(10);
		document.add(reference);

		/*
		 * PdfPTable table = new PdfPTable(1); table.addCell("Qualification");
		 * document.add(table);
		 */

		/*
		 * Image jpg =
		 * Image.getInstance("src\\main\\resources\\static\\images\\Bandeausup1.jpg");
		 * document.add(jpg);
		 * 
		 */

		// start second page
		document.newPage();

		Paragraph margeSup1 = new Paragraph(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		margeSup1.setAlignment(Element.ALIGN_CENTER);
		margeSup1.setSpacingAfter(-20);
		document.add(margeSup1);

		Paragraph objetTitre = new Paragraph(new Chunk("Objet", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		Paragraph objet = new Paragraph(
				new Chunk(rapport.getObjet(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		objet.setSpacingAfter(20);

		document.add(objetTitre);
		document.add(objet);

		Paragraph EchantillonsTitre = new Paragraph(
				new Chunk("Echantillons", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		EchantillonsTitre.setSpacingAfter(20);
		document.add(EchantillonsTitre);

		//////////////////////////////////////////////////////////

		PdfPTable tableEchantillon = new PdfPTable(6);
		tableEchantillon.setWidths(new float[] { 1, 1, 1.5f, 1, 3, 1 });

		tableEchantillon.addCell("Ordre");
		tableEchantillon.addCell("Numéro");
		tableEchantillon.addCell("Date");
		tableEchantillon.addCell("Version");
		tableEchantillon.addCell("Caractéristique");
		tableEchantillon.addCell("Statut");
		int i = 1;
		for (EchantillonAux e : echantillons) {

			tableEchantillon.addCell(String.valueOf(i));
			tableEchantillon.addCell(e.getNumero().toString());
			tableEchantillon.addCell(e.getDate());
			tableEchantillon.addCell(String.valueOf(e.getVersion()));
			tableEchantillon.addCell(e.getCaracteristique());

			boolean statut = e.isStatut();
			String etat = null;
			if (statut) {

				etat = "Actif";
			} else {

				etat = "Inactif";
			}
			tableEchantillon.addCell(etat);
			i++;
		}

		tableEchantillon.setSpacingAfter(20);
		document.add(tableEchantillon);

		Paragraph EssaisTitre = new Paragraph(new Chunk("Essais", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		EssaisTitre.setSpacingAfter(30);
		document.add(EssaisTitre);

		for (EssaiAux es : essais) {

			PdfPTable esTable = new PdfPTable(6);
			esTable.setWidths(new float[] { 0.5f, 3.5f, 1, 1, 1, 1 });

			esTable.addCell("N°");
			esTable.addCell("Essai");
			esTable.addCell("Version");
			esTable.addCell("Domaine");
			esTable.addCell("Statut");
			esTable.addCell("Résultat");
			int j = 1;

			esTable.addCell(es.getNumero().toString());
			esTable.addCell(es.getNom());
			esTable.addCell(es.getVersion());
			esTable.addCell(es.getDomaine());
			esTable.addCell(es.getStatut());
			esTable.addCell(es.getResultat());

			esTable.setSpacingAfter(20);
			document.add(esTable);

			List<SequenceAux> sequences = es.getSequences();

			Paragraph sequenceTitre = new Paragraph(
					new Chunk("    Séquences", FontFactory.getFont(FontFactory.TIMES_ROMAN, 18)));
			sequenceTitre.setSpacingAfter(30);
			document.add(sequenceTitre);

			for (SequenceAux s : sequences) {

				PdfPTable seqTable = new PdfPTable(10);
				seqTable.setWidths(new float[] { 1, 2f, 2.5f, 3.0f, 3.0f, 2.5f, 2.0f, 5.0f, 2.0f, 2.5f });

				seqTable.addCell(new Phrase("N°", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Nom", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Niveau", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Début", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Fin", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Durée", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Profil", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Commentaire", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Statut", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				seqTable.addCell(new Phrase("Résultat", FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
				int k = 1;

				//seqTable.addCell(String.valueOf(k));
				seqTable.addCell(new Phrase(String.valueOf(k), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase(s.getNom(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase(s.getNiveau(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase(s.getDebutText(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase(s.getFinText(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase("Durée", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase(s.getProfil(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase(s.getCommentaire(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase(s.getActif(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
				seqTable.addCell(new Phrase( s.getAvis(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));

				seqTable.setSpacingAfter(20);
				document.add(seqTable);
			}

			document.newPage();
			
			Paragraph margeSup2 = new Paragraph(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
			margeSup2.setAlignment(Element.ALIGN_CENTER);
			margeSup2.setSpacingAfter(-20);
			document.add(margeSup2);
			
		

		}
		
		Paragraph avisTitre = new Paragraph(
				new Chunk("Avis", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		avisTitre.setSpacingAfter(20);
		document.add(avisTitre);
		
		Paragraph avis = new Paragraph(
				new Chunk(rapport.getAvis(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		avis.setSpacingAfter(20);
		document.add(avis);
		
		

	}

}
