package com.dstarcev.smlr.service

interface KeyMapperService {
    interface Add {
        data class Success(val key: String, val link: String) : Add

        data class AlreadyExist(val key: Any) : Add
    }

    interface Get {
        data class Link(val link: String) : Get

        data class NotFound(val key: String) : Get
    }

    fun getLink(key: String): Get

    fun add(link: String): String
}