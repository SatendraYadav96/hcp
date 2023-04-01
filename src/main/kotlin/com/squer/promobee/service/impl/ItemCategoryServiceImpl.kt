package com.squer.promobee.service.impl

import com.squer.promobee.service.ItemCategoryService
import com.squer.promobee.service.repository.ItemCategoryRepository
import com.squer.promobee.service.repository.domain.ItemCategoryMaster
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class ItemCategoryServiceImpl @Autowired constructor(
        private val itemCategoryRepository: ItemCategoryRepository
): ItemCategoryService {

    override fun getItemCategory(): List<ItemCategoryMaster>{
        return itemCategoryRepository.getItemCategory()
    }

    override fun getItemCategoryById(id: String): ItemCategoryMaster{
        return itemCategoryRepository.getItemCategoryById(id)
    }
}