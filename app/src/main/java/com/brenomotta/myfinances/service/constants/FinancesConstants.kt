package com.brenomotta.myfinances.service.constants

class FinancesConstants {
    object TRANSACTIONS {
        const val INCOME = "receita"
        const val EXPENSES = "despesa"
    }
    object RECURRENCE {
        const val NONE = 0
        const val DAILY = 1
        const val WEEKLY = 2
        const val MONTHLY = 3
        const val ANNUAL = 4
    }
    object BUNDLE {
        const val DESCRIPTION = "description"
        const val ID = "id"
    }

    object DATE {
        const val DATE_FORMAT = "dd/MM/yyyy"
    }

}