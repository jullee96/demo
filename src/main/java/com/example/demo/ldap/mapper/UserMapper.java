package com.example.demo.ldap.mapper;

import java.util.List;
import com.example.demo.ldap.User;

public interface UserMapper {
	public List<User> getAllUserList() throws Exception;
	public List<User> getInOuUserList(User vo) throws Exception;

	public int saveUser(User vo);
	public int deleteUser(User vo);
}