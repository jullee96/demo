package com.example.demo.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


public class LDAPConnection {

    private DirContext dc = null; 
    /* connection */
    
    public void connection(){
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.0.207:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=example,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "admin");
        try {
                dc = new InitialDirContext(env);
                System.out.println("connection success to ldap server...");
        } catch (AuthenticationException ex) {
			System.out.println("connection fail to ldap server..."+ex.getMessage());
		} catch (NamingException e) {
            e.printStackTrace();
        }
    }
    
    /* use this to authenticate any existing user */
	public static boolean authUser(User vo)
	{
		try {
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://192.168.0.207:389");
			env.put(Context.SECURITY_PRINCIPAL, "cn="+vo.getCn()+",dc=example,dc=com");  //check the DN correctly
			env.put(Context.SECURITY_CREDENTIALS, vo.getPw());
			DirContext con = new InitialDirContext(env);
			
			System.out.println("authUser success"+": cn="+vo.getCn()+",dc=example,dc=com");
			//con.close();
			
			return true;
		}catch (Exception e) {
			System.out.println("failed:: "+e.getMessage());
			return false;
		}
	}
	

    ////* search method *////

	public void searchAll() throws NamingException {
		String searchFilter = "(objectClass=*)";
		SearchControls searchScope = new SearchControls();
		searchScope.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration baseDn = dc.search("dc=example,dc=com", searchFilter, searchScope);

		SearchResult result = null;

		while (baseDn.hasMore()) {
			result = (SearchResult) baseDn.next();
			Attributes attr = result.getAttributes();

			System.out.println("attr : " +attr);

//			System.out.println(attr.get("cn"));
//			System.out.println(attr.get("sn"));
//			System.out.println(attr.get("dn"));

			System.out.println("");

		}

	}

    
    public String searchUsers(User vo) throws NamingException {
    	String searchFilter = "(&(objectClass=*)(cn="+vo.getCn()+"))";
    	String[] reqAtt = { "cn", "sn","uid", "mail", "givenName" ,"objectClass" };
		SearchControls controls = new SearchControls();
		String resultStr = "";
		
		controls.setReturningAttributes(reqAtt);

    	NamingEnumeration baseDn = dc.search("ou="+vo.getOu()+",dc=example,dc=com",searchFilter, controls);
		String chkOu = "";
		String dn="ou="+vo.getOu()+",dc=example,dc=com";
    	SearchResult result = null;
    			
		while (baseDn.hasMore()) {
			result = (SearchResult)baseDn.next();
			Attributes attr = result.getAttributes();
			chkOu = attr.toString();
			System.out.println("dn : "+ dn);
			System.out.println(attr.get("mail"));
			System.out.println(attr.get("cn"));
			System.out.println(attr.get("sn"));
			System.out.println(attr.get("objectClass"));
			System.out.println(attr.get("uid"));
	
		}

//		String[] tmpOuSplit = chkOu.split(":");
//		
//		for (int i = 0; i < tmpOuSplit.length; i++) {
//			System.out.println("tmpOuSplit :" + tmpOuSplit[i]);
//		}
//
//		resultStr = tmpOuSplit[0].toString();
		return resultStr;

	}

    
    public String searchGroups(User vo) throws NamingException {
       String searchFilter = "(objectClass=inetOrgPerson)";
		String[] reqAtt = {  "cn", "sn","uid", "mail", "givenName" ,"objectClass" };
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(reqAtt);

		NamingEnumeration users = dc.search("ou="+vo.getOu()+",dc=example,dc=com", searchFilter, controls);
		String chkOu = "";
		String resultStr = "";

		SearchResult result = null;
		while (users.hasMore()) {
			
			result = (SearchResult) users.next();
			// 각 오브젝트의 dn출
			System.out.println("dn : " + result.getNameInNamespace());
			
			
//			Attributes attr = result.getAttributes();
//		//	String name = attr.get("cn").get(0).toString();
//			System.out.println(attr.get("cn"));
//			System.out.println(attr.get("sn"));
//	//		System.out.println(attr.get("uid"));
//			System.out.println("");
//            
		}

		String[] tmpOuSplit = chkOu.split(":");
		resultStr = tmpOuSplit[0].toString();

		return resultStr;

	}

    public void getAllUsers() throws NamingException {
		String searchFilter = "(objectClass=inetOrgPerson)";
		String[] reqAtt = { "cn", "sn" };
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(reqAtt);

		NamingEnumeration users = dc.search("cn=admin,dc=example,dc=com", searchFilter, controls);

		SearchResult result = null;
		while (users.hasMore()) {
			result = (SearchResult) users.next();
			System.out.println("result---" +result.toString());
			Attributes attr = result.getAttributes();
			System.out.println("get users....");

			String name = attr.get("cn").get(0).toString();

			System.out.println(attr.get("cn"));
			System.out.println(attr.get("sn"));
			System.out.println("");

		}

	}
    
    ////* add user method *////

	public void addUser(User vo) {
		Attributes attributes = new BasicAttributes();
		Attribute attribute = new BasicAttribute("objectClass");
		attribute.add("inetOrgPerson");
		attributes.put(attribute);
		// user details
		attributes.put("sn", vo.getSn());
	
		System.out.println("input cn? " + vo.getCn());
		try {
			dc.createSubcontext("cn="+ vo.getCn()+",ou="+vo.getOu()+",dc=example,dc=com", attributes);
			System.out.println("success");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	

	public void addUserToGroup(String username, String groupName)
	{
		ModificationItem[] mods = new ModificationItem[1];
		Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
		mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
		try {
			dc.modifyAttributes("ou="+groupName+",ou=groups,ou=system", mods);
			System.out.println("success");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	public void deleteUser(User vo)
	{
		try {
			dc.destroySubcontext("cn="+vo.getCn()+",ou=invesume,dc=example,dc=com");
			System.out.println("success");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void deleteUserFromGroup(String username, String groupName)
	{
		ModificationItem[] mods = new ModificationItem[1];
		Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
		mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
		try {
			dc.modifyAttributes("cn="+groupName+",ou=groups,ou=system", mods);
			System.out.println("success");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

    
	/* use this to update user password */
	public void updateUserPassword(String username, String password) {
		try {
			String dnBase=",ou=users,ou=system";
			ModificationItem[] mods= new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));// if you want, then you can delete the old password and after that you can replace with new password 
			dc.modifyAttributes("cn="+username +dnBase, mods);//try to form DN dynamically
			System.out.println("success");
		}catch (Exception e) {
			System.out.println("failed: "+e.getMessage());
		}
	}
}