/**
 * 
 */
package org.openmrs.module.mohtracportal.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.layout.web.LayoutSupport;
import org.openmrs.layout.web.address.AddressSupport;
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

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAndView mav = super.handleRequest(request, response);
		String portletPath = "/module/mohtracportal/portlets/welcome";
		
		mav.setViewName(portletPath);

		return mav;
	}

}
