<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/tracportal.css" />

<center>
<img src="${pageContext.request.contextPath}<spring:theme code="image.logo.large"/>" alt='<spring:message code="openmrs.title"/>' title='<spring:message code="openmrs.title"/>'/>
	
	<br/><br/><br/>
	
	<openmrs:portlet url="welcome" parameters="showName=true|showLogin=true" />
</center>

<openmrs:hasPrivilege privilege="View MOH-TRAC Portal">

	<script type='text/javascript'>
		function start(){
			window.location="module/mohtracportal/tracportal.htm";
		}
	
		setTimeout(start,4000);
	
	</script>
	
</openmrs:hasPrivilege>