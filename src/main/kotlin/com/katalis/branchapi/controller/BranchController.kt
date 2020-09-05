package com.katalis.branchapi.controller

import com.katalis.branchapi.dto.BranchDto
import com.katalis.branchapi.dto.BranchEditDto
import com.katalis.branchapi.dto.PageJsonBranchDto
import com.katalis.branchapi.dto.ResultDto
import com.katalis.branchapi.entity.SearchParameters
import com.katalis.branchapi.service.BranchService
import org.h2.mvstore.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/branch")
class BranchController(@Autowired val branchService: BranchService) {

    @GetMapping("/{id}")
    fun getByID(@PathVariable id: String): BranchDto {
        return branchService.getByID(id)
    }

    @GetMapping("/all/page/{page}/{size}")
    fun getAll(@PathVariable page: Int, @PathVariable size: Int): PageJsonBranchDto {
        if (page <= 0) {
            // TODO: Handle error
            return PageJsonBranchDto()
        }

        if (size <= 1) {
            // TODO: Handle error
            return PageJsonBranchDto()
        }

        return branchService.getAll(SearchParameters(page - 1, size))
    }

    @GetMapping("/all/level/{level}/{page}/{size}")
    fun getAllByLevel(@PathVariable level: Int, @PathVariable page: Int, @PathVariable size: Int): PageJsonBranchDto {
        return branchService.getAllByLevel(level, SearchParameters(page - 1, size))
    }

    @GetMapping("/all/children/{parentID}/{page}/{size}")
    fun getAllChildrenByParent(@PathVariable parentID: String, @PathVariable page: Int, @PathVariable size: Int): PageJsonBranchDto {
        return branchService.getAllChildren(parentID, SearchParameters(page - 1, size))
    }

    @PostMapping("/save")
    fun save(@RequestBody branchEditDto: BranchEditDto): BranchDto {
        return branchService.save(branchEditDto)
    }

    @PostMapping("/save/{parentID}")
    fun saveWithParentID(@PathVariable parentID: String, @RequestBody branchEditDto: BranchEditDto): BranchDto {
        return branchService.save(parentID, branchEditDto)
    }

    @PostMapping("/reparent/{id}/{newParentID}")
    fun reparent(@PathVariable id: String, @PathVariable newParentID: String): BranchDto {
        return branchService.reparent(id, newParentID)
    }

    @PostMapping("/removeParent/{id}")
    fun removeParent(@PathVariable id: String): BranchDto {
        return branchService.removeParent(id)
    }

    @PutMapping("/edit/{id}")
    fun edit(@PathVariable id: String, @RequestBody branchEditDto: BranchEditDto): BranchDto {
        return branchService.edit(id, branchEditDto)
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable id: String): ResultDto {
        return branchService.delete(id)
    }
}