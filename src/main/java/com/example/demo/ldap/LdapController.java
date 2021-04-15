package com.example.demo.ldap;

import javax.naming.NamingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LdapController {
		@RequestMapping(value="/ldap")
		public String showldapserverview() throws NamingException{
	
	    System.out.println("-----ldap controller-----");    
	 //   LDAPConnection con = new LDAPConnection();
	 //   con.addUser();
	    return "/ldap/view";
	}

		@RequestMapping(value="/login")
		public String searchProc(User vo) throws NamingException{
	
			System.out.println("-----login to authuser-----");    
			System.out.println("login user : " + vo.toString());
		
		    LDAPConnection con = new LDAPConnection();
		    
		    System.out.println("login user 하위에 존재하는 계정 정보 서칭");
		    con.connection();
		    con.searchUsers(vo);
		    
			System.out.println("-----end-----");    

	    return "redirect:/ldap";
	}

}
