package com.squer.promobee.controller

import com.squer.promobee.api.v1.enums.UserMenuEnum
import com.squer.promobee.api.v1.enums.UserRoleEnum
import com.squer.promobee.controller.dto.MenuPojo
import com.squer.promobee.security.domain.User
import com.squer.promobee.security.jwt.JwtTokenProvider
import com.squer.promobee.service.MenuActionService
import com.squer.promobee.service.ui.FormService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@Slf4j
open class UIController @Autowired constructor(
    private val menuActionService: MenuActionService,
    private val formService: FormService,
    private val tokenProvider: JwtTokenProvider
) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        val mapOfMenusForDesignation = mapOf(
                UserRoleEnum.HUB_MANAGER_ID.id to listOf<UserMenuEnum>(
                        UserMenuEnum.GRN_UPLOAD_LOG,
                        UserMenuEnum.GRN_ACKNOWLEDGE,
                        UserMenuEnum.PICKING_SLIP,
                        UserMenuEnum.MONTHLY_DISPATCH,
                        UserMenuEnum.SPECIAL_DISPATCH,
                        UserMenuEnum.VIRTUAL_DISPATCH,
                        UserMenuEnum.GROUP_INVOICE,
                        UserMenuEnum.SEARCH_INVOICE,
                        UserMenuEnum.DELIVERY_UPDATE,
                        UserMenuEnum.VENDOR,
                        UserMenuEnum.COST_CENTER,
                        UserMenuEnum.SAMPLE,
                        UserMenuEnum.SEARCH_INVENTORY,
                        UserMenuEnum.ITEM_WISE_REPORT,
                        UserMenuEnum.STOCK_LEDGER_REPORT,
                        UserMenuEnum.NEAR_TO_EXPIRY_REPORT,
                        UserMenuEnum.AGEING_REPORT,
                        UserMenuEnum.INVENTORY_REPORT,
                        UserMenuEnum.ADD_HSN,
                        UserMenuEnum.INVOICE_BOX_WEIGHT,
                        UserMenuEnum.FF_REPORT,
                        UserMenuEnum.PURCHASE_REPORT,
                        UserMenuEnum.DISPATCH_REPORT,
                        UserMenuEnum.DISPATCH_REGISTERS,
                        UserMenuEnum.DEVIATION_REPORT,
                        UserMenuEnum.ITEM_CONSUMPTION_REPORT,
                        UserMenuEnum.VIRTUAL_RECONCILIATION,
                        UserMenuEnum.INVENTORY_REVERSAL_REPORT,
                        UserMenuEnum.SHIP_ROCKET_REPORT
                ),
                UserRoleEnum.PRODUCT_MANAGER_ID.id to listOf<UserMenuEnum>(
                        UserMenuEnum.MONTHLY_ALLOCATION,
                        UserMenuEnum.SPECIAL_ALLOCATION,
                        UserMenuEnum.VIRTUAL_ALLOCATION,
                        UserMenuEnum.PICKING_SLIP,
                        UserMenuEnum.FF_REPORT,
                        UserMenuEnum.INVENTORY_REPORT,
                        UserMenuEnum.PURCHASE_REPORT,
                        UserMenuEnum.NEAR_TO_EXPIRY_REPORT,
                        UserMenuEnum.DISPATCH_REPORT,
                        UserMenuEnum.DISPATCH_REGISTERS,
                        UserMenuEnum.ITEM_CONSUMPTION_REPORT,
                        UserMenuEnum.INVENTORY_REVERSAL_REPORT,
                        UserMenuEnum.ALLOCATION_REPORT
                ),
            UserRoleEnum.BEX_ID.id to listOf<UserMenuEnum>(
                UserMenuEnum.BUSINESS_UNITS,
                UserMenuEnum.TEAM,
                UserMenuEnum.USER,
                UserMenuEnum.COST_CENTER,
                UserMenuEnum.BRAND,
                UserMenuEnum.SAMPLE,
                UserMenuEnum.FF_MASTER,
                UserMenuEnum.MONTHLY_INPUT_PLAN,
                UserMenuEnum.SPECIAL_DISPATCHES,
                UserMenuEnum.VIRTUAL_DISPATCHES,
                UserMenuEnum.FF_REPORT,
                UserMenuEnum.INVENTORY_REPORT,
                UserMenuEnum.DISPATCH_REGISTERS,
                UserMenuEnum.VIRTUAL_RECONCILIATION,


            ),

            UserRoleEnum.BU_HEAD_ID.id to listOf<UserMenuEnum>(
                UserMenuEnum.SPECIAL_DISPATCHES,
                UserMenuEnum.FF_REPORT,
                UserMenuEnum.PURCHASE_REPORT,
                UserMenuEnum.NEAR_TO_EXPIRY_REPORT,
                UserMenuEnum.DISPATCH_REPORT,
                UserMenuEnum.DISPATCH_REGISTERS,

            ),


            UserRoleEnum.COMPLIANCE_ADMIN_ID.id to listOf<UserMenuEnum>(


                ),

            UserRoleEnum.TEAM_SUPPORT_EXECUTIVE_ID.id to listOf<UserMenuEnum>(

                UserMenuEnum.MONTHLY_ALLOCATION,
                UserMenuEnum.SPECIAL_ALLOCATION,
                UserMenuEnum.VIRTUAL_ALLOCATION


            )



        )

        val parentMenus = mapOf(
                UserRoleEnum.HUB_MANAGER_ID.id to listOf<UserMenuEnum>(
                    UserMenuEnum.DASHBOARD,
                    UserMenuEnum.MASTERS,
                    UserMenuEnum.GOODS_RECEIPT,
                    UserMenuEnum.INVENTORY,
                    UserMenuEnum.DISPATCH_AND_INVOICING,
//                    UserMenuEnum.ITEM_REVALIDATION,
                    UserMenuEnum.HSN_AND_INVOICE,
                    UserMenuEnum.REPORT
                ),
                UserRoleEnum.PRODUCT_MANAGER_ID.id to listOf<UserMenuEnum>(
                    UserMenuEnum.DASHBOARD,
                    UserMenuEnum.ALLOCATION,
                    UserMenuEnum.ITEM_REVALIDATION,
                    UserMenuEnum.MASS_REVALIDATION,
                    UserMenuEnum.REPORT
                ),
            UserRoleEnum.BEX_ID.id to listOf<UserMenuEnum>(
                UserMenuEnum.DASHBOARD,
                UserMenuEnum.MASTERS,
                UserMenuEnum.APPROVALS,
                UserMenuEnum.REPORT,
                UserMenuEnum.FF_UPLOAD,
                UserMenuEnum.VIRTUAL_SAMPLE_UPLOAD,
                UserMenuEnum.COMPLIANCE_PROCESS,
                UserMenuEnum.OPTIMA_MAIL_LOGS,
                UserMenuEnum.OVERSAMPLING_DETAILS,
                UserMenuEnum.MANAGEMENT_DASHBOARD,
                UserMenuEnum.BATCH_RECONCILIATION,

                ),

            UserRoleEnum.BU_HEAD_ID.id to listOf<UserMenuEnum>(
                UserMenuEnum.DASHBOARD,
                UserMenuEnum.APPROVALS,
                UserMenuEnum.REPORT,
                UserMenuEnum.RECIPIENT_BLOCKED,
                UserMenuEnum.COMPLIANCE_DETAILS,

            ),

            UserRoleEnum.COMPLIANCE_ADMIN_ID.id to listOf<UserMenuEnum>(
                UserMenuEnum.DASHBOARD,
                UserMenuEnum.MASTER_BLOCKED_LIST,
                UserMenuEnum.NON_COMPLIANCE_UNBLOCK,
                UserMenuEnum.NON_COMPLIANCE_UPLOAD,
                UserMenuEnum.OVER_SAMPLING_UPLOAD,
                UserMenuEnum.OVER_SAMPLING_DETAILS_UPLOAD,
                UserMenuEnum.OVERSAMPLING_DETAILS,
                UserMenuEnum.MATERIAL_EXPIRY_UPLOAD,
                UserMenuEnum.OPTIMA_MAIL_LOGS,
            ),

            UserRoleEnum.TEAM_SUPPORT_EXECUTIVE_ID.id to listOf<UserMenuEnum>(
                UserMenuEnum.DASHBOARD,
                UserMenuEnum.ALLOCATION,
                UserMenuEnum.COMPLIANCE_PROCESS,
                UserMenuEnum.OVERSAMPLING_DETAILS

            )
        )
    }

    @GetMapping("/menus")
    open fun getMenusForUser(): ResponseEntity<*> {
        val user = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as User
        val designation = user.userDesignation!!.id!!
        val menus =  mapOfMenusForDesignation[designation]!!.groupBy{it.parentId}
        var menusToReturn = mutableListOf<MenuPojo>()
        parentMenus[designation]!!.forEach{
            val children = menus[it.key]
            menusToReturn.add(MenuPojo(it, children))
        }

        return ResponseEntity(menusToReturn, HttpStatus.OK)
    }

    @GetMapping("/form/{formCode}")
    open fun getFormMeta(@PathVariable("formCode")formCode: String): ResponseEntity<*> {
        return ResponseEntity(formService.getFormMeta(formCode), HttpStatus.OK)
    }
}
