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
                         <input id="idField" type="hidden" name="page" value ="listMode">
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
					<h1><fmt:message key = "key.createNewProject" bundle = "${resourceBundle}"/></h1>
					 <div class="row">
					<form method="POST" action="/createMode">
                         <p><fmt:message key = "key.mode" bundle = "${resourceBundle}"/>: <input id="nameField" type="text" required name="modeName"/></p>
                         <p>  <input type="submit" value=<fmt:message key = "key.create" bundle = "${resourceBundle}"/>></p>
                             </form>
                </div>
                <div class="row">

 </div>

                 </div>
					<div class="mobile">
						<table class="bordered">
							<thead>
								<tr>
									<th><fmt:message key = "key.mode" bundle = "${resourceBundle}"/></th>

								</tr>
							</thead>
							<tbody>
							<c:forEach var="mode" items="${modes}">
								<tr>
									<form method="POST">
                                                     <td> <a href="/modeCabinet?modeID=${mode.getId()}"><c:out value="${mode.getName()}"/></td>

                                              </form>
								</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
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