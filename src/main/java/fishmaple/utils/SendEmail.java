package fishmaple.utils;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class SendEmail {
    static String HOST = "smtp.qq.com"; // smtp服务器
    static String FROM = "blog@fishmaple.cn"; // 发件人地址
    public static String REDIRECT = "redirect@fishmaple.cn"; // 发件人地址
    static String USER = "鱼鱼"; // 用户名
    static String PWD = "-"; // 授权码
    static Logger log = LoggerFactory.getLogger(SendEmail.class);


     public static void send(String subject,String context,String tos,String ccs)  {
         Properties props = new Properties();

         try {

             MailSSLSocketFactory sf = new MailSSLSocketFactory();
             sf.setTrustAllHosts(true);
             props.put("mail.smtp.ssl.socketFactory",sf);//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
         }catch(Exception e){

        // props.put("mail.smtp.ssl.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）

     } props.put("mail.smtp.ssl.enable", "true");//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        // props.put("mail.smtp.socketFactory.port", "456");//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
         props.put("mail.smtp.host", HOST);//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
         props.put("mail.smtp.port", "465");//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.auth", "true");  //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        Session session = Session.getDefaultInstance(props);//用props对象构建一个session
        session.setDebug(true);
        try {
            Transport transport = session.getTransport("smtp");//发送邮件
            transport.connect(HOST,FROM, PWD);//连接服务器的邮箱
            MimeMessage message = new MimeMessage(session);//用session为参数定义消息对象
            message.setFrom(new InternetAddress(FROM));// 加载发件人地址
            String TOS[]=tos.split(",");
            InternetAddress[] sendTo = new InternetAddress[TOS.length]; // 加载收件人地址
            for (int i = 0; i < TOS.length; i++) {
                sendTo[i] = new InternetAddress(TOS[i]);
            }
            String CCS[]=ccs.split(",");
            InternetAddress[] cc = new InternetAddress[CCS.length]; // 加载收件人地址
            InternetAddress[] sendCC = new InternetAddress[CCS.length]; // 加载收件人地址
            for (int i = 0; i < CCS.length; i++) {
                sendCC[i] = new InternetAddress(CCS[i]);
            }
            message.addRecipients(Message.RecipientType.TO,sendTo);
            message.addRecipients(MimeMessage.RecipientType.CC, sendCC);//抄送
            message.setSubject(MimeUtility.encodeText(subject,"utf-8","B"));//加载标题
           Multipart multipart = new MimeMultipart();//向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            BodyPart contentPart = new MimeBodyPart();//设置邮件的文本内容
           contentPart.setContent(context, "text/html;charset=utf-8");
            //contentPart.setText(context);
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);//将multipart对象放到message中
            message.saveChanges(); //保存邮件


            transport.sendMessage(message, message.getAllRecipients());//把邮件发送出去
            transport.close();//关闭连接
            ////

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }



}
