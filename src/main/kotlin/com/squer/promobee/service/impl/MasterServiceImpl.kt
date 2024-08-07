package com.squer.promobee.service.impl

import com.squer.promobee.controller.dto.*
import com.squer.promobee.service.MasterService
import com.squer.promobee.service.repository.MasterRepository
import com.squer.promobee.service.repository.domain.*
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@Slf4j
class MasterServiceImpl @Autowired constructor(
    private val masterRepository: MasterRepository


): MasterService {

    // VENDOR SERVICE IMPL

    override fun getVendor(status: Int): List<VendorDTO> {
        return masterRepository.getVendor(status)
    }

    override fun getVendorById(id: String): Vendor {
        return masterRepository.getVendorById(id)
    }

    override fun addVendor(vnd: Vendor): Map<String, Any> {
        return masterRepository.addVendor(vnd)
    }

    override fun editVendor(vnd: Vendor) {
        return masterRepository.editVendor(vnd)
    }


    //COST CENTER IMPL

    override fun getCostCenter(status: Int): List<CostCenter> {
        return masterRepository.getCostCenter(status)
    }

    override fun addCostCenter(ccm: MasterCostCenter): Map<String, Any> {
        return masterRepository.addCostCenter(ccm)
    }

    override fun editCostCenter(ccm: MasterCostCenter) {
        return masterRepository.editCostCenter(ccm)
    }

    override fun getCostCenterById(id: String): CostCenter{
        return masterRepository.getCostCenterById(id)
    }


    //SAMPLE IMPL

    override fun getSample(status: Int): List<SampleMaster> {
        return masterRepository.getSample(status)
    }

    override fun addSample(smp: SampleMaster): Map<String, Any> {
        return masterRepository.addSample(smp)
    }

    override fun editSample(smp: SampleMaster) {
        return masterRepository.editSample(smp)
    }

    override fun getSampleById(id: String): SampleMaster {
        return masterRepository.getSampleById(id)
    }


    //DROPDOWN IMPL

    override fun getBusinessUnitDropdown(): List<BU> {
        return masterRepository.getBusinessUnitDropdown()
    }

    override fun getBrandDropdown(): List<BrandMaster> {
        return masterRepository.getBrandDropdown()
    }


    override fun getDivisionDropdown(): List<Division> {
        return masterRepository.getDivisionDropdown()
    }

    override fun getTeamDropdown(): List<Team> {
        return masterRepository.getTeamDropdown()
    }

    override fun getCostCenterDropdown(): List<CostCenter> {
        return masterRepository.getCostCenterDropdown()
    }

    override fun getItemCodeDropdown(): List<ItemDrodownDTO> {
        return masterRepository.getItemCodeDropdown()
    }

    override fun getRecipientDropdown(): List<RecipientDropDownDTO> {
        return masterRepository.getRecipientDropdown()
    }

    override fun getInvoiceDropdown(): List<InvoiceDropdownDTO> {
        return masterRepository.getInvoiceDropdown()
    }

    override fun getTransporter(): List<TransporterDropdownDTO> {
        return masterRepository.getTransporter()
    }

    override fun getLegalEntityDropdown(): List<LegalEntity> {
        return masterRepository.getLegalEntityDropdown()
    }

    override fun getRecipientDesignationDropdown(): List<RecipientDesignationDropdownDTO> {
        return masterRepository.getRecipientDesignationDropdown()
    }

    override fun getUserDesignationDropdown(): List<UserDesignationDropdownDTO> {
        return masterRepository.getUserDesignationDropdown()
    }

    override fun getUserDropdown(): List<UserDropdownDTO> {
        return masterRepository.getUserDropdown()
    }

    override fun getApproverDropdown(): List<ApproverDropdownDTO> {
        return masterRepository.getApproverDropdown()
    }

    override fun getOwnerDropdown(): List<ApproverDropdownDTO> {
        return masterRepository.getOwnerDropdown()
    }

    override fun getTseDropdown(): List<ApproverDropdownDTO> {
        return masterRepository.getTseDropdown()
    }


    // BUSINESS UNIT IMPL

    override fun getBusinessUnit(status: Int): List<BU> {
        return masterRepository.getBusinessUnit(status)
    }

    override fun getDivision(status: Int): List<Division> {
        return masterRepository.getDivision(status)
    }

    override fun getBusinessUnitById(id: String): BU {
        return masterRepository.getBusinessUnitById(id)
    }

    override fun editBusinessUnit(bu: BU) {
        return masterRepository.editBusinessUnit(bu)
    }

    override fun addBusinessUnit(bu: BU): Map<String, Any> {
        return masterRepository.addBusinessUnit(bu)
    }

    // TEAM IMPL

    override fun getTeam(status: Int): List<Team> {
        return masterRepository.getTeam(status)
    }

    override fun getBrandByTeamId(teamId: String): MutableList<TeamBrand> {
        return masterRepository.getBrandByTeamId(teamId)
    }

    override fun getTeamById(id: String): MutableList<Team> {
        return masterRepository.getTeamById(id)
    }

    override fun editTeam(tem: MasterTeam) {
        return masterRepository.editTeam(tem)
    }

    override fun addTeam(tem: MasterTeam): Map<String, Any> {
        return masterRepository.addTeam(tem)
    }


    //USER IMPL

    override fun getUser(status: String): List<Users> {
        return masterRepository.getUser(status)
    }

    override fun getUserById(id: String): Users {
        return masterRepository.getUserById(id)
    }

    override fun editUser(usr: MasterUsers) {
        return masterRepository.editUser(usr)
    }

    override fun addUser(usr: MasterUsers): Map<String, Any> {
        return masterRepository.addUser(usr)
    }


    //BRAND IMPL

    override fun getBrand(status: Int): List<BrandMaster> {
        return masterRepository.getBrand(status)
    }

    override fun getBrandById(id: String): BrandMaster {
        return masterRepository.getBrandById(id)
    }

    override fun editBrand(brd: MasterBrand) {
        return masterRepository.editBrand(brd)
    }

    override fun addBrand(brd: MasterBrand): Map<String, Any> {
        return masterRepository.addBrand(brd)
    }


    //RECIPIENT IMPL

    override fun getFieldForce(ff : FieldForceDTO): List<FF> {
        return masterRepository.getFieldForce(ff)
    }

    override fun getFieldForceHistory(id: String): List<RecipientHistoryDataDTO> {
        return masterRepository.getFieldForceHistory(id)
    }

    override fun getFieldForceById(id: String): FF {
        return masterRepository.getFieldForceById(id)
    }

    override fun editFieldForce(ff: MasterFF) {
        return masterRepository.editFieldForce(ff)
    }

    override fun addFieldForce(ff: MasterFF): Map<String, Any> {
        return masterRepository.addFieldForce(ff)
    }

    override fun deleteUserMapping(id: String) {
        return masterRepository.deleteUserMapping(id)
    }


}
