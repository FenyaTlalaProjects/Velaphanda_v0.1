package com.demo.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.model.Employee;
import com.demo.model.OrderDetails;
import com.demo.model.OrderHeader;
import com.demo.model.Tickets;

public class JavaMail {

	private static String emailFrom = "helpdesk@velaphanda.co.za";
	private static String password = "@Vela1357";
	
	private static String newTicketNum = "VTC000";
	private static String newOrderNum = "ORD000";
	
	@Autowired
	private HttpSession session = null;
	
	//Email message details to technician
	public static void sendEmailMessageDetailsToTechnician(Tickets ticket) {
		String[] to = { ticket.getEmployee().getEmail() };
		String from = emailFrom;
		String pass = password;
		String body = "Hi " + ticket.getEmployee().getFirstName() + " " + ticket.getEmployee().getLastName() + " please attend to the following service call:"
				
				+ "\n\nYour Ticket Details are as follows:" +"\n\n"
				
				+ "Ticket Number: " + newTicketNum+ticket.getRecordID() + "\n"
				+ "Device Model: " + ticket.getDevice().getModelNumber() + "\n"
				+ "Serial Number: " + ticket.getDevice().getSerialNumber() +"\n"
				+ "Device Brand: " + ticket.getDevice().getModelBrand() +"\n"				
				+ "Problem Description: " + ticket.getDescription() + "\n"
				+ "Location: " + ticket.getDevice().getStreetNumber()+" "+ticket.getDevice().getStreetName()+" , "+ ticket.getDevice().getCity_town() +" , "+ ticket.getDevice().getProvince() +"\n\n"
				
				+ "Client Contact Details:\n\n"
				
				+ "Name & Surname: " +ticket.getFirstName()+" "+ticket.getLastName() +"\n"
				+ "Email: " +ticket.getContactEmail()+ "\n" 
				+ "Cell Number: " + ticket.getContactCellNumber()+ "\n"
				+ "Tellphone Number: " + ticket.getContactTelephoneNumber() + "\n" 
				
				+ "\n\nKind Regards,\nVelaphanda Technical Team"
		        + "\nWebsite: www.velaphanda.com";
				
		String subject = "Velaphanda Ticket Ref No " + newTicketNum+ticket.getRecordID();
		
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}	
	//User feedback from system to client
	public static void sendMailFeedBackFromSystemToClient(Tickets ticket,Employee employee) throws ParseException {
		//String userEmail = ticket.getTicketLoggedBy();
		String userClientEmail = ticket.getContactEmail();
		String[] to = { userClientEmail };
		String from = emailFrom;
		String pass = password;
		String slaFourHours = slaEndTime(ticket.getDateTime());
				
		String body = "Hi " + ticket.getFirstName() + " " + ticket.getLastName()
				
				+ "\n\nYour Ticket Details are as follows:" +"\n\n"
				+ "Ticket Number: " + newTicketNum+ticket.getRecordID() + "\n"
				+ "Time Logged: " + ticket.getDateTime() + "\n"
				+ "SLA End Time: " + slaFourHours +"\n\n"	
				
				+ "Heldesk Agent Details:\n\n"
				
				+ "Name & Surname: " +employee.getFirstName()+" "+employee.getLastName() +"\n"
				+ "Email: " + "helpdesk@velaphanda.co.za" + "\n" 
				+ "Call Center Number: " + "012 765 0200 / 087 701 1691" + "\n\n"
				+ "Assigned Technician Details:\n\n"
				+ "Name & Surname: " +ticket.getEmployee().getFirstName()+" "+ticket.getEmployee().getLastName() +"\n"
				+ "Email: " +ticket.getEmployee().getEmail()+ "\n" 
				+ "Contact Number: " + ticket.getEmployee().getCellNumber()+ "\n"
				
				+ "\n\nKind Regards,\nVelaphanda Technical Team"
				+ "\nWebsite: www.velaphanda.com";
				
		String subject = "Velaphanda Ticket Ref No " + newTicketNum+ticket.getRecordID();
		
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
	
	//Email message details to technician for escalation
	public static void sendMailDetailsToTechnicianForEscalation(Tickets ticket,Employee employee) {
		
		String[] to = {ticket.getEmployee().getEmail() };
		String from = emailFrom;
		String pass = password;
		String body = "Hi " + employee.getFirstName() + " " + employee.getLastName() + " The following service call has been escalated to Senior Manager:\n"
				
				+ "\n\nTicket Details are as follows:" +"\n\n"
				
				+ "Ticket Number: " + newTicketNum+ticket.getRecordID() + "\n"
				+ "Device Model: " + ticket.getDevice().getModelNumber() + "\n"
				+ "Serial Number: " + ticket.getDevice().getSerialNumber() +"\n"
				+ "Device Brand: " + ticket.getDevice().getModelBrand() +"\n"				
				+ "Problem Description: " + ticket.getDescription() + "\n"
				+ "Reason for Escalation: " + ticket.getComments() + "\n"
				+ "Previous technician: " + ticket.getEmployee().getFirstName()+" "+ticket.getEmployee().getLastName() + "\n"			 	
				+ "Location: " + ticket.getDevice().getStreetNumber()+" "+ticket.getDevice().getStreetName()+" , "+ ticket.getDevice().getCity_town() +" , "+ ticket.getDevice().getProvince() +"\n\n"
				
				+ "Client Contact Details:\n\n"
				
				+ "Name & Surname: " +ticket.getFirstName()+" "+ticket.getLastName() +"\n"
				+ "Email: " +ticket.getContactEmail()+ "\n" 
				+ "Cell Number: " + ticket.getContactCellNumber()+ "\n"
				+ "Tellphone Number: " + ticket.getContactTelephoneNumber() + "\n" 
				
				+ "\n\nKind Regards,\nVelaphanda Technical Team"
				+ "\nWebsite: www.velaphanda.com";
		
		String subject = "Velaphanda Ticket Ref No " + newTicketNum+ticket.getRecordID() + " Escalated To Senior Manager";
		
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
	
	//Escalation Notification to manager
	public static void sendMailEscalationNotificationToManager(Tickets ticket,String managerFirstName, String managerLastName) {
		
		String[] to = {ticket.getEscalatedTo()};
		String from = emailFrom;
		String pass = password;
		String body = "Hi " + managerFirstName + " " + managerLastName + " The following service call has been escalated to you:\n"
				
				+ "Ticket Details are as follows:" +"\n\n"
				
				+ "Ticket Number: " + newTicketNum+ticket.getRecordID() + "\n"
				+ "Device Model: " + ticket.getDevice().getModelNumber() + "\n"
				+ "Serial Number: " + ticket.getDevice().getSerialNumber() +"\n"
				+ "Device Brand: " + ticket.getDevice().getModelBrand() +"\n"				
				+ "Problem Description: " + ticket.getDescription() + "\n"
				+ "Reason for Escalation: " + ticket.getComments() + "\n"
				+ "Previous technician: " + ticket.getEmployee().getFirstName()+" "+ticket.getEmployee().getLastName() + "\n"			 	
				+ "Location: " + ticket.getDevice().getStreetNumber()+" "+ticket.getDevice().getStreetName()+" , "+ ticket.getDevice().getCity_town() +"  , "+ ticket.getDevice().getProvince() +"\n\n"
				
				+ "Client Contact Details:\n\n"
				
				+ "Name & Surname: " +ticket.getFirstName()+" "+ticket.getLastName() +"\n"
				+ "Email: " +ticket.getContactEmail()+ "\n" 
				+ "Cell Number: " + ticket.getContactCellNumber()+ "\n"
				+ "Tellphone Number: " + ticket.getContactTelephoneNumber() + "\n" 
				
				+ "\n\nKind Regards,\nVelaphanda Technical Team"
				+ "\nWebsite: www.velaphanda.com";
		
		String subject = "Velaphanda Ticket Ref No " + newTicketNum+ticket.getRecordID() + " Escalated To You";
		
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
	
	//Send email for password
	public static void sendPasswordToEmployee(Employee emp, String passw) {
			String[] to = { emp.getEmail() };
			String from = emailFrom;
			String pass = password;
			String body = "Hi "
					+ emp.getFirstName()
					+ " "
					+ emp.getLastName()
					+ ","
					+ "\n\nYour password is "
					+ passw
					+ "\n\nRemember to change your password on your first login\n\nKind Regards\nVelaphanda Support Team\nWebsite: www.velaphanda.com" +"\n";
			String subject = "Password Reset";
			Properties props = System.getProperties();
			String host = "smtp.mweb.co.za";
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);

			try {
				message.setFrom(new InternetAddress(from));
				InternetAddress[] toAddress = new InternetAddress[to.length];

				// To get the array of addresses
				for (int i = 0; i < to.length; i++) {
					toAddress[i] = new InternetAddress(to[i]);
				}

				for (int i = 0; i < toAddress.length; i++) {
					message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				}

				message.setSubject(subject);
				message.setText(body);
				Transport transport = session.getTransport("smtp");
				transport.connect(host, from, pass);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (AddressException ae) {
				ae.printStackTrace();
			} catch (MessagingException me) {
				me.printStackTrace();
			}
	}
	
	//send email for blocked accounts
	public static void accountLocked(Employee employee, String managerMail) {
		String[] to = { employee.getEmail(), managerMail };
		String from = emailFrom;
		String pass = password;
		String body = "Hi "
				+ employee.getFirstName()
				+ ","
				+ "\n\nYour account has been blocked. Please consult your manager for password reset."
				+ "\n\nKind Regards\nVelaphanda Technical Team\nWebsite: www.velaphanda.com";
		String subject = "Password Reset Required";
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
	
	
	//send email for approved oder
	public static void sendEmailForOrderApproved(String technicianEmail,String managerEmail, String managerFirstName,String technicianFirstName, OrderHeader order) {

		String[] to = { technicianEmail };
		String from = emailFrom;
		String pass = password;
		String body = "Hi " + technicianFirstName
				
				+ ",\n\nOrder Number : "+ newOrderNum+order.getRecordID()+ " is approved by "+  managerFirstName + "\n\n"
				
				+ "Help Desk Contact Details:\n\n"
		
				+ "Email: " + "helpdesk@velaphanda.co.za" + "\n" 
				+ "Call Center Number: " + "012 765 0200 / 087 701 1691" + "\n\n"
				
				+ "\n\nKind Regards,\nVelaphanda Technical Team"
				+ "\nWebsite: www.velaphanda.com";
				
		String subject = "Order Number " + newOrderNum+order.getRecordID() +" Approved";
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
	
	//send email for shipped orders
	public static void sendEmailForShipment(String managerEmail,OrderHeader order) {

			String[] to = { order.getEmployee().getEmail()};
			String from = emailFrom;
			String pass = password;
			String body = "Hi " + order.getEmployee().getFirstName()
					+ ",\n\nYou order is shipped please confirm if you received the order : "
					+ newOrderNum+order.getRecordID() + ".\n\nKind Regards\nVelaphanda Technical Team\nWebsite: www.velaphanda.com";
			String subject = "Order " + newOrderNum+order.getRecordID()+ " Shipment";
			Properties props = System.getProperties();
			String host = "smtp.mweb.co.za";
			props.put("mail.smtp.starttls.enable", "false");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);

			try {
				message.setFrom(new InternetAddress(from));
				InternetAddress[] toAddress = new InternetAddress[to.length];

				// To get the array of addresses
				for (int i = 0; i < to.length; i++) {
					toAddress[i] = new InternetAddress(to[i]);
				}

				for (int i = 0; i < toAddress.length; i++) {
					message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				}

				message.setSubject(subject);
				message.setText(body);
				Transport transport = session.getTransport("smtp");
				transport.connect(host, from, pass);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (AddressException ae) {
				ae.printStackTrace();
			} catch (MessagingException me) {
				me.printStackTrace();
			}
	}
	
	//Send email for site stock order
	public static void sendEmailOrderForSiteStock(String managerEmail, String technicianEmail,String managerFirstName,
			OrderHeader order){
		String[] to = { managerEmail, technicianEmail };
		String from = emailFrom;
		String pass = password;
		String body = "Hi " + managerFirstName +",\n\n"
				
				+ "Please review and attend to the following order:" +"\n\n"
				
				+ "Order Number: " + newOrderNum+order.getRecordID() + "\n"
				+ "Name of Site: " + order.getCustomer().getCustomerName() + " , " + order.getCustomer().getStreetNumber() + " " 
								   + order.getCustomer().getStreetName() + " , " + order.getCustomer().getCity_town() 
								   + "," +order.getCustomer().getProvince()+ " , " + order.getCustomer().getZipcode() + "\n"
				+ "Technician Name: " + order.getEmployee().getFirstName() + " " + order.getEmployee().getLastName() + "\n"
								
				+ "Help Desk Contact Details:\n\n"
				
				+ "Email: " + "helpdesk@velaphanda.co.za" + "\n" 
				+ "Call Center Number: " + "012 765 0200 / 087 701 1691" + "\n\n"
				
				+ "\n\nKind Regards,\nVelaphanda Technical Team\nWebsite: www.velaphanda.com";		
			
		
		String subject = "New Order " + newOrderNum+order.getRecordID() +" "+ order.getStockType() + " Stock";
		
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		
	}
	
	// send email for boot stock order
	public static void sendEmailOrderForBootStock(String managerEmail, String technicianEmail,String managerFirstName,
			OrderHeader order){
		String[] to = { managerEmail, technicianEmail };
		String from = emailFrom;
		String pass = password;
		String body = "Hi " + managerFirstName +",\n\n"
			
				+ "Please review and attend to the following order:" +"\n\n"
				
				+ "Order Number: " + newOrderNum+order.getRecordID() + "\n"
				+ "Technician Name: " + order.getEmployee().getFirstName() + " " + order.getEmployee().getLastName() + "\n"
								
				+ "Help Desk Contact Details:\n\n"
				
				+ "Email: " + "helpdesk@velaphanda.co.za" + "\n" 
				+ "Call Center Number: " + "012 765 0200 / 087 701 1691" + "\n\n"
				
				+ "\n\nKind Regards,\nVelaphanda Technical Team"
				+ "\nWebsite: www.velaphanda.com";
		
		String subject = "New Order " + newOrderNum+order.getRecordID() +" "+ order.getStockType() + " Stock";
		
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}

	}	
	
	//send email for one hour reminder to technician
	public static void SendMailToTechniciansForOneHourReminder(Tickets ticket) {
			    String[] to = { ticket.getEmployee().getEmail()};
				String from = emailFrom;
				String pass = password;
				String body = "Hi "
						+ ticket.getEmployee().getFirstName() + " " + ticket.getEmployee().getLastName()
						+ ","
						+ "\n\nKindly note that ticket "
						+ newTicketNum+ticket.getRecordID()
						+ " is assigned to you and it needs to be closed within three hours from now.\n\n"
						
				  	    + "Help Desk Contact Details:\n\n"
					    + "Email: " + "helpdesk@velaphanda.co.za" + "\n" 
					    + "Call Center Number: " + "012 765 0200 / 087 701 1691" + "\n\n"
						
						+ "\n\nKind Regards\nVelaphanda Technical Team\nWebsite: www.velaphanda.com";
				String subject = "Velaphanda Ticket Ref No " + newTicketNum+ticket.getRecordID() + " Reminder";
				Properties props = System.getProperties();
				String host = "smtp.mweb.co.za";
				props.put("mail.smtp.starttls.enable", "false");
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.user", from);
				props.put("mail.smtp.password", pass);
				props.put("mail.smtp.port", "25");
				props.put("mail.smtp.auth", "true");

				Session session = Session.getDefaultInstance(props);
				MimeMessage message = new MimeMessage(session);

				try {
					message.setFrom(new InternetAddress(from));
					InternetAddress[] toAddress = new InternetAddress[to.length];

					// To get the array of addresses
					for (int i = 0; i < to.length; i++) {
						toAddress[i] = new InternetAddress(to[i]);
					}

					for (int i = 0; i < toAddress.length; i++) {
						message.addRecipient(Message.RecipientType.TO, toAddress[i]);
					}

					message.setSubject(subject);
					message.setText(body);
					Transport transport = session.getTransport("smtp");
					transport.connect(host, from, pass);
					transport.sendMessage(message, message.getAllRecipients());
					transport.close();
				} catch (AddressException ae) {
					ae.printStackTrace();
				} catch (MessagingException me) {
					me.printStackTrace();
				}
			}
		
		//send email for four hours reminder to managers
		public static void SendMailToManagersForFourHoursReminder(Tickets ticket, String[] managerEmails) {
			String[] to = managerEmails;
			String from = emailFrom;
			String pass = password;
			String body = "Hi All,"
					+ "\n\nSLA for ticket " + newTicketNum+ticket.getRecordID()
					+ " is bridged.\n\n" 
					
					+ "Help Desk Contact Details: \n\n"
				    + "Email: " + "helpdesk@velaphanda.co.za" + "\n" 
				    + "Call Center Number: " + "012 765 0200 / 087 701 1691" + "\n\n"
				    + 
					"\n\nKind Regards\nVelaphanda Technical Team\nWebsite: www.velaphanda.com";
			String subject = "Velaphanda Ticket Ref No " + newTicketNum+ticket.getRecordID()
					+ " SLA Bridged";
			Properties props = System.getProperties();
			String host = "smtp.mweb.co.za";
			props.put("mail.smtp.starttls.enable", "false");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);

			try {
				message.setFrom(new InternetAddress(from));
				InternetAddress[] toAddress = new InternetAddress[to.length];

				// To get the array of addresses
				for (int i = 0; i < to.length; i++) {
					toAddress[i] = new InternetAddress(to[i]);
				}

				for (int i = 0; i < toAddress.length; i++) {
					message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				}

				message.setSubject(subject);
				message.setText(body);
				Transport transport = session.getTransport("smtp");
				transport.connect(host, from, pass);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (AddressException ae) {
				ae.printStackTrace();
			} catch (MessagingException me) {
				me.printStackTrace();
			}
	}
		
	
	//send email to person who locked the call upon ticket been resolved
	public static void sendEmailForResolvedTickets(Tickets ticket, Employee employee) {
		String userClientEmail = ticket.getContactEmail();
		String[] to = { userClientEmail };
		//String[] to = {employee.getEmail() };
		String from = emailFrom;
		String pass = password;
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String timeCallClosed = sdfDate.format(now);
		String body = "Hi " + ticket.getFirstName() + " " + ticket.getLastName()
				
				+ "\n\nTicket Ref No " + newTicketNum+ticket.getRecordID() + " has been resolved:" +"\n\n"
			
				+ "Device Fault: " + ticket.getDescription() + "\n"
				/*+ "Action Taken: " + ticket.getComments()+ "\n"*/
				+ "Time Call Closed: " + timeCallClosed +"\n\n"
					
				+ "Technician Contact Details:\n\n"
				+ "Name & Surname: " +ticket.getEmployee().getFirstName()+" "+ticket.getEmployee().getLastName() +"\n"
				+ "Email: " +ticket.getEmployee().getEmail()+ "\n" 
				+ "Contact Number: " + ticket.getEmployee().getCellNumber()+ "\n\n"
				
				+ "Help Desk Contact Details: \n\n"
				+ "Email: " + "helpdesk@velaphanda.co.za" + "\n" 
				+ "Call Center Number: " + "012 765 0200 / 087 701 1691" + "\n\n"
				
				
				+ "\n\nKind Regards,\nVelaphanda Technical Team"
				+ "\nWebsite: www.velaphanda.com";
		
		String subject = "Ticket Ref No " + newTicketNum+ticket.getRecordID() + " has been resolved";
		
		Properties props = System.getProperties();
		String host = "smtp.mweb.co.za";
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
	
	//calculate sla end time for ticket
	private static String slaEndTime(String slaStart) throws ParseException{
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = format.parse(slaStart);
		Calendar cal = Calendar.getInstance();
		// remove next line if you're always using the current time.
		cal.setTime(currentDate);
		cal.add(Calendar.HOUR, +4);
		String slaFourHour = format.format(cal.getTime());
		return ""+slaFourHour;
	}
	

}