package com.squer.promobee.controller


import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.squer.promobee.api.v1.enums.*
import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.UploadService
import com.squer.promobee.service.repository.domain.*
import lombok.extern.slf4j.Slf4j
import org.apache.ibatis.session.SqlSessionFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.io.File
import java.util.*


@Slf4j
open class UploadController@Autowired constructor(
    private val uploadService: UploadService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    @Value("C:\\upload\\transporter")
    private lateinit var configPath: String


    @GetMapping("/getGrnUploadLog")
    open fun getGrnUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var grnUpl = uploadService.getGrnUploadLog()
        return ResponseEntity(grnUpl, HttpStatus.OK)
    }


    @GetMapping("/getTransporterUploadLog")
    open fun getTransporterUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var trnUpl = uploadService.getTransporterUploadLog()
        return ResponseEntity(trnUpl, HttpStatus.OK)
    }


    @GetMapping("/getInvoiceUploadLog")
    open fun getInvoiceUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.getInvoiceUploadLog()
        return ResponseEntity(invUpl, HttpStatus.OK)
    }

    @GetMapping("/getAllUploadLog/{typeId}")
    open fun getAllUploadLog(@PathVariable typeId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.getAllUploadLog(typeId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }

    @PostMapping("/transporterUpload")
    fun transporterUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.transporterUpload(dto)
    }



    @GetMapping("/transportExcelData/{uplId}")
    open fun transportExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.transportExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }

    @GetMapping("/transportErrorData/{uplId}")
    open fun transportErrorData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.transportExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }




    @PostMapping("/grnUpload")
    fun grnUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.grnUpload(dto)
    }

    @GetMapping("/grnExcelData/{uplId}")
    open fun grnExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.grnExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }


    @PostMapping("/recipientUpload")
    fun recipientUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.recipientUpload(dto)
    }


    @GetMapping("/recipientExcelData/{uplId}")
    open fun recipientExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.recipientExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }


    @PostMapping("/virtualUpload")
    fun virtualUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.virtualUpload(dto)
    }

    @GetMapping("/virtualSampleExcelData/{uplId}")
    open fun virtualSampleExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.virtualSampleExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }


    @GetMapping("/getRecipientByCode/{code}")
    fun getRecipientByCode(@PathVariable code: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = uploadService.getRecipientByCode(code)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getTransporterByName/{name}")
    fun getTransporterByName(@PathVariable name: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = uploadService.getTransporterByName(name)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDoctorsByCode/{code}")
    fun getDoctorsByCode(@PathVariable code: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = uploadService.getDoctorsByCode(code)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/invoiceUpload")
    fun invoiceUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.invoiceUpload(dto)
    }


    @GetMapping("/invoiceExcelData/{uplId}")
    open fun invoiceExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var invUpl = uploadService.invoiceExcelData(uplId)
        return ResponseEntity(invUpl, HttpStatus.OK)
    }

    @GetMapping("/getVirtualSampleUploadLog")
    open fun getVirtualSampleUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var trnUpl = uploadService.getVirtualSampleUploadLog()
        return ResponseEntity(trnUpl, HttpStatus.OK)
    }

    @GetMapping("/getRecipientUploadLog")
    open fun getRecipientUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var trnUpl = uploadService.getRecipientUploadLog()
        return ResponseEntity(trnUpl, HttpStatus.OK)
    }

    @GetMapping("/getNonComplianceUploadLog")
    open fun getNonComplianceUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var trnUpl = uploadService.getNonComplianceUploadLog()
        return ResponseEntity(trnUpl, HttpStatus.OK)
    }

    @GetMapping("/getOverSamplingUploadLog")
    open fun getOverSamplingUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var trnUpl = uploadService.getOverSamplingUploadLog()
        return ResponseEntity(trnUpl, HttpStatus.OK)
    }


    @GetMapping("/getOverSamplingDetailsUploadLog")
    open fun getOverSamplingDetailsUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var trnUpl = uploadService.getOverSamplingDetailsUploadLog()
        return ResponseEntity(trnUpl, HttpStatus.OK)
    }

    @GetMapping("/getMaterialExpiryUploadLog")
    open fun getMaterialExpiryUploadLog() : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var trnUpl = uploadService.getMaterialExpiryUploadLog()
        return ResponseEntity(trnUpl, HttpStatus.OK)
    }

    @PostMapping("/nonComplianceUpload")
    fun nonComplianceUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.nonComplianceUpload(dto)
    }


    @PostMapping("/overSamplingUpload")
    fun overSamplingUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.overSamplingUpload(dto)
    }

    @PostMapping("/overSamplingDetailsUpload")
    fun overSamplingDetailsUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.overSamplingDetailsUpload(dto)
    }

    @PostMapping("/materialExpiryUpload")
    fun materialExpiryUpload(@RequestBody dto: FileUploadDto) {
        return uploadService.materialExpiryUpload(dto)
    }


    @GetMapping("/nonComplianceExcelData/{uplId}")
    open fun nonComplianceExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var excel = uploadService.nonComplianceExcelData(uplId)
        return ResponseEntity(excel, HttpStatus.OK)
    }


    @GetMapping("/overSamplingExcelData/{uplId}")
    open fun overSamplingExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var excel = uploadService.overSamplingExcelData(uplId)
        return ResponseEntity(excel, HttpStatus.OK)
    }


    @GetMapping("/overSamplingDetailsExcelData/{uplId}")
    open fun overSamplingDetailsExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var excel = uploadService.overSamplingDetailsExcelData(uplId)
        return ResponseEntity(excel, HttpStatus.OK)
    }

    @GetMapping("/materialExpiryExcelData/{uplId}")
    open fun materialExpiryExcelData(@PathVariable uplId : String) : ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var excel = uploadService.materialExpiryExcelData(uplId)
        return ResponseEntity(excel, HttpStatus.OK)
    }


