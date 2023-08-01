package com.squer.promobee.service

import com.squer.promobee.controller.dto.TseListDTO
import com.squer.promobee.controller.dto.UserDTO


interface NewAllocationService {

   fun getTseList( id: String): List<TseListDTO>

   fun assignTse( id: String): List<TseListDTO>

   fun unAssignTse( id: String): List<TseListDTO>

   fun getBrandManagerForTse( id: String): List<UserDTO>



}