package com.squer.promobee.security.config

object SecurityConstants {

    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"

    val WEB_CONTENTS = arrayOf(
        "/favicon.ico",
        "/**/*.png",
        "/**/*.gif",
        "/**/*.svg",
        "/**/*.jpg",
        "/**/*.html",
        "/**/*.css",
        "/**/*.js"
    )

    val H2_URL = arrayOf(
        "/h2-console/**"
    )

    val SWAGGER_ENDPOINTS = arrayOf(
        "/api-docs/**",
        "/api-docs**",
        "/v1/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html/**"
    )

    val APP_ACTUATOR_HEALTH_ENDPOINTS = arrayOf(
        "/app-actuator/health"
    )

    val APP_ACTUATOR_ENDPOINTS = arrayOf(
        "/app-actuator/**"
    )

    val V1_LOGS_ENDPOINTS = arrayOf(
        "/v1/logs/**"
    )

    val V1_SIGN_UP_ENDPOINTS = arrayOf(
        "/v1/user/login",
        "/v1/user/setpassword",
        "/v1/user/register"
    )

    val V1_ENDPOINTS = arrayOf("/v1/**")

}
