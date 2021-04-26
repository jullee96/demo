package com.example.demo.ldap.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.ldap.User;

@Mapper
public interface UserMapper {
	public List<User> getAllUserList() throws Exception;
	public List<User> getInOuUserList(User vo) throws Exception;

	public List<User> getOuList() throws Exception;
	
	public int saveUser(User vo);
	public int deleteUser(User vo);
	
	public int saveOu(List<User> vo);

}