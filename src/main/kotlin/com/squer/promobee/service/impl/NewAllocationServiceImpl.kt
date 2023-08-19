package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.NewAllocationService
import com.squer.promobee.service.repository.NewAllocationRepository
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

    override fun getQuantityAllocatedOfUserToItem(userId: String, userDesgId: String, inventoryId: String, month: Int, year: Int, isSpecialDispatch: Int): List<DesignationWiseQuantityAllocatedDTO> {
        return newAllocationRepository.getQuantityAllocatedOfUserToItem(userId,userDesgId,inventoryId,month,year,isSpecialDispatch)
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
}