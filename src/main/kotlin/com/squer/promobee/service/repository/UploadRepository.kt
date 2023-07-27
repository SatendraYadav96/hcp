package com.squer.promobee.service.repository


import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.squer.promobee.api.v1.enums.UploadStatusEnum
import com.squer.promobee.api.v1.enums.UploadTypeEnum
import com.squer.promobee.controller.dto.*
import com.squer.promobee.persistence.BaseRepository
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.util.SecurityUtility
import com.squer.promobee.service.repository.domain.UploadLog
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import java.io.File
import java.io.FileNotFoundException
import java.lang.constant.ConstantDescs.NULL
import java.time.LocalDate
import java.util.*


@Repository
class UploadRepository(
    securityUtility: SecurityUtility,
): BaseRepository<UploadLog>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    @Value("C:\\upload\\transporter")
    private lateinit var configPath: String



    fun getGrnUploadLog (): List<UploadLogDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()




        return sqlSessionFactory.openSession().selectList("UploadLogMapper.getGrnUploadLog", data)
    }


    fun getTransporterUploadLog (): List<UploadLogDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("UploadLogMapper.getTransporterUploadLog", data)
    }

    fun getInvoiceUploadLog (): List<UploadLogDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()


        return sqlSessionFactory.openSession().selectList("UploadLogMapper.getInvoiceUploadLog", data)
    }

    fun getAllUploadLog(typeId: String): List<UploadLogDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("typeId",typeId)


        return sqlSessionFactory.openSession().selectList("UploadLogMapper.getAllUploadLog", data)
    }


    fun transporterUpload(dto: FileUploadDto) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        //val filePath = "${configPath}/deliveryUpload/${System.currentTimeMillis()}${dto.fileName}"
        val filePath = "${configPath}/deliveryUpload/${dto.fileName}"
        //File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.substring(dto.byteCode.indexOf(";base64,") + ";base64,".length)))
        File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.toString()))

        var counter = 0

        val validHeader: Boolean = true

        val line  = ""

        val successCount = 0

        var upl = UploadLog()

        var uplId =  UUID.randomUUID().toString()

        if(dto.byteCode.isEmpty() || dto.byteCode.isBlank()){
            data.put("id",uplId)
            data.put("type",UploadTypeEnum.TRANSPORT_DETAILS.id)
            data.put("totalRecord",counter)
            data.put("recordUpload",counter)
            data.put("statusId",UploadStatusEnum.FILE_NOT_FOUND.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)

            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogFileNotFound", data)
        }

        else{
            data.put("id",uplId)
            data.put("type",UploadTypeEnum.TRANSPORT_DETAILS.id)
            data.put("recordUpload",counter)
            data.put("statusId",UploadStatusEnum.QUEUED.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)


            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogQueued", data)
        }






        var headers = mutableListOf<String>("Invoice No", "LR No","Box","Weight", "Dispatch Date", "Expected Delivery Date", "Actual Delivery Date", "Transporter",
            "Delivered To Name", "Cost", "Docket Status")

       var csvReader = CsvReader()
       csvReader.autoRenameDuplicateHeaders
        var rows = CsvReader().readAllWithHeader(File(filePath))



        var i = 0

            rows.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                var dto = TransporterUploadDto()

                data.put("uploadId",uplId)
                data.put("createdBy",user.id)



                data.put("transportId",UUID.randomUUID().toString())
                data.put("transportUploadId",uplId)
                data.put("invoiceNo",it.get(headers[0]).toString().trim())
//                var inh = invoiceRepository.getInvoice(it.get(headers[0])!!.toInt())
//                data.put("invoiceHeaderId",inh.id)
                data.put("lrNo",it.get(headers[1]).toString().trim())
                data.put("boxNo",it.get(headers[2]).toString().trim())
                data.put("weight",it.get(headers[3]).toString().trim())
                data.put("dispatchDate",it.get(headers[4]).toString().trim())
                data.put("expectedDeliveryDate",it.get(headers[5]).toString().trim())
                data.put("actuallyDeliveryDate",it.get(headers[6]).toString().trim())
                data.put("trnName",it.get(headers[7]).toString().trim())
//                var trn = invoiceRepository.getTransporter(it.get(headers[7]).toString().trim())
//                data.put("trnId",trn.id)
                data.put("deliveredToName",it.get(headers[8]).toString().trim())
                data.put("deliveryCost",it.get(headers[9]).toString().trim())
                data.put("docketState",it.get(headers[10]).toString().trim())
