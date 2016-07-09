package com.dstarcev.smlr.service

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class DefaultKeyMapperService : KeyMapperService {
    private val map: MutableMap<String, String> = ConcurrentHashMap()

    override fun getLink(key: String) =
            if (map.containsKey(key)) {
                KeyMapperService.Get.Link(map[key]!!)
            } else {
                KeyMapperService.Get.NotFound(key)
            }

    override fun add(key: String, link: String): KeyMapperService.Add {
        if (map.containsKey(key)) {
            return KeyMapperService.Add.AlreadyExist(key)
        }

        map.put(key, link)
        return KeyMapperService.Add.Success(key, link)
    }
}