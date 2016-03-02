/**
 * 
 */
package org.openmrs.module.mohtracportal.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;
import org.openmrs.module.mohtracportal.util.ContextProvider;
import org.openmrs.module.mohtracportal.util.FileExporter;
import org.openmrs.module.mohtracportal.util.MohTracConfigurationUtil;
import org.openmrs.module.mohtracportal.util.MohTracUtil;
import org.openmrs.web.WebConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Yves GAKUBA
 * 
 */
public class MohTracPatientListController extends ParameterizableViewController {

	protected Log log = LogFactory.getLog(getClass());

	private List<Object> res;

	private List<Integer> numberOfPages;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewName());
		HashMap<String, String> reportProperties=new HashMap<String, String>();

		try {
			if (Context.getAuthenticatedUser() == null)
				return new ModelAndView(new RedirectView(request
						.getContextPath()
						+ "/login.htm"));

			MohTracPortalService service = Context
					.getService(MohTracPortalService.class);
			
			List<User> users = Context.getUserService().getAllUsers();
			mav.addObject("usersList", users);
			
			mav.addObject("tracnetIdentifierTypeId", MohTracConfigurationUtil
					.getTracNetIdentifierTypeId());
			mav.addObject("localIdentifierTypeId", MohTracConfigurationUtil
					.getLocalHealthCenterIdentifierTypeId());

			mav.addObject("numberofUsersPerColumn",
					(((int) users.size() / 5) < 1) ? users.size() : (int) users
							.size() / 5);

			int pageSize = MohTracConfigurationUtil.getNumberOfRecordPerPage();
			String pageNumber = request.getParameter("page");
			List<Object> data = new ArrayList<Object>();
			List<Integer> selectedUsers = new ArrayList<Integer>();
			String parameters = "";

			if (request.getParameter("from") != null
					&& request.getParameter("from").compareTo("") != 0)
				parameters += "&from=" + request.getParameter("from");
			if (request.getParameter("to") != null
					&& request.getParameter("to").compareTo("") != 0)
				parameters += "&to=" + request.getParameter("to");
			if (request.getParameter("users") != null
					&& request.getParameter("users").compareTo("") != 0)
				parameters += "&users=" + request.getParameter("users");
			mav.addObject("parameters", parameters);
			if (request.getParameter("users") != null) {
				selectedUsers = parseUsers(request.getParameter("users"), mav);
			}
			if (pageNumber != null) {
				if (pageNumber.compareToIgnoreCase("1") == 0
						|| pageNumber.compareToIgnoreCase("") == 0) {

					res = service.getPatientIdByRegistrationDate(request
							.getParameter("from"), request.getParameter("to"),
							selectedUsers);

					// data collection
					for (int i = 0; i < pageSize; i++) {
						if (res.size() == 0)
							break;
						if (i >= res.size() - 1) {
							data.add(res.get(i));
							break;
						} else {
							data.add(res.get(i));
						}
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
						.size()) ? pageSize * (Integer.parseInt(pageNumber))
						: res.size();
				pagerInfos[2] = res.size();

				ApplicationContext appContext = ContextProvider
						.getApplicationContext();

				String pageInf = appContext.getMessage(
						"mohtracportal.pagingInfo.showingResults", pagerInfos,
						Context.getLocale());

				mav.addObject("numberOfPages", numberOfPages);
				mav.addObject("patientList", data);
				mav.addObject("pageSize", pageSize);
				mav.addObject("pageInfos", pageInf);

				if (Integer.valueOf(pageNumber) > 1)
					mav
							.addObject("prevPage",
									(Integer.valueOf(pageNumber)) - 1);
				else
					mav.addObject("prevPage", -1);
				if (Integer.valueOf(pageNumber) < numberOfPages.size())
					mav
							.addObject("nextPage",
									(Integer.valueOf(pageNumber)) + 1);
				else
					mav.addObject("nextPage", -1);
				mav
						.addObject("lastPage",
								((numberOfPages.size() >= 1) ? numberOfPages
										.size() : 1));
			}
			
			FileExporter fexp = new FileExporter();

			String from = (request.getParameter("from") == null) ? "" : request
					.getParameter("from");
			String to = (request.getParameter("to") == null) ? "" : request
					.getParameter("to");

			// setting report properties
			
			reportProperties.put("rptType", MohTracUtil.getMessage("mohtracportal.report.list.patient.registered", null));
			reportProperties.put("rptListType", "");
			reportProperties.put("rptDescription", "");
			
			// end settings
			
			if (request.getParameter("export") != null
					&& request.getParameter("export")
							.compareToIgnoreCase("csv") == 0) {
				fexp.exportToCSVFile(request, response, res,
						"list_of_patients_registered"+((from.compareTo("")==0)?"":"_from_" + from) + ((to.compareTo("")==0)?"":"_to_" + to)+ ".csv", MohTracUtil.getMessage("mohtracportal.report.list.patient.registered", null),
						from, to, selectedUsers);
			}
			if (request.getParameter("export") != null
					&& request.getParameter("export")
							.compareToIgnoreCase("pdf") == 0) {
				fexp.exportToPDF(request, response, res,
						"list_of_patients_registered"+((from.compareTo("")==0)?"":"_from_" + from) + ((to.compareTo("")==0)?"":"_to_" + to)+ ".pdf", MohTracUtil.getMessage("mohtracportal.report.list.patient.registered", null),
						from, to, selectedUsers);
			}

		} catch (Exception ex) {
			request
					.getSession()
					.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
							MohTracUtil.getMessage("mohtracportal.error.data.onload",null));
			log
					.error(">>>MOH-TRAC>>PORTAL>>PATIENT>>LISTING>> An error occured when trying to load data from database");
			ex.printStackTrace();
		}

		return mav;
	}

	/**
	 * @param usersList
	 * @param mav
	 * @return
	 */
	private List<Integer> parseUsers(String usersList, ModelAndView mav) throws Exception{
		StringTokenizer st = new StringTokenizer(usersList, ",");
		List<Integer> usersId = new ArrayList<Integer>();
		String value = "";
		while (st.hasMoreTokens()) {
			value = st.nextToken();
			if (value != null && value.compareToIgnoreCase("") != 0) {
				usersId.add(Integer.valueOf(value));
			}
		}
		mav.addObject("usrs", usersId);
		return usersId;
	}

}
