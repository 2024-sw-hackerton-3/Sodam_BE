package org.example.sodam.domain.food.presentation

import org.example.sodam.global.feign.FoodBasicFeignClientService
import org.example.sodam.global.feign.GPTFeignClientService
import org.example.sodam.global.feign.GPTImageFeignClientService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/food")
class FoodController(
    private val foodBasicFeignClientService: FoodBasicFeignClientService,
    private val gptFeignClientService: GPTFeignClientService,
    private val gptImageFeignClientService: GPTImageFeignClientService
) {
    @GetMapping("/query/list")
    fun basicInfo(@RequestParam(value = "name") name: String?) = foodBasicFeignClientService.getFoodInfo(name)

    @GetMapping("/query/recipe")
    fun gptRecipe(
        @RequestParam(name = "name")
        name: String,
        @RequestParam(name = "cooking_time")
        cookingTime: String,
        @RequestParam(name = "qnt")
        qnt: String
    ) = gptFeignClientService.getInfo(name, cookingTime, qnt)

    @PostMapping("/query/image")
    fun imageGptRecipe(
        @RequestParam("file")file: MultipartFile
    ) = gptImageFeignClientService.getInfo(file)

//    @PostMapping
//    fun save() = saveFoodBasicInfoService.getFoodInfo(null)
}
