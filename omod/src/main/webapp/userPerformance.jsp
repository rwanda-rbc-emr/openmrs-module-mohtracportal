<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/scripts/jquery-1.3.2.js" />
<%@ taglib prefix="mohtractag" uri="/WEB-INF/view/module/mohtracportal/taglibs/mohtractag.tld" %>
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/listingstyle.css" />

<%@ include file="template/localHeader.jsp"%>

<h2><spring:message code="@MODULE_ID@.user.performance"/></h2>
<br/>
<div style="margin:5px auto 5px auto; width: 80%">
	<openmrs:hasPrivilege privilege="View TRAC Portal Users performance">
		<form method="post" action="performance.htm">
			<table>
				<tr>
					<td><spring:message code="@MODULE_ID@.user"/></td>
					<td><select name="user">	
						<option value="0"><spring:message code="@MODULE_ID@.user.all"/></option>
						<c:forEach items="${users}" var="user">
							<option value="${user.id}" <c:if test="${user.id==param.user || user.id==curUserId}">selected='selected'</c:if>>${user.personName}</option>
						</c:forEach>
					</select></td>
					<td><input type="submit" value="<spring:message code="general.refresh"/>"/></td>
				</tr>
			</table>
		</form>
	</openmrs:hasPrivilege>
</div>


<c:if test="${param.user==0}">
	<div style="margin:5px auto 5px auto; width: 98%">
		<div style="width: 90%; margin: auto; padding: 3px; text-align: right;"><span style="min-width: 80px; background-color: cadetblue;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>: <spring:message code="Patient.header"/>;&nbsp;&nbsp;&nbsp;&nbsp;
		<span style="min-width: 80px; background-color: aliceblue;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>: <spring:message code="Encounter.header"/>;&nbsp;&nbsp;&nbsp;&nbsp;
		<span style="min-width: 80px; background-color: #DDDDDD;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>: <spring:message code="Obs.header"/><br/></div>
		<div class="list_container">
			<div class="list_title"><spring:message code="@MODULE_ID@.users.performance"/></div>
			<table class="list_data">
				<tr>
					<th class="columnHeader"><spring:message code="Person.names"/></th>
					<th class="columnHeader"><spring:message code="User.username"/></th>
					<th class="columnHeader"><spring:message code="User.roles"/></th>
					<th class="columnHeader"><spring:message code="@MODULE_ID@.performance.active.firstlast"/></th>
					<th class="columnHeader"><spring:message code="@MODULE_ID@.performance.today"/></th>
					<th class="columnHeader"><spring:message code="@MODULE_ID@.general"/></th>
				</tr>
				<c:forEach items="${users}" var="user" varStatus="status">
					<%@ include file="template/userPerformanceRow.jsp"%>
				</c:forEach>
				<tr class="list_title">
					<td colspan="6" class="rowValue">&nbsp;&nbsp;</td>
				</tr>
			</table>
		</div>
	</div>
</c:if>

