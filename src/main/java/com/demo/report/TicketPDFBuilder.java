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

public class TicketPDFBuilder extends ReportITextPdfView {

	private Date currentDate;
	private SimpleDateFormat dateFormat;
	static Calendar cal = Calendar.getInstance();
	
	private Tickets ticket = null;

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

	public TicketPDFBuilder() throws DocumentException, IOException {
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
		// TODO Auto-generated method stub
		Tickets ticket = (Tickets) model.get("ticket");

		currentDate = new Date();
		dateFormat = new SimpleDateFormat();
		document.open();

		// Current Time Stamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(30);

		/*This is absolute path for Velaphanda server*/
		
		//QA testing Application path
		//Image img = Image.getInstance("/xampp/tomcat/webapps/Velaphanda_Dev_v0.3/WEB-INF/resources/images/mainlogo_small.jpg");
		
		//Production Application path
		//Image img = Image.getInstance("/xampp/tomcat/webapps/Velaphanda_v0.2/WEB-INF/resources/images/mainlogo_small.jpg");
		
		/*Path to use when running project on your PC:*/
		Image img = Image.getInstance(System.getProperty("user.home") + "/git/Velaphanda_v0.1/src/main/webapp/WEB-INF/resources/images/mainlogo_small.jpg");
		
		img.setWidthPercentage(1);
		PdfPCell cell = new PdfPCell(img);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		document.add(table);
		// Rotate event = new Rotate();

		table = new PdfPTable(3);
		table.setWidthPercentage(100);		
		
			PdfPCell customerDevice = getCustomerDevice("DEVICE",
				"Serial Number: "+ ticket.getDevice().getSerialNumber(),
				"Model: " + ticket.getDevice().getModelNumber(),
				"Device Brand: " +ticket.getDevice().getModelBrand()+"\n\n", 
				"LOCATION: ",
				ticket.getDevice().getFloorNumber()+" " +ticket.getDevice().getBuildingName(),
				"Street: "+ ticket.getDevice().getStreetNumber() +" "+ ticket.getDevice().getStreetName(),
				"City Town: " + ticket.getDevice().getCity_town(),
				"Area Code: " + ticket.getDevice().getAreaCode(),
				"Province: " + ticket.getDevice().getProvince()
				
					);
			table.addCell(customerDevice);
	
		PdfPCell ticketContacts = getTicketContacts("TICKET CONTACTS",
				"Name: " +ticket.getFirstName()+" "+ticket.getLastName(),
				"Cell No: " + ticket.getContactCellNumber(),
				"Telephone No: " +ticket.getContactTelephoneNumber(),
				"E-MAIL: " +ticket.getContactEmail());
		table.addCell(ticketContacts);

		PdfPCell ticketInformation = getTicketInformation(
				"TICKET", "Ticket No: " +"VTC000"+ticket.getRecordID(),
				"Customer: " + ticket.getDevice().getCustomerDevice().getCustomerName(),
				"Status: " + ticket.getStatus(),
				"Date: " + ticket.getDateTime(),
				"Assigned To: "+ ticket.getEmployee().getFirstName() +" "+ticket.getEmployee().getLastName(),
				"Description: "+ ticket.getDescription());

		table.addCell(ticketInformation);

		document.add(table);

		// Line Item
		table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10);
		table.setSpacingAfter(10);

		table.setWidths(new int[] { 5, 5, 6, 3, 3 });

		table.addCell(getCell("Ticket Number", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Status", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Priority", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Technician Email", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Comments", Element.ALIGN_LEFT, font10b));

		

			table.addCell(getlineItemCell("VTC000"+ticket.getRecordID(), font8));
			table.addCell(getlineItemCell(ticket.getStatus(), font8));
			table.addCell(getlineItemCell(ticket.getPriority(),font8));
			table.addCell(getlineItemCell(ticket.getEmployee().getEmail(), font8));
			table.addCell(getlineItemCell(ticket.getComments(), font8));

		document.add(table);
		
		document.close();

	}

	public String convertDate(Date d, String newFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
		return sdf.format(d);
	}

	public PdfPCell getCustomerDevice(String heading,String serialNumber,
			String modelNumber, String modelBrand, String locationHeading,String floorAndBuildingName,
			String streetName,String cityTown, String province,
			String areaCode) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(heading, font12b));
		cell.addElement(new Paragraph(serialNumber, font8b));
		cell.addElement(new Paragraph(modelNumber, font8));
		cell.addElement(new Paragraph(modelBrand, font8));
		cell.addElement(new Paragraph(locationHeading, font8b));
		cell.addElement(new Paragraph(floorAndBuildingName, font8));
		cell.addElement(new Paragraph(streetName, font8));
		cell.addElement(new Paragraph(cityTown, font8));
		cell.addElement(new Paragraph(province, font8));
		cell.addElement(new Paragraph(areaCode, font8));
		return cell;
	}

	public PdfPCell getTicketContacts(String contactPerson,String name,
			String cellNumber, String tellphone, String email
			) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(contactPerson, font12b));
		cell.addElement(new Paragraph(name, font8));
		cell.addElement(new Paragraph(cellNumber, font8));
		cell.addElement(new Paragraph(tellphone, font8));
		cell.addElement(new Paragraph(email, font8));
		return cell;
	}

	public PdfPCell getHeadingDeliveredNote(String headingNote) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(headingNote, font12b));

		return cell;
	}

	public PdfPCell getTicketInformation(String heading, String ticketNumber,String customer ,String date,
			String status, String assignedTo, String description) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(heading, font12b));
		cell.addElement(new Paragraph(ticketNumber, font8));
		cell.addElement(new Paragraph(customer, font8));
		cell.addElement(new Paragraph(date, font8));
		cell.addElement(new Paragraph(status, font8));
		cell.addElement(new Paragraph(assignedTo, font8));
		cell.addElement(new Paragraph(description, font8));

		return cell;
	}

	public PdfPCell getCustomerContacts(String contactPerson,
			String contactNumber, String email) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(contactPerson, font8));
		cell.addElement(new Paragraph(contactNumber, font8));
		cell.addElement(new Paragraph(email, font8));

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
