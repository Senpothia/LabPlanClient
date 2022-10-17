package com.michel.lab.view.pdf;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
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
import com.lowagie.text.pdf.PdfPCell;
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

	private URL bandeau = getClass().getClassLoader()
			.getResource("/main/resources/static/images/bandeau_entreprise1.png");

	private static final Phrase HeaderFooter = null;

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {

		super.buildPdfMetadata(model, document, request);

		RapportAux rapport = (RapportAux) model.get("rapport");

		try {

			Image entete = Image.getInstance("https://i.ibb.co/61HYK40/Bandeausup1.jpg");
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
				new Chunk(rapport.getTitre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 28)));
		titre.setAlignment(Element.ALIGN_CENTER);
		titre.setSpacingAfter(40);
		document.add(titre);

		Paragraph auteur = new Paragraph(
				new Chunk(rapport.getAuteur(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 16)));
		auteur.setAlignment(Element.ALIGN_CENTER);
		auteur.setSpacingAfter(10);
		document.add(auteur);

		Paragraph date = new Paragraph(new Chunk(rapport.getDate(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 16)));
		date.setAlignment(Element.ALIGN_CENTER);
		date.setSpacingAfter(10);
		document.add(date);

		Paragraph edition = new Paragraph(new Chunk("Edition: " + rapport.getVersion().toString(),
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 16)));
		edition.setAlignment(Element.ALIGN_CENTER);
		edition.setSpacingAfter(10);
		document.add(edition);

		Paragraph projet = new Paragraph(
				new Chunk("Projet: " + rapport.getProjet(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 16)));
		projet.setAlignment(Element.ALIGN_CENTER);
		projet.setSpacingAfter(10);
		document.add(projet);

		Paragraph demande = new Paragraph(
				new Chunk("Demande: " + rapport.getDemande(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 16)));
		demande.setAlignment(Element.ALIGN_CENTER);
		demande.setSpacingAfter(10);
		document.add(demande);

		Paragraph reference = new Paragraph(
				new Chunk("Référence: " + rapport.getIdentifiant(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 16)));
		reference.setAlignment(Element.ALIGN_CENTER);
		reference.setSpacingAfter(10);
		document.add(reference);

		// start second page
		document.newPage();

		Paragraph margeSup1 = new Paragraph(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		margeSup1.setAlignment(Element.ALIGN_CENTER);
		margeSup1.setSpacingAfter(-20);
		document.add(margeSup1);

		Paragraph objetTitre = new Paragraph(new Chunk("1. Objet", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		Paragraph objet = new Paragraph(
				new Chunk(rapport.getObjet(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		objet.setSpacingAfter(20);

		document.add(objetTitre);
		document.add(objet);

		Paragraph EchantillonsTitre = new Paragraph(
				new Chunk("2. Echantillons", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		EchantillonsTitre.setSpacingAfter(20);
		document.add(EchantillonsTitre);

		int i = 1;
		for (EchantillonAux e : echantillons) {

			PdfPTable tableEchantillon = new PdfPTable(2);
			tableEchantillon.setWidths(new float[] { 1, 3.0f });

			PdfPCell cell1 = new PdfPCell(new Paragraph("Ordre"));
			cell1.setBackgroundColor(new Color(190, 58, 220));
			PdfPCell cell2 = new PdfPCell(new Paragraph(String.valueOf(i)));
			cell2.setBackgroundColor(new Color(190, 58, 220));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Numéro"));
			PdfPCell cell4 = new PdfPCell(new Paragraph(e.getNumero().toString()));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Date"));

			PdfPCell cell6 = new PdfPCell(new Paragraph(e.getDate()));

			PdfPCell cell7 = new PdfPCell(new Paragraph("Version"));

			PdfPCell cell8 = new PdfPCell(new Paragraph(e.getVersion().toString()));

			PdfPCell cell9 = new PdfPCell(new Paragraph("Caractéristique"));
			PdfPCell cell10 = new PdfPCell(new Paragraph(e.getCaracteristique()));

			boolean statut = e.isStatut();
			String etat = null;
			if (statut) {

				etat = "Actif";
			} else {

				etat = "Inactif";
			}

			PdfPCell cell11 = new PdfPCell(new Paragraph("Statut"));
			PdfPCell cell12 = new PdfPCell(new Paragraph(etat));

			tableEchantillon.addCell(cell1);
			tableEchantillon.addCell(cell2);
			tableEchantillon.addCell(cell3);
			tableEchantillon.addCell(cell4);
			tableEchantillon.addCell(cell5);
			tableEchantillon.addCell(cell6);
			tableEchantillon.addCell(cell7);
			tableEchantillon.addCell(cell8);
			tableEchantillon.addCell(cell9);
			tableEchantillon.addCell(cell10);
			tableEchantillon.addCell(cell11);
			tableEchantillon.addCell(cell12);

			i++;
			tableEchantillon.setSpacingAfter(20);
			document.add(tableEchantillon);
			
		}
		
		document.newPage();
		Paragraph EssaisRésultats = new Paragraph(new Chunk("3. Résultats", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		EssaisRésultats.setSpacingAfter(20);
		document.add(EssaisRésultats);
		
		PdfPTable resTable = new PdfPTable(3);
		resTable.setWidths(new float[] { 0.5f, 3f, 1.0f });
		
		PdfPCell cell1 = new PdfPCell(new Paragraph(new Chunk("N°")));
		cell1.setBackgroundColor(new Color(46, 127, 238));
		
		PdfPCell cell111 = new PdfPCell(new Paragraph(new Chunk("Test")));
		cell111.setBackgroundColor(new Color(46, 127, 238));
		
		PdfPCell cell2 = new PdfPCell(new Paragraph(new Chunk("Résultat")));
		cell2.setBackgroundColor(new Color(46, 127, 238));
		resTable.addCell(cell1);
		resTable.addCell(cell111);
		resTable.addCell(cell2);
		
		for (EssaiAux es : essais) {
			
			PdfPCell cell3 = new PdfPCell(new Paragraph(new Chunk(es.getNumero().toString())));
			PdfPCell cell31 = new PdfPCell(new Paragraph(new Chunk(es.getNom().toString())));

			PdfPCell cell4 = new PdfPCell(new Paragraph(new Chunk(es.getResultat())));
			
			resTable.addCell(cell3);
			resTable.addCell(cell31);
			resTable.addCell(cell4);
		}
		
		document.add(resTable);
		document.newPage();

		Paragraph EssaisTitre = new Paragraph(new Chunk("4. Essais", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		EssaisTitre.setSpacingAfter(20);
		document.add(EssaisTitre);
		Integer nbreTable = 0;
		Boolean sautDePage = false;
		for (EssaiAux es : essais) {
			
			nbreTable++;
			PdfPTable esTable = new PdfPTable(2);
			esTable.setWidths(new float[] { 1f, 3f });

			PdfPCell cell11 = new PdfPCell(new Paragraph(new Chunk("N°")));
			cell11.setBackgroundColor(new Color(46, 127, 238));

			PdfPCell cell12 = new PdfPCell(new Paragraph(new Chunk(es.getNumero().toString())));
			cell12.setBackgroundColor(new Color(46, 127, 238));

			PdfPCell cell13 = new PdfPCell(new Paragraph("Essai"));

			PdfPCell cell14 = new PdfPCell(new Paragraph(es.getNom()));

			PdfPCell cell15 = new PdfPCell(new Paragraph("Domaine"));

			PdfPCell cell16 = new PdfPCell(new Paragraph(es.getDomaine()));

			PdfPCell cell17 = new PdfPCell(new Paragraph("Statut"));
			PdfPCell cell18 = new PdfPCell(new Paragraph(es.getStatut()));

			PdfPCell cell19 = new PdfPCell(new Paragraph("Résultat"));
			PdfPCell cell20 = new PdfPCell(new Paragraph(es.getResultat()));

			esTable.addCell(cell11);
			esTable.addCell(cell12);
			esTable.addCell(cell13);
			esTable.addCell(cell14);
			esTable.addCell(cell15);
			esTable.addCell(cell16);
			esTable.addCell(cell17);
			esTable.addCell(cell18);
			esTable.addCell(cell19);
			esTable.addCell(cell20);
			int j = 1;

			esTable.setSpacingAfter(20);
			document.add(esTable);

			List<SequenceAux> sequences = es.getSequences();

			Paragraph sequenceTitre = new Paragraph(
					new Chunk("    Séquences", FontFactory.getFont(FontFactory.TIMES_ROMAN, 18)));
			sequenceTitre.setSpacingAfter(30);
			document.add(sequenceTitre);

			int k = 1;

			for (SequenceAux s : sequences) {

				if (writer.getPageNumber() == 3) {

					Paragraph margeSup2 = new Paragraph(
							new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
					margeSup2.setAlignment(Element.ALIGN_CENTER);
					margeSup2.setSpacingAfter(30);
					document.add(margeSup2);

				}

				LocalDateTime debut = s.getDebut();
				LocalDateTime fin = s.getFin();
				Duration duration = Duration.between(debut, fin);
				Long dureeLongHours = duration.toHours();
				Long dureeLongMins = duration.toMinutes();
				String duree = null;

				if (dureeLongHours < 1) {

					duree = Long.toString(dureeLongMins) + "min";
				} else {

					duree = Long.toString(dureeLongHours) + "h";
				}

				PdfPCell cell21 = new PdfPCell(new Paragraph("N°"));
				cell21.setBackgroundColor(new Color(17, 142, 46));

				PdfPCell cell22 = new PdfPCell(
						new Phrase(String.valueOf(k), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				cell22.setBackgroundColor(new Color(17, 142, 46));
				
				nbreTable++;
				PdfPTable seqTable = new PdfPTable(2);
				seqTable.setWidths(new float[] { 1, 3f });

				seqTable.addCell(cell21);
				seqTable.addCell(cell22);

				seqTable.addCell(new Phrase("Niveau", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase(s.getNiveau(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase("Début", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase(s.getDebutText(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase("Fin", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase(s.getFinText(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase("Durée", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase(duree, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase("Profil", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase(s.getProfil(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase("Commentaire", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase(s.getCommentaire(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase("Statut", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase(s.getActif(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase("Résultat", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
				seqTable.addCell(new Phrase(s.getAvis(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));

				seqTable.setSpacingAfter(20);
				document.add(seqTable);
				k++;
				
				if(nbreTable == 3) {
					
					document.newPage();
					Paragraph margeSup2 = new Paragraph(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
					margeSup2.setAlignment(Element.ALIGN_CENTER);
					margeSup2.setSpacingAfter(-20);
					document.add(margeSup2);
					nbreTable = 0;
					sautDePage = true;
				}
			}
			
			if(!sautDePage) {
				
				document.newPage();
			}
			
			nbreTable = 0;
			Paragraph margeSup2 = new Paragraph(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
			margeSup2.setAlignment(Element.ALIGN_CENTER);
			margeSup2.setSpacingAfter(-20);
			document.add(margeSup2);
			sautDePage = false;

		}

		Paragraph avisTitre = new Paragraph(new Chunk("5. Avis", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24)));
		avisTitre.setSpacingAfter(20);
		document.add(avisTitre);

		Paragraph avis = new Paragraph(new Chunk(rapport.getAvis(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
		avis.setSpacingAfter(20);
		document.add(avis);

	}

}
