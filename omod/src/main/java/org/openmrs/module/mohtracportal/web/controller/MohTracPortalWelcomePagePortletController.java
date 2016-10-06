/**
 * 
 */
package org.openmrs.module.mohtracportal.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.api.context.Context;
import org.openmrs.layout.web.LayoutSupport;
import org.openmrs.layout.web.address.AddressSupport;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;
import org.openmrs.module.reporting.report.Report;
import org.openmrs.web.controller.layout.LayoutPortletController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Yves GAKUBA
 *
 */
public class MohTracPortalWelcomePagePortletController extends LayoutPortletController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.web.controller.layout.LayoutPortletController#
	 * getLayoutSupportInstance()
	 */
	@Override
	protected LayoutSupport getLayoutSupportInstance() {
		return AddressSupport.getInstance();
	}

	private void addLostToFollowUpReportToModel(ModelAndView mav) {
		Report lostFollowUpreport = Context.getService(MohTracPortalService.class)
				.executeAndGetAdultArtMonthlyWhichIncludesAdultFollowUpReport();
				
		mav.addObject("lostToFollowup", lostFollowUpreport != null ? lostFollowUpreport : "");
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAndView mav = super.handleRequest(request, response);
		String portletPath = "/module/mohtracportal/portlets/welcome";
		mav.setViewName(portletPath);

		// Map<String, Object> mohPortalObjects = new HashMap<String, Object>();
		//
		// mohPortalObjects.put("authUser", Context.getAuthenticatedUser());
		//
		// request.setAttribute("mohPortalObjects", mohPortalObjects);
		addLostToFollowUpReportToModel(mav);
		
		return mav;
	}

}
