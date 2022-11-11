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
package com.keygenqt.shop.api

import com.keygenqt.shop.api.exceptions.authentication
import com.keygenqt.shop.api.exceptions.session
import com.keygenqt.shop.api.routing.greeting
import com.keygenqt.shop.api.routing.login
import com.keygenqt.shop.api.routing.main
import com.keygenqt.shop.api.routing.rockets
import com.keygenqt.shop.api.security.SessionService
import com.keygenqt.shop.api.utils.AppConstants
import com.keygenqt.shop.base.LoaderConfig
import com.keygenqt.shop.db.base.DatabaseMysql
import com.keygenqt.shop.db.service.AdminsService
import com.keygenqt.shop.db.service.RocketsService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module as koinModule

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

fun Application.module() {
    with(environment.config) {

        // load config
        val conf = LoaderConfig.loadProperties(this.property("ktor.config.app").getString())

        // init db app
        val db = DatabaseMysql(
            dbconfig = this.property("ktor.config.dbconfig").getString()
        )

        // init koin
        startKoin {
            printLogger()
            modules(koinModule {
                // app config
                single { conf }

                // db services
                single { AdminsService(db) }
                single { RocketsService(db) }

                // session service
                single {
                    SessionService(
                        db = db,
                        secret = conf.getPropOrNull("secret")
                    )
                }
            })
        }

        // init session
        install(Sessions) {
            session(
                secret = conf.getPropOrNull("secret"),
                signKey = conf.getPropOrNull("signKey")
            )
        }

        // init auth
        install(Authentication) {
            authentication()
        }

        // init json
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }

        // init routing
        install(Routing) {

            // root
            main()

            route("/api") {
                // guest
                login()
                greeting()
                // user
                authenticate(AppConstants.SESSION_KEY) {
                    rockets()
                }
            }
        }
    }
}
