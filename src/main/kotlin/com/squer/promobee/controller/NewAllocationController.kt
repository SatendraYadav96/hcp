package com.squer.promobee.controller

import com.squer.cme.utils.EmailUtils
import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.EmailService
import com.squer.promobee.service.NewAllocationService
import lombok.extern.slf4j.Slf4j
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@Slf4j
open class NewAllocationController @Autowired constructor(
    private val newAllocationService: NewAllocationService,
    private val emailService: EmailService,
    private val mailSender: JavaMailSender
){

    @Autowired
    lateinit var sqlSessionFactory: SqlSessionFactory


    @GetMapping("/getTseList/{id}")
    open fun getTseList(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.getTseList(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/assignTse/{id}")
    open fun assignTse(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.assignTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/unAssignTse/{id}")
    open fun unAssignTse(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.unAssignTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getBrandManagerForTse/{id}")
    open fun getBrandManagerForTse(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.getBrandManagerForTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    //SPECIAL ALLOCATION

    @GetMapping("/getTeamForSpeicalAllocation/{id}")
    open fun getTeamForSpeicalAllocation(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.getBrandManagerForTse(id)
        return ResponseEntity(data, HttpStatus.OK)
    }

    //MONTHLY ALLOCATION

    @GetMapping("/monthly/create/{year}/{month}")
    open fun createMonthlyPlan(@PathVariable year: Int , @PathVariable month: Int): ResponseEntity<*>{
        val items = newAllocationService.createMonthlyPlan(year,month)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @GetMapping("/isPlanApprovedOrSubmitLock/{month}/{year}")
    open fun isPlanApprovedOrSubmitLock(@PathVariable month: String , @PathVariable year: String): ResponseEntity<*> {
        val data = newAllocationService.isPlanApprovedOrSubmitLock(month , year)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getcheckforsampleFifocheckpopup/{planId}/{inventoryId}/{isItem}")
    open fun getcheckforsampleFifocheckpopup(@PathVariable planId: String , @PathVariable inventoryId: String, @PathVariable isItem: Int): ResponseEntity<*> {
        val data = newAllocationService.getcheckforsampleFifocheckpopup(planId , inventoryId ,isItem )
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getTeamForCommonAllocation/{ccmId}")
    open fun getTeamForCommonAllocation(@PathVariable ccmId :String ): ResponseEntity<*> {
        val data = newAllocationService.getTeamForCommonAllocation(ccmId )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getQuantityAllocatedOfUserToItem/{userId}/{inventoryId}/{month}/{year}/{isSpecialDispatch}")
    open fun getQuantityAllocatedOfUserToItem(@PathVariable userId :String,@PathVariable inventoryId :String,@PathVariable month :Int,
        @PathVariable year :Int,@PathVariable isSpecialDispatch :Int ): ResponseEntity<*> {
        val data = newAllocationService.getQuantityAllocatedOfUserToItem(userId,inventoryId,month,year,isSpecialDispatch )
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getQuantityAllocatedDifferentialRecipient/{planId}/{inventoryId}/{teamId}")
    open fun getQuantityAllocatedDifferentialRecipient(@PathVariable planId :String,@PathVariable inventoryId :String,@PathVariable teamId :String): ResponseEntity<*> {
        val data = newAllocationService.getQuantityAllocatedDifferentialRecipient(planId,inventoryId,teamId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getTeamForDifferentialAllocation/{planId}/{teamId}/{inventoryId}")
    open fun getTeamForDifferentialAllocation(@PathVariable planId :String , @PathVariable teamId :String , @PathVariable inventoryId :String ): ResponseEntity<*> {
        val data = newAllocationService.getTeamForDifferentialAllocation(planId, teamId , inventoryId )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/saveCommonAllocation")
    open fun saveCommonAllocation(@RequestBody saveAlloc : List<saveCommonAllocationDTO>): ResponseEntity<*>{
        val items = newAllocationService.saveCommonAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @PostMapping("/saveDifferentialAllocation")
    open fun saveDifferentialAllocation(@RequestBody saveAlloc : List<saveDifferentialAllocation>): ResponseEntity<*>{
        val items = newAllocationService.saveDifferentialAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @PostMapping("/submitMonthlyAllocation")
    open fun submitMonthlyAllocation(@RequestBody alloc : submitAllocationDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val items = newAllocationService.submitMonthlyAllocation(alloc)
        val months = mapOf(
            1 to "January",
            2 to "February",
            3 to "March",
            4 to "April",
            5 to "May",
            6 to "June",
            7 to "July",
            8 to "August",
            9 to "September",
            10 to "October",
            11 to "November",
            12 to "December"
        )
        var emailUtil = EmailUtils()
//                mutableListOf(it.UserEmailAddress)?.let { it1 ->
//                    emailUtil.sendMail(
//                        it1, mutableListOf("shraddha.tambe@sanofi.com"),
//                        "Hi, <br/> ${user.name} has submitted the Special Dispatch Plan for Month - ${months[alloc.month]} - ${alloc.year} & plan - ${alloc.name}, Kindly look into this and take further actions <br/> Thank You. ",
//                        "Special Plan Approval Notification Mail")
//                }
        emailUtil.sendMail (mutableListOf("shraddha.tambe@sanofi.com"), mutableListOf("satendra.yadav@squer.co.in"),
            "Hi, <br/></br> ${user.name} has submitted the Monthly Dispatch Plan for Month of <b> ${months[alloc.month]} ${alloc.year}. <br/></br> Kindly look into this and take further actions. </br> <br/> Thank You. ",
            "Monthly Plan Approval Notification Mail")

        var errorMap: MutableMap<String, String> = HashMap()
        println("Monthly Allocation submitted Successfully !")
        errorMap["message"] = "Monthly Allocation for ${months[alloc.month]} - ${alloc.year} is submitted successfully !"
        errorMap["error"] = "true"
        return ResponseEntity(errorMap, HttpStatus.OK)
    }


    //SPECIAL ALLOCATION


    @PostMapping("/special/create")
    open fun createSpecialPlan(@RequestBody alloc : CreateAllocationDTO): ResponseEntity<*>{
        val items = newAllocationService.createSpecialPlan(alloc)
        return ResponseEntity(items, HttpStatus.OK)
    }

    @GetMapping("/special/edit/{planId}")
    open fun editSpecialPlan(@PathVariable planId: String): ResponseEntity<*> {
        val data = newAllocationService.editSpecialPlan(planId )
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/searchSpecialPlan/{month}/{year}/{status}/{remark}")
    open fun searchSpecialPlan(@PathVariable month: Int, @PathVariable year: Int,@PathVariable status: String,@PathVariable remark: String): ResponseEntity<*> {
        val data = newAllocationService.searchSpecialPlan(month,year,status,remark )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getTeamForSpecialAllocation/{ccmId}")
    open fun getTeamForSpecialAllocation(@PathVariable ccmId: String): ResponseEntity<*> {
        val data = newAllocationService.getTeamForSpecialAllocation(ccmId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getRecipientForSpecialAllocation/{ccmId}")
    open fun getRecipientForSpecialAllocation(@PathVariable ccmId: String): ResponseEntity<*> {
        val data = newAllocationService.getRecipientForSpecialAllocation(ccmId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getSpecialQuantityAllocatedDifferentialRecipient/{planId}/{inventoryId}")
    open fun getSpecialQuantityAllocatedDifferentialRecipient(@PathVariable planId :String,@PathVariable inventoryId :String): ResponseEntity<*> {
        val data = newAllocationService.getSpecialQuantityAllocatedDifferentialRecipient(planId,inventoryId)
        return ResponseEntity(data, HttpStatus.OK)
    }




    @PostMapping("/saveSpecialAllocation")
    open fun saveSpecialAllocation(@RequestBody saveAlloc : List<saveDifferentialAllocation>): ResponseEntity<*>{
        val items = newAllocationService.saveSpecialAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @PostMapping("/deleteSpecialAllocation/{dipId}")
    open fun deleteSpecialAllocation(@PathVariable dipId: String): ResponseEntity<*> {
        val data = newAllocationService.deleteSpecialAllocation(dipId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/deleteSpecialAllocationDID/{dipId}")
    open fun deleteSpecialAllocationDID(@PathVariable dipId: String): ResponseEntity<*> {
        val data = newAllocationService.deleteSpecialAllocationDID(dipId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @PostMapping("/submitSpecialAllocation")
    open fun submitSpecialAllocation(@RequestBody alloc : submitSpecialAllocationDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User

        val items = newAllocationService.submitSpecialAllocation(alloc)
        val months = mapOf(
            1 to "January",
            2 to "February",
            3 to "March",
            4 to "April",
            5 to "May",
            6 to "June",
            7 to "July",
            8 to "August",
            9 to "September",
            10 to "October",
            11 to "November",
            12 to "December"
        )

        var data: MutableMap<String, Any> = mutableMapOf()

        data.put("UserID",user.id)


        var buHead = sqlSessionFactory.openSession().selectList<ApprovalUserDetailsDTO>("ApprovalMapper.specialPlanSubmitMail",data)

        if(buHead.size > 0) {

            buHead.forEach {
                var emailUtil = EmailUtils()
//                mutableListOf(it.UserEmailAddress)?.let { it1 ->
//                    emailUtil.sendMail(
//                        it1, mutableListOf("shraddha.tambe@sanofi.com"),
//                        "Hi, <br/> ${user.name} has submitted the Special Dispatch Plan for Month - ${months[alloc.month]} - ${alloc.year} & plan - ${alloc.name}, Kindly look into this and take further actions <br/> Thank You. ",
//                        "Special Plan Approval Notification Mail")
//                }
               emailUtil.sendMail (mutableListOf(it.UserEmailAddress), mutableListOf("shraddha.tambe@sanofi.com","satendra.yadav@squer.co.in"),
                        "Hi, <br/></br> ${user.name} has submitted the Special Dispatch Plan for Month of  <b>  ${months[alloc.month]} ${alloc.year} </b> as plan in name of <b> ${alloc.name} </b>. <br/></br> Kindly look into this and take further actions. </br> <br/> Thank You. ",
                        "Special Plan Approval Notification Mail")
                }

                println("Mail Sent!")
            }

        var errorMap: MutableMap<String, String> = HashMap()
        println("Special Allocation submitted Successfully !")
        errorMap["message"] = "Special Allocation for ${months[alloc.month]}  ${alloc.year}  & Plan - ${alloc.name} is submitted successfully !"
        errorMap["error"] = "true"
        return ResponseEntity(errorMap, HttpStatus.OK)
    }


    @GetMapping("/getAllocationStatusDropdown")
    fun getAllocationStatusDropdown(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val data = newAllocationService.getAllocationStatusDropdown()
        return ResponseEntity(data, HttpStatus.OK)
    }





    //VIRTUAL ALLOCATION


    @GetMapping("/virtual/create/{year}/{month}")
    open fun createVirtualPlan(@PathVariable year: Int , @PathVariable month: Int): ResponseEntity<*>{
        val items = newAllocationService.createVirtualPlan(year,month)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @GetMapping("/isVirtualPlanApprovedOrSubmitLock/{month}/{year}")
    open fun isVirtualPlanApprovedOrSubmitLock(@PathVariable month: String , @PathVariable year: String): ResponseEntity<*> {
        val data = newAllocationService.isVirtualPlanApprovedOrSubmitLock(month , year)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getVirtualTeamForCommonAllocation/{ccmId}")
    open fun getVirtualTeamForCommonAllocation(@PathVariable ccmId :String ): ResponseEntity<*> {
        val data = newAllocationService.getVirtualTeamForCommonAllocation(ccmId)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getVirtualQuantityAllocatedToUser/{userId}/{inventoryId}/{month}/{year}/{isSpecialDispatch}/{planId}")
    open fun getVirtualQuantityAllocatedToUser(@PathVariable userId :String,@PathVariable inventoryId :String,@PathVariable month :Int,
                                              @PathVariable year :Int,@PathVariable isSpecialDispatch :Int,@PathVariable planId :String
       ): ResponseEntity<*> {
        val data = newAllocationService.getVirtualQuantityAllocatedToUser(userId,inventoryId,month,year,isSpecialDispatch, planId )
        return ResponseEntity(data, HttpStatus.OK)
    }



    @GetMapping("/getVirtualTeamForDifferentialAllocation/{planId}/{teamId}/{inventoryId}")
    open fun getVirtualTeamForDifferentialAllocation(@PathVariable planId :String , @PathVariable teamId :String , @PathVariable inventoryId :String ): ResponseEntity<*> {
        val data = newAllocationService.getVirtualTeamForDifferentialAllocation(planId, teamId , inventoryId )
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getVirtualQuantityAllocatedDifferentialRecipient/{planId}/{inventoryId}/{teamId}")
    open fun getVirtualQuantityAllocatedDifferentialRecipient(@PathVariable planId :String,@PathVariable inventoryId :String,@PathVariable teamId :String): ResponseEntity<*> {
        val data = newAllocationService.getVirtualQuantityAllocatedDifferentialRecipient(planId,inventoryId,teamId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping("/saveVirtualCommonAllocation")
    open fun saveVirtualCommonAllocation(@RequestBody saveAlloc : List<saveVirtualCommonAllocationDTO>): ResponseEntity<*>{
        val items = newAllocationService.saveVirtualCommonAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @PostMapping("/saveVirtualDifferentialAllocation")
    open fun saveVirtualDifferentialAllocation(@RequestBody saveAlloc : List<saveDifferentialAllocation>): ResponseEntity<*>{
        val items = newAllocationService.saveVirtualDifferentialAllocation(saveAlloc)
        return ResponseEntity(items, HttpStatus.OK)
    }


    @PostMapping("/submitVirtualAllocation")
    open fun submitVirtualAllocation(@RequestBody alloc : submitAllocationDTO): ResponseEntity<*>{
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val items = newAllocationService.submitVirtualAllocation(alloc)
        val months = mapOf(
            1 to "January",
            2 to "February",
            3 to "March",
            4 to "April",
            5 to "May",
            6 to "June",
            7 to "July",
            8 to "August",
            9 to "September",
            10 to "October",
            11 to "November",
            12 to "December"
        )
        var emailUtil = EmailUtils()
//                mutableListOf(it.UserEmailAddress)?.let { it1 ->
//                    emailUtil.sendMail(
//                        it1, mutableListOf("shraddha.tambe@sanofi.com"),
//                        "Hi, <br/> ${user.name} has submitted the Special Dispatch Plan for Month - ${months[alloc.month]} - ${alloc.year} & plan - ${alloc.name}, Kindly look into this and take further actions <br/> Thank You. ",
//                        "Special Plan Approval Notification Mail")
//                }
        emailUtil.sendMail (mutableListOf("shraddha.tambe@sanofi.com"), mutableListOf("satendra.yadav@squer.co.in"),
            "Hi, <br/></br> ${user.name} has submitted the Virtual Dispatch Plan for Month of <b> ${months[alloc.month]} ${alloc.year}. <br/></br> Kindly look into this and take further actions. </br> <br/> Thank You. ",
            "Virtual Plan Approval Notification Mail")

        var errorMap: MutableMap<String, String> = HashMap()
        println("Virtual Allocation submitted Successfully !")
        errorMap["message"] = "Virtual Allocation for ${months[alloc.month]} - ${alloc.year} is submitted successfully !"
        errorMap["error"] = "true"
        return ResponseEntity(errorMap, HttpStatus.OK)
    }

    @GetMapping("/getActiveUsers/{userId}")
    open fun getActiveUsers(@PathVariable userId: String): ResponseEntity<*> {
        val data = newAllocationService.getActiveUsers(userId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getDownloadAllocation/{planId}")
    open fun getDownloadAllocation(@PathVariable planId: String): ResponseEntity<*> {
        val data = newAllocationService.getDownloadAllocation(planId)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/getBlockedRecipients/{code}")
    open fun getBlockedRecipients(@PathVariable code: String): ResponseEntity<*> {
        val data = newAllocationService.getBlockedRecipients(code)
        return ResponseEntity(data, HttpStatus.OK)
    }




    @PostMapping("/getMultipleAllocationCostCenter")
    open fun getMultipleAllocationCostCenter(@RequestBody mulAlloc: List<MultipleAllocationExcelDTO>): ResponseEntity<*> {
        val data = newAllocationService.getMultipleAllocationCostCenter(mulAlloc)
        return ResponseEntity(data, HttpStatus.OK)
    }


    @GetMapping("/getMultipleAllocationAll")
    open fun getMultipleAllocationAll(@RequestBody mulAlloc: List<MultipleAllocationExcelDTO>): ResponseEntity<*> {
        var data = newAllocationService.getMultipleAllocationAll(mulAlloc)

       // var byteData = String(Base64.getEncoder().encode(data))

        return ResponseEntity(data , HttpStatus.OK)

    }


    @PostMapping("/getMultipleAllocationExcel")
    open fun getMultipleAllocationInventory(@RequestBody mulAlloc: List<MultipleAllocationExcelDTO>): ResponseEntity<*> {
        val data = newAllocationService.getMultipleAllocationInventory(mulAlloc)
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping("/loginAsBM/{id}")
    open fun loginAsBM(@PathVariable id: String): ResponseEntity<*> {
        val data = newAllocationService.loginAsBM(id)
        return ResponseEntity(data, HttpStatus.OK)
    }




























































}
