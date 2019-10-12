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
 <script type = "text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

 <body>


<script>
$(document).ready(function (){
$('select[name="cid"]').on("change", function (){
alert($('select[name="cid"]').val());
});
});
</script>


<p> <input id="idField" type="hidden" name="requestId" value = <c:out value="${request.getId()}"/>></p>
  <p> <input  type="hidden" name="requestName" value = <c:out value="${request}"/>></p>
<fmt:message key = "key.client" bundle = "${resourceBundle}"/>: <c:out value="${user.getName()}" />*:<select class="client" name="cid"required size="1" >
             <option <c:if test="${request.getProjectId()==null}"> disabled </c:if> ><fmt:message key = "key.chooseClient" bundle = "${resourceBundle}"/></option>

             <c:forEach var="client" items="${clients}">
                <option <c:if test="${client.getId()==request.getClientId()}"> selected </c:if> value="${client.getId()}"><c:out value="${client.getName()}"/></option>
                </c:forEach>

             </select>
             </p>


 </body>
 </html>