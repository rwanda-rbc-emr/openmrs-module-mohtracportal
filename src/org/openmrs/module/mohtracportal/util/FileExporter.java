package org.openmrs.module.mohtracportal.util;

/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 */
public class FileExporter {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * Auto generated method comment
	 * 
	 * @param request
	 * @param response
	 * @param res
	 * @param filename
	 * @param title
	 * @param to
	 * @param from
	 * @param selectedUsers
	 * @throws Exception
	 */
	public void exportToCSVFile(HttpServletRequest request,
			HttpServletResponse response, List<Object> res, String filename,
			String title, String from, String to, List<Integer> selectedUsers)
			throws Exception {

		SimpleDateFormat sdf = Context.getDateFormat();
		ServletOutputStream outputStream = null;

		try {
			outputStream = response.getOutputStream();
			PersonService ps = Context.getPersonService();

			String users = "";
			for (Integer usrId : selectedUsers) {
				users += ps.getPerson(usrId).getPersonName() + "; ";
			}

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ filename + "\"");
			outputStream.println(MohTracUtil.getMessage(
					"mohtracportal.report.title", null)
					+ ", : " + title);
			if (from.trim().compareTo("") != 0)
				outputStream.println(MohTracUtil.getMessage(
						"mohtracportal.from", null)
						+ ", : " + from);
			if (to.trim().compareTo("") != 0)
				outputStream.println(MohTracUtil.getMessage("mohtracportal.to",
						null)
						+ ", : " + to);
			outputStream.println(MohTracUtil.getMessage(
					"mohtracportal.report.created.on", null)
					+ ", : " + sdf.format(new Date()));// Report
			// date
			outputStream.println(MohTracUtil.getMessage(
					"mohtracportal.report.created.by", null)
					+ ", : " + Context.getAuthenticatedUser().getPersonName());// Report
			// author
			if (users.trim().compareTo("") != 0)
				outputStream.println(MohTracUtil.getMessage(
						"mohtracportal.patient.enterers", null)
						+ ", : " + users);
			outputStream.println();
			Integer numberOfPatients = res.size();
			outputStream.println(MohTracUtil.getMessage(
					"mohtracportal.numberOfPatients", null)
					+ ", " + numberOfPatients.toString());
			outputStream.println();

			boolean hasPrivToViewPatientNames = Context.getAuthenticatedUser()
					.hasPrivilege("View Patient Names");

			outputStream.println(MohTracUtil.getMessage(
					"mohtracportal.report.list.no", null)
					+ ","
					+ ((hasPrivToViewPatientNames) ? MohTracUtil.getMessage(
							"mohtracportal.patient.names", null)
							+ ", " : "")
					+ MohTracPortalTag.getIdentifierTypeNameByIdAsString(""
							+ MohTracConfigurationUtil
									.getTracNetIdentifierTypeId())
					+ ", "
					+ MohTracPortalTag.getIdentifierTypeNameByIdAsString(""
							+ MohTracConfigurationUtil
									.getLocalHealthCenterIdentifierTypeId())
					+ ", "
					+ MohTracUtil.getMessage(
							"mohtracportal.patient.date.created", null)
					+ "("
					+ Context.getDateFormat().toPattern()
					+ "), "
					+ MohTracUtil.getMessage(
							"mohtracportal.numberOfEncounters", null));
			outputStream.println();

			int ids = 0;

			for (Object patient : res) {
				Object[] o = (Object[]) patient;

				ids += 1;
				outputStream
						.println(ids
								+ ","
								+ ((hasPrivToViewPatientNames) ? MohTracPortalTag
										.getPersonNames(Integer.valueOf(o[0]
												.toString()))
										+ ","
										: "")
								+ MohTracPortalTag
										.personIdentifierByPatientIdAndIdentifierTypeId(
												Integer
														.valueOf(o[0]
																.toString()),
												MohTracConfigurationUtil
														.getTracNetIdentifierTypeId())
								+ ","
								+ MohTracPortalTag
										.personIdentifierByPatientIdAndIdentifierTypeId(
												Integer
														.valueOf(o[0]
																.toString()),
												MohTracConfigurationUtil
														.getLocalHealthCenterIdentifierTypeId())
								+ ","
								+ sdf.format(o[1])
								+ ","
								+ MohTracPortalTag
										.getNumberOfEncounterByPatient(Integer
												.valueOf(o[0].toString())));
			}

			outputStream.flush();
			log.info("csv File created");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != outputStream)
				outputStream.close();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param res
	 * @param filename
	 * @param title
	 * @param from
	 * @param to
	 * @param selectedUsers
	 * @throws Exception
	 */
	public void exportToPDF(HttpServletRequest request,
			HttpServletResponse response, List<Object> res, String filename,
			String title, String from, String to, List<Integer> selectedUsers)
			throws Exception {
		SimpleDateFormat sdf = Context.getDateFormat();
		Document document = new Document();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + "\""); // file name

