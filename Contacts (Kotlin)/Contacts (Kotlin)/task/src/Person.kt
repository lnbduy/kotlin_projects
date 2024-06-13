package contacts

class Person(): Contact() {
    private val updatableProperties = listOf("name", "surname", "birthDate", "gender", "number")
    private var name: String = ""
    private var surname: String = ""
    private var birthDate: String? = null
        set(value) {
            field = if(validateBirthDate(value)) value else null
        }
    private var gender: String? = null
        set(value) {
            field = if (validateGender(value)) value else null
        }

    private fun validateBirthDate(value: String?): Boolean {
        return if (value.isNullOrEmpty()) {
            println("Bad birth date!")
            false
        } else true

    }

    private fun validateGender(value: String?): Boolean {
        return if (value != null && !listOf("M", "F").contains(value)) {
            println("Bad gender!")
            false
        } else true
    }

    override fun getBriefInfo() = "$name $surname"

    override fun getFullInfo(): String {
        return """
            Name: $name
            Surname: $surname
            Birth date: ${birthDate ?: "[no data]"}
            Gender: ${gender ?: "[no data]"}
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
                "surname" -> surname = newValue
                "birthDate" -> birthDate = newValue
                "gender" -> gender = newValue
                "number" -> phoneNumber = newValue
            }
        }
    }

    override fun getPropertyValue(property: String): String? {
        return when(property) {
            "name" -> name
            "surname" -> surname
            "birthDate" -> birthDate
            "gender" -> gender
            "number" -> phoneNumber
            else -> null
        }
    }

    override fun toSearchableString(): String {
        return "$name$surname$birthDate$phoneNumber"
    }

}