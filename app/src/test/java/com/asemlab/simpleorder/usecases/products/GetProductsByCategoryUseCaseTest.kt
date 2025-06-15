package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.usecases.BaseUseCaseTest
import com.asemlab.simpleorder.usecases.DummyProductsData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetProductsByCategoryUseCaseTest : BaseUseCaseTest() {

    lateinit var getProductsByCategoryUseCase: GetProductsByCategoryUseCase


    @Before
    override fun setUp() {
        super.setUp()
        getProductsByCategoryUseCase = GetProductsByCategoryUseCase(fakeRepo)
    }


    @Test
    fun `get filtered products successfully`() = runBlocking {

        val selectedCategory = DummyProductsData.dummyCategories[0]
        val expectedProducts = DummyProductsData.filterByCategory(selectedCategory)

        coEvery { fakeRepo.getProductsByCategory(selectedCategory) } returns
                DummyProductsData.filterByCategory(selectedCategory)

        val result = getProductsByCategoryUseCase.invoke(selectedCategory)

        Assert.assertEquals(expectedProducts.size, result.size)
        Assert.assertEquals(expectedProducts.first().id, result.first().id)

        coVerify(exactly = 1) { fakeRepo.getProductsByCategory(any()) }
        confirmVerified()
    }

    @Test
    fun `return empty products list when category not found`() = runBlocking {

        val unknownCategory = Category("-1", "Dummy")
        val expectedProducts = DummyProductsData.filterByCategory(unknownCategory)

        coEvery { fakeRepo.getProductsByCategory(unknownCategory) } returns
                DummyProductsData.filterByCategory(unknownCategory)

        val result = getProductsByCategoryUseCase.invoke(unknownCategory)

        Assert.assertTrue(result.isEmpty())
        Assert.assertEquals(expectedProducts.size, result.size)

        coVerify(exactly = 1) { fakeRepo.getProductsByCategory(any()) }
        confirmVerified()
    }


    @Test
    fun `throw exception when problem occurs in repository`() = runBlocking {

        val selectedCategory = DummyProductsData.dummyCategories[0]
        val expectedMessage = "Not authorized"

        coEvery { fakeRepo.getProductsByCategory(selectedCategory) } throws
                Exception(expectedMessage)
        try {
            getProductsByCategoryUseCase.invoke(selectedCategory)
        } catch (e: Exception) {
            Assert.assertEquals(expectedMessage, e.message)

        }

        coVerify(exactly = 1) { fakeRepo.getProductsByCategory(any()) }
        confirmVerified()
    }

}