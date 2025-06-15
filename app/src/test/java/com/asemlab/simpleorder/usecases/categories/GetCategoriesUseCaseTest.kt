package com.asemlab.simpleorder.usecases.categories

import com.asemlab.simpleorder.models.Category
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

class GetCategoriesUseCaseTest : BaseUseCaseTest() {

    lateinit var getCategoriesUseCase: GetCategoriesUseCase


    @Before
    override fun setUp() {
        super.setUp()
        getCategoriesUseCase = GetCategoriesUseCase(fakeRepo)
    }


    @Test
    fun `get all categories successfully`() = runBlocking {

        val dummyCategories = DummyProductsData.dummyCategories

        coEvery { fakeRepo.getCategories() } returns
                ServerResponse.Success(dummyCategories)

        val result = getCategoriesUseCase.invoke()

        Assert.assertTrue(result is ServerResponse.Success)
        Assert.assertEquals(
            dummyCategories.size,
            (result as ServerResponse.Success).responseData.size
        )
        Assert.assertEquals(dummyCategories.first().name, result.responseData.first().name)

        coVerify(exactly = 1) { fakeRepo.getCategories() }
        confirmVerified()
    }

    @Test
    fun `get empty list when data is empty`() = runBlocking {

        val dummyCategories = emptyList<Category>()

        coEvery { fakeRepo.getCategories() } returns
                ServerResponse.Success(dummyCategories)

        val result = getCategoriesUseCase.invoke()

        Assert.assertTrue(result is ServerResponse.Success)
        Assert.assertTrue((result as ServerResponse.Success).responseData.isEmpty())

        coVerify(exactly = 1) { fakeRepo.getCategories() }
        confirmVerified()
    }

    @Test
    fun `return error response when failed to get data from api`() = runBlocking {
        val expectedMessage = "Not found"
        val expectedCode = 404

        coEvery { fakeRepo.getCategories() } returns
                ServerResponse.Error(expectedMessage, expectedCode)

        val result = getCategoriesUseCase.invoke()

        Assert.assertTrue(result is ServerResponse.Error)
        Assert.assertEquals(expectedCode, (result as ServerResponse.Error).code)
        Assert.assertEquals(expectedMessage, result.errorBody)

        coVerify(exactly = 1) { fakeRepo.getCategories() }
        confirmVerified()
    }


    @Test
    fun `throw exception when problem occurs in repository`() = runBlocking {
        val expectedMessage = "Not authorized"

        coEvery { fakeRepo.getCategories() } throws Exception(expectedMessage)
        try {
            getCategoriesUseCase.invoke()
        } catch (e: Exception) {
            Assert.assertEquals(expectedMessage, e.message)

        }

        coVerify(exactly = 1) { fakeRepo.getCategories() }
        confirmVerified()
    }

}