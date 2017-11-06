package net.kitsunemimi.filesync.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.kitsunemimi.filesync.Synchronizer;

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
	public String processDirs(HttpServletRequest req, HttpServletResponse res,
			  @RequestParam(value = "dir1", required = true) String dir1,
			  @RequestParam(value = "dir2", required = true) String dir2) {
		Synchronizer sync;
		
		try {
			sync = new Synchronizer(dir1, dir2);
		} catch (IllegalArgumentException e) {
			//res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			req.setAttribute("errorMsg", "The requested directories are invalid/do not exist.");
			return "dir-select";
		}
		
		HttpSession session = req.getSession();
		
		try {
			session.setAttribute("changes", sync.getChanges());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "list-changes";
	}
}
