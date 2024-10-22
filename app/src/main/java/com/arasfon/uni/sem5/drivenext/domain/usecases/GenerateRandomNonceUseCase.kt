package com.arasfon.uni.sem5.drivenext.domain.usecases

import com.arasfon.uni.sem5.drivenext.domain.models.RandomNonce
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class GenerateRandomNonceUseCase @Inject constructor() {
    operator fun invoke() : RandomNonce {
        val rawNonce = generateRandomNonce()
        val hashedNonce = hashNonce(rawNonce)

        return RandomNonce(rawNonce, hashedNonce)
    }

    private fun generateRandomNonce(): String {
        return UUID.randomUUID().toString()
    }

    private fun hashNonce(nonce: String): String {
        val bytes = nonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
