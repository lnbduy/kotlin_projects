package contacts

import java.time.LocalDateTime

class ContactInterface {
    private val contacts = mutableListOf<Contact>()

    fun process() {
        do {
            println("[menu] Enter action (add, list, search, count, exit):")
            val input = readln()
            when (input) {
                "count" -> count()
                "list" -> list()
                "search" -> search()
                "add" -> add()
                "edit" -> edit()
            }
            println()
        } while (input != "exit")
    }

    private fun count() {
        println("The Phone Book has ${contacts.size} records.")
    }

    private fun list() {
        if (contacts.isNotEmpty()) {
            showContactsBriefInfo()
            println("[list] Enter action ([number], back):")
            when (val input = readln()) {
                "back" -> return
                else -> {
                    val index = input.toIntOrNull()
                    if (index != null && index <= contacts.size && index >= 1) {
                        println(contacts[index-1].getFullInfo())
                        menuRecord(contacts[index-1])
                    } else {
                        println("Invalid index")
                    }
                }
            }
        }
    }


    private fun search() {
        do {
            println("Enter search query:")
            val query = readln()
            val results = contacts.filter { it.toSearchableString().lowercase().contains(query.lowercase()) }
            println("Found ${results.size} results:")
            results.forEachIndexed { index, contact -> println("${index + 1}. ${contact.getBriefInfo()}") }
            println("\n[search] Enter action ([number], back, again):")
            val input = readln()
            when (input) {
                "back" -> return
                else -> {
                    val num = input.toIntOrNull()
                    if (num != null && num <= results.size && num >= 1) {
                        menuRecord(results[num - 1])
                    }
                }
            }
        } while (input == "again")
    }

    private fun menuRecord(contact: Contact) {
        println(contact.getFullInfo())
        do {
            println("[record] Enter action (edit, delete, menu):")
            val input = readln()
            when(input) {
                "edit" -> editContact(contact)
                "delete" -> contacts.remove(contact)
                "menu" -> return
                else -> "Invalid action"
            }
        } while (input != "menu")
    }

    private fun add() {
        println("Enter the type (person, organization):")
        val type = readln()
        when (type) {
            "person" -> addContact(Person())
            "organization" -> addContact(Organization())
            else -> println("Invalid type")
        }
    }

    private fun addContact(contact: Contact) {
        for (property in contact.getUpdatableProperties()) {
            println(getEnterPropertyPrompt(property))
            contact.updateProperty(property, readln())
        }
        contact.timeCreated = LocalDateTime.now()
        contacts.add(contact).also { println("The record added.") }
    }

    private fun edit() {
        if (contacts.isEmpty()) {
            println("No records to edit!")
            return
        }
        showContactsBriefInfo()
        println("Select a record:")
        val num = readln().toIntOrNull()
        if (num != null && num <= contacts.size) {
            val contact = contacts[num - 1]
            editContact(contact)
        } else {
            println("The record does not exist!")
        }
    }

    private fun editContact(contact: Contact) {
        println("Select a field (${contact.getUpdatableProperties().joinToString(", ")})")
        val property = readln()
        println(getEnterPropertyPrompt(property))
        contact.updateProperty(property, readln())
        contact.timeLastEdit = LocalDateTime.now()
        println("Saved")
        println(contact.getFullInfo())
    }

    private fun showContactsBriefInfo() {
        contacts.forEachIndexed { index, contact -> println("${index + 1}. ${contact.getBriefInfo()}") }
    }

    private fun getEnterPropertyPrompt(property: String): String {
        return "Enter the " + when (property) {
            "name", "surname", "address", "number" -> property
            "birthDate" -> "birth date"
            "gender" -> "gender (M, F)"
            else -> ""
        } + " :"
    }
}