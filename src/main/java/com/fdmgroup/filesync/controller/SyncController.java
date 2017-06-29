package com.fdmgroup.filesync.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.filesync.Synchronizer;

@Controller
public class SyncController {
	
	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	public String showNetSelect() {
		return "net-select";
	}
	
	@RequestMapping(value = "/sync/local", method = RequestMethod.GET)
	public String localDirSelect() {
		return "dir-select";
	}

	@RequestMapping(value = "/sync/local", method = RequestMethod.POST)
	public String processDirs(HttpServletRequest request,
			  @RequestParam(value = "dir1", required = true) String dir1,
			  @RequestParam(value = "dir2", required = true) String dir2) {
		Synchronizer sync;
		
		try {
			sync = new Synchronizer(dir1, dir2);
		} catch (IllegalArgumentException e) {
			// TODO: return some error
			return "dir-select";
		}
		
		HttpSession session = request.getSession();
		
		try {
			session.setAttribute("changes", sync.getChanges());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "list-changes";
	}
}
