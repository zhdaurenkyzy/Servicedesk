<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head><link rel="shortcut icon" href="C:/Users/Жансая/IdeaProjects/servicedesk/favicon.ico" type="image/x-icon"/></head>

</head>
<body>
	<p>Error</p>
	<c:if test="${message == 1}"><fmt:message key = "key.incorrectFormat" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 2}"><fmt:message key = "key.wrongLoginOrPass" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 3}"><fmt:message key = "key.groupAlreadyExists" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 4}"><fmt:message key = "key.projectAlreadyExists" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 5}"> <fmt:message key = "key.accountAlreadyExist" bundle = "${resourceBundle}"/></c:if>
	<c:if test="${message == 6}"> <fmt:message key = "key.accessDenied" bundle = "${resourceBundle}"/> </c:if>
</body>
</html>


