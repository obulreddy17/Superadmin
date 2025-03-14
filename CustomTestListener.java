package cprefund;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class CustomTestListener implements ITestListener {
    private StringBuilder report = new StringBuilder();
    private int passedTests = 0;
    private int failedTests = 0;
    private int skippedTests = 0;
    private final String reportFilePath = "test-report.html";

    @Override
    public void onStart(ITestContext context) {
        report.append("<html><body>")
              .append("<h1>Test Suite Started at: ")
              .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
              .append("</h1><hr>");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests++;
        report.append("<p style='color:green;'>Test Passed: ")
              .append(result.getName())
              .append("</p>");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedTests++;
        report.append("<p style='color:red;'>Test Failed: ")
              .append(result.getName())
              .append("</p>");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests++;
        report.append("<p style='color:orange;'>Test Skipped: ")
              .append(result.getName())
              .append("</p>");
    }

    @Override
    public void onFinish(ITestContext context) {
        report.append("<hr><h2>Test Suite Finished at: ")
              .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
              .append("</h2>")
              .append("<p>Total Tests: ").append(passedTests + failedTests + skippedTests).append("</p>")
              .append("<p>Passed: ").append(passedTests).append("</p>")
              .append("<p>Failed: ").append(failedTests).append("</p>")
              .append("<p>Skipped: ").append(skippedTests).append("</p>")
              .append("</body></html>");

        // Save the report to a file
        saveReportToFile();

        // Send the report via email
        sendEmailReport();
    }

    private void saveReportToFile() {
        try (FileWriter writer = new FileWriter(reportFilePath)) {
            writer.write(report.toString());
            System.out.println("Test report saved to " + reportFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmailReport() {
        String to = "obul@neokred.tech";  // Multiple recipients (comma-separated)
        String cc = "richard@neokred.tech,ravishankar@neokred.tech";  // CC recipients (comma-separated)
        String from = "obul@neokred.tech";  // Sender email
        String host = "smtp.office365.com";  // Office 365 SMTP Server
        String password = "NKtech@123";  // ⚠️ Change this for security

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            // Add multiple TO recipients
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Add multiple CC recipients
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));

            message.setSubject("TestNG Execution Report");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Add email body with HTML formatting
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent("<h2>TestNG Execution Report</h2><p>Find the attached <b>test-report.html</b>.</p><hr>" + report.toString(), "text/html");

            // Attach the `test-report.html` file
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(reportFilePath));

            // Add parts to the multipart
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(attachmentPart);

            // Set content
            message.setContent(multipart);

            // Send email
            Transport.send(message);
            System.out.println("Email sent successfully with attachment: " + reportFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}