//                var docket = invoiceRepository.getDocket(it.get(headers[10]).toString().trim())
//                docket.docketId?.let { it1 -> data.put("docketId", it1) }





                sqlSessionFactory.openSession().insert("UploadLogMapper.insertTempTransportDetails", data)

//                i++


            }



        data.put("uploadId",uplId)
        data.put("createdBy",user.id)

         sqlSessionFactory.openSession().update("UploadLogMapper.uploadTransportDetails", data)


    }


    fun transportExcelData (uplId : String): List<TransporterUploadDto> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",uplId)


        return sqlSessionFactory.openSession().selectList("UploadLogMapper.transportExcelData", data)
    }



    fun grnUpload(dto: FileUploadDto) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        //val filePath = "${configPath}/deliveryUpload/${System.currentTimeMillis()}${dto.fileName}"
        val filePath = "${configPath}/grnUpload/${dto.fileName}"
        //File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.substring(dto.byteCode.indexOf(";base64,") + ";base64,".length)))
        File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.toString()))

        var counter = 0

        val validHeader: Boolean = true

        var upl = UploadLog()

        var uplId =  UUID.randomUUID().toString()

        if(dto.byteCode.isEmpty() || dto.byteCode.isBlank()){
            data.put("id",uplId)
            data.put("type",UploadTypeEnum.GRN.id)
            data.put("totalRecord",counter)
            data.put("recordUpload",counter)
            data.put("statusId",UploadStatusEnum.FILE_NOT_FOUND.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)

            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogFileNotFound", data)
        }

        else{
            data.put("id",uplId)
            data.put("type",UploadTypeEnum.GRN.id)
            data.put("recordUpload",counter)
            data.put("statusId",UploadStatusEnum.QUEUED.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)


            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogQueued", data)
        }






        var headers = mutableListOf<String>("PO", "Cost Ctr", "Material", "Batch", "Material Description",
            "Pstng Date", "Qty in UnE", "Amount in LC", "Vendor Code", "Vendor Name", "Rate",
            "Medical Code", "Mat.Doc.", "Item", "Sample Expiry","Expiry Date")

        var csvReader = CsvReader()
        csvReader.autoRenameDuplicateHeaders
        var rows = CsvReader().readAllWithHeader(File(filePath))



        var i = 0

        rows.forEach {
            var data: MutableMap<String, Any> = mutableMapOf()

            var dto = GrnUploadDTO()

            var empty = NULL

            data.put("grnId",UUID.randomUUID().toString())
            data.put("grnUploadId",uplId)
            data.put("poNo",it.get(headers[0]).toString().trim())
            data.put("costCenter",it.get(headers[1]).toString().trim())
            data.put("material",it.get(headers[2]).toString().trim())
            data.put("batchNo",it.get(headers[3]).toString().trim())
            data.put("materialDescription",it.get(headers[4]).toString().trim())
            data.put("postingDate",it.get(headers[5]).toString().trim())
            data.put("quantity",it.get(headers[6]).toString().trim())
            data.put("amount",it.get(headers[7]).toString().trim())
            data.put("vendorCode",it.get(headers[8]).toString().trim())
            data.put("vendorName",it.get(headers[9]).toString().trim())
            data.put("ratePerUnit",it.get(headers[10]).toString().trim())
                data.put("medicalCode",it.get(headers[11]).toString().trim())


            data.put("itemNo",it.get(headers[13]).toString().trim())
            data.put("sampleExpiry",it.get(headers[14]).toString().trim())
            data.put("expiryDate",it.get(headers[15]).toString().trim())


            sqlSessionFactory.openSession().insert("UploadLogMapper.insertTempGrnDetails", data)


        }


        data.put("uploadid",uplId)
        data.put("createdBy",user.id)

        sqlSessionFactory.openSession().update("UploadLogMapper.uploadGrnDetails", data)


    }



    fun grnExcelData(uplId: String): List<GrnUploadDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",uplId)


        return sqlSessionFactory.openSession().selectList("UploadLogMapper.grnExcelData", data)
    }





    fun recipientUpload(dto: FileUploadDto) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        val filePath = "${configPath}/recipientUpload/${dto.fileName}"
        File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.toString()))

        var counter = 0

        val validHeader: Boolean = true

        var upl = UploadLog()

        var uplId =  UUID.randomUUID().toString()

        if(dto.byteCode.isEmpty() || dto.byteCode.isBlank()){
            data.put("id",uplId)
            data.put("type",UploadTypeEnum.RECIPIENT.id)
            data.put("totalRecord",counter)
            data.put("recordUpload",counter)
            data.put("statusId",UploadStatusEnum.FILE_NOT_FOUND.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)

            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogFileNotFound", data)
        }

        else{
            data.put("id",uplId)
            data.put("type",UploadTypeEnum.RECIPIENT.id)
            data.put("recordUpload",counter)
            data.put("statusId",UploadStatusEnum.QUEUED.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)


            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogQueued", data)
        }






        var headers = mutableListOf<String>("Employee Code","Employee Name","Address","City","Role","State","Zip","Zone","Employee Workday id","Gender","DOJ (DD/MM/YYYY)",
            "Mobile Number","Email Address","Team","Sub Team","AM Email","RBM Email","HQ","Status","Remark")

        var csvReader = CsvReader()
        csvReader.autoRenameDuplicateHeaders
        var rows = CsvReader().readAllWithHeader(File(filePath))



        var i = 0

        rows.forEach {
            var data: MutableMap<String, Any> = mutableMapOf()

            var dto = RecipientUploadDTO()

            var empty = NULL

            data.put("recipientId",UUID.randomUUID().toString())
            data.put("recipientUploadId",uplId)
            data.put("code",it.get(headers[0]).toString().trim())
            data.put("name",it.get(headers[1]).toString().trim())
            data.put("address",it.get(headers[2]).toString().trim())
            data.put("city",it.get(headers[3]).toString().trim())
            data.put("designation",it.get(headers[4]).toString().trim())
            data.put("state",it.get(headers[5]).toString().trim())
            data.put("zip",it.get(headers[6]).toString().trim())
            data.put("zone",it.get(headers[7]).toString().trim())
            data.put("workId",it.get(headers[8]).toString().trim())
            data.put("gender",it.get(headers[9]).toString().trim())
            data.put("joiningDate",it.get(headers[10]).toString().trim())
            data.put("mobile",it.get(headers[11]).toString().trim())
            data.put("email",it.get(headers[12]).toString().trim())
            data.put("bu",it.get(headers[13]).toString().trim())
            data.put("team",it.get(headers[14]).toString().trim())
            data.put("emailAM",it.get(headers[15]).toString().trim())
            data.put("emailRM",it.get(headers[16]).toString().trim())
            data.put("headquarter",it.get(headers[17]).toString().trim())
            data.put("status",it.get(headers[18]).toString().trim())
            data.put("remarks",it.get(headers[19]).toString().trim())


            sqlSessionFactory.openSession().insert("UploadLogMapper.insertTempRecipientDetails", data)


        }


        data.put("uploadid",uplId)
        data.put("createdBy",user.id)

        sqlSessionFactory.openSession().update("UploadLogMapper.uploadRecipientDetails", data)


    }




    fun recipientExcelData(uplId: String): List<RecipientUploadDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",uplId)


        return sqlSessionFactory.openSession().selectList("UploadLogMapper.recipientExcelData", data)
    }


    fun virtualUpload(dto: FileUploadDto) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        val filePath = "${configPath}/virtualUpload/${dto.fileName}"
        File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.toString()))

        var counter = 0

        val validHeader: Boolean = true

        var upl = UploadLog()

        var uplId =  UUID.randomUUID().toString()

        if(dto.byteCode.isEmpty() || dto.byteCode.isBlank()){
            data.put("id",uplId)
            data.put("type",UploadTypeEnum.VIRTUAL_SAMPLES.id)
            data.put("totalRecord",counter)
            data.put("recordUpload",counter)
            data.put("statusId",UploadStatusEnum.FILE_NOT_FOUND.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)

            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogFileNotFound", data)
        }

        else{
            data.put("id",uplId)
            data.put("type",UploadTypeEnum.VIRTUAL_SAMPLES.id)
            data.put("recordUpload",counter)
            data.put("statusId",UploadStatusEnum.QUEUED.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)


            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogQueued", data)
        }






        var headers = mutableListOf<String>("Created By","User Email","User Position","Territory","Emp ID","SKU","Lot","External Id",
            "Customer","Mobile Phone","Quantity","Date Created","Request Completed","Request Started","Request Status","Team","Sub Team","Address","Street 1",
            "Street 2","Street 3","City","State/Province","Postal Code")

        var csvReader = CsvReader()
        csvReader.autoRenameDuplicateHeaders
        var rows = CsvReader().readAllWithHeader(File(filePath))



        var i = 0

        rows.forEach {
            var data: MutableMap<String, Any> = mutableMapOf()

            var dto = VirtualSampleUploadDTO()

            var empty = NULL

            data.put("virtualId",UUID.randomUUID().toString())
            data.put("virtualUploadId",uplId)
            data.put("createdBy",it.get(headers[0]).toString().trim())
            data.put("userEmail",it.get(headers[1]).toString().trim())
            data.put("userPosition",it.get(headers[2]).toString().trim())
            data.put("territory",it.get(headers[3]).toString().trim())
            data.put("empId",it.get(headers[4]).toString().trim())
            data.put("sku",it.get(headers[5]).toString().trim())
            data.put("lot",it.get(headers[6]).toString().trim())
            data.put("externalId",it.get(headers[7]).toString().trim())
            data.put("customer",it.get(headers[8]).toString().trim())
            data.put("mobile",it.get(headers[9]).toString().trim())
            data.put("quantity",it.get(headers[10]).toString().trim())
            data.put("dateCreated",it.get(headers[11]).toString().trim())
            data.put("requestCompleted",it.get(headers[12]).toString().trim())
            data.put("requestStarted",it.get(headers[13]).toString().trim())
            data.put("requestStatus",it.get(headers[14]).toString().trim())
            data.put("team",it.get(headers[15]).toString().trim())
            data.put("subTeam",it.get(headers[16]).toString().trim())
            data.put("address",it.get(headers[17]).toString().trim())
            data.put("street1",it.get(headers[18]).toString().trim())
            data.put("street2",it.get(headers[19]).toString().trim())
            data.put("street3",it.get(headers[20]).toString().trim())
            data.put("city",it.get(headers[21]).toString().trim())
            data.put("state",it.get(headers[22]).toString().trim())
            data.put("postalCode",it.get(headers[23]).toString().trim())




            sqlSessionFactory.openSession().insert("UploadLogMapper.insertTempVirtualSampleDetails", data)


        }

        val currentDate: LocalDate = LocalDate.now()

        var month: Int = currentDate.getMonth().value

        var fromMonth: Int = currentDate.getMonth().value
        var toMonth: Int = currentDate.getMonth().value

        if(month == 1 || month == 2 || month == 3) {
            fromMonth = 1
            toMonth =  3

        }

        if(month == 4 || month == 5 || month == 6) {
            fromMonth = 4
            toMonth =  6

        }

        if(month == 7 || month == 8 || month == 9) {
            fromMonth = 7
            toMonth =  9

        }

        if(month == 10 || month == 11 || month == 12) {
            fromMonth = 10
            toMonth =  12

        }


        data.put("uploadid",uplId)
        data.put("createdBy",user.id)
        data.put("frommonth",fromMonth)
        data.put("tomonth",toMonth)

        sqlSessionFactory.openSession().update("UploadLogMapper.uploadVirtualSampleDetails", data)


    }




    fun virtualSampleExcelData(uplId: String): List<VirtualSampleUploadDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",uplId)


        return sqlSessionFactory.openSession().selectList("UploadLogMapper.virtualSampleExcelData", data)
    }

    fun getRecipientByCode(code: String): RecipientDTO {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("code",code)


        return sqlSessionFactory.openSession().selectOne("UploadLogMapper.getRecipientByCode", data)
    }


    fun getTransporterByName(name: String): TransporterDropdownDTO {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("name",name)


        return sqlSessionFactory.openSession().selectOne("UploadLogMapper.getTransporterByName", data)
    }


    fun getDoctorsByCode(code: String): RecipientDTO {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("code",code)


        return sqlSessionFactory.openSession().selectOne("UploadLogMapper.getDoctorsByCode", data)
    }



    fun invoiceUpload(dto: FileUploadDto) {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        val filePath = "${configPath}/invoiceUpload/${dto.fileName}"
        File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.toString()))

        var counter = 0

        val validHeader: Boolean = true

        var upl = UploadLog()

        var uplId =  UUID.randomUUID().toString()

        try {

            if (dto.byteCode.isEmpty() || dto.byteCode.isBlank()) {
                data.put("id", uplId)
                data.put("type", UploadTypeEnum.INVOICE_DETAILS.id)
                data.put("totalRecord", counter)
                data.put("recordUpload", counter)
                data.put("statusId", UploadStatusEnum.FILE_NOT_FOUND.id)
                data.put("createdBy", user.id)
                data.put("updatedBy", user.id)
                data.put("parentId", uplId)

                sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogFileNotFound", data)


            } else {
                data.put("id", uplId)
                data.put("type", UploadTypeEnum.INVOICE_DETAILS.id)
                data.put("recordUpload", counter)
                data.put("statusId", UploadStatusEnum.QUEUED.id)
                data.put("createdBy", user.id)
                data.put("updatedBy", user.id)
                data.put("parentId", uplId)


                sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogQueued", data)


            }
        }
        catch(e:FileNotFoundException){
            println("File not Found !")
        }






        var headers = mutableListOf<String>("Month", "Year", "Plan Name", "State", "Employee", "Designation", "Code", "Boxes", "Weight","Dimension",
            "Transporter", "LR Nov", "PlanId", "Plan","FFCode")

        var csvReader = CsvReader()
        csvReader.autoRenameDuplicateHeaders
        var rows = CsvReader().readAllWithHeader(File(filePath))







        rows.forEach {
                var data: MutableMap<String, Any> = mutableMapOf()

                var dto = InvoiceUploadDTO()

                var empty = NULL



                dto.invoiceId = UUID.randomUUID().toString()
                dto.invoiceUploadId = uplId
                dto.month = it.get(headers[0]).toString().trim()
                dto.year = it.get(headers[1]).toString().trim()
                dto.planName = it.get(headers[2]).toString().trim()
                dto.state = it.get(headers[3]).toString().trim()
                dto.employeeName = it.get(headers[4]).toString().trim()
                dto.employeeDesignation = it.get(headers[5]).toString().trim()
                dto.employeeCode = it.get(headers[6]).toString().trim()
                dto.boxes = it.get(headers[7]).toString().trim()
                dto.weight = it.get(headers[8]).toString().trim()
                dto.dimension = it.get(headers[9]).toString().trim()
                dto.transporterName = it.get(headers[10]).toString().trim()
                dto.lrNo = it.get(headers[11]).toString().trim()
                dto.planId = it.get(headers[12]).toString().trim()
                dto.planType = it.get(headers[13]).toString().trim()
                dto.ffCode = it.get(headers[14]).toString().trim()




                if (dto.month.isNullOrEmpty()) {
                    dto.errorText = "Month field cannot be empty."
                   // throw error("Month field cannot be empty.")
                }
                if (dto.year.isNullOrEmpty()) {
                    dto.errorText = "Year field cannot be empty."
                    //throw error("Year field cannot be empty.")
                }

                if (dto.planType == "VIRTUAL") {
                    var doctors = getDoctorsByCode(it.get(headers[6]).toString().trim())

                    if (doctors == null) {
                        dto.errorText = "Invalid Employee code entered.|"
                       // throw error("Invalid Doctor code entered.|")
                    } else {
                        dto.employeeId = doctors.recipientId
                    }

                } else {
                    var recp = getRecipientByCode(it.get(headers[6]).toString().trim())
                    if (recp == null) {
                        dto.errorText = "Invalid Employee code entered.|"
                        //throw error("Invalid Employee code entered.|")

                    } else {
                        dto.employeeId = recp.recipientId
                    }
                }

                var transporter = getTransporterByName(it.get(headers[10]).toString().trim())
                if (transporter == null) {
                    dto.errorText = " Invalid Transporter entered."
                   // throw error("Invalid Transporter entered.")
                } else {
                    dto.transporterId = transporter.transporterId
                }

                if (dto.boxes!! <= "0" || dto.boxes.isNullOrEmpty()) {
                    dto.errorText = " Boxes cannot be less than 0."
                   // throw error("Boxes cannot be less than 0.")

                }

                if (dto.weight!! <= "0" || dto.weight.isNullOrEmpty()) {
                    dto.errorText = " Weight cannot be less than 0."
                   // throw error("Weight cannot be less than 0.")

                }

                if (dto.dimension.isNullOrEmpty()) {
                    dto.errorText = "Dimension field cannot be empty."
                   // throw error("Dimension field cannot be empty.")
                }

                if(dto.lrNo.isNullOrEmpty()){
                    dto.errorText = "LR No cannot be empty."
                    //throw error("LR No cannot be empty.")
                }




                var isInvoiceAlreadyGenerated = false

                var invoiceCount = 0

                var isSpecial = 0

                    if(dto.errorText.isNullOrEmpty()) {

                        if (dto.planType == "MONTHLY") {

                            isSpecial = 0

                            var data: MutableMap<String, Any> = mutableMapOf()

                            dto.employeeId?.let { it1 -> data.put("employeeId", it1) }
                            dto.month?.let { it1 -> data.put("month", it1) }
                            dto.year?.let { it1 -> data.put("year", it1) }

                            invoiceCount = sqlSessionFactory.openSession()
                                .selectOne("UploadLogMapper.isInvoiceGeneratedForTheRecipient", data)

                            if (invoiceCount > 0) {
                                isInvoiceAlreadyGenerated = true
                                dto.errorText = "Invoice already generated"

                                //throw error("Invoice already generated")
                            }

                        } else if (dto.planType == "VIRTUAL") {

                            isSpecial = 1

                            var data: MutableMap<String, Any> = mutableMapOf()

                            dto.employeeId?.let { it1 -> data.put("employeeId", it1) }
                            dto.month?.let { it1 -> data.put("month", it1) }
                            dto.year?.let { it1 -> data.put("year", it1) }
                            dto.planId?.let { it1 -> data.put("planId", it1) }
                            dto.ffCode?.let { it1 -> data.put("ffCode", it1) }

                            invoiceCount = sqlSessionFactory.openSession()
                                .selectOne("UploadLogMapper.isInvoiceGeneratedForTheRecipientVirtual", data)

                            if (invoiceCount > 0) {
                                isInvoiceAlreadyGenerated = true
                                dto.errorText = "Invoice already generated"

                                //throw error("Invoice already generated")
                            }


                        } else {
                            var data: MutableMap<String, Any> = mutableMapOf()

                            isSpecial = 1

                            dto.employeeId?.let { it1 -> data.put("employeeId", it1) }
                            dto.month?.let { it1 -> data.put("month", it1) }
                            dto.year?.let { it1 -> data.put("year", it1) }
                            dto.planId?.let { it1 -> data.put("planId", it1) }

                            invoiceCount = sqlSessionFactory.openSession()
                                .selectOne("UploadLogMapper.isInvoiceGeneratedForTheRecipientSpecial", data)

                            if (invoiceCount > 0) {
                                isInvoiceAlreadyGenerated = true
                                dto.errorText = "Invoice already generated"

                                //throw error("Invoice already generated")


                            }


                        }

                    }

                if (dto.errorText.isNullOrEmpty()) {

                    var data: MutableMap<String, Any> = mutableMapOf()

                    data.put("invoiceId", dto.invoiceId!!)
                    data.put("invoiceUploadId", dto.invoiceUploadId!!)
                    data.put("month", dto.month!!)
                    data.put("year", dto.year!!)
                    data.put("state", dto.state!!)
                    data.put("employeeName", dto.employeeName!!)
                    data.put("employeeCode", dto.employeeCode!!)
                    data.put("boxes", dto.boxes!!)
                    data.put("weight", dto.weight!!)
                    data.put("transporterName", dto.transporterName!!)
                    data.put("lrNo", dto.lrNo!!)
                    data.put("employeeId", dto.employeeId!!)
                    data.put("transporterId", dto.transporterId!!)
                    data.put("planName", dto.planName!!)
                    data.put("planId", dto.planId!!)
                    if (dto.errorText.isNullOrEmpty()) {
                        data.put("errorText", "")
                    } else {
                        data.put("errorText", dto.errorText!!)
                    }

                    data.put("employeeDesignation", dto.employeeDesignation!!)
                    data.put("dimension", dto.dimension!!)
                    data.put("ffCode", dto.ffCode!!)


                    sqlSessionFactory.openSession().insert("UploadLogMapper.insertTempInvoiceDetails", data)


                } else {

                    var data: MutableMap<String, Any> = mutableMapOf()

                    data.put("invoiceId", dto.invoiceId!!)
                    data.put("invoiceUploadId", dto.invoiceUploadId!!)
                    data.put("month", dto.month!!)
                    data.put("year", dto.year!!)
                    data.put("state", dto.state!!)
                    data.put("employeeName", dto.employeeName!!)
                    data.put("employeeCode", dto.employeeCode!!)
                    data.put("boxes", dto.boxes!!)
                    data.put("weight", dto.weight!!)
                    data.put("transporterName", dto.transporterName!!)
                    data.put("lrNo", dto.lrNo!!)
                    data.put("employeeId", dto.employeeId!!)
                    data.put("transporterId", dto.transporterId!!)
                    data.put("planName", dto.planName!!)
                    data.put("planId", dto.planId!!)
                    data.put("errorText", dto.errorText!!)
                    data.put("employeeDesignation", dto.employeeDesignation!!)
                    data.put("dimension", dto.dimension!!)
                    data.put("ffCode", dto.ffCode!!)


                    sqlSessionFactory.openSession().insert("UploadLogMapper.insertTempInvoiceDetails", data)
                }

                var i = 0
                if (dto.errorText.isNullOrEmpty()) {


                    var itcCount = ItemCategoryCountDTO()


                    var samplesCount: Double?

                    var inputsCount: Double?


                    if (dto.planType == "MONTHLY") {

                        var data: MutableMap<String, Any> = mutableMapOf()


                        data.put("month", dto.month!!)
                        data.put("year", dto.year!!)
                        data.put("recipientId", dto.employeeId!!)
                        data.put("IsSpecialDispatch", 0)


                        samplesCount =
                            sqlSessionFactory.openSession().selectOne("UploadLogMapper.getSamplesCount", data)

                        data.put("month", dto.month!!)
                        data.put("year", dto.year!!)
                        data.put("recipientId", dto.employeeId!!)
                        data.put("IsSpecialDispatch", 0)


                        inputsCount = sqlSessionFactory.openSession().selectOne("UploadLogMapper.getInputCount", data)


                        itcCount.sampleItems = samplesCount
                        itcCount.nonSampleItems = inputsCount

                        itcCount;


                        if (itcCount.sampleItems == null) {
                            itcCount.sampleItems = 0.0;
                        }
                        if (itcCount.nonSampleItems == null) {
                            itcCount.nonSampleItems = 0.0;
                        }


                    } else {


                        var data: MutableMap<String, Any> = mutableMapOf()


                        data.put("month", dto.month!!)
                        data.put("year", dto.year!!)
                        data.put("recipientId", dto.employeeId!!)
                        data.put("IsSpecialDispatch", 1)


                        samplesCount =
                            sqlSessionFactory.openSession().selectOne("UploadLogMapper.getSamplesCount", data)

                        data.put("month", dto.month!!)
                        data.put("year", dto.year!!)
                        data.put("recipientId", dto.employeeId!!)
                        data.put("IsSpecialDispatch", 1)


                        inputsCount = sqlSessionFactory.openSession().selectOne("UploadLogMapper.getInputCount", data)


                        itcCount.sampleItems = samplesCount
                        itcCount.nonSampleItems = inputsCount

                        itcCount;




                        if (itcCount.sampleItems == null) {
                            itcCount.sampleItems = 0.0;
                        }
                        if (itcCount.nonSampleItems == null) {
                            itcCount.nonSampleItems = 0.0;
                        }


                    }


                    if(dto.errorText.isNullOrEmpty()) {
                        if (dto.planType == "MONTHLY") {

                            var data: MutableMap<String, Any> = mutableMapOf()

                            data.put("ID_USR", user.id)
                            data.put("month", dto.month!!)
                            data.put("year", dto.year!!)
                            data.put("ID_EMPLOYEE_IVT", dto.employeeId!!)
                            data.put("EMPLOYEE_CODE_IVT", dto.employeeCode!!)
                            data.put("WEIGHT_IVT", dto.weight!!)
                            data.put("LrNo", dto.lrNo!!)
                            data.put("Boxes", dto.boxes!!)
                            data.put("Transporter", dto.transporterId!!)
                            data.put("SampleItems", itcCount.sampleItems!!)
                            data.put("NonSampleItems", itcCount.nonSampleItems!!)
                            data.put("ID_GROUP_INVOICE_INH", UUID.randomUUID().toString())
                            data.put("PLANID", dto.planId!!)

                            sqlSessionFactory.openSession().update("UploadLogMapper.saveGenerateInvoice", data)
                        } else if (dto.planType == "VIRTUAL") {


                            var data: MutableMap<String, Any> = mutableMapOf()

                            data.put("ID_USR", user.id)
                            data.put("month", dto.month!!)
                            data.put("year", dto.year!!)
                            data.put("ID_EMPLOYEE_IVT", dto.employeeId!!)
                            data.put("EMPLOYEE_CODE_IVT", dto.employeeCode!!)
                            data.put("WEIGHT_IVT", dto.weight!!)
                            data.put("LrNo", dto.lrNo!!)
                            data.put("Boxes", dto.boxes!!)
                            data.put("Dimension", dto.dimension!!)
                            data.put("Transporter", dto.transporterId!!)
                            data.put("SampleItems", itcCount.sampleItems!!)
                            data.put("NonSampleItems", itcCount.nonSampleItems!!)
                            data.put("ID_GROUP_INVOICE_INH", UUID.randomUUID().toString())
                            data.put("PLANID", dto.planId!!)
                            data.put("FFCODE", dto.ffCode!!)

                            sqlSessionFactory.openSession().update("UploadLogMapper.saveGenerateInvoiceVirtual", data)

                        } else {

                            var data: MutableMap<String, Any> = mutableMapOf()

                            data.put("ID_USR", user.id)
                            data.put("month", dto.month!!)
                            data.put("year", dto.year!!)
                            data.put("ID_EMPLOYEE_IVT", dto.employeeId!!)
                            data.put("EMPLOYEE_CODE_IVT", dto.employeeCode!!)
                            data.put("WEIGHT_IVT", dto.weight!!)
                            data.put("LrNo", dto.lrNo!!)
                            data.put("Boxes", dto.boxes!!)
                            data.put("Transporter", dto.transporterId!!)
                            data.put("SampleItems", itcCount.sampleItems!!)
                            data.put("NonSampleItems", itcCount.nonSampleItems!!)
                            data.put("ID_GROUP_INVOICE_INH", UUID.randomUUID().toString())
                            data.put("PLANID", dto.planId!!)

                            sqlSessionFactory.openSession().update("UploadLogMapper.saveGenerateInvoiceSpecial", data)
                        }


                    }

                }

                if (dto.errorText.isNullOrEmpty()) {

                    var data: MutableMap<String, Any> = mutableMapOf()

                    data.put("id", uplId)
                    data.put("type", UploadTypeEnum.INVOICE_DETAILS.id)
                    data.put("totalRecord", rows.count())
                    data.put("recordUpload", rows.count() )
                    data.put("statusId", UploadStatusEnum.COMPLETED_SUCCESSFULLY.id)
                    data.put("createdBy", user.id)
                    data.put("updatedBy", user.id)
                    data.put("parentId", uplId)


                    sqlSessionFactory.openSession().update("UploadLogMapper.updatedUploadLogCompletedSuccessFully", data)

                } else {

                    var data: MutableMap<String, Any> = mutableMapOf()

                    data.put("id", uplId)
                    data.put("type", UploadTypeEnum.INVOICE_DETAILS.id)
                    data.put("totalRecord", rows.count())
                    data.put("recordUpload", 0)
                    data.put("statusId", UploadStatusEnum.COMPLETED_ERRORS.id)
                    data.put("createdBy", user.id)
                    data.put("updatedBy", user.id)
                    data.put("parentId", uplId)


                    sqlSessionFactory.openSession().update("UploadLogMapper.updatedUploadLogCompletedWithErrors", data)
                }


            }








        return System.out.println("Invoice Generated successfully !")


    }

    private fun <T> MutableList(): MutableList<T> {
        return MutableList<T>()
    }

    private fun <T> List(): List<T> {

        return List<T>()
    }


    fun invoiceExcelData(uplId: String): List<InvoiceUploadDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("id",uplId)


        return sqlSessionFactory.openSession().selectList("UploadLogMapper.invoiceExcelData", data)
    }

    fun getVirtualSampleUploadLog (): List<UploadLogDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()




        return sqlSessionFactory.openSession().selectList("UploadLogMapper.getVirtualSampleUploadLog", data)
    }

    fun getRecipientUploadLog (): List<UploadLogDTO> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        var data: MutableMap<String, Any> = mutableMapOf()




        return sqlSessionFactory.openSession().selectList("UploadLogMapper.getRecipientUploadLog", data)
    }



}






