package com.squer.promobee.controller

import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.ReportService
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
open class ReportController @Autowired constructor(
    private val reportService: ReportService
) {

    private val log = LoggerFactory.getLogger(javaClass)


    @PostMapping("/getReportRecipient")
    fun getReportRecipient(@RequestBody ff: FFReportDTO): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = reportService.getReportRecipient(ff)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/getReportPurchase")
    fun getReportPurchase(@RequestBody pur : PurchaseReportParamDTO): ResponseEntity<*>{
        val data = reportService.getReportPurchase(pur)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @PostMapping("/getReportDispatches")
    fun getReportDispatches(@RequestBody disp : DispatchesReportParamDto): ResponseEntity<*>{
        val data = reportService.getReportDispatches(disp)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @PostMapping("/getReportDispatchRegister")
    fun getReportDispatchRegister(@RequestBody dispReg : DispatchRegisterParamDTO ): ResponseEntity<*>{
        val data = reportService.getReportDispatchRegister(dispReg)
        return  ResponseEntity(data,HttpStatus.OK)
    }



    @GetMapping("/getReportDeviation/{quarterName}/{fromDate}/{toDate}/{userId}/{userDesgId}")
    fun getReportDeviation(@PathVariable quarterName: String,@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable userId:String,@PathVariable userDesgId:String): ResponseEntity<*>{
        val data = reportService.getReportDeviation(quarterName,fromDate,toDate,userId,userDesgId)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @PostMapping("/getReportItemConsumption")
    fun getReportItemConsumption(@RequestBody item : ItemConsumptionParamDTO): ResponseEntity<*>{
        val data = reportService.getReportItemConsumption(item)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @PostMapping("/getReportDestruction")
    fun getReportDestruction(@RequestBody dest: DestructionReportParamDTO): ResponseEntity<*>{
        val data = reportService.getReportDestruction(dest)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @PostMapping("/getReportSimpleInventory")
    fun getReportSimpleInventory(@RequestBody simInv: SimpleInvenotryParamDTO): ResponseEntity<*>{
        val data = reportService.getReportSimpleInventory(simInv)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @PostMapping("/getReportNearToExpirySample")
    fun getReportNearToExpirySample(@RequestBody sample: NearExpiryReportParamDTO): ResponseEntity<*>{
        val data = reportService.getReportNearToExpirySample(sample)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @PostMapping("/getReportNearToExpiryInput")
    fun getReportNearToExpiryInput(@RequestBody input: NearExpiryReportParamDTO): ResponseEntity<*>{
        val data = reportService.getReportNearToExpiryInput(input)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @PostMapping("/getReportSpecialDispatch")
    fun getReportSpecialDispatch(@RequestBody speDisp: SpecialDispatchReportParamDTO): ResponseEntity<*>{
        val data = reportService.getReportSpecialDispatch(speDisp)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @GetMapping("/getReportDispatchByTeam/{year}/{special}")
    fun getReportDispatchByTeam(@PathVariable year: String, @PathVariable  special: String): ResponseEntity<*>{
        val data = reportService.getReportDispatchByTeam(year,special)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @PostMapping("/getItemWiseReport")
    fun getItemWiseReport(@RequestBody item : ItemWiseReportParamDTO): ResponseEntity<*>{
        val data = reportService.getItemWiseReport(item)
        return  ResponseEntity(data,HttpStatus.OK)

    }


    @GetMapping("/getStockLedgerReport/{fromDate}/{toDate}/{itemId}")
    fun getStockLedgerReport(@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable itemId: String): ResponseEntity<*>{
        val data = reportService.getStockLedgerReport(fromDate,toDate,itemId)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @PostMapping("/getAgeingReport")
    fun getAgeingReport(@RequestBody age:AgeingReportParamDTO): ResponseEntity<*>{
        val data = reportService.getAgeingReport(age)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getShiprocketReport/{fromDate}/{toDate}")
    fun getShiprocketReport(@PathVariable fromDate: String, @PathVariable  toDate: String): ResponseEntity<*>{
        val data = reportService.getShiprocketReport(fromDate,toDate)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getVirtualReconciliationReport/{fromDate}/{toDate}/{businessUnit}")
    fun getVirtualReconciliationReport(@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable  businessUnit: String): ResponseEntity<*>{
        val data = reportService.getVirtualReconciliationReport(fromDate,toDate,businessUnit)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getBatchReconciliation")
    fun getBatchReconciliation(): ResponseEntity<*>{
        val data = reportService.getBatchReconciliation()
        return  ResponseEntity(data,HttpStatus.OK)
    }





}