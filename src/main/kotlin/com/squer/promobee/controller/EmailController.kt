package com.squer.promobee.controller


import com.squer.promobee.api.v1.enums.UserRoleEnum
import com.squer.promobee.controller.dto.FileContentPOJO
import com.squer.promobee.controller.dto.MailContentPOJO
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.domain.enum.UserStatusEnum
import com.squer.promobee.service.EmailService
import lombok.extern.slf4j.Slf4j
import org.apache.ibatis.session.SqlSessionFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.web.bind.annotation.GetMapping
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.servlet.http.HttpServletResponse


@Slf4j
open class EmailController@Autowired constructor(
    private val emailService: EmailService,
    private val mailSender:JavaMailSender
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory




    @GetMapping("/getConsolidatedExpiryReport")
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

            // Starting writing the bytes in it

            // Starting writing the bytes in it
            os.write(data)
            println("Successfully"
                    + " byte inserted")
            os.close()
        }
        val calendar = Calendar.getInstance()
        val mimeMessage= mailSender.createMimeMessage()
        val mimeMessageHelper= MimeMessageHelper(mimeMessage,true)
        mimeMessageHelper.setFrom("satendrayadav01567@gmail.com")
        mimeMessageHelper.setTo("satendra.yadav@squer.co.in")
        mimeMessageHelper.setCc("atharvdiagnostic@gmail.com")
        mimeMessageHelper.setText("Dear All,\n" +
                "\n" +
                "Attached are the Consolidated data of Inputs and Samples which are in “Near Expiry” Category.\n" +
                "\n" +
                "Respective Brand Managers and their Superior have been intimated over mail.\n" +
                "\n" +
                "Thank You\n" +
                "\n" +
                " ")
        mimeMessageHelper.setSubject("Consolidated Expiry Mail for the Month-"+ calendar.get(Calendar.MONTH),)
        intervals.forEach{
            val fileSystemResource= FileSystemResource(File("D:\\UNS_MAILS\\ExpiryReportIn${it[0]}-${it[1]}days.xlsx"))
            mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)
        }
        mailSender.send(mimeMessage)
        println("Mail Sent!")
        return  ResponseEntity(fileContentList, HttpStatus.OK)


    }



    //BRAND MANAGER ITEM MAILS

    @GetMapping("/SendTestMailForItemExpiry")
    fun SendTestMailForItemExpiry  (response: HttpServletResponse): ResponseEntity<*> {

        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("designationId", UserRoleEnum.PRODUCT_MANAGER_ID.id)
        data0.put("statusId", UserStatusEnum.ACTIVE.id)


        var brandManager = sqlSessionFactory.openSession().selectList<User>("UserMapper.emailer", data0)


        var intervals = mutableListOf<IntArray>()
        intervals.add(intArrayOf(32, 61, 1))
        intervals.add(intArrayOf(62, 121, 1))

        lateinit var data: ByteArray
        var fileContentList = mutableListOf<MailContentPOJO>()

        brandManager.forEach { it ->

            intervals.forEach { it ->
                var index1 = it[0]
                var index2 = it[1]
                data = emailService.SendTestMailForItemExpiry(response, it[0], it[1])


                fileContentList.add(
                    MailContentPOJO(
                        fileName = "ExpiryReportIn${index1}-${index2}days",
                        String(Base64.getEncoder().encode(data))
                    )
                )
                val file = File("D:\\UNS_MAILS\\ExpiryReportIn${index1}-${index2}days.xlsx");

                val os = FileOutputStream(file)

                // Starting writing the bytes in it

                // Starting writing the bytes in it
                os.write(data)
                println(
                    "Successfully"
                            + " byte inserted"
                )
                os.close()
            }
            val calendar = Calendar.getInstance()
            val mimeMessage = mailSender.createMimeMessage()
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
            mimeMessageHelper.setFrom("satendrayadav01567@gmail.com")
            mimeMessageHelper.setTo(it.email!!)
            mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
            mimeMessageHelper.setText("Hi, ",it.name +

                    "\n You are advised to take necessary actions for utilization of these Inputs Before they got blocked\n" +

                    "\nPS :- Due to brand alignment in Promobee few input / Sample are shown in the list might be from other brands. Requesting you to ignore the same.\n" +

                    "\nThank You\n" +
                    " ")
            mimeMessageHelper.setSubject("Item Expiry Mail for the Month-" + calendar.get(Calendar.MONTH),)
            intervals.forEach {
                val fileSystemResource =
                    FileSystemResource(File("D:\\UNS_MAILS\\ExpiryReportIn${it[0]}-${it[1]}days.xlsx"))
                mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)
            }

            mailSender.send(mimeMessage)
            println("Mail Sent!")

        }

        return ResponseEntity(fileContentList, HttpStatus.OK)

    }







    @GetMapping("/SendTestMailForSampleExpiry")
    fun SendTestMailForSampleExpiry  (response: HttpServletResponse): ResponseEntity<*> {

        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("designationId", UserRoleEnum.PRODUCT_MANAGER_ID.id)
        data0.put("statusId", UserStatusEnum.ACTIVE.id)


        var brandManager = sqlSessionFactory.openSession().selectList<User>("UserMapper.emailer", data0)


        var intervals = mutableListOf<IntArray>()
        intervals.add(intArrayOf(185, 270, 1))
        intervals.add(intArrayOf(270, 365, 2))

        lateinit var data: ByteArray
        var fileContentList = mutableListOf<MailContentPOJO>()

        brandManager.forEach { it ->

            intervals.forEach { it ->
                var index1 = it[0]
                var index2 = it[1]
                data = emailService.SendTestMailForSampleExpiry(response, it[0], it[1])


                fileContentList.add(
                    MailContentPOJO(
                        fileName = "ExpiryReportIn${index1}-${index2}days",
                        String(Base64.getEncoder().encode(data))
                    )
                )
                val file = File("D:\\UNS_MAILS\\ExpiryReportIn${index1}-${index2}days.xlsx");

                val os = FileOutputStream(file)

                // Starting writing the bytes in it

                // Starting writing the bytes in it
                os.write(data)
                println(
                    "Successfully"
                            + " byte inserted"
                )
                os.close()
            }
            val calendar = Calendar.getInstance()
            val mimeMessage = mailSender.createMimeMessage()
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
            mimeMessageHelper.setFrom("satendrayadav01567@gmail.com")
            mimeMessageHelper.setTo(it.email!!)
            mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
            mimeMessageHelper.setText("Hi, ",it.name +

                    "\n You are advised to take necessary actions for utilization of these Inputs Before they got blocked\n" +

                    "\nPS :- Due to brand alignment in Promobee few input / Sample are shown in the list might be from other brands. Requesting you to ignore the same.\n" +

                    "\nThank You\n" +
                    " ")
            mimeMessageHelper.setSubject("Sample Expiry Mail for the Month-" + calendar.get(Calendar.MONTH),)
            intervals.forEach {
                val fileSystemResource =
                    FileSystemResource(File("D:\\UNS_MAILS\\ExpiryReportIn${it[0]}-${it[1]}days.xlsx"))
                mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)
            }

            mailSender.send(mimeMessage)
            println("Mail Sent!")

        }

        return ResponseEntity(fileContentList, HttpStatus.OK)

    }




    // BU CHAMP COMPLAINCE MAIL

    @GetMapping("/Send_Mail_optima/{uploadId}")
    fun Send_Mail_optima  (response: HttpServletResponse,uploadId:String): ResponseEntity<*> {

        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var brandManager = sqlSessionFactory.openSession().selectList<User>("ReportMapper.GetBuChampionForCompliance")


        lateinit var data: ByteArray
        var fileContentList = mutableListOf<MailContentPOJO>()

        brandManager.forEach { it ->


                data = emailService.Send_Mail_optima(response, uploadId)


                fileContentList.add(
                    MailContentPOJO(
                        fileName = "Blocked list",
                        String(Base64.getEncoder().encode(data))
                    )
                )
                val file = File("D:\\UNS_MAILS\\Blocked list.xlsx");

                val os = FileOutputStream(file)

                os.write(data)
                println(
                    "Successfully"
                            + " byte inserted"
                )
                os.close()

            val calendar = Calendar.getInstance()
            val mimeMessage = mailSender.createMimeMessage()
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
            mimeMessageHelper.setFrom("satendrayadav01567@gmail.com")
            mimeMessageHelper.setTo(it.email!!)
            mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
            mimeMessageHelper.setText("Hi, ",it.name +

                    "\n Following are the Names of FF/AM/RBM who have been blocked from receiving any further Medicine Samples since they have not Validated / Distributed 100% of samples received by them\n" +

                    "\nIf any of them have not validated or distributed for a valid reason,you have to mention the reason for Non Validation/Distribution in PromoBee using below link within next 7 days. \n" +
                    "This will enable the Medicine Samples dispatch in the current month post admin authorization\n" +

                    "\nKindly do the needful\n" +
                    "\nThank You.\n" +
                    " ")
            mimeMessageHelper.setSubject("Blocked list of FF and Remarks not submitted")

                val fileSystemResource =
                    FileSystemResource(File("D:\\UNS_MAILS\\Blocked list.xlsx"))
                mimeMessageHelper.addAttachment(fileSystemResource.filename, fileSystemResource)


            mailSender.send(mimeMessage)
            println("Mail Sent!")

        }

        return ResponseEntity(fileContentList, HttpStatus.OK)

    }














}