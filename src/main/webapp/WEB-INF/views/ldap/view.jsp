<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
	<title>JNDI ldap Test</title> 
</head> 

<script src="/plugins/jquery/jquery.min.js"></script>

<body> 
	<h2>Hello ldap!</h2> 
	<div>ldap 페이지 입니다</div> 
	<br>
	<div>
		<button type="button" class="btn btn-primary gray" onclick="location.href='/ldap/connect'">connect</button> to ldap server
	</div>
	<br><br>
	<form id="frm" name="frm" method="post">
		<div>
			<p>회사의 유저 찾기</p>
			<input type="text" name="ou" id="ou" value="testGroup" placeholder="회사를 입력헤주세요"><br>
			<input type="text" name="cn1" id="cn" placeholder="이름을 입력헤주세요">
			<button type="button" id="btnSearchOrgPerson">Search</button>
		
			<p>회사의 사람들 찾기 - 하위 엔트리 출력</p>
			<input type="text" name="ou2" id="ou" value="testGroup" placeholder="회사를 입력헤주세요"><br>
			<button type="button" id="btnSearchOrgPeople">Search</button>
		
			<br><br>
			<p>특정 유저로 로그인 - 하위 엔트리 출력</p>
			<input type="text" name="cn2" id="cn" placeholder="이름을 입력헤주세요"><br>
			<input type="text" name="pw" id="pw" placeholder="비밀번호를 입력헤주세요">
			<button type="button" id="btnLogin" >Login</button>

			<br><br>
			<p>회사 추가</p>
			<input type="text" name="cn2" id="cn" placeholder="이름을 입력헤주세요"><br>
			<button type="button" id="btnSaveOrg" >add</button>

			<br><br>
			<p>유저 추가</p>
			<select name="ou">
					<option value="">--회사를 선택하세요--</option>
					<option value="ivs">ivs</option>
					<option value="testGroup">testGroup</option>

<%-- 					<c:forEach var="item" items="${tagList}" varStatus="status">
						<option value="${item.org}">${item.org}</option>
					</c:forEach>
 --%>
			</select><br>
			<input type="text" name="sn" id="sn" value="lee" placeholder="성을 입력헤주세요"><br>
			<input type="text" name="cn" id="cn" value="eee" placeholder="이름을 입력헤주세요"><br>

			<button type="button" id="btnSaveUser" >add</button>

		</div>
	</form>
</body> 

<script type="text/javascript">
$(function() {
	$("#btnSearchOrgPerson").on("click", fnSearchOrgPerson);
	$("#btnSearchOrgPeople").on("click", fnSearchOrgPeople);
	$("#btnLogin").on("click", fnLogin);
	$("#btnSaveOrg").on("click", fnSaveOrg);
	$("#btnSaveUser").on("click", fnSaveUser);
});

function fnSearchOrgPerson() {
	document.frm.action = "/ldap/searchPerson";
	document.frm.submit();
}

function fnSearchOrgPeople() {
	document.frm.action = "/ldap/searchPeople";
	document.frm.submit();
}

function fnLogin() {
	document.frm.action = "/ldap/login";
	document.frm.submit();
}

function fnSaveOrg(){
	document.frm.action = "/ldap/saveOrg";
	document.frm.submit();	
}

function fnSaveUser(){
	document.frm.action = "/ldap/saveUser";
	document.frm.submit();
}

</script>
</html>
