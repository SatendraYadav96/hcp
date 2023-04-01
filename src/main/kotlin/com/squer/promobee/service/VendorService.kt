package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.Vendor

interface VendorService {

    fun getVendorByCode(code: String): Vendor

    fun insertVendor(vendor: Vendor)
}