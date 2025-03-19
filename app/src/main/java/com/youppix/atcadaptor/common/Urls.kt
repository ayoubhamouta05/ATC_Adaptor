package com.youppix.atcadaptor.common

object Urls {

    private const val BASE_URL = "http://192.168.103.86:8080/atc_adaptor/"

    /** auth urls */
    const val SIGNUP_URL = "$BASE_URL/auth/signup.php"
    const val VERIFY_CODE_URL = "$BASE_URL/auth/verifyCode.php"
    const val LOGIN_URL = "$BASE_URL/auth/login.php"
    const val CHECK_EMAIL_URL = "$BASE_URL/auth/forgotPassword/checkEmail.php"
    const val VERIFY_CODE_FORGOT_PASSWORD_URL = "$BASE_URL/auth/forgotPassword/verifycode.php"
    const val RESET_PASSWORD_URL = "$BASE_URL/auth/forgotPassword/resetPassword.php"

    /** home urls */
    const val HOME_SEARCH_URL = "$BASE_URL/home/homeSearch.php"

    /** details urls */
    const val GET_DETAILS_URL = "$BASE_URL/details/getDetails.php"
}