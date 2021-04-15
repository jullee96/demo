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
		public String searchProc(User vo) throws Exception{
	
			System.out.println("-----login to authuser-----");    
			System.out.println("login user : " + vo.toString());
		
		    LDAPConnection con = new LDAPConnection();
		    
		    System.out.println("aa");
		    con.connection();
		    
	//	    con.authUser(vo);
		    con.getAllUsers();
		    System.out.println("bb");
	//	    con.searchUsers(vo);
		    
			System.out.println("-----end-----");    

	    return "redirect:/ldap";
	}

}
