package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.TseListDTO
import com.squer.promobee.service.NewAllocationService
import com.squer.promobee.service.repository.NewAllocationRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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


}