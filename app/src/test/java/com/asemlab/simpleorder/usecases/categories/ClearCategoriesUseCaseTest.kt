package com.asemlab.simpleorder.usecases.categories

import com.asemlab.simpleorder.usecases.BaseUseCaseTest
import com.asemlab.simpleorder.usecases.DummyProductsData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ClearCategoriesUseCaseTest : BaseUseCaseTest() {

    lateinit var clearCategoriesUseCase: ClearCategoriesUseCase

    @Before
    override fun setUp() {
        super.setUp()
        clearCategoriesUseCase = ClearCategoriesUseCase(fakeRepo)
    }


    @Test
    fun `clear all categories successfully`() = runBlocking {


        var resultList = DummyProductsData.dummyCategories

        coEvery { fakeRepo.clearCategories() } answers { resultList = emptyList() }

        clearCategoriesUseCase.invoke()

        Assert.assertNotEquals(DummyProductsData.dummyCategories.size, resultList.size)
        Assert.assertTrue(resultList.isEmpty())

        coVerify(exactly = 1) { fakeRepo.clearCategories() }
        confirmVerified()
    }


    @Test
    fun `throw exception when problem occurs in repository`() = runBlocking {

        val expectedMessage = "Not authorized"

        coEvery { fakeRepo.clearCategories() } throws Exception(expectedMessage)

        try {
            clearCategoriesUseCase.invoke()
        } catch (e: Exception) {
            Assert.assertEquals(expectedMessage, e.message)
        }


        coVerify(exactly = 1) { fakeRepo.clearCategories() }
        confirmVerified()
    }

}
