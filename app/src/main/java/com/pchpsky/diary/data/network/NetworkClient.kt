package com.pchpsky.diary.data.network

import arrow.core.Either
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.pchpsky.diary.data.network.exceptions.NetworkError
import com.pchpsky.diary.data.network.exceptions.handlers.NetworkErrorHandler
import com.pchpsky.schema.*
import com.pchpsky.schema.type.BloodGlucoseUnits
import com.pchpsky.schema.type.SettingsInput
import javax.inject.Inject

class NetworkClient @Inject constructor(
    private val apolloClient: ApolloClient,
    private val errorsHandler: NetworkErrorHandler
) {

    suspend fun user(): Either<NetworkError, CurrentUserQuery.Data?> {
        return errorsHandler.withErrorHandler {
            apolloClient.query(CurrentUserQuery()).await()
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Either<NetworkError, CreateSessionMutation.Data> {
        return errorsHandler.withErrorHandler {
            apolloClient.mutate(CreateSessionMutation(email, password)).await()
        }
    }

    suspend fun createUser(
        email: String,
        password: String
    ): Either<NetworkError, CreateUserMutation.Data> {
        return errorsHandler.withErrorHandler {
            apolloClient.mutate(CreateUserMutation(email, password)).await()
        }
    }

    suspend fun createInsulin(
        color: String,
        name: String
    ): Either<NetworkError, CreateInsulinMutation.Data> {
        return errorsHandler.withErrorHandler {
            apolloClient.mutate(CreateInsulinMutation(color, name)).await()
        }
    }

    suspend fun insulins(): Either<NetworkError, InsulinsQuery.Data> {
        return errorsHandler.withErrorHandler {
            apolloClient.query(InsulinsQuery()).await()
        }
    }

    suspend fun updateInsulin(
        id: String,
        color: String,
        name: String
    ): Either<NetworkError, UpdateInsulinMutation.Data> {
        return errorsHandler.withErrorHandler {
            apolloClient.mutate(UpdateInsulinMutation(id, color, name)).await()
        }
    }

    suspend fun deleteInsulin(id: String): Either<NetworkError, DeleteInsulinMutation.Data> {
        return errorsHandler.withErrorHandler {
            apolloClient.mutate(DeleteInsulinMutation(id)).await()
        }
    }

    suspend fun updateGlucoseUnit(glucoseUnit: String): Either<NetworkError, UpdateSettingsMutation.Data> {
        val bloodGlucoseUnits = BloodGlucoseUnits.safeValueOf(glucoseUnit)

        return errorsHandler.withErrorHandler {
            apolloClient.mutate(
                UpdateSettingsMutation(
                    SettingsInput(
                        Input.fromNullable(
                            bloodGlucoseUnits
                        )
                    )
                )
            ).await()
        }
    }

    suspend fun settings(): Either<NetworkError, SettingsQuery.Data> {
        return errorsHandler.withErrorHandler {
            apolloClient.query(SettingsQuery()).await()
        }
    }
}