package com.squer.promobee.service.repository



import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.squer.promobee.api.v1.enums.*
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





@Repository
class InvoiceRepository(
    securityUtility: SecurityUtility
): BaseRepository<InvoiceHeader>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    @Autowired
    lateinit var inventoryRepository : InventoryRepository


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

        val sDate1 = "2i16-i1-i1"
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



    fun printLabel(inh: PrintInvoiceDTO): String {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()
        inh.inhId?.let { data.put("id", it) }
        inh.invoiceNo?.let {
            data.put("invoiceNo", it)}

            var labelDetails = sqlSessionFactory.openSession()
                .selectList<LabelPrintDetailsDTO>("InvoiceHeaderMapper.getLabelPrintDetailsByInvoices", data)




        /*  first, get and initialize an engine  */
        /*  first, get and initialize an engine  */
        val ve = VelocityEngine()
        ve.init()
        /*  next, get the Template  */
        /*  next, get the Template  */
        val t: Template = ve.getTemplate("src/main/resources/htmlPrint/promoPrintLabel.vm")
        /*  create a context and add data */
        /*  create a context and add data */
        val context = VelocityContext()

        labelDetails.forEach {
            var i = 0;
            var length =  "";
            var breadth =  "";
            var height =  "";
            context.put("TransporterName", labelDetails[i].transporterName)
            context.put("InvoiceLRNo", labelDetails[i].lRNumber)
            context.put("InvoiceCreatedDate", labelDetails[i].invoiceDate)
            context.put("InvoiceNo", labelDetails[i].invoiceNo)
            context.put("RecipientCode", labelDetails[i].recipientCode)
            context.put("RecipientName", labelDetails[i].recipientName)
            context.put("RecipientTeam", labelDetails[i].teamName)
            context.put("RecipientDesgination", labelDetails[i].recipientDesgination)
            context.put("RecipientAddress", labelDetails[i].recipientAddress)
            context.put("RecipientCity", labelDetails[i].recipientCity)
            context.put("RecipientState", labelDetails[i].recipientState)
            context.put("RecipientPhone", labelDetails[i].recipientPhone)
            context.put("RecipientPinCode", labelDetails[i].recipientPinCode)
            context.put("RecipientHeadQuarter", labelDetails[i].recipientHeadQuarter)
            context.put("InvoiceBoxes", labelDetails[i].noOfBoxes)
            context.put("InvoiceWeight", labelDetails[i].weight)

            if(labelDetails[i].dimension !== null){
                context.put("length",length)
                context.put("breadth",breadth)
                context.put("height",height)
            }else{
                context.put("length",length)
                context.put("breadth",breadth)
                context.put("height",height)
            }

        }


        val writer = StringWriter()
        t.merge(context, writer)

        System.out.println(writer.toString())

        try {

            val k = writer.toString()

            var path = "D:\\LabelPdf\\Label.pdf";

            val document = Document()
            //val outputStream = ByteArrayOutputStream(k.toInt())
            val file: OutputStream = FileOutputStream(File(path))

           PdfWriter.getInstance(document, file)
            //PdfWriter.getInstance(document, outputStream)

            document.open()

            val paragraph = Paragraph(k)

            document.add(paragraph)

        HtmlConverter.convertToPdf(k, file)


            document.close()
            file.close()
            //outputStream.close();
            //return outputStream.toByteArray();
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return writer.toString()



//        val json = JSONObject(writer)
//        System.out.println(json.toString());
//
//        return json.toJSONString()






    }



//    private fun JSONObject(writer: StringWriter): JSONObject {
//
//         return JSONObject()
//    }


    fun  getRecipientToGenerateInvoice(recipientId: String):Recipient{
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("recipientId",recipientId)

        return sqlSessionFactory.openSession().selectOne("RecipientMapper.getRecipient",data)




    }



    fun  getRecipientItemCategoryCount(month: Int, year: Int, recipientId: String):ItemCategoryCountDTO{
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User




        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("month",month)
        data.put("year",year)
        data.put("recipientId",recipientId)

        var samplesCount  =  return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getSamplesCount",data)

        var data0: MutableMap<String, Any> = mutableMapOf()
        data0.put("month",month)
        data0.put("year",year)
        data0.put("recipientId",recipientId)

        var inputCount = return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getInputCount",data0)




        var itcCount = ItemCategoryCountDTO()
        itcCount.sampleItems = samplesCount
//        if(itcCount.sampleItems == null){
//            itcCount.sampleItems = 0.0
//        }
        itcCount.nonSampleItems = inputCount
//        if(itcCount.nonSampleItems == null){
//            itcCount.nonSampleItems = 0.0
//        }

        return itcCount




    }



    fun  getDispatchDetailsForInvoicing(month: Int, year: Int, recipientId: String):List<DispatchDetailDTO>{
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User




        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("month",month)
        data.put("year",year)
        data.put("recipientId",recipientId)

        return sqlSessionFactory.openSession().selectList("DispatchDetailMapper.getDispatchDetails",data)




    }


    fun getItemMasterById(id: String):Item {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("id",id)

        return sqlSessionFactory.openSession().selectOne("ItemMapper.getItemMasterById",data)

    }

    fun getSampleMasterById(id: String):SampleMaster {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("id",id)

        return sqlSessionFactory.openSession().selectOne("SampleMasterMapper.getSampleMasterById",data)

    }


    fun getDispatchPlanById(id: String):DispatchPlan {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("id",id)

         return sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanById",data)

    }



    fun  generateInvoice(genInv : GenerateInvoiceDTO){
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        genInv.recipientId?.let { data.put("recipientId", it) }
        genInv.boxes?.let { data.put("boxes", it) }
        genInv.weight?.let { data.put("weight", it) }
        genInv.transporter?.let { data.put("transporter", it) }
        genInv.lrNo?.let { data.put("lrNo", it) }
        genInv.dimension?.let { data.put("dimension", it) }
        genInv.month?.let { data.put("month", it) }
        genInv.year?.let { data.put("year", it) }
        genInv.isSpecial?.let { data.put("isSpecial", it) }

        var recipient = genInv.recipientId?.let { getRecipientToGenerateInvoice(it) }

        var itcCount = genInv.month?.let { genInv.year?.let { it1 ->
            genInv.recipientId?.let { it2 ->
                getRecipientItemCategoryCount(it,
                    it1, it2
                )
            }
        } }


        // INVOICE HEADER INSERT

        var inh = InvoiceHeader()

        var inhId = UUID.randomUUID().toString()

        var data1: MutableMap<String, Any> = mutableMapOf()

        data1.put("id",inhId)
        inh.invoiceNo?.let { data1.put("invoiceNo", it) }
        data1.put("type",InvoiceTypeEnum.DISPATCHED.id)
        data1.put("statusId",InvoiceStatusEnum.GENERATED_PRINTED.id)
        recipient?.team?.let { data1.put("teamId", it.id) }
        recipient?.let { data1.put("recipientId", it.id) }
        recipient?.address?.let { data1.put("addressLine1", it) }
        recipient?.address?.let { data1.put("addressLine2", it) }
        recipient?.state?.let { data1.put("states", it) }
        recipient?.city?.let { data1.put("city", it) }
        recipient?.zip?.let { data1.put("zip", it) }
        recipient?.mobile?.let { data1.put("phone", it) }
        genInv.weight?.let { data1.put("weight", it) }
        genInv.boxes?.let { data1.put("noOfBoxes", it) }
        genInv.transporter?.let { data1.put("transporterId", it) }
        //sample value
        if(itcCount?.sampleItems !== null){
            itcCount?.sampleItems?.let { data1.put("sampleValue", it) }
        }else {
            data1.put("sampleValue",0)
        }

        // item value
        if(itcCount?.nonSampleItems !== null){
            itcCount?.nonSampleItems?.let { data1.put("otherItemValue", it) }
        }else {
            data1.put("otherItemValue",0)
        }

        genInv.lrNo?.let { data1.put("lrNumber", it) }
        data1.put("createdBy", user.id)
        data1.put("updatedBy", user.id)
        recipient?.designation?.let { data1.put("designationId", it.id) }
        recipient?.cfa?.let { data1.put("cfa", it) }

        sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertGenerateInvoiceHeader",data1)


        var dispatchDetails = genInv.month?.let { genInv.year?.let { it1 ->
            genInv.recipientId?.let { it2 ->
                getDispatchDetailsForInvoicing(it,
                    it1, it2
                )
            }
        } }

        var i = 0
        var inventory: List<Inventory> = ArrayList()
        dispatchDetails?.forEach {

            i < dispatchDetails!!.count()
             dispatchDetails = genInv.month?.let { genInv.year?.let { it1 ->
                genInv.recipientId?.let { it2 ->
                    getDispatchDetailsForInvoicing(it,
                        it1, it2
                    )
                }
            } }






            listOf(dispatchDetails?.get(i)?.inventoryId?.let { it1 -> inventoryRepository.getInventoryById(it1) })!!.also { inventory =
                it as List<Inventory>
            }


            var samples = ItemCategoryEnum.SAMPLES
            var itemId = ""

            if(inventory[i].categoryId!!.equals(samples)){

                var smp = inventory[i].item?.let { it1 -> getSampleMasterById(it1.id) }
                itemId = smp.toString()


            }else {
                var itm = inventory[i].item?.let { it1 -> getItemMasterById(it1.id) }

                itemId = itm.toString()
            }

            // INVOICE DETAIL IND


            var data2  : MutableMap<String, Any> = mutableMapOf()

            var ind = InvoiceDetail()

            var indId = UUID.randomUUID().toString()

            var value = inventory[i].ratePerUnit?.let { it1 -> dispatchDetails?.get(i)!!.qtyDispatch!!.times(it1) }

            data2.put("id",indId)
            data2.put("headerId",inhId)
            inventory[i].item?.let { it1 -> data2.put("item", it1.id) }
            dispatchDetails?.get(i)?.qtyDispatch?.let { it1 -> data2.put("quantity", it1) }
            dispatchDetails!![0]?.let { it1 -> it1.id?.let { it2 -> data2.put("didId", it2) } }
            value?.let { it1 -> data2.put("value", it1.toDouble()) }
            data2.put("createdBy",user.id)
            data2.put("updatedBy",user.id)
            data2.put("inventoryId",inventory[i].id)
            inventory[i].hsnCode?.let { it1 -> data2.put("hsnCode", it1) }
            inventory[i].rate?.let { it1 -> data2.put("rate", it1) }

            sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertGenerateInvoiceDetail",data2)



            // DISPATCH DETAIL UPDATE


            var data3: MutableMap<String, Any> = mutableMapOf()

            data3.put("detailStatus",DispatchDetailStatusEnum.INVOICED.id)
            dispatchDetails?.get(i)?.recipientId?.let { it1 -> data3.put("recipientId", it1) }
            data3.put("updatedBy",user.id)

              sqlSessionFactory.openSession().update("DispatchDetailMapper.editDispatchDetailsForInvoicing",data3)


            var data4: MutableMap<String, Any> = mutableMapOf()

            var qtyDisp = dispatchDetails?.get(i)?.let { it1 -> it1.qtyDispatch?.let { it2 ->
                inventory[i].qtyDispatched?.plus(
                    it2
                )
            } }


            var qtyAlloc = dispatchDetails?.get(i)?.let { it1 -> it1.qtyDispatch?.let { it2 ->
                inventory[i].qtyAllocated?.minus(
                    it2
                )
            } }

            qtyDisp?.let { it1 -> data4.put("qtyDispatched", it1.toInt()) }
            qtyAlloc?.let { it1 -> data4.put("qtyAllocated", it1.toInt()) }
            data4.put("updatedBy",user.id)
            data4.put("id",inventory[i].id)


            sqlSessionFactory.openSession().update("InventoryMapper.generateInvoiceUpdate",data4)



        }



        var dispatchPlan = dispatchDetails?.get(i)?.planId?.let { getDispatchPlanById(it) }



        var allocatedCount : Int = genInv.month?.let { genInv.year?.let { it1 ->
            genInv.recipientId?.let { it2 ->
                getDispatchDetailsForInvoicing(it,
                    it1, it2
                )
            }
        } }!!.count()



        if(allocatedCount > 0 ){
            var data5: MutableMap<String, Any> = mutableMapOf()
            data5.put("invoiceStatus",DispatchPlanInvoiceStatus.FULLY_INVOICED.id)
            data5.put("updatedBy",user.id)
            dispatchPlan?.let { data5.put("id", it.id) }

            sqlSessionFactory.openSession().update("DispatchPlanMapper.generateInvoiceDispatchPlanFullyInvoiced",data5)
        }else {
            var data6: MutableMap<String, Any> = mutableMapOf()
            data6.put("invoiceStatus",DispatchPlanInvoiceStatus.PARTIAL_INVOICED.id)
            data6.put("updatedBy",user.id)
            dispatchPlan?.let { data6.put("id", it.id) }

            sqlSessionFactory.openSession().update("DispatchPlanMapper.generateInvoiceDispatchPlanPartialInvoiced",data6)

        }

        var idp = InvoiceDetailPlan()

        var idpId = UUID.randomUUID().toString()

        var data7: MutableMap<String, Any> = mutableMapOf()

        data7.put("id",idpId)
        data7.put("headerId",inhId)
        dispatchPlan?.let { data7.put("planId", it.id) }

        sqlSessionFactory.openSession().insert("InvoiceDetailPlanMapper.insertGenerateInvoiceIDP",data7)

        return System.out.println("Invoice Generated successfully !")



        }






    }



















