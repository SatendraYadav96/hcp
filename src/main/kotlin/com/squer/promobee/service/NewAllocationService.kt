package com.squer.promobee.service

import com.squer.promobee.controller.dto.TseListDTO


interface NewAllocationService {

   fun getTseList( id: String): List<TseListDTO>

   fun assignTse( id: String): List<TseListDTO>

   fun unAssignTse( id: String): List<TseListDTO>



}