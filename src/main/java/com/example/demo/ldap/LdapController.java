package com.example.demo.ldap;

import javax.naming.NamingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/ldap")
public class LdapController {
		@RequestMapping(value="")
		public String showldapserverview() throws NamingException{
	    System.out.println("-----ldap controller-----");    
	    return "/ldap/view";
	}
		@RequestMapping(value="/connect")
		public String connectionTest(User vo) throws NamingException{
			System.out.println("-----login to admin connect-----");   
		    LDAPConnection con = new LDAPConnection();
		    con.connection();

		    con.searchAll();	    
		
			return "redirect:/ldap";
		}

		@RequestMapping(value="/searchPerson")
		public String searchProc(User vo, Model model) throws NamingException{
	
			System.out.println("-----search to org user-----");    
			System.out.println("search user : " + vo.toString());
		
		    LDAPConnection con = new LDAPConnection();
		    
		    System.out.println("search user 하위에 존재하는 계정 정보 서칭");
		    con.connection();
		    String ret =""; 
		    ret = con.searchUsers(vo);
			System.out.println("ret : "+ret);    

		//    model.addAttribute("");
		    
			System.out.println("-----end-----");    

	    return "redirect:/ldap";
	}
		
		@RequestMapping(value="/login")
		public String authLogin(User vo) throws NamingException{
	
			System.out.println("-----login to authuser-----");    
			System.out.println("login user : " + vo.toString());
		
		    LDAPConnection con = new LDAPConnection();
		    
		    System.out.println("login user 하위에 존재하는 계정 정보 서칭");
		    con.connection();
		    
			System.out.println("-----end-----");    

	    return "redirect:/ldap";
	}
		
		@RequestMapping(value="/saveOrg")
		public String saveOrg(User vo) throws NamingException{
	
			System.out.println("-----save to org entry-----");    
			System.out.println("save org names : " + vo.toString());
		
		    LDAPConnection con = new LDAPConnection();
		    con.connection();
		   
			System.out.println("-----end-----");    

	    return "redirect:/ldap";
	}

		@RequestMapping(value="/saveUser")
		public String saveUser(User vo) throws NamingException{
	
			System.out.println("-----save to user entry-----");    
			System.out.println("save user : " + vo.toString());
		
		    LDAPConnection con = new LDAPConnection();
		    con.connection();
		   
			System.out.println("-----end-----");    

	    return "redirect:/ldap";
	}
}
