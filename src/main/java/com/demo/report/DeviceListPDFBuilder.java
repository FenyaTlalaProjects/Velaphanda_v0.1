package com.demo.report;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import com.demo.model.Accessories;
import com.demo.model.Device;
import com.demo.model.OrderDetails;
import com.demo.model.TicketHistory;
import com.demo.model.Tickets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DeviceListPDFBuilder extends ReportITextPdfView {

	static Calendar cal = Calendar.getInstance();
	
	private Device device = null;
	
	public static final String DEST = "folderName";
	public static final String VelaphandaLogo = "/resources/mainlogo_small.jpg";
	public static final String FONT = "/resources/OpenSans-Regular.ttf";
	public static final String FONTB = "/resources//OpenSans-Bold.ttf";

	protected Font font8;
	protected Font font8b;
	protected Font font10;
	protected Font font10b;
	protected Font font12;
	protected Font font12b;
	protected Font font14;
	protected Font font14b;
	protected Font font18;
	protected Font font18b;

	public DeviceListPDFBuilder() throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(FONT, BaseFont.WINANSI,
				BaseFont.EMBEDDED);
		BaseFont bfb = BaseFont.createFont(FONTB, BaseFont.WINANSI,
				BaseFont.EMBEDDED);
		font8 = new Font(bf, 8);
		font8b = new Font(bfb, 8);
		font10 = new Font(bf, 10);
		font10b = new Font(bfb, 10);
		font12 = new Font(bf, 12);
		font12b = new Font(bfb, 12);
		font14 = new Font(bf, 14);
		font14b = new Font(bfb, 14);
		font18 = new Font(bf, 18);
		font18b = new Font(bfb, 18);
	}

	@Override
	public void buildPdfDocument(Map<String, Object> model, Document document,
			PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response, Long recordID) throws Exception {
		
	    List<Device> deviceList = (List<Device>) model.get("deviceList");
		
		
		document.open();

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(30);

		/*This is absolute path for Velaphanda server*/
		
		//QA testing Application path
		Image img = Image.getInstance("/xampp/tomcat/webapps/Velaphanda_Dev_v0.3/WEB-INF/resources/images/mainlogo_small.jpg");
		
		//Production Application path
		//Image img = Image.getInstance("/xampp/tomcat/webapps/Velaphanda_v0.2/WEB-INF/resources/images/mainlogo_small.jpg");
		
		/*Path to use when running project on your PC:*/
		//Image img = Image.getInstance(System.getProperty("user.home") + "/git/Velaphanda_v0.1/src/main/webapp/WEB-INF/resources/images/mainlogo_small.jpg");


		img.setWidthPercentage(1);
		PdfPCell cell = new PdfPCell(img);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		document.add(table);
		// Rotate event = new Rotate();

		//Device Heading
		table = new PdfPTable(1);
		table.setWidthPercentage(100);		
			PdfPCell deviceHeading = getDeviceHeading("DEVICES LIST");					
			table.addCell(deviceHeading);
		document.add(table);
	
		// Device List Items
		table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10);
		table.setSpacingAfter(10);

		table.setWidths(new int[] { 5, 4, 3, 3, 6 });
		
		table.addCell(getCell("Customer Name", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Serial Number", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Model Number", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Brand", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Address ", Element.ALIGN_LEFT, font10b));
		
		for (Device devicesList : deviceList) {
			
			table.addCell(getlineItemCell(devicesList.getCustomerDevice().getCustomerName(), font8));
			table.addCell(getlineItemCell(devicesList.getSerialNumber(), font8));
			table.addCell(getlineItemCell(devicesList.getModelNumber(), font8));
			table.addCell(getlineItemCell(devicesList.getModelBrand(), font8));
			table.addCell(getlineItemCell(devicesList.getStreetNumber()+" "+devicesList.getBuildingName(), font8));
		}
		document.add(table);
		
		document.close();

	}

	public String convertDate(Date d, String newFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
		return sdf.format(d);
	}
	
	public PdfPCell getDeviceHeading(String heading) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(heading,font12b));
		
		return cell;
	}

	public PdfPCell getlineItemCell(String value, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		cell.setUseDescender(true);
		Paragraph p = new Paragraph(value, font);
		cell.addElement(p);
		return cell;
	}
	
	public PdfPCell getCell(String value, int alignment, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setUseAscender(true);
		cell.setUseDescender(true);
		Paragraph p = new Paragraph(value, font);
		p.setAlignment(alignment);
		cell.addElement(p);
		return cell;
	}

}
