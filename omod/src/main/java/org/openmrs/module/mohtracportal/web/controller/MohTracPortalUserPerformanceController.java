/**
 * 
 */
package org.openmrs.module.mohtracportal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.api.context.Context;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracPortalUserPerformanceController extends
		ParameterizableViewController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		if (Context.getAuthenticatedUser() == null)
			return new ModelAndView(new RedirectView(request.getContextPath()
					+ "/login.htm"));
		
		mav.setViewName(getViewName());

		if (request.getParameter("user") == null){
			mav.addObject("currentUser", Context.getAuthenticatedUser());
			mav.addObject("curUserId", Context.getAuthenticatedUser().getUserId());
		}
		else
			mav.addObject("currentUser", Context.getUserService().getUser(
					Integer.valueOf(request.getParameter("user"))));
		if (Context.getAuthenticatedUser().hasPrivilege("View Users"))
			mav.addObject("users", Context.getUserService().getAllUsers());

//		if (Context.getAuthenticatedUser().hasPrivilege("View Users"))
//			mav.addObject("viewAllowed", Context.getAuthenticatedUser().hasPrivilege("View TRAC Portal Users performance"));

		return mav;
	}

}
