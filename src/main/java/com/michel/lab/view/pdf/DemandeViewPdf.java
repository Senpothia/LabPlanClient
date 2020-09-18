package com.michel.lab.view.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.michel.lab.model.DemandeAux;
import com.michel.lab.model.FicheAux;

@Component("demande2")
public class DemandeViewPdf extends AbstractPdfView {

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {

		super.buildPdfMetadata(model, document, request);

		DemandeAux demande = (DemandeAux) model.get("demande");

		try {
			document.setMargins(30, 30, 30, 30);
			Image entete = Image.getInstance("src\\main\\resources\\static\\images\\bandeau_entreprise1.png");
			
			// Image.getInstance("src\\main\\resources\\static\\images\\BandeauSup1.jpg");
			entete.scaleAbsolute(535, 100);
			HeaderFooter header = new HeaderFooter(new Phrase(new Chunk(entete, 0, -35)), false);
			HeaderFooter footer = new HeaderFooter(
					new Phrase("Fiche de demande d'essai", FontFactory.getFont(FontFactory.TIMES)), false);
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

		DemandeAux demande = (DemandeAux) model.get("demande");

		Paragraph titre = new Paragraph(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 28)));
		document.add(titre);

		PdfPTable table = new PdfPTable(2);
		table.setWidths(new float[] { 1, 2.5f });
		table.setWidthPercentage(100);
		PdfPCell cellTitre = null;

		cellTitre = new PdfPCell(
				new Phrase("Fiche de demande d'essais", FontFactory.getFont(FontFactory.TIMES_BOLD, 28)));

		cellTitre.setBackgroundColor(new Color(153, 204, 255));
		cellTitre.setColspan(8);
		cellTitre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cellTitre.setPadding(15);
		table.addCell(cellTitre);

		PdfPCell cellSubTitre2 = new PdfPCell(
				new Phrase("Partie demandeur", FontFactory.getFont(FontFactory.TIMES_BOLD, 16)));

