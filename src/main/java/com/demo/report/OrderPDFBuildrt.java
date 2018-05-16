package com.demo.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.CustomerContactDetailsDaoInt;
import com.demo.dao.OrderDetailsDaoInt;
import com.demo.dao.OrdersDaoInt;
import com.demo.model.OrderDetails;
import com.demo.model.OrderHeader;
import com.demo.service.VelaphandaInt;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class OrderPDFBuildrt extends ReportITextPdfView{

	private Date currentDate;
	private SimpleDateFormat dateFormat;
	static Calendar cal = Calendar.getInstance();
		
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
	
	@Autowired
	private OrdersDaoInt ordersDaoInt;
	@Autowired
	private OrderDetailsDaoInt detailsDaoInt;
	@Autowired
	private CustomerContactDetailsDaoInt contactDetails;
	@Autowired
	private VelaphandaInt velaphandaInt;
	
	private OrderHeader orderHeader;
	
	@Override
	public void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response,Long recordID) throws Exception {
				
				
				//Get payment information from order table
		    List<OrderDetails> listItems = (List<OrderDetails>) model.get("listItems");
			
			orderHeader = listItems.get(0).getOrderHeader();
			
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
				//Rotate event = new Rotate();

				table = new PdfPTable(3);
				table.setWidthPercentage(100);
				//Set<CustomerContactDetails> contactList = (Set<CustomerContactDetails>) listItems.get(0).getOrderHeader().getCustomer().getCustomerContactDetails();
				//List <CustomerContactDetails> cus = (List<CustomerContactDetails>) listItems.get(0).getOrderHeader().getCustomer().getCustomerContactDetails();
				System.out.println();
				if(orderHeader.getStockType().equalsIgnoreCase("Site"))
				{
					//customerContact = cus.get(0);
					// Customer Address
					PdfPCell customerAddress = getCustomerAddressSite("DELIVER TO",
							orderHeader.getCustomer().getCustomerName(), orderHeader.getCustomer().getStreetNumber()+" "+orderHeader.getCustomer().getStreetName(),orderHeader.getCustomer().getCity_town(),
							"Contact Person: "+orderHeader.getEmployee().getFirstName()+" "+orderHeader.getEmployee().getLastName() , "Contact No: "+orderHeader.getEmployee().getCellNumber() ,
							"E-Mail: "+ orderHeader.getEmployee().getEmail(),"","","");
					table.addCell(customerAddress);
				}else if(orderHeader.getStockType().equalsIgnoreCase("Boot")){
					// Customer Address
					PdfPCell customerAddress = getCustomerAddressBoot("DELIVER TO",
							"Contact Person: "+orderHeader.getEmployee().getFirstName()+" "+orderHeader.getEmployee().getLastName() , "Contact No: "+orderHeader.getEmployee().getCellNumber() ,
							"E-Mail: "+ orderHeader.getEmployee().getEmail(),"","","");
					table.addCell(customerAddress);
				}
				PdfPCell velaphandaAddress = getVelaphandaAddress("DELIVERY NOTE","VELAPHANDA TRADING & PROJECTS ", "REG NO:2008/164490/23",
						"296 GALWAY AVENUE, BRONBERRICK", "CENTURION 0158",
						"TEL: 012 765 0200/087 701 1689"+" FAX: 086 403 7955",
						"E-MAIL: sales@velaphanda.co.za");				
				table.addCell(velaphandaAddress);

				Date today;
			    String timeCallClosed;				   
				dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
				today = new Date();
				timeCallClosed = dateFormat.format(today);
				System.out.println(timeCallClosed);
				Calendar cal = Calendar.getInstance();
				System.out.println(new SimpleDateFormat("MMM").format(cal.getTime()));
				if(orderHeader.getStockType().equalsIgnoreCase("Site"))	{
				String customerName = orderHeader.getCustomer().getCustomerName();
				PdfPCell paymentInformation = getPaymentForSiteInformation(
						"PAYMENT INFORMATION", "DATE OF DELIVERY: "+ timeCallClosed,
						"DELIVERY NOTE NO: "+ customerName.substring(0,3)+"/"+new SimpleDateFormat("MMM").format(cal.getTime())+"/ORD00"+orderHeader.getRecordID(), "ORDER NO: ORD00"+orderHeader.getRecordID(), "CONTRACT NO:", "WAYBILL NO:",
						"PLEASE REMIT TO admin@velaphanda.co.za");
						table.addCell(paymentInformation);
				}else if(orderHeader.getStockType().equalsIgnoreCase("Boot")){
				PdfPCell paymentInformation = getPaymentForBootInformation(
						"PAYMENT INFORMATION", "DATE OF DELIVERY: "+ timeCallClosed,
						"DELIVERY NOTE NO: " + orderHeader.getEmployee().getFirstName()+"/"+new SimpleDateFormat("MMM").format(cal.getTime())+"/ORD00"+orderHeader.getRecordID(), "ORDER NO: ORD00"+orderHeader.getRecordID(), "CONTRACT NO:", "WAYBILL NO:",
						"PLEASE REMIT TO admin@velaphanda.co.za");	

				table.addCell(paymentInformation);
				}
				document.add(table);

				// Line Item
				table = new PdfPTable(5);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10);
				table.setSpacingAfter(10);
				
				table.setWidths(new int[] { 5, 5, 6, 3 ,3});

				table.addCell(getCell("Part Number", Element.ALIGN_LEFT, font10b));
				table.addCell(getCell("Brand", Element.ALIGN_LEFT, font10b));
				table.addCell(getCell("Description", Element.ALIGN_LEFT, font10b));
				table.addCell(getCell("Qty Ordered", Element.ALIGN_LEFT, font10b));
				table.addCell(getCell("Qty Delivered", Element.ALIGN_LEFT, font10b));

				for(OrderDetails ordDetails:listItems){
					
					table.addCell(getlineItemCell(ordDetails.getPartNumber(), font8));
					table.addCell(getlineItemCell(ordDetails.getModelBrand(), font8));
					table.addCell(getlineItemCell(ordDetails.getItemDescription(), font8));
					table.addCell(getlineItemCell(""+ordDetails.getQuantity(), font8));
					table.addCell(getlineItemCell(""+ordDetails.getQuantity(), font8));
									
				}
				document.add(table);
				
				// Signature
				table = new PdfPTable(2);
				cell.setBorder(PdfPCell.NO_BORDER);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10);
				table.setSpacingAfter(10);
				table.setWidths(new int[] {10, 10 });

				table.addCell(signature(
						"Received By:______________________________________________________________\n\n", font8b));
				table.addCell(signature(
						"Signature:________________________________________________________________", font8b));
				table.addCell(signature(
						"Print Name & Surname:_________________________________________________\n\nID/EMPLOYEE NO:________________________________________________________\n\n", font8b));				
				table.addCell(signature(
						"Date/Time Received:____________________________________________________", font8b));
				
			
				//Note
				document.add(table);				
				table = new PdfPTable(1);
				cell.setBorder(PdfPCell.NO_BORDER);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10);
				table.setSpacingAfter(10);
				table.setWidths(new int[] {10});
								
				table.addCell(notes(
						"NOTES:________________________________________________________________________________________________________________________________________\n\n", font12b));					

				//OWNERSHIP
				document.add(table);			
				table = new PdfPTable(1);
				cell.setBorder(PdfPCell.NO_BORDER);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10);
				table.setSpacingAfter(10);
				table.setWidths(new int[] {10});
				
				table.addCell(ownership(
						"                                                                                            OWNERSHIP OF GOODS REMAINS VESTED IN VELAPHANDA TRADING AND PROJECTS UNTIL PAID FOR IN FULL.", font8b));
				
				document.add(table);

				document.close();}
		
			
	public String convertDate(Date d, String newFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
		return sdf.format(d);
	}

	public OrderPDFBuildrt() throws DocumentException, IOException {
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

	public PdfPCell getCustomerAddressSite(String customer, String streetNumber,
			String streetName, String city, String contactPerson,
			String contactNumber, String email, String string, String string2, String string3) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(customer, font12b));
		cell.addElement(new Paragraph(streetNumber, font8));
		cell.addElement(new Paragraph(streetName, font8));
		cell.addElement(new Paragraph(city, font8));
		cell.addElement(new Paragraph(contactPerson, font8));
		cell.addElement(new Paragraph(contactNumber, font8));
		cell.addElement(new Paragraph(email, font8));
		return cell;
	}
	
	public PdfPCell getCustomerAddressBoot(String customer, String streetNumber,
			String streetName, String city, String contactPerson,
			String contactNumber, String email) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(customer, font12b));
		cell.addElement(new Paragraph(streetNumber, font8));
		cell.addElement(new Paragraph(streetName, font8));
		cell.addElement(new Paragraph(city, font8));
		cell.addElement(new Paragraph(contactPerson, font8));
		cell.addElement(new Paragraph(contactNumber, font8));
		cell.addElement(new Paragraph(email, font8));
		return cell;
	}

	public PdfPCell getHeadingDeliveredNote(String headingNote) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(headingNote, font12b));

		return cell;
	}
	
	public PdfPCell getVelaphandaAddress(String heading, String companyName,
			String registration, String address, String address1,
			String telephone, String email) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(heading, font12b));
		cell.addElement(new Paragraph(companyName, font8));
		cell.addElement(new Paragraph(registration, font8));
		cell.addElement(new Paragraph(address, font8));
		cell.addElement(new Paragraph(address1, font8));
		cell.addElement(new Paragraph(telephone, font8));
		cell.addElement(new Paragraph(email, font8));

		return cell;
	}

	public PdfPCell getPaymentInformation(String String, String heading, String date,
			String noteNumber, String orderNumber,
			String contractNumber, String wayToBill, String message) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(heading, font12b));
		cell.addElement(new Paragraph(date, font8));
		cell.addElement(new Paragraph(noteNumber, font8));
		cell.addElement(new Paragraph(orderNumber, font8));
		cell.addElement(new Paragraph(contractNumber, font8));
		cell.addElement(new Paragraph(wayToBill, font8));
		cell.addElement(new Paragraph(message, font8));

		return cell;
	}
	
	public PdfPCell getPaymentForSiteInformation(String heading, String date,
			String noteNumber, String orderNumber,
			String contractNumber, String wayToBill, String message) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(heading, font12b));
		cell.addElement(new Paragraph(date, font8));
		cell.addElement(new Paragraph(noteNumber, font8));
		cell.addElement(new Paragraph(orderNumber, font8));
		cell.addElement(new Paragraph(contractNumber, font8));
		cell.addElement(new Paragraph(wayToBill, font8));
		cell.addElement(new Paragraph(message, font8));

		return cell;
	}
	
	public PdfPCell getPaymentForBootInformation( String heading, String date,
			String noteNumber, String orderNumber,
			String contractNumber, String wayToBill, String message) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.addElement(new Paragraph(heading, font12b));
		cell.addElement(new Paragraph(date, font8));
		cell.addElement(new Paragraph(noteNumber, font8));
		cell.addElement(new Paragraph(orderNumber, font8));
		cell.addElement(new Paragraph(contractNumber, font8));
		cell.addElement(new Paragraph(wayToBill, font8));
		cell.addElement(new Paragraph(message, font8));

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

	public PdfPCell signature(String value, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setUseAscender(true);
		cell.setUseDescender(true);
		Paragraph p = new Paragraph(value, font);
		cell.addElement(p);
		return cell;
	}
	
	public PdfPCell notes(String value, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setUseAscender(true);
		cell.setUseDescender(true);
		Paragraph p = new Paragraph(value, font);
		cell.addElement(p);
		return cell;
	}
	
	public PdfPCell ownership(String value, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
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
