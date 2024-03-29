package com.squer.promobee.controller


import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.InvoiceService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*


@Slf4j
open class InvoiceController@Autowired constructor(
    private val invoiceService: InvoiceService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/getInvoiceHeaderById/{id}")
    open fun getInvoiceHeaderById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val inhData = invoiceService.getInvoiceHeaderById(id)
        return ResponseEntity(inhData, HttpStatus.OK)
    }

    @GetMapping("/getDoctorById/{id}")
    open fun getDoctorById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val doc = invoiceService.getDoctorById(id)
        return ResponseEntity(doc, HttpStatus.OK)
    }

    @GetMapping("/getPrintInvoiceHeaders/{inhId}")
    open fun getPrintInvoiceHeaders(@PathVariable inhId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printDetails = invoiceService.getPrintInvoiceHeaders(inhId)
        return ResponseEntity(printDetails, HttpStatus.OK)
    }

    @GetMapping("/getVirtualPrintInvoiceHeaders/{inhId}")
    open fun getVirtualPrintInvoiceHeaders(@PathVariable inhId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printDetailsVirtual = invoiceService.getVirtualPrintInvoiceHeaders(inhId)
        return ResponseEntity(printDetailsVirtual, HttpStatus.OK)
    }

    @GetMapping("/getInvoiceDetailsForPrint/{inhId}")
    open fun getInvoiceDetailsForPrint(@PathVariable inhId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val invoiceDetails = invoiceService.getInvoiceDetailsForPrint(inhId)
        return ResponseEntity(invoiceDetails, HttpStatus.OK)
    }


    @PostMapping("/printInvoice")
    open fun printInvoice(@RequestBody inh: List<PrintInvoiceDTO>): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printInvoiceData = invoiceService.printInvoice(inh)

        var fileContentList = mutableListOf<FileContentPOJO>()
        printInvoiceData?.forEach {
            fileContentList.add(FileContentPOJO(fileName = "invoice_${System.currentTimeMillis()}" , String(Base64.getEncoder().encode(it))))
        }
        return ResponseEntity(fileContentList, HttpStatus.OK)
    }

    @GetMapping("/getHsnRate/{hcmCode}")
    open fun getHsnRate(@PathVariable hcmCode: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val hsnData = invoiceService.getHsnRate(hcmCode)
        return ResponseEntity(hsnData, HttpStatus.OK)
    }

    @PostMapping("/searchInvoice")
    open fun searchInvoice(@RequestBody searchInvoice: SearchInvoiceDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val searchInvoiceData = invoiceService.searchInvoice(searchInvoice)

        return ResponseEntity(searchInvoiceData, HttpStatus.OK)
    }

    @PostMapping("/getGroupInvoiceListHub")
    open fun getGroupInvoiceListHub(@RequestBody groupInvoice: GroupInvoiceParamDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val groupInvoiceData = invoiceService.getGroupInvoiceListHub(groupInvoice)

        return ResponseEntity(groupInvoiceData, HttpStatus.OK)
    }


    @PostMapping("/getInvoicesForGrouping")
    open fun getInvoicesForGrouping(@RequestBody groupInvoice: GroupInvoiceParamDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val groupInvoicingData = invoiceService.getInvoicesForGrouping(groupInvoice)

        return ResponseEntity(groupInvoicingData, HttpStatus.OK)
    }


    @PostMapping("/printLabel")
    open fun printLabel(@RequestBody inh: List<PrintInvoiceDTO>): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val printLabelData = invoiceService.printLabel(inh)
        var fileContentList = mutableListOf<FileContentPOJO>()
        printLabelData?.forEach {
            fileContentList.add(FileContentPOJO(fileName = "label_${System.currentTimeMillis()}" , String(Base64.getEncoder().encode(it))))
        }
        return ResponseEntity(fileContentList, HttpStatus.OK)
    }

    @GetMapping("/getRecipientToGenerateInvoice/{recipientId}")
    open fun getRecipientToGenerateInvoice(@PathVariable recipientId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getRecipientToGenerateInvoice(recipientId)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDoctorToGenerateInvoice/{id}")
    open fun getDoctorToGenerateInvoice(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getDoctorToGenerateInvoice(id)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getInventoryByIdForInvoicing/{id}")
    open fun getInventoryByIdForInvoicing(@PathVariable invId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getInventoryByIdForInvoicing(invId)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getRecipientItemCategoryCount/{month}/{year}/{recipientId}/{isSpecial}/{planId}")
    open fun getRecipientItemCategoryCount(@PathVariable month: Int,@PathVariable year: Int,@PathVariable recipientId: String , @PathVariable isSpecial:Int , @PathVariable planId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getRecipientItemCategoryCount(month,year,recipientId,isSpecial,planId)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDoctorItemCategoryCount/{planId}/{recipientId}")
    open fun getDoctorItemCategoryCount(@PathVariable planId: String, @PathVariable recipientId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getDoctorItemCategoryCount(planId,recipientId)

        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getDispatchDetailsForInvoicing/{month}/{year}/{recipientId}/{isSpecial}/{planId}")
    open fun getDispatchDetailsForInvoicing(@PathVariable month: Int,@PathVariable year: Int,@PathVariable recipientId: String, @PathVariable isSpecial:Int , @PathVariable planId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getDispatchDetailsForInvoicing(month,year,recipientId,isSpecial,planId)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDispatchDetailVirtual/{planId}/{recipientId}")
    open fun getDispatchDetailVirtual(@PathVariable planId: String, @PathVariable recipientId: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getDispatchDetailVirtual(planId,recipientId)

        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getItemMasterById/{id}")
    open fun getItemMasterById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getItemMasterById(id)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getSampleMasterById/{id}")
    open fun getSampleMasterById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getSampleMasterById(id)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDispatchPlanById/{id}")
    open fun getDispatchPlanById(@PathVariable id: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getDispatchPlanById(id)

        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/generateInvoice")
        open fun generateInvoice(@RequestBody genInv : GenerateInvoiceDTO ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val generateInvoiceData = invoiceService.generateInvoice(genInv)

        return ResponseEntity(generateInvoiceData, HttpStatus.OK)
    }


    @GetMapping("/getInvoice/{invoiceNo}")
    open fun getInvoice(@PathVariable invoiceNo: Int): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getInvoice(invoiceNo)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getTransporter/{name}")
    open fun getTransporter(@PathVariable name: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getTransporter(name)

        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getDocket/{docketName}")
    open fun getDocket(@PathVariable docketName: String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = invoiceService.getDocket(docketName)

        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/generateInvoiceVirtual")
    open fun generateInvoiceVirtual(@RequestBody genInv : GenerateInvoiceVirtualDTO ): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val generateInvoiceData = invoiceService.generateInvoiceVirtual(genInv)

        return ResponseEntity(generateInvoiceData, HttpStatus.OK)
    }










}




