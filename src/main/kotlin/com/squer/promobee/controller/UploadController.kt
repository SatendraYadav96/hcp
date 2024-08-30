package com.squer.promobee.controller


import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.squer.promobee.api.v1.enums.*
import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.UploadService
import com.squer.promobee.service.repository.NewAllocationRepository
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
    private val uploadService: UploadService,
    @Autowired
    var newAllocationRepository: NewAllocationRepository
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


    @PostMapping("/multipleAllocationUpload")
    fun multipleAllocationUpload(@RequestBody dto: MultipleAllocationUploadDTO) : ResponseEntity<*>{
        var data: MutableMap<String, Any> = mutableMapOf()
        var user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        var users = User()
        if(user.userDesignation!!.id == UserRoleEnum.TEAM_SUPPORT_EXECUTIVE_ID.id){
            users =  newAllocationRepository.loginAsBM(user.id)
            user = users
        }else{
            user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        }
        var errorMap: MutableMap<String, String> = HashMap()

        val filePath = "${configPath}/multipleAllocationUpload/${dto.fileName}"

        File(filePath).writeBytes(Base64.getDecoder().decode(dto.byteCode.toString()))

        var counter = 0

       // var upl = UploadLog()

        var uplId =  UUID.randomUUID().toString()

        if(dto.byteCode.isEmpty() || dto.byteCode.isBlank()){


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
           // var data: MutableMap<String, Any> = mutableMapOf()
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

        var head = headerRow.size

        var plan = DispatchPlan()
        var data7: MutableMap<String, Any> = mutableMapOf()
        data7.put("planId",dto.planId)
        plan = sqlSessionFactory.openSession().selectOne<DispatchPlan>("DispatchPlanMapper.multipleAllocationUploadPlan",data7)



        if(plan.isVirtual == 1 && user.userDesignation!!.id == UserLovEnum.PRODUCT_MANAGER_DESIGNATION.id ){

            var m = 0
            for (i in 4 until head){

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
                val dataList = mutableListOf<Map<String, Any>>() // List to hold all records for bulk insert
                var inventoryId = it.id
                var virtualAllocatedStock = 0
                var allocationQtySum = 0
                data.put("id",inventoryId!!)

                var availableStock = sqlSessionFactory.openSession().selectOne<Inventory>("InventoryMapper.multipleAllocationAvailableStock",data)

                var inventoryStock = availableStock.qtyReceived!! -availableStock.qtyAllocated!!-availableStock.qtyDispatched!!

                data.put("planId",dto.planId!!)
                data.put("invId",inventoryId!!)

                virtualAllocatedStock = sqlSessionFactory.openSession().selectOne("DispatchDetailMapper.virtualAllocatedStock",data)

                var realAllocatedVirtualStock = inventoryStock - virtualAllocatedStock


                rows.forEach {
                    var sum = rows.map { it -> it[headerRow[n]]}

                    var result = sum.filter { it!!.isNotBlank()}

                    val resultInts = result.map { it!!.toInt() }

                     allocationQtySum = resultInts.sum()
                    if(it.get(headerRow[n])!!.isNotEmpty()){

                        if(allocationQtySum > realAllocatedVirtualStock ){
                            errorMap["message"] = "Allocation quantity is greater than the available stock for ${availableStock.poNo}"
                            errorMap["error"] = "true"
                            errorMap["info"] = "error"

                            return ResponseEntity(errorMap , HttpStatus.OK)
                        } else {
                            val data = mutableMapOf<String, Any>()

                            var dispatchDetail = VirtualDispatchDetail()

                            var ff = Recipient()

                            var vvId = UUID.randomUUID().toString()
                            data["id"] = vvId
                            data["planId"] = dto.planId
                            it.get(headerRow[2].toString().trim())?.let { it1 -> data.put("code", it1) }
                            ff = sqlSessionFactory.openSession()
                                .selectOne<Recipient>("RecipientMapper.multipleAllocation", data)

                            data["inventoryId"] = inventoryId
                            data["recipientId"] = ff.id
                            it.get(headerRow[n])?.let { it1 -> data.put("qtyDispatch", it1) }
                            data["quarterlyPlanId"] = "00000000-0000-0000-0000-000000000000"
                            data["detailStatus"] = DispatchDetailStatusEnum.ALLOCATED.id
                            data["createdBy"] = user.id
                            data["updatedBy"] = user.id
                            // Accumulate data map in the list for bulk insert
                            dataList.add(data)

                        }

                    }

                }

                // Function to process in batches
                fun <T> List<T>.batchProcess(batchSize: Int, process: (List<T>) -> Unit) {
                    if (batchSize <= 0) {
                        throw IllegalArgumentException("Step must be positive, was: $batchSize")
                    }
                    for (i in indices step batchSize) {
                        val end = minOf(i + batchSize, size)
                        process(this.subList(i, end))
                    }
                }

// Number of parameters each record uses in the SQL statement
                val parametersPerRecord = 10 // Adjust this based on your actual parameters
                val batchSize = maxOf(dataList.size / parametersPerRecord, 1)

// Perform bulk insert in batches
                if (dataList.isNotEmpty()) {
                    dataList.batchProcess(batchSize) { batch ->
                        sqlSessionFactory.openSession().use { session ->
                            session.insert("DispatchDetailMapper.multipleAllocationVirtualBM", batch)
                        }
                    }
                }
                data.put("planId", dto.planId)
                data.put("inventoryId", inventoryId)

                sqlSessionFactory.openSession()
                    .delete("DispatchDetailMapper.deleteZeroQuantityAllocationVirtualBM", data)


                n++

            }
        }

        else if(plan.isVirtual == 1 && user.userDesignation!!.id == UserLovEnum.REGIONAL_BUSINESS_MANAGER.id ){

            var employee = Users()

            data.put("userId",user.id)

            employee = sqlSessionFactory.openSession().selectOne<Users>("UsersMasterMapper.getRbm",data)

            var m = 0

            for (i in 4 until head){

                var item = ItemDrodownDTO()

                var inv = mutableListOf<Inventory>()

                var text = headerRow[i]

                var  itemCode = text.split("/")

                var invBatchNo = itemCode[4]

                data.put("itemCode", itemCode[1])

                item = sqlSessionFactory.openSession().selectOne<ItemDrodownDTO>("ItemMapper.multipleAllocation",data)


                var data: MutableMap<String, Any> = mutableMapOf()

                data.put("id",item.itemId!!)

                inv = sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.multipleAllocation",data)


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
                val dataList = mutableListOf<Map<String, Any>>() // List to hold all records for bulk insert
                var allocationQtySum = 0
                var inventoryId = it.id

                var data: MutableMap<String, Any> = mutableMapOf()
                data.put("planId", dto.planId)
                data.put("inventoryId", inventoryId)
                data.put("recipientId", employee.userRecipientId!!)

                var rbmAvailableStock = sqlSessionFactory.openSession().selectOne<DispatchDetailDTO>("VirtualDispatchDetailMapper.rbmStockVirtualDID",data)

                var availableStock = rbmAvailableStock.qtyDispatch!!



                rows.forEach {
                    var sum = rows.map { it -> it[headerRow[n]]}

                    var result = sum.filter { it!!.isNotBlank()}

                    val resultInts = result.map { it!!.toInt() }

                    allocationQtySum = resultInts.sum()
                    if(it.get(headerRow[n])!!.isNotEmpty()){

                        if(allocationQtySum > availableStock ){
                            errorMap["message"] = "Allocation quantity is greater than the available stock !"
                            errorMap["error"] = "true"

                            return ResponseEntity(errorMap , HttpStatus.OK)
                        } else {

                            val data = mutableMapOf<String, Any>()

                            var dispatchDetail = DispatchDetail()

                            var ff = Recipient()

                            data["id"] = UUID.randomUUID().toString()
                            data["planId"] = dto.planId

                            it.get(headerRow[2].toString().trim())?.let { it1 -> data.put("code", it1) }
                            ff = sqlSessionFactory.openSession()
                                .selectOne<Recipient>("RecipientMapper.multipleAllocation", data)

                            data["inventoryId"] = inventoryId
                            data["recipientId"] = ff.id
                            it.get(headerRow[n])?.let { it1 -> data.put("qtyDispatch", it1) }
                            data["quarterlyPlanId"] = "00000000-0000-0000-0000-000000000000"
                            data["detailStatus"] = DispatchDetailStatusEnum.ALLOCATED.id
                            data["createdBy"] = user.id
                            data["updatedBy"] = user.id

                            dataList.add(data)

                        }

                    }
                }
                // Function to process in batches
                fun <T> List<T>.batchProcess(batchSize: Int, process: (List<T>) -> Unit) {
                    if (batchSize <= 0) {
                        throw IllegalArgumentException("Step must be positive, was: $batchSize")
                    }
                    for (i in indices step batchSize) {
                        val end = minOf(i + batchSize, size)
                        process(this.subList(i, end))
                    }
                }

// Number of parameters each record uses in the SQL statement
                val parametersPerRecord = 10 // Adjust this based on your actual parameters
                val batchSize = maxOf(dataList.size / parametersPerRecord, 1)

// Perform bulk insert in batches
                if (dataList.isNotEmpty()) {
                    dataList.batchProcess(batchSize) { batch ->
                        sqlSessionFactory.openSession().use { session ->
                            session.insert("DispatchDetailMapper.multipleAllocationVirtualBM", batch)
                        }
                    }
                }

                data.put("planId", dto.planId)
                data.put("inventoryId", inventoryId)
                data.put("recipientId", employee.userRecipientId!!)
                data.put("qtyDispatch",allocationQtySum)

                sqlSessionFactory.openSession().update("DispatchDetailMapper.updateRBMDidStock", data)

                data.put("planId",dto.planId)
                data.put("inventoryId",inventoryId)
                sqlSessionFactory.openSession().delete("DispatchDetailMapper.deleteZeroQuantityAllocationVirtualBM",data)


                n++

            }
        }

        else  {

            if (user.userDesignation!!.id == UserRoleEnum.REGIONAL_BUSINESS_MANAGER_ID.id){

                var employee = Users()

               // var data0 : MutableMap<String, String> = mutableMapOf()

                data.put("userId",user.id)

                employee = sqlSessionFactory.openSession().selectOne<Users>("UsersMasterMapper.getRbm",data)

                var m = 0
                for (i in 4 until head){


                   // var data: MutableMap<String, Any> = mutableMapOf()

                    var item = ItemDrodownDTO()

                    var inv = mutableListOf<Inventory>()



                    var text = headerRow[i]



                    var  itemCode = text.split("/")

                    var invBatchNo = itemCode[4]

                    data.put("itemCode", itemCode[1])

                    item = sqlSessionFactory.openSession().selectOne<ItemDrodownDTO>("ItemMapper.multipleAllocation",data)


                  //  var data1: MutableMap<String, Any> = mutableMapOf()

                    data.put("id",item.itemId!!)

                    inv = sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.multipleAllocation",data)


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
                    val dataList = mutableListOf<Map<String, Any>>() // List to hold all records for bulk insert
                    var inventoryId = it.id
                    var allocationQtySum = 0
                    rows.forEach {
                        var sum = rows.map { it -> it[headerRow[n]]}

                        var result = sum.filter { it!!.isNotBlank()}


                        val resultInts = result.map { it!!.toInt() }

                        allocationQtySum = resultInts.sum()

                        if(it.get(headerRow[n])!!.isNotEmpty()){

                            val data = mutableMapOf<String, Any>()
                            var dispatchDetail = DispatchDetail()

                            var ff = Recipient()

                            data["id"] = UUID.randomUUID().toString()
                            data["planId"] = dto.planId

                            // var data3: MutableMap<String, Any> = mutableMapOf()
                            it.get(headerRow[2].toString().trim())?.let { it1 -> data.put("code", it1) }
                            ff = sqlSessionFactory.openSession()
                                .selectOne<Recipient>("RecipientMapper.multipleAllocation", data)

                            data["inventoryId"] = inventoryId
                            data["recipientId"] = ff.id
                            it.get(headerRow[n])?.let { it1 -> data.put("qtyDispatch", it1) }
                            data["quarterlyPlanId"] = "00000000-0000-0000-0000-000000000000"
                            data["detailStatus"] = DispatchDetailStatusEnum.ALLOCATED.id
                            data["createdBy"] = user.id
                            data["updatedBy"] = user.id

                            // Accumulate data map in the list for bulk insert
                            dataList.add(data)

                        }


                    }
                    // Function to process in batches
                    fun <T> List<T>.batchProcess(batchSize: Int, process: (List<T>) -> Unit) {
                        if (batchSize <= 0) {
                            throw IllegalArgumentException("Step must be positive, was: $batchSize")
                        }
                        for (i in indices step batchSize) {
                            val end = minOf(i + batchSize, size)
                            process(this.subList(i, end))
                        }
                    }

// Number of parameters each record uses in the SQL statement
                    val parametersPerRecord = 10 // Adjust this based on your actual parameters
                    val batchSize = maxOf(dataList.size / parametersPerRecord, 1)

// Perform bulk insert in batches
                    if (dataList.isNotEmpty()) {
                        dataList.batchProcess(batchSize) { batch ->
                            sqlSessionFactory.openSession().use { session ->
                                session.insert("DispatchDetailMapper.multipleAllocation", batch)
                            }
                        }
                    }

                    data.put("inventoryId",inventoryId)
                    data.put("recipientId",employee.userRecipientId!!)
                    data.put("qtyDispatch", allocationQtySum)
                    data.put("planId",dto.planId)

                    sqlSessionFactory.openSession().update("DispatchDetailMapper.updateCommonAllocation",data)

                    data.put("planId",dto.planId)
                    data.put("inventoryId",inventoryId)
                    sqlSessionFactory.openSession().delete("DispatchDetailMapper.deleteZeroQuantityAllocation",data)

                    n++



                }

            } else {
                var m = 0
                for (i in 4 until head){

                 //   var data: MutableMap<String, Any> = mutableMapOf()

                    var item = ItemDrodownDTO()

                    var inv = mutableListOf<Inventory>()

                    var text = headerRow[i]

                    var  itemCode = text.split("/")

                    var invBatchNo = itemCode[4]

                    data.put("itemCode", itemCode[1])

                    item = sqlSessionFactory.openSession().selectOne<ItemDrodownDTO>("ItemMapper.multipleAllocation",data)

                  //  var data1: MutableMap<String, Any> = mutableMapOf()

                    data.put("id",item.itemId!!)

                    inv = sqlSessionFactory.openSession().selectList<Inventory>("InventoryMapper.multipleAllocation",data)


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
                    val dataList = mutableListOf<Map<String, Any>>() // List to hold all records for bulk insert
                    var inventoryId = it.id

                  //  var data5: MutableMap<String, Any> = mutableMapOf()
                    data.put("id",inventoryId!!)

                    var availableStock = sqlSessionFactory.openSession().selectOne<Inventory>("InventoryMapper.multipleAllocationAvailableStock",data)

                    var inventoryStock = availableStock.qtyReceived!! -availableStock.qtyAllocated!!-availableStock.qtyDispatched!!
                    var allocationQtySum = 0

                    rows.forEach {
                        var sum = rows.map { it -> it[headerRow[n]]}

                        var result = sum.filter { it!!.isNotBlank()}

                        val resultInts = result.map { it!!.toInt() }

                        allocationQtySum = resultInts.sum()

                        if(it.get(headerRow[n])!!.isNotEmpty()){

                            if(allocationQtySum > inventoryStock ){
                                errorMap["message"] = "Allocation quantity is greater than the available stock for ${availableStock.poNo}"
                                errorMap["error"] = "true"
                                errorMap["info"] = "error"

                                return ResponseEntity(errorMap , HttpStatus.OK)
                            }else{
                                val data = mutableMapOf<String, Any>()

                                var dispatchDetail = DispatchDetail()

                                var ff = Recipient()

                                data["id"] = UUID.randomUUID().toString()
                                data["planId"] = dto.planId

                                //  var data3: MutableMap<String, Any> = mutableMapOf()
                                it.get(headerRow[2].toString().trim())?.let { it1 -> data.put("code", it1) }
                                ff = sqlSessionFactory.openSession()
                                    .selectOne<Recipient>("RecipientMapper.multipleAllocation", data)

                                data["inventoryId"] = inventoryId
                                data["recipientId"] = ff.id
                                it.get(headerRow[n])?.let { it1 -> data.put("qtyDispatch", it1) }
                                data["quarterlyPlanId"] = "00000000-0000-0000-0000-000000000000"
                                data["detailStatus"] = DispatchDetailStatusEnum.ALLOCATED.id
                                data["createdBy"] = user.id
                                data["updatedBy"] = user.id

                                // Accumulate data map in the list for bulk insert
                                dataList.add(data)

                            }

                        }

                    }
                    if(allocationQtySum < inventoryStock ){
                        data.put("id",inventoryId)
                        data.put("qtyAllocated",allocationQtySum)
                        data.put("updatedBy",user.id)

                        sqlSessionFactory.openSession().update("InventoryMapper.multipleAllocationQtyAllocated",data)
                    }

                    // Function to process in batches
                    fun <T> List<T>.batchProcess(batchSize: Int, process: (List<T>) -> Unit) {
                        if (batchSize <= 0) {
                            throw IllegalArgumentException("Step must be positive, was: $batchSize")
                        }
                        for (i in indices step batchSize) {
                            val end = minOf(i + batchSize, size)
                            process(this.subList(i, end))
                        }
                    }

// Number of parameters each record uses in the SQL statement
                    val parametersPerRecord = 10 // Adjust this based on your actual parameters
                    val batchSize = maxOf(dataList.size / parametersPerRecord, 1)

// Perform bulk insert in batches
                    if (dataList.isNotEmpty()) {
                        dataList.batchProcess(batchSize) { batch ->
                            sqlSessionFactory.openSession().use { session ->
                                session.insert("DispatchDetailMapper.multipleAllocation", batch)
                            }
                        }
                    }

                    data.put("planId",dto.planId)
                    data.put("inventoryId",inventoryId)
                    sqlSessionFactory.openSession().delete("DispatchDetailMapper.deleteZeroQuantityAllocation",data)

                    n++
                }



            }

        }

        var record = rows.count()

       // var data: MutableMap<String, Any> = mutableMapOf()
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
