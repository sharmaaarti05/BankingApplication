package com.ba.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.ba.model.TransactionHistory;

public class PDFGenerator {

	public static void generatePDF(List<TransactionHistory> dataList, String filePath) throws IOException {
		try (PDDocument document = new PDDocument()) {
			PDPage page = new PDPage();
			document.addPage(page);
			PDPageContentStream contentStream = null;
			;
			try {
				contentStream = new PDPageContentStream(document, page);
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
				float margin = 10;
				float yPosition = page.getMediaBox().getHeight() - margin;
				float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
				float yStart = yPosition;
				int rowsPerPage = 30;
				int numRows = 0;
				String[] headers = { "Customer ID", "Credit/Debit", "Amount", "Final Balance", "Date" };
				drawTableHeader(contentStream, tableWidth, yStart, yPosition, margin, headers);
				yPosition -= 30;

				for (TransactionHistory txnHis : dataList) {

					List<String> rowdata = new ArrayList<>();
					rowdata.add(String.valueOf(txnHis.getCustomerId()));
					rowdata.add(String.valueOf(txnHis.getCreditDebit()));
					rowdata.add(String.valueOf(txnHis.getAmount()));
					rowdata.add(String.valueOf(txnHis.getFinalBalance()));
					rowdata.add(String.valueOf(txnHis.getDatetime()));
					drawTableRow(contentStream, tableWidth, yStart, yPosition, margin, rowdata);
					yPosition -= 20;
					numRows++;
					if (numRows % rowsPerPage == 0) {
						contentStream.close();
						PDPage newPage = new PDPage();
						document.addPage(newPage);
						contentStream = new PDPageContentStream(document, newPage);
						yPosition = yStart;
					}
				}

			} finally {
				contentStream.close();
			}

			document.save(filePath);
			document.close();
		}
	}

	private static void drawTableHeader(PDPageContentStream contentStream, float tableWidth, float yStart,
			float yPosition, float margin, String[] headers) throws IOException {
		contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
		contentStream.setLineWidth(0.5f);
		contentStream.moveTo(margin, yPosition);
		contentStream.lineTo(margin + tableWidth, yPosition);
		contentStream.stroke();
		yPosition -= 15;
		float y = yPosition + 5;
		for (String header : headers) {
			contentStream.beginText();
			contentStream.newLineAtOffset(margin + 2, y);
			contentStream.showText(header);
			contentStream.endText();
			margin = margin + 100;
			// y -= 15;
		}
	}

	private static void drawTableRow(PDPageContentStream contentStream, float tableWidth, float yStart, float yPosition,
			float margin, List<String> rowData) throws IOException {
		contentStream.setFont(PDType1Font.HELVETICA, 12);
		contentStream.setLineWidth(0.5f);
		contentStream.moveTo(margin, yPosition);
		contentStream.lineTo(margin + tableWidth, yPosition);
		contentStream.stroke();
		float y = yPosition + 5;
		for (String data : rowData) {
			contentStream.beginText();
			contentStream.newLineAtOffset(margin + 2, y);
			contentStream.showText(data);
			contentStream.endText();
			margin = margin + 100;
			// y -= 15;
		}
	}
}
