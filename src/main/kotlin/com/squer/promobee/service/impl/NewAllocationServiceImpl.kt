package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.NewAllocationService
import com.squer.promobee.service.repository.NewAllocationRepository
import com.squer.promobee.service.repository.domain.DispatchPlan
import com.squer.promobee.service.repository.domain.Team
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
@Slf4j
open class NewAllocationServiceImpl @Autowired constructor(
    private val newAllocationRepository: NewAllocationRepository,



): NewAllocationService {

    private val log = LoggerFactory.getLogger(javaClass)


    override fun getTseList(id: String): List<TseListDTO> {
        return newAllocationRepository.getTseList(id)
    }

    override fun assignTse(id: String) {
        return newAllocationRepository.assignTse(id)
    }

    override fun unAssignTse(id: String) {
        return newAllocationRepository.unAssignTse(id)
    }

    override fun getBrandManagerForTse(id: String): List<UserDTO> {
        return newAllocationRepository.getBrandManagerForTse(id)
    }

    override fun createMonthlyPlan(year: Int, month: Int): List<AllocationInventoryDetailsWithCostCenterDTO> {
        return newAllocationRepository.createMonthlyPlan(year,month)
    }

    override fun isPlanApprovedOrSubmitLock(month: String, year: String): ResponseEntity<out Any> {
        return newAllocationRepository.isPlanApprovedOrSubmitLock(month,year)
    }

    override fun getcheckforsampleFifocheckpopup(planId: String, inventoryId: String, isItem: Int): ResponseEntity<out Any> {
        return newAllocationRepository.getcheckforsampleFifocheckpopup(planId,inventoryId,isItem)
    }

    override fun getTeamForCommonAllocation(ccmId: String): List<CommonAllocationTeamDTO> {
//        val data =  newAllocationRepository.getTeamForCommonAllocation(ccmId)
//        data.forEach {
//            it.team=it.team+" - "+it.designation
//        }
//        return data
        return newAllocationRepository.getTeamForCommonAllocation(ccmId)
    }

    override fun getQuantityAllocatedOfUserToItem(userId: String,  inventoryId: String, month: Int, year: Int, isSpecialDispatch: Int ): List<DesignationWiseQuantityAllocatedDTO> {
        return newAllocationRepository.getQuantityAllocatedOfUserToItem(userId,inventoryId,month,year,isSpecialDispatch)
    }

    override fun getQuantityAllocatedDifferentialRecipient(planId: String,inventoryId: String, teamId: String): List<DifferentialRecipientAllocationDTO> {
        return newAllocationRepository.getQuantityAllocatedDifferentialRecipient(planId,inventoryId,teamId)
    }


    override fun getTeamForDifferentialAllocation(planId: String, teamId: String, inventoryId: String): List<AllocationDifferentialRecipientDTO> {
        return newAllocationRepository.getTeamForDifferentialAllocation(planId , teamId , inventoryId)
    }

    override fun saveCommonAllocation(saveAlloc: List<saveCommonAllocationDTO>) : ResponseEntity<MutableMap<String, String>>{
        return newAllocationRepository.saveCommonAllocation(saveAlloc)


    }

    override fun saveDifferentialAllocation(saveAlloc: List<saveDifferentialAllocation>) {
        return newAllocationRepository.saveDifferentialAllocation(saveAlloc)
    }

    override fun submitMonthlyAllocation(alloc: submitAllocationDTO) {
        return newAllocationRepository.submitMonthlyAllocation(alloc)
    }


    override fun createSpecialPlan(alloc: CreateAllocationDTO): CreateSpecialAllocationDTO{
        return newAllocationRepository.createSpecialPlan(alloc)
    }

    override fun editSpecialPlan(planId: String): MutableList<AllocationInventoryDetailsWithCostCenterDTO> {
        return newAllocationRepository.editSpecialPlan(planId)
    }

    override fun searchSpecialPlan(month: Int, year: Int, status: String, remark: String): MutableList<DispatchPlan> {
        return newAllocationRepository.searchSpecialPlan(month,year,status,remark)
    }

    override fun getTeamForSpecialAllocation(ccmId: String): List<Team> {
        return newAllocationRepository.getTeamForSpecialAllocation(ccmId)
    }

    override fun getRecipientForSpecialAllocation(ccmId: String): List<RecipientReportDTO> {
        return newAllocationRepository.getRecipientForSpecialAllocation(ccmId)
    }

    override fun getSpecialQuantityAllocatedDifferentialRecipient(planId: String, inventoryId: String): List<DifferentialRecipientAllocationDTO> {
        return newAllocationRepository.getSpecialQuantityAllocatedDifferentialRecipient(planId,inventoryId)
    }

    override fun saveSpecialAllocation(saveAlloc: List<saveDifferentialAllocation>): ResponseEntity<MutableMap<String, String>> {
        return newAllocationRepository.saveSpecialAllocation(saveAlloc)
    }


    override fun deleteSpecialAllocation(dipId: String): Map<String, Any> {
        return newAllocationRepository.deleteSpecialAllocation(dipId)
    }

    override fun deleteSpecialAllocationDID(dipId: String): Map<String, Any> {
        return newAllocationRepository.deleteSpecialAllocationDID(dipId)
    }

    override fun submitSpecialAllocation(alloc: submitSpecialAllocationDTO) {
        return newAllocationRepository.submitSpecialAllocation(alloc)
    }

    override fun getAllocationStatusDropdown(): List<AllocationStatusDropdownDTO> {
        return newAllocationRepository.getAllocationStatusDropdown()
    }

    override fun createVirtualPlan(year:Int,month: Int): CreateVirtualAllocationDTO {
        return newAllocationRepository.createVirtualPlan(year,month)
    }

    override fun isVirtualPlanApprovedOrSubmitLock(month: String, year: String): Map<String, Any> {
        return newAllocationRepository.isVirtualPlanApprovedOrSubmitLock(month,year)
    }

    override fun getVirtualTeamForCommonAllocation(ccmId: String): List<CommonAllocationTeamDTO> {
        return newAllocationRepository.getVirtualTeamForCommonAllocation(ccmId)
    }

    override fun getVirtualQuantityAllocatedToUser(userId: String, inventoryId: String, month: Int, year: Int, isSpecialDispatch: Int,planId: String): List<DesignationWiseQuantityAllocatedDTO> {
        return newAllocationRepository.getVirtualQuantityAllocatedToUser(userId,inventoryId,month,year,isSpecialDispatch,planId)
    }

    override fun getVirtualTeamForDifferentialAllocation(planId: String, teamId: String, inventoryId: String): List<AllocationDataTeamPopupDetailsDTO> {
        return newAllocationRepository.getVirtualTeamForDifferentialAllocation(planId,teamId,inventoryId)
    }

    override fun getVirtualQuantityAllocatedDifferentialRecipient(planId: String, inventoryId: String, teamId: String): List<DifferentialRecipientAllocationDTO> {
        return newAllocationRepository.getVirtualQuantityAllocatedDifferentialRecipient(planId,inventoryId,teamId)
    }

    override fun saveVirtualCommonAllocation(saveAlloc: List<saveVirtualCommonAllocationDTO>): ResponseEntity<MutableMap<String, String>>  {
        return newAllocationRepository.saveVirtualCommonAllocation(saveAlloc)
    }

    override fun saveVirtualDifferentialAllocation(saveAlloc: List<saveDifferentialAllocation>) {
        return newAllocationRepository.saveVirtualDifferentialAllocation(saveAlloc)
    }

    override fun submitVirtualAllocation(alloc: submitAllocationDTO) {
        return newAllocationRepository.submitVirtualAllocation(alloc)
    }


    override fun getActiveUsers(userId: String): List<ActiveUsersDTO> {
        return newAllocationRepository.getActiveUsers(userId)
    }

    override fun getDownloadAllocation(planId: String): List<DownloadAllocationDTO> {
        return newAllocationRepository.getDownloadAllocation(planId)
    }

    override fun getBlockedRecipients(code: String): List<BlockedRecipientDTO> {
        return newAllocationRepository.getBlockedRecipients(code)
    }

    override fun getMultipleAllocationCostCenter(mulAlloc: List<MultipleAllocationExcelDTO>): List<MultipleAllocationDTO> {
        return newAllocationRepository.getMultipleAllocationCostCenter(mulAlloc)
    }

    override fun getMultipleAllocationAll(mulAlloc: List<MultipleAllocationExcelDTO>): ByteArray {
        return newAllocationRepository.getMultipleAllocationAll(mulAlloc)
    }

    override fun getMultipleAllocationInventory(mulAlloc: List<MultipleAllocationExcelDTO>): List<MultipleAllocationInventoryDTO> {
        return newAllocationRepository.getMultipleAllocationInventory(mulAlloc)
    }

    override fun loginAsBM(id: String):  User {
        return newAllocationRepository.loginAsBM(id)
    }



}