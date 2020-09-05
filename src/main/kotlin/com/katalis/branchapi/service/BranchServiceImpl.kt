package com.katalis.branchapi.service

import com.katalis.branchapi.dto.BranchDto
import com.katalis.branchapi.dto.BranchEditDto
import com.katalis.branchapi.dto.PageJsonBranchDto
import com.katalis.branchapi.dto.ResultDto
import com.katalis.branchapi.entity.Branch
import com.katalis.branchapi.entity.SearchParameters
import com.katalis.branchapi.repository.BranchRepository
import com.katalis.branchapi.utils.getTimeNow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BranchServiceImpl(@Autowired var branchRepository: BranchRepository) : BranchService {

    override fun getByID(id: String): BranchDto {
        val branch = branchRepository.getOne(id.toLong())
        return branchToBranchDto(branch)
    }

    override fun getAll(searchParameters: SearchParameters): PageJsonBranchDto {
        var pageRequest: Pageable

        var branchPage = if (searchParameters.sortColumn == "" || searchParameters.sortMode == "") {
            pageRequest = PageRequest.of(searchParameters.page, searchParameters.size)
            branchRepository.findAll(pageRequest)
        } else {
            pageRequest = if (searchParameters.sortMode == "desc") {
                PageRequest
                        .of(searchParameters.page, searchParameters.size, Sort.by(searchParameters.sortColumn).descending())
            } else {
                PageRequest
                        .of(searchParameters.page, searchParameters.size, Sort.by(searchParameters.sortColumn).ascending())
            }

            branchRepository.findAll(pageRequest)
        }

        return branchPageToPageJsonBranchDto(branchPage)
    }

    override fun getAllByLevel(level: Int, searchParameters: SearchParameters): PageJsonBranchDto {
        var pageRequest = PageRequest.of(searchParameters.page, searchParameters.size)
        var branchPage = branchRepository.findAllByLevel(level, pageRequest)

        return branchPageToPageJsonBranchDto(branchPage)
    }

    override fun getAllChildren(parentID: String, searchParameters: SearchParameters): PageJsonBranchDto {
        var pageRequest = PageRequest.of(searchParameters.page, searchParameters.size)
        var branchPage = branchRepository.findAllByParentID(parentID, pageRequest)

        return branchPageToPageJsonBranchDto(branchPage)
    }

    override fun getAllByKeyword(searchParameters: SearchParameters): PageJsonBranchDto {
        var pageRequest: Pageable

        var branchPage = if (searchParameters.sortColumn == "" || searchParameters.sortMode == "") {
            pageRequest = PageRequest.of(searchParameters.page, searchParameters.size)
            branchRepository.findAllByKeyword(searchParameters.keyword.toString(), pageRequest)
        } else {
            pageRequest = if (searchParameters.sortMode == "desc") {
                PageRequest
                        .of(searchParameters.page, searchParameters.size, Sort.by(searchParameters.sortColumn).descending())
            } else {
                PageRequest
                        .of(searchParameters.page, searchParameters.size, Sort.by(searchParameters.sortColumn).ascending())
            }

            branchRepository.findAllByKeyword(searchParameters.keyword.toString(), pageRequest)
        }

        return branchPageToPageJsonBranchDto(branchPage)
    }

    override fun getAllByLevelAndKeyword(level: Int, searchParameters: SearchParameters): PageJsonBranchDto {
        var pageRequest = PageRequest.of(searchParameters.page, searchParameters.size)
        var branchPage = branchRepository.findAllByLevelAndKeyword(level, searchParameters.keyword.toString(), pageRequest)

        return branchPageToPageJsonBranchDto(branchPage)
    }

    override fun save(branchEditDto: BranchEditDto): BranchDto {
        // set timezone to current time
        branchEditDto.timezone = getTimeNow()
        return branchToBranchDto(branchRepository.save(branchEditDtoToBranch(branchEditDto)))
    }

    override fun save(parentID: String, branchEditDto: BranchEditDto): BranchDto {
        // set timezone to current time
        branchEditDto.timezone = getTimeNow()

        var branch = branchEditDtoToBranch(branchEditDto)
        branch.parentID = parentID

        return branchToBranchDto(branchRepository.save(branch))
    }

    override fun edit(id: String, branchEditDto: BranchEditDto): BranchDto {
        var branch = branchRepository.getOne(id.toLong())
        var branchEdit = branchEditDtoToBranch(branchEditDto)
        // populate edit data
        branch.name = branchEdit.name
        branch.timezone = getTimeNow()

        return branchToBranchDto(branchRepository.save(branch))
    }

    override fun delete(id: String): ResultDto {
        val branch = branchRepository.getOne(id.toLong())
        branchRepository.delete(branch)
        return ResultDto("Data has been deleted successfully!", 0)
    }

    override fun reparent(id: String, newParentID: String): BranchDto {
        var branch = branchRepository.getOne(id.toLong())
        // reparent
        branch.parentID = newParentID

        return branchToBranchDto(branchRepository.save(branch))
    }

    override fun removeParent(id: String): BranchDto {
        var branch = branchRepository.getOne(id.toLong())
        // remove parent set to null
        branch.parentID = null

        return branchToBranchDto(branchRepository.save(branch))
    }

    fun branchToBranchDto(branch: Branch): BranchDto {
        return BranchDto(branch.id.toString(), branch.name, branch.level, branch.parentName, branch.parentID, branch.timezone, branch.ehomeId, branch.lastActive)
    }

    fun branchEditDtoToBranch(branchEditDto: BranchEditDto): Branch {
        var branch = Branch()

        branch.name = branchEditDto.name
        branch.timezone = branchEditDto.timezone

        return branch
    }

    fun branchPageToPageJsonBranchDto(branchPage: Page<Branch>): PageJsonBranchDto {
        var pageJsonBranchDto = PageJsonBranchDto()
        // populate data
        pageJsonBranchDto.totalElements = branchPage.numberOfElements
        pageJsonBranchDto.totalPages = branchPage.totalPages

        var branchList: MutableList<BranchDto> = mutableListOf()
        for (branch in branchPage.content) {
            branchList.add(branchToBranchDto(branch))
        }

        pageJsonBranchDto.elements = branchList.toList()

        return pageJsonBranchDto
    }
}