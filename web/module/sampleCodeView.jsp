<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ taglib prefix="mohtractag" uri="/WEB-INF/view/module/mohtracportal/taglibs/mohtractag.tld" %>
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/tracportal.css" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/listingstyle.css" />

<%@ include file="template/localHeader.jsp"%>

<openmrs:require privilege="View lab results" otherwise="/login.htm" redirect="/module/@MODULE_ID@/samplecodemanagement.list?status=all&page=1" />

<h2><spring:message code="@MODULE_ID@.test.list.title"/></h2>
<br/>

<table width="300px">
	<tr>
		<td width="100px" style="border: 1px solid #cccccc; padding:1px 2px 1px 2px; vertical-align: text-top;"><a href="samplecodemanagement.list?status=all&page=1"><div id="allTestDivId" class="menuTab <c:if test="${param.status=='all'}">menuTabCurrent</c:if>">All</div></a></td>
		<td width="100px" style="border: 1px solid #cccccc; padding:1px 2px 1px 2px; vertical-align: text-top;"><a href="samplecodemanagement.list?status=complete&page=1"><div id="completeTestDivId" class="menuTab <c:if test="${param.status=='complete'}">menuTabCurrent</c:if>">Complete</div></a></td>
		<td width="100px" style="border: 1px solid #cccccc; padding:1px 2px 1px 2px; vertical-align: text-top;"><a href="samplecodemanagement.list?status=incomplete&page=1"><div id="incompleteTestDivId" class="menuTab <c:if test="${param.status=='incomplete'}">menuTabCurrent</c:if>">Incomplete</div></a></td>
	</tr>
</table>

<div style="margin:5px; font-size: 0.9em;">
	<div id="list_container" style="width: 99%">
		<div id="list_title">
			<div class="list_title_msg"><spring:message code="@MODULE_ID@.test.listing"/></div>
			<div class="list_title_bts">
				<!-- <span>
					<form action="patientList.htm?export=csv&${parameters}" method="post" style="display: inline;">
						<input type="submit" class="list_exportBt" value="<spring:message code="@MODULE_ID@.patientlist.xprt.csv"/>" title="<spring:message code="@MODULE_ID@.patientlist.csv"/>"/>
					</form>&nbsp;&nbsp;
					<form action="patientList.htm?export=pdf&${parameters}" method="post" style="display: inline;">
						<input type="submit" class="list_exportBt" value="<spring:message code="@MODULE_ID@.patientlist.xprt.pdf"/>" title="<spring:message code="@MODULE_ID@.patientlist.pdf"/>"/>
					</form>
				</span> -->
			</div>
			<div style="clear:both;"></div>
		</div>
		<table id="list_data">
			<tr>
				<th width="8%" class="columnHeader"><spring:message code="@MODULE_ID@.test.date"/></th>
				<th width="3%" class="columnHeader numberCol">#.</th>
				<th width="10%" class="columnHeader"><spring:message code="@MODULE_ID@.test.code"/></th>
				<th width="10%" class="columnHeader"><spring:message code="Encounter.location"/></th>
				<openmrs:hasPrivilege privilege="View Patient Names">
					<th width="20%" class="columnHeader"><spring:message code="@MODULE_ID@.person.names"/></th>
				</openmrs:hasPrivilege>
				<th width="20%" class="columnHeader"><spring:message code="@MODULE_ID@.test.name"/></th>
				<th width="15%" class="columnHeader"><spring:message code="@MODULE_ID@.test.result"/></th>
				<th width="14%" class="columnHeader"><spring:message code="@MODULE_ID@.test.comment"/></th>
			</tr>
			<c:if test="${empty sampletests}"><tr><td colspan="8" width="100%" style="text-align: center;"><spring:message code="@MODULE_ID@.tablelist.empty"/></td></tr></c:if>
			<c:set value="0" var="index"/>
			<c:forEach items="${sampletests}" var="sample" varStatus="status">
				<tr>
					<c:choose>
					  <c:when test="${sample.testTaken.obsDatetime == currentDate}">
					   	<td class="rowValue" <c:if test="${index%2!=0}">style="background-color: whitesmoke;"</c:if>><c:if test="${sample.testTaken.obsDatetime!=currentDate}"><openmrs:formatDate date="${sample.testTaken.obsDatetime}" type="medium"/><c:set value="${sample.testTaken.obsDatetime}" var="currentDate"/></c:if></td>
					  </c:when>
					  <c:otherwise>
					  	<c:set value="${index+1}" var="index"/>
					   	<td class="rowValue" style="border-top: 1px solid cadetblue; <c:if test="${index%2!=0}">background-color: whitesmoke;</c:if>"><c:if test="${sample.testTaken.obsDatetime!=currentDate}"><openmrs:formatDate date="${sample.testTaken.obsDatetime}" type="medium"/><c:set value="${sample.testTaken.obsDatetime}" var="currentDate"/></c:if></td>
					  </c:otherwise>
					</c:choose>
					<!-- <td class="rowValue ${status.count%2!=0?'even':''}"><openmrs:formatDate date="${sample.testTaken.obsDatetime}" type="medium" /></td> -->
					<td class="rowValue ${status.count%2!=0?'even':''}">${((param.page-1)*pageSize)+status.count}.</td>
					<td class="rowValue ${status.count%2!=0?'even':''}">${sample.sampleCode}</td>
					<td class="rowValue ${status.count%2!=0?'even':''}">${sample.testTaken.location.name}</td>
					<openmrs:hasPrivilege privilege="View Patient Names">
						<td class="rowValue ${status.count%2!=0?'even':''}">${sample.testTaken.person.personName}</td>
					</openmrs:hasPrivilege>
					<td class="rowValue ${status.count%2!=0?'even':''}">${sample.testTaken.concept.name}</td>
					<td class="rowValue ${status.count%2!=0?'even':''}"><openmrs:format obsValue="${sample.testTaken}" /></td>
					<td class="rowValue ${status.count%2!=0?'even':''}">${sample.comment}</td>
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
								<a href="samplecodemanagement.list?status=${param.status}&page=1"><div class="list_pageNumber" style="text-align: center;">|&lt; <spring:message code="@MODULE_ID@.first"/></div></a>
							</td>
							<td width="100px" class="" style="padding:1px 2px 1px 2px; vertical-align: text-top;"><a href="samplecodemanagement.list?status=${param.status}&page=${prevPage}">
								<div class="list_pageNumber" style="text-align: center;">&lt;&lt; <spring:message code="general.previous"/></div></a>
							</td>
						</c:if>
						<c:if test="${nextPage!=-1}">
							<td width="100px" class="" style="padding:1px 2px 1px 2px; vertical-align: text-top;">
								<a href="samplecodemanagement.list?status=${param.status}&page=${nextPage}"><div class="list_pageNumber" style="text-align: center;"><spring:message code="general.next"/> &gt;&gt;</div></a>
							</td>
							<td width="100px" class="" style="padding:1px 2px 1px 2px; vertical-align: text-top;">
								<a href="samplecodemanagement.list?status=${param.status}&page=${lastPage}"><div class="list_pageNumber" style="text-align: center;"><spring:message code="@MODULE_ID@.last"/> &gt;|</div></a>
							</td>
						</c:if>
					</tr>
				</table>		
			</div>
			<div style="clear: both"></div>
		</div>
	</div>
		
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>