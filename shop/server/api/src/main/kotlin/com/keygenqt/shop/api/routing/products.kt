/*
 * Copyright 2022 Vitaliy Zarubin
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
package com.keygenqt.shop.api.routing

import com.keygenqt.shop.api.base.Exceptions
import com.keygenqt.shop.api.extension.*
import com.keygenqt.shop.data.requests.OrderProduct
import com.keygenqt.shop.data.responses.AdminRole
import com.keygenqt.shop.data.responses.ProductPageResponse
import com.keygenqt.shop.data.responses.ProductPricesResponse
import com.keygenqt.shop.db.entities.ProductEntity
import com.keygenqt.shop.db.entities.toModel
import com.keygenqt.shop.db.entities.toModelWithUploads
import com.keygenqt.shop.db.entities.toModels
import com.keygenqt.shop.db.service.ProductsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import kotlin.math.ceil

/**
 * Request update [ProductEntity]
 */
@Serializable
data class ProductRequest(
    @field:NotNull
    val categoryID: Int,

    @field:NotNull
    @field:Size(max = 255, message = "Max size must 255")
    val image1: String,

    @field:NotNull
    @field:Size(max = 255, message = "Max size must 255")
    val image2: String,

    @field:NotNull
    @field:Size(max = 255, message = "Max size must 255")
    val image3: String,

    @field:NotNull
    @field:Size(min = 3, max = 255, message = "Size must be between 3 and 255")
    val name: String,

    @field:NotNull
    @field:Size(min = 3, max = 1000, message = "Size must be between 3 and 1000")
    val description: String,

    @field:NotNull
    @field:Positive
    val price: Double,

    @field:NotNull
    val isPublished: Boolean,

    /**
     * List ids collections uploads
     */
    val collections: List<Int> = listOf(),

    /**
     * List urls uploads
     */
    val uploads: List<String> = listOf()
)

/**
 * Request update [ProductEntity.isPublished]
 */
@Serializable
data class ProductStateRequest(
    @field:NotNull
    val isPublished: Boolean
)

fun Route.products() {

    val productsService: ProductsService by inject()
    val pageSize = 9

    route("/products") {
        get("/published") {
            // check role
            call.checkRoleFull()
            // get request
            val order = OrderProduct.valueOf(call.getQueryParam("order"))
            val range = call.getDoublesQueryParam("range")
            val categories = call.getNumbersQueryParam("categories")
            val collections = call.getNumbersQueryParam("collections")
            val page: Int = call.getQueryParam("page").toInt().takeIf { it > 0 }
                ?: throw Exceptions.BadRequest()
            // act
            val entities = productsService.transaction {
                getPagePublished(
                    page = page,
                    order = order,
                    range = Pair(range[0], range[1]),
                    categories = categories,
                    collections = collections,
                    pageSize = pageSize,
                ).toModels()
            }
            val pages = productsService.transaction {
                getCountPublished(
                    order = order,
                    range = Pair(range[0], range[1]),
                    categories = categories,
                    collections = collections,
                )
            }
            // response
            call.respond(
                ProductPageResponse(
                    pages = ceil(pages.toDouble() / pageSize).toInt(),
                    products = entities.toTypedArray()
                )
            )
        }
        get("/prices") {
            // check role
            call.checkRoleFull()
            // act
            val minPriceProduct = productsService.transaction {
                getMinPrice()
            }
            val maxPriceProduct = productsService.transaction {
                getMaxPrice()
            }
            // response
            call.respond(
                ProductPricesResponse(
                    min = minPriceProduct,
                    max = maxPriceProduct
                )
            )
        }
        get {
            // check role
            call.checkRoleAuth()
            // act
            val entities = productsService.transaction {
                getAll().toModels()
            }
            // response
            call.respond(entities)
        }
        get("/published/by-ids") {
            // check role
            call.checkRoleFull()
            // get request
            val ids = call.getNumbersQueryParam()
            // act
            val entities = productsService.transaction {
                getByIdPublished(ids).toModels()
            }
            // response
            call.respond(entities)
        }
        get("/{id}") {
            // check role
            val role = call.checkRoleFull()
            // get request
            val id = call.getNumberParam()
            // act
            val entity = productsService.transaction {
                when (role) {
                    AdminRole.GUEST -> findByIdPublished(id)?.toModel()
                    else -> findById(id)?.toModelWithUploads()
                } ?: throw Exceptions.NotFound()
            }
            // response
            call.respond(entity)
        }
        post {
            // check role
            call.checkRoleAuth()
            // get request
            val request = call.receiveValidate<ProductRequest>()
            // act
            val response = productsService.transaction {
                insert(
                    categoryID = request.categoryID,
                    image1 = request.image1,
                    image2 = request.image2,
                    image3 = request.image3,
                    name = request.name,
                    description = request.description,
                    price = request.price,
                    isPublished = request.isPublished,
                    collections = request.collections,
                    uploads = request.uploads,
                ).toModel()
            }
            // response
            call.respond(response)
        }
        put("/{id}") {
            // check role
            call.checkRoleAuth()
            // get request
            val id = call.getNumberParam()
            val request = call.receiveValidate<ProductRequest>()
            // act
            val response = productsService.transaction {
                findById(id)?.update(
                    categoryID = request.categoryID,
                    image1 = request.image1,
                    image2 = request.image2,
                    image3 = request.image3,
                    name = request.name,
                    description = request.description,
                    price = request.price,
                    isPublished = request.isPublished,
                    collections = request.collections,
                    uploads = request.uploads,
                )?.toModel() ?: throw Exceptions.NotFound()
            }
            // response
            call.respond(response)
        }
        put("/state/{id}") {
            // check role
            call.checkRoleAuth()
            // get request
            val id = call.getNumberParam()
            val request = call.receiveValidate<ProductStateRequest>()
            // act
            val response = productsService.transaction {
                findById(id)?.updateState(
                    isPublished = request.isPublished,
                )?.toModel() ?: throw Exceptions.NotFound()
            }
            // response
            call.respond(response)
        }
    }
}
