/**
 * 
 */
package org.openmrs.module.mohtracportal.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.Sponsor;
import org.openmrs.module.mohtracportal.SponsorLocation;
import org.openmrs.module.mohtracportal.SponsorEditor;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;
import org.openmrs.module.mohtracportal.util.MohTracUtil;
import org.openmrs.propertyeditor.LocationEditor;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracPortalLocationSponsorFormController extends
		SimpleFormController {

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Location.class, new LocationEditor());
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
				OpenmrsUtil.getDateFormat(), true));
		binder.registerCustomEditor(Sponsor.class, new SponsorEditor());
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		SponsorLocation spLoc = new SponsorLocation();
		MohTracPortalService service = Context.getService(MohTracPortalService.class);

		try {
			if (request.getParameter("sponsorLocationId") == null)
				spLoc = new SponsorLocation();
			else
				spLoc = service.getLocationSponsorById(Integer.valueOf(request
						.getParameter("sponsorLocationId")));
		} catch (Exception ex) {
			String msg = getMessageSourceAccessor().getMessage("fail.to.load");
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,
					msg);
			ex.printStackTrace();
		}
		return spLoc;
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("sponsors", MohTracUtil.createSponsorsOptions());

		return map;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ModelAndView mav = new ModelAndView(new RedirectView(request
				.getContextPath()
				+ getSuccessView()));
		MohTracPortalService service = Context.getService(MohTracPortalService.class);

		try {
			SponsorLocation spLoc = (SponsorLocation) command;
			spLoc.setCreatedBy(Context.getAuthenticatedUser());
			spLoc.setDateCreated(new Date());

			service.saveLocationSponsor(spLoc);

			String msg = getMessageSourceAccessor().getMessage("Form.saved");
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,
					msg);
		} catch (Exception ex) {
			String msg = getMessageSourceAccessor()
					.getMessage("Form.not.saved");
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,
					msg);
			ex.printStackTrace();
		}

		return mav;
	}

}
