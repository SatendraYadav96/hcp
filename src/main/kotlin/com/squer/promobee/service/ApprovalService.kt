package com.squer.promobee.service

import com.squer.promobee.controller.dto.MontlyApprovalBexDTO
import com.squer.promobee.controller.dto.RecipientReportDTO



interface ApprovalService {

    fun getMonthlyApprovalForBex(month : Int,  year : Int,  userId : String,  userDesgId : String) : List<MontlyApprovalBexDTO>



}