<c:if test="${param.user!=0}">
	<div style="margin:5px auto 5px auto; width: 80%">
		<div id="userInfo" style="float: left; width: 40%">
			<div class="list_container">
				<div class="list_title"><spring:message code="@MODULE_ID@.user.identification"/></div>
				<table class="list_data">
					<tr class="rowValue">
						<td><spring:message code="Person.names"/></td>
						<td> : ${currentUser.personName}</td>
					</tr>
					<tr class="rowValue even">
						<td><spring:message code="User.username"/></td>
						<td> : ${currentUser.username}</td>
					</tr>
					<tr class="rowValue">
						<td><spring:message code="User.roles"/></td>
						<td> : ${currentUser.roles}</td>
					</tr>
					<!-- <tr class="rowValue even">
						<td>Created on</td>
						<td> : </td>
					</tr> -->
					<tr class="list_title">
						<td class="rowValue">&nbsp;&nbsp;</td>
						<td class="rowValue">&nbsp;&nbsp;</td>
					</tr>
				</table>
			</div>
		</div>
		
		<div id="userPerformance" style="float: right; width: 58%">
			<div class="list_container">
				<div class="list_title"><spring:message code="@MODULE_ID@.user.active.when"/></div>
				<table class="list_data">
					<tr>
						<th class="columnHeader"><spring:message code="@MODULE_ID@.performance.header.first.patient.created"/></th>
						<th class="columnHeader"><spring:message code="@MODULE_ID@.performance.header.first.encounter.created"/></th>
						<th class="columnHeader"><spring:message code="@MODULE_ID@.performance.header.first.obs.created"/></th>
					</tr>
					<tr class="rowValue">
						<td>${mohtractag:dateFirstObjectCreatedByUser(currentUser.id,0)}</td>
						<td>${mohtractag:dateFirstObjectCreatedByUser(currentUser.id,1)}</td>
						<td>${mohtractag:dateFirstObjectCreatedByUser(currentUser.id,2)}</td>
					</tr>
					<tr>
						<th class="columnHeader"><spring:message code="@MODULE_ID@.performance.header.last.patient.created"/></th>
						<th class="columnHeader"><spring:message code="@MODULE_ID@.performance.header.last.encounter.created"/></th>
						<th class="columnHeader"><spring:message code="@MODULE_ID@.performance.header.last.obs.created"/></th>
					</tr>
					<tr class="rowValue">
						<td>${mohtractag:dateLastObjectCreatedByUser(currentUser.id,0)}</td>
						<td>${mohtractag:dateLastObjectCreatedByUser(currentUser.id,1)}</td>
						<td>${mohtractag:dateLastObjectCreatedByUser(currentUser.id,2)}</td>
					</tr>
					<tr class="list_title">
						<td class="rowValue">&nbsp;&nbsp;</td>
						<td class="rowValue">&nbsp;&nbsp;</td>
						<td class="rowValue">&nbsp;&nbsp;</td>
					</tr>
				</table>
			</div><br/><br/>
			
			<div class="list_container">
				<div id="patientTabs">
					<ul>
						<li><a hidefocus="hidefocus" onclick="return changeTab(this);" class="current " href="#" id="todayTab"><spring:message code="@MODULE_ID@.performance.today"/></a></li>
						<!-- <li><a hidefocus="hidefocus" onclick="return changeTab(this);" href="#" id="weekTab" title="5 days from today">Week</a></li> -->
						<li><a hidefocus="hidefocus" onclick="return changeTab(this);" href="#" id="generalTab"><spring:message code="@MODULE_ID@.general"/></a></li>
					</ul>
				</div>
				
				<div class="list_title"><spring:message code="@MODULE_ID@.user.evaluation"/>&nbsp;:&nbsp;<span id="critere"><spring:message code="@MODULE_ID@.performance.today"/></span></div>
				
				<div id="today" style="">
					<table class="list_data">
						<tr class="rowValue">
							<td><spring:message code="@MODULE_ID@.performance.number.patient"/></td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,0,0)}</td>
						</tr>
						<tr class="rowValue even">
							<td><spring:message code="@MODULE_ID@.performance.number.encounter"/></td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,1,0)}</td>
						</tr>
						<tr class="rowValue">
							<td><spring:message code="@MODULE_ID@.performance.number.obs"/></td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,2,0)}</td>
						</tr>
						<tr class="list_title">
							<td class="rowValue">&nbsp;&nbsp;</td>
							<td class="rowValue">&nbsp;&nbsp;</td>
						</tr>
					</table>
				</div>
				
				<!-- <div id="week" style="display: none;">
					<table class="list_data">
						<tr class="rowValue">
							<td>Number of patients created</td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,0,1)}</td>
						</tr>
						<tr class="rowValue even">
							<td>Number of encounters created</td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,1,1)}</td>
						</tr>
						<tr class="rowValue">
							<td>Number of observations created</td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,2,1)}</td>
						</tr>
						<tr class="list_title">
							<td class="rowValue">&nbsp;&nbsp;</td>
							<td class="rowValue">&nbsp;&nbsp;</td>
						</tr>
					</table>
				</div> -->
				
				<div id="general" style="display: none;">
					<table class="list_data">
						<tr class="rowValue">
							<td><spring:message code="@MODULE_ID@.performance.number.patient"/></td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,0,2)}</td>
						</tr>
						<tr class="rowValue even">
							<td><spring:message code="@MODULE_ID@.performance.number.encounter"/></td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,1,2)}</td>
						</tr>
						<tr class="rowValue">
							<td><spring:message code="@MODULE_ID@.performance.number.obs"/></td>
							<td> : ${mohtractag:numberOfObjectCreatedByUser(currentUser.id,2,2)}</td>
						</tr>
						<tr class="list_title">
							<td class="rowValue">&nbsp;&nbsp;</td>
							<td class="rowValue">&nbsp;&nbsp;</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		
		<div style="clear: both;"></div>
	
	</div>
</c:if>

<script type="text/javascript">

	function changeTab(tabObj) {
		$("#critere").html(tabObj.text);
		if (!document.getElementById || !document.createTextNode) {return;}
		if (typeof tabObj == "string")
			tabObj = document.getElementById(tabObj);
		
		if (tabObj) {
			var tabs = tabObj.parentNode.parentNode.getElementsByTagName('a');
			for (var i=0; i<tabs.length; i++) {
				if (tabs[i].className.indexOf('current') != -1) {
					manipulateClass('remove', tabs[i], 'current');
				}
				var divId = tabs[i].id.substring(0, tabs[i].id.lastIndexOf("Tab"));
				var divObj = document.getElementById(divId);
				if (divObj) {
					if (tabs[i].id == tabObj.id)
						divObj.style.display = "";
					else
						divObj.style.display = "none";
				}
			}
			addClass(tabObj, 'current');
			
			setTabCookie(tabObj.id);
		}
		return false;
	}

</script>
<%@ include file="/WEB-INF/template/footer.jsp" %>