package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.CostCenter
import com.squer.promobee.service.repository.domain.CostCenterBrand
import com.squer.promobee.service.repository.domain.SampleMaster
import com.squer.promobee.service.repository.domain.Vendor
import java.util.*


interface MasterService {

    // VENDOR SERVICES

    fun getVendor (status: Int): List<VendorDTO>

    fun getVendorById (id: String): List<VendorDTO>

    fun addVendor(vnd: Vendor)

    fun editVendor (vnd: Vendor)

    //COST CENTER SERVICES

    fun getCostCenter(status: Int): List<CostCenter>

    fun addCostCenter(ccm: CostCenter)

    fun editCostCenter (ccm: CostCenter)

    //MASTER SERVICES

    fun getSample(status: Int): List<SampleMaster>

    fun addSample(smp: SampleMaster)

    fun editSample(smp: SampleMaster)


}