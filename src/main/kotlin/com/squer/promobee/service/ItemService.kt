package com.squer.promobee.service

import com.squer.promobee.service.repository.domain.Item

interface ItemService {

    fun getMaxItemCount(code: String): String

    fun getItemDataById(id: String): Item

    fun getItemDataByCode(code: String): Item

    fun insertItem(item: Item)

}