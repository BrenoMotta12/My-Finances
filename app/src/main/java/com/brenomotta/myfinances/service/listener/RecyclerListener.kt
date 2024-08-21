package com.brenomotta.myfinances.service.listener

import com.brenomotta.myfinances.service.model.AccountModel

interface RecyclerListener {

    /**
     * Edição
     */
    fun onListClick(id: Int)


    /**
     * Remoção
     */
    fun onDeleteClick(id: Int)
}