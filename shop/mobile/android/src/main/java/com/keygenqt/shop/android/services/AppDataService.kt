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
package com.keygenqt.shop.android.services

import com.keygenqt.shop.android.data.AppDatabase
import com.keygenqt.shop.android.services.impl.CartDataService
import com.keygenqt.shop.android.services.impl.CategoryDataService
import com.keygenqt.shop.android.services.impl.CollectionDataService
import com.keygenqt.shop.android.services.impl.OrderHistoryDataService

/**
 * Base service module for work with db room
 *
 * @author Vitaliy Zarubin
 */
class AppDataService(
    override val db: AppDatabase,
) : CartDataService,
    CategoryDataService,
    CollectionDataService,
    OrderHistoryDataService {

    /**
     * Performed when the user logs out
     */
    override suspend fun clearCacheAfterLogout() {
        super<CartDataService>.clearCacheAfterLogout()
        super<CategoryDataService>.clearCacheAfterLogout()
        super<CollectionDataService>.clearCacheAfterLogout()
        super<OrderHistoryDataService>.clearCacheAfterLogout()
    }
}
