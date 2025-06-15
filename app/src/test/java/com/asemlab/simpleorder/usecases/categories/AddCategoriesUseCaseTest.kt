package com.asemlab.simpleorder.usecases.categories

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

class AddCategoriesUseCaseTest : BaseUseCaseTest() {

    lateinit var addCategoriesUseCase: AddCategoriesUseCase

    @Before
    override fun setUp() {
        super.setUp()
        addCategoriesUseCase = AddCategoriesUseCase(fakeRepo)
    }


    @Test
    fun `add categories successfully`() = runBlocking {


        val resultList = mutableListOf<Category>()

        coEvery { fakeRepo.addCategories(DummyProductsData.dummyCategories) } answers {
            resultList.addAll(DummyProductsData.dummyCategories)
        }

        addCategoriesUseCase.invoke(DummyProductsData.dummyCategories)

        Assert.assertEquals(DummyProductsData.dummyCategories.size, resultList.size)
        Assert.assertEquals(DummyProductsData.dummyCategories.first().name, resultList.first().name)

        coVerify(exactly = 1) { fakeRepo.addCategories(any()) }
        confirmVerified()
    }

    @Test
    fun `add empty list adds nothing and returns old list`() = runBlocking {


        val resultList = DummyProductsData.dummyCategories

        coEvery { fakeRepo.addCategories(any()) } answers {}


        addCategoriesUseCase.invoke(emptyList<Category>())

        Assert.assertEquals(DummyProductsData.dummyCategories.size, resultList.size)
        Assert.assertEquals(DummyProductsData.dummyCategories.first().name, resultList.first().name)

        coVerify(exactly = 1) { fakeRepo.addCategories(any()) }
        confirmVerified()
    }

    @Test
    fun `add empty list adds nothing and returns empty list`() = runBlocking {


        val resultList = mutableListOf<Category>()

        coEvery { fakeRepo.addCategories(any()) } answers {}


        addCategoriesUseCase.invoke(emptyList<Category>())

        Assert.assertTrue(resultList.isEmpty())

        coVerify(exactly = 1) { fakeRepo.addCategories(any()) }
        confirmVerified()
    }


    @Test
    fun `throw exception when problem occurs in repository`() = runBlocking {

        val expectedMessage = "Not authorized"

        coEvery { fakeRepo.addCategories(any()) } throws Exception(expectedMessage)

        try {
            addCategoriesUseCase.invoke(emptyList())
        } catch (e: Exception) {
            Assert.assertEquals(expectedMessage, e.message)
        }


        coVerify(exactly = 1) { fakeRepo.addCategories(any()) }
        confirmVerified()
    }

}
