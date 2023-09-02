package com.kozan.utils

import java.security.GeneralSecurityException
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptHelper {

    private const val ALGORITM = "Blowfish"
    const val KEY = "2356a3a42ba5781f80a72dad3f90aeee8ba93c7637aaf218a8b8c18c"

    private val SERVER_URL = byteArrayOf(
        60,
        -77,
        63,
        77,
        100,
        75,
        59,
        -54,
        6,
        12,
        -79,
        62,
        16,
        -97,
        72,
        100,
        -21,
        35,
        -92,
        24,
        -9,
        -99,
        70,
        125,
        45,
        -99,
        29,
        101,
        34,
        -38,
        19,
        16
    )

    @Throws(GeneralSecurityException::class)
    fun encrypt(key: String, plainText: String): ByteArray? {
        return getCipher(key, Cipher.ENCRYPT_MODE).run { doFinal(plainText.toByteArray()) }
    }

    @Throws(GeneralSecurityException::class)
    fun decrypt(key: String, encryptedText: ByteArray?): String {
        return getCipher(key, Cipher.DECRYPT_MODE).run {
            doFinal(encryptedText).run {
                String(this)
            }
        }
    }

    private fun getCipher(key: String, cipherMode: Int): Cipher {
        return Cipher.getInstance(ALGORITM).apply {
            init(cipherMode, SecretKeySpec(key.toByteArray(), ALGORITM))
        }
    }

    fun getServerUrl(): String? {
        return try {
            decrypt(KEY, SERVER_URL)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
            ""
        }
    }
}