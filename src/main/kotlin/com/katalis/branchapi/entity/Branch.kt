package com.katalis.branchapi.entity

import javax.persistence.*

@Entity
@Table(name = "branch")
@Access(value = AccessType.FIELD)
data class Branch(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long? = null,
        @Column(name = "name")
        var name: String? = null,
        @Column(name = "level")
        var level: Int? = null,
        @Column(name = "parent_name")
        var parentName: String? = null,
        @Column(name = "parent_id")
        var parentID: String? = null,
        @Column(name = "timezone")
        var timezone: String? = null,
        @Column(name = "ehome_id")
        var ehomeId: String? = null,
        @Column(name = "last_active")
        var lastActive: Int? = null
)