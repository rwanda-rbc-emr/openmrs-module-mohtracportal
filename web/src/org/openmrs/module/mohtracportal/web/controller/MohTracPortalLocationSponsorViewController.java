/**
 * 
 */
package org.openmrs.module.mohtracportal.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.SponsorLocation;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;
import org.openmrs.module.mohtracportal.util.ContextProvider;
import org.openmrs.module.mohtracportal.util.MohTracConfigurationUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Administrator
 * 
 */
public class MohTracPortalLocationSponsorViewController extends
		ParameterizableViewController {

	private Log log = LogFactory.getLog(this.getClass());

	private List<Integer> res;

	private List<Integer> numberOfPages;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (Context.getAuthenticatedUser() == null)
			return new ModelAndView(new RedirectView(request.getContextPath()
					+ "/login.htm"));
		
		ModelAndView mav = new ModelAndView(getViewName());

		int pageSize = MohTracConfigurationUtil.getNumberOfRecordPerPage();
		String pageNumber = request.getParameter("page");
		List<SponsorLocation> data = new ArrayList<SponsorLocation>();

		MohTracPortalService service = Context.getService(MohTracPortalService.class);

		try {
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
			if (pageNumber.compareToIgnoreCase("1") == 0
					|| pageNumber.compareToIgnoreCase("") == 0) {

				res = new ArrayList<Integer>();
				if (request.getParameter("sponsorId") != null) {
					int sponsorId = Integer.valueOf(request
							.getParameter("sponsorId"));
					res = service.getLocationBySponsorId(sponsorId);
					mav.addObject("sponsorName", service.getSponsorById(
							sponsorId).getName());
					log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
							+ service.getSponsorById(sponsorId).getName());
				} else if (request.getParameter("locationId") != null) {
					int locationId = Integer.valueOf(request
							.getParameter("locationId"));
					res = service.getSponsorListByLocationId(locationId);
					mav.addObject("locationName", Context.getLocationService()
							.getLocation(locationId).getName());
					log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
							+ Context.getLocationService().getLocation(
									locationId).getName());
				} else
					res = service.getAllLocationSponsorIds();

				// data collection
				for (int i = 0; i < pageSize; i++) {
					if (res.size() == 0)
						break;
					if (i >= res.size() - 1) {
						data.add(service.getLocationSponsorById(res.get(i)));
						break;
					} else
						data.add(service.getLocationSponsorById(res.get(i)));
				}

				// paging
				int n = (res.size() == ((int) (res.size() / pageSize))
						* pageSize) ? (res.size() / pageSize) : ((int) (res
						.size() / pageSize)) + 1;
				numberOfPages = new ArrayList<Integer>();
				for (int i = 1; i <= n; i++) {
					numberOfPages.add(i);
				}

			} else {
				for (int i = (pageSize * (Integer.parseInt(pageNumber) - 1)); i < pageSize
						* (Integer.parseInt(pageNumber)); i++) {
					if (i >= res.size())
						break;
					else
						data.add(service.getLocationSponsorById(res.get(i)));
				}
			}

			// page infos
			Object[] pagerInfos = new Object[3];
			pagerInfos[0] = (res.size() == 0) ? 0 : (pageSize * (Integer
					.parseInt(pageNumber) - 1)) + 1;
			pagerInfos[1] = (pageSize * (Integer.parseInt(pageNumber)) <= res
					.size()) ? pageSize * (Integer.parseInt(pageNumber)) : res
					.size();
			pagerInfos[2] = res.size();

			ApplicationContext appContext = ContextProvider
					.getApplicationContext();

			String pageInf = appContext.getMessage(
					"mohtracportal.pagingInfo.showingResults", pagerInfos,
					Context.getLocale());
			// log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+pageInf);

			mav.addObject("numberOfPages", numberOfPages);
			mav.addObject("locationSponsors", data);
			mav.addObject("pageSize", pageSize);
			mav.addObject("pageInfos", pageInf);

			if (Integer.valueOf(pageNumber) > 1)
				mav.addObject("prevPage", (Integer.valueOf(pageNumber)) - 1);
			else
				mav.addObject("prevPage", -1);
			if (Integer.valueOf(pageNumber) < numberOfPages.size())
				mav.addObject("nextPage", (Integer.valueOf(pageNumber)) + 1);
			else
				mav.addObject("nextPage", -1);
			mav.addObject("lastPage",
					((numberOfPages.size() >= 1) ? numberOfPages.size() : 1));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mav;
	}

}
