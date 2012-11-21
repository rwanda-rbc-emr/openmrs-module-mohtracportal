<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ taglib prefix="mohtractag" uri="/WEB-INF/view/module/mohtracportal/taglibs/mohtractag.tld" %>

<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/scripts/jquery-1.3.2.js" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/tracportal.css" />

<openmrs:require privilege="Manage MOH-TRAC Portal Configurations" otherwise="/login.htm" redirect="/module/@MODULE_ID@/configuration.form" />

<!-- include file="template/tracPortalHeader.jsp" -->

<form action="configuration.form?save=true" method="post" style="width:90%; margin:6px auto;">
	<h2><spring:message code="@MODULE_ID@.config.title"/></h2>
	<br/>
	
	<c:if test="${mohtracConfigured.propertyValue=='false' || mohtracConfigured.propertyValue=='FALSE' || mohtracConfigured.propertyValue=='False'}">
		<div style="border: 1px solid red; padding: 3px; -moz-border-radius: 3px; font-size: 0.8em;">
			<spring:message code="@MODULE_ID@.config.notice"/>
		</div>
	</c:if>
	
	<c:if test="${mohtracConfigured.propertyValue=='true' || mohtracConfigured.propertyValue=='TRUE' || mohtracConfigured.propertyValue=='True'}">
		<div class="box"><spring:message code="@MODULE_ID@.config.configured"/></div>
	</c:if>
	<br/>
	
	<b class="boxHeader"><spring:message code="@MODULE_ID@.config.general"/></b>
	<div class="box">
		<table class="configTable">
			<tr class="even">
				<td class="configDescription"><spring:message code="@MODULE_ID@.config.recordperpage"/></td>
				<td class="configSelect"><select name="recordOnAPage">
					<option value="15" <c:if test="${recordOnPage.propertyValue==15}">selected='selected'</c:if>>15</option>
					<option value="25" <c:if test="${recordOnPage.propertyValue==25}">selected='selected'</c:if>>25</option>
					<option value="35" <c:if test="${recordOnPage.propertyValue==35}">selected='selected'</c:if>>35</option>
					<option value="50" <c:if test="${recordOnPage.propertyValue==50}">selected='selected'</c:if>>50</option>
					<option value="100" <c:if test="${recordOnPage.propertyValue==100}">selected='selected'</c:if>>100</option>
				</select>
				</td>
			</tr>
			<tr class="even">
				<td colspan="2" class="configComment">${recordOnPage.description}</td>					
			</tr>
			<tr>
				<td class="configDescription"><spring:message code="@MODULE_ID@.config.location.default"/></td>
				<td class="configSelect"><select name="defaultLocation">
					<option value="0">--</option>
					<c:forEach var="location" items="${locations}">
						<option value="${location.key}" <c:if test="${defaultLocationId.propertyValue==location.key}">selected='selected'</c:if>>${location.value}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="configComment">${defaultLocationId.description}</td>					
			</tr>
			<tr class="even">
				<td class="configDescription"><spring:message code="@MODULE_ID@.config.cd4count.critical"/></td>
				<td class="configSelect"><input type="text" size="5" value="${criticalLevelOfCD4Count.propertyValue}" name="criticalLevelOfCD4Count"/>
				</td>
			</tr>
			<tr class="even">
				<td colspan="2" class="configComment">${criticalLevelOfCD4Count.description}</td>					
			</tr>
		</table>
	</div>
	<br/>
	
	<b class="boxHeader"><spring:message code="@MODULE_ID@.config.identifiertype"/></b>
	<div class="box">		
		<table class="configTable">
			<c:forEach items="${gp_identifierTypes}" var="identifier" varStatus="status">
				<tr class="<c:if test="${(status.count)%2==0}">even</c:if>">
					<td class="configDescription"><c:if test="${identifier!=null}">${mohtractag:identifierTypeNameByIdAsString(identifier.propertyValue)}</c:if></td>
					<td class="configSelect"><select name="${mohtractag:gpParser(identifier.property)}">
							<option value="">--</option>
							<c:forEach items="${identifierTypes}" var="identifierType">
								<option value="${identifierType.key}" <c:if test="${identifier.propertyValue==identifierType.key}">selected='selected'</c:if>>${identifierType.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="<c:if test="${(status.count)%2==0}">even</c:if>">
					<td colspan="2" class="configComment">${identifier.description}</td>					
				</tr>
			</c:forEach>
		</table>
	</div>
	<br/>
	
	<b class="boxHeader"><spring:message code="@MODULE_ID@.config.program"/></b>
	<div class="box">
		<table class="configTable">
			<tr class="even">
				<td class="configDescription"><spring:message code="@MODULE_ID@.config.program.hiv"/></td>
				<td class="configSelect"><select name="hiv_program">
					<option value="0">--</option>
					<c:forEach var="program" items="${programs}">
						<option value="${program.key}" <c:if test="${hiv_program.propertyValue==program.key}">selected='selected'</c:if>>${program.value}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr class="even">
				<td colspan="2" class="configComment">${hiv_program.description}</td>					
			</tr>
			<tr>
				<td class="configDescription"><spring:message code="@MODULE_ID@.config.program.pmtct"/></td>
				<td class="configSelect"><select name="pmtct_program">
					<option value="0">--</option>
					<c:forEach var="program" items="${programs}">
						<option value="${program.key}" <c:if test="${pmtct_program.propertyValue==program.key}">selected='selected'</c:if>>${program.value}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="configComment">${pmtct_program.description}</td>					
			</tr>
		</table>
	</div><br/>
	
	<b class="boxHeader"><spring:message code="@MODULE_ID@.config.attributes"/></b>
	<div class="box">
		<table class="configTable">
			<c:forEach items="${gp_attributeTypes}" var="attribute" varStatus="status">
				<tr class="<c:if test="${(status.count)%2==0}">even</c:if>">
					<td class="configDescription"><c:if test="${attribute!=null}">${mohtractag:attributeTypeNameByIdAsString(attribute.propertyValue)}</c:if></td>
					<td class="configSelect"><select name="${mohtractag:gpParser(attribute.property)}">
							<option value="">--</option>
							<c:forEach items="${attributeTypes}" var="attributeType">
								<option value="${attributeType.key}" <c:if test="${attribute.propertyValue==attributeType.key}">selected='selected'</c:if>>${attributeType.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="<c:if test="${(status.count)%2==0}">even</c:if>">
					<td colspan="2" class="configComment">${attribute.description}</td>					
				</tr>
			</c:forEach>
		</table>
	</div><br/>
	
	<span title="${mohtracConfigured.description}"><input <c:if test="${mohtracConfigured.propertyValue=='true' || mohtracConfigured.propertyValue=='TRUE'}">checked='checked'</c:if> type="checkbox" name="config_chkbx" id="config_chkbx_id" value="1"/><label for="config_chkbx_id"><spring:message code="@MODULE_ID@.config.configured"/></label></span>
	
	<br/><br/>
	<input type="button" value="<spring:message code='@MODULE_ID@.config.save'/>" id="btSave"/>
	<br/><br/>

</form>

<script>

$(document).ready( function() {
	$("#btSave").click(function(){
		if(confirm("<spring:message code='@MODULE_ID@.general.message.confirm.save'/>"))
			this.form.submit();
		});
});

</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>