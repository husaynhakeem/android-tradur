package io.husaynhakeem.tradurlibrary


object Tradur {

    var apiKey: String = ""
        get() {
            if (field.isBlank()) {
                throw RuntimeException("Tradur api key must not be blank or empty")
            }
            return field
        }

    fun init(apiKey: String) {
        this.apiKey = apiKey
    }
}