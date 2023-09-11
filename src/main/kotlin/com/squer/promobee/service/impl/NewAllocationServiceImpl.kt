package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.NewAllocationService
import com.squer.promobee.service.repository.NewAllocationRepository
import com.squer.promobee.service.repository.domain.DispatchPlan
import com.squer.promobee.service.repository.domain.Recipient
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

    override fun assignTse(id: String): List<TseListDTO> {
        return newAllocationRepository.assignTse(id)
    }

    override fun unAssignTse(id: String): List<TseListDTO> {
        return newAllocationRepository.unAssignTse(id)
    }

    override fun getBrandManagerForTse(id: String): List<UserDTO> {
        return newAllocationRepository.getBrandManagerForTse(id)
    }

    override fun createMonthlyPlan(yearMonth: Long): List<AllocationInventoryDetailsWithCostCenterDTO> {
        return newAllocationRepository.createMonthlyPlan(yearMonth)
    }

    override fun isPlanApprovedOrSubmitLock(month: String, year: String): ResponseEntity<out Any> {
        return newAllocationRepository.isPlanApprovedOrSubmitLock(month,year)
    }

    override fun getcheckforsampleFifocheckpopup(planId: String, inventoryId: String, isItem: Int): ResponseEntity<out Any> {
        return newAllocationRepository.getcheckforsampleFifocheckpopup(planId,inventoryId,isItem)
    }

    override fun getTeamForCommonAllocation(ccmId: String): List<CommonAllocationTeamDTO> {
        return newAllocationRepository.getTeamForCommonAllocation(ccmId)
    }

    override fun getQuantityAllocatedOfUserToItem(userId: String,  inventoryId: String, month: Int, year: Int, isSpecialDispatch: Int): List<DesignationWiseQuantityAllocatedDTO> {
        return newAllocationRepository.getQuantityAllocatedOfUserToItem(userId,inventoryId,month,year,isSpecialDispatch)
    }

    override fun getTeamForDifferentialAllocation(planId: String, teamId: String, inventoryId: String): List<AllocationDataTeamPopupDetailsDTO> {
        return newAllocationRepository.getTeamForDifferentialAllocation(planId , teamId , inventoryId)
    }

    override fun saveCommonAllocation(saveAlloc: List<saveCommonAllocationDTO>) {
        return newAllocationRepository.saveCommonAllocation(saveAlloc)
    }

    override fun saveDifferentialAllocation(saveAlloc: List<saveDifferentialAllocation>) {
        return newAllocationRepository.saveDifferentialAllocation(saveAlloc)
    }

    override fun submitMonthlyAllocation(alloc: submitAllocationDTO) {
        return newAllocationRepository.submitMonthlyAllocation(alloc)
    }


    override fun createSpecialPlan(alloc: CreateAllocationDTO): MutableList<AllocationInventoryDetailsWithCostCenterDTO> {
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

    override fun getRecipientForSpecialAllocation(teamId: String): List<Recipient> {
        return newAllocationRepository.getRecipientForSpecialAllocation(teamId)
    }

    override fun saveSpecialAllocation(saveAlloc: List<saveDifferentialAllocation>) {
        return newAllocationRepository.saveSpecialAllocation(saveAlloc)
    }


    override fun deleteSpecialAllocation(dipId: String): Map<String, Any> {
        return newAllocationRepository.deleteSpecialAllocation(dipId)
    }

    override fun deleteSpecialAllocationDID(dipId: String): Map<String, Any> {
        return newAllocationRepository.deleteSpecialAllocationDID(dipId)
    }

    override fun createVirtualPlan(yearMonth: Long): List<AllocationInventoryDetailsWithCostCenterDTO> {
        return newAllocationRepository.createVirtualPlan(yearMonth)
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

    override fun saveVirtualCommonAllocation(saveAlloc: List<saveVirtualCommonAllocationDTO>) {
        return newAllocationRepository.saveVirtualCommonAllocation(saveAlloc)
    }

    override fun submitVirtualAllocation(alloc: submitAllocationDTO) {
        return newAllocationRepository.submitVirtualAllocation(alloc)
    }





}