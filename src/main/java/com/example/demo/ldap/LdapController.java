package com.example.demo.ldap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.ldap.service.UserService;

@Controller
@RequestMapping(value="/ldap")
public class LdapController {
	
	@Autowired
	UserService us;
	
		@RequestMapping(value="")
		public String showldapserverview() throws NamingException{
	    System.out.println("-----ldap controller-----");    
	    return "/ldap/view";
		}
		
		
		@RequestMapping(value="/connect")
		public String connectionTest(User vo) throws Exception{
			System.out.println("-----login to admin connect-----");   
		    LDAPConnection con = new LDAPConnection();
		    con.connection();

		    ArrayList <HashMap<String,String>> list = con.searchAll();	    
			
		    List <User> voList = new ArrayList<User>();
		    int ii=0;
		    for(int i=0;i<list.size();i++) {
		    	for( Entry<String, String> elem : list.get(i).entrySet() ){
		    		 System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
		    		 if(elem.getKey() == "ou") {
		    			 
		    			 System.out.println("elem "+ii+" : " + elem.getValue());
		    			 User tt= new User();
		    			 tt.setOu(elem.getValue());
		    			 voList.add(tt);
		    			 ii++;
		    		 }
		    	}
		    }
		    
		    
		    // db에 저장된 회사정보 불러오기
		    List <User> ouList = new ArrayList<User>();
		    ouList = us.getOuList();
		    
		    
		    for (User el : voList) {
		    	System.out.println("voList  " +el.toString());		    	
		    }
		   
		    
		    for (User el : ouList) {
		    	System.out.println("ouList  " +el.toString());		    	
		    }
		   
		    System.out.println("ddpd >> "+ Arrays.equals(voList.toArray(), ouList.toArray()));

		    if(Arrays.equals(voList.toArray(), ouList.toArray()) != true) {
		    	
		    Collection <User> ListA = voList;
		    Collection <User> ListB = ouList;
		    	
		      // 기존에가진 회사정보 중복값 삭제
		    ListA.removeAll(ListB);
		    
		    
		    for (User el : ListA) {
		    	System.out.println("ListA  " +el.toString());		    	
		    }
		       // 회사정보 저장
			    int ret = us.saveOu(voList);
		    
		       if (ret==1) {
			    	System.out.println("새로 불러든 회사 정보 저장");
			    }else {
			    	System.out.println("회사정보 저장 실패");
			    }
		    }else {
		    	
		    	System.out.println("새로 저장할 내용 없음");
		    }
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
		
		@RequestMapping(value="/searchPeople")
		public String searchGroup(User vo, Model model) throws NamingException{
	
			System.out.println("-----search to org user-----");    
			System.out.println("search user : " + vo.toString());
		
		    LDAPConnection con = new LDAPConnection();
		    
		    System.out.println("search user 하위에 존재하는 계정 정보 서칭");
		    con.connection();
		    String ret =""; 
		    ret = con.searchGroups(vo);
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
		    con.addUser(vo);
		   
			System.out.println("-----end-----");    

	    return "redirect:/ldap";
	}
}
