<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${local}"/>
<fmt:setBundle basename = "lang" var = "resourceBundle" scope="session"/>
 <html>
 <head><link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>

 <body>
<div class="col">
   <form method="POST" action="/addUserToGroup">
   <p><input id="idField" type="hidden" name="groupId" value = <c:out value="${group.getId()}"/>></p>
      <p><fmt:message key = "key.engineers" bundle = "${resourceBundle}"/>:<select size="5" multiple name="allUserList[]" required>
         <option disabled><fmt:message key = "key.chooseEngineer" bundle = "${resourceBundle}"/></option>
      <c:forEach var="list" items="${lists}">
         <option value="${list.getId()}"><c:out value="${list.getName()}"/></option>
         </c:forEach>
      </select>
      </p>
       <p><input type="submit" value=">>"></p>
   </form>
</div>
<div class="col">
   <form action="/removeUserFromGroup" method="POST">
   <p><input id="idField" type="hidden" name="groupId" value = <c:out value="${group.getId()}"/>></p>
       <p><fmt:message key = "key.engineers" bundle = "${resourceBundle}"/>:<select size="5" multiple name="usersGroup[]" required>
          <option disabled><fmt:message key = "key.chooseEngineer" bundle = "${resourceBundle}"/></option>
       <c:forEach var="currentList" items="${currentLists}">
          <option value="${currentList.getId()}"><c:out value="${currentList.getName()}"/></option>
       </c:forEach>
       </select>
       </p>
      <p><input type="submit" value="<<"></p>
   </form>
</div>
   <p><c:out value="${message}" /></p>
 </body>
 </html>