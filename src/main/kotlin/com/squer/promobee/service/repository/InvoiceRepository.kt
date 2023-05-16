package com.squer.promobee.service.repository


import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.squer.promobee.api.v1.enums.TeamEnum
import com.squer.promobee.controller.dto.*
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDate.of
import java.time.ZoneId
import java.util.*
import kotlin.math.roundToLong


@Suppress("UNREACHABLE_CODE")
@Repository
class InvoiceRepository(
    securityUtility: SecurityUtility
): BaseRepository<InvoiceHeader>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    fun getInvoiceHeaderById(id: String): InvoiceHeader {
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id", id)

        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getInvoiceHeaderById", data)

    }

    fun getDoctorById(id: String): Doctor {
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("id", id)

        return sqlSessionFactory.openSession().selectOne("DoctorMapper.getDoctorById", data)


    }

    fun getPrintInvoiceHeaders(inhId: String): InvoicePrintDetailsDTO {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("id", inhId)

        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getPrintInvoiceHeaders", data)


    }

    fun getVirtualPrintInvoiceHeaders(inhId: String): InvoicePrintDetailsDTO {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("id", inhId)

        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getVirtualPrintInvoiceHeaders", data)

    }


    fun getInvoiceDetailsForPrint(inhId: String): List<InvoiceDetailsPrintDTO> {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("InhID", inhId)

        return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.getPrintInvoiceDetails", data)

    }


    fun getHsnRate(hcmCode: String): HSN {
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("hcmCode", hcmCode)

        return sqlSessionFactory.openSession().selectOne("HSNMapper.getHsnRate", data)

    }


    fun printInvoice(inh: PrintInvoiceDTO): String {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
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

        var hoUser: Boolean = printDetails?.teamId?.equals(TeamEnum.DEFAULT_HO_TEAM.id) ?: true; false
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
        if (printDetails?.employeeRemark !== null) {
            context.put("EmployeeRemark", printDetails?.employeeRemark)
        } else {
            context.put("EmployeeRemark", "")
        }
        context.put("TotalSampleValue", printDetails?.employeeSampleValue)
        context.put("TotalInputValue", printDetails?.employeeInputValue)
        context.put("TotalSumValue", printDetails?.employeeValue?.roundToLong())
        var tableRow = ""
        var srNo = 1
        var value: Double? = 0.00



        printDetailsBody?.forEach {


//             if(it.invoiceDetailsBatchNo !== null){
//                  it.invoiceDetailsBatchNo
//             } else {
//                 ""
//             }

            var taxableValue = it.InvoiceDetailsRatePerUnit?.let { it1 -> it.invoiceDetailsQuantity?.times(it1) }
            var gstAmount = it.InvoiceDetailsGSTRate?.let { it1 -> taxableValue?.times(it1) }?.div(100)
            var amount = gstAmount?.let { it1 -> taxableValue?.plus(it1) }
            tableRow = tableRow + "<tr>" +
                    "<td>" + srNo++ + "</td>" + "\n" + "\t" +
                    "<td>" + it.invoiceDetailsProductCode + "</td>" + "\n" + "\t" +
                    "<td>" + it.invoiceDetailsHSNCode + "</td>" + "\n" + "\t" +
                    "<td>" + it.invoiceDetailsItemDescription + "</td>" + "\n" + "\t" +
                    "<td>" + it.invoiceDetailsQuantity?.toInt() + "</td>" + "\n" + "\t" +
                    "<td>" + it.invoiceDetailsSAPCode + "</td>" + "\n" + "\t" +
                    "<td>" + if (it.invoiceDetailsBatchNo !== null) {
                it.invoiceDetailsBatchNo
            } else {
                ""
            } + "</td>" + "\n" + "\t" +
                    "<td>" + it.invoiceDetailsExpiryDate + "</td>" + "\n" + "\t" +
                    "<td>" + it.InvoiceDetailsRatePerUnit + "</td>" + "\n" + "\t" +
                    "<td>" + taxableValue + "</td>" + "\n" + "\t" +
                    "<td>" + value + "</td>" + "\n" + "\t" +
                    "<td>" + value + "</td>" + "\n" + "\t" +
                    "<td>" + value + "</td>" + "\n" + "\t" +
                    "<td>" + value + "</td>" + "\n" + "\t" +
                    "<td>" + it.InvoiceDetailsGSTRate + "</td>" + "\n" + "\t" +
                    "<td>" + gstAmount + "</td>" + "\n" + "\t" +
                    "<td>" + amount + "</td>" + "\n" + "\t" +
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

//         val plainText = Jsoup.parse(writer.toString()).toString()


//         try {
//             //Create Document instance.
//             val document = Document()
//
//             //Create OutputStream instance.
//             val outputStream: OutputStream = FileOutputStream(File("D:\\TestFile.pdf"))
//
//
//
//             //Create PDFWriter instance.
//             PdfWriter.getInstance(document, outputStream)
//
//             //set A4 Size
//
//             document.pageSize = PageSize.A4
//
//             //Open the document.
//             document.open()
//
//             //Add content to the document.
//             document.add(
//                 Paragraph(
//                     "Hello world, " +
//                             "this is a test pdf file."
//                 )
//             )
//
////             var printFile : Any= "src/main/resources/htmlPrint/promoPrintInvoice.vm"
////
////             document.add(printFile)
//
//             //Close document and outputStream.
//             document.close()
//             outputStream.close()
//             println("Pdf created successfully.")
//         } catch (e: Exception) {
//             e.printStackTrace()
//         }


        try {

            val k = writer.toString()
//             var path = "D:\\InvoicePdf"
            var path = "D:\\InvoicePdf\\Test.pdf";

            val document = Document()
//             val file: OutputStream = FileOutputStream(File("C:\\InvoicePdf\\Test.pdf"))
            val file: OutputStream = FileOutputStream(File(path))

//             document.addAuthor("");
//             document.addCreationDate();
//             document.addProducer();
//             document.addCreator("aaa");
//             document.addTitle("");
//             document.setPageSize(PageSize.A4);

            PdfWriter.getInstance(document, file)
            document.open()

            val paragraph = Paragraph(k)

            document.add(paragraph)
//             val htmlWorker = HTMLWorker(document)
//
//             htmlWorker.parse(StringReader(k))
            HtmlConverter.convertToPdf(k, file)

            document.close()
            file.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return writer.toString();

    }


    fun searchInvoice(searchInvoice: SearchInvoiceDTO): List<InvoiceHeaderDTO> {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val date = Date()
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth


//        var fromDate = LocalDate.MIN
//        val fromDateFormatted = SimpleDateFormat("yyyy-MM-dd").format(fromDate)

        val sDate1 = "2016-01-01"
        val date1 = SimpleDateFormat("yyyy-MM-dd").parse(sDate1)
        val fromDateFormatted = SimpleDateFormat("yyyy-MM-dd").format(date1)


//        var toDate = Date(Long.MAX_VALUE)
//        val toDateFormatted = SimpleDateFormat("yyyy-MM-dd").format(toDate)

        val eDate1 = date
        val toDateFormatted = SimpleDateFormat("yyyy-MM-dd").format(eDate1)



        var fromDate = "";
        var toDate = "";

        if(searchInvoice.monthIndex == ""){

            if(searchInvoice.yearIndex == "") {

                 fromDate = fromDateFormatted
                 toDate = toDateFormatted
            } else {
                 fromDate = searchInvoice.yearIndex?.let { of(it?.toInt(),1, 1) }.toString()


                 toDate = searchInvoice.yearIndex?.let { of(it?.toInt(),12, 31) }.toString()


            }
        } else {
            fromDate = searchInvoice.yearIndex?.let { searchInvoice.monthIndex?.let { it1 ->
                of(it?.toInt(),
                    it1?.toInt(), 1)
            } }.toString()

            val sourceDate = fromDate
            val format = SimpleDateFormat("yyyy-MM-dd")
            val myDate = SimpleDateFormat("yyyy-MM-dd").parse(sourceDate)
            val myDate1 = SimpleDateFormat("yyyy-MM-dd").format(myDate)
            //var myDate = format.parse(sourceDate)
//            myDate = DateUtil.addDays(myDate, 1)
            val fmDate = LocalDate.parse(myDate1.toString())
            val fmDate1 = fmDate.plusMonths(1).plusDays(-1)

            toDate = fmDate1.toString()




        }

        var data: MutableMap<String, Any> = mutableMapOf()


         data.put("fromDate", fromDate)
        data.put("toDate", toDate )
        searchInvoice.recipientId?.let { data.put("recipientId", it) }
            searchInvoice.invoiceNo?.let { data.put("invoiceNo", it) }

        if (searchInvoice.recipientId == "" && searchInvoice.invoiceNo == null) {


            var invoices =
                return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.searchInvoiceCondition1", data)

            return invoices

        }
        if (searchInvoice.recipientId !== "") {

            if(searchInvoice.invoiceNo == null || searchInvoice.invoiceNo == 0){

                var invoices =
                    return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.searchInvoiceCondition2", data)

                return invoices


            } else {

                var invoices =
                    return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.searchInvoiceCondition3", data)

                return invoices

            }

        } else {

            var invoices =
                return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.searchInvoiceCondition4", data)

            return invoices


        }


    }




    fun getGroupInvoiceListHub(groupInvoice: GroupInvoiceParamDTO): List<GroupInvoicesListHubDTO> {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        groupInvoice.fromDate?.let { data.put("FromDate", it) }
        groupInvoice.toDate?.let { data.put("ToDate", it) }
        groupInvoice.invoiceNumber?.let { data.put("InvoiceNumber", it) }




            var groupInvoices =
                return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.getGroupInvoiceListHub", data)

            return groupInvoices

        }




    fun getInvoicesForGrouping(groupInvoice: GroupInvoiceParamDTO): List<InvoicesForGroupingDTO> {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        groupInvoice.fromDate?.let { data.put("FromDate", it) }
        groupInvoice.toDate?.let { data.put("ToDate", it) }
        groupInvoice.invoiceNumber?.let { data.put("InvoiceNumber", it) }




        var groupInvoices =
            return sqlSessionFactory.openSession().selectList("InvoiceHeaderMapper.getInvoicesForGrouping", data)

        return groupInvoices

    }



    fun printLabel(inh: PrintInvoiceDTO) {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        inh.inhId?.let { data.put("id", it) }
        inh.invoiceNo?.let { data.put("invoiceNo", it) }
//        var invoice = inh.inhId?.let { getInvoiceHeaderById(it) }
//        val date = Date()
//        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
//        val year = localDate.year
//        val month = localDate.monthValue
//        val day = localDate.dayOfMonth
//        var employeePeriod = localDate.month.toString() + "-" + year
//        var promoMonth = localDate.month.toString() + "-" + year
//        var printDetails = inh.inhId?.let { getPrintInvoiceHeaders(it) }
//        var printDetailsBody = inh.inhId?.let { getInvoiceDetailsForPrint(it) }
//        var hoUser: Boolean = printDetails?.teamId?.equals(TeamEnum.DEFAULT_HO_TEAM.id) ?: true; false
//        val ve = VelocityEngine()
//        ve.init()
//        val t: Template = ve.getTemplate("src/main/resources/htmlPrint/promoPrintInvoice.vm")
//        val context = VelocityContext()
//        context.put("InvoiceNumber", printDetails?.invoiceNumber)
//        context.put("EmployeeCode", printDetails?.employeeCode)
//        context.put("EmployeeDesignation", printDetails?.employeeDesignation)
//        context.put("EmployeeName", printDetails?.employeeName)
//        context.put("EmployeeAddress", printDetails?.employeeAddress)
//        context.put("EmployeeCity", printDetails?.employeeCity)
//        context.put("EmployeeState", printDetails?.employeeState)
//        context.put("EmployeePinCode", printDetails?.employeePinCode)
//        context.put("EmployeeMobileNumber", printDetails?.employeeMobileNumber)
//        context.put("EmployeePeriod", employeePeriod)
//        context.put("EmployeeLRNumber", printDetails?.employeeLRNumber)
//        context.put("EmployeeDate", printDetails?.employeeDate)
//        context.put("EmployeeLRDate", printDetails?.employeeLRDate)
//        context.put("EmployeeTeam", printDetails?.employeeTeam)
//        context.put("EmployeeTransport", printDetails?.employeeTransport)
//        context.put("EmployeeCFA", printDetails?.employeeCFA)
//        context.put("PROMOMONTH", promoMonth)
//        context.put("PLANTYPE", printDetails?.type)
//        context.put("EmployeeTotalNoOfCases", printDetails?.employeeTotalNoOfCases)
//        context.put("EmployeeTotalWeight", printDetails?.employeeTotalWeight)
//        if (printDetails?.employeeRemark !== null) {
//            context.put("EmployeeRemark", printDetails?.employeeRemark)
//        } else {
//            context.put("EmployeeRemark", "")
//        }
//        context.put("TotalSampleValue", printDetails?.employeeSampleValue)
//        context.put("TotalInputValue", printDetails?.employeeInputValue)
//        context.put("TotalSumValue", printDetails?.employeeValue?.roundToLong())
//        var tableRow = ""
//        var srNo = 1
//        var value: Double? = 0.00
//
//        printDetailsBody?.forEach {
//
//            var taxableValue = it.InvoiceDetailsRatePerUnit?.let { it1 -> it.invoiceDetailsQuantity?.times(it1) }
//            var gstAmount = it.InvoiceDetailsGSTRate?.let { it1 -> taxableValue?.times(it1) }?.div(100)
//            var amount = gstAmount?.let { it1 -> taxableValue?.plus(it1) }
//            tableRow = tableRow + "<tr>" +
//                    "<td>" + srNo++ + "</td>" + "\n" + "\t" +
//                    "<td>" + it.invoiceDetailsProductCode + "</td>" + "\n" + "\t" +
//                    "<td>" + it.invoiceDetailsHSNCode + "</td>" + "\n" + "\t" +
//                    "<td>" + it.invoiceDetailsItemDescription + "</td>" + "\n" + "\t" +
//                    "<td>" + it.invoiceDetailsQuantity?.toInt() + "</td>" + "\n" + "\t" +
//                    "<td>" + it.invoiceDetailsSAPCode + "</td>" + "\n" + "\t" +
//                    "<td>" + if (it.invoiceDetailsBatchNo !== null) {
//                it.invoiceDetailsBatchNo
//            } else {
//                ""
//            } + "</td>" + "\n" + "\t" +
//                    "<td>" + it.invoiceDetailsExpiryDate + "</td>" + "\n" + "\t" +
//                    "<td>" + it.InvoiceDetailsRatePerUnit + "</td>" + "\n" + "\t" +
//                    "<td>" + taxableValue + "</td>" + "\n" + "\t" +
//                    "<td>" + value + "</td>" + "\n" + "\t" +
//                    "<td>" + value + "</td>" + "\n" + "\t" +
//                    "<td>" + value + "</td>" + "\n" + "\t" +
//                    "<td>" + value + "</td>" + "\n" + "\t" +
//                    "<td>" + it.InvoiceDetailsGSTRate + "</td>" + "\n" + "\t" +
//                    "<td>" + gstAmount + "</td>" + "\n" + "\t" +
//                    "<td>" + amount + "</td>" + "\n" + "\t" +
//                    "</tr>"
//
//
//        }
//
//        context.put("tableRow", tableRow)
//        val writer = StringWriter()
//        t.merge(context, writer)
//        System.out.println(writer.toString())
//
//        try {
//
//            val k = writer.toString()
//            var path = "D:\\InvoicePdf\\Test.pdf";
//
//            val document = Document()
//            val file: OutputStream = FileOutputStream(File(path))
//            PdfWriter.getInstance(document, file)
//            document.open()
//
//            val paragraph = Paragraph(k)
//
//            document.add(paragraph)
//            HtmlConverter.convertToPdf(k, file)
//            document.close()
//            file.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

//        return writer.toString();

    }








}









