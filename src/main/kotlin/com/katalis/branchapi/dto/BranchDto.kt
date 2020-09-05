package com.katalis.branchapi.dto

data class BranchDto(
        var id: String? = null,
        var name: String? = null,
        var level: Int? = null,
        var parentName: String? = null,
        var parentId: String? = null,
        var timezone: String? = null,
        var ehomeId: String? = null,
        var lastActive: Int? = null
)