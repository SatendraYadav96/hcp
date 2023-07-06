package com.squer.promobee.service.impl

import com.squer.promobee.service.VendorService
import com.squer.promobee.service.repository.VendorRepository
import com.squer.promobee.service.repository.domain.Vendor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class VendorServiceImpl @Autowired constructor(
        private val vendorRepository: VendorRepository
) : VendorService{

    override fun getVendorByCode(code: String): List<Vendor>{
        return vendorRepository.getVendorByCode(code)
    }

    override fun insertVendor(vendor: Vendor) {
        return vendorRepository.insertVendor(vendor)
    }

}