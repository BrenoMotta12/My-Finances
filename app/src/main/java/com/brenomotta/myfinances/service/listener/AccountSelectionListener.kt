package com.brenomotta.myfinances.service.listener

import android.accounts.Account
import com.brenomotta.myfinances.service.model.AccountModel

interface AccountSelectionListener {

    fun onAccountSelected(accountModel: AccountModel)
}