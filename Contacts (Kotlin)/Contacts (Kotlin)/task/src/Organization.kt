package contacts

class Organization(): Contact() {
    private var updatableProperties = listOf("name", "address", "number")
    private var name: String = ""
    private var address: String = ""

    override fun getBriefInfo() = "$name"

    override fun getFullInfo(): String {
        return """
            Organization name: $name
            Address: ${address}
            Number: ${phoneNumber ?: "[no data]"}
            Time created: ${timeCreated ?: "[no data]"}
            Time last edit: ${timeLastEdit ?: "[no data]"}

        """.trimIndent()
    }

    override fun getUpdatableProperties(): List<String> {
        return updatableProperties
    }

    override fun updateProperty(property: String, newValue: String) {
        if (updatableProperties.contains(property)) {
            when(property) {
                "name" -> name = newValue
                "address" -> address = newValue
                "number" -> phoneNumber = newValue
            }
        }
    }

    override fun getPropertyValue(property: String): String? {
        return when(property) {
            "name" -> name
            "address" -> address
            "number" -> phoneNumber
            else -> null
        }
    }

    override fun toSearchableString(): String {
        return "$name$address$phoneNumber"
    }
}