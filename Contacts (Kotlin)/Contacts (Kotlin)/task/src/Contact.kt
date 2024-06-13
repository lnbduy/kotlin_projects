package contacts

import java.time.LocalDateTime

open abstract class Contact() {
    abstract fun getBriefInfo(): String
    abstract fun getFullInfo(): String
    abstract fun toSearchableString(): String
    abstract fun getUpdatableProperties() : List<String>
    abstract fun updateProperty(property: String, newValue: String)
    abstract fun getPropertyValue(property: String): String?

    var timeCreated: LocalDateTime? = null
    var timeLastEdit: LocalDateTime? = null
    var phoneNumber: String? = null
        // Custom Setter
        set(value) {
            field = if (validPhoneNumber(value)) value else null
        }

    private fun validPhoneNumber(value: String?): Boolean {
        val regStr = "^\\+?(\\([A-Za-z0-9]{1,}\\)|[A-Za-z0-9]{1,})(\\s|-)?((\\([A-Za-z0-9]{2,}\\)|[A-Za-z0-9]{2,}))?((\\s|-)?[A-Za-z0-9]{2,})*\$".toRegex()
        return if (value != null && (!value.matches(regStr) || value.count { c -> c=='(' } >= 2)) {
            println("Wrong number format!")
            false
        } else {
            true
        }
    }
}