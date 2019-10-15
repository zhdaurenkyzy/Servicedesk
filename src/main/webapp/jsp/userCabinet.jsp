<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${local}"/>
<fmt:setBundle basename = "lang" var = "resourceBundle" scope="session"/>
<html>
	<head><link rel="shortcut icon" href="C:/Users/Жансая/IdeaProjects/servicedesk/favicon.ico" type="image/x-icon"/>

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
        			<div class="row" align="left">
                      <c:forEach var="language" items="${languages}">
                        <form method="POST" action="/setLanguage">
                         <input id="idField" type="hidden" name="page" value ="/jsp/userCabinet.jsp">
                         <input id="idField" type="hidden" name="languageId" value = <c:out value="${language.getId()}"/>>
                         <input type="submit" value=<c:out value="${language.getName()}"/>>
                        </form>
                      </c:forEach>
                    </div>
     <div class="row" align="right">
     <c:if test="${user!=null}">
      <p><fmt:message key = "key.youAreLoggedIn" bundle = "${resourceBundle}"/>: <c:out value="${user.getName()}" /></p>
       <form  method="POST" action="/logout">
             <input type="submit" value=<fmt:message key = "key.logout" bundle = "${resourceBundle}"/>>
              </form>
              </c:if>
              </div>

				<div class="headerContent">
                					<div class="logo"><a href="/">Service<span class="pink">Desk</span><span class="gray"></span></a></div>
                                    					<ul class="nav">
                                    						<li><a href="#" class="active"><fmt:message key = "key.personalAccount" bundle = "${resourceBundle}"/></a></li>
                                                            <li><a href="/listRequest"><fmt:message key = "key.requests" bundle = "${resourceBundle}"/></a></li>
                                                            <li><a href="/jsp/options.jsp"><fmt:message key = "key.options" bundle = "${resourceBundle}"/></a></li>
                                    					</ul>
                                    				</div>
			</div>
			<div class="content">

				<div class="main">

					<h1>Кабинет пользователя</h1>
<form method="POST" <c:if test="${user.getId() != null}">action="/updateUser"></c:if><c:if test="${user.getId() == null}">action="/registration"></c:if>
     <p><fmt:message key = "key.userName" bundle = "${resourceBundle}"/>: <input id="nameField" type="text" name="name" required value = <c:out value="${user.getName()}"/>></p>
      <p><fmt:message key = "key.position" bundle = "${resourceBundle}"/>: <input id="positionField" type="text"  name="position" value = <c:out value="${user.getPosition()}"/>></p>
      <p><fmt:message key = "key.phone" bundle = "${resourceBundle}"/>: <input id="phoneField" type="text" name="phone" value = <c:out value="${user.getPhone()}"/>></p>
      <p><fmt:message key = "key.mobile" bundle = "${resourceBundle}"/>: <input id="mobileField" type="text" name="mobile" value = <c:out value="${user.getMobile()}"/>></p>
      <p><fmt:message key = "key.mail" bundle = "${resourceBundle}"/>: <input id="mailField" type="text" name="mail" value = <c:out value="${user.getMail()}"/>></p>
      <p><fmt:message key = "key.userLogin" bundle = "${resourceBundle}"/>:
        <c:if test="${user.getId() != null}"><c:out value="${user.getLogin()}" /></c:if>
        <c:if test="${user.getId() == null}"> <input id="loginField" type="text" required name="login"/></c:if>
      </p>
      <p><fmt:message key = "key.password" bundle = "${resourceBundle}"/>: <input id="passwordField" type="password"  name="password" required></p>
      <c:if test="${user.getId() != null}"><p><fmt:message key = "key.confirmPassword" bundle = "${resourceBundle}"/>: <input id="passwordField" type="password"  name="repeatPassword" required></p></c:if>
     <div class="row">
     <c:if test="${user.getId() != null}">
         <input type="submit" value=<fmt:message key = "key.update" bundle = "${resourceBundle}"/>>
     </c:if>
     <c:if test="${user.getId() == null}">
         <input type="submit" value=<fmt:message key = "key.registration" bundle = "${resourceBundle}"/>>
     </c:if>
     </div>
         </form>

				</div>
			</div>
			<div class="footer">
            				<p>&copy; Zhansaya  <a href="https://github.com/zhdaurenkyzy/Servicedesk">Github.com</a></p>
            			</div>
		</div>
		<script type="text/javascript">
            <%@include file="/js/css3-mediaqueries.js"%>
        </script>
	</body>
</html>