//    @PostMapping("/multipleAllocationUpload")
//    fun multipleAllocationUpload(@RequestBody dto: MultipleAllocationUploadDTO): ResponseEntity<*> {
//        var errorMap: MutableMap<String, String> = HashMap()
//        var data = uploadService.multipleAllocationUpload(dto)
//
//        return ResponseEntity(errorMap , HttpStatus.OK)
//    }

//    @PostMapping("/multipleAllocationUpload")
//    fun multipleAllocationUpload(@RequestBody dto: MultipleAllocationUploadDTO): ResponseEntity<*> {
//        var errorMap: MutableMap<String, String> = HashMap()
//        var data = uploadService.multipleAllocationUpload(dto)
//
//        return ResponseEntity(errorMap , HttpStatus.OK)
//    }

    @PostMapping("/multipleAllocationUpload")
    fun multipleAllocationUpload(@RequestBody dto: MultipleAllocationUploadDTO) : ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        //var data10: MutableMap<String, Any> = mutableMapOf()
        var errorMap: MutableMap<String, String> = HashMap()



        val filePath = "${configPath}/multipleAllocationUpload/${dto.fileName}"

        File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.toString()))



        var counter = 0

        val validHeader: Boolean = true

        val line  = ""

        val successCount = 0

        var upl = UploadLog()

        var uplId =  UUID.randomUUID().toString()

        if(dto.byteCode.isEmpty() || dto.byteCode.isBlank()){
            var data: MutableMap<String, Any> = mutableMapOf()

            data.put("id",uplId)
            data.put("type", UploadTypeEnum.MULTIPLE_ALLOCATION.id)
            data.put("totalRecord",counter)
            data.put("recordUpload",counter)
            data.put("statusId", UploadStatusEnum.FILE_NOT_FOUND.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)

            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogFileNotFound", data)
        }

        else{
            var data: MutableMap<String, Any> = mutableMapOf()
            data.put("id",uplId)
            data.put("type", UploadTypeEnum.MULTIPLE_ALLOCATION.id)
            data.put("recordUpload",counter)
            data.put("statusId", UploadStatusEnum.QUEUED.id)
            data.put("createdBy",user.id)
            data.put("updatedBy",user.id)
            data.put("parentId",uplId)


            sqlSessionFactory.openSession().insert("UploadLogMapper.insertUploadLogQueued", data)
        }

        var allocationInventory = mutableListOf<MultipleAllocationInventoryDTO>()



        csvReader().readAllWithHeader(filePath)


        // Read CSV file
        var csvReaders = CsvReader()
        var csvData = csvReaders.readAll(File(filePath))

        // Extract headers horizontally
        var headerRow = csvData.firstOrNull() ?: emptyList()


        println("Headers: $headerRow")




        var csvReader = CsvReader()
        csvReader.autoRenameDuplicateHeaders






        var rows = CsvReader().readAllWithHeader(File(filePath))

        var i = 4

        var invOG = mutableListOf<Inventory>()

        var inv1 = mutableListOf<Inventory>()

        var head = headerRow.size


        var isHead = head >= i










        var plan = DispatchPlan()
        var data7: MutableMap<String, Any> = mutableMapOf()
        data7.put("planId",dto.planId)
        plan = sqlSessionFactory.openSession().selectOne<DispatchPlan>("DispatchPlanMapper.multipleAllocationUploadPlan",data7)



        if(plan.isVirtual == 1 && user.userDesignation!!.id == UserLovEnum.PRODUCT_MANAGER_DESIGNATION.id ){

            var m = 0
            for (i in 4 until head){


                var data: MutableMap<String, Any> = mutableMapOf()

                var item = ItemDrodownDTO()

                var inv = mutableListOf<Inventory>()



                var text = headerRow[i]



                var  itemCode = text.split("/")

                var invBatchNo = itemCode[4]

                data.put("itemCode", itemCode[1])

                item = sqlSessionFactory.openSession().selectOne<ItemDrodownDTO>("ItemMapper.multipleAllocation",data)


                var data1: MutableMap<String, Any> = mutableMapOf()

                data1.put("id",item.itemId!!)

                inv = sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.multipleAllocation",data1)


                if(inv[m].categoryId?.id  == ItemCategoryEnum.SAMPLES.id){
                    var ogInv = inv.filter { it.batchNo == invBatchNo }

                    invOG.addAll(ogInv)
                } else{
                    invOG.addAll(listOf(inv[m]))
                }

                invOG





            }

            var n = 4
            invOG.forEach {
                var inventoryId = it.id



                rows.forEach {

                    if(it.get(headerRow[n])!!.isNotEmpty()){

                        var sum = rows.map { it -> it[headerRow[n]]}

                        var result = sum.filter { it!!.isNotBlank()}

                        var resultWithoutSpaces = result.map { it!!.trim() }

                        val resultInts = resultWithoutSpaces.map { it.toInt() }

                        var allocationQtySum = resultInts.sum()

                        var data5: MutableMap<String, Any> = mutableMapOf()
                        data5.put("id",inventoryId!!)

                        var availableStock = sqlSessionFactory.openSession().selectOne<Inventory>("InventoryMapper.multipleAllocationAvailableStock",data5)

                        var inventoryStock = availableStock.qtyReceived!! -availableStock.qtyAllocated!!-availableStock.qtyDispatched!!




                        if(allocationQtySum > inventoryStock ){
                            errorMap["message"] = "Allocation quantity is greater than the available stock for ${availableStock.poNo}"
                            errorMap["error"] = "true"

                            return ResponseEntity(errorMap , HttpStatus.OK)
                        } else {
                            var data2: MutableMap<String, Any> = mutableMapOf()

                            var dispatchDetail = VirtualDispatchDetail()

                            var ff = Recipient()

                            data2.put("id", UUID.randomUUID().toString())
                            data2.put("planId", dto.planId)

                            var data3: MutableMap<String, Any> = mutableMapOf()
                            it.get(headerRow[2].toString().trim())?.let { it1 -> data3.put("code", it1) }
                            ff = sqlSessionFactory.openSession()
                                .selectOne<Recipient>("RecipientMapper.multipleAllocation", data3)

                            data2.put("inventoryId", inventoryId)
                            data2.put("recipientId", ff.id)
                            it.get(headerRow[n])?.let { it1 -> data2.put("qtyDispatch", it1) }
                            data2.put("quarterlyPlanId", "00000000-0000-0000-0000-000000000000")
                            data2.put("detailStatus", DispatchDetailStatusEnum.ALLOCATED.id)
                            data2.put("createdBy", user.id)
                            data2.put("updatedBy", user.id)

                            sqlSessionFactory.openSession().insert("DispatchDetailMapper.multipleAllocationVirtualBM", data2)

//                    var data4: MutableMap<String, Any> = mutableMapOf()
//                    data4.put("id", inventoryId)
//                    var inv = sqlSessionFactory.openSession()
//                        .selectOne<Inventory>("InventoryMapper.multipleAllocations", data4)
//
//                    var didQty = it.get(headerRow[n])
//
//                    var dispatchedQty = 0
//
//                    if (didQty.isNullOrEmpty()) {
//                        dispatchedQty
//                    } else {
//                        dispatchedQty = didQty!!.toInt()
//                    }
//
//
//                    var qtyAlloc = inv.qtyAllocated!!.plus(dispatchedQty)
//
//                    var data5: MutableMap<String, Any> = mutableMapOf()
//
//                    data5.put("id", inventoryId)
//                    data5.put("qtyAllocated", qtyAlloc)
//                    data5.put("updatedBy", user.id)
//
//                    sqlSessionFactory.openSession().update("InventoryMapper.multipleAllocationQtyAllocated", data5)


                            var data6: MutableMap<String, Any> = mutableMapOf()

                            data6.put("planId", dto.planId)
                            data6.put("inventoryId", inventoryId)

                            sqlSessionFactory.openSession()
                                .delete("DispatchDetailMapper.deleteZeroQuantityAllocationVirtualBM", data6)
                        }



                    }

                }


                n++



            }
        }

        else if(plan.isVirtual == 1 && user.userDesignation!!.id == UserLovEnum.REGIONAL_BUSINESS_MANAGER.id ){

            var employee = Users()

            var data0 : MutableMap<String, String> = mutableMapOf()

            data0.put("userId",user.id)

            employee = sqlSessionFactory.openSession().selectOne<Users>("UsersMasterMapper.getRbm",data0)

            var m = 0

            for (i in 4 until head){


                var data: MutableMap<String, Any> = mutableMapOf()

                var item = ItemDrodownDTO()

                var inv = mutableListOf<Inventory>()



                var text = headerRow[i]



                var  itemCode = text.split("/")

                var invBatchNo = itemCode[4]

                data.put("itemCode", itemCode[1])

                item = sqlSessionFactory.openSession().selectOne<ItemDrodownDTO>("ItemMapper.multipleAllocation",data)


                var data1: MutableMap<String, Any> = mutableMapOf()

                data1.put("id",item.itemId!!)

                inv = sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.multipleAllocation",data1)


                if(inv[m].categoryId?.id  == ItemCategoryEnum.SAMPLES.id){
                    var ogInv = inv.filter { it.batchNo == invBatchNo }

                    invOG.addAll(ogInv)
                } else{
                    invOG.addAll(listOf(inv[m]))
                }

                invOG





            }
            var n = 4

            invOG.forEach {
                var inventoryId = it.id

                rows.forEach {

                    if(it.get(headerRow[n])!!.isNotEmpty()){

                        var sum = rows.map { it -> it[headerRow[n]]}

                        var result = sum.filter { it!!.isNotBlank()}

                        var resultWithoutSpaces = result.map { it!!.trim() }

                        val resultInts = resultWithoutSpaces.map { it.toInt() }

                        var allocationQtySum = resultInts.sum()

                        var data5: MutableMap<String, Any> = mutableMapOf()
                        data5.put("planId", dto.planId)
                        data5.put("inventoryId", inventoryId)
                        data5.put("recipientId", employee.userRecipientId!!)

                        var rbmAvailableStock = sqlSessionFactory.openSession().selectOne<DispatchDetailDTO>("VirtualDispatchDetailMapper.rbmStockVirtualDID",data5)

                        if(allocationQtySum > rbmAvailableStock.qtyDispatch!! ){
                            errorMap["message"] = "Allocation quantity is greater than the available stock !"
                            errorMap["error"] = "true"

                            return ResponseEntity(errorMap , HttpStatus.OK)
                        } else {

                            var data2: MutableMap<String, Any> = mutableMapOf()

                            var dispatchDetail = DispatchDetail()

                            var ff = Recipient()

                            data2.put("id", UUID.randomUUID().toString())
                            data2.put("planId", dto.planId)

                            var data3: MutableMap<String, Any> = mutableMapOf()
                            it.get(headerRow[2].toString().trim())?.let { it1 -> data3.put("code", it1) }
                            ff = sqlSessionFactory.openSession()
                                .selectOne<Recipient>("RecipientMapper.multipleAllocation", data3)

                            data2.put("inventoryId", inventoryId)
                            data2.put("recipientId", ff.id)
                            it.get(headerRow[n])?.let { it1 -> data2.put("qtyDispatch", it1) }
                            data2.put("quarterlyPlanId", "00000000-0000-0000-0000-000000000000")
                            data2.put("detailStatus", DispatchDetailStatusEnum.ALLOCATED.id)
                            data2.put("createdBy", user.id)
                            data2.put("updatedBy", user.id)

                            sqlSessionFactory.openSession().insert("DispatchDetailMapper.multipleAllocationVirtualBM", data2)

                            var data4: MutableMap<String, Any> = mutableMapOf()

                            var didQty = it.get(headerRow[n])

                            data4.put("planId", dto.planId)
                            data4.put("inventoryId", inventoryId)
                            data4.put("recipientId", employee.userRecipientId!!)
                            data4.put("qtyDispatch",didQty!!)

                            sqlSessionFactory.openSession().update("DispatchDetailMapper.updateRBMDidStock", data4)

//                        var data4: MutableMap<String, Any> = mutableMapOf()
//                        data4.put("id", inventoryId)
//                        var inv = sqlSessionFactory.openSession()
//                            .selectOne<Inventory>("InventoryMapper.multipleAllocations", data4)
//
//                        var didQty = it.get(headerRow[n])
//
//                        var dispatchedQty = 0
//
//                        if(didQty.isNullOrEmpty()){
//                            dispatchedQty
//                        } else{
//                            dispatchedQty =  didQty!!.toInt()
//                        }
//
//
//
//                        var qtyAlloc = inv.qtyAllocated!!.plus(dispatchedQty)
//
//                        var data5: MutableMap<String, Any> = mutableMapOf()
//
//                        data5.put("id", inventoryId)
//                        data5.put("qtyAllocated", qtyAlloc)
//                        data5.put("updatedBy", user.id)
//
//                        sqlSessionFactory.openSession().update("InventoryMapper.multipleAllocationQtyAllocated", data5)

                            var data6: MutableMap<String, Any> = mutableMapOf()

                            data6.put("planId", dto.planId)
                            data6.put("inventoryId", inventoryId)

                            sqlSessionFactory.openSession().delete("DispatchDetailMapper.deleteZeroQuantityAllocation",data6)




                        }




                    }










                }


                n++



            }
        }

        else  {

            if (user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id){

                var employee = Users()

                var data0 : MutableMap<String, String> = mutableMapOf()

                data0.put("userId",user.id)

                employee = sqlSessionFactory.openSession().selectOne<Users>("UsersMasterMapper.getRbm",data0)

                var m = 0
                for (i in 4 until head){


                    var data: MutableMap<String, Any> = mutableMapOf()

                    var item = ItemDrodownDTO()

                    var inv = mutableListOf<Inventory>()



                    var text = headerRow[i]



                    var  itemCode = text.split("/")

                    var invBatchNo = itemCode[4]

                    data.put("itemCode", itemCode[1])

                    item = sqlSessionFactory.openSession().selectOne<ItemDrodownDTO>("ItemMapper.multipleAllocation",data)


                    var data1: MutableMap<String, Any> = mutableMapOf()

                    data1.put("id",item.itemId!!)

                    inv = sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.multipleAllocation",data1)


                    if(inv[m].categoryId?.id  == ItemCategoryEnum.SAMPLES.id){
                        var ogInv = inv.filter { it.batchNo == invBatchNo }

                        invOG.addAll(ogInv)
                    } else{
                        invOG.add(inv[m])
                    }

                    invOG

                }

                var n = 4

                invOG.forEach {
                    var inventoryId = it.id

                    rows.forEach {

                        if(it.get(headerRow[n])!!.isNotEmpty()){

                            var data2: MutableMap<String, Any> = mutableMapOf()

                            var dispatchDetail = DispatchDetail()

                            var ff = Recipient()

                            data2.put("id", UUID.randomUUID().toString())
                            data2.put("planId", dto.planId)

                            var data3: MutableMap<String, Any> = mutableMapOf()
                            it.get(headerRow[2].toString().trim())?.let { it1 -> data3.put("code", it1) }
                            ff = sqlSessionFactory.openSession()
                                .selectOne<Recipient>("RecipientMapper.multipleAllocation", data3)

                            data2.put("inventoryId", inventoryId)
                            data2.put("recipientId", ff.id)
                            it.get(headerRow[n])?.let { it1 -> data2.put("qtyDispatch", it1) }
                            data2.put("quarterlyPlanId", "00000000-0000-0000-0000-000000000000")
                            data2.put("detailStatus", DispatchDetailStatusEnum.ALLOCATED.id)
                            data2.put("createdBy", user.id)
                            data2.put("updatedBy", user.id)

                            sqlSessionFactory.openSession().insert("DispatchDetailMapper.multipleAllocation", data2)

                            var data4: MutableMap<String, Any> = mutableMapOf()
                            data4.put("id", inventoryId)
                            var inv = sqlSessionFactory.openSession()
                                .selectOne<Inventory>("InventoryMapper.multipleAllocations", data4)

                            var data6: MutableMap<String, Any> = mutableMapOf()
                            data6.put("planId", dto.planId)
                            data6.put("inventoryId", inventoryId)

                            sqlSessionFactory.openSession().delete("DispatchDetailMapper.deleteZeroQuantityAllocation",data6)

                            var didQty = it.get(headerRow[n])

                            var data7: MutableMap<String, Any> = mutableMapOf()

                            data7.put("inventoryId",inventoryId)
                            data7.put("recipientId",employee.userRecipientId!!)
                            data7.put("qtyDispatch", didQty!!)
                            data7.put("planId",plan.id!!)

                            sqlSessionFactory.openSession().update("DispatchDetailMapper.updateCommonAllocation",data7)
                        }





                    }


                    n++



                }

            } else {
                var m = 0
                for (i in 4 until head){


                    var data: MutableMap<String, Any> = mutableMapOf()

                    var item = ItemDrodownDTO()

                    var inv = mutableListOf<Inventory>()



                    var text = headerRow[i]



                    var  itemCode = text.split("/")

                    var invBatchNo = itemCode[4]

                    data.put("itemCode", itemCode[1])

                    item = sqlSessionFactory.openSession().selectOne<ItemDrodownDTO>("ItemMapper.multipleAllocation",data)


                    var data1: MutableMap<String, Any> = mutableMapOf()

                    data1.put("id",item.itemId!!)

                    inv = sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.multipleAllocation",data1)


                    if(inv[m].categoryId?.id  == ItemCategoryEnum.SAMPLES.id){
                        var ogInv = inv.filter { it.batchNo == invBatchNo }

                        invOG.addAll(ogInv)
                    } else{
                        invOG.add(inv[m])
                    }

                    invOG

                }

                var n = 4

                invOG.forEach {
                    var inventoryId = it.id





                    rows.forEach {

                        if(it.get(headerRow[n])!!.isNotEmpty()){

                            var sum = rows.map { it -> it[headerRow[n]]}

                            var result = sum.filter { it!!.isNotBlank()}

                            var resultWithoutSpaces = result.map { it!!.trim() }

                            val resultInts = resultWithoutSpaces.map { it.toInt() }

                            var allocationQtySum = resultInts.sum()

                            var data5: MutableMap<String, Any> = mutableMapOf()
                            data5.put("id",inventoryId!!)

                            var availableStock = sqlSessionFactory.openSession().selectOne<Inventory>("InventoryMapper.multipleAllocationAvailableStock",data5)

                            var inventoryStock = availableStock.qtyReceived!! -availableStock.qtyAllocated!!-availableStock.qtyDispatched!!




                            if(allocationQtySum > inventoryStock ){
                                errorMap["message"] = "Allocation quantity is greater than the available stock for ${availableStock.poNo}"
                                errorMap["error"] = "true"
                                errorMap["info"] = "error"

                                return ResponseEntity(errorMap , HttpStatus.OK)
                            }else{
                                var data2: MutableMap<String, Any> = mutableMapOf()

                                var dispatchDetail = DispatchDetail()

                                var ff = Recipient()

                                data2.put("id", UUID.randomUUID().toString())
                                data2.put("planId", dto.planId)

                                var data3: MutableMap<String, Any> = mutableMapOf()
                                it.get(headerRow[2].toString().trim())?.let { it1 -> data3.put("code", it1) }
                                ff = sqlSessionFactory.openSession()
                                    .selectOne<Recipient>("RecipientMapper.multipleAllocation", data3)

                                data2.put("inventoryId", inventoryId)
                                data2.put("recipientId", ff.id)
                                it.get(headerRow[n])?.let { it1 -> data2.put("qtyDispatch", it1) }
                                data2.put("quarterlyPlanId", "00000000-0000-0000-0000-000000000000")
                                data2.put("detailStatus", DispatchDetailStatusEnum.ALLOCATED.id)
                                data2.put("createdBy", user.id)
                                data2.put("updatedBy", user.id)

                                sqlSessionFactory.openSession().insert("DispatchDetailMapper.multipleAllocation", data2)

                                var data4: MutableMap<String, Any> = mutableMapOf()
                                data4.put("id", inventoryId)
                                var inv = sqlSessionFactory.openSession()
                                    .selectOne<Inventory>("InventoryMapper.multipleAllocations", data4)

                                var didQty = it.get(headerRow[n])

                                var dispatchedQty = 0

                                if(didQty.isNullOrEmpty()){
                                    dispatchedQty
                                } else{
                                    dispatchedQty =  didQty!!.toInt()
                                }



                                var qtyAlloc = inv.qtyAllocated!!.plus(dispatchedQty)

                                var data5: MutableMap<String, Any> = mutableMapOf()

                                data5.put("id", inventoryId)
                                data5.put("qtyAllocated", qtyAlloc)
                                data5.put("updatedBy", user.id)

                                sqlSessionFactory.openSession().update("InventoryMapper.multipleAllocationQtyAllocated", data5)

                                var data6: MutableMap<String, Any> = mutableMapOf()

                                data6.put("planId", dto.planId)
                                data6.put("inventoryId", inventoryId)

                                sqlSessionFactory.openSession().delete("DispatchDetailMapper.deleteZeroQuantityAllocation",data6)
                            }


                        }





                    }


                    n++



                }
            }


        }


        var record = rows.count()

        var data: MutableMap<String, Any> = mutableMapOf()
        data.put("id",uplId)
        data.put("type", UploadTypeEnum.MULTIPLE_ALLOCATION.id)
        data.put("totalRecord",record)
        data.put("recordUpload",record)
        data.put("statusId", UploadStatusEnum.COMPLETED_SUCCESSFULLY.id)
        data.put("createdBy",user.id)
        data.put("updatedBy",user.id)
        data.put("parentId",uplId)


        sqlSessionFactory.openSession().update("UploadLogMapper.multipleAllocation", data)

        errorMap["message"] = "Allocation completed successfully!"
        errorMap["error"] = "false"
        errorMap["info"] = "success"
        return ResponseEntity(errorMap ,HttpStatus.OK)







    }













}