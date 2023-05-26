package com.squer.promobee.controller

import com.squer.promobee.security.domain.User
import com.squer.promobee.service.ReportService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*


@Slf4j
open class ReportController @Autowired constructor(
    private val reportService: ReportService
) {

    private val log = LoggerFactory.getLogger(javaClass)

//    @GetMapping("/getReportRecipient/{businessUnit}/{divison}/{team}/{statusId}")
//    fun getReportRecipient(@PathVariable businessUnit: String, @PathVariable divison: String, @PathVariable team:String, @PathVariable statusId:String): ResponseEntity<*> {
//        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
//        val data = reportService.getReportRecipient(businessUnit, divison, team, statusId)
//        return ResponseEntity(data, HttpStatus.OK)
//    }

    @GetMapping("/getReportRecipient/{businessUnit}/{team}/{statusId}")
    fun getReportRecipient(@PathVariable businessUnit: String,  @PathVariable team:String, @PathVariable statusId:String): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = reportService.getReportRecipient(businessUnit,  team, statusId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getReportPurchase/{startDate}/{endDate}/{userId}/{userDesgId}/{businessUnit}/{divison}")
    fun getReportPurchase(@PathVariable startDate: String , @PathVariable endDate: String,@PathVariable userId:String,@PathVariable userDesgId:String,@PathVariable businessUnit: String,@PathVariable divison: String): ResponseEntity<*>{
        val data = reportService.getReportPurchase(startDate,endDate,userId,userDesgId,businessUnit,divison)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @GetMapping("/getReportDispatches/{startDate}/{endDate}/{filter}/{filterPlan}/{userId}/{userDesgId}/{businessUnit}/{division}")
    fun getReportDispatches(@PathVariable startDate: String, @PathVariable  endDate: String,@PathVariable filter:Int,@PathVariable filterPlan:Int,@PathVariable userId:String,@PathVariable userDesgId:String,@PathVariable businessUnit: String,@PathVariable division: String): ResponseEntity<*>{
        val data = reportService.getReportDispatches(startDate,endDate,filter,filterPlan,userId,userDesgId,businessUnit,division)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @GetMapping("/getReportDispatchRegister/{startDate}/{endDate}/{userId}/{userDesgId}/{businessUnit}/{division}/{team}/{statusId}/{filterPlan}")
    fun getReportDispatchRegister(@PathVariable  startDate: String, @PathVariable  endDate: String, @PathVariable userId:String, @PathVariable userDesgId:String, @PathVariable businessUnit: String, @PathVariable division: String, @PathVariable team:String,  @PathVariable statusId: String, @PathVariable filterPlan:Int): ResponseEntity<*>{
        val data = reportService.getReportDispatchRegister(startDate,endDate,userId,userDesgId,businessUnit,division,team,statusId,filterPlan)
        return  ResponseEntity(data,HttpStatus.OK)
    }



    @GetMapping("/getReportDeviation/{quarterName}/{fromDate}/{toDate}/{userId}/{userDesgId}")
    fun getReportDeviation(@PathVariable quarterName: String,@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable userId:String,@PathVariable userDesgId:String): ResponseEntity<*>{
        val data = reportService.getReportDeviation(quarterName,fromDate,toDate,userId,userDesgId)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @GetMapping("/getReportItemConsumption/{fromDate}/{toDate}/{userId}/{userDesgId}/{businessUnit}/{divison}")
    fun getReportItemConsumption(@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable userId:String,@PathVariable userDesgId:String,@PathVariable businessUnit: String,@PathVariable divison: String): ResponseEntity<*>{
        val data = reportService.getReportItemConsumption(fromDate,toDate,userId,userDesgId,businessUnit,divison)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getReportDestruction/{fromDate}/{toDate}/{userId}/{userDesgId}/{businessUnit}/{divison}/{statusId}")
    fun getReportDestruction(@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable userId:String,@PathVariable userDesgId:String,@PathVariable businessUnit: String,@PathVariable divison: String,@PathVariable statusId: String): ResponseEntity<*>{
        val data = reportService.getReportDestruction(fromDate,toDate,userId,userDesgId,businessUnit,divison,statusId)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @GetMapping("/getReportSimpleInventory/{businessUnit}/{divison}/{userId}/{userDesgId}")
    fun getReportSimpleInventory(@PathVariable businessUnit: String,@PathVariable divison: String,@PathVariable userId:String,@PathVariable userDesgId:String): ResponseEntity<*>{
        val data = reportService.getReportSimpleInventory(businessUnit,divison,userId,userDesgId)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getReportNearToExpirySample/{businessUnit}/{divison}/{userId}/{userDesgId}/{type}")
    fun getReportNearToExpirySample(@PathVariable businessUnit: String,@PathVariable divison: String,@PathVariable userId:String,@PathVariable userDesgId:String,@PathVariable type:String): ResponseEntity<*>{
        val data = reportService.getReportNearToExpirySample(businessUnit,divison,userId,userDesgId,type)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getReportNearToExpiryInput/{businessUnit}/{divison}/{userId}/{userDesgId}/{type}")
    fun getReportNearToExpiryInput(@PathVariable businessUnit: String,@PathVariable divison: String,@PathVariable userId:String,@PathVariable userDesgId:String,@PathVariable type:String): ResponseEntity<*>{
        val data = reportService.getReportNearToExpiryInput(businessUnit,divison,userId,userDesgId,type)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @GetMapping("/getReportSpecialDispatch/{fromDate}/{toDate}/{userId}/{userDesgId}/{businessUnit}/{divison}")
    fun getReportSpecialDispatch(@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable userId:String,@PathVariable userDesgId:String,@PathVariable businessUnit: String,@PathVariable divison: String): ResponseEntity<*>{
        val data = reportService.getReportSpecialDispatch(fromDate,toDate,userId,userDesgId,businessUnit,divison)
        return  ResponseEntity(data,HttpStatus.OK)
    }


    @GetMapping("/getReportDispatchByTeam/{year}/{special}")
    fun getReportDispatchByTeam(@PathVariable year: String, @PathVariable  special: String): ResponseEntity<*>{
        val data = reportService.getReportDispatchByTeam(year,special)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getItemWiseReport/{fromDate}/{toDate}/{businessUnit}/{divison}")
    fun getItemWiseReport(@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable businessUnit: String,@PathVariable divison: String): ResponseEntity<*>{
        val data = reportService.getItemWiseReport(fromDate,toDate,businessUnit,divison)
        return  ResponseEntity(data,HttpStatus.OK)

    }


    @GetMapping("/getStockLedgerReport/{fromDate}/{toDate}/{itemId}")
    fun getStockLedgerReport(@PathVariable fromDate: String, @PathVariable  toDate: String,@PathVariable itemId: String): ResponseEntity<*>{
        val data = reportService.getStockLedgerReport(fromDate,toDate,itemId)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getAgeingReport/{userId}/{userDesgId}/{businessUnit}/{divison}")
    fun getAgeingReport(@PathVariable userId:String,@PathVariable userDesgId:String,@PathVariable businessUnit: String,@PathVariable divison: String): ResponseEntity<*>{
        val data = reportService.getAgeingReport(userId,userDesgId,businessUnit,divison)
        return  ResponseEntity(data,HttpStatus.OK)
    }

    @GetMapping("/getShiprocketReport/{fromDate}/{toDate}")
    fun getShiprocketReport(@PathVariable fromDate: String, @PathVariable  toDate: String): ResponseEntity<*>{
        val data = reportService.getShiprocketReport(fromDate,toDate)
        return  ResponseEntity(data,HttpStatus.OK)
    }

}