package com.example.demo.ldap.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ldap.User;
import com.example.demo.ldap.mapper.UserMapper;

@Service
public class UserService {
	@Autowired
	UserMapper um;
	
	public List<User> findAll() throws Exception {
		return um.getAllUserList();
	}

	public List<User> findOuUser(User vo) throws Exception {
		return um.getInOuUserList(vo);
	}
	
	public List<User> getOuList() throws Exception {
		return um.getOuList();
	}

	public int saveOu(List<User> vo) throws Exception {
		System.out.println("service--");
		for (int i=0;i<vo.size();i++) {
			System.out.println(vo.get(i).toString());
			System.out.println(vo.get(i).getOu());

		}
		return um.saveOu(vo);
	}
}
