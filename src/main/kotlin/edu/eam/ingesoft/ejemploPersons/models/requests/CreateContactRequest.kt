package edu.eam.ingesoft.ejemploPersons.models.requests

data class CreateContactRequest(
    val idContact: String,
    val personId: String,
    val contactName: String,
    val contactMail: String,
    val contactAddress: String,
    val contactPhone: String,
)
