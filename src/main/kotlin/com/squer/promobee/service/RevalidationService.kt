package com.squer.promobee.service

import com.squer.promobee.controller.dto.ItemRevalidationDTO



interface RevalidationService {

    fun getItemRevalidationHub( itemId: String,  revldType:String) : List<ItemRevalidationDTO>





}