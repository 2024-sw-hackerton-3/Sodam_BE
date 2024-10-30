package org.example.sodam.global.feign

import com.google.gson.Gson
import org.example.sodam.domain.food.domain.FoodRecipe
import org.example.sodam.domain.user.exception.UserNotFoundException
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.global.feign.dto.request.GPTRequest
import org.example.sodam.global.feign.dto.request.SendMessage
import org.example.sodam.global.feign.dto.response.GPTResponse
import org.example.sodam.global.feign.dto.response.RecipeContent
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GPTFeignClientService(
    @Value("\${feign.gpt-key}")
    private val gptKey: String,
    private val gptFeignClient: GPTFeignClient,
    private val userFacade: UserFacade
) {
    fun getInfo(name: String, cookingTime: String, qnt: String): FoodRecipe {
        val user = userFacade.getCurrentUser() ?: throw UserNotFoundException
        val userMessage = SendMessage(
            role = "user",
            content = """
                이 음식($name)의 레시피를 알려주세요. 
                ${qnt}을 조리할 예정이고, 소요시간은 $cookingTime 정도입니다.
                응답은 다음 포맷으로 작성해 주세요(이스케이프 문자를 포함하지 않고), 재료와 향신료 계열 양념 나눠서 표기해 주세요.
                향신료 계열은 소금, 후추와 같이 요리에 꼭 필요한 조미료를 포함합니다. 이들 재료는 'sauce' 리스트에 넣어주세요.
                사용자는 ${user.allergy} 해당 항목에 알러지를 가지고 있습니다(null일시 상관X).
                이 레시피에 ${user.allergy}를 포함하고 있다면 'danger'의 값을 true로 반환해주세요. 해당 사항 없을 시 false를 반환해주시기 바랍니다.:
                { "danger": 알러지를 가지고있는가 여부(Boolean), "name": "요리의 이름", "substanList": ["전체 요리에 필요한 재료 이름", "전체 요리에 필요한 재료 이름"], "substan": ["필요한 재료: 용량", "필요한 재료: 용량"], "sauce": ["필요한 향신료 계열 양념: 용량", "필요한 향신료 계열 양념: 용량"], "step": ["1. ~를 합니다", "2. ~를 합니다."] }
            """.trimIndent()
        )

        val request = GPTRequest(
            model = "gpt-4o-mini",
            messages = listOf(userMessage)
        )

        val recipeInfo = gptFeignClient.gpt("Bearer $gptKey", request)

        val gptResponse = Gson().fromJson(recipeInfo, GPTResponse::class.java)

        val content = gptResponse.choices[0].message.content.replace("\\", "")

        val recipeContent = Gson().fromJson(content, RecipeContent::class.java)

        return FoodRecipe(
            danger = recipeContent.danger,
            name = recipeContent.name,
            qnt = qnt,
            cookingTime = cookingTime,
            substanList = recipeContent.substanList,
            substan = recipeContent.substan,
            sauce = recipeContent.sauce,
            step = recipeContent.step
        )
    }
}
