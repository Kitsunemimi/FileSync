package com.fdmgroup.filesync.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.filesync.Synchronizer;

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

	@RequestMapping(value = "/sync/local", method = RequestMethod.POST)
	public String processDirs(@RequestParam(value = "dir1", required = true) String dir1,
							  @RequestParam(value = "dir2", required = true) String dir2) {
		Synchronizer sync = new Synchronizer(dir1, dir2);
		
		try {
			sync.getChanges();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "dir-select";
	}
}
