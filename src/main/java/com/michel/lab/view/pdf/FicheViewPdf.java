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
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.michel.lab.model.FicheAux;
import com.michel.lab.model.RapportAux;

@Component("fiche")
public class FicheViewPdf extends AbstractPdfView {

	private static final Phrase HeaderFooter = null;

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {

		super.buildPdfMetadata(model, document, request);

		FicheAux fiche = (FicheAux) model.get("fiche");

		try {
			document.setMargins(30, 30, 30, 30);
			Image entete = Image.getInstance("src\\main\\resources\\static\\images\\bandeau_entreprise1.png");
			entete.scaleAbsolute(535, 100);
			HeaderFooter header = new HeaderFooter(new Phrase(new Chunk(entete, 0, -35)), false);
			HeaderFooter footer = new HeaderFooter(
					new Phrase(String.valueOf(fiche.getNumero()), FontFactory.getFont(FontFactory.TIMES)), false);
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
		
		
		FicheAux fiche = (FicheAux) model.get("fiche");

		
		 Paragraph titre = new Paragraph( new Chunk(" ",
		 FontFactory.getFont(FontFactory.TIMES_ROMAN, 28)));
		// titre.setAlignment(Element.ALIGN_CENTER); titre.setSpacingAfter(10);
		 document.add(titre);
		 

		PdfPTable table = new PdfPTable(8);
		table.setWidths(new float[] { 1, 1, 1, 1, 1, 1, 1, 1 });
		table.setWidthPercentage(100);
		PdfPCell cellTitre = null;
	//	cell = new PdfPCell(new Phrase("Fiche d'anomalie technique"));
		cellTitre = new PdfPCell(new Phrase("Fiche d'anomalie technique",
				 FontFactory.getFont(FontFactory.TIMES_BOLD, 28)));
		
		cellTitre.setBackgroundColor(new Color( 153, 204, 255 ));
		cellTitre.setColspan(8);
		cellTitre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellTitre);
		
		document.add(table);

	}

}
