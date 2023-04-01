package com.squer.promobee.service.repository.domain

import com.fasterxml.jackson.databind.util.Named
import com.squer.promobee.security.domain.AuditableEntity
import com.squer.promobee.security.domain.NamedSquerEntity

class ApprovalChainDefinitionADF: AuditableEntity(){
    var buId: NamedSquerEntity?= null
    var statusId: NamedSquerEntity?= null
}