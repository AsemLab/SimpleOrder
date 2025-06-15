package com.asemlab.simpleorder.usecases

import com.asemlab.simpleorder.repositories.ProductsRepository
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.After
import org.junit.Before

abstract class BaseUseCaseTest {

    lateinit var fakeRepo: ProductsRepository

    @Before
    open fun setUp(){
        fakeRepo = mockk(relaxed = true)
    }

    @After
    fun tearDown(){
        clearAllMocks()
    }
}