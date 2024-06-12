package com.squer.promobee.controller



import com.squer.cme.utils.EmailUtils
import com.squer.promobee.controller.dto.MailContentPOJO

import com.squer.promobee.service.EmailService

import lombok.extern.slf4j.Slf4j
import org.apache.ibatis.session.SqlSessionFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper

import org.springframework.web.bind.annotation.GetMapping

import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.servlet.http.HttpServletResponse


@Slf4j
open class EmailController@Autowired constructor(
    private val emailService: EmailService,

    ) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    @GetMapping("/getConsolidateExpiryReport")
    fun getConsolidateExpiryReport  (response: HttpServletResponse): ResponseEntity<*> {
        var intervals= mutableListOf<IntArray>()
        intervals.add(intArrayOf(32, 61, 1))
        intervals.add(intArrayOf(62, 121, 1))
        intervals.add(intArrayOf(185, 270, 1))
        intervals.add(intArrayOf(270, 365, 2))
        lateinit var data: ByteArray
        var fileContentList = mutableListOf<MailContentPOJO>()
        intervals.forEach{ it ->
            var index1= it[0]
            var index2=it[1]
            data = emailService.getConsolidateExpiryReport(response,it[0],it[1])


            fileContentList.add(MailContentPOJO(fileName = "ExpiryReportIn${index1}-${index2}days" , String(Base64.getEncoder().encode(data))))
            val file = File("D:\\UNS_MAILS\\ExpiryReportIn${index1}-${index2}days.xlsx");

            val os= FileOutputStream(file)

            os.write(data)
            println("Successfully"
                    + " byte inserted")
            os.close()
        }
        val calendar = Calendar.getInstance()

        var currentMonth = calendar.get(Calendar.MONTH)


        var monthNames = arrayOf("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER")
        var currentMonthName = monthNames[currentMonth]

        intervals.forEach{
            val fileSystemResource= FileSystemResource(File("D:\\UNS_MAILS\\ExpiryReportIn${it[0]}-${it[1]}days.xlsx"))
        }
        var emailUtil = EmailUtils()
        emailUtil.sendMail(mutableListOf("satendra.yadav@squer.co.in","ashutosh.pavaskar@sanofi.com"), mutableListOf(), "Dear All </br> </br> Attached are the Consolidated data of Inputs and Samples which are in Near Expiry Category. </br> </br> Respective Brand Managers and their Superior have been intimated over mail. </br></br> Thank You.",
            "Consolidated Expiry Mail for the Month- ${currentMonthName}")
        println("Mail Sent!")
        return  ResponseEntity(fileContentList, HttpStatus.OK)


    }



    //BRAND MANAGER ITEM MAILS

