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

public class DeviceDetailsPDFBuilder extends ReportITextPdfView {

	private Date currentDate;
	private SimpleDateFormat dateFormat;
	static Calendar cal = Calendar.getInstance();
	
	private Device device = null;
	
	private Accessories accessories = null;

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

	public DeviceDetailsPDFBuilder() throws DocumentException, IOException {
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
		
	    List<Accessories> accessories = (List<Accessories>) model.get("accessories");
		
	 
		device = accessories.get(0).getDevice();
		
		currentDate = new Date();
		dateFormat = new SimpleDateFormat();
		document.open();

		// Current Time Stamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(30);

		/*This is absolute path for Velaphanda server*/
		//Image img = Image.getInstance("/xampp/tomcat/webapps/Velaphanda_v0.2/WEB-INF/resources/images/mainlogo_small.jpg");
		
		/*Path to use when running project on your PC:*/
		Image img = Image.getInstance(System.getProperty("user.home") + "/git/Velaphanda_v0.1/src/main/webapp/WEB-INF/resources/images/mainlogo_small.jpg");


		img.setWidthPercentage(1);
		PdfPCell cell = new PdfPCell(img);
		cell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell);
		document.add(table);
		// Rotate event = new Rotate();

		table = new PdfPTable(2);
		table.setWidthPercentage(100);		
		
			PdfPCell machineDetails = getMachineDetails("MACHINE DETAILS",
					"Customer Name: " +device.getCustomerDevice().getCustomerName(), 
					"Serial No: " + device.getSerialNumber(), 
					"Model Number: "+ device.getModelNumber(),   
					"Model Brand: " + device.getModelBrand(), 
					"Start Date: "+ device.getStartDate(),   
					"Installation Date: "+ device.getInstallationDate(),   
					"End Date: "+ device.getEndDate(),   
					"Colour Reading: "+ device.getColourReading(),   
					"Colour Copy Cost: "+ device.getColourCopyCost(),   
					"Mono Reading: "+ device.getMonoReading(), 
					"Mono Copy Cost: "+ device.getMonoCopyCost());
			table.addCell(machineDetails);
	
		PdfPCell deviceAddressAndContactPerson = getAddressAndContactPerson("ADDRESS AND CONTACT PERSON",
				
				"ADDRESS",				
				"Street: "+ device.getStreetNumber() +" "+ device.getStreetName(),
				"City Town: " + device.getCity_town(),
				"Province: " + device.getProvince() +"\n\n",
				
				"CONTACT",
				"Name: "+ device.getContactPerson().getFirstName() +" "+ device.getContactPerson().getLastName(),
				"Mobile Number: "+ device.getContactPerson().getCellphone(),
				"Tellphone Number: "+ device.getContactPerson().getTelephone(),
				"Email: " + device.getContactPerson().getEmail());
		table.addCell(deviceAddressAndContactPerson);		

		document.add(table);

		// Accessories Items
		table = new PdfPTable(2);
		table.setWidthPercentage(50);
		table.setSpacingBefore(10);
		table.setSpacingAfter(10);

		table.setWidths(new int[] { 5, 5});
		
		table.addCell(getCell("Accessory Type", Element.ALIGN_LEFT, font10b));
		table.addCell(getCell("Serial Number", Element.ALIGN_LEFT, font10b));
		
		for (Accessories accessoriesList : accessories) {

			table.addCell(getlineItemCell(accessoriesList.getAccessotyType(), font8));
			table.addCell(getlineItemCell(accessoriesList.getSerial(), font8));
			
		}
		document.add(table);
		
		document.close();

	}

	public String convertDate(Date d, String newFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
		return sdf.format(d);
	}

	public PdfPCell getMachineDetails(String heading,String customerName,String serialNumber,
			String modelNumber,String modelBrand, String startDate, String installationDate,
			String endDate,String colourReading, String colourCopyCost, String monoReading,
			String monoCopyCost) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(heading, font12b));
		cell.addElement(new Paragraph(customerName, font8));
		cell.addElement(new Paragraph(serialNumber,font8));
		cell.addElement(new Paragraph(modelNumber, font8));
		cell.addElement(new Paragraph(modelBrand, font8));
		cell.addElement(new Paragraph(startDate, font8));
		cell.addElement(new Paragraph(installationDate, font8));
		cell.addElement(new Paragraph(endDate, font8));
		cell.addElement(new Paragraph(colourReading, font8));		
		cell.addElement(new Paragraph(colourCopyCost, font8));
		cell.addElement(new Paragraph(monoReading, font8));
		cell.addElement(new Paragraph(monoCopyCost, font8));
		
		return cell;
	}

	public PdfPCell getAddressAndContactPerson(String heading,String addressHeading, String street,
			String cityTown, String province,String contactHeading,
			String name,String cellNumber, String tellphone, String email
			) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
				
		cell.addElement(new Paragraph(heading, font12b));
		cell.addElement(new Paragraph(addressHeading, font8b));
		cell.addElement(new Paragraph(street, font8));
		cell.addElement(new Paragraph(cityTown, font8));
		cell.addElement(new Paragraph(province, font8));
		cell.addElement(new Paragraph(contactHeading, font8b));
		cell.addElement(new Paragraph(name, font8));
		cell.addElement(new Paragraph(cellNumber, font8));
		cell.addElement(new Paragraph(tellphone, font8));
		cell.addElement(new Paragraph(email, font8));
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
