package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.ItemCategoryMaster

interface ItemCategoryService {

    fun getItemCategory(): List<ItemCategoryMaster>

    fun getItemCategoryById(id: String): ItemCategoryMaster
}