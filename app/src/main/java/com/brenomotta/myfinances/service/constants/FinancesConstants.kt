package com.brenomotta.myfinances.service.constants

class FinancesConstants {
    object TRANSACTIONS {
        const val INCOME = "receita"
        const val EXPENSES = "despesa"
    }
    object RECURRENCE {
        const val MONTHLY = "0"
        const val WEEKLY = "1"
        const val DAILY = "2"
        const val ANNUAL = "3"
    }
    object BUNDLE {
        const val DESCRIPTION = "description"
        const val ID = "id"
    }

    object DATE {
        const val DATE_FORMAT = "dd/MM/yyyy"
    }

}