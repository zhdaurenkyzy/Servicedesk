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
                         <input id="idField" type="hidden" name="page" value ="groupCabinet?Id=${group.getId()}">
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
<h2><fmt:message key = "key.createNewGroup" bundle = "${resourceBundle}"/>:</h2>
<div class="row">
  <form method="POST" action="${uri}">
     <p><fmt:message key = "key.groupName" bundle = "${resourceBundle}"/>: <input id="nameField" type="text" required name="groupName" value=<c:out value="${group.getName()}"/>></p>
     <p><input id="idField" type="hidden" name="groupId" value = <c:out value="${group.getId()}"/>></p>
   <p>
<c:if test="${uri == '/createGroup'}">
    <input type="submit" value=<fmt:message key = "key.create" bundle = "${resourceBundle}"/>>
</c:if>
<c:if test="${uri == '/updateGroupName'}">
    <input type="submit" value=<fmt:message key = "key.edit" bundle = "${resourceBundle}"/>>
</c:if>
   </p>
  </form>
</div>
<div class="row">
<c:if test="${uri == '/updateGroupName'}">
    <form method="POST" action="/deleteGroup">
         <p><input id="idField" type="hidden" name="groupId" value = <c:out value="${group.getId()}"/>></p>
         <p><input type="submit" value=<fmt:message key = "key.delete" bundle = "${resourceBundle}"/>></p>
      </form>
    <jsp:include page="listEngineer.jsp" />
</c:if>
</div>

 <div class="footer">
              <p>&copy; Zhansaya <a href="https://github.com/zhdaurenkyzy/Servicedesk">Github.com</a></p>
              </div>
             </div>
          <script type="text/javascript">
            <%@include file="/js/css3-mediaqueries.js"%>
           </script>
 	</body>
 </html>