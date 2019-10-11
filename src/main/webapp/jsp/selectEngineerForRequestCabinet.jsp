<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${local}"/>
<fmt:setBundle basename = "lang" var = "resourceBundle" scope="session"/>
 <html>
 <head><link rel="shortcut icon" href="C:/Users/Жансая/IdeaProjects/servicedesk/favicon.ico" type="image/x-icon"/></head>
 <script type = "text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

 <body>

<script>
$(document).ready(function (){
$('select[name="eid"]').on("change", function (){
alert($('select[name="eid"]').val());
});
});
</script>

<p> <input id="idField" type="hidden" name="requestId" value = <c:out value="${request.getId()}"/>></p>

  <p> <input  type="hidden" name="requestName" value = <c:out value="${request}"/>></p>
<fmt:message key = "key.engineer" bundle = "${resourceBundle}"/>* :<select class="engineer"  name="eid" required size="1" >

             <option disabled><fmt:message key = "key.chooseEngineer" bundle = "${resourceBundle}"/></option>

             <c:forEach var="user" items="${users}">
                <option <c:if test="${user.getId()==request.getEngineerId()}"> selected </c:if> value="${user.getId()}"><c:out value="${user.getName()}"/></option>
                </c:forEach>
             </select>
             </p>
   <span class = "engineer"></span>


 </body>
 </html>
