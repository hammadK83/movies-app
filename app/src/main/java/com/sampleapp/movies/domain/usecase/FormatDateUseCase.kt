package com.sampleapp.movies.domain.usecase

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FormatDateUseCase @Inject constructor() {
    operator fun invoke(inputDate: String?): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(inputDate, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
        return date.format(outputFormatter).orEmpty()
    }
}