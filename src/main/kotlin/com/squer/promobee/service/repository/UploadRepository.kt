package com.squer.promobee.service.repository


import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.squer.promobee.api.v1.enums.UploadStatusEnum
import com.squer.promobee.api.v1.enums.UploadTypeEnum
import com.squer.promobee.controller.dto.FileUploadDto
import com.squer.promobee.controller.dto.TransporterUploadDto
import com.squer.promobee.controller.dto.UploadLogDTO
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
import java.util.*



@Repository
class UploadRepository(
    securityUtility: SecurityUtility
): BaseRepository<UploadLog>(
    securityUtility = securityUtility
) {

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    @Value("\${application.config.path}")
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

        if(dto.fileName.isEmpty() || dto.fileName.isBlank()){
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

        //return println(message = "File Uploaded Successfully !")
    }










}






