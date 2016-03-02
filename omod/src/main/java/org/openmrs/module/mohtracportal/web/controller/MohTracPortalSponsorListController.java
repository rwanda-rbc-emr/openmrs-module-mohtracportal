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
import org.openmrs.module.mohtracportal.Sponsor;
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
public class MohTracPortalSponsorListController extends
		ParameterizableViewController {
	private Log log = LogFactory.getLog(this.getClass());

	private List<Sponsor> res;

	private List<Integer> numberOfPages;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if (Context.getAuthenticatedUser() == null)
			return new ModelAndView(new RedirectView(request.getContextPath()
					+ "/login.htm"));
		
		ModelAndView mav = new ModelAndView(getViewName());
		List<Sponsor> data = new ArrayList<Sponsor>();

		MohTracPortalService service = Context.getService(MohTracPortalService.class);

		try {
			String pageNumber = request.getParameter("page");
			int pageSize = MohTracConfigurationUtil.getNumberOfRecordPerPage();
		
			if (pageNumber.compareToIgnoreCase("1") == 0
					|| pageNumber.compareToIgnoreCase("") == 0) {
				res = new ArrayList<Sponsor>();
				res = service.getAllSponsors();

				// data collection
				for (int i = 0; i < pageSize; i++) {
					if (res.size() == 0)
						break;
					if (i >= res.size() - 1) {
						data.add(res.get(i));
						break;
					} else
						data.add(res.get(i));
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
						data.add(res.get(i));
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

			mav.addObject("numberOfPages", numberOfPages);
			mav.addObject("sponsors", data);
			mav.addObject("pageSize", pageSize);
			mav.addObject("pageInfos", appContext.getMessage(
					"mohtracportal.pagingInfo.showingResults", pagerInfos,
					Context.getLocale()));
			
//			log.info("===================================>Data :"+data.size()+" /pgSize :"+pageSize+" /pgrInfo :"+pagerInfos);
			
		} catch (Exception ex) {
			log.error("===============================>> An error occured when trying to load data: "+ex.getMessage());
			ex.printStackTrace();
		}

		return mav;
	}
}
