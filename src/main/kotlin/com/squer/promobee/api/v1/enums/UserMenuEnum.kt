package com.squer.promobee.api.v1.enums

enum class UserMenuEnum(val label: String, val key: String, val title: String, val path: String, val parentId: String){
    DASHBOARD(" Dashboard", "dashboard", "dashboard", "", ""),
    BEX_DASHBOARD("BexDashboard", "bexdashboard", "bexdashboard", "/home/bexdashboard", ""),
    BM_DASHBOARD("BmDashboard", "bmdashboard", "bmdashboard", "/home/bmdashboard", ""),
    BUHEAD_DASHBOARD("Buhead Dashboard", "buhead_dashboard", "buhead_dashboard", "/home/buheaddashboard", ""),
    COMPLIANCE_ADMIN_DASHBOARD("Compliance Admin Dashboard", "compliance_admin_dashboard", "compliance_admin_dashboard", "", ""),
    TSE_DASHBOARD("Tse Dashboard", "tse_dashboard", "tse_dashboard", "", ""),
    RBM_DASHBOARD("Rbm Dashboard", "rbm_dashboard", "rbm_dashboard", "/home/rbmdashboard", ""),
    NSM_DASHBOARD("Nsm Dashboard", "nsm_dashboard", "nsm_dashboard", "", ""),
    GOODS_RECEIPT("Goods Receipt", "goods_receipt", "goods_receipt", "", ""),
    GRN_UPLOAD_LOG("GRN Upload Log", "grn_upload_log", "grn_upload_log", "/home/grn/logs", "goods_receipt"),
    GRN_ACKNOWLEDGE("Acknowledge", "acknowledge", "acknowledge", "/home/grn/acknowledge", "goods_receipt"),
    DISPATCH_AND_INVOICING("Dispatch & Invoicing", "dispatch_invoicing", "dispatch_invoicing", "", ""),
    PICKING_SLIP("Picking Slip", "picking_slip", "picking_slip", "/home/dispatchInvoicing/pickingSlip", "dispatch_invoicing"),
    MONTHLY_DISPATCH("Monthly Dispatch", "monthly_dispatch","monthly_dispatch", "/home/dispatchInvoicing/monthlyDispatch", "dispatch_invoicing"),
    SPECIAL_DISPATCH("Special Dispatch", "special_dispatch", "special_dispatch","/home/dispatchInvoicing/specialDispatch", "dispatch_invoicing"),
    VIRTUAL_DISPATCH("Virtual Dispatch", "virtual_dispatch", "virtual_dispatch", "/home/dispatchInvoicing/virtualDispatch", "dispatch_invoicing"),
    GROUP_INVOICE("Group Invoice", "group_invoice", "group_invoice", "/home/dispatchInvoicing/groupInvoice", "dispatch_invoicing"),
    SEARCH_INVOICE("Search Invoice", "search_invoice", "search_invoice","/home/dispatchInvoicing/searchInvoice", "dispatch_invoicing"),
    DELIVERY_UPDATE("Delivery Update", "delivery_update", "delivery_update", "/home/dispatchInvoicing/deliveryUpdate", "dispatch_invoicing"),
    MASTERS("Masters", "masters", "masters", "", ""),
    VENDOR("Vendor", "vendor", "vendor", "/home/masters/vendor", "masters"),
    TEAM("Sub Team", "team", "team", "/home/masters/team", "masters"),
    USER("User", "user", "user", "/home/masters/user", "masters"),
    BRAND("Brand", "brand", "brand", "/home/masters/brand", "masters"),
    FF_MASTER("FF", "ff master", "ff master", "/home/masters/ffMaster", "masters"),
    BUSINESS_UNITS("Team", "business Unit", "business Unit", "/home/masters/businessUnit", "masters"),
    COMMON_MASTER("Common Master", "common master", "common master", "/home/masters/commonMaster", "masters"),
    COST_CENTER("costCenter", "costCenter", "costCenter", "/home/masters/costCenter", "masters"),
    SAMPLE("sample", "sample", "sample", "/home/masters/samples", "masters"),
    INVENTORY("Inventory", "inventory", "inventory","", ""),
    SEARCH_INVENTORY("Search", "search_inventory","search_inventory", "/home/inventory/search", "inventory"),
    ITEM_WISE_REPORT("Item Wise Report", "item_wise_report", "item_wise_report", "/home/inventory/itemWiseReport", "inventory"),
    STOCK_LEDGER_REPORT("Stock Ledger Report", "stock_ledger_report", "stock_ledger_report", "/home/inventory/stockLedgerReport", "inventory"),
    NEAR_TO_EXPIRY_REPORT("Near To Expiry Report","near_to_expiry_report", "near_to_expiry_report", "/home/inventory/nearToExpiryReport", "inventory"),
    AGEING_REPORT("Ageing Report", "ageing_report", "ageing_report", "/home/inventory/ageingReport", "inventory"),
    INVENTORY_REPORT("Inventory Report", "inventory_report", "inventory_report", "/home/inventory/inventoryReport", "report"),
    ITEM_REVALIDATION("Item Revalidation", "item_revalidation", "item_revalidation", "/home/itemRevalidation", ""),
    HSN_AND_INVOICE("HSN & Invoice", "hsn_invoice", "hsn_invoice", "", ""),
    ADD_HSN("Add HSN", "add_hsn", "add_hsn", "/home/hsnInvoice/addHsn", "hsn_invoice"),
    INVOICE_BOX_WEIGHT("Invoice Box & Weight", "invoice_box_weight", "invoice_box_weight", "/home/hsnInvoice/addInvoice", "hsn_invoice"),
    REPORT("Report", "report", "report", "", ""),
    FF_REPORT("FF Report", "receipt_report", "receipt_report","/home/report/recipientReport", "report"),
    PURCHASE_REPORT("Purchase Report", "purchase_report", "purchase_report", "/home/report/purchaseReport", "report"),
    DISPATCH_REPORT("Dispatch Report", "dispatch_report", "dispatch_report", "/home/report/dispatchReport", "report"),
    DISPATCH_REGISTERS("Dispatch Registers", "dispatch_registers", "dispatch_registers", "/home/report/dispatchRegisterReport", "report"),
    DEVIATION_REPORT("Deviation Report", "deviation_report", "deviation_report", "/home/report/deviationReport" , "report"),
    ITEM_CONSUMPTION_REPORT("Item Consumption Report", "item_consumption_report", "item_consumption_report", "/home/report/itemConsumptionReport", "report"),
    VIRTUAL_RECONCILIATION("Virtual Reconciliation", "virtual_reconciliation", "virtual_reconciliation", "/home/report/virtualReconciliation", "report"),
    INVENTORY_REVERSAL_REPORT("Inventory Reversal Report", "inventory_reversal_report", "inventory_reversal_report", "/home/report/inventoryReversalReport", "report"),
    SHIP_ROCKET_REPORT("Ship Rocket Report", "ship_rocket_report", "ship_rocket_report", "/home/report/shipRocketReport", "report"),
    ADMIN("Admin", "admin", "admin", "", ""),
    TSE("Tse", "tse", "tse", "/home/admin/assign", "admin"),
    ALLOCATION("Allocation", "allocation", "allocation", "", ""),
    MONTHLY_ALLOCATION("Monthly Allocation", "monthly_allocation", "monthly_allocation", "/home/allocations/monthly/create","allocation"),
    SPECIAL_ALLOCATION("Special Allocation", "special_allocation", "special_allocation", "/home/allocations/special/create", "allocation"),
    VIRTUAL_ALLOCATION("Virtual Allocation", "virtual_allocation", "virtual_allocation", "/home/allocations/virtual/create", "allocation"),
    MASS_REVALIDATION("Mass Revalidation", "mass_revalidation", "mass_revalidation", "/home/massRevalidation", ""),
    ALLOCATION_REPORT("Allocation Report", "allocation_report", "allocation_report", "/home/report/allocationReport", "report"),
    APPROVALS("Approvals", "approvals", "approvals", "", ""),
    MONTHLY_INPUT_PLAN("Monthly Input Plan", "monthly_input_plan", "monthly_input_plan", "/home/approvals/monthlyInputPlan", "approvals"),
    SPECIAL_DISPATCHES("Special Dispatches", "special_dispatches", "special_dispatches", "/home/approvals/specialDispatches", "approvals"),
    VIRTUAL_DISPATCHES("Virtual Dispatches", "virtual_dispatches", "virtual_dispatches", "/home/approvals/virtualDispatches", "approvals"),
    VIRTUAL_DISPATCHE("Virtual Dispatche", "virtual_dispatche", "virtual_dispatche", "/home/approvals/virtualDispatches", "report"),
    FF_UPLOAD("FF Upload ", "ff_master", "ff_master", "/home/upload/ffMasterUplaod", ""),
    VIRTUAL_SAMPLE_UPLOAD("Virtual Sample Upload", "virtual_Sample_Upload", "virtual_Sample_Upload", "/home/upload/virtualSampleUpload", ""),
    COMPLIANCE_PROCESS("Compliance Process", "compliance_Process", "compliance_Process", "/home/master/ffUnBlockList", ""),
    OPTIMA_MAIL_LOGS("Optima Mail Logs", "optima_mail_logs", "optima_mail_logs", "/home/compliance/optimaMailSendLogs", ""),
    OVERSAMPLING_DETAILS("Over Sampling Details", "over_sampling_details", "over_sampling_details", "/home/compliance/complianceDetailsList", ""),
    MANAGEMENT_DASHBOARD("Management Dashboard", "management_dashboard", "management_dashboard", "/home/managementDashboard", ""),
    BATCH_RECONCILIATION("Batch Reconciliation", "batch_reconciliation", "batch_reconciliation", "/home/managementDashboard/reconciliation", ""),
    RECIPIENT_BLOCKED("FF Blocked", "ff_blocked", "ff_blocked", "/home/optimaMi/recommendedListOfBlocking", ""),
    COMPLIANCE_DETAILS("Compliance Details", "compliance_details", "compliance_details", "/home/compliance/complianceDetailsList", ""),
    NON_COMPLIANCE_UNBLOCK("Non Compliance Unblock", "non_compliance_unblock", "non_compliance_unblock", "/home/master/ffUnBlockList", ""),
    MASTER_BLOCKED_LIST("Master Blocked List", "master_blocked_list", "master_blocked_list", "/home/master/ffBlockList", ""),
    NON_COMPLIANCE_UPLOAD("Non Compliance Upload", "non_compliance_upload", "non_compliance_upload", "/home/optimaMi/upload", ""),
    OVER_SAMPLING_UPLOAD("Over Sampling Upload", "over_sampling_upload", "over_sampling_upload", "/home/compliance/upload", ""),
    OVER_SAMPLING_DETAILS_UPLOAD("Over Sampling Details Upload", "over_sampling_details_upload", "over_sampling_details_upload", "/home/complianceDetails/upload", ""),
    MATERIAL_EXPIRY_UPLOAD("Material Expiry Upload", "material_expiry_upload", "material_expiry_upload", "/home/optimaMaterial/upload", ""),

}
