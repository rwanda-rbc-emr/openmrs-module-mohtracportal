<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/scripts/jquery-1.3.2.js" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/scripts/jquery.cookie.js" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/scripts/jquery.treeview.js" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/jquery.treeview.css" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/tracportal.css" />

<openmrs:require privilege="View MOH-TRAC Portal" otherwise="/login.htm" redirect="/module/@MODULE_ID@/tracPortal.htm" />

<div style="font-size: 0.9em;">
	
	<div id="tracAdminListContent">
		
		<h1><spring:message code="@MODULE_ID@.activities" /></h1>
		<br />
		
		<div id="patientTabs">
			<ul>
				<openmrs:hasPrivilege privilege="View TRAC Portal modules functionalities">
					<li><a hidefocus="hidefocus" onclick="return changeTab(this);" href="#" id="moduleTab" class="current "><spring:message code="@MODULE_ID@.modules"/></a></li>
					<c:set var="canViewModules" value="1"/>
				</openmrs:hasPrivilege>
				<openmrs:hasPrivilege privilege="View TRAC Portal Administration Page">
					<li><a hidefocus="hidefocus" onclick="return changeTab(this);" href="#" id="administrationTab"><spring:message code="@MODULE_ID@.administration"/></a></li>
				</openmrs:hasPrivilege>
				<li><a hidefocus="hidefocus" onclick="return changeTab(this);" href="#" <c:if test="${canViewModules eq null}">class="current "</c:if> id="generalTab"><spring:message code="@MODULE_ID@.general"/></a></li>					
			</ul>
		</div>
		
		<openmrs:hasPrivilege privilege="View TRAC Portal modules functionalities">
			<div id="module" style="padding-left: 10px;">
				<ul id="menuList" class="treeview-red">
					<openmrs:extensionPoint pointId="org.openmrs.tracmodule.list" type="html">
						<openmrs:hasPrivilege privilege="${extension.requiredPrivilege}">
				
							<li><span style="font-size: 1.1em; font-weight: bold;"><spring:message code="${extension.title}" /></span>
							<ul>
								<c:forEach items="${extension.links}" var="link">
									<c:choose>
										<c:when test="${fn:startsWith(link.key, 'module/')}">
											<%-- Added for backwards compatibility for most links --%>
											<li><span><a href="${pageContext.request.contextPath}/${link.key}"><spring:message code="${link.value}" /></span></a></li>
										</c:when>
										<c:otherwise>
											<%-- Allows for external absolute links  --%>
											<li><span><a href='<c:url value="${link.key}"/>'><spring:message code='${link.value}' /></span></a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
							</li>
				
						</openmrs:hasPrivilege>
					</openmrs:extensionPoint>
				</ul>
			</div>
		</openmrs:hasPrivilege>
		
			<div id="general" style="<c:if test="${canViewModules ne null}">display:none;</c:if> padding-left: 10px;">
				<c:remove var="canViewModules"/>
				<div class="adminMenuList">			
					<h4><spring:message code="@MODULE_ID@.users.performance"/></h4>
					<ul id="menu">
						<li><a href="performance.htm"><spring:message code="@MODULE_ID@.user.performance"/></a></li>
					</ul>
					
					<openmrs:hasPrivilege privilege="View TRAC Portal patients listing and export">
						<br/>
						<h4><spring:message code="@MODULE_ID@.patient.listing"/></h4>
						<ul id="menu">
							<li><a href="patientList.htm?page=1"><spring:message code="@MODULE_ID@.patientlist.title"/></a></li>
						</ul>
					</openmrs:hasPrivilege>
				</div>
			</div>
		
		<openmrs:hasPrivilege privilege="View TRAC Portal Administration Page">
			<div id="administration" style="display: none; padding-left: 10px;">
				<div class="adminMenuList">
					<openmrs:hasPrivilege privilege="Manage MOH-TRAC Portal Configurations">
						<h4><spring:message code="@MODULE_ID@.configurations"/></h4>
						<ul id="menu">
							<li><a href="configuration.form"><spring:message code="@MODULE_ID@.portal.configuration"/></a></li>
						</ul>
					</openmrs:hasPrivilege>
					
						<openmrs:hasPrivilege privilege="Manage Sponsors,Manage Locations/Sponsors">
							<br/>
							<h4><spring:message code="@MODULE_ID@.locationsponsors"/></h4>
						</openmrs:hasPrivilege>
						<ul id="menu">
							<openmrs:hasPrivilege privilege="Manage Sponsors">
								<li><a href="sponsors.list?page=1"><spring:message code="@MODULE_ID@.sponsors.manage"/></a></li>
							</openmrs:hasPrivilege>
							<openmrs:hasPrivilege privilege="Manage Locations/Sponsors">
								<li><a href="locationsponsor.list?page=1"><spring:message code="@MODULE_ID@.locationsponsors.manage"/></a></li>
							</openmrs:hasPrivilege>
						</ul>
					
					<openmrs:hasPrivilege privilege="View lab results">		
						<br/>
						<h4><spring:message code="@MODULE_ID@.test.samplecode"/></h4>
						<ul id="menu">
							<li><a href="samplecodemanagement.list?status=all&page=1"><spring:message code="@MODULE_ID@.test.sample.manage"/></a></li>
						</ul>
					</openmrs:hasPrivilege>
							
					<br/>
					<h4><spring:message code="@MODULE_ID@.regimen.title"/></h4>
					<ul id="menu">
						<li><a href="${pageContext.request.contextPath}/admin/concepts/conceptDrug.list"><spring:message code="ConceptDrug.manage"/></a></li>
					</ul>
				</div>
			</div>
		</openmrs:hasPrivilege>
	
	</div>

</div>


<script type="text/javascript">

	function changeTab(tabObj) {
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
			/*setTabCookie(tabObj.id);*/
		}
		return false;
	}

	jQuery(document).ready( function() {
		
		jQuery("#menuList").treeview({
			animated: "fast",
			collapsed: true,
			unique: true,
			persist: "cookie",
			toggle: function() {
				window.console && console.log("%o was toggled", this);
			}
		});
	
	});

</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>