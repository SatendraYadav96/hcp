package com.squer.promobee.service

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.repository.domain.*


interface MasterService {

    // VENDOR SERVICES

    fun getVendor (status: Int): List<VendorDTO>

    fun getVendorById (id: String): Vendor

    fun addVendor(vnd: Vendor): Map<String, Any>

    fun editVendor (vnd: Vendor)

    //COST CENTER SERVICES

    fun getCostCenter(status: Int): List<CostCenter>

    fun addCostCenter(ccm: MasterCostCenter): Map<String, Any>

    fun editCostCenter (ccm: MasterCostCenter)

    fun getCostCenterById (id: String): CostCenter

    //MASTER SERVICES

    fun getSample(status: Int): List<SampleMaster>

    fun addSample(smp: SampleMaster): Map<String, Any>

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

    fun getApproverDropdown(): List<UserDropdownDTO>




    // BUSINESS UNIT SERVICES

    fun getBusinessUnit(status: Int): List<BU>

    fun getBusinessUnitById (id: String): BU

    fun editBusinessUnit(bu: BU)

    fun addBusinessUnit(bu: BU): Map<String, Any>

    //TEAM SERVICES

    fun getTeam(status: Int): List<Team>

    fun getBrandByTeamId (teamId: String): MutableList<TeamBrand>

    fun getTeamById (id: String): MutableList<Team>

    fun editTeam(tem: MasterTeam)

    fun addTeam(tem: MasterTeam): Map<String, Any>


    //USER SERVICES

    fun getUser(status: String): List<Users>

    fun getUserById (id: String): Users

    fun editUser(usr: MasterUsers)

    fun addUser(usr: MasterUsers)

    //BRAND SERVICES

    fun getBrand(status: Int): List<BrandMaster>

    fun getBrandById (id: String): BrandMaster

    fun editBrand(brd: MasterBrand)

    fun addBrand(brd: MasterBrand): Map<String, Any>

    //RECIPIENT SERVICES

    fun getFieldForce( ff : FieldForceDTO): List<FF>

    fun getFieldForceHistory (id: String): List<RecipientHistoryDTO>

    fun getFieldForceById (id: String): FF

    fun editFieldForce(ff: MasterFF)

    fun addFieldForce(ff: MasterFF): Map<String, Any>

    fun deleteUserMapping (id: String)









}