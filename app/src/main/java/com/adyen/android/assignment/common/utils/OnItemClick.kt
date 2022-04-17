package com.adyen.android.assignment.common.utils

interface OnItemClick<T> {
    fun onItemClick(item: T, position: Int)
}