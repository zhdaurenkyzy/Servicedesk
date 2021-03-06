<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${local}"/>
<fmt:setBundle basename = "lang" var = "resourceBundle" scope="session"/>
<html>
	<head><link rel="shortcut icon" href="C:/Users/Жансая/IdeaProjects/servicedesk/favicon.ico" type="image/x-icon"/>
    	<head>
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
                         <input id="idField" type="hidden" name="page" value ="listRequest">
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
				<div class="leftCol">
					<ul class="leftNav">
						<li><a href="/listRequest?authorId=${user.getId()}"><fmt:message key = "key.createdRequests" bundle = "${resourceBundle}"/></a></li>
						<li><a href="/listRequest?statusId=1"><fmt:message key = "key.openRequests" bundle = "${resourceBundle}"/></a></li>
						<li><a href="/listRequest?statusId=2"><fmt:message key = "key.inProgressRequests" bundle = "${resourceBundle}"/></a></li>
						<li><a href="/listRequest?statusId=3"><fmt:message key = "key.requestsWaitingForResponse" bundle = "${resourceBundle}"/></a></li>
						<li><a href="/listRequest?statusId=4"><fmt:message key = "key.closedRequests" bundle = "${resourceBundle}"/></a></li>
						<li><a href="/listRequest?clientId=${user.getId()}"><fmt:message key = "key.requestsByClient" bundle = "${resourceBundle}"/></a></li>
						<li><a href="/listRequest?engineerId=${user.getId()}"><fmt:message key = "key.assignedToMe" bundle = "${resourceBundle}"/></a></li>
						<c:if test="${user.getUserRole()==role}"><li><a href="/listRequest?engineerId=0"><fmt:message key = "key.notSighnedRequests" bundle = "${resourceBundle}"/></a></li></c:if>
						<li><a href="/listRequest"><fmt:message key = "key.allRequests" bundle = "${resourceBundle}"/></a></li>
					</ul>
				</div>
				<div class="main">
					<h1><fmt:message key = "key.ifYouWantToCreateARequestClick" bundle = "${resourceBundle}"/></h1>
					<div class="row">
<form method="GET" action="/requestCabinet?requestId=0">
     <p><input type="submit" value=<fmt:message key = "key.createNewRequest" bundle = "${resourceBundle}"/>></p>
  </form>
  </div>

				</div>
			</div>
			 <form method="POST" action="/searchRequest">
             <table class="bordered">

                      <tr>
                    <td>
                   <fmt:message key = "key.requestId" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" checked value="request.REQUEST_ID">
                    </td>
                     <td>
                    <fmt:message key = "key.theme" bundle = "${resourceBundle}"/> <input name="searchCriteria" type="radio" value="request.REQUEST_THEME">
                     </td>
                      <td>
                      <fmt:message key = "key.priorities" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="request.REQUEST_PRIORITY_ID">
                      </td>
                     <td>
                     <fmt:message key = "key.status" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="status.STATUS_NAME">
                     </td>
                     <td>
                     <fmt:message key = "key.groups" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="engineer_group.GROUP_NAME">
                     </td>
                     <td>
                     <fmt:message key = "key.engineers" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="engineer.USER_NAME">
                     </td>
                     <td>
                     <fmt:message key = "key.projects" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="project.PROJECT_NAME">
                     </td>
                     <td>
                     <fmt:message key = "key.clients" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="client.USER_NAME">
                     </td>
                     <td>
                     <fmt:message key = "key.authorOfCreation" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="author_of_creation.USER_NAME">
                     </td>
                     <td>
                     <fmt:message key = "key.dateOfCreation" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="request.REQUEST_DATE_OF_CREATION">
                     </td>
                     <td>
                     <fmt:message key = "key.AuthorOfDecision" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="author_of_decision.USER_NAME">
                     </td>
                     <td>
                     <fmt:message key = "key.dateOfDecision" bundle = "${resourceBundle}"/><input name="searchCriteria" type="radio" value="request.REQUEST_DATE_OF_DECISION">
                     </td>
                     </tr>
                     </table>
                     <input type="text" name="searchText">
                <input type="submit" value=<fmt:message key = "key.search" bundle = "${resourceBundle}"/>></p>
              </form>
			<!--Table-->
            					<div class="mobile">
            						<table class="bordered">
            							<thead>
            								<tr>

            									<th><fmt:message key = "key.id" bundle = "${resourceBundle}"/></th>
            									<th><fmt:message key = "key.theme" bundle = "${resourceBundle}"/></th>
            									<th><fmt:message key = "key.status" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.priority" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.groups" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.engineer" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.projects" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.client" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.authorOfCreation" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.dateOfCreation" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.AuthorOfDecision" bundle = "${resourceBundle}"/></th>
                                                <th><fmt:message key = "key.dateOfDecision" bundle = "${resourceBundle}"/></th>
            								</tr>
            							</thead>
            							<tbody>

            									<c:forEach var="requestState" items="${requestStates}">
                                                           <tr>
                                                                  <td>
                                                                   <c:out value="${requestState.getRequestId()}"/>
                                                                   </td>
                                                                    <td>
                                                                    <a href="/requestCabinet?requestId=${requestState.getRequestId()}" ><c:out value="${requestState.getRequestTheme()}"/>
                                                                   </td>
                                                                   <td>
                                                                    <c:out value="${requestState.getStatusName()}"/>
                                                                    </td>
                                                                    <td>
                                                                    <c:out value="${requestState.getRequestPriorityId()}"/>
                                                                    </td>
                                                                     <td>
                                                                     <c:out value="${requestState.getGroupName()}"/>
                                                                     </td>
                                                                     <td>
                                                                      <c:out value="${requestState.getEngineerName()}"/>
                                                                     </td>
                                                                      <td>
                                                                      <c:out value="${requestState.getProjectName()}"/>
                                                                       </td>
                                                                       <td>
                                                                       <c:out value="${requestState.getClientName()}"/>
                                                                        </td>
                                                                     <td>
                                                                     <c:out value="${requestState.getAuthorCreationName()}"/>
                                                                     </td>
                                                                    <td>
                                                                    <c:out value="${requestState.getRequestDateOfCreation()}"/>
                                                                    </td>
                                                                    <td>
                                                                     <c:out value="${requestState.getAuthorOfDecisionName()}"/>
                                                                      </td>
                                                                      <td>
                                                                     <c:out value="${requestState.getRequestDateOfDecision()}"/>
                                                                    </td>

                                                                   </td>

                                                           </tr>
                                                        </c:forEach>

            							</tbody>
            						</table>
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