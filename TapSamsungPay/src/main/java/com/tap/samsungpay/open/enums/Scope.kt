package com.tap.samsungpay.open.enums

enum class Scope {
    TAP_TOKEN,
    SAMSUNG_TOKEN
}




enum class ThemeMode {
    DARK,
    LIGHT,
}
enum class Language {
    EN,
    AR,
}


enum class SupportedSchemes {
    AMERICAN_EXPRESS,

    MASTERCARD,

    VISA,

    ALL
}

enum class SupportedFundSource {
    ALL,
    DEBIT,
    CREDIT
}

enum class SupportedPaymentAuthentications {
    ThreeDS,
    EMV,
}

enum class SDKMODE {
    SANDBOX,
    PRODUCTION,
}


enum class Edges {
    CURVED,
    STRAIGHT
}
