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
                                <input id="idField" type="hidden" name="page" value ="decisionCabinet?requestId=${request.getId()}">
                  <input id="idField" type="hidden" name="languageId" value = <c:out value="${language.getId()}"/>>
                                           <input type="submit" value=<c:out value="${language.getName()}"/>>
                                          </form>
                                        </c:forEach>
                                      </div>
                       <div class="row" align="right">
                        <p><fmt:message key = "key.youAreLoggedIn" bundle = "${resourceBundle}"/>: <c:out value="${user.getName()}" /></p>
                         <form  method="POST" action="/logout">
                               <input type="submit" value=<fmt:message key = "key.logout" bundle = "${resourceBundle}"/>>
                                </form>
                                </div>

                  				<div class="headerContent">
                                  	<div class="logo"><a href="/">Service<span class="pink">Desk</span><span class="gray"></span></a></div>
                                            <ul class="nav">
                                                  <li><a href="/jsp/userCabinet.jsp" class="active"><fmt:message key = "key.personalAccount" bundle = "${resourceBundle}"/></a></li>
                                                  <li><a href="/listRequest"><fmt:message key = "key.requests" bundle = "${resourceBundle}"/></a></li>
                                                  <li><a href="/jsp/options.jsp"><fmt:message key = "key.options" bundle = "${resourceBundle}"/></a></li>

                                            </ul>
                                      </div>
                  			</div>
                  			<div class="content">

                  				<div class="main">

 <form method="POST" action="/resolveRequest">

 <p> <input id="idField" type="hidden" name="requestId" value = <c:out value="${request.getId()}"/>></p>
  <p><h2><fmt:message key = "key.requestId" bundle = "${resourceBundle}"/>: <c:out value="${request.getId()}"/></h2></p>

<p><fmt:message key = "key.decision" bundle = "${resourceBundle}"/><Br>
   <textarea name="decision" cols="100" rows="10"><c:out value="${request.getDecision()}"/></textarea></p>
    <input type="submit" value="<fmt:message key = "key.resolved" bundle = "${resourceBundle}"/>">
        </form>
        <form method="POST" action="/requestCabinet?requestId=${request.getId()}">

           <input type="submit" value=<fmt:message key = "key.goBackRequestCabinet" bundle = "${resourceBundle}"/>>
             </form>
    <form method="POST" action="/listRequest">
   <input type="submit" value=<fmt:message key = "key.listRequest" bundle = "${resourceBundle}"/>>
     </form>
 </body>
 </html>
