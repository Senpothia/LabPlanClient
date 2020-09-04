package com.michel.lab.view.pdf;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Phaser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.michel.lab.constants.Constants;
import com.michel.lab.model.EchantillonAux;
import com.michel.lab.model.EssaiAux;
import com.michel.lab.model.RapportAux;

@Component("rapport")
public class RapportViewPdf extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		RapportAux rapport = (RapportAux) model.get("rapport");
		List<EchantillonAux> echantillons = (List<EchantillonAux>) model.get("echantillons");
		List<EssaiAux> essais = (List<EssaiAux>) model.get("essais");
		
	
		PdfPTable table = new PdfPTable(1);
		table.addCell("Qualification");
		document.add(table);
		
	}

}
