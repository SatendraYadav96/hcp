package com.squer.promobee.service.repository



import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfCopy
import com.itextpdf.text.pdf.PdfImportedPage
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfSmartCopy
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
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.RuntimeSingleton
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.io.ByteArrayOutputStream
import java.io.StringReader
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDate.of
import java.time.ZoneId
import java.util.*
import kotlin.math.roundToLong


@Repository
class InvoiceRepository(
    securityUtility: SecurityUtility,
): BaseRepository<InvoiceHeader>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory

    @Autowired
    lateinit var inventoryRepository : InventoryRepository


//    @Value("src/main/resources/htmlPrint/promoPrintInvoices.vm")
//    private lateinit var vmConfigPath: String
//
//    @Value("src/main/resources/htmlPrint/promoPrintLabel.vm")
//    private lateinit var vmConfigPathLabel: String


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

    fun getPrintInvoiceHeaders(inhId: String): MutableList<InvoicePrintDetailsDTO> {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("id", inhId)

        return sqlSessionFactory.openSession().selectList<InvoicePrintDetailsDTO>("InvoiceHeaderMapper.getPrintInvoiceHeaders", data)


    }

    fun getVirtualPrintInvoiceHeaders(inhId: String): InvoicePrintDetailsDTO {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("id", inhId)

        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getVirtualPrintInvoiceHeaders", data)

    }


    fun getInvoiceDetailsForPrint(inhId: String): MutableList<InvoiceDetailsPrintDTO> {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("id", inhId)

        return sqlSessionFactory.openSession().selectList<InvoiceDetailsPrintDTO>("InvoiceHeaderMapper.getPrintInvoiceDetails", data).toMutableList()

    }


    fun getHsnRate(hcmCode: String): HSN {
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("hcmCode", hcmCode)

        return sqlSessionFactory.openSession().selectOne("HSNMapper.getHsnRate", data)

    }


    fun printInvoice(inh: List<PrintInvoiceDTO>): MutableList<ByteArray>? {
        val writer = StringWriter()
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val date = Date()
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val year = localDate.year
        val employeePeriod = "${localDate.month}-$year"
        val promoMonth = "${localDate.month}-$year"

        val finalArray = mutableListOf<ByteArray>()

        inh.forEach { i ->

            val printDetails = i.inhId?.let { getPrintInvoiceHeaders(it) }?.toMutableList() ?: mutableListOf()
            val printDetailsBody = i.inhId?.let { getInvoiceDetailsForPrint(it) }?.toMutableList() ?: mutableListOf()

            VelocityEngine().apply {
                setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath")
                setProperty("file.resource.loader.path", "/templates/printInvoice.vm")
                init()
            }

            val data0: MutableMap<String, Any> = mutableMapOf("code" to "INVOICE")
            val pic = sqlSessionFactory.openSession().selectOne<PrintInvoiceTableDto>("ReportMapper.printInvoiceDatabase", data0)
            val contentConfig = pic.contentPic.toString()
            val template = convertToTemplate(contentConfig)
            val context = VelocityContext()

            printDetails.forEach { it ->

                context.put("InvoiceNumber", it.invoiceNumber ?: "")
                context.put("EmployeeCode", it.employeeCode ?: "")
                context.put("EmployeeDesignation", it.employeeDesignation ?: "")
                context.put("EmployeeName", it.employeeName ?: "")
                context.put("EmployeeAddress", it.employeeAddress ?: "")
                context.put("EmployeeCity", it.employeeCity ?: "")
                context.put("EmployeeState", it.employeeState ?: "")
                context.put("EmployeePinCode", it.employeePinCode ?: "")
                context.put("EmployeeMobileNumber", it.employeeMobileNumber ?: "")
                context.put("EmployeePeriod", employeePeriod)
                context.put("EmployeeLRNumber", it.employeeLRNumber ?: "")
                context.put("EmployeeDate", it.employeeDate ?: "")
                context.put("EmployeeLRDate", it.employeeLRDate ?: "")
                context.put("EmployeeTeam", it.employeeTeam ?: "")
                context.put("EmployeeTransport", it.employeeTransport ?: "")
                context.put("EmployeeCFA", it.employeeCFA ?: "")
                context.put("PROMOMONTH", promoMonth)
                context.put("PLANTYPE", it.type ?: "")
                context.put("EmployeeTotalNoOfCases", it.employeeTotalNoOfCases ?: "")
                context.put("EmployeeTotalWeight", it.employeeTotalWeight ?: "")
                context.put("EmployeeRemark", it.employeeRemark ?: "")
                context.put("TotalSampleValue", String.format("%.2f", it.employeeSampleValue).toDouble() ?: "")
                context.put("TotalInputValue", String.format("%.2f", it.employeeInputValue).toDouble() ?: "")
                context.put("TotalSumValue", String.format("%.2f", it.employeeValue).toDouble() ?: "")

            }

            var tableRow = ""
            var srNo = 1
            var value = 0.00


            printDetailsBody.forEach { it ->

                val taxableValue = it.InvoiceDetailsRatePerUnit?.let { rate -> it.invoiceDetailsQuantity?.times(rate) }?.toDouble()
                val formattedTaxableValue = String.format("%.2f", taxableValue ?: 0.0)

                val gstAmount = it.InvoiceDetailsGSTRate?.let { rate -> taxableValue?.times(rate)?.div(100) }
                val formattedGstAmount = String.format("%.2f", gstAmount ?: 0.0)
                val amount = gstAmount?.let { it1 -> taxableValue?.plus(it1) }?.toDouble()
                val formattedTotalAmount = String.format("%.2f", amount ?: 0.0)

                tableRow += """
                    <tr>
                        <td style="border-left:none; border-bottom: 1px solid #0000;">$srNo</td>
                        <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;" >${it.invoiceDetailsProductCode ?: ""}</td>
                        <td colspan="2" style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${it.invoiceDetailsHSNCode ?: ""}</td>
                        <td colspan="2" style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${it.invoiceDetailsItemDescription ?: ""}</td>
                        <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${it.invoiceDetailsQuantity?.toInt() ?: ""}</td>
                        <td colspan="2" style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${it.invoiceDetailsSAPCode?: ""}</td>
                        <td colspan="2" style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${it.invoiceDetailsBatchNo ?: ""}</td>
                        <td colspan="2" style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${it.invoiceDetailsExpiryDate?: ""}</td>
                        <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${it.InvoiceDetailsRatePerUnit}</td>
                        <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${formattedTaxableValue}</td>
                         <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">$value</td>
                        <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">$value</td>
                      <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">$value</td>
                        <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">$value</td>
                         <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${it.InvoiceDetailsGSTRate}</td>
                        <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${formattedGstAmount}</td>
                        <td style="border-left:1px solid #0000; border-bottom: 1px solid #0000;">${formattedTotalAmount}</td>
                    </tr>
                """.trimIndent()
                srNo++

            }



            context.put("tableRow", tableRow)

            template.merge(context, writer)
        }

        try {
            println("===================================")
            println(writer.toString())
            println("===================================")
            val byteArrayOutputStream = ByteArrayOutputStream()
            HtmlConverter.convertToPdf(writer.toString(), byteArrayOutputStream)
            finalArray.add(byteArrayOutputStream.toByteArray())

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return finalArray
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


    fun printLabel(inh: List<PrintInvoiceDTO>):MutableList<ByteArray>? {
        var writer = StringWriter()
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        val byteArrayOutputStream = ByteArrayOutputStream()

        var finalArray = mutableListOf<ByteArray>()

        var ids = inh.map {
            it.inhId
        }

       var data: MutableMap<String, Any> = mutableMapOf("ids" to ids)

        var labelDetails = sqlSessionFactory.openSession().selectList<LabelPrintDetailsDTO>("InvoiceHeaderMapper.getLabelPrintDetailsByInvoices", data)

        labelDetails

        var pic = PrintInvoiceTableDto()

        var data0: MutableMap<String, Any> = mutableMapOf()
        data0.put("code","LABEL")

        pic = sqlSessionFactory.openSession().selectOne<PrintInvoiceTableDto>("ReportMapper.printInvoiceDatabase",data0)

        var contentConfig = ""

        contentConfig = pic.contentPic.toString()

        println(contentConfig)

        val labelArray = mutableListOf<Map<String,Any?>>()
        for(detail in labelDetails) {
            val invoiceNo = detail.invoiceNo
            for(i in 1..(detail.noOfBoxes ?: 1)) {
                val context = mutableMapOf<String, Any?>()
                var length = "";
                var breadth = "";
                var height = "";
                context["InvoiceNo"] =detail.invoiceNo
                context["TransporterName"] =detail.transporterName
                context["InvoiceLRNo"] =detail.lRNumber
                context["InvoiceCreatedDate"] =detail.invoiceDate
                context["RecipientCode"] =detail.recipientCode
                context["RecipientName"] =detail.recipientName
                context["RecipientTeam"] =detail.teamName
                context["RecipientDesgination"] =detail.recipientDesgination
                context["RecipientAddress"] =detail.recipientAddress
                context["RecipientCity"] =detail.recipientCity
                context["RecipientState"] =detail.recipientState
                context["RecipientPhone"] =detail.recipientPhone
                context["RecipientPinCode"] =detail.recipientPinCode
                context["RecipientHeadQuarter"] =detail.recipientHeadQuarter
                context["InvoiceBoxes"] =detail.noOfBoxes
                context["InvoiceWeight"] =detail.weight

                if (detail.dimension !== null) {
                    context["length"] =length
                    context["breadth"] =breadth
                    context["height"] =height
                } else {
                    context["length"] =length
                    context["breadth"] =breadth
                    context["height"] =height
                }
                labelArray.add(context)

            }
        }

        val template = convertToTemplate(contentConfig)
        val velocityContext = VelocityContext()
        velocityContext.put("labels", labelArray)
        template.merge(velocityContext   , writer)

        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            HtmlConverter.convertToPdf(writer.toString(), byteArrayOutputStream)
            finalArray.add(byteArrayOutputStream.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return finalArray

    }





    fun getRecipientToGenerateInvoice(recipientId: String): Recipient {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("recipientId", recipientId)

             return sqlSessionFactory.openSession().selectOne("RecipientMapper.getRecipient", data)


        }

    fun getDoctorToGenerateInvoice(id: String): Doctor {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id", id)

        return sqlSessionFactory.openSession().selectOne("DoctorMapper.getDoctor", data)


    }


    fun getInventoryByIdForInvoicing(invId: String): MutableList<Inventory> {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id", invId)

        return sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.getInventoryByIdForInvoicing", data).toMutableList()


    }


        fun getRecipientItemCategoryCount(month: Int, year: Int, recipientId: String,isSpecial:Int,planId: String): ItemCategoryCountDTO {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("month", month)
            data.put("year", year)
            data.put("recipientId", recipientId)
            data.put("isSpecial",isSpecial)
            data.put("planId",planId)

            var i = 0

            var samplesCount = 0.0

            var inputCount = 0.0

             samplesCount =
                 sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getSamplesCount", data)

            var data0: MutableMap<String, Any> = mutableMapOf()
            data0.put("month", month)
            data0.put("year", year)
            data0.put("recipientId", recipientId)
            data0.put("isSpecial",isSpecial)
            data0.put("planId",planId)

             inputCount =
                 sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getInputCount", data0)


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



    fun getDoctorItemCategoryCount(planId: String, recipientId: String): MutableList<ItemCategoryCountDTO> {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("planId", planId)
        data.put("recipientId", recipientId)

        var i = 0

        var samplesCount =
            return sqlSessionFactory.openSession().selectList<ItemCategoryCountDTO>("InvoiceHeaderMapper.getSamplesCountVirtual", data).toMutableList()

        var data0: MutableMap<String, Any> = mutableMapOf()

        data0.put("planId", planId)
        data0.put("recipientId", recipientId)

        var inputCount =
            return sqlSessionFactory.openSession().selectList<ItemCategoryCountDTO>("InvoiceHeaderMapper.getInputCountVirtual", data0).toMutableList()


        var itcCount = MutableList<ItemCategoryCountDTO>()

        itcCount.get(i).sampleItems = samplesCount
//        if(itcCount.sampleItems == null){
//            itcCount.sampleItems = 0.0
//        }
        itcCount.get(i).nonSampleItems = inputCount
//        if(itcCount.nonSampleItems == null){
//            itcCount.nonSampleItems = 0.0
//        }

        return itcCount


    }

    private fun <T> MutableList(): MutableList<ItemCategoryCountDTO> {
        TODO("Not yet implemented")
    }


    fun getDispatchDetailsForInvoicing(month: Int, year: Int, recipientId: String,isSpecial:Int,planId:String): MutableList<DispatchDetailDTO> {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("month", month)
            data.put("year", year)
            data.put("recipientId", recipientId)
            data.put("isSpecial",isSpecial)
        data.put("planId",planId)

            return sqlSessionFactory.openSession().selectList<DispatchDetailDTO>("DispatchDetailMapper.getDispatchDetails", data).toMutableList()


        }


    fun getDispatchDetailVirtual(planId: String, recipientId: String): MutableList<DispatchDetailDTO> {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("planId", planId)
        data.put("recipientId", recipientId)


        return sqlSessionFactory.openSession().selectList<DispatchDetailDTO>("DispatchDetailMapper.getDispatchDetailVirtual", data).toMutableList()


    }


        fun getItemMasterById(id: String): Item {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

            var data: MutableMap<String, Any> = mutableMapOf()
            data.put("id", id)

            return sqlSessionFactory.openSession().selectOne("ItemMapper.getItemMasterById", data)

        }

        fun getSampleMasterById(id: String): SampleMaster {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

            var data: MutableMap<String, Any> = mutableMapOf()
            data.put("id", id)

            return sqlSessionFactory.openSession().selectOne("SampleMasterMapper.getSampleMasterById", data)

        }


        fun getDispatchPlanById(id: String): DispatchPlan {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

            var data: MutableMap<String, Any> = mutableMapOf()
            data.put("id", id)

            return sqlSessionFactory.openSession().selectOne("DispatchPlanMapper.getDispatchPlanById", data)

        }




    fun getInvoice(invoiceNo : Int): InvoiceHeader {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("invoiceNo", invoiceNo)

        return sqlSessionFactory.openSession().selectOne("InvoiceHeaderMapper.getInvoice", data)
    }

    fun getTransporter(name : String): Transporter {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("name", name)

        return sqlSessionFactory.openSession().selectOne("TransporterMapper.getTransporterByName", data)
    }


    fun getDocket(docketName : String): DocketDTO {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("docketName", docketName)

        return sqlSessionFactory.openSession().selectOne("TransporterMapper.getDocket", data)
    }



    fun generateInvoice(genInv: GenerateInvoiceDTO){
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
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

        var invoiceCount = 0

        var isInvoiceAlreadyGenerated = false

        var plans = 0

        if(genInv !== null){

            var data: MutableMap<String, Any> = mutableMapOf()
            genInv.recipientId?.let { data.put("employeeId", it) }
            genInv.month?.let { data.put("month", it) }
            genInv.year?.let { data.put("year", it) }
            genInv.isSpecial?.let { data.put("isSpecial", it) }

            invoiceCount = sqlSessionFactory.openSession()
                .selectOne("UploadLogMapper.isInvoiceGeneratedForTheRecipient", data)

            if (invoiceCount > 0) {
                isInvoiceAlreadyGenerated = true
                println("Invoice already generated !")

            } else {




                var recipient = genInv.recipientId?.let { getRecipientToGenerateInvoice(it) }

                var itcCount = genInv.month?.let { genInv.year?.let { it1 -> genInv.recipientId?.let { it2 ->
                    genInv.isSpecial?.let { it3 -> genInv.planId?.let { it4 ->
                        getRecipientItemCategoryCount(
                            it, it1,
                            it2,
                            it3,
                            it4
                        )
                    }
                    }
                } } }

                var dispatchDetails = genInv.month?.let { genInv.year?.let { it1 -> genInv.recipientId?.let { it2 ->
                    genInv.isSpecial?.let { it3 -> genInv.planId?.let { it4 ->
                        getDispatchDetailsForInvoicing(
                            it, it1,
                            it2,
                            it3,
                            it4
                        )
                    }
                    }
                } } }


              var i = 0

                dispatchDetails?.forEach {

                    var inhCount = mutableListOf<InvoiceHeader>()



                    var data0: MutableMap<String, Any> = mutableMapOf()

                    data0.put("planId",it.planId!!)
                    data0.put("recipientId",it.recipientId!!)
                    inhCount = sqlSessionFactory.openSession()
                        .selectList<InvoiceHeader>("InvoiceHeaderMapper.invoiceDetails", data0)

                    var i = 0

                    if(inhCount.size > 0) {

                        var inventory = it.inventoryId?.let { it1 -> getInventoryByIdForInvoicing(it1) }

                        var samples = ItemCategoryEnum.SAMPLES.id
                        var itemId = ""



                        inventory?.forEach {
                            if(it.categoryId!!.equals(samples)) {
                                var smp = it.item?.let { it1 -> getSampleMasterById(it1.id) }
                                if (smp != null) {
                                    itemId = smp.id
                                }

                            } else {
                                var itm = it.item?.let { it1 -> getItemMasterById(it1.id) }
                                if (itm != null) {
                                    itemId = itm.id
                                }
                            }
                        }




                        // INVOICE DETAIL IND




                        var data2: MutableMap<String, Any> = mutableMapOf()

                        var ind = InvoiceDetail()

                        var indId = UUID.randomUUID().toString()

                        var value =
                            inventory?.get(i)?.ratePerUnit?.let { it1 -> it!!.qtyDispatch!!.times(it1) }

                        data2.put("id", indId)
                        data2.put("headerId", inhCount[i].id!!)
                        inventory?.get(i)?.item?.let { it1 -> data2.put("item", it1.id) }
                        it.qtyDispatch?.let { it1 -> data2.put("quantity", it1) }
                        it.let { it1 -> it1.id?.let { it2 -> data2.put("didId", it2) } }
                        value?.let { it1 -> data2.put("value", it1.toDouble()) }
                        data2.put("createdBy", user.id)
                        data2.put("updatedBy", user.id)
                        inventory?.get(i)?.id?.let { it1 -> data2.put("inventoryId", it1) }
                        inventory?.get(i)?.hsnCode?.let { it1 -> data2.put("hsnCode", it1) }
                        inventory?.get(i)?.rate?.let { it1 -> data2.put("rate", it1) }

                        sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertGenerateInvoiceDetail", data2)


                        // DISPATCH DETAIL UPDATE


                        var data3: MutableMap<String, Any> = mutableMapOf()

                        data3.put("detailStatus", DispatchDetailStatusEnum.INVOICED.id)
                        it.recipientId?.let { it1 -> data3.put("recipientId", it1) }
                        data3.put("updatedBy", user.id)
                        data3.put("planId", it.planId!!)
                        data3.put("invId",it.inventoryId!!)

                        sqlSessionFactory.openSession()
                            .update("DispatchDetailMapper.editDispatchDetailsForInvoicing", data3)



                        // INVENTORY UPDATE


                        var data4: MutableMap<String, Any> = mutableMapOf()

                        var qtyDisp = it.let { it1 ->
                            it1.qtyDispatch?.let { it2 ->
                                inventory?.get(i)?.qtyDispatched?.plus(
                                    it2
                                )
                            }
                        }


                        var qtyAlloc = it.let { it1 ->
                            it1.qtyDispatch?.let { it2 ->
                                inventory?.get(i)?.qtyAllocated?.minus(
                                    it2
                                )
                            }
                        }

                        qtyDisp?.let { it1 -> data4.put("qtyDispatched", it1.toInt()) }
                        qtyAlloc?.let { it1 -> data4.put("qtyAllocated", it1.toInt()) }
                        data4.put("updatedBy", user.id)
                        inventory?.get(i)?.id?.let { it1 -> data4.put("id", it1) }


                        sqlSessionFactory.openSession().update("InventoryMapper.generateInvoiceUpdate", data4)


                        var dispatchPlan = it.planId?.let { getDispatchPlanById(it) }


                        var allocatedCount: Int = genInv.month?.let {
                            genInv.year?.let { it1 ->
                                genInv.recipientId?.let { it2 ->
                                    genInv.isSpecial?.let { it3 ->
                                        genInv.planId?.let { it4 ->
                                            getDispatchDetailsForInvoicing(
                                                it,
                                                it1, it2,
                                                it3,
                                                it4
                                            )
                                        }
                                    }
                                }
                            }
                        }!!.count()

                        if (allocatedCount > 0) {
                            var data5: MutableMap<String, Any> = mutableMapOf()
                            data5.put("invoiceStatus", DispatchPlanInvoiceStatus.FULLY_INVOICED.id)
                            data5.put("updatedBy", user.id)
                            dispatchPlan?.let { data5.put("id", it.id) }

                            sqlSessionFactory.openSession()
                                .update("DispatchPlanMapper.generateInvoiceDispatchPlanFullyInvoiced", data5)
                        } else {
                            var data6: MutableMap<String, Any> = mutableMapOf()
                            data6.put("invoiceStatus", DispatchPlanInvoiceStatus.PARTIAL_INVOICED.id)
                            data6.put("updatedBy", user.id)
                            dispatchPlan?.let { data6.put("id", it.id) }

                            sqlSessionFactory.openSession()
                                .update("DispatchPlanMapper.generateInvoiceDispatchPlanPartialInvoiced", data6)

                        }

                        var idp = InvoiceDetailPlan()

                        var idpId = UUID.randomUUID().toString()

                        var data7: MutableMap<String, Any> = mutableMapOf()

                        data7.put("id", idpId)
                        data7.put("headerId", inhCount[i].id!!)
                        dispatchPlan?.let { data7.put("planId", it.id) }

                        sqlSessionFactory.openSession().insert("InvoiceDetailPlanMapper.insertGenerateInvoiceIDP", data7)


                    }else{


                        // INVOICE HEADER INSERT

                        var inh = InvoiceHeader()

                        var inhId = UUID.randomUUID().toString()


                        var data1: MutableMap<String, Any> = mutableMapOf()



                        data1.put("id", inhId)
                        inh.invoiceNo?.let { data1.put("invoiceNo", it) }
                        data1.put("type", InvoiceTypeEnum.DISPATCHED.id)
                        data1.put("statusId", InvoiceStatusEnum.GENERATED_PRINTED.id)
                        recipient?.team?.let { it1 -> data1.put("teamId", it1.id) }
                        recipient?.let { it1 -> data1.put("recipientId", it1.id) }
                        recipient?.address?.let { it1 -> data1.put("addressLine1", it1) }
                        recipient?.address?.let { it1 -> data1.put("addressLine2", it1) }
                        recipient?.state?.let { it1 -> data1.put("states", it1) }
                        recipient?.city?.let { it1 -> data1.put("city", it1) }
                        recipient?.zip?.let { it1 -> data1.put("zip", it1) }
                        recipient?.mobile?.let { it1 -> data1.put("phone", it1) }
                        genInv.weight?.let { data1.put("weight", it) }
                        genInv.boxes?.let { data1.put("noOfBoxes", it) }
                        genInv.transporter?.let { data1.put("transporterId", it) }
                        //sample value
                        if (itcCount?.sampleItems !== null) {
                            itcCount?.sampleItems?.let { data1.put("sampleValue", it) }
                        } else {
                            data1.put("sampleValue", 0)
                        }

                        // item value
                        if (itcCount?.nonSampleItems !== null) {
                            itcCount?.nonSampleItems?.let { data1.put("otherItemValue", it) }
                        } else {
                            data1.put("otherItemValue", 0)
                        }

                        genInv.lrNo?.let { data1.put("lrNumber", it) }
                        data1.put("createdBy", user.id)
                        data1.put("updatedBy", user.id)
                        recipient?.designation?.let { it1 -> data1.put("designationId", it1.id) }
                        recipient?.cfa?.let { it1 -> data1.put("cfa", it1) }

                        sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertGenerateInvoiceHeader", data1)


                        var inventory = it.inventoryId?.let { it1 -> getInventoryByIdForInvoicing(it1) }

                        var samples = ItemCategoryEnum.SAMPLES.id
                        var itemId = ""



                        inventory?.forEach {
                            if(it.categoryId!!.equals(samples)) {
                                var smp = it.item?.let { it1 -> getSampleMasterById(it1.id) }
                                if (smp != null) {
                                    itemId = smp.id
                                }

                            } else {
                                var itm = it.item?.let { it1 -> getItemMasterById(it1.id) }
                                if (itm != null) {
                                    itemId = itm.id
                                }
                            }
                        }




                        // INVOICE DETAIL IND




                        var data2: MutableMap<String, Any> = mutableMapOf()

                        var ind = InvoiceDetail()

                        var indId = UUID.randomUUID().toString()

                        var value =
                            inventory?.get(i)?.ratePerUnit?.let { it1 -> it!!.qtyDispatch!!.times(it1) }

                        data2.put("id", indId)
                        data2.put("headerId", inhId)
                        inventory?.get(i)?.item?.let { it1 -> data2.put("item", it1.id) }
                        it.qtyDispatch?.let { it1 -> data2.put("quantity", it1) }
                        it.let { it1 -> it1.id?.let { it2 -> data2.put("didId", it2) } }
                        value?.let { it1 -> data2.put("value", it1.toDouble()) }
                        data2.put("createdBy", user.id)
                        data2.put("updatedBy", user.id)
                        inventory?.get(i)?.id?.let { it1 -> data2.put("inventoryId", it1) }
                        inventory?.get(i)?.hsnCode?.let { it1 -> data2.put("hsnCode", it1) }
                        inventory?.get(i)?.rate?.let { it1 -> data2.put("rate", it1) }

                        sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertGenerateInvoiceDetail", data2)


                        // DISPATCH DETAIL UPDATE


                        var data3: MutableMap<String, Any> = mutableMapOf()

                        data3.put("detailStatus", DispatchDetailStatusEnum.INVOICED.id)
                        it.recipientId?.let { it1 -> data3.put("recipientId", it1) }
                        data3.put("updatedBy", user.id)
                        data3.put("planId", it.planId!!)
                        data3.put("invId",it.inventoryId!!)

                        sqlSessionFactory.openSession()
                            .update("DispatchDetailMapper.editDispatchDetailsForInvoicing", data3)



                        // INVENTORY UPDATE


                        var data4: MutableMap<String, Any> = mutableMapOf()

                        var qtyDisp = it.let { it1 ->
                            it1.qtyDispatch?.let { it2 ->
                                inventory?.get(i)?.qtyDispatched?.plus(
                                    it2
                                )
                            }
                        }


                        var qtyAlloc = it.let { it1 ->
                            it1.qtyDispatch?.let { it2 ->
                                inventory?.get(i)?.qtyAllocated?.minus(
                                    it2
                                )
                            }
                        }

                        qtyDisp?.let { it1 -> data4.put("qtyDispatched", it1.toInt()) }
                        qtyAlloc?.let { it1 -> data4.put("qtyAllocated", it1.toInt()) }
                        data4.put("updatedBy", user.id)
                        inventory?.get(i)?.id?.let { it1 -> data4.put("id", it1) }


                        sqlSessionFactory.openSession().update("InventoryMapper.generateInvoiceUpdate", data4)


                        var dispatchPlan = it.planId?.let { getDispatchPlanById(it) }


                        var allocatedCount: Int = genInv.month?.let {
                            genInv.year?.let { it1 ->
                                genInv.recipientId?.let { it2 ->
                                    genInv.isSpecial?.let { it3 ->
                                        genInv.planId?.let { it4 ->
                                            getDispatchDetailsForInvoicing(
                                                it,
                                                it1, it2,
                                                it3,
                                                it4
                                            )
                                        }
                                    }
                                }
                            }
                        }!!.count()

                        if (allocatedCount > 0) {
                            var data5: MutableMap<String, Any> = mutableMapOf()
                            data5.put("invoiceStatus", DispatchPlanInvoiceStatus.FULLY_INVOICED.id)
                            data5.put("updatedBy", user.id)
                            dispatchPlan?.let { data5.put("id", it.id) }

                            sqlSessionFactory.openSession()
                                .update("DispatchPlanMapper.generateInvoiceDispatchPlanFullyInvoiced", data5)
                        } else {
                            var data6: MutableMap<String, Any> = mutableMapOf()
                            data6.put("invoiceStatus", DispatchPlanInvoiceStatus.PARTIAL_INVOICED.id)
                            data6.put("updatedBy", user.id)
                            dispatchPlan?.let { data6.put("id", it.id) }

                            sqlSessionFactory.openSession()
                                .update("DispatchPlanMapper.generateInvoiceDispatchPlanPartialInvoiced", data6)

                        }

                        var idp = InvoiceDetailPlan()

                        var idpId = UUID.randomUUID().toString()

                        var data7: MutableMap<String, Any> = mutableMapOf()

                        data7.put("id", idpId)
                        data7.put("headerId", inhId)
                        dispatchPlan?.let { data7.put("planId", it.id) }

                        sqlSessionFactory.openSession().insert("InvoiceDetailPlanMapper.insertGenerateInvoiceIDP", data7)


                    }






                }




            }




            } else {
                println("There is not data in genInv payload !")
            }

        }


    fun generateInvoiceVirtual(genInv: GenerateInvoiceVirtualDTO){
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        genInv.recipientId?.let { data.put("recipientId", it) }
        genInv.boxes?.let { data.put("boxes", it) }
        genInv.weight?.let { data.put("weight", it) }
        genInv.transporter?.let { data.put("transporter", it) }
        genInv.lrNo?.let { data.put("lrNo", it) }
        genInv.dimension?.let { data.put("dimension", it) }
        genInv.planId?.let { data.put("planId", it) }
        genInv.isvirtual?.let { data.put("isvirtual", it) }


        var plans = 0

        var data0: MutableMap<String, Any> = mutableMapOf()

        genInv.planId?.let { data0.put("planId", it) }
        genInv.recipientId?.let { data0.put("recipientId", it) }

        plans = sqlSessionFactory.openSession().selectOne("UploadLogMapper.isInvoiceGeneratedVirtual", data0)

        if(plans > 0 ){
            println("plan" + genInv.planId + "for" + genInv.recipientId + "already invoiced !")
        }

        if(genInv.isvirtual == 1){

            var doctor = genInv.recipientId?.let { getDoctorToGenerateInvoice(it) }

            var dispatchDetails = genInv.planId?.let { genInv.recipientId?.let { it1 ->
                getDispatchDetailVirtual(it,
                    it1
                )
            } }

            var itcCount = genInv.planId?.let { genInv.recipientId?.let { it1 -> getDoctorItemCategoryCount(it, it1) } }


            var i = 0

            dispatchDetails?.forEach {
                // INVOICE HEADER INSERT

                var inh = InvoiceHeader()

                var inhId = UUID.randomUUID().toString()

                var data1: MutableMap<String, Any> = mutableMapOf()



                data1.put("id", inhId)
                inh.invoiceNo?.let { data1.put("invoiceNo", it) }
                data1.put("type", InvoiceTypeEnum.DISPATCHED.id)
                data1.put("statusId", InvoiceStatusEnum.GENERATED_PRINTED.id)
                doctor?.teamId?.let { it1 -> data1.put("teamId", it1.id) }
                doctor?.id?.let { it1 -> data1.put("recipientId", it1) }
                doctor?.address?.let { it1 -> data1.put("addressLine1", it1) }
                doctor?.address?.let { it1 -> data1.put("addressLine2", it1) }
                doctor?.state?.let { it1 -> data1.put("states", it1) }
                doctor?.city?.let { it1 -> data1.put("city", it1) }
                doctor?.zip?.let { it1 -> data1.put("zip", it1) }
                doctor?.mobile?.let { it1 -> data1.put("phone", it1) }
                genInv.weight?.let { data1.put("weight", it) }
                genInv.boxes?.let { data1.put("noOfBoxes", it) }
                genInv.transporter?.let { data1.put("transporterId", it) }
                //sample value
                if (itcCount?.get(i)?.sampleItems !== null) {
                    itcCount?.get(i)?.sampleItems?.let { data1.put("sampleValue", it) }
                } else {
                    data1.put("sampleValue", 0)
                }

                // item value
                if (itcCount?.get(i)?.nonSampleItems !== null) {
                    itcCount?.get(i)?.nonSampleItems?.let { data1.put("otherItemValue", it) }
                } else {
                    data1.put("otherItemValue", 0)
                }

                genInv.lrNo?.let { data1.put("lrNumber", it) }
                data1.put("createdBy", user.id)
                data1.put("updatedBy", user.id)
                doctor?.designationId?.let { it1 -> data1.put("designationId", it1.id) }
                doctor?.state?.let { it1 -> data1.put("cfa", it1) }

                sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertGenerateInvoiceHeader", data1)


                var inventory = it.inventoryId?.let { it1 -> getInventoryByIdForInvoicing(it1) }

                var samples = ItemCategoryEnum.SAMPLES.id
                var itemId = ""



                inventory?.forEach {
                    if(it.categoryId!!.equals(samples)) {
                        var smp = it.item?.let { it1 -> getSampleMasterById(it1.id) }
                        if (smp != null) {
                            itemId = smp.id
                        }

                    } else {
                        var itm = it.item?.let { it1 -> getItemMasterById(it1.id) }
                        if (itm != null) {
                            itemId = itm.id
                        }
                    }
                }




                // INVOICE DETAIL IND




                var data2: MutableMap<String, Any> = mutableMapOf()

                var ind = InvoiceDetail()

                var indId = UUID.randomUUID().toString()

                var value =
                    inventory?.get(i)?.ratePerUnit?.let { it1 -> it!!.qtyDispatch!!.times(it1) }

                data2.put("id", indId)
                data2.put("headerId", inhId)
                inventory?.get(i)?.item?.let { it1 -> data2.put("item", it1.id) }
                it.qtyDispatch?.let { it1 -> data2.put("quantity", it1) }
                it.let { it1 -> it1.id?.let { it2 -> data2.put("didId", it2) } }
                value?.let { it1 -> data2.put("value", it1.toDouble()) }
                data2.put("createdBy", user.id)
                data2.put("updatedBy", user.id)
                inventory?.get(i)?.id?.let { it1 -> data2.put("inventoryId", it1) }
                inventory?.get(i)?.hsnCode?.let { it1 -> data2.put("hsnCode", it1) }
                inventory?.get(i)?.rate?.let { it1 -> data2.put("rate", it1) }

                sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertGenerateInvoiceDetail", data2)


                // DISPATCH DETAIL UPDATE


                var data3: MutableMap<String, Any> = mutableMapOf()

                data3.put("detailStatus", DispatchDetailStatusEnum.INVOICED.id)
                it.recipientId?.let { it1 -> data3.put("recipientId", it1) }
                data3.put("updatedBy", user.id)

                sqlSessionFactory.openSession()
                    .update("DispatchDetailMapper.editDispatchDetailsForInvoicing", data3)



                // INVENTORY UPDATE


                var data4: MutableMap<String, Any> = mutableMapOf()

                var qtyDisp = it.let { it1 ->
                    it1.qtyDispatch?.let { it2 ->
                        inventory?.get(i)?.qtyDispatched?.plus(
                            it2
                        )
                    }
                }


                var qtyAlloc = it.let { it1 ->
                    it1.qtyDispatch?.let { it2 ->
                        inventory?.get(i)?.qtyAllocated?.minus(
                            it2
                        )
                    }
                }

                qtyDisp?.let { it1 -> data4.put("qtyDispatched", it1.toInt()) }
                qtyAlloc?.let { it1 -> data4.put("qtyAllocated", it1.toInt()) }
                data4.put("updatedBy", user.id)
                inventory?.get(i)?.id?.let { it1 -> data4.put("id", it1) }


                sqlSessionFactory.openSession().update("InventoryMapper.generateInvoiceUpdate", data4)


                var dispatchPlan = it.planId?.let { getDispatchPlanById(it) }



                var allocatedCount : Int = genInv.planId?.let { it1 -> genInv.recipientId?.let { it2 ->
                    getDispatchDetailVirtual(it1,
                        it2
                    )
                } }!!.count()

                if (allocatedCount > 0) {
                    var data5: MutableMap<String, Any> = mutableMapOf()
                    data5.put("invoiceStatus", DispatchPlanInvoiceStatus.FULLY_INVOICED.id)
                    data5.put("updatedBy", user.id)
                    dispatchPlan?.let { data5.put("id", it.id) }

                    sqlSessionFactory.openSession()
                        .update("DispatchPlanMapper.generateInvoiceDispatchPlanFullyInvoiced", data5)
                } else {
                    var data6: MutableMap<String, Any> = mutableMapOf()
                    data6.put("invoiceStatus", DispatchPlanInvoiceStatus.PARTIAL_INVOICED.id)
                    data6.put("updatedBy", user.id)
                    dispatchPlan?.let { data6.put("id", it.id) }

                    sqlSessionFactory.openSession()
                        .update("DispatchPlanMapper.generateInvoiceDispatchPlanPartialInvoiced", data6)

                }

                var idp = InvoiceDetailPlan()

                var idpId = UUID.randomUUID().toString()

                var data7: MutableMap<String, Any> = mutableMapOf()

                data7.put("id", idpId)
                data7.put("headerId", inhId)
                dispatchPlan?.let { data7.put("planId", it.id) }

                sqlSessionFactory.openSession().insert("InvoiceDetailPlanMapper.insertGenerateInvoiceIDP", data7)

                i++

            }



            println("Virtual invoice generated successfully !")

        }
        else{

            var recipient = genInv.recipientId?.let { getRecipientToGenerateInvoice(it) }

            var dispatchDetails = genInv.planId?.let { genInv.recipientId?.let { it1 ->
                getDispatchDetailVirtual(it,
                    it1
                )
            } }

            var itcCount = genInv.planId?.let { genInv.recipientId?.let { it1 -> getDoctorItemCategoryCount(it, it1) } }


            var i = 0

            dispatchDetails?.forEach {
                // INVOICE HEADER INSERT

                var inh = InvoiceHeader()

                var inhId = UUID.randomUUID().toString()

                var data1: MutableMap<String, Any> = mutableMapOf()



                data1.put("id", inhId)
                inh.invoiceNo?.let { data1.put("invoiceNo", it) }
                data1.put("type", InvoiceTypeEnum.DISPATCHED.id)
                data1.put("statusId", InvoiceStatusEnum.GENERATED_PRINTED.id)
                recipient?.team?.let { it1 -> data1.put("teamId", it1.id) }
                recipient?.id?.let { it1 -> data1.put("recipientId", it1) }
                recipient?.address?.let { it1 -> data1.put("addressLine1", it1) }
                recipient?.address?.let { it1 -> data1.put("addressLine2", it1) }
                recipient?.state?.let { it1 -> data1.put("states", it1) }
                recipient?.city?.let { it1 -> data1.put("city", it1) }
                recipient?.zip?.let { it1 -> data1.put("zip", it1) }
                recipient?.mobile?.let { it1 -> data1.put("phone", it1) }
                genInv.weight?.let { data1.put("weight", it) }
                genInv.boxes?.let { data1.put("noOfBoxes", it) }
                genInv.transporter?.let { data1.put("transporterId", it) }
                //sample value
                if (itcCount?.get(i)?.sampleItems !== null) {
                    itcCount?.get(i)?.sampleItems?.let { data1.put("sampleValue", it) }
                } else {
                    data1.put("sampleValue", 0)
                }

                // item value
                if (itcCount?.get(i)?.nonSampleItems !== null) {
                    itcCount?.get(i)?.nonSampleItems?.let { data1.put("otherItemValue", it) }
                } else {
                    data1.put("otherItemValue", 0)
                }

                genInv.lrNo?.let { data1.put("lrNumber", it) }
                data1.put("createdBy", user.id)
                data1.put("updatedBy", user.id)
                recipient?.designation?.let { it1 -> data1.put("designationId", it1.id) }
                recipient?.cfa?.let { it1 -> data1.put("cfa", it1) }

                sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertGenerateInvoiceHeader", data1)


                var inventory = it.inventoryId?.let { it1 -> getInventoryByIdForInvoicing(it1) }

                var samples = ItemCategoryEnum.SAMPLES.id
                var itemId = ""



                inventory?.forEach {
                    if(it.categoryId!!.equals(samples)) {
                        var smp = it.item?.let { it1 -> getSampleMasterById(it1.id) }
                        if (smp != null) {
                            itemId = smp.id
                        }

                    } else {
                        var itm = it.item?.let { it1 -> getItemMasterById(it1.id) }
                        if (itm != null) {
                            itemId = itm.id
                        }
                    }
                }




                // INVOICE DETAIL IND




                var data2: MutableMap<String, Any> = mutableMapOf()

                var ind = InvoiceDetail()

                var indId = UUID.randomUUID().toString()

                var value =
                    inventory?.get(i)?.ratePerUnit?.let { it1 -> it!!.qtyDispatch!!.times(it1) }

                data2.put("id", indId)
                data2.put("headerId", inhId)
                inventory?.get(i)?.item?.let { it1 -> data2.put("item", it1.id) }
                it.qtyDispatch?.let { it1 -> data2.put("quantity", it1) }
                it.let { it1 -> it1.id?.let { it2 -> data2.put("didId", it2) } }
                value?.let { it1 -> data2.put("value", it1.toDouble()) }
                data2.put("createdBy", user.id)
                data2.put("updatedBy", user.id)
                inventory?.get(i)?.id?.let { it1 -> data2.put("inventoryId", it1) }
                inventory?.get(i)?.hsnCode?.let { it1 -> data2.put("hsnCode", it1) }
                inventory?.get(i)?.rate?.let { it1 -> data2.put("rate", it1) }

                sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertGenerateInvoiceDetail", data2)


                // DISPATCH DETAIL UPDATE


                var data3: MutableMap<String, Any> = mutableMapOf()

                data3.put("detailStatus", DispatchDetailStatusEnum.INVOICED.id)
                it.recipientId?.let { it1 -> data3.put("recipientId", it1) }
                data3.put("updatedBy", user.id)

                sqlSessionFactory.openSession()
                    .update("DispatchDetailMapper.editDispatchDetailsForInvoicing", data3)



                // INVENTORY UPDATE


                var data4: MutableMap<String, Any> = mutableMapOf()

                var qtyDisp = it.let { it1 ->
                    it1.qtyDispatch?.let { it2 ->
                        inventory?.get(i)?.qtyDispatched?.plus(
                            it2
                        )
                    }
                }


                var qtyAlloc = it.let { it1 ->
                    it1.qtyDispatch?.let { it2 ->
                        inventory?.get(i)?.qtyAllocated?.minus(
                            it2
                        )
                    }
                }

                qtyDisp?.let { it1 -> data4.put("qtyDispatched", it1.toInt()) }
                qtyAlloc?.let { it1 -> data4.put("qtyAllocated", it1.toInt()) }
                data4.put("updatedBy", user.id)
                inventory?.get(i)?.id?.let { it1 -> data4.put("id", it1) }


                sqlSessionFactory.openSession().update("InventoryMapper.generateInvoiceUpdate", data4)


                var dispatchPlan = it.planId?.let { getDispatchPlanById(it) }


                var allocatedCount : Int = genInv.planId?.let { it1 -> genInv.recipientId?.let { it2 ->
                    getDispatchDetailVirtual(it1,
                        it2
                    )
                } }!!.count()

                if (allocatedCount > 0) {
                    var data5: MutableMap<String, Any> = mutableMapOf()
                    data5.put("invoiceStatus", DispatchPlanInvoiceStatus.FULLY_INVOICED.id)
                    data5.put("updatedBy", user.id)
                    dispatchPlan?.let { data5.put("id", it.id) }

                    sqlSessionFactory.openSession()
                        .update("DispatchPlanMapper.generateInvoiceDispatchPlanFullyInvoiced", data5)
                } else {
                    var data6: MutableMap<String, Any> = mutableMapOf()
                    data6.put("invoiceStatus", DispatchPlanInvoiceStatus.PARTIAL_INVOICED.id)
                    data6.put("updatedBy", user.id)
                    dispatchPlan?.let { data6.put("id", it.id) }

                    sqlSessionFactory.openSession()
                        .update("DispatchPlanMapper.generateInvoiceDispatchPlanPartialInvoiced", data6)

                }

                var idp = InvoiceDetailPlan()

                var idpId = UUID.randomUUID().toString()

                var data7: MutableMap<String, Any> = mutableMapOf()

                data7.put("id", idpId)
                data7.put("headerId", inhId)
                dispatchPlan?.let { data7.put("planId", it.id) }

                sqlSessionFactory.openSession().insert("InvoiceDetailPlanMapper.insertGenerateInvoiceIDP", data7)

                i++

            }

            println("special invoice generated successfully !")

        }






    }

    fun convertToTemplate(content: String): Template {
        val rs = RuntimeSingleton.getRuntimeServices()
        val sr = StringReader(content)
        val sn = rs.parse(sr, "User Information")
        val t = Template()
        t.setRuntimeServices(rs)
        t.setData(sn)
        t.initDocument()
        return t
    }









    }

private fun Template.merge(context: VelocityContext, k: MutableList<StringWriter>) {

}



























