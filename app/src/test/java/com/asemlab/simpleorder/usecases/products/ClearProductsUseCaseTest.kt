package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.usecases.BaseUseCaseTest
import com.asemlab.simpleorder.usecases.DummyProductsData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ClearProductsUseCaseTest : BaseUseCaseTest() {

    lateinit var clearProductsUseCase: ClearProductsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        clearProductsUseCase = ClearProductsUseCase(fakeRepo)
    }


    @Test
    fun `clear all products successfully`() = runBlocking {


        var resultList = DummyProductsData.dummyProducts

        coEvery { fakeRepo.clearProducts() } answers { resultList = emptyList() }

        clearProductsUseCase.invoke()

        Assert.assertNotEquals(DummyProductsData.dummyProducts.size, resultList.size)
        Assert.assertTrue(resultList.isEmpty())

        coVerify(exactly = 1) { fakeRepo.clearProducts() }
        confirmVerified()
    }


    @Test
    fun `throw exception when problem occurs in repository`() = runBlocking {

        val expectedMessage = "Not authorized"

        coEvery { fakeRepo.clearProducts() } throws Exception(expectedMessage)

        try {
            clearProductsUseCase.invoke()
        } catch (e: Exception) {
            Assert.assertEquals(expectedMessage, e.message)
        }


        coVerify(exactly = 1) { fakeRepo.clearProducts() }
        confirmVerified()
    }

}
