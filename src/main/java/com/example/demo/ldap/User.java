package com.example.demo.ldap;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private String cn;
	private String sn;
	private String pw;
	private String ou;
	private String mail;
	private String uid;
	private String gid;
	private String userpassword;
	private String objectClass[];
	private String givenName;
	
}
