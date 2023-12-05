package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class EmailSender {
    public EmailSender() {
    }
    @Value("${sendgrid.api.key}")
    private String sendgridApiKey;
    public void sendActivationEmail(String userEmail, String activationLink) throws IOException {
        Email from = new Email("nemanjamc8@gmail.com");
        String subject = "Account activation";
        Email to = new Email(userEmail);
        Content content = new Content("text/plain", "Verify your account by clicking on a link: " + activationLink);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}

