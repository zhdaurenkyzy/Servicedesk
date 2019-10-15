<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/customTag.tld" prefix="custom"%>
<html>
<head><link rel="shortcut icon" href="C:/Users/Жансая/IdeaProjects/servicedesk/favicon.ico" type="image/x-icon"/></head>

</head>
<body>
<custom:customIf displayBody="true">
		<p>Error</p>
	</custom:customIf>


	<c:if test="${message == 1}"><fmt:message key = "key.incorrectFormat" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 2}"><fmt:message key = "key.wrongLoginOrPass" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 3}"><fmt:message key = "key.groupAlreadyExists" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 4}"><fmt:message key = "key.projectAlreadyExists" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 5}"> <fmt:message key = "key.accountAlreadyExist" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 6}"> <fmt:message key = "key.accessDenied" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 7}"> <fmt:message key = "key.passwordIncorrectFormat" bundle = "${resourceBundle}"/></c:if>
    <c:if test="${message == 8}"> <fmt:message key = "key.mailIncorrectFormat" bundle = "${resourceBundle}"/></c:if>
    <c:if test="${message == 9}"> <fmt:message key = "key.loginIncorrectFormat" bundle = "${resourceBundle}"/></c:if>
</body>
</html>


