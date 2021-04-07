package com.duzi.tddtoysample.temp

import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MockitoTest {

    @Mock
    private lateinit var example: Example

    private lateinit var exampleImpl: ExampleImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        exampleImpl = ExampleImpl(example)
    }

    @Test
    fun stub_메서드호출시_예상값_일치하는지_테스트1() {
        whenever(example.getId())
                .thenReturn(100)
                .thenReturn(200)

        whenever(example.getUrl(30))
                .thenReturn("www.naver.com")
                .thenReturn("www.kakao.com")


        Assert.assertEquals(100, example.getId())
        Assert.assertEquals(200, example.getId())

        Assert.assertEquals("www.naver.com", example.getUrl(30))
        Assert.assertEquals("www.kakao.com", example.getUrl(30))
    }

    @Test
    fun stub_메서드호출시_예상값_일치하는지_테스트2() {
        whenever(example.getId())
                .thenReturn(110)
                .thenReturn(210)

        whenever(example.getUrl(40))
                .thenReturn("www.naver1.com")
                .thenReturn("www.kakao1.com")


        Assert.assertEquals(110, example.getId())
        Assert.assertEquals(210, example.getId())

        Assert.assertEquals("www.naver1.com", example.getUrl(40))
        Assert.assertEquals("www.kakao1.com", example.getUrl(40))
    }

    @Test
    fun stub_메서드호출시_예상값_일치하는지_테스트3() {
        whenever(example.getId())
                .thenReturn(300)
                .thenReturn(400)

        whenever(example.getUrl(60))
                .thenReturn("www.naver2.com")
                .thenReturn("www.kakao2.com")


        Assert.assertEquals(300, example.getId())
        Assert.assertEquals(400, example.getId())

        Assert.assertEquals("www.naver2.com", example.getUrl(60))
        Assert.assertEquals("www.kakao2.com", example.getUrl(60))
    }

    @Test
    fun stub_구현체_메서드호출시_예상값_일치하는지_테스트1() {
        whenever(example.getId())
                .thenReturn(100)

        whenever(example.getUrl(100))
                .thenReturn("www.naver.com")


        Assert.assertEquals("www.naver.com", exampleImpl.run(100))
    }

    @Test
    fun stub_구현체_메서드호출시_예상값_일치하는지_테스트2() {
        whenever(example.getId())
                .thenReturn(200)

        whenever(example.getUrl(200))
                .thenReturn("www.kakao.com")


        Assert.assertEquals("www.kakao.com", exampleImpl.run(200))
    }

    class ExampleImpl(private val example: Example) {
        fun run(id: Int): String {
            if (id == example.getId()) {
                return example.getUrl(id)
            }

            return ""
        }
    }

    interface Example {
        fun getId() : Int
        fun getUrl(id: Int) : String
    }
}