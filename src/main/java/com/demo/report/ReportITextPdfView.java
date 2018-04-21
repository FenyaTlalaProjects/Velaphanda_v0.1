package com.demo.report;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class ReportITextPdfView extends AbstractView{
	
	public ReportITextPdfView() {
		setContentType("application/pdf");
	}

	@Override
	public boolean generatesDownloadContent() {
		return true;
	}
		
	@Override
	public void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		// Apply preferences and build metadata.
		Document document = newDocument();
		PdfWriter writer = newWriter(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);
		Long recordID =0L;

		// Build PDF document.
		document.open();
		buildPdfDocument(model, document, writer, request, response,recordID);
		document.close();

		// Flush to HTTP response.
		writeToResponse(response, baos);
	}

	public Document newDocument() {
		return new Document(PageSize.A4.rotate());
	}
	
	public PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}
	
	public void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {

		writer.setViewerPreferences(getViewerPreferences());
	}
	
	public int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}
	
	public void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}
	
	public abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response,Long recordID) throws Exception;	

}
