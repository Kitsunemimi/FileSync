package com.fdmgroup.filesync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SyncController {
	
	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	public String showNetSelect(Model model) {
		return "net-select";
	}
	
	@RequestMapping(value = "/sync/local", method = RequestMethod.GET)
	public String localDirSelect(Model model) {
		
		
		return "dir-select";
	}

}
