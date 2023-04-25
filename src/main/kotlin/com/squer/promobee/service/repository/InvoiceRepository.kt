package com.squer.promobee.service.repository


import com.squer.promobee.api.v1.enums.TeamEnum
import com.squer.promobee.controller.dto.InvoiceDetailsPrintDTO
import com.squer.promobee.controller.dto.InvoiceHeaderDTO
import com.squer.promobee.controller.dto.InvoicePrintDetailsDTO
import com.squer.promobee.controller.dto.PrintInvoiceDTO
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.*
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.lang.Math.round
import java.time.ZoneId
import java.util.*


private fun String?.replace(s: String, srNo: Double?) {

}

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


    fun getHsnRate(hcmCode:String): HSN{
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var data : MutableMap<String , Any> = mutableMapOf()

        data.put("hcmCode",hcmCode)

        return sqlSessionFactory.openSession().selectOne("HSNMapper.getHsnRate",data)

    }



    fun printInvoice(inh:PrintInvoiceDTO):List<InvoicePrintDetailsDTO> {

        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User


        try {
            var data: MutableMap<String, Any> = mutableMapOf()

            var inh = PrintInvoiceDTO()
            var currDHO = DispatchDetailHo()
            var invoiceItemsPaging: Int? = 0

            var printDetails = inh?.invoiceHeaderId?.let { InvoicePrintDetailsDTO(it) }
            var invoice = inh.invoiceHeaderId?.let { getInvoiceHeaderById(it) }

            var doctor = Doctor()
            var doc = invoice?.recipientId.toString().also { doctor.id = it }

            var invoiceCutoffDate: String? = "invoiceCutoffDate"


            if (doc != null) {
                inh.invoiceHeaderId?.let { data.put("id", it) }

                var printDetails = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getVirtualPrintInvoiceHeaders", data)


            } else {

                var printDetails = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getPrintInvoiceHeaders", data)
            }

            if (printDetails?.employeeLRDate!! <= invoiceCutoffDate.toString()) {

                var invoicePrint: String? = null
                invoicePrint = ClassPathResource("src/main/resources/htmlPrint/print1.html").file.toString()
                var invoicePrintDetails = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getPrintInvoiceHeaders", data)
                var iHoUser = also { printDetails?.teamId = it.toString() }
//            if(ho!= null) true else false
                var ho: Boolean? = iHoUser.toString().toBoolean()

                if (ho == true) true else false

                val date = Date()
                val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val year = localDate.year
                val month = localDate.monthValue
                val day = localDate.dayOfMonth

                var employeePeriod = month.toString() + "-" + year
                var promoMonth = month.toString() + "-" + year

                var sampleValue: Double = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getDistrubutedValuesForPrintDetailSampleValue", data)

                var inputValue: Double = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getDistrubutedValuesForPrintDetailInputValue", data)

                var data0: MutableMap<String, Any> = mutableMapOf()

                printDetails.employeeCode?.let { data0.put("code", it) }

                var employeeEntitity =
                    return sqlSessionFactory.openSession().selectList("RecipientMapper.getLegalEntity", data0)



                printDetails?.employeeValue = sampleValue.plus(inputValue)

                printDetails?.employeeSampleValue = sampleValue
                printDetails?.employeeInputValue = inputValue

                invoicePrint =
                    printDetails?.employeeDesignation?.let { invoicePrint!!.replace("#EmployeeDesignation", it) }
                invoicePrint = printDetails?.invoiceNumber?.let { invoicePrint!!.replace("#InvoiceNumber", it) }
                invoicePrint = (if (ho == true) currDHO.recipientName else printDetails?.employeeName)?.let {
                    invoicePrint?.replace(
                        "#EmployeeName",
                        it
                    )
                }

                invoicePrint = (if (ho == true) currDHO.recipientCode else printDetails?.employeeCode)?.let {
                    invoicePrint?.replace(
                        "#EmployeeCode",
                        it
                    )
                }

                invoicePrint = printDetails?.employeeAddress?.let { invoicePrint?.replace("#EmployeeAddress", it) }
                invoicePrint = printDetails?.employeeCity?.let { invoicePrint?.replace("#EmployeeCity", it) }
                invoicePrint = printDetails?.employeeState?.let { invoicePrint?.replace("#EmployeeState", it) }
                invoicePrint = printDetails?.employeePinCode?.let { invoicePrint?.replace("#EmployeePinCode", it) }
                invoicePrint = printDetails?.employeeMobileNumber?.let { invoicePrint?.replace("#EmployeeMobileNumber", it) }
                invoicePrint = printDetails?.employeePeriod?.let { invoicePrint?.replace("#EmployeePeriod", it) }
                invoicePrint = printDetails?.employeeLRNumber?.let { invoicePrint?.replace("#EmployeeLRNumber", it) }
                invoicePrint = printDetails?.employeeDate?.let { invoicePrint?.replace("#EmployeeDate", it) }
                invoicePrint = printDetails?.employeeLRDate?.let { invoicePrint?.replace("#EmployeeLRDate", it) }
                invoicePrint = printDetails?.employeeTeam?.let { invoicePrint?.replace("#EmployeeTeam", it) }
                invoicePrint = printDetails?.employeeTransport?.let { invoicePrint?.replace("#EmployeeTransport", it) }
                invoicePrint = printDetails?.employeePermitNo?.let { invoicePrint?.replace("#EmployeePermitNo", it) }
                invoicePrint = printDetails?.employeeCFA?.let { invoicePrint?.replace("#EmployeeCFA", it) }

                invoicePrint = invoicePrint?.replace("#GSTnumber", employeeEntitity.toString()).toString()

                if (printDetails?.isSpecial == 1) {

                    invoicePrint = invoicePrint?.replace("#PROMOMONTH", promoMonth)
                    invoicePrint = invoicePrint?.replace("#PLANTYPE", "MONTHLY")


                } else {
                    invoicePrint = invoicePrint?.replace("#PROMOMONTH", promoMonth)
                    invoicePrint = invoicePrint?.replace("#PLANTYPE", "SPECIAL")

                }

                //EmployeeInvoiceDetails


                var srNo: Int? = 1
                var itemValue1: Double? = 0.0
                var sampleValue1: Double? = 0.0
                var finalInvoiceDetailsBodyToPrint: String? = ""

                var invoiceDetailTableBody: String? = null
                invoiceDetailTableBody =
                    ClassPathResource("src/main/resources/htmlPrint/printInvoiceDetails1.html").file.toString()

                var invoiceDetailsPrintList = InvoiceDetailsPrintDTO()


                var invoiceDetailsPrintList1 = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getPrintInvoiceDetails", data)


                var invoiceDids = emptyList<String>()

//            val item
//            foreach(item  invoiceDetailsPrintList1) {
                var temp = invoiceDetailTableBody

                temp = temp?.replace("#InvoiceDetailsSrNo", srNo = srNo!! + 1).toString()
                temp =
                    invoiceDetailsPrintList.invoiceDetailsProductCode?.let {
                        temp!!.replace(
                            "#InvoiceDetailsProductCode",
                            it
                        )
                    }

                temp =
                    invoiceDetailsPrintList.invoiceDetailsSAPCode?.let { temp?.replace("#InvoiceDetailsSAPCode", it) }
                temp = invoiceDetailsPrintList.invoiceDetailsItemDescription?.let {
                    temp?.replace(
                        "#InvoiceDetailsItemDescription",
                        it
                    )
                }
                temp =
                    invoiceDetailsPrintList.invoiceDetailsBatchNo?.let { temp?.replace("#InvoiceDetailsBatchNo", it) }
                temp = invoiceDetailsPrintList.invoiceDetailsExpiryDate?.let {
                    temp?.replace(
                        "#InvoiceDetailsExpiryDate",
                        it
                    )
                }
                temp =
                    temp?.replace("#InvoiceDetailsQuantity", invoiceDetailsPrintList?.invoiceDetailsQuantity).toString()

                if (invoiceDetailsPrintList.invoiceItemType == "S") {
                    sampleValue1 = sampleValue1!! + invoiceDetailsPrintList?.invoiceItemValue!!
                } else {
                    itemValue1 = itemValue1!! + invoiceDetailsPrintList?.invoiceItemValue!!
                }

                invoiceDids.add(temp)

                //FOOTER START

//            var invoiceDetailTableBody : String? = null
                var footerPrint = ClassPathResource("src/main/resources/htmlPrint/invoiceFooter.html").file.toString()

                footerPrint = footerPrint.replace("#EmployeeValue", printDetails?.employeeValue).toString()
                footerPrint = footerPrint.replace("#TotalSampleValue", sampleValue).toString()
                footerPrint = footerPrint.replace("#TotalInputValue", inputValue).toString()
                footerPrint = footerPrint.replace("#TotalSumValue", printDetails?.employeeValue).toString()
                footerPrint =
                    footerPrint.replace("#EmployeeTotalNoOfCases", printDetails?.employeeTotalNoOfCases).toString()
                footerPrint = footerPrint.replace("#EmployeeTotalWeight", printDetails?.employeeTotalWeight).toString()
                footerPrint = footerPrint.replace(
                    "#EmployeeRemark", if (ho == true) printDetails?.employeeRemark +
                            (currDHO.recipientName + "-" + currDHO.recipientCode), printDetails?.employeeRemark
                ).toString()


                //FOOTER END

                var tempInvoiceToShow = invoicePrint

                var i: Int? = 0;

                var finalInvoices: String? = "";





                for (i in 0 until invoiceItemsPaging!!) {

                    tempInvoiceToShow = invoicePrint

                    tempInvoiceToShow =
                        tempInvoiceToShow.replace("#EmployeeInvoiceDetails", invoiceItemsPaging).toString()
                    tempInvoiceToShow = tempInvoiceToShow.replace("#FooterSectionToAdd", footerPrint)
                    var finalInvoices = finalInvoices + tempInvoiceToShow;


                }


            } else {
                var ho: Boolean? = false

                var invoicePrint: String? = null
                invoicePrint = ClassPathResource("src/main/resources/htmlPrint/print1.html").file.toString()

                if (doc != null) {
                    ho = false;

                } else {
                    ho = printDetails.teamId?.equals(TeamEnum.DEFAULT_HO_TEAM)
                    if (ho == true) true else false


                }

                val date = Date()
                val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val year = localDate.year
                val month = localDate.monthValue
                val day = localDate.dayOfMonth

                var employeePeriod = month.toString() + "-" + year
                var promoMonth = month.toString() + "-" + year
                var dar: String = printDetails?.month?.plus(1).toString()


                if (printDetails?.isSpecial != 1) {
                    var sd: String? = dar
                    promoMonth = dar + "-" + year

                }

                var sampleValue: Double = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getDistrubutedValuesForPrintDetailSampleValue", data)

                var inputValue: Double = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getDistrubutedValuesForPrintDetailInputValue", data)


                var data0: MutableMap<String, Any> = mutableMapOf()

                printDetails?.employeeCode?.let { data0.put("code", it) }

                var employeeEntitity =
                    return sqlSessionFactory.openSession().selectList("RecipientMapper.getLegalEntity", data0)


                printDetails?.employeeValue = sampleValue.plus(inputValue)

                printDetails?.employeeSampleValue = sampleValue
                printDetails?.employeeInputValue = inputValue

                invoicePrint =
                    printDetails?.employeeDesignation?.let { invoicePrint!!.replace("#EmployeeDesignation", it) }
                invoicePrint = printDetails?.invoiceNumber?.let { invoicePrint!!.replace("#InvoiceNumber", it) }
                invoicePrint = (if (ho == true) currDHO.recipientName else printDetails?.employeeName)?.let {
                    invoicePrint?.replace(
                        "#EmployeeName",
                        it
                    )
                }

                invoicePrint = (if (ho == true) currDHO.recipientCode else printDetails?.employeeCode)?.let {
                    invoicePrint?.replace(
                        "#EmployeeCode",
                        it
                    )
                }

                invoicePrint = printDetails?.employeeAddress?.let { invoicePrint?.replace("#EmployeeAddress", it) }
                invoicePrint = printDetails?.employeeCity?.let { invoicePrint?.replace("#EmployeeCity", it) }
                invoicePrint = printDetails?.employeeState?.let { invoicePrint?.replace("#EmployeeState", it) }

                //get Employee State code

                var scm = StateCodeMaster()

                var scmState = scm.code

                var stCode: String? = "";
                var stateCode = listOf(scm.name.equals(printDetails.employeeState))

                for (i in stateCode) {
                    stCode = i.toString()

                }

                invoicePrint = stCode?.let { invoicePrint?.replace("#StateCodes", it) }
                invoicePrint = printDetails?.employeePinCode?.let { invoicePrint?.replace("#EmployeePinCode", it) }
                invoicePrint =
                    printDetails?.employeeMobileNumber?.let { invoicePrint?.replace("#EmployeeMobileNumber", it) }
                invoicePrint = printDetails?.employeePeriod?.let { invoicePrint?.replace("#EmployeePeriod", it) }
                invoicePrint = printDetails?.employeeLRNumber?.let { invoicePrint?.replace("#EmployeeLRNumber", it) }
                invoicePrint = printDetails?.employeeDate?.let { invoicePrint?.replace("#EmployeeDate", it) }
                invoicePrint = printDetails?.employeeLRDate?.let { invoicePrint?.replace("#EmployeeLRDate", it) }
                invoicePrint = printDetails?.employeeTeam?.let { invoicePrint?.replace("#EmployeeTeam", it) }
                invoicePrint = printDetails?.employeeTransport?.let { invoicePrint?.replace("#EmployeeTransport", it) }
                invoicePrint = printDetails?.employeePermitNo?.let { invoicePrint?.replace("#EmployeePermitNo", it) }
                invoicePrint = printDetails?.employeeCFA?.let { invoicePrint?.replace("#EmployeeCFA", it) }
                invoicePrint = invoicePrint?.replace("#GSTnumber", employeeEntitity.toString()).toString()
                invoicePrint = printDetails?.employeeTransport?.let { invoicePrint!!.replace("#COURIERNO", it) }
                invoicePrint = printDetails?.employeeLRDate?.let { invoicePrint?.replace("#InvoiceDate", it) }
                invoicePrint = printDetails?.invoiceNumber?.let { invoicePrint?.replace("#InvoiceNumber", it) }

                if (printDetails?.isSpecial == 1) {

                    invoicePrint = invoicePrint?.replace("#PROMOMONTH", promoMonth)
                    invoicePrint = invoicePrint?.replace("#PLANTYPE", "MONTHLY")


                } else {
                    invoicePrint = invoicePrint?.replace("#PROMOMONTH", promoMonth)
                    invoicePrint = invoicePrint?.replace("#PLANTYPE", "SPECIAL")

                }


                //EMPLOYEE INVOICE DETAILS

                var srNo: Int? = 1;
                var finalInvoiceDetailsBodyToPrint: String? = "";

                var invoiceDetailTableBody: String? = null
                invoiceDetailTableBody =
                    ClassPathResource("src/main/resources/htmlPrint/printInvoiceDetails1.html").file.toString()

                var invoiceDetailsPrintList = InvoiceDetailsPrintDTO()


                var invoiceDetailsPrintList1 = return sqlSessionFactory.openSession()
                    .selectList("InvoiceHeaderMapper.getPrintInvoiceDetails", data)

//            var invoiceItemsPaging : Int? = invoiceDetailsPrintList.Count/invoiceItemsToShowPerPage

                var itemValue1: Double? = 0.0
                var sampleValue2: Double? = 0.0
                var totaltaxvalue: Double? = 0.0
                var itemvalue2: Double? = 0.0

                var invoiceDids = emptyList<String>()

                var temp = invoiceDetailTableBody

                temp = temp?.replace("#InvoiceDetailsSrNo", srNo = srNo!! + 1).toString()
                temp =
                    invoiceDetailsPrintList.invoiceDetailsProductCode?.let {
                        temp!!.replace(
                            "#InvoiceDetailsProductCode",
                            it
                        )
                    }

                temp =
                    invoiceDetailsPrintList.invoiceDetailsSAPCode?.let { temp?.replace("#InvoiceDetailsSAPCode", it) }
                temp = invoiceDetailsPrintList.invoiceDetailsItemDescription?.let {
                    temp?.replace(
                        "#InvoiceDetailsItemDescription",
                        it
                    )
                }
                temp =
                    invoiceDetailsPrintList.invoiceDetailsBatchNo?.let { temp?.replace("#InvoiceDetailsBatchNo", it) }
                temp = invoiceDetailsPrintList.invoiceDetailsExpiryDate?.let {
                    temp?.replace(
                        "#InvoiceDetailsExpiryDate",
                        it
                    )
                }
                temp =
                    temp?.replace("#InvoiceDetailsQuantity", invoiceDetailsPrintList?.invoiceDetailsQuantity).toString()
                temp =
                    invoiceDetailsPrintList.invoiceDetailsHSNCode?.let { temp?.replace("#InvoiceDetailsHSNCode", it) }

                //GET THE RATE FROM HSN CODE

                var valv: Double? = 0.0
                var hcm = HSN()
                var rate = invoiceDetailsPrintList.invoiceDetailsHSNCode?.let { getHsnRate(it) }
//            var rate = hcm.hcmCode.equals(invoiceDetailsPrintList.invoiceDetailsHSNCode)
                for (ra in rate) valv = ra

                if (valv != 0.0) {
                    totaltaxvalue = 0.0
                    itemvalue2 = ((valv?.let { invoiceDetailsPrintList.invoiceItemValue?.times(it) })?.div(100))
                    totaltaxvalue = itemvalue2?.let { invoiceDetailsPrintList.invoiceItemValue?.plus(it) }
                    var terstr: String? = (invoiceDetailsPrintList.invoiceDetailsQuantity?.let {
                        invoiceDetailsPrintList.invoiceItemValue?.div(
                            it
                        )
                    }).toString()

                    var sdads = terstr

                    temp = sdads?.let { temp?.replace("#InvoiceDetailsRate", it) }
                    temp = temp?.replace("#InvoiceDetailsTaxableValue", invoiceDetailsPrintList.invoiceItemValue)
                        .toString()
                    if (stCode != "27") {
                        temp = temp.replace("#InvoiceDetailsCGSTRate", "0.00")
                        temp = temp.replace("#InvoiceDetailsCGST", "0.00")
                        temp = temp.replace("#InvoiceDetailsSGSTRate", "0.00")
                        temp = temp.replace("#InvoiceDetailsSGST", "0.00")
                        temp = temp?.replace("#InvoiceDetailsIGSTRate", valv).toString()
                        temp = temp?.replace("#InvoiceDetailsIGST", ((valv?.let {
                            invoiceDetailsPrintList.invoiceItemValue?.times(
                                it
                            )
                        })?.div(100))).toString()

                        temp = temp?.replace("#InvoiceDetailsTotalTaxAmount", ((valv?.let {
                            invoiceDetailsPrintList.invoiceItemValue?.times(
                                it
                            )
                        })?.div(100))).toString()

                        temp = temp?.replace("#InvoiceDetailsTotalAmountinRs", totaltaxvalue).toString()


                    } else {

                        var totaltaxvalue: Double? = 0.0

                        var itemvalue2 = ((valv?.let { invoiceDetailsPrintList.invoiceItemValue?.times(it) })?.div(100))
                        totaltaxvalue = itemvalue2?.let { invoiceDetailsPrintList.invoiceItemValue?.plus(it) }

                        temp = temp.replace("#InvoiceDetailsCGSTRate", valv?.div(2)).toString()
                        temp = temp.replace("#InvoiceDetailsCGST", itemvalue2?.div(2)).toString()
                        temp = temp.replace("#InvoiceDetailsSGSTRate", valv?.div(2)).toString()
                        temp = temp.replace("#InvoiceDetailsSGST", itemvalue2?.div(2)).toString()
                        temp = temp?.replace("#InvoiceDetailsIGSTRate", "0.00")
                        temp = temp?.replace("#InvoiceDetailsIGST", "0.00")
                        temp = temp?.replace("#InvoiceDetailsTotalTaxAmount", itemvalue2).toString()
                        temp = temp?.replace("#InvoiceDetailsTotalAmountinRs", totaltaxvalue).toString()

                    }

                } else {

                    totaltaxvalue = 0.00;
                    itemvalue2 = 0.00;
                    totaltaxvalue = invoiceDetailsPrintList.invoiceItemValue;
                    var terstr = (invoiceDetailsPrintList.invoiceDetailsQuantity?.let {
                        invoiceDetailsPrintList.invoiceItemValue?.div(
                            it
                        )
                    }).toString()

                    var sdads = terstr

                    temp = sdads?.let { temp?.replace("#InvoiceDetailsRate", it) }
                    temp = temp?.replace("#InvoiceDetailsTaxableValue", invoiceDetailsPrintList.invoiceItemValue)
                        .toString()

                    if (stCode != "27") {

                        temp = temp?.replace("#InvoiceDetailsCGSTRate", "0.00")
                        temp = temp?.replace("#InvoiceDetailsCGST", "0.00")
                        temp = temp?.replace("#InvoiceDetailsSGSTRate", "0.00")
                        temp = temp?.replace("#InvoiceDetailsSGST", "0.00")
                        temp = temp?.replace("#InvoiceDetailsIGSTRate", "0.00")
                        temp = temp?.replace("#InvoiceDetailsIGST", "0.00")
                        temp = temp?.replace("#InvoiceDetailsTotalTaxAmount", "0.00")
                        temp = temp?.replace("#InvoiceDetailsTotalAmountinRs", totaltaxvalue).toString()


                    } else {
                        totaltaxvalue = 0.00;
                        itemvalue2 = 0.00;
                        totaltaxvalue = itemvalue2?.let { invoiceDetailsPrintList.invoiceItemValue?.plus(it) }

                        temp = temp?.replace("#InvoiceDetailsCGSTRate", "0.00")
                        temp = temp?.replace("#InvoiceDetailsCGST", "0.00")
                        temp = temp?.replace("#InvoiceDetailsSGSTRate", "0.00")
                        temp = temp?.replace("#InvoiceDetailsSGST", "0.00")
                        temp = temp?.replace("#InvoiceDetailsIGSTRate", "0.00")
                        temp = temp?.replace("#InvoiceDetailsIGST", "0.00")
                        temp = temp?.replace("#InvoiceDetailsTotalTaxAmount", "0.00")
                        temp = temp?.replace("#InvoiceDetailsTotalAmountinRs", totaltaxvalue).toString()


                    }


                }

                if (invoiceDetailsPrintList.invoiceItemType == "S") {

                    sampleValue2 = sampleValue2!! + invoiceDetailsPrintList.invoiceItemValue!! + itemvalue2!!

                } else {
                    itemvalue2 = itemvalue2!! + invoiceDetailsPrintList.invoiceItemValue!! + itemvalue2!!
                }

                invoiceDids.add(temp)


                // FOOTER START

                var footerPrint = ClassPathResource("src/main/resources/htmlPrint/invoiceFooter.html").file.toString()

                footerPrint = footerPrint.replace("#TotalSampleValue", sampleValue2).toString()
                footerPrint = footerPrint.replace("#TotalInputValue", inputValue).toString()
                footerPrint = footerPrint.replace("#TotalSumValue", sampleValue2?.plus(inputValue)).toString()
                footerPrint =
                    footerPrint?.replace("#EmployeeTotalNoOfCases", printDetails.employeeTotalNoOfCases).toString()
                footerPrint = footerPrint.replace("#EmployeeTotalWeight", printDetails.employeeTotalWeight).toString()
                footerPrint = footerPrint.replace(
                    "#EmployeeRemark", if (ho == true) printDetails.employeeRemark +
                            (currDHO.recipientName + "-" + currDHO.recipientCode), printDetails.employeeRemark
                ).toString()

                //FOOTER END

                var tempInvoiceToShow = invoicePrint

                var i: Int? = 0;

                var finalInvoices: String? = "";





                for (i in 0 until invoiceItemsPaging!!) {

                    tempInvoiceToShow = invoicePrint

                    tempInvoiceToShow =
                        tempInvoiceToShow?.replace("#EmployeeInvoiceDetails", invoiceItemsPaging).toString()
                    tempInvoiceToShow = tempInvoiceToShow.replace("#FooterSectionToAdd", footerPrint)
                    finalInvoices = finalInvoices + tempInvoiceToShow;


                }


            }


            var finalInvoicingPrintHtml = ClassPathResource("src/main/resources/htmlPrint/finalPrint.html").file.toString()



//            finalInvoicingPrintHtml = finalInvoicingPrintHtml.replace("#MainPageBreakDiv",finalInvoices)

            val date = Date()
            val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val year = localDate.year
            val month = localDate.monthValue
            val day = localDate.dayOfMonth

            var fileName = "InvoicePrint_"+day+month+year

            var filePath = ("src/main/resources/tempLocation")+ "//"+ fileName + ".pdf";









        }catch (e:Exception) {


            println("Error Found !")

        }






















        return TODO("Provide the return value")
    }













    }

    private fun foreach(any: Any, function: () -> Unit) {

    }


    private fun <E> List<E>.add(temp: E) {

    }

    private fun Any.toDouble() {

    }

    private fun String.replace(s: String, srNo: Int?) {

    }




private operator fun HSN?.iterator(): Iterator<Double?> {

    return TODO("Provide the return value")
}

private fun String.replace(s: String, unit: Unit, employeeRemark: String?) {

}






