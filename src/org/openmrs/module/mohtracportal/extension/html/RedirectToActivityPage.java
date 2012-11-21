/**
 * 
 */
package org.openmrs.module.mohtracportal.extension.html;

import org.openmrs.module.web.extension.PortletExt;

/**
 * @author Yves GAKUBA
 *
 */
public class RedirectToActivityPage extends PortletExt {

	@Override
	public String getPortletParameters() {
		return null;
	}

	@Override
	public String getPortletUrl() {
		return "redirectToActivity";
	}

}	