		PdfWriter writer = PdfWriter.getInstance(document, response
				.getOutputStream());
		writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));

		HeaderFooter event = new HeaderFooter();
		writer.setPageEvent(event);

		document.open();
		document.setPageSize(PageSize.A4);

		document
				.addAuthor(Context.getAuthenticatedUser().getPersonName()
						.getFamilyName()
						+ " "
						+ Context.getAuthenticatedUser().getPersonName()
								.getGivenName());// the name of the author

		PersonService ps = Context.getPersonService();

		String users = "";
		for (Integer usrId : selectedUsers) {
			users += ps.getPerson(usrId).getPersonName() + "; ";
		}

		FontSelector fontTitle = new FontSelector();
		fontTitle.addFont(new Font(FontFamily.COURIER, 10.0f, Font.BOLD));

		title = MohTracUtil.getMessage("mohtracportal.report.title", null)
				+ "      : " + title;
		String underLine = "";
		int count = 0;
		while (count < title.length()) {
			count += 1;
			underLine += "_";
		}

		document.add(fontTitle.process(title));// Report title
		if (from.trim().compareTo("") != 0)
			document.add(fontTitle.process("\n"
					+ MohTracUtil.getMessage("mohtracportal.from", null)
					+ "      : " + from));// from
		if (to.trim().compareTo("") != 0)
			document.add(fontTitle.process("\n"
					+ MohTracUtil.getMessage("mohtracportal.to", null)
					+ "        : " + to));// to
		document.add(fontTitle.process("\n"
				+ MohTracUtil.getMessage("mohtracportal.report.created.on",
						null) + " : " + sdf.format(new Date())));// Report date
		document.add(fontTitle.process("\n"
				+ MohTracUtil.getMessage("mohtracportal.report.created.by",
						null) + " : "
				+ Context.getAuthenticatedUser().getPersonName()));// Report
		// author
		
		Integer numberOfPatients = res.size();		
		document.add(fontTitle.process("\n"
				+ MohTracUtil.getMessage("mohtracportal.numberOfPatients",
						null) + " : "
				+ numberOfPatients.toString()));// Number of patients
		
		if (users.trim().compareTo("") != 0)
			document.add(fontTitle.process("\n"
					+ MohTracUtil.getMessage("mohtracportal.patient.enterers",
							null) + " : " + users));// enterer(s)
		document.add(fontTitle.process("\n" + underLine));// Report title
		document.add(new Paragraph("\n\n"));

		boolean hasPrivToViewPatientNames = Context.getAuthenticatedUser()
				.hasPrivilege("View Patient Names");

		// PdfLine line;
		PdfPTable table = null;
		if (hasPrivToViewPatientNames == true) {
			float[] colsWidth = { 1.2f, 5f, 2.7f, 2.7f, 4.2f, 2.7f };
			table = new PdfPTable(colsWidth);
		} else {
			float[] colsWidth = { 1.2f, 2.7f, 2.7f, 4.2f, 2.7f };
			table = new PdfPTable(colsWidth);
		}

		// column number
		table.setTotalWidth(540f);

		// title row
		FontSelector fontTitleSelector = new FontSelector();
		fontTitleSelector.addFont(new Font(FontFamily.COURIER, 9, Font.BOLD));
		BaseColor bckGroundTitle = new BaseColor(170, 170, 170);

		// table Header
		PdfPCell cell = new PdfPCell(fontTitleSelector.process(MohTracUtil
				.getMessage("mohtracportal.report.list.no", null)));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		if (hasPrivToViewPatientNames) {
			cell = new PdfPCell(fontTitleSelector.process(MohTracUtil
					.getMessage("mohtracportal.patient.names", null)));
			cell.setBackgroundColor(bckGroundTitle);
			table.addCell(cell);
		}

		cell = new PdfPCell(fontTitleSelector
				.process(MohTracPortalTag
						.getIdentifierTypeNameByIdAsString(""
								+ MohTracConfigurationUtil
										.getTracNetIdentifierTypeId())));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		cell = new PdfPCell(fontTitleSelector.process(MohTracPortalTag
				.getIdentifierTypeNameByIdAsString(""
						+ MohTracConfigurationUtil
								.getLocalHealthCenterIdentifierTypeId())));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		cell = new PdfPCell(fontTitleSelector.process(MohTracUtil.getMessage(
				"mohtracportal.patient.date.created", null)
				+ "(" + Context.getDateFormat().toPattern() + ")"));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		cell = new PdfPCell(fontTitleSelector.process(MohTracUtil.getMessage(
				"mohtracportal.numberOfEncounters", null)));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		// normal row
		FontSelector fontselector = new FontSelector();
		fontselector.addFont(new Font(FontFamily.COURIER, 8, Font.NORMAL));

		// empty row
		FontSelector fontEmptyCell = new FontSelector();
		fontEmptyCell.addFont(new Font(FontFamily.COURIER, 8, Font.NORMAL));

		int ids = 0;

		for (Object patient : res) {
			Object[] o = (Object[]) patient;
			ids += 1;

			cell = new PdfPCell(fontselector.process(ids + ""));
			if (o[2].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(238, 238, 238));
			if (o[3].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(224, 0, 0));
			table.addCell(cell);

			if (hasPrivToViewPatientNames) {
				String names = MohTracPortalTag.getPersonNames(Integer
						.valueOf(o[0].toString()));
				cell = new PdfPCell(fontselector.process(names + ""));
				if (names.compareTo("-") == 0)
					cell.setBackgroundColor(new BaseColor(224, 224, 240));
				if (o[2].toString().compareTo("1") == 0)
					cell.setBackgroundColor(new BaseColor(238, 238, 238));
				if (o[3].toString().compareTo("1") == 0)
					cell.setBackgroundColor(new BaseColor(224, 0, 0));
				table.addCell(cell);
			}

			String tracnetId = MohTracPortalTag
					.personIdentifierByPatientIdAndIdentifierTypeId(Integer
							.valueOf(o[0].toString()), MohTracConfigurationUtil
							.getTracNetIdentifierTypeId());
			cell = new PdfPCell(fontselector.process(tracnetId + ""));
			if (tracnetId.compareTo("-") == 0)
				cell.setBackgroundColor(new BaseColor(224, 224, 240));
			if (o[2].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(238, 238, 238));
			if (o[3].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(224, 0, 0));
			table.addCell(cell);

			String cohortId = MohTracPortalTag
					.personIdentifierByPatientIdAndIdentifierTypeId(Integer
							.valueOf(o[0].toString()), MohTracConfigurationUtil
							.getLocalHealthCenterIdentifierTypeId());
			cell = new PdfPCell(fontselector.process(cohortId + ""));
			if (cohortId.compareTo("-") == 0)
				cell.setBackgroundColor(new BaseColor(224, 224, 240));
			if (o[2].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(238, 238, 238));
			if (o[3].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(224, 0, 0));
			table.addCell(cell);

			cell = new PdfPCell(fontselector.process(sdf.format(o[1]) + ""));
			if (o[2].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(238, 238, 238));
			if (o[3].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(224, 0, 0));
			table.addCell(cell);

			String numberOfEncounters = MohTracPortalTag
					.getNumberOfEncounterByPatient(Integer.valueOf(o[0]
							.toString()));
			cell = new PdfPCell(fontselector.process(numberOfEncounters + ""));
			if (numberOfEncounters.compareTo("-") == 0)
				cell.setBackgroundColor(new BaseColor(224, 224, 240));
			if (o[2].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(238, 238, 238));
			if (o[3].toString().compareTo("1") == 0)
				cell.setBackgroundColor(new BaseColor(224, 0, 0));
			table.addCell(cell);
		}

		document.add(table);
		document.close();

		log.info("pdf file created");
	}

	static class HeaderFooter extends PdfPageEventHelper {
		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle rect = writer.getBoxSize("art");

			Phrase header = new Phrase(String.format("- %d -", writer
					.getPageNumber()));
			header.setFont(new Font(FontFamily.COURIER, 4, Font.NORMAL));

			if (document.getPageNumber() > 1) {
				ColumnText.showTextAligned(writer.getDirectContent(),
						Element.ALIGN_CENTER, header, (rect.getLeft() + rect
								.getRight()) / 2, rect.getTop() + 40, 0);
			}

			Phrase footer = new Phrase(String.format("- %d -", writer
					.getPageNumber()));
			footer.setFont(new Font(FontFamily.COURIER, 4, Font.NORMAL));

			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_CENTER, footer, (rect.getLeft() + rect
							.getRight()) / 2, rect.getBottom() - 40, 0);

		}
	}
}
