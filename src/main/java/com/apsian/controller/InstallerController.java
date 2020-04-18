package com.apsian.controller;

import java.awt.AWTException;
import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apsian.service.Installer;

@RestController
public class InstallerController {
	@GetMapping("/v1/check")
	public String check() throws IOException, InterruptedException, AWTException {
		Installer installer = new Installer();
        installer.appInstaller();
		return "Success";
	}
}