		cellSubTitre2.setBackgroundColor(new Color(244, 176, 131));
		cellSubTitre2.setColspan(8);
		cellSubTitre2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellSubTitre2);

		////
		PdfPCell cellCode = new PdfPCell(new Phrase("Code:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellCode.setColspan(1);
		cellCode.setBackgroundColor(new Color(244, 176, 131));
		//cellCode.setMinimumHeight(40);
		cellCode.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellCode);

		PdfPCell cellCode2 = new PdfPCell(new Phrase(demande.getProduit(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellCode2.setColspan(1);
		cellCode2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellCode2);

		/////

		PdfPCell cellEchantillon = new PdfPCell(
				new Phrase("Echantillon:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellEchantillon.setColspan(1);
		cellEchantillon.setBackgroundColor(new Color(244, 176, 131));
		cellEchantillon.setMinimumHeight(60);
		cellEchantillon.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellEchantillon);

		PdfPCell cellEchantillon2 = new PdfPCell(
				new Phrase(demande.getEchantillon(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellEchantillon2.setColspan(1);
		cellEchantillon2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellEchantillon2);

		/////

		PdfPCell cellObjectif = new PdfPCell(new Phrase("Objectif:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellObjectif.setColspan(1);
		cellObjectif.setBackgroundColor(new Color(244, 176, 131));
		cellObjectif.setMinimumHeight(60);
		cellObjectif.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellObjectif);

		PdfPCell cellObjectif2 = new PdfPCell(
				new Phrase(demande.getObjectif(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellObjectif2.setColspan(1);
		cellObjectif2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellObjectif2);

		//////

		PdfPCell cellEssai = new PdfPCell(
				new Phrase("Essais demandés:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellEssai.setColspan(1);
		cellEssai.setBackgroundColor(new Color(244, 176, 131));
		cellEssai.setMinimumHeight(60);
		cellEssai.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellEssai);

		PdfPCell cellEssai2 = new PdfPCell(new Phrase(demande.getEssai(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellEssai2.setColspan(1);
		cellEssai2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellEssai2);

		/////

		PdfPCell cellAuxiliaire = new PdfPCell(
				new Phrase("Eléments auxiliaires:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellAuxiliaire.setColspan(1);
		cellAuxiliaire.setBackgroundColor(new Color(244, 176, 131));
		cellAuxiliaire.setMinimumHeight(60);
		cellAuxiliaire.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellAuxiliaire);

		PdfPCell cellAuxiliare2 = new PdfPCell(
				new Phrase("Elements auxiliaires", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellAuxiliare2.setColspan(1);
		cellAuxiliare2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellAuxiliare2);

		/////

		PdfPCell cellUrgence = new PdfPCell(
				new Phrase("Degré d'urgence:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellUrgence.setColspan(1);
		cellUrgence.setBackgroundColor(new Color(244, 176, 131));
		//cellUrgence.setMinimumHeight(40);
		cellUrgence.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellUrgence);

		PdfPCell cellUrgence2 = new PdfPCell(
				new Phrase("Degré d'urgence", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellUrgence2.setColspan(1);
		cellUrgence2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellUrgence2);

		/////

		PdfPCell cellDemandeur = new PdfPCell(
				new Phrase("Nom du demandeur:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellDemandeur.setColspan(1);
		cellDemandeur.setBackgroundColor(new Color(244, 176, 131));
		//cellDemandeur.setMinimumHeight(40);
		cellDemandeur.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDemandeur);

		PdfPCell cellDemandeur2 = new PdfPCell(
				new Phrase(demande.getDemandeur(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellDemandeur2.setColspan(1);
		cellDemandeur2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDemandeur2);

		/////

		PdfPCell cellDate = new PdfPCell(new Phrase("Date de la demande:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellDate.setColspan(1);
		cellDate.setBackgroundColor(new Color(244, 176, 131));
		//cellDate.setMinimumHeight(40);
		cellDate.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDate);

		PdfPCell cellDate2 = new PdfPCell(new Phrase("date", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellDate2.setColspan(1);
		cellDate2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDate2);

		/////

		PdfPCell cellSubTitre3 = new PdfPCell(
				new Phrase("Partie laboratoire", FontFactory.getFont(FontFactory.TIMES_BOLD, 16)));

		cellSubTitre3.setBackgroundColor(new Color(153, 204, 255));
		cellSubTitre3.setColspan(8);
		cellSubTitre3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellSubTitre3);

		/////

		PdfPCell cellNumero = new PdfPCell(
				new Phrase("Numéro de demande:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellNumero.setColspan(1);
		cellNumero.setBackgroundColor(new Color(153, 204, 255));
		//cellNumero.setMinimumHeight(40);
		cellNumero.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellNumero);

		PdfPCell cellNumero2 = new PdfPCell(new Phrase("Numero", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellNumero2.setColspan(1);
		cellNumero2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellNumero2);

		/////

		PdfPCell cellAvis = new PdfPCell(
				new Phrase("Avis du laboratoire:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellAvis.setColspan(1);
		cellAvis.setBackgroundColor(new Color(153, 204, 255));
		cellAvis.setMinimumHeight(60);
		cellAvis.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellAvis);

		PdfPCell cellAvis2 = new PdfPCell(new Phrase(demande.getAvis(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellAvis2.setColspan(1);
		cellAvis2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellAvis2);

		/////

		PdfPCell cellObservations = new PdfPCell(
				new Phrase("Observations:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellObservations.setColspan(1);
		cellObservations.setBackgroundColor(new Color(153, 204, 255));
		cellObservations.setMinimumHeight(60);
		cellObservations.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellObservations);

		PdfPCell cellObservations2 = new PdfPCell(
				new Phrase("observations", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellObservations2.setColspan(1);
		cellObservations2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellObservations2);

		/////

		PdfPCell cellTech = new PdfPCell(
				new Phrase("Essais réalisés par:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellTech.setColspan(1);
		cellTech.setBackgroundColor(new Color(153, 204, 255));
		//cellTech.setMinimumHeight(40);
		cellTech.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellTech);

		PdfPCell cellTechs2 = new PdfPCell(new Phrase("nom du tech", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellTechs2.setColspan(1);
		cellTechs2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellTechs2);

		/////

		PdfPCell cellDateRapport = new PdfPCell(new Phrase("Date:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellDateRapport.setColspan(1);
		cellDateRapport.setBackgroundColor(new Color(153, 204, 255));
		//cellDateRapport.setMinimumHeight(40);
		cellDateRapport.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDateRapport);

		PdfPCell cellDateRapport2 = new PdfPCell(
				new Phrase("date du rapport", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellDateRapport2.setColspan(1);
		cellDateRapport2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDateRapport2);

		/////

		PdfPCell cellNumRapport = new PdfPCell(new Phrase("Numéro du rapport:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellNumRapport.setColspan(1);
		cellNumRapport.setBackgroundColor(new Color(153, 204, 255));
	//	cellNumRapport.setMinimumHeight(40);
		cellNumRapport.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellNumRapport);

		PdfPCell cellNumRapport2 = new PdfPCell(
				new Phrase("Numéro du rapport", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellNumRapport2.setColspan(1);
		cellNumRapport2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellNumRapport2);

		document.add(table);

	}

}
