package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.usecases.BaseUseCaseTest
import com.asemlab.simpleorder.usecases.DummyProductsData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AddProductsUseCaseTest : BaseUseCaseTest() {

    lateinit var addProductsUseCase: AddProductsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        addProductsUseCase = AddProductsUseCase(fakeRepo)
    }


    @Test
    fun `add products successfully`() = runBlocking {


        val resultList = mutableListOf<Product>()

        coEvery { fakeRepo.addProducts(DummyProductsData.dummyProducts) } answers {
            resultList.addAll(DummyProductsData.dummyProducts)
        }

        addProductsUseCase.invoke(DummyProductsData.dummyProducts)

        Assert.assertEquals(DummyProductsData.dummyProducts.size, resultList.size)
        Assert.assertEquals(DummyProductsData.dummyProducts.first().name, resultList.first().name)

        coVerify(exactly = 1) { fakeRepo.addProducts(any()) }
        confirmVerified()
    }

    @Test
    fun `add empty list adds nothing and returns old list`() = runBlocking {


        val resultList = DummyProductsData.dummyProducts

        coEvery { fakeRepo.addProducts(any()) } answers {}


        addProductsUseCase.invoke(emptyList<Product>())

        Assert.assertEquals(DummyProductsData.dummyProducts.size, resultList.size)
        Assert.assertEquals(DummyProductsData.dummyProducts.first().name, resultList.first().name)

        coVerify(exactly = 1) { fakeRepo.addProducts(any()) }
        confirmVerified()
    }

    @Test
    fun `add empty list adds nothing and returns empty list`() = runBlocking {


        val resultList = mutableListOf<Product>()

        coEvery { fakeRepo.addProducts(any()) } answers {}


        addProductsUseCase.invoke(emptyList<Product>())

        Assert.assertTrue(resultList.isEmpty())

        coVerify(exactly = 1) { fakeRepo.addProducts(any()) }
        confirmVerified()
    }

    @Test
    fun `throw exception when problem occurs in repository`() = runBlocking {

        val expectedMessage = "Not authorized"

        coEvery { fakeRepo.addProducts(any()) } throws Exception(expectedMessage)

        try {
            addProductsUseCase.invoke(emptyList())
        } catch (e: Exception) {
            Assert.assertEquals(expectedMessage, e.message)
        }


        coVerify(exactly = 1) { fakeRepo.addProducts(any()) }
        confirmVerified()
    }

}
