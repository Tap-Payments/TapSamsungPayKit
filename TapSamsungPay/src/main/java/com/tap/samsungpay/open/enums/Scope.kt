package com.tap.samsungpay.open.enums

enum class Scope {
    TAPTOKEN,
    SAMSUNG_TOKEN
}


enum class CardType {
    CREDIT,
    DEBIT,
    ALL
}

enum class ThemeMode {
    DARK,
    LIGHT,
}

enum class SupportedBrands {
    AMERICAN_EXPRESS,
    MADA,
    MASTERCARD,
    OMANNET,
    VISA,
    MEEZA,
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

enum class Directions {
    LTR,
    DYNAMIC
}

enum class Edges {
    CURVED,
    STRAIGHT
}
