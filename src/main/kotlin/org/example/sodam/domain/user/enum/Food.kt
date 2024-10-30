package org.example.sodam.domain.user.enum

enum class Food(val subcategories: List<String>) {
    STEWS(listOf("김치찌개", "된장찌개", "순두부찌개")), // 찌개류
    RICE(listOf("비빔밥", "돌솥비빔밥", "김밥")), // 밥류
    NOODLES(listOf("칼국수", "냉면", "잔치국수")), // 면류
    DESSERTS(listOf("호떡", "팥빙수", "떡")), // 디저트류
    SALADS(listOf("미나리무침", "콩나물무침", "잡채")), // 샐러드류
    GRILLED(listOf("불고기", "고등어구이", "삼겹살")), // 구이류
    FRIED(listOf("튀김", "치킨", "강정")) // 튀김류
}

// 김치찌개, 된장찌개는 STEW니까 STEW의 카운트를 2로 올리고
// 김밥이 있으니까, RICE의 카운트도 1로 올ㅣㄹ는 방식으로
