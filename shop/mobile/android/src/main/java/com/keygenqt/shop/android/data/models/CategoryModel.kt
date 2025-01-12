/*
 * Copyright 2023 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keygenqt.shop.android.data.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keygenqt.shop.android.BuildConfig
import com.keygenqt.shop.data.responses.CategoryResponse

/**
 * Category product model
 */
@Entity
@Immutable
data class CategoryModel(
    @PrimaryKey val id: Int,
    val key: String,
    val name: String,
    val desc: String,
    val image: String,
    val isPublished: Boolean,
    val createAt: String,
    val updateAt: String,
)

fun CategoryResponse.mapToModel() =
    CategoryModel(
        id = id,
        key = key,
        name = name,
        desc = desc,
        image = image.let {
            if (BuildConfig.DEBUG) it.replace("localhost", "10.0.2.2") else it
        },
        isPublished = isPublished,
        createAt = createAt,
        updateAt = updateAt,
    )

fun List<CategoryResponse>.mapToModels() =
    map { it.mapToModel() }
