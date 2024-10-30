package org.example.sodam.global.feign

import com.google.gson.Gson
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.global.feign.dto.response.GPTResponse
import org.example.sodam.global.feign.dto.response.ImageRecipeContent
import org.example.sodam.global.s3.FileUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class GPTImageFeignClientService(
    @Value("\${feign.gpt-key}")
    private val gptKey: String,
    private val fileUtil: FileUtil,
    private val userFacade: UserFacade,
    private val gptFeignClient: GPTFeignClient
) {
    fun getInfo(file: MultipartFile): ImageRecipeContent {
        val user = userFacade.getCurrentUser()
        val imageUrl = fileUtil.upload(file).substringBefore("?")
        val real = fileUtil.generateObjectUrl(imageUrl)

        val userMessages = listOf(
            mapOf(
                "role" to "user",
                "content" to listOf(
                    mapOf(
                        "type" to "text",
                        "text" to
                            """
                                사용자는 ${user.allergy} 해당 항목에 알러지를 가지고 있습니다(null일시 상관X).
                                이 레시피에 ${user.allergy}를 포함하고 있다면 'danger'의 값을 true로 반환해주세요. 해당 사항 없을 시 false를 반환해주시기 바랍니다.:
                                아래 이미지를 분석하여 한가지 요리의 이름과 레시피를 알려주세요.
                                답변이 중간에 끊기지 않게 해줘.
                                답변은 아래와 같은 형태로만 해주시길 바랍니다.:
                                {
                                    "danger": 알러지를 가지고있는가 여부(Boolean),
                                    "name": "요리의 이름",
                                    "substanList": ["전체 요리에 필요한 재료 이름"],
                                    "substan": ["필요한 재료: 용량"],
                                    "sauce": ["필요한 향신료 계열 양념: 용량"],
                                    "step": ["1. ~를 합니다", "2. ~를 합니다."],
                                    "cookingTime": "~예상 소요 시간",
                                    "qnt": "레시피 용량(eg.2인분)"
                                    }
                            """.trimIndent()
                    ),
                    mapOf("type" to "image_url", "image_url" to mapOf("url" to real))
                )
            )
        )

        val request = mapOf(
            "model" to "gpt-4-turbo",
            "messages" to userMessages,
            "max_tokens" to 1000
        )

        val imageRecipeInfo = gptFeignClient.imageGpt("Bearer $gptKey", request)

        val gptResponse = Gson().fromJson(imageRecipeInfo, GPTResponse::class.java)

        val content = gptResponse.choices[0].message.content.replace("\\", "")

        val recipeContent = Gson().fromJson(content, ImageRecipeContent::class.java)

        return ImageRecipeContent(
            danger = recipeContent.danger,
            name = recipeContent.name,
            substanList = recipeContent.substanList,
            substan = recipeContent.substan,
            sauce = recipeContent.sauce,
            step = recipeContent.step,
            cookingTime = recipeContent.cookingTime,
            qnt = recipeContent.qnt
        )
    }
}
