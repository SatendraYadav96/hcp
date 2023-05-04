package com.squer.promobee.service.repository


import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.squer.promobee.api.v1.enums.TeamEnum
import com.squer.promobee.controller.dto.InvoiceDetailsPrintDTO
import com.squer.promobee.controller.dto.InvoicePrintDetailsDTO
import com.squer.promobee.controller.dto.PrintInvoiceDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.*
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.io.*
import java.time.Year
import java.time.ZoneId
import java.util.*


@Suppress("UNREACHABLE_CODE")
@Repository
class InvoiceRepository(
    securityUtility: SecurityUtility
): BaseRepository<InvoiceHeader>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    fun getInvoiceHeaderById(id:String):InvoiceHeader{
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String , Any> = mutableMapOf()

        data.put("id",id)

        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getInvoiceHeaderById",data)

    }

    fun getDoctorById(id:String): Doctor {
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String , Any> = mutableMapOf()


        data.put("id",id)

        return  sqlSessionFactory.openSession().selectOne("DoctorMapper.getDoctorById",data)


    }

    fun getPrintInvoiceHeaders(inhId:String): InvoicePrintDetailsDTO {
       //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String , Any> = mutableMapOf()


        data.put("id",inhId)

        return  sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getPrintInvoiceHeaders",data)




    }

    fun getVirtualPrintInvoiceHeaders(inhId:String): InvoicePrintDetailsDTO {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String , Any> = mutableMapOf()


        data.put("id",inhId)

        return  sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getVirtualPrintInvoiceHeaders",data)

    }


    fun getInvoiceDetailsForPrint(inhId:String): List<InvoiceDetailsPrintDTO> {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String , Any> = mutableMapOf()


        data.put("InhID",inhId)

        return  sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.getPrintInvoiceDetails",data)

    }


    fun getHsnRate(hcmCode:String): HSN{
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String , Any> = mutableMapOf()

        data.put("hcmCode",hcmCode)

        return sqlSessionFactory.openSession().selectOne("HSNMapper.getHsnRate",data)

    }



     fun printInvoice(inh:PrintInvoiceDTO): String {
         val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
         var data: MutableMap<String, Any> = mutableMapOf()
         inh.inhId?.let { data.put("id", it) }
         inh.invoiceNo?.let { data.put("invoiceNo", it) }
         var invoice = inh.inhId?.let { getInvoiceHeaderById(it) }
         val date = Date()
         val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
         val year = localDate.year
         val month = localDate.monthValue
         val day = localDate.dayOfMonth
//         var employeePeriod = month.toString() + "-" + year
         var employeePeriod = localDate.month.toString() + "-" + year
//         var promoMonth = month.toString() + "-" + year
         var promoMonth = localDate.month.toString() + "-" + year
         var printDetails = inh.inhId?.let { getPrintInvoiceHeaders(it) }
        // var printDetailsDoc = inh.inhId?.let { getVirtualPrintInvoiceHeaders(it) }
         var printDetailsBody = inh.inhId?.let { getInvoiceDetailsForPrint(it) }
         var hoUser : Boolean = printDetails?.teamId?.equals(TeamEnum.DEFAULT_HO_TEAM.id) ?: true ; false
         /*  first, get and initialize an engine  */
         /*  first, get and initialize an engine  */
         val ve = VelocityEngine()
         ve.init()
         /*  next, get the Template  */
         /*  next, get the Template  */
         val t: Template = ve.getTemplate("src/main/resources/htmlPrint/promoPrintInvoice.vm")
         /*  create a context and add data */
         /*  create a context and add data */
         val context = VelocityContext()
         context.put("InvoiceNumber", printDetails?.invoiceNumber)
         context.put("EmployeeCode", printDetails?.employeeCode)
         context.put("EmployeeDesignation", printDetails?.employeeDesignation)
         context.put("EmployeeName", printDetails?.employeeName)
         context.put("EmployeeAddress", printDetails?.employeeAddress)
         context.put("EmployeeCity", printDetails?.employeeCity)
         context.put("EmployeeState", printDetails?.employeeState)
         context.put("EmployeePinCode", printDetails?.employeePinCode)
         context.put("EmployeeMobileNumber", printDetails?.employeeMobileNumber)
         context.put("EmployeePeriod", employeePeriod)
         context.put("EmployeeLRNumber", printDetails?.employeeLRNumber)
         context.put("EmployeeDate", printDetails?.employeeDate)
         context.put("EmployeeLRDate", printDetails?.employeeLRDate)
         context.put("EmployeeTeam", printDetails?.employeeTeam)
         context.put("EmployeeTransport", printDetails?.employeeTransport)
         context.put("EmployeeCFA", printDetails?.employeeCFA)
         context.put("PROMOMONTH", promoMonth)
         context.put("PLANTYPE", printDetails?.type)
         context.put("EmployeeTotalNoOfCases", printDetails?.employeeTotalNoOfCases)
         context.put("EmployeeTotalWeight", printDetails?.employeeTotalWeight)
         if(printDetails?.employeeRemark !== null) {
             context.put("EmployeeRemark", printDetails?.employeeRemark)
         } else {
             context.put("EmployeeRemark", "")
         }
         context.put("TotalSampleValue", printDetails?.employeeSampleValue)
         context.put("TotalInputValue", printDetails?.employeeInputValue)
         context.put("TotalSumValue", printDetails?.employeeValue)
         var tableRow = ""
         var srNo = 1



         printDetailsBody?.forEach {
             tableRow =  tableRow + "<tr>"+
                     "<td>" + srNo++  + "</td>"+
             "<td>" + it.invoiceDetailsProductCode + "</td>"+
             "<td>" + it.invoiceDetailsHSNCode + "</td>"+
             "<td>" + it.invoiceDetailsItemDescription + "</td>"+
             "<td>" + it.invoiceDetailsQuantity?.toInt() + "</td>"+
             "<td>" + it.invoiceDetailsSAPCode + "</td>"+
             "<td>" + it.invoiceDetailsBatchNo + "</td>"+
             "<td>" + it.invoiceDetailsExpiryDate + "</td>"+
             "<td>" + it.InvoiceDetailsRatePerUnit + "</td>"+
             "<td>" + it.invoiceItemValue + "</td>"+
                     "</tr>"
         }

         context.put("tableRow", tableRow)







         /* now render the template into a StringWriter */
         /* now render the template into a StringWriter */
         val writer = StringWriter()
         t.merge(context, writer)
         /* show the World */
         /* show the World */
         System.out.println(writer.toString())

         try {
             //Create Document instance.
             val document = Document()

             //Create OutputStream instance.
             val outputStream: OutputStream = FileOutputStream(File("D:\\TestFile.pdf"))

             //Create PDFWriter instance.
             PdfWriter.getInstance(document, outputStream)

             //set A4 Size

             document.pageSize = PageSize.A4

             //Open the document.
             document.open()

             //Add content to the document.
             document.add(
                 Paragraph(
                     "Hello world, " +
                             "this is a test pdf file."
                 )
             )

//             var printFile : Any= "src/main/resources/htmlPrint/promoPrintInvoice.vm"
//
//             document.add(printFile)

             //Close document and outputStream.
             document.close()
             outputStream.close()
             println("Pdf created successfully.")
         } catch (e: Exception) {
             e.printStackTrace()
         }

         return writer.toString();












     }




}









