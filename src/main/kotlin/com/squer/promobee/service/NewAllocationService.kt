package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.DispatchPlan
import com.squer.promobee.service.repository.domain.Recipient
import com.squer.promobee.service.repository.domain.Team
import org.springframework.http.ResponseEntity


interface NewAllocationService {

   fun getTseList( id: String): List<TseListDTO>

   fun assignTse( id: String)

   fun unAssignTse( id: String)

   fun getBrandManagerForTse( id: String): List<UserDTO>

   fun createMonthlyPlan(year:Int,month: Int): List<AllocationInventoryDetailsWithCostCenterDTO>

   fun isPlanApprovedOrSubmitLock( month: String , year: String): ResponseEntity<out Any>

   fun getcheckforsampleFifocheckpopup( planId: String , inventoryId: String, isItem: Int): ResponseEntity<out Any>

   fun getTeamForCommonAllocation( ccmId: String): List<CommonAllocationTeamDTO>

   fun getQuantityAllocatedOfUserToItem( userId :String, inventoryId :String,  month :Int,
                                         year :Int,  isSpecialDispatch :Int): List<DesignationWiseQuantityAllocatedDTO>

   fun getQuantityAllocatedDifferentialRecipient( planId:String,inventoryId :String,  teamId :String): List<DifferentialRecipientAllocationDTO>


   fun getTeamForDifferentialAllocation( planId :String,  teamId :String,  inventoryId :String ): List<AllocationDifferentialRecipientDTO>

   fun saveCommonAllocation (saveAlloc : List<saveCommonAllocationDTO>)

   fun saveDifferentialAllocation (saveAlloc : List<saveDifferentialAllocation>)

   fun submitMonthlyAllocation (alloc : submitAllocationDTO)

   fun createSpecialPlan (alloc : CreateAllocationDTO): MutableList<AllocationInventoryDetailsWithCostCenterDTO>

   fun editSpecialPlan (planId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO>

   fun searchSpecialPlan(month: Int,year: Int,status:String,remark:String): MutableList<DispatchPlan>


   fun getTeamForSpecialAllocation( ccmId: String): List<Team>

   fun getRecipientForSpecialAllocation( ccmId: String): List<Recipient>

   fun getSpecialQuantityAllocatedDifferentialRecipient( planId:String,inventoryId :String,  teamId :String): List<DifferentialRecipientAllocationDTO>

   fun saveSpecialAllocation (saveAlloc : List<saveDifferentialAllocation>)

   fun deleteSpecialAllocation( dipId: String): Map<String, Any>

   fun deleteSpecialAllocationDID( dipId: String): Map<String, Any>

   fun submitSpecialAllocation (alloc : submitSpecialAllocationDTO)

   fun getAllocationStatusDropdown(): List<AllocationStatusDropdownDTO>


   fun createVirtualPlan(year:Int,month: Int): List<VirtualAllocationInventoryDetailsWithCostCenterDTO>

   fun isVirtualPlanApprovedOrSubmitLock( month: String , year: String):Map<String , Any>

   fun getVirtualTeamForCommonAllocation( ccmId: String): List<CommonAllocationTeamDTO>

   fun getVirtualQuantityAllocatedToUser( userId :String, inventoryId :String,  month :Int,
                                         year :Int,  isSpecialDispatch :Int,planId: String): List<DesignationWiseQuantityAllocatedDTO>

   fun getVirtualTeamForDifferentialAllocation( planId :String,  teamId :String,  inventoryId :String ): List<AllocationDataTeamPopupDetailsDTO>

   fun getVirtualQuantityAllocatedDifferentialRecipient( planId:String,inventoryId :String,  teamId :String): List<DifferentialRecipientAllocationDTO>

   fun saveVirtualDifferentialAllocation (saveAlloc : List<saveDifferentialAllocation>)

   fun saveVirtualCommonAllocation (saveAlloc : List<saveVirtualCommonAllocationDTO>)

   fun submitVirtualAllocation (alloc : submitAllocationDTO)

   fun getActiveUsers (userId: String): List<ActiveUsersDTO>

   fun getDownloadAllocation (planId: String): List<DownloadAllocationDTO>

   fun getBlockedRecipients (code: String): List<BlockedRecipientDTO>

   fun getMultipleAllocationCostCenter (mulAlloc: List<MultipleAllocationExcelDTO>): List<MultipleAllocationDTO>

   fun getMultipleAllocationAll (mulAlloc: List<MultipleAllocationExcelDTO>): ByteArray

   fun getMultipleAllocationInventory (mulAlloc: List<MultipleAllocationExcelDTO>): List<MultipleAllocationInventoryDTO>







}