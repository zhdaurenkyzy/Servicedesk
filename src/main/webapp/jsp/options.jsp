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

    		<title>Параметры</title>
    	</head>
	<body>
		<div class="wrapper">
		<div class="header">
        			<div class="row" align="left">
                      <c:forEach var="language" items="${languages}">
                        <form method="POST" action="/setLanguage">
                         <input id="idField" type="hidden" name="page" value ="/jsp/options.jsp">
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
                                            <li><a href="#"><fmt:message key = "key.options" bundle = "${resourceBundle}"/></a></li>
                    					</ul>
                    				</div>
                    			</div>
			<div class="content">

				<div class="main">
					<h1>Параметры</h1>

					<!--Списки параметров-->

					<div class="row">
						<div class="col">
							<ul>
								<li><form method="GET" action="/listGroup">
                                          <p><input type="submit" value=<fmt:message key = "key.groups" bundle = "${resourceBundle}"/>></p>
                                     </form>
                                </li>
								<li><form method="POST" action="/listProject?state=true" >
                                          <p><input type="submit" value=<fmt:message key = "key.projects" bundle = "${resourceBundle}"/>></p>
                                     </form>
                                </li>

							</ul>
						</div>
						<div class="col">
							<ul>
								<li><form method="POST" action="/listUser" >
                                        <p><input type="submit" value=<fmt:message key = "key.users" bundle = "${resourceBundle}"/>></p>
                                    </form>
                                </li>

							</ul>
						</div>

					</div>

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