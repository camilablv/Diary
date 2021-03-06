package com.pchpsky.diary.utils.extensions

import com.pchpsky.diary.data.network.model.Insulin
import com.pchpsky.schema.CreateInsulinMutation
import com.pchpsky.schema.InsulinsQuery
import com.pchpsky.schema.SettingsQuery
import com.pchpsky.schema.UpdateInsulinMutation

fun CreateInsulinMutation.Data.insulin(): Insulin {
    return Insulin(
        id = insulin?.id!!,
        color = insulin.color!!,
        name = insulin.name!!
    )
}

fun InsulinsQuery.Data.insulins(): List<Insulin>? {
    return settings?.insulins?.map {
        Insulin(
            id = it?.id!!,
            color = it.color!!,
            name = it.name!!
        )
    }
}

fun SettingsQuery.Data.insulins(): MutableList<Insulin>? {
    return settings?.insulins?.map {
        Insulin(
            id = it?.id!!,
            color = it.color!!,
            name = it.name!!
        )
    }?.toMutableList()
}

fun UpdateInsulinMutation.Data.insulin(): Insulin {
    return Insulin(
        id = insulin?.id!!,
        name = insulin.name!!,
        color = insulin.color!!
    )
}