package com.squer.promobee.service.repository



import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
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
import java.io.ByteArrayOutputStream
import java.io.StringWriter
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

    fun getPrintInvoiceHeaders(inhId: String): MutableList<InvoicePrintDetailsDTO> {
        //val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        data.put("id", inhId)

        return sqlSessionFactory.openSession().selectList<InvoicePrintDetailsDTO>("InvoiceHeaderMapper.getPrintInvoiceHeaders", data).toMutableList()


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


        data.put("InhID", inhId)

        return sqlSessionFactory.openSession().selectList<InvoiceDetailsPrintDTO>("InvoiceHeaderMapper.getPrintInvoiceDetails", data).toMutableList()

    }


    fun getHsnRate(hcmCode: String): HSN {
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("hcmCode", hcmCode)

        return sqlSessionFactory.openSession().selectOne("HSNMapper.getHsnRate", data)

    }


    fun printInvoice(inh: List<PrintInvoiceDTO>):  MutableList<ByteArray>? {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()


        var printDetails =  mutableListOf<MutableList<InvoicePrintDetailsDTO>>()

        var printDetailsBody =  mutableListOf<MutableList<InvoiceDetailsPrintDTO>>()

        val date = Date()
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth

        var employeePeriod = localDate.month.toString() + "-" + year

        var promoMonth = localDate.month.toString() + "-" + year



        var finalArray = mutableListOf<ByteArray>()


      inh.forEach { i ->

          //var invoice = i.inhId?.let { getInvoiceHeaderById(i.inhId!!) }




          i.inhId?.let { getPrintInvoiceHeaders(i.inhId!!) }?.let { printDetails.add(it) }


          i.inhId?.let { getInvoiceDetailsForPrint(i.inhId!!) }?.let { printDetailsBody.add(it) }

          //var hoUser: Boolean = printDetails?.teamId?.equals(TeamEnum.DEFAULT_HO_TEAM.id) ?: true; false


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


          var i = 0

          var n = 0

          printDetails.forEach { pd ->

              pd.forEach {


                  context.put("InvoiceNumber", it.invoiceNumber)
                  context.put("EmployeeCode", it.employeeCode)
                  context.put("EmployeeDesignation", it.employeeDesignation)
                  context.put("EmployeeName", it.employeeName)
                  context.put("EmployeeAddress", it.employeeAddress)
                  context.put("EmployeeCity", it.employeeCity)
                  context.put("EmployeeState", it.employeeState)
                  context.put("EmployeePinCode", it.employeePinCode)
                  context.put("EmployeeMobileNumber", it.employeeMobileNumber)
                  context.put("EmployeePeriod", employeePeriod)
                  context.put("EmployeeLRNumber", it.employeeLRNumber)
                  context.put("EmployeeDate", it.employeeDate)
                  context.put("EmployeeLRDate", it.employeeLRDate)
                  context.put("EmployeeTeam", it.employeeTeam)
                  context.put("EmployeeTransport", it.employeeTransport)
                  context.put("EmployeeCFA", it.employeeCFA)
                  context.put("PROMOMONTH", promoMonth)
                  context.put("PLANTYPE", it.type)
                  context.put("EmployeeTotalNoOfCases", it.employeeTotalNoOfCases)
                  context.put("EmployeeTotalWeight", it.employeeTotalWeight)
                  if (it.employeeRemark !== null) {
                      context.put("EmployeeRemark", it.employeeRemark)
                  } else {
                      context.put("EmployeeRemark", "")
                  }
                  context.put("TotalSampleValue", it.employeeSampleValue)
                  context.put("TotalInputValue", it.employeeInputValue)
                  context.put("TotalSumValue", it.employeeValue?.roundToLong())

                  i++
              }

          }

          var tableRow = ""
          var srNo = 1
          var value: Double? = 0.00





          printDetailsBody.forEach {pb ->
              pb.forEach {

                  var taxableValue =
                      it.InvoiceDetailsRatePerUnit?.let { it1 -> it.invoiceDetailsQuantity?.times(it1) }?.roundToLong()
                  var gstAmount = it.InvoiceDetailsGSTRate?.let { it1 -> taxableValue?.times(it1) }?.div(100)?.roundToLong()
                  var amount = gstAmount?.let { it1 -> taxableValue?.plus(it1) }

                  tableRow = tableRow + "<tr>" +
                          "<td>" + srNo++ + "</td>" + "\n" + "\t" +
                          "<td >" + it.invoiceDetailsProductCode + "</td>" + "\n" + "\t" +




                          "<td colspan=\"2\">" + it.invoiceDetailsHSNCode + "</td>" + "\n" + "\t" +
                          "<td colspan=\"2\">" + it.invoiceDetailsItemDescription + "</td>" + "\n" + "\t" +
                          "<td>" + it.invoiceDetailsQuantity?.toInt() + "</td>" + "\n" + "\t" +
                          "<td colspan=\"2\">" + it.invoiceDetailsSAPCode + "</td>" + "\n" + "\t" +
                          "<td colspan=\"2\">" + if (it.invoiceDetailsBatchNo !== null) {
                      it.invoiceDetailsBatchNo
                  } else {
                      ""
                  } + "</td>" + "\n" + "\t" +
                          //                      "<td>" + it[i].invoiceDetailsExpiryDate + "</td>" + "\n" + "\t" +
                          "<td colspan=\"2\">" + it.invoiceDetailsExpiryDate + "</td>" + "\n" + "\t" +
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


          }

          context.put("tableRow", tableRow)

          val writer = StringWriter()
          t.merge(context, writer)

          System.out.println(writer.toString())
          val byteArrayOutputStream = ByteArrayOutputStream()

          try {

              val k = writer.toString()


              val document = Document()

              document.open()

              val paragraph = Paragraph(k)

              document.add(paragraph)

              HtmlConverter.convertToPdf(k, byteArrayOutputStream)


              document.close()
              finalArray.add(byteArrayOutputStream.toByteArray());


          } catch (e: Exception) {
              e.printStackTrace()
          }


      }
        return finalArray;


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
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


//
//
        var labelDetails = mutableListOf<MutableList<LabelPrintDetailsDTO>>()

//        inh.inhId?.let { data.put("id", it) }
//        inh.invoiceNo?.let { data.put("invoiceNo", it) }
//
//        var dataInh = data
        var i = 0
//
//        //var label = LabelPrintDetailsDTO()





//
//
//        dataInh.forEach {
//            println(it)
//        }

        //var ids: List<String> = dataInh.get("id") as List<String>
        inh.forEach {i ->

            //var i = 0

            var data: MutableMap<String, Any> = mutableMapOf()

            i.inhId?.let { data.put("id", it) }



            i.invoiceNo?.let { data.put("invoiceNo", it) }


            labelDetails.add(
                sqlSessionFactory.openSession()
                    .selectList<LabelPrintDetailsDTO>(
                        "InvoiceHeaderMapper.getLabelPrintDetailsByInvoices",
                        data
                    ).toMutableList()
            )


            //arrayOf(labelDetails)




        }

        labelDetails


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


//


        var n = 0

        i = 0

        var finalArray = mutableListOf<ByteArray>()
        labelDetails.forEach {

            var length = "";
            var breadth = "";
            var height = "";
            context.put("InvoiceNo", labelDetails[i].get(n).invoiceNo)
            context.put("TransporterName", labelDetails[i].get(n).transporterName)
            context.put("InvoiceLRNo", labelDetails[i].get(n).lRNumber)
            context.put("InvoiceCreatedDate", labelDetails[i].get(n).invoiceDate)
            context.put("RecipientCode", labelDetails[i].get(n).recipientCode)
            context.put("RecipientName", labelDetails[i].get(n).recipientName)
            context.put("RecipientTeam", labelDetails[i].get(n).teamName)
            context.put("RecipientDesgination", labelDetails[i].get(n).recipientDesgination)
            context.put("RecipientAddress", labelDetails[i].get(n).recipientAddress)
            context.put("RecipientCity", labelDetails[i].get(n).recipientCity)
            context.put("RecipientState", labelDetails[i].get(n).recipientState)
            context.put("RecipientPhone", labelDetails[i].get(n).recipientPhone)
            context.put("RecipientPinCode", labelDetails[i].get(n).recipientPinCode)
            context.put("RecipientHeadQuarter", labelDetails[i].get(n).recipientHeadQuarter)
            context.put("InvoiceBoxes", labelDetails[i].get(n).noOfBoxes)
            context.put("InvoiceWeight", labelDetails[i].get(n).weight)

            if (labelDetails[i].get(n).dimension !== null) {
                context.put("length", length)
                context.put("breadth", breadth)
                context.put("height", height)
            } else {
                context.put("length", length)
                context.put("breadth", breadth)
                context.put("height", height)
            }


            val writer = StringWriter()
            t.merge(context, writer)

            System.out.println(writer.toString())
            val byteArrayOutputStream = ByteArrayOutputStream()

            try {

                val k = writer.toString()


                val document = Document()


                document.open()

                val paragraph = Paragraph(k)

                document.add(paragraph)




                HtmlConverter.convertToPdf(k, byteArrayOutputStream)


                document.close()
                finalArray.add(byteArrayOutputStream.toByteArray());
                i++


            } catch (e: Exception) {
                e.printStackTrace()
            }
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


    fun getInventoryByIdForInvoicing(invId: String): MutableList<Inventory> {
        val user =
            (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id", invId)

        return sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.getInventoryByIdForInvoicing", data).toMutableList()


    }


        fun getRecipientItemCategoryCount(month: Int, year: Int, recipientId: String): MutableList<ItemCategoryCountDTO> {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("month", month)
            data.put("year", year)
            data.put("recipientId", recipientId)

            var i = 0

            var samplesCount =
                return sqlSessionFactory.openSession().selectList<ItemCategoryCountDTO>("InvoiceHeaderMapper.getSamplesCount", data).toMutableList()

            var data0: MutableMap<String, Any> = mutableMapOf()
            data0.put("month", month)
            data0.put("year", year)
            data0.put("recipientId", recipientId)

            var inputCount =
                return sqlSessionFactory.openSession().selectList<ItemCategoryCountDTO>("InvoiceHeaderMapper.getInputCount", data0).toMutableList()


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


    fun getDispatchDetailsForInvoicing(month: Int, year: Int, recipientId: String): MutableList<DispatchDetailDTO> {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("month", month)
            data.put("year", year)
            data.put("recipientId", recipientId)

            return sqlSessionFactory.openSession().selectList<DispatchDetailDTO>("DispatchDetailMapper.getDispatchDetails", data).toMutableList()


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


        fun generateInvoice(genInv: List<GenerateInvoiceDTO>) {
            val user =
                (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
            var data: MutableMap<String, Any> = mutableMapOf()

            var i: Int = 0

            genInv.forEach {

                it.recipientId?.let { it1 -> data.put("recipientId", it1) }
                it.boxes?.let { data.put("boxes", it) }
                it.weight?.let { data.put("weight", it) }
                it.transporter?.let { data.put("transporter", it) }
                it.lrNo?.let { data.put("lrNo", it) }
                it.dimension?.let { data.put("dimension", it) }
                it.month?.let { data.put("month", it) }
                it.year?.let { data.put("year", it) }
                it.isSpecial?.let { data.put("isSpecial", it) }


                var recipient = it.recipientId?.let { it1 -> getRecipientToGenerateInvoice(it1) }

                var itcCount = it.month?.let { it1 -> it.year?.let { it2 ->
                    it.recipientId?.let { it3 ->
                        getRecipientItemCategoryCount(it1,
                            it2, it3
                        )
                    }
                } }


                var dispatchDetails = it.month?.let { it1 -> it.year?.let { it2 ->
                    it.recipientId?.let { it3 ->
                        getDispatchDetailsForInvoicing(it1,
                            it2, it3
                        )
                    }
                } }


                // INVOICE HEADER INSERT

                i = 0

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
                it.weight?.let { data1.put("weight", it) }
                it.boxes?.let { data1.put("noOfBoxes", it) }
                it.transporter?.let { data1.put("transporterId", it) }
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

                genInv.get(i).lrNo?.let { data1.put("lrNumber", it) }
                data1.put("createdBy", user.id)
                data1.put("updatedBy", user.id)
                recipient?.designation?.let { it1 -> data1.put("designationId", it1.id) }
                recipient?.cfa?.let { it1 -> data1.put("cfa", it1) }

                sqlSessionFactory.openSession().insert("InvoiceHeaderMapper.insertGenerateInvoiceHeader", data1)


                var inventory: List<Inventory> = ArrayList()



                dispatchDetails?.forEach {

                    i < dispatchDetails!!.count()
//                    dispatchDetails = genInv.get(i).month?.let {
//                        genInv.get(i).year?.let { it1 ->
//                            genInv.get(i).recipientId?.let { it2 ->
//                                getDispatchDetailsForInvoicing(
//                                    it,
//                                    it1, it2
//                                )
//                            }
//                        }
//                    }







//                    listOf(dispatchDetails?.get(i)?.inventoryId?.let { it1 -> invoiceRepository.getInventoryByIdForInvoicing(it1) })!!.also {
//                        inventory =
//                            it as List<Inventory>
//                    }



                    inventory = dispatchDetails?.get(i)?.inventoryId?.let { it1 -> getInventoryByIdForInvoicing(it1) }!!


                    var samples = ItemCategoryEnum.SAMPLES
                    var itemId = ""

                    if (inventory[i].categoryId!!.equals(samples)) {

                        var smp = inventory[i].item?.let { it1 -> getSampleMasterById(it1.id) }
                        itemId = smp.toString()


                    } else {
                        var itm = inventory[i].item?.let { it1 -> getItemMasterById(it1.id) }

                        itemId = itm.toString()
                    }

                    // INVOICE DETAIL IND


                    var data2: MutableMap<String, Any> = mutableMapOf()

                    var ind = InvoiceDetail()

                    var indId = UUID.randomUUID().toString()

                    var value =
                        inventory[i].ratePerUnit?.let { it1 -> dispatchDetails?.get(i)!!.qtyDispatch!!.times(it1) }

                    data2.put("id", indId)
                    data2.put("headerId", inhId)
                    inventory[i].item?.let { it1 -> data2.put("item", it1.id) }
                    dispatchDetails?.get(i)?.qtyDispatch?.let { it1 -> data2.put("quantity", it1) }
                    dispatchDetails!![i]?.let { it1 -> it1.id?.let { it2 -> data2.put("didId", it2) } }
                    value?.let { it1 -> data2.put("value", it1.toDouble()) }
                    data2.put("createdBy", user.id)
                    data2.put("updatedBy", user.id)
                    data2.put("inventoryId", inventory[i].id)
                    inventory[i].hsnCode?.let { it1 -> data2.put("hsnCode", it1) }
                    inventory[i].rate?.let { it1 -> data2.put("rate", it1) }

                    sqlSessionFactory.openSession().insert("InvoiceDetailMapper.insertGenerateInvoiceDetail", data2)


                    // DISPATCH DETAIL UPDATE


                    var data3: MutableMap<String, Any> = mutableMapOf()

                    data3.put("detailStatus", DispatchDetailStatusEnum.INVOICED.id)
                    dispatchDetails?.get(i)?.recipientId?.let { it1 -> data3.put("recipientId", it1) }
                    data3.put("updatedBy", user.id)

                    sqlSessionFactory.openSession()
                        .update("DispatchDetailMapper.editDispatchDetailsForInvoicing", data3)


                    var data4: MutableMap<String, Any> = mutableMapOf()

                    var qtyDisp = dispatchDetails?.get(i)?.let { it1 ->
                        it1.qtyDispatch?.let { it2 ->
                            inventory[i].qtyDispatched?.plus(
                                it2
                            )
                        }
                    }


                    var qtyAlloc = dispatchDetails?.get(i)?.let { it1 ->
                        it1.qtyDispatch?.let { it2 ->
                            inventory[i].qtyAllocated?.minus(
                                it2
                            )
                        }
                    }

                    qtyDisp?.let { it1 -> data4.put("qtyDispatched", it1.toInt()) }
                    qtyAlloc?.let { it1 -> data4.put("qtyAllocated", it1.toInt()) }
                    data4.put("updatedBy", user.id)
                    data4.put("id", inventory[i].id)


                    sqlSessionFactory.openSession().update("InventoryMapper.generateInvoiceUpdate", data4)





                }


                var dispatchPlan = dispatchDetails?.get(i)?.planId?.let { getDispatchPlanById(it) }


                var allocatedCount: Int = genInv.get(i).month?.let {
                    genInv.get(i).year?.let { it1 ->
                        genInv.get(i).recipientId?.let { it2 ->
                            getDispatchDetailsForInvoicing(
                                it,
                                it1, it2
                            )
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

                i++

                //return System.out.println("Invoice Generated successfully !")




            }

            return System.out.println("Invoice Generated successfully !")

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








    }



















