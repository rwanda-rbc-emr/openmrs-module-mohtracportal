<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<%@ taglib prefix="mohtractag" uri="/WEB-INF/view/module/mohtracportal/taglibs/mohtractag.tld" %>

<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/tracportal.css" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/listingstyle.css" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<openmrs:require privilege="View TRAC Portal patients listing and export" otherwise="/login.htm" redirect="/module/@MODULE_ID@/patientList.htm?page=1" />

<h2><spring:message code="@MODULE_ID@.patientlist.title"/></h2><br/>
	<div>	
		<div style="float: left; width: 60%;">
			<form action="patientList.htm" method="get">
				<table>
					<tr>
						<td><spring:message code="@MODULE_ID@.from"/></td>
						<td><img border="0" src="<openmrs:contextPath/>/images/info.gif" title="<spring:message code="@MODULE_ID@.help.from"/>"/></td>
						<td><input value="${param.from}" size="11" type="text" onclick="showCalendar(this)" name="from"/></td>
						<td style="margin-left: 10px;"><spring:message code="@MODULE_ID@.to"/></td>
						<td><img border="0" src="<openmrs:contextPath/>/images/info.gif" title="<spring:message code="@MODULE_ID@.help.to"/>"/></td>
						<td><input value="${param.to}" size="11" type="text" onclick="showCalendar(this)" name="to"/></td>
						<td><input type="submit" class="list_exportBt" value="<spring:message code="@MODULE_ID@.refresh"/>" title="<spring:message code="@MODULE_ID@.refresh"/>" id="btRefresh"/></td>
						<td><input value="1" type="hidden" name="page"/></td>
						<td><input value="" type="hidden" id="users" name="users"/></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div style="float: right; max-width: 35%; text-align:right;">&nbsp;
		</div>
		
		<div style="clear: both;"></div>
	</div>
	<div id="usersId" style="margin-top: 4px; text-align: left; padding-left: 7px;" class="menuTab" title="<spring:message code="@MODULE_ID@.show"/>"><spring:message code="@MODULE_ID@.selected"/> : <span id="nbrOfUsers"><spring:message code="@MODULE_ID@.all"/></span> <spring:message code="@MODULE_ID@.users"/></div>
	<div id="usersList" style="margin-bottom: 5px;"><br/>
		<input type="hidden" value="<spring:message code='@MODULE_ID@.checkAll'/>" id="checkAllWord"/>
		<input type="button" checked="checked" value="<spring:message code="@MODULE_ID@.uncheckAll"/>" onclick="checking()" name="chkbxUsr_all" id="chkbxUsrId_all"/>
		<table>
			<tr>
				<td>
					<div class="listUser">
						<table>
							<c:forEach items="${usersList}" var="user" varStatus="status">
								<c:if test="${user.personName ne 'daemon daemon'}">
									<tr>
										<td><input value="${user.id}" type="checkbox" <c:if test="${empty usrs}">checked="checked"</c:if><c:forEach items="${usrs}" var="usr"><c:if test="${usr==user.id}">checked="checked"</c:if></c:forEach> name="chkbxUsr_${status.count}" id="chkbxUsrId_${status.count}" onclick="checkStatus()"/></td>
										<td><label for="chkbxUsrId_${status.count}">${user.personName}</label></td>
									</tr>
								</c:if>
								<c:if test="${status.count%numberofUsersPerColumn==0}"></table></div></td><td><div class="listUser"><table></c:if>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div id="close" class="menuTab" style="text-align: left; padding-left: 7px;" title="<spring:message code="@MODULE_ID@.hide"/>"><spring:message code="@MODULE_ID@.hide"/></div>
	</div>
	
	<hr/><br/>
	
	<div style="margin: auto; width: 80%; font-size: 0.9em;">
		<div id="list_container" style="width: 99%">
			<div id="list_title">
				<div class="list_title_msg"><spring:message code="@MODULE_ID@.patient.listing"/></div>
				<div class="list_title_bts">
					<span>
						<openmrs:hasPrivilege privilege="Export Collective Patient Data">
							<form action="patientList.htm?page=1&export=csv${parameters}" method="post" style="display: inline;">
								<input type="submit" class="list_exportBt" value="<spring:message code="@MODULE_ID@.patientlist.xprt.csv"/>" title="<spring:message code="@MODULE_ID@.patientlist.csv"/>"/>
							</form>&nbsp;&nbsp;
							<form action="patientList.htm?page=1&export=pdf${parameters}" method="post" style="display: inline;">
								<input type="submit" class="list_exportBt" value="<spring:message code="@MODULE_ID@.patientlist.xprt.pdf"/>" title="<spring:message code="@MODULE_ID@.patientlist.pdf"/>"/>
							</form>
						</openmrs:hasPrivilege>
					</span>
				</div>
				<div style="clear:both;"></div>
			</div>
			<table id="list_data">
				<tr>
					<th width="15%" class="columnHeader"><spring:message code="general.dateCreated"/></th>
					<th width="5%" class="columnHeader numberCol">#.</th>
					<openmrs:hasPrivilege privilege="View Patient Names">
						<th width="30%" class="columnHeader"><spring:message code="@MODULE_ID@.patient.names"/></th>
					</openmrs:hasPrivilege>
					<th width="15%" class="columnHeader">${mohtractag:identifierTypeNameByIdAsString(tracnetIdentifierTypeId)}</th>
					<th width="15%" class="columnHeader">${mohtractag:identifierTypeNameByIdAsString(localIdentifierTypeId)}</th>
					<th width="20%" class="columnHeader"><spring:message code="@MODULE_ID@.numberOfEncounters"/></th>
				</tr>
				<c:if test="${empty patientList}"><tr><td colspan="6" width="100%" style="text-align: center;"><spring:message code="@MODULE_ID@.tablelist.empty"/></td></tr></c:if>
				<c:set value="0" var="index"/>
				<c:forEach items="${patientList}" var="patient" varStatus="status">
					<tr>
						<c:choose>
						  <c:when test="${(patient[1]) == currentDate}">
						    <td class="rowValue" <c:if test="${index%2!=0}">style="background-color: whitesmoke;"</c:if>><c:if test="${patient[1]!=currentDate}"><openmrs:formatDate date="${patient[1]}" type="medium"/><c:set value="${patient[1]}" var="currentDate"/></c:if></td>
						  </c:when>
						  <c:otherwise>
						   	<c:set value="${index+1}" var="index"/>
						   	<td class="rowValue" style="border-top: 1px solid cadetblue; <c:if test="${index%2!=0}">background-color: whitesmoke;</c:if>"><c:if test="${patient[1]!=currentDate}"><openmrs:formatDate date="${patient[1]}" type="medium"/><c:set value="${patient[1]}" var="currentDate"/></c:if></td>
						  </c:otherwise>
						</c:choose>
						<td class="rowValue ${status.count%2!=0?'even':''}">${((param.page-1)*pageSize)+status.count}.</td>
						<openmrs:hasPrivilege privilege="View Patient Names">
							<td class="rowValue ${status.count%2!=0?'even':''}"><a href="<openmrs:contextPath/>/patientDashboard.form?patientId=${patient[0]}"><div class="menuLink" title="<spring:message code="patientDashboard.viewDashboard"/>">${mohtractag:personName(patient[0])}</div></a></td>
						</openmrs:hasPrivilege>
						<td class="rowValue ${status.count%2!=0?'even':''}">${mohtractag:personIdentifierByIds(patient[0],tracnetIdentifierTypeId)}</td>
						<td class="rowValue ${status.count%2!=0?'even':''}">${mohtractag:personIdentifierByIds(patient[0],localIdentifierTypeId)}</td>
						<td class="rowValue ${status.count%2!=0?'even':''}">${mohtractag:numberOfEncounterByPatient(patient[0])}&nbsp;<spring:message code="@MODULE_ID@.encounters"/></td>
					</tr>
				</c:forEach>
			</table>
			<div id="list_footer">
				<div class="list_footer_info">${pageInfos}</div>
				<div class="list_footer_pages">		
					<table>
						<tr>
							<c:if test="${prevPage!=-1}">
								<td width="100px" class="" style="padding:1px 2px 1px 2px; vertical-align: text-top;">
									<a href="patientList.htm?page=1${parameters}"><div class="list_pageNumber" style="text-align: center;">|&lt; <spring:message code="@MODULE_ID@.first"/></div></a>
								</td>
								<td width="100px" class="" style="padding:1px 2px 1px 2px; vertical-align: text-top;"><a href="patientList.htm?page=${prevPage}${parameters}">
									<div class="list_pageNumber" style="text-align: center;">&lt;&lt; <spring:message code="general.previous"/></div></a>
								</td>
							</c:if>
							<c:if test="${nextPage!=-1}">
								<td width="100px" class="" style="padding:1px 2px 1px 2px; vertical-align: text-top;">
									<a href="patientList.htm?page=${nextPage}${parameters}"><div class="list_pageNumber" style="text-align: center;"><spring:message code="general.next"/> &gt;&gt;</div></a>
								</td>
								<td width="100px" class="" style="padding:1px 2px 1px 2px; vertical-align: text-top;">
									<a href="patientList.htm?page=${lastPage}${parameters}"><div class="list_pageNumber" style="text-align: center;"><spring:message code="@MODULE_ID@.last"/> &gt;|</div></a>
								</td>
							</c:if>
						</tr>
					</table>		
				</div>
				<div style="clear: both"></div>
			</div>
		</div>
	</div>
	
	<script>
		jQuery(document).ready(function(){
			jQuery("#usersList").hide();checkStatus();
			jQuery("#usersId").click(function(){
				if(document.getElementById("usersList").style.display=="none")
					jQuery("#usersList").show(500);
				else jQuery("#usersList").hide(500);
			});
			jQuery("#close").click(function(){
				jQuery("#usersList").hide(500);
			});
		});

		function checking(){
			var i=1;
			if(document.getElementById("chkbxUsrId_all").value==jQuery("#checkAllWord").val()){
				while(document.getElementById("chkbxUsrId_"+i)!=null){
					document.getElementById("chkbxUsrId_"+i).checked=true;
					i++;
				}
				document.getElementById("chkbxUsrId_all").value="<spring:message code='@MODULE_ID@.uncheckAll'/>";
			}else {
				while(document.getElementById("chkbxUsrId_"+i)!=null){
					document.getElementById("chkbxUsrId_"+i).checked=false;
					i++;
				}
				document.getElementById("chkbxUsrId_all").value="<spring:message code='@MODULE_ID@.checkAll'/>";
			}
			checkStatus();
		}

		function checkStatus(){
			var i=1,checked=0,notChecked=false,usersList="";
			while(document.getElementById("chkbxUsrId_"+i)!=null){
				if(document.getElementById("chkbxUsrId_"+i).checked){
					checked++;
					usersList+=document.getElementById("chkbxUsrId_"+i).value+",";
				}
				else notChecked=true;
				i++;
			}
			jQuery("#nbrOfUsers").html(((notChecked)?checked:"<spring:message code='@MODULE_ID@.all'/>"));
			buildUsersList(usersList);
		}

		function buildUsersList(list){
			jQuery("#users").val(list);
		}
	</script>
	
<%@ include file="/WEB-INF/template/footer.jsp" %>