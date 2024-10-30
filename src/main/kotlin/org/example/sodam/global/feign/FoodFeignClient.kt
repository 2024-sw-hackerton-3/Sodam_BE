package org.example.sodam.global.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "food", url = "\${url.food}")
interface FoodFeignClient {

    // 레시피 기본정보(리스트 보기)
    @GetMapping("\${feign.food-key}/json/Grid_20150827000000000226_1/{start}/{end}")
    fun basicInfo(
        @PathVariable start: String,
        @PathVariable end: String,
        @RequestParam(name = "RECIPE_NM_KO") koName: String?
    ): String

    // 레시피 과정정보(상세보기)
    @GetMapping("\${feign.food-key}/json/Grid_20150827000000000228_1/1/5")
    fun recipeInto(
        @RequestParam(name = "RECIPE_ID") id: Int
    ): String
}
