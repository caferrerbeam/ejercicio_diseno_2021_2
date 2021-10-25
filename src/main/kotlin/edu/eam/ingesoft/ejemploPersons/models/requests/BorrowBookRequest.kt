package edu.eam.ingesoft.ejemploPersons.models.requests

import javax.validation.constraints.Pattern

data class BorrowBookRequest(
    val bookId: String,
    val userId: String,
)
