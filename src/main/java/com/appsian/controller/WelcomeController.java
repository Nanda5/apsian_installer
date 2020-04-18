package com.appsian.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appsian.model.Installer;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("installForm", new Installer());
		return "welcome"; // view
	}

	@PostMapping("install")
	public String insataller(@Valid Installer installer, BindingResult result, Model model) {
		System.out.println("coming");
		System.out.println(installer);
		model.addAttribute("installForm", installer);
		if (result.hasErrors()) {
			model.addAttribute("error", "Please Fill the fields");
			return "welcome";
		}
		model.addAttribute("message", "Execution Trigered Successfully");
		return "welcome";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		return "redirect:/";
	}
}