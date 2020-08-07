package com.michel.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.michel.lab.proxy.MicroServiceLab;
import com.michel.lab.service.UserConnexion;

@Controller
@RequestMapping("/labplan/private/sequence")
public class SequenceController {
	
	@Autowired
	private MicroServiceLab microServiceLab;

	@Autowired
	private UserConnexion userConnexion;
	
	

}
