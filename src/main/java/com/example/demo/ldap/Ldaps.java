package com.example.demo.ldap;

import javax.naming.*;
import javax.naming.directory.*;

import org.springframework.stereotype.Component;

import java.util.Hashtable;
 
/**
 * Demonstrates how to create an initial context to an LDAP server
 * using an LDAPS URL. For this example to work, JSSE must be installed and
 * configured, and the issuer of the LDAP server's certificate must 
 * be in the JSSE trust store.
 *
 * usage: java Ldaps
 */

@Component
public class Ldaps {
    public static void getLdap() {
    // Set up environment for creating initial context
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
 
    // Specify LDAPS URL
    env.put(Context.PROVIDER_URL, "ldaps://192.168.0.207/cn=admin");
 
    // Authenticate as S. User and password "mysecret"
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, "cn=amin, dc=example, dc=com");
    env.put(Context.SECURITY_CREDENTIALS, "admin");
 
    System.out.println("env---? " + env);    	
    
    
    try {
        // Create initial context
        DirContext ctx = new InitialDirContext(env);
 
        System.out.println(ctx.lookup("cn=ivs"));
 
        // ... do something useful with ctx
 
        // Close the context when we're done
        ctx.close();
    } catch (NamingException e) {
        e.printStackTrace();
    }
    }
}