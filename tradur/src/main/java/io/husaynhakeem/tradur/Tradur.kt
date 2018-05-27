package io.husaynhakeem.tradur


object Tradur {

    var apiKey: String = ""
        get() {
            if (field.isBlank()) {
                throw RuntimeException("Tradur api key must not be blank or empty")
            }
            return field
        }

    fun init(apiKey: String) {
        Tradur.apiKey = apiKey
    }
}