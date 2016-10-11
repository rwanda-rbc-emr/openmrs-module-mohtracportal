package org.openmrs.module.mohtracportal.service;

import java.util.Collection;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleConstants;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.ModuleUtil;
import org.openmrs.module.reporting.report.Report;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.module.rwandasphstudyreports.api.CDCReportsService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class MohTracPortalServiceTest extends BaseModuleContextSensitiveTest {

	@Override
	public Boolean useInMemoryDatabase() {
		return false;
	}

	@Before
	public void setupForTest() throws Exception {
		if (!Context.isSessionOpen()) {
			Context.openSession();
		}
		Context.clearSession();
		authenticate();
	}

	@Override
	public Properties getRuntimeProperties() {
		Properties props = super.getRuntimeProperties();

		props.setProperty("connection.url",
				"jdbc:mysql://localhost:3306/ecdmrs_pih?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8");
		props.setProperty("connection.username", "ecdmrs_pih_user");
		props.setProperty("connection.password", "cxOLDkN6K~OX");
		props.setProperty("junit.username", "test");// OpenMRS username
		props.setProperty("junit.password", "Password123");// OpenMRS password

		props.setProperty(ModuleConstants.RUNTIMEPROPERTY_MODULE_LIST_TO_LOAD,
				"mohtracportal-0.2.7-SNAPSHOT.omod htmlwidgets-1.7.2.omod calculation-1.2.omod serialization.xstream-0.2.7.omod reporting-0.9.7.omod rwandasphstudyreports-1.0-SNAPSHOT.omod");

		return props;
	}

	@Before
	public void init() {
		// ModuleUtil.startup(getRuntimeProperties());
	}

	@Test
	public void shouldSetupContext() {
		Assert.assertNotNull(Context.getService(MohTracPortalService.class));
	}

	/**
	 * @TODO weird to load all modules which rwandasphstudyreports depends on
	 *       before loading it
	 */
	@Test
	public void reportingBug() {
		Assert.assertNotNull(Context.getService(CDCReportsService.class));

		ModuleUtil.startup(getRuntimeProperties());

		Collection<Module> loadedModules = ModuleFactory.getLoadedModules();

		Assert.assertTrue(loadedModules.size() > 0);

		/*
		 * this next line throws org.hibernate.HibernateException: Unable to
		 * locate named class
		 * org.openmrs.module.reporting.serializer.ReportingSerializer in the web application
		 */
		ReportRequest rr = Context.getService(MohTracPortalService.class).executeAndGetAdultFollowUpReportRequest();

		ReportRequest req = Context.getService(ReportService.class).getReportRequestByUuid(rr.getUuid());
		Report report = Context.getService(ReportService.class).runReport(rr);

		Assert.assertNotNull(rr);
		Assert.assertEquals(req.getUuid(), rr.getUuid());
		Assert.assertEquals(report.getRequest().getUuid(), rr.getUuid());
	}
}
