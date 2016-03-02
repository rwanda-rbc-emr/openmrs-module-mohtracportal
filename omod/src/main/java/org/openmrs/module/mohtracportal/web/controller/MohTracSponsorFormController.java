/**
 * 
 */
package org.openmrs.module.mohtracportal.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.Sponsor;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;
import org.openmrs.web.WebConstants;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Administrator
 * 
 */
public class MohTracSponsorFormController extends SimpleFormController {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// TODO Auto-generated method stub
		super.initBinder(request, binder);
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		MohTracPortalService service = Context.getService(MohTracPortalService.class);
		Sponsor sponsor = null;
		if (request.getParameter("sponsorId") != null)
			sponsor = service.getSponsorById(Integer.valueOf(request
					.getParameter("sponsorId")));
		else
			sponsor = new Sponsor();

		return sponsor;
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		return map;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ModelAndView mav = new ModelAndView(new RedirectView(request
				.getContextPath()
				+ getSuccessView()));

		MohTracPortalService service = Context.getService(MohTracPortalService.class);
		Sponsor sponsor = (Sponsor) command;
		
		try {
			sponsor.setDateCreated(new Date());
			sponsor.setCreator(Context.getAuthenticatedUser());
			service.saveSponsor(sponsor);
			
			String msg = getMessageSourceAccessor().getMessage("Form.saved");
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,
					msg);
		} catch (Exception e) {
			log.error("================> An error occured when trying to save : "+e.getMessage());
			e.printStackTrace();
			String msg = getMessageSourceAccessor().getMessage("Form.not.saved");
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,
					msg);
		}
		
		return mav;
	}

}
