package com.katalis.branchapi.controller

import com.katalis.branchapi.dto.*
import com.katalis.branchapi.entity.SearchParameters
import com.katalis.branchapi.service.BranchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("/api/branch")
class BranchController(@Autowired val branchService: BranchService) {

    @GetMapping("/{id}")
    fun getByID(@PathVariable id: String): ResponseEntity<*> {
        // validate data request
        if (id.isEmpty()) {
            return ResponseEntity(ErrorDto("ID cannot be empty!"), HttpStatus.BAD_REQUEST)
        }

        var branchDto: BranchDto
        try {
            branchDto = branchService.getByID(id)
        } catch (e: Exception) {
            println(e.message)
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(branchDto, HttpStatus.OK)
    }

    @GetMapping("/all/page/{page}/{size}")
    fun getAll(@PathVariable page: Int, @PathVariable size: Int): ResponseEntity<*> {
        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAll(SearchParameters(page - 1, size))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/all/sort/{sortColumn}/{sortMode}/page/{page}/{size}")
    fun getAllWithSorting(
            @PathVariable sortColumn: String, @PathVariable sortMode: String,
            @PathVariable page: Int, @PathVariable size: Int): ResponseEntity<*> {

        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        if (sortColumn.isEmpty() || sortMode.isEmpty() || sortMode == "asc" || sortMode == "desc") {
            return ResponseEntity(ErrorDto("Invalid sorting parameter!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAll(SearchParameters(page - 1, size, "", sortColumn, sortMode))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/all/level/{level}/page/{page}/{size}")
    fun getAllByLevel(@PathVariable level: Int, @PathVariable page: Int, @PathVariable size: Int): ResponseEntity<*> {
        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAllByLevel(level, SearchParameters(page - 1, size))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/all/level/{level}/sort/{sortColumn}/{sortMode}/page/{page}/{size}")
    fun getAllByLevelWithSorting(
            @PathVariable level: Int, @PathVariable page: Int,
            @PathVariable sortColumn: String, @PathVariable sortMode: String,
            @PathVariable size: Int): ResponseEntity<*> {

        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        if (sortColumn.isEmpty() || sortMode.isEmpty() || sortMode == "asc" || sortMode == "desc") {
            return ResponseEntity(ErrorDto("Invalid sorting parameter!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAllByLevel(level, SearchParameters(page - 1, size, "", sortColumn, sortMode))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/all/level/{level}/search/{keyword}/page/{page}/{size}")
    fun getAllByLevelAndKeyword(
            @PathVariable level: Int, @PathVariable keyword: String,
            @PathVariable page: Int, @PathVariable size: Int): ResponseEntity<*> {

        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAllByLevelAndKeyword(level, SearchParameters(page - 1, size, keyword))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/all/level/{level}/search/{keyword}/sort/{sortColumn}/{sortMode}/page/{page}/{size}")
    fun getAllByLevelAndKeywordWithSorting(
            @PathVariable level: Int, @PathVariable keyword: String,
            @PathVariable sortColumn: String, @PathVariable sortMode: String,
            @PathVariable page: Int, @PathVariable size: Int): ResponseEntity<*> {

        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        if (sortColumn.isEmpty() || sortMode.isEmpty() || sortMode == "asc" || sortMode == "desc") {
            return ResponseEntity(ErrorDto("Invalid sorting parameter!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAllByLevelAndKeyword(level, SearchParameters(page - 1, size, keyword, sortColumn, sortMode))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/all/children/{parentID}/{page}/{size}")
    fun getAllChildrenByParent(@PathVariable parentID: String, @PathVariable page: Int, @PathVariable size: Int): ResponseEntity<*> {

        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAllChildren(parentID, SearchParameters(page - 1, size))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/attendance/{page}/{size}")
    fun getAllBranchByAttendance(@PathVariable page: Int, @PathVariable size: Int): ResponseEntity<*> {
        // TODO: Need to confirm about docs
        return ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/all/search/{keyword}/page/{page}/{size}")
    fun getAllByKeyword(@PathVariable keyword: String, @PathVariable page: Int, @PathVariable size: Int): ResponseEntity<*> {
        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAllByKeyword(SearchParameters(page - 1, size, keyword))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/all/search/{keyword}/sort/{sortColumn}/{sortMode}/page/{page}/{size}")
    fun getAllByKeywordWithSorting(
            @PathVariable keyword: String, @PathVariable sortColumn: String,
            @PathVariable sortMode: String, @PathVariable page: Int,
            @PathVariable size: Int): ResponseEntity<*> {
        `
        if (page <= 0) {
            return ResponseEntity(ErrorDto("Page must be greater than or equal to 1!"), HttpStatus.BAD_REQUEST)
        }

        if (size <= 1) {
            return ResponseEntity(ErrorDto("Size must be greater than 1!"), HttpStatus.BAD_REQUEST)
        }

        if (sortColumn.isEmpty() || sortMode.isEmpty() || sortMode == "asc" || sortMode == "desc") {
            return ResponseEntity(ErrorDto("Invalid sorting parameter!"), HttpStatus.BAD_REQUEST)
        }

        var result: PageJsonBranchDto
        try {
            result = branchService.getAllByKeyword(SearchParameters(page - 1, size, keyword, sortColumn, sortMode))
        } catch (e: Exception) {
            if (e is JpaObjectRetrievalFailureException) {
                return ResponseEntity(ErrorDto("Data not found!"), HttpStatus.NOT_FOUND)
            } else {
                return ResponseEntity(ErrorDto("Internal error!"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
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
