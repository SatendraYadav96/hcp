package com.squer.promobee.service

import com.squer.promobee.controller.dto.AllocationInventoryDetailsWithCostCenterDTO
import com.squer.promobee.controller.dto.TseListDTO
import com.squer.promobee.controller.dto.UserDTO
import org.springframework.http.ResponseEntity


interface NewAllocationService {

   fun getTseList( id: String): List<TseListDTO>

   fun assignTse( id: String): List<TseListDTO>

   fun unAssignTse( id: String): List<TseListDTO>

   fun getBrandManagerForTse( id: String): List<UserDTO>

   fun createMonthlyPlan(yearMonth: Long): List<AllocationInventoryDetailsWithCostCenterDTO>

   fun isPlanApprovedOrSubmitLock( month: String , year: String)

   fun getcheckforsampleFifocheckpopup( planId: String , inventoryId: String, isItem: Int): ResponseEntity<out Any>



}