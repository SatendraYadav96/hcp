package com.squer.promobee.security.util

import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AESUtil {
    companion object {
        val key = "Bar12345Bar12345" // 128 bit key


        fun encrypt(text: String?): String? {
            if (text == null) return  null
            val aesKey: Key = SecretKeySpec(key.toByteArray(), "AES")
            val cipher: Cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, aesKey)
            val encrypted: ByteArray = cipher.doFinal(text.toByteArray())
            return Base64.getEncoder().encodeToString(encrypted)
        }

        fun decrypt(text: String?): String? {
            if (text == null) return null
            val decoded = Base64.getDecoder().decode(text)
            val aesKey: Key = SecretKeySpec(key.toByteArray(), "AES")
            val cipher: Cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, aesKey)
            val decrypted: ByteArray = cipher.doFinal(decoded)
            return String(decrypted)
        }
    }

}