//    @GetMapping("/SendTestMailForItemExpiry")
//    fun SendTestMailForItemExpiry  (response: HttpServletResponse): ResponseEntity<*> {
//
//        val user =
//            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//
//        var data0: MutableMap<String, Any> = mutableMapOf()
//
//        data0.put("designationId", UserRoleEnum.PRODUCT_MANAGER_ID.id)
//        data0.put("statusId", UserStatusEnum.ACTIVE.id)
//
//
//        var brandManager = sqlSessionFactory.openSession().selectList<User>("UserMapper.emailer", data0)
//
//
//        var intervals = mutableListOf<IntArray>()
//        intervals.add(intArrayOf(32, 61, 1))
//        intervals.add(intArrayOf(62, 121, 1))
//
//        lateinit var data: ByteArray
//        var fileContentList = mutableListOf<MailContentPOJO>()
//
//        brandManager.forEach { it ->
//
//            var userId = it.id
//
//            intervals.forEach { it ->
//
//                var index1 = it[0]
//                var index2 = it[1]
//                data = emailService.SendTestMailForItemExpiry(response,userId ,it[0], it[1])
//
//
//                fileContentList.add(
//                    MailContentPOJO(
//                        fileName = "ExpiryReportIn${index1}-${index2}days",
//                        String(Base64.getEncoder().encode(data))
//                    )
//                )
//                val file = File("D:\\UNS_MAILS\\ExpiryReportIn${index1}-${index2}days.xlsx");
//
//                val os = FileOutputStream(file)
//
//                // Starting writing the bytes in it
//
//                // Starting writing the bytes in it
//                os.write(data)
//                println(
//                    "Successfully"
//                            + " byte inserted"
//                )
//                os.close()
//            }
//            val calendar = Calendar.getInstance()
//            val mimeMessage = mailSender.createMimeMessage()
//            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
//            mimeMessageHelper.setFrom("chc.promobee@squer.co.in")
//           // mimeMessageHelper.setTo(it.email!!)
//            mimeMessageHelper.setTo(InternetAddress.parse("shraddha.tambe@sanofi.com , amol.mali@sanofi.com"))
//            mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
//            mimeMessageHelper.setText("Hi, " + it.name +
//                    "\n You are advised to take necessary actions for utilization of these Inputs Before they got blocked\n" +
//                "\nPS :- Due to brand alignment in Promobee few inputs are shown in the list might be from other brands. Requesting you to ignore the same.\n" +
//                "\nThank You\n" +
//                " ")
//
//
//            var currentMonth = calendar.get(Calendar.MONTH)
//
//
//            var monthNames = arrayOf("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER")
//            var currentMonthName = monthNames[currentMonth]
//            mimeMessageHelper.setSubject("Item Expiry Mail for the Month-" + currentMonthName)
//            intervals.forEach {
//                val fileSystemResource =
//                    FileSystemResource(File("D:\\UNS_MAILS\\ExpiryReportIn${it[0]}-${it[1]}days.xlsx"))
//                mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)
//            }
//
//            mailSender.send(mimeMessage)
//            println("Mail Sent!")
//
//        }
//
//        return ResponseEntity(fileContentList, HttpStatus.OK)
//
//    }
//
//
//
//
//
//
//
//    @GetMapping("/SendTestMailForSampleExpiry")
//    fun SendTestMailForSampleExpiry  (response: HttpServletResponse): ResponseEntity<*> {
//
//        val user =
//            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//
//        var data0: MutableMap<String, Any> = mutableMapOf()
//
//        data0.put("designationId", UserRoleEnum.PRODUCT_MANAGER_ID.id)
//        data0.put("statusId", UserStatusEnum.ACTIVE.id)
//
//
//        var brandManager = sqlSessionFactory.openSession().selectList<User>("UserMapper.emailer", data0)
//
//
//        var intervals = mutableListOf<IntArray>()
//        intervals.add(intArrayOf(185, 270, 1))
//        intervals.add(intArrayOf(270, 365, 2))
//
//        lateinit var data: ByteArray
//        var fileContentList = mutableListOf<MailContentPOJO>()
//
//        brandManager.forEach { it ->
//
//            var userId = it.id
//
//            intervals.forEach { it ->
//                var index1 = it[0]
//                var index2 = it[1]
//                data = emailService.SendTestMailForSampleExpiry(response, userId,it[0], it[1])
//
//
//                fileContentList.add(
//                    MailContentPOJO(
//                        fileName = "ExpiryReportIn${index1}-${index2}days",
//                        String(Base64.getEncoder().encode(data))
//                    )
//                )
//                val file = File("D:\\UNS_MAILS\\ExpiryReportIn${index1}-${index2}days.xlsx");
//
//                val os = FileOutputStream(file)
//
//                // Starting writing the bytes in it
//
//                // Starting writing the bytes in it
//                os.write(data)
//                println(
//                    "Successfully"
//                            + " byte inserted"
//                )
//                os.close()
//            }
//            val calendar = Calendar.getInstance()
//            val mimeMessage = mailSender.createMimeMessage()
//            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
//            mimeMessageHelper.setFrom("chc.promobee@squer.co.in")
////            mimeMessageHelper.setTo(it.email!!)
//            mimeMessageHelper.setTo(InternetAddress.parse("shraddha.tambe@sanofi.com , amol.mali@sanofi.com"))
//           mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
//            mimeMessageHelper.setText("Hi, " + it.name +
//                    "\n You are advised to take necessary actions for utilization of these Samples Before they got blocked\n" +
//                    "\nPS :- Due to brand alignment in Promobee few Samples are shown in the list might be from other brands. Requesting you to ignore the same.\n" +
//                    "\nThank You\n" +
//                    " ")
//
//            var currentMonth = calendar.get(Calendar.MONTH)
//
//
//            var monthNames = arrayOf("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER")
//            var currentMonthName = monthNames[currentMonth]
//            mimeMessageHelper.setSubject("Sample Expiry Mail for the Month-" + currentMonthName)
//            intervals.forEach {
//                val fileSystemResource =
//                    FileSystemResource(File("D:\\UNS_MAILS\\ExpiryReportIn${it[0]}-${it[1]}days.xlsx"))
//                mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)
//            }
//
//            mailSender.send(mimeMessage)
//            println("Mail Sent!")
//
//        }
//
//        return ResponseEntity(fileContentList, HttpStatus.OK)
//
//    }
//
//
//
//
//    // BU CHAMP COMPLAINCE MAIL
//
//
//
//    @GetMapping("/Send_Mail_optima/{uploadId}")
//    fun Send_Mail_optima  (@PathVariable uploadId:String , response: HttpServletResponse): ResponseEntity<*> {
//
//        val user =
//            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//
//
//        var brandManager = sqlSessionFactory.openSession().selectList<UserEmailSendDTO>("ReportMapper.GetBuChampionForCompliance")
//
//
//        lateinit var data: ByteArray
//        var fileContentList = mutableListOf<MailContentPOJO>()
//
//        brandManager.forEach { it ->
//
//
//
//
//
//                data = emailService.Send_Mail_optima( uploadId, response)
//
//
//                fileContentList.add(
//                    MailContentPOJO(
//                        fileName = "Blocked list",
//                        String(Base64.getEncoder().encode(data))
//                    )
//                )
//                val file = File("D:\\UNS_MAILS\\Blocked list.xlsx");
//
//                val os = FileOutputStream(file)
//
//                os.write(data)
//                println(
//                    "Successfully"
//                            + " byte inserted"
//                )
//                os.close()
//
//            val calendar = Calendar.getInstance()
//            val mimeMessage = mailSender.createMimeMessage()
//            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
//            mimeMessageHelper.setFrom("chc.promobee@squer.co.in")
//           // mimeMessageHelper.setTo("Sanjeev.Bidi@sanofi.com")
//            mimeMessageHelper.setTo(InternetAddress.parse("dinesh.sawant@sanofi.com , sanjeev.bidi@sanofi.com"))
//            mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
//
//
//
//            mimeMessageHelper.setText(
//                "Hi, " + it.userName + "\n\n" +
//                        "Following are the Names of FF/AM/RBM who have been blocked from receiving any further Medicine Samples since they have not Validated / Distributed 100% of samples received by them.\n\n" +
//
//                        "If any of them have not validated or distributed for a valid reason, you have to mention the reason for Non Validation/Distribution in PromoBee using the link below within the next 7 days:\n\n" +
//                        "http://promobee-uat/#/login\n\n" +
//                        "This will enable the Medicine Samples dispatch in the current month post admin authorization.\n\n" +
//                        "Kindly do the needful.\n\n" +
//                        "Thank You.\n\n"
//            );
//
//            mimeMessageHelper.setSubject("Blocked list of FF and Remarks not submitted")
//
//                val fileSystemResource =
//                    FileSystemResource(File("D:\\UNS_MAILS\\Blocked list.xlsx"))
//                mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)
//
//
//            mailSender.send(mimeMessage)
//            println("Mail Sent!")
//
//        }
//
//        return ResponseEntity(fileContentList, HttpStatus.OK)
//
//    }
//
//
//
//    @GetMapping("/Send_Mail_oversampling")
//
//    fun Send_Mail_oversampling  (): ResponseEntity<*> {
//
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//
//        var data = sqlSessionFactory.openSession().selectList<ComplianceBuChampionDTO>("ComplianceDetailsMapper.buChampionData")
//
//
//        data.forEach {
//
//            val calendar = Calendar.getInstance()
//            val mimeMessage = mailSender.createMimeMessage()
//            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
//            mimeMessageHelper.setFrom("chc.promobee@squer.co.in")
//            //mimeMessageHelper.setTo(it.EMAIL_ADDRESS_USR!!)
//            mimeMessageHelper.setTo("satendra.yadav@squer.co.in")
//           // mimeMessageHelper.setTo(InternetAddress.parse("dinesh.sawant@sanofi.com , sanjeev.bidi@sanofi.com"))
//            //mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
//            mimeMessageHelper.setText("Hi, " + it.NAME_USR +
//                    "\n\nBelow are the doctors who have been given more than 120 units of medicine samples in a quarter. " +
//                    "\nAs per SOP, you need to give the reason for this over sampling. " +
//                    "\nKindly click on the below link and then select the reasons from the dropdown for each doctor. " +
//                    "\n\nclick on the " +
//                    "http://promobee-uat/#/login\n\n" +
//                    "\n\nKindly do the needful" +
//                    "\n\nThank You.");
//            mimeMessageHelper.setSubject("Compliance Remarks not submitted")
//
//
//            mailSender.send(mimeMessage)
//            println("Mail Sent!")
//
//
//
//
//        }
//
//
//        return ResponseEntity(data, HttpStatus.OK)
//
//    }
//
//
//
//
//
//
//
//
//
//    @GetMapping("/SpecialDraftPlanReminder")
//    fun SpecialDraftPlanReminder  (): ResponseEntity<*> {
//
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//
//        var data = emailService.SpecialDraftPlanReminder()
//
//        return ResponseEntity(data, HttpStatus.OK)
//
//    }
//
//
//
//
//    @GetMapping("/SendMailFFSampleInputNearExpiry/{uploadId}")
//    fun SendMailFFSampleInputNearExpiry  (@PathVariable uploadId:String): ResponseEntity<*> {
//
//        val user =
//            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//
//        var data1: MutableMap<String, Any> = mutableMapOf()
//
//        data1.put("uploadId",uploadId)
//
//        var recipient = sqlSessionFactory.openSession().selectList<Recipient>("RecipientMapper.SendMailFFSampleInputNearExpiry",data1)
//
//
//
//        lateinit var data: ByteArray
//        var fileContentList = mutableListOf<MailContentPOJO>()
//
//        recipient.forEach { it ->
//
//
//                data = emailService.SendMailFFSampleInputNearExpiry(uploadId)
//
//
//                fileContentList.add(
//                    MailContentPOJO(
//                        fileName = "Near Expiry Product",
//                        String(Base64.getEncoder().encode(data))
//                    )
//                )
//                val file = File("D:\\UNS_MAILS\\NearExpiryProduct.xlsx");
//
//                val os = FileOutputStream(file)
//
//                // Starting writing the bytes in it
//
//                // Starting writing the bytes in it
//                os.write(data)
//                println(
//                    "Successfully"
//                            + " byte inserted"
//                )
//                os.close()
//
//            val calendar = Calendar.getInstance()
//            val mimeMessage = mailSender.createMimeMessage()
//            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
//            mimeMessageHelper.setFrom("chc.promobee@squer.co.in")
////            mimeMessageHelper.setTo(it.email!!)
//            mimeMessageHelper.setTo(InternetAddress.parse("dinesh.sawant@sanofi.com , sanjeev.bidi@sanofi.com"))
//            mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
//            mimeMessageHelper.setText("Hi, ${it.name} \n\n" +
//                    "The attached excel has details of Physician Samples and Inputs having near expiry as per system. \n\n" +
//                    "Please utilize these items at your earliest convenience.\n\n" +
//                    "Thank you.\n");
//            mimeMessageHelper.setSubject("Near Expiry Products" )
//            val fileSystemResource =
//                FileSystemResource(File("D:\\UNS_MAILS\\NearExpiryProduct.xlsx"))
//            mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)
//
//            mailSender.send(mimeMessage)
//            println("Mail Sent!")
//
//        }
//
//        return ResponseEntity(fileContentList, HttpStatus.OK)
//
//    }
//
//
//
//    @GetMapping("/SendMailFFSampleInputExpired/{uploadId}")
//    fun SendMailFFSampleInputExpired  (@PathVariable uploadId:String): ResponseEntity<*> {
//
//        val user =
//            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//
//        var data1: MutableMap<String, Any> = mutableMapOf()
//
//        data1.put("uploadId",uploadId)
//
//        var recipient = sqlSessionFactory.openSession().selectList<Recipient>("RecipientMapper.SendMailFFSampleInputNearExpiry",data1)
//
//
//
//        lateinit var data: ByteArray
//        var fileContentList = mutableListOf<MailContentPOJO>()
//
//        recipient.forEach { it ->
//
//            data = emailService.SendMailFFSampleInputExpired(uploadId)
//
//
//            fileContentList.add(
//                MailContentPOJO(
//                    fileName = "Near Expiry Product",
//                    String(Base64.getEncoder().encode(data))
//                )
//            )
//            val file = File("D:\\UNS_MAILS\\ExpiredProduct.xlsx");
//
//            val os = FileOutputStream(file)
//
//            // Starting writing the bytes in it
//
//            // Starting writing the bytes in it
//            os.write(data)
//            println(
//                "Successfully"
//                        + " byte inserted"
//            )
//            os.close()
//
//            val calendar = Calendar.getInstance()
//            val mimeMessage = mailSender.createMimeMessage()
//            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
//            mimeMessageHelper.setFrom("chc.promobee@squer.co.in")
////            mimeMessageHelper.setTo(it.email!!)
//            mimeMessageHelper.setTo(InternetAddress.parse("dinesh.sawant@sanofi.com , sanjeev.bidi@sanofi.com"))
//            mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
//            mimeMessageHelper.setText("Hi, ${it.name} \n\nAction for Expired Samples\n" +
//                    "  \n" +
//                    "    You are requested to send the expired samples to the nearest C&F by courier.\n" +
//                    "    Please enter the quantity sent to C&F in the \"Qty sent To CFA\" column.\n" +
//                    "    If the quantity is different from the balance quantity, please mention the reason for this in the \"Reason for Diff in Qty\" column.\n" +
//                    "  \n\n" +
//                    "  Action for Expired Inputs\n" +
//                    "  \n" +
//                    "    Please destroy the expired inputs at your end.\n" +
//                    "    Please indicate whether you have destroyed the expired inputs in the \"Destroyed (Yes/No)\" column.\n" +
//                    "  \n\n" +
//                    " Once you have entered the above details for all rows, please enter the courier details and click on \"SUBMIT\"\n\n" +
//                    " Please note that if you do not take action on this email, your access to future samples will be blocked\n\n" +
//                    " To check the expiry of samples and inputs and take appropriate action, please click on the following links\n" +
//                    "  \n" +
//                    "     href=\"http://aspire-squer.com:8080/webapp/index.jsp#/home/promobee/details?cert=X7e2zG9Hpp%2Fy8rpePCaByjtjEXMqvWNXG%2BlfWQ3zt93NRRo8Y%2FFFaK7IBXADbzVxqG%2BjHhfQRwnizfA8UmNPjqoXPRj3fkLctlf%2FcbnBuHLYgUjxIufcuIcmZfNmUjuX2AQqngja%2B6qh1Zkz4PD20Xb0%2FU9Kn6cbxk9aKlEx%2FjzgikTU3YSg%2FOOGFxigWxsV&empid=84ea02b6-fe27-4b0f-b964-3debd95b2729&type=sample\"Click here To Check The Samples Expiry And Take Appropriate Action\n" +
//                    "     href=\"http://aspire-squer.com:8080/webapp/index.jsp#/home/promobee/details?cert=X7e2zG9Hpp%2Fy8rpePCaByjtjEXMqvWNXG%2BlfWQ3zt93NRRo8Y%2FFFaK7IBXADbzVxqG%2BjHhfQRwnizfA8UmNPjqoXPRj3fkLctlf%2FcbnBuHLYgUjxIufcuIcmZfNmUjuX2AQqngja%2B6qh1Zkz4PD20Xb0%2FU9Kn6cbxk9aKlEx%2FjzgikTU3YSg%2FOOGFxigWxsV&empid=0c8c111a-bc40-42d4-9b14-cb848dab0b51&type=input\"Click here To Check The Inputs"
//            )
//
//            mimeMessageHelper.setSubject("Expired Sample and Inputs" )
//            val fileSystemResource =
//                FileSystemResource(File("D:\\UNS_MAILS\\ExpiredProduct.xlsx"))
//            mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)
//
//            mailSender.send(mimeMessage)
//            println("Mail Sent!")
//
//        }
//
//        return ResponseEntity(fileContentList, HttpStatus.OK)
//
//    }




}
