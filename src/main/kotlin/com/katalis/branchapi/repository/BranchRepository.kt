package com.katalis.branchapi.repository

import com.katalis.branchapi.entity.Branch
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BranchRepository : JpaRepository<Branch, Long> {
    fun findAllByLevel(level: Int, pageable: Pageable): Page<Branch>
    fun findAllByParentID(ParentID: String, pageable: Pageable): Page<Branch>

    // Just querying the name of the branch based on keyword
    @Query("select br from Branch as br where br.name like %:keyword%")
    fun findAllByKeyword(@Param("keyword") keyword: String, pageable: Pageable): Page<Branch>

    @Query("select br from Branch as br where br.level = :level and br.name like %:keyword%")
    fun findAllByLevelAndKeyword(@Param("level") level: Int, @Param("keyword") keyword: String, pageable: Pageable): Page<Branch>

}