package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.MasterService
import com.squer.promobee.service.repository.MasterRepository
import com.squer.promobee.service.repository.domain.CostCenter
import com.squer.promobee.service.repository.domain.CostCenterBrand
import com.squer.promobee.service.repository.domain.SampleMaster
import com.squer.promobee.service.repository.domain.Vendor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
@Slf4j
class MasterServiceImpl @Autowired constructor(
    private val masterRepository: MasterRepository


): MasterService {

    // VENDOR SERVICE IMPL

    override fun getVendor(status: Int): List<VendorDTO> {
        return masterRepository.getVendor(status)
    }

    override fun addVendor(vnd: Vendor) {
        return masterRepository.addVendor(vnd)
    }

    override fun editVendor(vnd: Vendor) {
        return masterRepository.editVendor(vnd)
    }


    //COST CENTER IMPL

    override fun getCostCenter(status: Int): List<CostCenter> {
        return masterRepository.getCostCenter(status)
    }

    override fun addCostCenter(ccm: CostCenter) {
        return masterRepository.addCostCenter(ccm)
    }

    override fun editCostCenter(ccm: CostCenter) {
        return masterRepository.editCostCenter(ccm)
    }


    //SAMPLE IMPL

    override fun getSample(status: Int): List<SampleMaster> {
        return masterRepository.getSample(status)
    }

    override fun addSample(smp: SampleMaster) {
        return masterRepository.addSample(smp)
    }

    override fun editSample(smp: SampleMaster) {
        return masterRepository.editSample(smp)
    }


}