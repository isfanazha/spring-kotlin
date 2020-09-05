package com.katalis.branchapi.repository

import com.katalis.branchapi.entity.Branch
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BranchRepository : JpaRepository<Branch, Long> {
    fun findAllByLevel(level: Int, pageable: Pageable): Page<Branch>
    fun findAllByParentID(ParentID: String, pageable: Pageable): Page<Branch>
}