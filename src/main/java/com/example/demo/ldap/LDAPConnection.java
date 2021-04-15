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
//        public static void main() throws NamingException {
//            LDAPConnection con = new LDAPConnection();
//            con.connection();
//            System.out.println("start");
//    //        con.searchUsers();
//            
//            System.out.println("----------");
//            con.searchGroups();
//            
//            System.out.println("----------");
//      //      con.getAllUsers();
//            
//            System.out.println("----------");
//      //      con.addUser();
//            
//            System.out.println("end");
//        }

    public void connection(){
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.0.207:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=example,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "admin");
        try {
                dc = new InitialDirContext(env);
                System.out.println("success");
        } catch (AuthenticationException ex) {
			System.out.println(ex.getMessage());
		} catch (NamingException e) {
            e.printStackTrace();
        }
    }

//    public void searchUsers() throws NamingException {
//        String searchFilter = "(&(objectClass=inetOrgPerson))";
//		String[] reqAtt = { "cn", "sn","uid" };
//		SearchControls controls = new SearchControls();
//		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//		controls.setReturningAttributes(reqAtt);
//
//		NamingEnumeration users = dc.search("cn=ivs,dc=example,dc=com", searchFilter, controls);
//
//		SearchResult result = null;
//		while (users.hasMore()) {
//			result = (SearchResult) users.next();
//			Attributes attr = result.getAttributes();
//			String name = attr.get("cn").get(0).toString();
//			System.out.println(attr.get("cn"));
//			System.out.println(attr.get("sn"));
//			System.out.println(attr.get("uid"));
//			System.out.println("");
//            
//		}
//
//	}

    
    public void searchUsers(User vo) throws NamingException {
        String searchFilter = "(&(objectClass=inetOrgPerson))";
		String[] reqAtt = { "cn", "sn","uid" };
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(reqAtt);

		NamingEnumeration users = dc.search("cn="+vo.getCn()+",dc=example,dc=com", searchFilter, controls);

		SearchResult result = null;
		while (users.hasMore()) {
			result = (SearchResult) users.next();
			Attributes attr = result.getAttributes();
			String name = attr.get("cn").get(0).toString();
			System.out.println(attr.get("cn"));
			System.out.println(attr.get("sn"));
			System.out.println(attr.get("uid"));
			System.out.println("");
            
		}

	}

    
    public void searchGroups() throws NamingException {
        String searchFilter = "(&(objectClass=inetOrgPerson))";
		String[] reqAtt = { "cn", "sn","uid" };
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(reqAtt);

		NamingEnumeration users = dc.search("dc=example,dc=com", searchFilter, controls);

		SearchResult result = null;
		while (users.hasMore()) {
			result = (SearchResult) users.next();
			Attributes attr = result.getAttributes();
			String name = attr.get("cn").get(0).toString();
			System.out.println(attr.get("cn"));
			System.out.println(attr.get("sn"));
			System.out.println(attr.get("uid"));
			System.out.println("");
            
		}

	}

    public void getAllUsers() throws NamingException {
		String searchFilter = "(objectClass=inetOrgPerson)";
		String[] reqAtt = { "cn", "sn" };
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(reqAtt);

		NamingEnumeration users = dc.search("dc=example,dc=com", searchFilter, controls);

		SearchResult result = null;
		while (users.hasMore()) {
			result = (SearchResult) users.next();
			Attributes attr = result.getAttributes();
			String name = attr.get("cn").get(0).toString();
			System.out.println(attr.get("cn"));
			System.out.println(attr.get("sn"));
			System.out.println("");

		}

	}

	public void addUser(User vo) {
		Attributes attributes = new BasicAttributes();
		Attribute attribute = new BasicAttribute("objectClass");
		attribute.add("inetOrgPerson");

		attributes.put(attribute);
		// user details
	//	attributes.put("sn", vo.getSn());
	
		try {
			dc.createSubcontext("cn="+ vo.getCn()+",ou=invesume,dc=example,dc=com", attributes);
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
			dc.modifyAttributes("cn="+groupName+",ou=groups,ou=system", mods);
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

    /* use this to authenticate any existing user */
//	public static boolean authUser(String username, String password)
	public static boolean authUser(User vo)
	{
		try {
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://192.168.0.207:389");
			env.put(Context.SECURITY_PRINCIPAL, "cn="+vo.getCn()+",dc=example,dc=com");  //check the DN correctly
			env.put(Context.SECURITY_CREDENTIALS, vo.getPw());
			DirContext con = new InitialDirContext(env);
			System.out.println("authUser success");
			//con.close();
			
			return true;
		}catch (Exception e) {
			System.out.println("failed:: "+e.getMessage());
			return false;
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