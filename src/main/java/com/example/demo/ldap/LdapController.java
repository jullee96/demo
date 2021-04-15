package com.example.demo.ldap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LdapController {
		@RequestMapping(value="/ldap")
		public String showldapserver(){
	
	    System.out.println("-----ldap controller-----");
	    
	    Ldaps test = new Ldaps();
//	    test.getLdap();
	    
	    return "/ldap/view";
	}
}
