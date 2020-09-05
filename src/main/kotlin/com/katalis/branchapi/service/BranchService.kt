package com.katalis.branchapi.service

import com.katalis.branchapi.dto.BranchDto
import com.katalis.branchapi.dto.BranchEditDto
import com.katalis.branchapi.dto.PageJsonBranchDto
import com.katalis.branchapi.dto.ResultDto
import com.katalis.branchapi.entity.SearchParameters

interface BranchService {
    // get
    fun getByID(id: String): BranchDto

    fun getAll(searchParameters: SearchParameters): PageJsonBranchDto
    fun getAllByLevel(level: Int, searchParameters: SearchParameters): PageJsonBranchDto
    fun getAllChildren(parentID: String, searchParameters: SearchParameters): PageJsonBranchDto

    // save
    fun save(branchEditDto: BranchEditDto): BranchDto
    fun save(parentID: String, branchEditDto: BranchEditDto): BranchDto

    // update
    fun edit(id: String, branchEditDto: BranchEditDto): BranchDto
    fun reparent(id: String, newParentID: String): BranchDto
    fun removeParent(id: String): BranchDto

    // delete
    fun delete(id: String): ResultDto
}