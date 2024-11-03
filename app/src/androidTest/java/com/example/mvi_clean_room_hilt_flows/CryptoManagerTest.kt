package com.example.mvi_clean_room_hilt_flows

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mvi_clean_room_hilt_flows.data.CryptoManager
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@RunWith(AndroidJUnit4::class)
class CryptoManagerTest {

    private lateinit var cryptoManager: CryptoManager

    @Before
    fun setUp() {
        cryptoManager = CryptoManager()
    }

    @Test
    fun encryptAndDecrypt() {
        // Given
        val originalData = "Test message".toByteArray(Charsets.UTF_8)
        val outputStream = ByteArrayOutputStream()

        // When
        val encryptedData = cryptoManager.encrypt(originalData, outputStream)
        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val decryptedData = cryptoManager.decrypt(inputStream)

        // Then
        assertNotNull(encryptedData) // Verify encryption produces a result
        assertArrayEquals(originalData, decryptedData) // Ensure decrypted data matches the original
    }
}
