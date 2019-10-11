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
 <div class="wrapper">
 <div class="header">
 <c:forEach var="language" items="${languages}">
       <tr>
           <td>
               <form method="POST" action="/setLanguage" >
                  <input id="idField" type="hidden" name="page" value ="requestCabinet?requestId=${request.getId()}">
                  <p> <input id="idField" type="hidden" name="languageId" value = <c:out value="${language.getId()}"/>></p>
                  <input type="submit" onClick="history.go(0)" value=<c:out value="${language.getName()}"/>>
               </form>
           </td>
       </tr>
  </c:forEach>
</div>
<div align = "right">
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

<div class="content">
<div class="main">
<h2><fmt:message key = "key.requestId" bundle = "${resourceBundle}"/>: <c:out value="${request.getId()}"/></h2>
<div class="col">
<ul>
<li><c:if test="${user.getUserRole().getId()==0 && request.getId()!=null}">
 <form  method="POST" action="/deleteRequest">
 <p> <input id="idField" type="hidden" name="requestId" value = <c:out value="${request.getId()}"/>></p>
         <input type="submit" value=<fmt:message key = "key.delete" bundle = "${resourceBundle}"/>>
  </form>
  </c:if>
  </li>
  </ul>
  </div>

  <div class="col">
  	<ul>

<li><c:if test="${request.getId()!=null}">
   <form  method="POST" action="/historyOfRequest?requestId=${request.getId()}">

           <input type="submit" value=<fmt:message key = "key.historyOfChange" bundle = "${resourceBundle}"/>/>
    </form>
    </c:if>
    </li>
    </ul>
     </div>

   </div>




 <form method="POST" action="${uri}">

 <p> <input id="idField" type="hidden" name="requestId" value = <c:out value="${request.getId()}"/>></p>
  <p> <input  type="hidden" name="requestName" value = <c:out value="${request}"/>></p>

      <p><fmt:message key = "key.status" bundle = "${resourceBundle}"/>:<select size="1" name="selectStatus" required>
      <option disabled><fmt:message key = "key.chooseStatus" bundle = "${resourceBundle}"/></option>
      <c:forEach var="status" items="${statuses}">
         <option <c:if test="${status.getId()==request.getStatusId()}"> selected </c:if>value="${status.getId()}"><c:out value="${status.getName()}"/></option>
         </c:forEach>
      </select>


 <fmt:message key = "key.mode" bundle = "${resourceBundle}"/>* :<select required size="1" name="selectMode">
       <option disabled><fmt:message key = "key.chooseMode" bundle = "${resourceBundle}"/></option>

<c:forEach var="mode" items="${modes}">
<option <c:if test="${mode.getId()==request.getModeId()}"> selected </c:if> value="${mode.getId()}">
<c:out value="${mode.getName()}"/>
</option>
 </c:forEach>
       </select>
       </p>

  <p><fmt:message key = "key.level" bundle = "${resourceBundle}"/>* :<select required size="1" name="selectLevel" required>
       <option disabled><fmt:message key = "key.chooseLevel" bundle = "${resourceBundle}"/></option>
       <c:forEach var="level" items="${levels}">
          <option <c:if test="${level.getId()==request.getLevelId()}"> selected </c:if> value="${level.getId()}"><c:out value="${level.getName()}"/></option>
          </c:forEach>
       </select>

<fmt:message key = "key.priority" bundle = "${resourceBundle}"/>* :<select size="1" name="selectPriority">
         <option disabled><fmt:message key = "key.choosePriority" bundle = "${resourceBundle}"/></option>
         <c:forEach var="priority" items="${priorities}">
            <option <c:if test="${priority.getId()==request.getPriority().getId()}"> selected </c:if> value="${priority.getId()}"><c:out value="${priority.getId()}"/></option>
            </c:forEach>
         </select>
         </p>
<script type = "text/javascript">
$(document).ready(function (){
var id=$(".group").val();
$.ajax({
type:"POST",
url:"/engineerRequest",
data:{id:id},
success:function(data){
$(".engineer").html(data);
}
});
$(".group").on("change", function (){
var id = $(".group").val();
if(id==0){

}
$.ajax({
type:"POST",
url:"/engineerRequest",
data:{id:id},
success:function(data){
$(".engineer").html(data);
}
});
});
});
</script>

<p><fmt:message key = "key.groupName" bundle = "${resourceBundle}"/>* :<select class="group" required size="1" name="selectGroup">

         <option disabled><fmt:message key = "key.chooseGroup" bundle = "${resourceBundle}"/></option>

         <option <c:if test="${group.getId()==request.getGroupId()}"> selected </c:if> value="${group.getId()==null}"><fmt:message key = "key.allUser" bundle = "${resourceBundle}"/></option>
        <c:forEach var="group" items="${groups}">
         <option <c:if test="${group.getId()==request.getGroupId()}"> selected </c:if> value="${group.getId()}"><c:out value="${group.getName()}"/></option>
            </c:forEach>
         </select>

   <span class = "engineer"></span>


<script type = "text/javascript">
$(document).ready(function (){
var idProject=$(".project").val();
$.ajax({
type:"POST",
url:"/listClient",
data:{idProject:idProject},
success:function(data){
$(".client").html(data);
}
});
$(".project").on("change", function (){
var idProject = $(".project").val();
if(idProject==0){
}
$.ajax({
type:"POST",
url:"/listClient",
data:{idProject:idProject},
success:function(data){
$(".client").html(data);
}
});
});
});
</script>

<p><fmt:message key = "key.projectName" bundle = "${resourceBundle}"/>* :<select class="project" required size="1" name="selectProject">


        <option name="idProject" <c:if test="${project.isState()==false}"> selected </c:if> value="${project.getId()==null}"><fmt:message key = "key.allClient" bundle = "${resourceBundle}"/></option>
         <c:forEach var="project" items="${projects}">
            <option name="idProject"<c:if test="${project.getId()==request.getProjectId()}"> selected </c:if> value="${project.getId()}"><c:out value="${project.getName()}"/></option>

            </c:forEach>
         </select>
<span class = "client"></span>

<p><fmt:message key = "key.theme" bundle = "${resourceBundle}"/>: <input id="themeField" type="text"  name="themeName" required value=<c:out value="${request.getTheme()}"/>></p>
<p><fmt:message key = "key.description" bundle = "${resourceBundle}"/><Br>
   <textarea name="description" cols="100" rows="10"><c:out value="${request.getDescription()}"/></textarea></p>

<c:if test="${uri == '/updateRequest'}">
    <input type="submit" value=<fmt:message key = "key.update" bundle = "${resourceBundle}"/>>
</c:if>
<c:if test="${uri == '/createRequest'}">
    <input type="submit" value=<fmt:message key = "key.create" bundle = "${resourceBundle}"/>>
</c:if>
               </form>
 <form method="POST" action="/decisionCabinet?requestId=${request.getId()}">
    <c:if test="${request.getId()!=null}"> <input type="submit" value=<fmt:message key = "key.decision" bundle = "${resourceBundle}"/>> </c:if>
 </form>
                 </div>
                 <div class="footer">
                             				<p>&copy; Zhansaya <a href="#">Github.com</a></p>
                             			</div>
                             			</div>
                             			</div>
 </body>
 </html>
