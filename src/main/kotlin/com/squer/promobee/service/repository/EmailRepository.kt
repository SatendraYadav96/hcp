package com.squer.promobee.service.repository

import com.squer.promobee.api.v1.enums.UserRoleEnum
import com.squer.promobee.controller.dto.BlockedForBUCModel
import com.squer.promobee.controller.dto.ItemExpireModel
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.domain.enum.UserStatusEnum
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.HSN
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.io.ByteArrayOutputStream
import javax.servlet.http.HttpServletResponse

@Repository
class EmailRepository(
    securityUtility: SecurityUtility
): BaseRepository<HSN>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    fun getConsolidateExpiryReport(response: HttpServletResponse, index1:Int, index2:Int):ByteArray
    {

        ///listItem.put("filter",count)



        var data1 = mutableListOf<ItemExpireModel>()
        var bytes = byteArrayOf()

        var listItem: MutableMap<String, Any> = mutableMapOf()
        listItem.put("fromdate", index1)
        listItem.put("Todate", index2)


        data1= sqlSessionFactory.openSession().selectList<ItemExpireModel>("ReportMapper.getConsolidateExpiryReport", listItem)
        val xlwb = XSSFWorkbook()
        val xlws = xlwb.createSheet("ExpiryReportIn${index1}-${index2}days")
        var row= xlws.createRow(0)
        row.createCell(0).setCellValue("ITEM_NAME")
        row.createCell(1).setCellValue("ITEM_CODE")
        row.createCell(2).setCellValue("Batch_No")
        row.createCell(3).setCellValue("Category")
        row.createCell(4).setCellValue("EXPIRY_DATE")
        row.createCell(5).setCellValue("STOCK")
        row.createCell(6).setCellValue("VALUE")
        var rowCount= 1

        //write

        data1.forEach {
            var columnCount = 0
            var row= xlws.createRow(rowCount++)
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


    fun SendTestMailForItemExpiry(response: HttpServletResponse, index1:Int, index2:Int):ByteArray
    {

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





    fun SendTestMailForSampleExpiry(response: HttpServletResponse, index1:Int, index2:Int):ByteArray
    {

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


    fun Send_Mail_optima(response: HttpServletResponse, uploadId:String):ByteArray
    {


        var brandManager = sqlSessionFactory.openSession().selectList<User>("ReportMapper.GetBuChampionForCompliance")

        var data1 = mutableListOf<BlockedForBUCModel>()
        var bytes = byteArrayOf()
        val bos = ByteArrayOutputStream()


        brandManager.forEach {  it ->


            var listItem: MutableMap<String, Any> = mutableMapOf()


            data1 = sqlSessionFactory.openSession()
                .selectList<BlockedForBUCModel>("ReportMapper.GetunblockRequestforBUCdetails")


            val xlwb = XSSFWorkbook()
            val xlws = xlwb.createSheet("Blocked list")
            var row = xlws.createRow(0)
            row.createCell(0).setCellValue("EmployeeCode")
            row.createCell(1).setCellValue("EmployeeName")
            row.createCell(4).setCellValue("Month")
            row.createCell(5).setCellValue("Year")
            row.createCell(6).setCellValue("Designation")
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






}