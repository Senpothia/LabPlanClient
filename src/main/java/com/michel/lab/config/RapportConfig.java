package com.michel.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lowagie.text.Document;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;

@Configuration
public class RapportConfig {
	
	
	@Bean
	public Document makeDocument() {

		 Document document  = new Document();
		 HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
		 HeaderFooter footer = new HeaderFooter(new Phrase("This is page "), new Phrase("."));
		 document.setHeader(header);
		 document.setFooter(footer);
		return document;
	}

}
