package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.security.domain.User
import com.squer.promobee.service.repository.domain.*
import java.util.*
import kotlin.collections.ArrayList


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

    fun getBrandDropdown(): List<BrandMaster>

    fun getDivisionDropdown(): List<Division>

    fun getTeamDropdown(): List<Team>

    fun getCostCenterDropdown(): List<CostCenter>

    fun getItemCodeDropdown(): List<ItemDrodownDTO>

    fun getRecipientDropdown(): List<RecipientDropDownDTO>

    fun getInvoiceDropdown(): List<InvoiceDropdownDTO>

    fun getTransporter(): List<TransporterDropdownDTO>

    fun getLegalEntityDropdown(): List<LegalEntity>

    fun getRecipientDesignationDropdown(): List<RecipientDesignationDropdownDTO>

    fun getUserDesignationDropdown(): List<UserDesignationDropdownDTO>

    fun getUserDropdown(): List<UserDropdownDTO>




    // BUSINESS UNIT SERVICES

    fun getBusinessUnit(status: Int): List<BU>

    fun getBusinessUnitById (id: String): BU

    fun editBusinessUnit(bu: BU)

    fun addBusinessUnit(bu: BU)

    //TEAM SERVICES

    fun getTeam(status: Int): List<Team>

    fun getBrandByTeamId (teamId: String): MutableList<TeamBrand>

    fun getTeamById (id: String): MutableList<Team>

    fun editTeam(tem: Team)







}