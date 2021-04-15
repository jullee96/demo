<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html> <html lang="ko"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
	<title>View Test Page</title> 
</head> 
<body> 
	<h2>Hello ldap!</h2> 
	<div>ldap 페이지 입니다</div> 
	<form action="/login" method="post">
	<div>
		<input type="text" name="cn" id="cn" placeholder="이름을 입력헤주세요"><br>
		<input type="text" name="pw" id="pw" placeholder="비밀번호를 입력헤주세요">
		<button type="submit">Login</button>
	</div>
	</form>
	<div>
	</div>
</body> 
</html>
