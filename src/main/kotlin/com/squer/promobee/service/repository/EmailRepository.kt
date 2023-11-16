package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.UserRoleEnum
import com.squer.promobee.controller.dto.BlockedForBUCModel
import com.squer.promobee.controller.dto.ComplianceSampleInputNearExpiryDTO
import com.squer.promobee.controller.dto.ItemExpireModel
import com.squer.promobee.controller.dto.UserEmailSendDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.domain.enum.UserStatusEnum
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.*
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletResponse



@Repository
class EmailRepository(
    securityUtility: SecurityUtility,
    private val mailSender: JavaMailSender

): BaseRepository<HSN>(
    securityUtility = securityUtility





) {

    private val scheduler = Executors.newSingleThreadScheduledExecutor()



    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory




    fun getConsolidateExpiryReport(response: HttpServletResponse, index1: Int, index2: Int): ByteArray {

        ///listItem.put("filter",count)


        var data1 = mutableListOf<ItemExpireModel>()
        var bytes = byteArrayOf()

        var listItem: MutableMap<String, Any> = mutableMapOf()
        listItem.put("fromdate", index1)
        listItem.put("Todate", index2)


        data1 = sqlSessionFactory.openSession()
            .selectList<ItemExpireModel>("ReportMapper.getConsolidateExpiryReport", listItem)
        val xlwb = XSSFWorkbook()
        val xlws = xlwb.createSheet("ExpiryReportIn${index1}-${index2}days")
        var row = xlws.createRow(0)
        row.createCell(0).setCellValue("ITEM_NAME")
        row.createCell(1).setCellValue("ITEM_CODE")
        row.createCell(2).setCellValue("Batch_No")
        row.createCell(3).setCellValue("Category")
        row.createCell(4).setCellValue("EXPIRY_DATE")
        row.createCell(5).setCellValue("STOCK")
        row.createCell(6).setCellValue("VALUE")
        var rowCount = 1

        //write

        data1.forEach {
            var columnCount = 0
            var row = xlws.createRow(rowCount++)
            row.createCell(columnCount++).setCellValue("${it.name_ITM}")
            row.createCell(columnCount++).setCellValue("${it.code_ITM}")
            row.createCell(columnCount++).setCellValue("${it.batch_NO}")
            row.createCell(columnCount++).setCellValue("${it.category}")
            row.createCell(columnCount++).setCellValue("${it.expiry_date_inv}")
            row.createCell(columnCount++).setCellValue("${it.stock}")
            row.createCell(columnCount).setCellValue("${it.value}")

        }


        val bos = ByteArrayOutputStream()
        try {
            xlwb.write(bos)
        } finally {
            bos.close()
        }
        bytes = bos.toByteArray()



        return bytes

    }


    // BRAND MANAGER ITEM MAIL


    fun SendTestMailForItemExpiry(response: HttpServletResponse, index1: Int, index2: Int): ByteArray {

        ///listItem.put("filter",count)

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("designationId", UserRoleEnum.PRODUCT_MANAGER_ID.id)
        data0.put("statusId", UserStatusEnum.ACTIVE.id)


        var brandManager = sqlSessionFactory.openSession().selectList<User>("UserMapper.emailer", data0)

        var data1 = mutableListOf<ItemExpireModel>()
        var bytes = byteArrayOf()
        val bos = ByteArrayOutputStream()

        brandManager.forEach { it ->


            var listItem: MutableMap<String, Any> = mutableMapOf()

            listItem.put("itemID", it.id)
            listItem.put("fromdate", index1)
            listItem.put("Todate", index2)



            data1 = sqlSessionFactory.openSession()
                .selectList<ItemExpireModel>("ReportMapper.SendTestMailForItemExpiry", listItem)


            val xlwb = XSSFWorkbook()
            val xlws = xlwb.createSheet("ExpiryReportIn${index1}-${index2}days")
            var row = xlws.createRow(0)
            row.createCell(0).setCellValue("ITEM_NAME")
            row.createCell(1).setCellValue("ITEM_CODE")
            // row.createCell(2).setCellValue("Batch_No")
            // row.createCell(3).setCellValue("Category")
            row.createCell(4).setCellValue("EXPIRY_DATE")
            row.createCell(5).setCellValue("STOCK")
            row.createCell(6).setCellValue("VALUE")
            var rowCount = 1

            //write

            data1.forEach {
                var columnCount = 0
                var row = xlws.createRow(rowCount++)
                row.createCell(columnCount++).setCellValue("${it.name_ITM}")
                row.createCell(columnCount++).setCellValue("${it.code_ITM}")
                //row.createCell(columnCount++).setCellValue("${it.batch_NO}")
                //row.createCell(columnCount++).setCellValue("${it.category}")
                row.createCell(columnCount++).setCellValue("${it.expiry_date_inv}")
                row.createCell(columnCount++).setCellValue("${it.stock}")
                row.createCell(columnCount).setCellValue("${it.value}")

            }



            try {
                xlwb.write(bos)
            } finally {
                bos.close()
            }

        }

        bytes = bos.toByteArray()



        return bytes

    }


    fun SendTestMailForSampleExpiry(response: HttpServletResponse, index1: Int, index2: Int): ByteArray {

        ///listItem.put("filter",count)

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("designationId", UserRoleEnum.PRODUCT_MANAGER_ID.id)
        data0.put("statusId", UserStatusEnum.ACTIVE.id)


        var brandManager = sqlSessionFactory.openSession().selectList<User>("UserMapper.emailer", data0)

        var data1 = mutableListOf<ItemExpireModel>()
        var bytes = byteArrayOf()
        val bos = ByteArrayOutputStream()

        brandManager.forEach { it ->


            var listItem: MutableMap<String, Any> = mutableMapOf()

            listItem.put("itemID", it.id)
            listItem.put("fromdate", index1)
            listItem.put("Todate", index2)




            data1 = sqlSessionFactory.openSession()
                .selectList<ItemExpireModel>("ReportMapper.GetunblockRequestforBUCdetails", listItem)


            val xlwb = XSSFWorkbook()
            val xlws = xlwb.createSheet("ExpiryReportIn${index1}-${index2}days")
            var row = xlws.createRow(0)
            row.createCell(0).setCellValue("ITEM_NAME")
            row.createCell(1).setCellValue("ITEM_CODE")
            // row.createCell(2).setCellValue("Batch_No")
            // row.createCell(3).setCellValue("Category")
            row.createCell(4).setCellValue("EXPIRY_DATE")
            row.createCell(5).setCellValue("STOCK")
            row.createCell(6).setCellValue("VALUE")
            var rowCount = 1

            //write

            data1.forEach {
                var columnCount = 0
                var row = xlws.createRow(rowCount++)
                row.createCell(columnCount++).setCellValue("${it.name_ITM}")
                row.createCell(columnCount++).setCellValue("${it.code_ITM}")
                //row.createCell(columnCount++).setCellValue("${it.batch_NO}")
                //row.createCell(columnCount++).setCellValue("${it.category}")
                row.createCell(columnCount++).setCellValue("${it.expiry_date_inv}")
                row.createCell(columnCount++).setCellValue("${it.stock}")
                row.createCell(columnCount).setCellValue("${it.value}")

            }



            try {
                xlwb.write(bos)
            } finally {
                bos.close()
            }

        }

        bytes = bos.toByteArray()



        return bytes

    }


    // BU CHAMP COMPLIANCE MAIL


    fun Send_Mail_optima( uploadId: String , response: HttpServletResponse): ByteArray {


        var brandManager = sqlSessionFactory.openSession().selectList<UserEmailSendDTO>("ReportMapper.GetBuChampionForCompliance")

        var data1 = mutableListOf<BlockedForBUCModel>()
        var bytes = byteArrayOf()
        val bos = ByteArrayOutputStream()


        brandManager.forEach { it ->


            var listItem: MutableMap<String, Any> = mutableMapOf()


            data1 = sqlSessionFactory.openSession().selectList<BlockedForBUCModel>("ReportMapper.GetunblockRequestforBUCdetails")


            val xlwb = XSSFWorkbook()
            val xlws = xlwb.createSheet("Blocked list")
            var row = xlws.createRow(0)
            row.createCell(0).setCellValue("EmployeeCode")
            row.createCell(1).setCellValue("EmployeeName")
            row.createCell(2).setCellValue("Month")
            row.createCell(3).setCellValue("Year")
            row.createCell(4).setCellValue("Designation")
            var rowCount = 1

            //write

            data1.forEach {
                var columnCount = 0
                var row = xlws.createRow(rowCount++)
                row.createCell(columnCount++).setCellValue("${it.employeeCode}")
                row.createCell(columnCount++).setCellValue("${it.employeeName}")
                row.createCell(columnCount++).setCellValue("${it.month}")
                row.createCell(columnCount++).setCellValue("${it.year}")
                row.createCell(columnCount).setCellValue("${it.designation}")

            }



            try {
                xlwb.write(bos)
            } finally {
                bos.close()
            }

        }


        bytes = bos.toByteArray()



        return bytes

    }


    fun SpecialDraftPlanReminder() {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        fun startSpecialDraftReversalEmail() {
            scheduler.scheduleAtFixedRate(
                { SpecialDraftPlanReminder() },
                0,
                1,
                TimeUnit.DAYS
            )
        }

        fun startSpecialMail(){
            startSpecialDraftReversalEmail()
        }

        val currentDate = LocalDate.now()
        val currentMonth = currentDate.monthValue - 1
        val currentYear = currentDate.year - 1
       // var twentyFifthDay = currentDate.withDayOfMonth(25)

        val twentyThirdDay = currentDate.withDayOfMonth(23)

        val twentyEightDay = currentDate.withDayOfMonth(28)

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("month",currentMonth)
        data.put("year",currentYear)

        var plan = sqlSessionFactory.openSession().selectList<DispatchPlan>("DispatchPlanMapper.SpecialDraftPlanReminder",data)

        plan.forEach{
            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("userId",it.owner!!.id)

            var brandManager = sqlSessionFactory.openSession().selectOne<User>("UserMapper.SpecialDraftPlanReminder",data)


            if(currentDate == twentyThirdDay){

                val calendar = Calendar.getInstance()
                val mimeMessage = mailSender.createMimeMessage()
                val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
                mimeMessageHelper.setFrom("satendrayadav01567@gmail.com")
                mimeMessageHelper.setTo(brandManager.email!!)
                mimeMessageHelper.setCc("satendra.yadav@squer.co.in")
                mimeMessageHelper.setText(
                    """
        Hi, ${brandManager.name!!.trim()},

        Please note you have not submitted your special dispatch for month ${it.month} year ${it.year}. Remarks: ${it.remarks!!.trim()}

        If you don't submit your special plan, the plan will get deleted after 5 days and allocated quantity will automatically get reversed in inventory.

        Kindly do the needful.

        Thank You.
    """.trim()
                )
                mimeMessageHelper.setSubject("Special Plan Nullify")


                mailSender.send(mimeMessage)
                println("Mail Sent!")
            }

            if(currentDate == twentyEightDay){
                plan.forEach {
                    var data: MutableMap<String, Any> = mutableMapOf()
                    data.put("planId",it.id)

                    var dispatchDetail = sqlSessionFactory.openSession().selectList<DispatchDetail>("DispatchDetailMapper.SpecialDraftPlanReminder",data)

                    dispatchDetail.forEach {
                        var data1: MutableMap<String, Any> = mutableMapOf()

                        var allocatedQty = it.qtyDispatch

                        data1.put("invId",it.inventoryId!!)

                        var inv = sqlSessionFactory.openSession().selectOne<Inventory>("InventoryMapper.SpecialDraftPlanReminder",data1)


                        var data2: MutableMap<String, Any> = mutableMapOf()

                        var invQty = inv.qtyAllocated?.minus(allocatedQty!!)

                        data2.put("invId",inv.id)
                        data2.put("qty",invQty!!)
                        data2.put("updatedBy",user.id)

                        sqlSessionFactory.openSession().update("InventoryMapper.SpecialDraftPlanReminderInventory",data2)

                        var data3: MutableMap<String, Any> = mutableMapOf()

                        data3.put("planId",it.planId!!.id)

                        sqlSessionFactory.openSession().delete("DispatchDetailMapper.SpecialDraftPlanReminderDelete",data3)

                    }
                    var data4: MutableMap<String, Any> = mutableMapOf()

                    data4.put("planId",it.id)

                    sqlSessionFactory.openSession().delete("DispatchPlanMapper.SpecialDraftPlanReminderDelete",data4)

                }

            }





        }



    }



    // Mail trigger to FF for Sample/Input Near expiry


    fun SendMailFFSampleInputNearExpiry(uploadId: String):ByteArray {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("uploadId",uploadId)

        var recipient = sqlSessionFactory.openSession().selectList<Recipient>("RecipientMapper.SendMailFFSampleInputNearExpiry",data)

        var bytes = byteArrayOf()
        val bos = ByteArrayOutputStream()


        recipient.forEach { it ->


            var listItem: MutableMap<String, Any> = mutableMapOf()

            listItem.put("uploadId",uploadId)

          var data1 = sqlSessionFactory.openSession()
                .selectList<ComplianceSampleInputNearExpiryDTO>("ReportMapper.SendMailFFSampleInputNearExpiry",listItem)

            var currentDate = LocalDate.now()

            var oneEightyDays = currentDate.plusDays(180)



            var productData = data1.filter {it.expiryDate!! < oneEightyDays.toString() || it.expiryDate!! > currentDate.toString() }




//                val xlwb = XSSFWorkbook()
//                val xlws = xlwb.createSheet("Near Expiry Product")

               val xlwb = XSSFWorkbook()
               val xlws = xlwb.createSheet("Near Expiry Product")

            var row = xlws.createRow(0)
                row.createCell(0).setCellValue("EmployeeCode")
                row.createCell(1).setCellValue("EmployeeName")
                row.createCell(2).setCellValue("ProductName")
                row.createCell(3).setCellValue("ProductCode")
                row.createCell(4).setCellValue("BatchNo")
                row.createCell(5).setCellValue("BalancedQty")
                row.createCell(6).setCellValue("Expiry")
                var rowCount = 1

                //write

            productData.forEach {
                    var columnCount = 0
                    var row = xlws.createRow(rowCount++)
                row.createCell(columnCount++).setCellValue("${it.empCode}")
                row.createCell(columnCount++).setCellValue("${it.empName}")
                row.createCell(columnCount++).setCellValue("${it.productName}")
                row.createCell(columnCount++).setCellValue("${it.productCode}")
                row.createCell(columnCount).setCellValue("${it.batchNo}")
                row.createCell(columnCount).setCellValue("${it.qtyBalanced}")
                row.createCell(columnCount).setCellValue("${it.expiryDate}")


                try {
                    xlwb.write(bos)
                } finally {
                    bos.close()
                }


            }


        }
        bytes = bos.toByteArray()



        return bytes



        }






    // Mail trigger to FF for Expired Sample/Input




    fun SendMailFFSampleInputExpired(uploadId: String):ByteArray {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("uploadId",uploadId)

        var recipient = sqlSessionFactory.openSession().selectList<Recipient>("RecipientMapper.SendMailFFSampleInputNearExpiry",data)

        var bytes = byteArrayOf()
        val bos = ByteArrayOutputStream()


        recipient.forEach { it ->


            var listItem: MutableMap<String, Any> = mutableMapOf()

            listItem.put("uploadId",uploadId)

            var data1 = sqlSessionFactory.openSession()
                .selectList<ComplianceSampleInputNearExpiryDTO>("ReportMapper.SendMailFFSampleInputNearExpiry",listItem)

            var currentDate = LocalDate.now()

            var oneEightyDays = currentDate.plusDays(180)



            var productData = data1.filter {it.expiryDate!! > currentDate.toString() }





            val xlwb = XSSFWorkbook()
            val xlws = xlwb.createSheet("Expired Products")
            var row = xlws.createRow(0)
                row.createCell(0).setCellValue("EmployeeCode")
                row.createCell(1).setCellValue("EmployeeName")
                row.createCell(2).setCellValue("ProductName")
                row.createCell(3).setCellValue("ProductCode")
                row.createCell(4).setCellValue("BatchNo")
                row.createCell(5).setCellValue("BalancedQty")
                row.createCell(6).setCellValue("Expiry")
                var rowCount = 1

                //write

            productData.forEach {
                var columnCount = 0
                var row = xlws.createRow(rowCount++)
                row.createCell(columnCount++).setCellValue("${it.empCode}")
                row.createCell(columnCount++).setCellValue("${it.empName}")
                row.createCell(columnCount++).setCellValue("${it.productName}")
                row.createCell(columnCount++).setCellValue("${it.productCode}")
                row.createCell(columnCount).setCellValue("${it.batchNo}")
                row.createCell(columnCount).setCellValue("${it.qtyBalanced}")
                row.createCell(columnCount).setCellValue("${it.expiryDate}")


                try {
                    xlwb.write(bos)
                } finally {
                    bos.close()
                }


            }


        }
        bytes = bos.toByteArray()



        return bytes



    }










    }


















