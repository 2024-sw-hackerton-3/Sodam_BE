package org.example.sodam.global.feign

import org.example.sodam.global.feign.dto.request.GPTRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "gpt", url = "\${url.gpt}")
interface GPTFeignClient {

    // 레시피 창조
    @PostMapping
    fun gpt(
        @RequestHeader("Authorization") apiKey: String,
        @RequestBody request: GPTRequest
    ): String

    // 사진 분석 -> 레시피 생성
    @PostMapping
    fun imageGpt(@RequestHeader("Authorization") apiKey: String, @RequestBody request: Map<String, Any>): String
}
