package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.*
import java.util.*


interface MasterService {

    // VENDOR SERVICES

    fun getVendor (status: Int): List<VendorDTO>

    fun getVendorById (id: String): Vendor

    fun addVendor(vnd: Vendor)

    fun editVendor (vnd: Vendor)

    //COST CENTER SERVICES

    fun getCostCenter(status: Int): List<CostCenter>

    fun addCostCenter(ccm: CostCenter)

    fun editCostCenter (ccm: CostCenter)

    fun getCostCenterById (id: String): CostCenter

    //MASTER SERVICES

    fun getSample(status: Int): List<SampleMaster>

    fun addSample(smp: SampleMaster)

    fun editSample(smp: SampleMaster)

    fun getSampleById (id: String): SampleMaster

    // DROPDOWN SERVICES

    fun getBusinessUnitDropdown(): List<BU>


}