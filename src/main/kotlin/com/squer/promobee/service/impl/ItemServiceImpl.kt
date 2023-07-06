package com.squer.promobee.service.impl

import com.squer.promobee.service.ItemService
import com.squer.promobee.service.repository.ItemRepository
import com.squer.promobee.service.repository.domain.Item
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class ItemServiceImpl @Autowired constructor(
       private val itemRepository: ItemRepository
): ItemService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun getMaxItemCount(code: String): String {
        return itemRepository.getMaxItemCode(code)
    }

    override fun getItemDataById(id: String): Item {
        return itemRepository.getItemDataById(id)
    }

    override fun getItemDataByCode(code: String): List<Item> {
        return itemRepository.getItemDataByCode(code)
    }

    override fun insertItem(item: Item) {
        return itemRepository.insertItem(item)
    }
}