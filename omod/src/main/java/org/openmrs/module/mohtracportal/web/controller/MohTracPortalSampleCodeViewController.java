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
import org.openmrs.module.mohtracportal.SampleCode;
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
public class MohTracPortalSampleCodeViewController extends
		ParameterizableViewController {

	private Log log = LogFactory.getLog(this.getClass());

	private List<String> res;

	private List<Integer> numberOfPages;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (Context.getAuthenticatedUser() == null)
			return new ModelAndView(new RedirectView(request.getContextPath()
					+ "/login.htm"));
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewName());
		
		int pageSize = MohTracConfigurationUtil.getNumberOfRecordPerPage();
		String pageNumber = request.getParameter("page");
		List<SampleCode> data = new ArrayList<SampleCode>();

		MohTracPortalService service = Context.getService(MohTracPortalService.class);

		try {

			if (pageNumber.compareToIgnoreCase("1") == 0
					|| pageNumber.compareToIgnoreCase("") == 0) {

				res = new ArrayList<String>();
				if(request.getParameter("status")!=null)
				{
					String status=request.getParameter("status");
//					if(request.getParameter("status").compareToIgnoreCase("incomplete")==0){
						res = service.getAllSampleCodesByResultStatus(status);						
//					}else if(request.getParameter("status").compareToIgnoreCase("complete")==0){
//						
//					}
				}
				
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Page :"+pageNumber);
								
				// data collection
				for (int i = 0; i < pageSize; i++) {
					if (res.size() == 0)
						break;
					if (i >= res.size() - 1) {
						data.add(service.getSampleTestBySampleCode(res.get(i)));
						break;
					} else
						data.add(service.getSampleTestBySampleCode(res.get(i)));
				}
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Collection done :"+data.size());

				// paging
				int n = (res.size() == ((int) (res.size() / pageSize))
						* pageSize) ? (res.size() / pageSize) : ((int) (res
						.size() / pageSize)) + 1;
				numberOfPages = new ArrayList<Integer>();
				for (int i = 1; i <= n; i++) {
					numberOfPages.add(i);
				}
				
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Paging done :"+numberOfPages.size());

			} else {
				for (int i = (pageSize * (Integer.parseInt(pageNumber) - 1)); i < pageSize
						* (Integer.parseInt(pageNumber)); i++) {
					if (i >= res.size())
						break;
					else
						data.add(service.getSampleTestBySampleCode(res.get(i)));
				}
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Page :"+pageNumber);
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Collection done :"+data.size());
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
			
			String pageInf=appContext.getMessage(
					"mohtracportal.pagingInfo.showingResults", pagerInfos,
					Context.getLocale());
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+pageInf);
			
			
			mav.addObject("numberOfPages", numberOfPages);
			mav.addObject("sampletests", data);
			mav.addObject("pageSize", pageSize);
			mav.addObject("pageInfos", pageInf);
			
			if(Integer.valueOf(pageNumber)>1)
				mav.addObject("prevPage", (Integer.valueOf(pageNumber))-1);
			else mav.addObject("prevPage", -1);
			if(Integer.valueOf(pageNumber)<numberOfPages.size())
				mav.addObject("nextPage", (Integer.valueOf(pageNumber))+1);
			else mav.addObject("nextPage", -1);
			mav.addObject("lastPage", ((numberOfPages.size()>=1)?numberOfPages.size():1));
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mav;
	}
}
