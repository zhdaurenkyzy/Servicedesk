<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${local}"/>
<fmt:setBundle basename = "lang" var = "resourceBundle" scope="session"/>

<html>
<head><link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		<style>
    <%@include file="/css/style.css"%>
    <%@include file="/css/media-queries.css"%>
</style>
	</head>
	<body>
		<div class="wrapper">
			<div class="header">
			<div class="row" align="right">
              <c:forEach var="language" items="${languages}">
                <form method="POST" action="setLanguage">
                 <input id="idField" type="hidden" name="page" value ="jsp/login.jsp">
                 <input id="idField" type="hidden" name="languageId" value = <c:out value="${language.getId()}"/>>
                 <input type="submit" value=<c:out value="${language.getName()}"/>>
                </form>
              </c:forEach>
            </div>
				<div class="headerContent">
					<div class="logo"><a href="/">Service<span class="pink">Desk</span><span class="gray"></span></a></div>

				</div>
			</div>

			<div class="content">
				<div class="main" align="center">
					<h1>Добро пожаловать в ServiceDesk</h1>
					</div>
					<!--Формы-->
					<form method="POST" action="/login">
                <div class="row" align="center">
					        <table>
                            <tr>
                                <td><label for="loginField"><fmt:message key = "key.userLogin" bundle = "${resourceBundle}"/></label></td>
                                <td><input id="loginField" type="text" name="login"></td>
                            </tr>

                            <tr>
                                <td><label for="passwordField"><fmt:message key = "key.password" bundle = "${resourceBundle}"/></label></td>
                                <td><input id="passwordField" type="password" name="password"></td>
                            </tr>
                            </table>
                 </div>
                 <div class="row" align="center">
                           <table>
                           <tr>
                           <th>
                                <div class="row" align="center">
                                      <input type="submit" value=<fmt:message key = "key.login" bundle = "${resourceBundle}"/>>
                                </div>
                           </th>
                           <th>
                      </form>
                      <form method="GET" action="/userCabinet">
                                       <input type="submit" value=<fmt:message key = "key.registrationAsGuest" bundle = "${resourceBundle}"/>>
                      </form>
                            </th>
                            <tr>
                            </table>

				 </div>
			</div>
			<div class="footer">
				<p>&copy; Zhansaya <a href="#">Github.com</a></p>
			</div>
		</div>
		<script type="text/javascript">
    <%@include file="/js/css3-mediaqueries.js"%>
</script>

	</body>
</html>