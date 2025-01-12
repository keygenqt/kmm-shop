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
package com.keygenqt.shop.pc.client.extensions

import com.keygenqt.shop.base.AESEncryption
import com.keygenqt.shop.data.responses.SessionCookieResponse
import com.keygenqt.shop.pc.client.utils.Constants
import io.ktor.http.*
import io.ktor.util.date.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun SessionCookieResponse.toCookie(): Cookie {
    return Cookie(
        name = this.name,
        value = this.value,
        domain = Constants.GET_DOMAIN,
        maxAge = this.maxAge,
        expires = GMTDate(this.expires),
        path = this.path,
        httpOnly = true,
    )
}

/**
 * Save cookie to file
 */
fun SessionCookieResponse.save() {
    // to json
    val json = Json.encodeToString(this)
    // encrypt
    val encrypt = AESEncryption.encrypt(Constants.SECRET_COOKIE_FILE, json)
    // save
    encrypt?.let {
        File(Constants.PATH_COOKIE_FILE).writeBytes(encrypt)
    }
}

/**
 * Read cookie from file
 */
fun SessionCookieResponse.Companion.read(): SessionCookieResponse? {
    // read cookie
    File(Constants.PATH_COOKIE_FILE).let {
        if (it.isFile) {
            // read file
            val data = it.readBytes()
            // encrypt
            val decrypt = AESEncryption.decrypt(Constants.SECRET_COOKIE_FILE, data)
            // convert
            decrypt?.let {
                return Json.decodeFromString<SessionCookieResponse>(decrypt)
            }
        }
    }
    return null
}
