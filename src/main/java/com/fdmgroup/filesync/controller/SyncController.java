package com.fdmgroup.filesync.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping(value = "/sync/local/test", method = RequestMethod.POST)
	public String processDirs(HttpServletRequest request, @RequestParam(value = "dir1", required = true) String dir1,
							  @RequestParam(value = "dir2", required = true) String dir2) {
		System.out.println(dir1);
		System.out.println(dir2);
		
		
		return "dir-select";
	}
}
