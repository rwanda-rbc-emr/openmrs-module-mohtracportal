/**
 * 
 */
package org.openmrs.module.mohtracportal;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ServiceContext;
import org.openmrs.module.mohtracportal.service.MohTracPortalService;
import org.springframework.util.StringUtils;

/**
 * @author Yves GAKUBA
 * 
 */

public class SponsorEditor extends PropertyEditorSupport {

	private Log log = LogFactory.getLog(this.getClass());

	public SponsorEditor() {
	}

	public void setAsText(String text) throws IllegalArgumentException {
		MohTracPortalService service = (MohTracPortalService) ServiceContext
				.getInstance().getService(MohTracPortalService.class);
		if (StringUtils.hasText(text)) {
			try {
				setValue(service.getSponsorById(Integer.valueOf(text)));
			} catch (Exception ex) {
				log.error("Error setting text: " + text, ex);
				throw new IllegalArgumentException("Location not found: "
						+ ex.getMessage());
			}
		} else {
			setValue(null);
		}
	}

	public String getAsText() {
		Sponsor t = (Sponsor) getValue();
		if (t == null && Context.isAuthenticated()) {
			return null;
		} else {
			return t.getSponsorId().toString();
		}
	}

}
