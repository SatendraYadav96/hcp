package com.squer.promobee.service.impl



import com.squer.promobee.controller.dto.ItemRevalidationDTO
import com.squer.promobee.service.RevalidationService
import com.squer.promobee.service.repository.RevalidationRepository


import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class RevalidationServiceImpl @Autowired constructor(
    private val revalidationRepository: RevalidationRepository


): RevalidationService {


    private val log = LoggerFactory.getLogger(javaClass)

    override fun getItemRevalidationHub(itemId: String, revldType: String): List<ItemRevalidationDTO> {
        return revalidationRepository.getItemRevalidationHub(itemId,revldType)
    }





}