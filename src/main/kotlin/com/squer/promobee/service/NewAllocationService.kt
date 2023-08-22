package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.DispatchPlan
import org.springframework.http.ResponseEntity


interface NewAllocationService {

   fun getTseList( id: String): List<TseListDTO>

   fun assignTse( id: String): List<TseListDTO>

   fun unAssignTse( id: String): List<TseListDTO>

   fun getBrandManagerForTse( id: String): List<UserDTO>

   fun createMonthlyPlan(yearMonth: Long): List<AllocationInventoryDetailsWithCostCenterDTO>

   fun isPlanApprovedOrSubmitLock( month: String , year: String): ResponseEntity<out Any>

   fun getcheckforsampleFifocheckpopup( planId: String , inventoryId: String, isItem: Int): ResponseEntity<out Any>

   fun getTeamForCommonAllocation( ccmId: String): List<CommonAllocationTeamDTO>

   fun getQuantityAllocatedOfUserToItem( userId :String,  userDesgId :String,  inventoryId :String,  month :Int,
                                         year :Int,  isSpecialDispatch :Int): List<DesignationWiseQuantityAllocatedDTO>


   fun getTeamForDifferentialAllocation( planId :String,  teamId :String,  inventoryId :String ): List<AllocationDataTeamPopupDetailsDTO>

   fun saveCommonAllocation (saveAlloc : List<saveCommonAllocationDTO>)

   fun saveDifferentialAllocation (saveAlloc : List<saveDifferentialAllocation>)

   fun submitMonthlyAllocation (alloc : submitAllocationDTO)

   fun createSpecialPlan (alloc : CreateAllocationDTO): MutableList<AllocationInventoryDetailsWithCostCenterDTO>

   fun editSpecialPlan (planId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO>

   fun searchSpecialPlan(month: Int,year: Int,status:String,remark:String): MutableList<DispatchPlan>

}