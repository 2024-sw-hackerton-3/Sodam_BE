package org.example.sodam.domain.user.enum

enum class Allergy(val koName: String) {
    EGGS("난류"), // 난류
    MILK("우유"), // 우유
    BUCKWHEAT("메밀"), // 메밀
    PEANUT("땅콩"), // 땅콩
    SOY("대두"), // 대두
    WHEAT("밀"), // 밀
    MACKEREL("고등어"), // 고등어
    CRAB("게"), // 게
    SHRIMP("새우"), // 새우
    PORK("돼지고기"), // 돼지고기
    PEACH("복숭아"), // 복숭아
    TOMATO("토마토"), // 토마토
    SULFITES("아황산염"), // 아황산염
    WALNUT("호두"), // 호두
    CHICKEN("닭고기"), // 닭고기
    BEEF("소고기"), // 소고기
    SQUID("오징어"), // 오징어
    SHELLFISH("조개류"), // 조개류(굴, 전복, 홍합 포함)
    PINE_NUT("잣"), // 잣
    NONE("없음")
}
