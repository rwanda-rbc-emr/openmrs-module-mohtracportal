/**
 * 
 */
package org.openmrs.module.mohtracportal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.util.MohTracConfigurationUtil;
import org.openmrs.module.mohtracportal.util.MohTracPortalTag;
import org.openmrs.module.mohtracportal.util.MohTracUtil;
import org.openmrs.web.WebConstants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracModulesConfigurations extends ParameterizableViewController {
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if (Context.getAuthenticatedUser() == null)
			return new ModelAndView(new RedirectView(request.getContextPath()
					+ "/login.htm"));
		
		ModelAndView mav = new ModelAndView(getViewName());
		if (request.getParameter("save") != null && request.getParameter("save") != "true")
			saveVCTConfiguration(request, mav);
		
		mav.addObject("programs", MohTracUtil.createProgramOptions());
		mav.addObject("mohtracConfigured", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.configured"));
		mav.addObject("localIdentifierType", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.identifierType.hc_localid"));
		mav.addObject("hiv_program", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.program.hiv"));
		mav.addObject("pmtct_program", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.program.pmtct"));
		mav.addObject("recordOnPage", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.recordperpage"));
		mav.addObject("criticalLevelOfCD4Count", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.cd4count.criticallevel"));
		mav.addObject("defaultLocationId", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.defaultLocationId"));
		mav.addObject("nidIdentifierType", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.identifierType.nid"));
		mav.addObject("tracnetIdentifierType", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.identifierType.tracnet"));
		mav.addObject("localIdentifierType", Context.getAdministrationService().getGlobalPropertyObject("mohtracportal.identifierType.hc_localid"));
		mav.addObject("identifierTypes", MohTracUtil.createIdentifierTypesOptions());
		mav.addObject("locations", MohTracUtil.createLocationOptions());
		mav.addObject("attributeTypes", MohTracUtil.createAttributesOptions());
		mav.addObject("gp_attributeTypes", MohTracConfigurationUtil.getNeededAttributeType());
		//log.info("===============> "+MohTracConfigurationUtil.getNeededAttributeType()+" : "+MohTracUtil.createAttributesOptions());
		mav.addObject("gp_identifierTypes", MohTracConfigurationUtil.getNeededIdentifierType());
		//log.info("===============> "+MohTracConfigurationUtil.getNeededIdentifierType()+" : "+MohTracUtil.createIdentifierTypesOptions());

		return mav;
	}
	
	private void saveVCTConfiguration(HttpServletRequest request, ModelAndView mav) {
		AdministrationService as = Context.getAdministrationService();
		
		try {
			//config_chkbx
			GlobalProperty gpModuelesConfig = as.getGlobalPropertyObject("mohtracportal.configured");
			gpModuelesConfig.setPropertyValue("" + (request.getParameter("config_chkbx") != null));
			as.saveGlobalProperty(gpModuelesConfig);
			
			//helpmessage
			GlobalProperty gpHelpMessage = as.getGlobalPropertyObject("mohtracportal.helpmessage.display");
			gpHelpMessage.setPropertyValue("" + (request.getParameter("displayHelpMessage") != null));
			as.saveGlobalProperty(gpHelpMessage);
			
			//program hiv
			GlobalProperty gpProgramHiv = as.getGlobalPropertyObject("mohtracportal.program.hiv");
			gpProgramHiv.setPropertyValue(request.getParameter("hiv_program"));
			as.saveGlobalProperty(gpProgramHiv);
			
			//program pmtct
			GlobalProperty gpProgramPmtct = as.getGlobalPropertyObject("mohtracportal.program.pmtct");
			gpProgramPmtct.setPropertyValue(request.getParameter("pmtct_program"));
			as.saveGlobalProperty(gpProgramPmtct);
			
			//number of record on a page
			GlobalProperty gpRecordperpage = as.getGlobalPropertyObject("mohtracportal.recordperpage");
			gpRecordperpage.setPropertyValue(request.getParameter("recordOnAPage"));
			as.saveGlobalProperty(gpRecordperpage);
			
			//critical level of cd4 count
			GlobalProperty gpCriticalLevelOfCD4Count = as.getGlobalPropertyObject("mohtracportal.cd4count.criticallevel");
			gpCriticalLevelOfCD4Count.setPropertyValue(request.getParameter("criticalLevelOfCD4Count"));
			as.saveGlobalProperty(gpCriticalLevelOfCD4Count);
			
			//default location id
			GlobalProperty gpDefaultLocation = as.getGlobalPropertyObject("mohtracportal.defaultLocationId");
			gpDefaultLocation.setPropertyValue(request.getParameter("defaultLocation"));
			as.saveGlobalProperty(gpDefaultLocation);
			
			//identifiertype id
			for(GlobalProperty gp:MohTracConfigurationUtil.getNeededIdentifierType()){
				//GlobalProperty gpNIDIdentifierType = as.getGlobalPropertyObject("mohtracportal.identifierType.nid");
				gp.setPropertyValue(""+ request.getParameter(MohTracPortalTag.globalPropertyParser(gp.getProperty())));
				as.saveGlobalProperty(gp);
			}
			
			for(GlobalProperty gp:MohTracConfigurationUtil.getNeededAttributeType()){
				gp.setPropertyValue("" + request.getParameter(MohTracPortalTag.globalPropertyParser(gp.getProperty())));
				as.saveGlobalProperty(gp);
			}
			
//			String msg = getMessageSourceAccessor().getMessage();
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Form.saved");
		}
		catch (Exception ex) {
//			String msg = getMessageSourceAccessor().getMessage();
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Form.not.saved");
			
			log.error("An error occured when trying to save the MoH-TRAC Modules Configurations: \n");
			ex.printStackTrace();
		}
		
	}
	
}
