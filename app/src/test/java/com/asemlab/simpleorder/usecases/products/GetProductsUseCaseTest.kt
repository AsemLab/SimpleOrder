package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse
import com.asemlab.simpleorder.usecases.BaseUseCaseTest
import com.asemlab.simpleorder.usecases.DummyProductsData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetProductsUseCaseTest : BaseUseCaseTest() {

    lateinit var getProductsUseCase: GetProductsUseCase


    @Before
    override fun setUp() {
        super.setUp()
        getProductsUseCase = GetProductsUseCase(fakeRepo)
    }


    @Test
    fun `get all products successfully`() = runBlocking {

        val dummyProducts = DummyProductsData.dummyProducts

        coEvery { fakeRepo.getProducts() } returns
                ServerResponse.Success(dummyProducts)

        val result = getProductsUseCase.invoke()

        Assert.assertTrue(result is ServerResponse.Success)
        Assert.assertEquals(
            dummyProducts.size,
            (result as ServerResponse.Success).responseData.size
        )
        Assert.assertEquals(dummyProducts.first().name, result.responseData.first().name)

        coVerify(exactly = 1) { fakeRepo.getProducts() }
        confirmVerified()
    }

    @Test
    fun `get empty list when data is empty`() = runBlocking {

        val dummyProducts = emptyList<Product>()

        coEvery { fakeRepo.getProducts() } returns
                ServerResponse.Success(dummyProducts)

        val result = getProductsUseCase.invoke()

        Assert.assertTrue(result is ServerResponse.Success)
        Assert.assertTrue((result as ServerResponse.Success).responseData.isEmpty())

        coVerify(exactly = 1) { fakeRepo.getProducts() }
        confirmVerified()
    }

    @Test
    fun `return error response when failed to get data from api`() = runBlocking {
        val expectedMessage = "Not found"
        val expectedCode = 404

        coEvery { fakeRepo.getProducts() } returns
                ServerResponse.Error(expectedMessage, expectedCode)

        val result = getProductsUseCase.invoke()

        Assert.assertTrue(result is ServerResponse.Error)
        Assert.assertEquals(expectedCode, (result as ServerResponse.Error).code)
        Assert.assertEquals(expectedMessage, result.errorBody)

        coVerify(exactly = 1) { fakeRepo.getProducts() }
        confirmVerified()
    }


    @Test
    fun `throw exception when problem occurs in repository`() = runBlocking {
        val expectedMessage = "Not authorized"

        coEvery { fakeRepo.getProducts() } throws Exception(expectedMessage)
        try {
            getProductsUseCase.invoke()
        } catch (e: Exception) {
            Assert.assertEquals(expectedMessage, e.message)

        }

        coVerify(exactly = 1) { fakeRepo.getProducts() }
        confirmVerified()
    }

}