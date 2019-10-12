<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${local}"/>
<fmt:setBundle basename = "lang" var = "resourceBundle" scope="session"/>
<html>
	<head><link rel="shortcut icon" href="C:/Users/Жансая/IdeaProjects/servicedesk/favicon.ico" type="image/x-icon"/>
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
                         <input id="idField" type="hidden" name="page" value=<c:if test="${userIDFromOptions!=null}">"editUserByOperator?userIDFromOptions=${userIDFromOptions}"></c:if>
                         <c:if test="${userIDFromOptions==null}">"/editUserByOperator"></c:if>
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
                                    						<li><a href="/userCabinet" class="active"><fmt:message key = "key.personalAccount" bundle = "${resourceBundle}"/></a></li>
                                    						<li><a href="/listRequest"><fmt:message key = "key.requests" bundle = "${resourceBundle}"/></a></li>
                                    						<li><a href="jsp/options.jsp"><fmt:message key = "key.options" bundle = "${resourceBundle}"/></a></li>

                                    					</ul>
                                    				</div>
			</div>
			<div class="content">

				<div class="main">
					<h1>Кабинет пользователя</h1>
<form method="POST" action="${uri}">
    <label><fmt:message key = "key.userName" bundle = "${resourceBundle}"/>: <input id="nameField" type="text" name="name" required value =<c:out value="${userFromOptions.getName()}"/>></label>

      <p><fmt:message key = "key.position" bundle = "${resourceBundle}"/>: <input id="positionField" type="text"  name="position" value =<c:out value="${userFromOptions.getPosition()}"/>></p>
      <p><input  type="hidden" name="id" value =<c:out value="${userFromOptions.getId()}"/>></p>

      <p><fmt:message key = "key.phone" bundle = "${resourceBundle}"/>: <input id="phoneField" type="text" name="phone" value =<c:out value="${userFromOptions.getPhone()}"/>></p>
      <p><fmt:message key = "key.mobile" bundle = "${resourceBundle}"/>: <input id="mobileField" type="text" name="mobile" value =<c:out value="${userFromOptions.getMobile()}"/>></p>
      <p><fmt:message key = "key.mail" bundle = "${resourceBundle}"/>: <input id="mailField" type="text" name="mail" value =<c:out value="${userFromOptions.getMail()}"/>></p>
      <p><fmt:message key = "key.userLogin" bundle = "${resourceBundle}"/>:<c:if test="${userFromOptions.getId()!=0}"><c:out value="${userFromOptions.getLogin()}" /></c:if>
            <c:if test="${uri == '/registrationUserByOperator'}"> <input id="loginField" type="text" required name="login"/></c:if>
            <p><fmt:message key = "key.password" bundle = "${resourceBundle}"/>: <input id="passwordField" type="password"  name="password"/></p>

<c:if test="${userFromOptions.getId()==null}">
     <p> <input type="radio" name="role"  value="2"<c:if test="${uri == '/registrationUserByOperator'}">checked</c:if>><fmt:message key = "key.client" bundle = "${resourceBundle}"/></p>
     <p> <input type="radio" name="role" value="1"><fmt:message key = "key.engineer" bundle = "${resourceBundle}"/></p>
      <p><input type="radio" name="role" value="0"><fmt:message key = "key.operator" bundle = "${resourceBundle}"/></p>

 </c:if>

<div class="row">
     <c:if test="${uri == '/updateUserByOperator'}">
         <input type="submit" value=<fmt:message key = "key.update" bundle = "${resourceBundle}"/>>
     </c:if>
     <c:if test="${uri == '/registrationUserByOperator'}">
         <input type="submit" value=<fmt:message key = "key.registration" bundle = "${resourceBundle}"/>>
     </c:if>
     </div>
         </form>

<c:if test="${user.getUserRole().getId()==0 && userFromOptions.getId()!=null}">
 <form  method="POST" action="/deleteUser">
 <p><input  type="hidden" name="id" value =<c:out value="${userFromOptions.getId()}"/>></p>
         <input type="submit" value=<fmt:message key = "key.delete" bundle = "${resourceBundle}"/>>
</form>
  </c:if>

<c:if test="${userFromOptions.getUserRole().getId()==2 && userFromOptions.getId()!=null}">
<form method="POST" action="/editUserProject">

 <p><input  type="hidden" name="id" value =<c:out value="${userFromOptions.getId()}"/>></p>
    <p><select name="selectProject" size="5">
        <c:forEach var="project" items="${projects}">

    <option value="${project.getId()}"><c:out value="${project.getName()}"/></option>
      </c:forEach>
    </select>
    </p>

      <p><input type="submit" value="Добавить/изменить проект"/></p>

 </form>
 </c:if>
<c:if test="${userFromOptions.getUserRole().getId()==2 && userProjectFromOptions!=null}">
<form method="POST" action="/removeUserProject">

 <p><input  type="hidden" name="id" value =<c:out value="${userFromOptions.getId()}"/>></p>
         <p><input type="submit" value="Удалить с проекта"/></p>

 </form>
 </c:if>
				</div>
			</div>
			<div class="footer">
            				<p>&copy; Zhansaya <a href="https://github.com/zhdaurenkyzy/Servicedesk">Github.com</a></p>
            			</div>
		</div>

	</body>
</html>