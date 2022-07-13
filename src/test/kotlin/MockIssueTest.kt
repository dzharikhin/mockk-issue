import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class MockIssueTest {

    @Test
    fun test() {
        val executorMockk = mockk<Executor>()
        every {
            executorMockk.hint(ResultContainerOne::class).process(
                RequestOne()
            )
        } returns ResultContainerOne("mock1")
        every {
            executorMockk.hint(ResultContainerTwo::class).process(
                RequestTwo()
            )
        } returns ResultContainerTwo("mock2")
        assertEquals("mock2", executorMockk.process(RequestTwo()).value)
    }
}

interface Executor {
    fun <Result> process(request: Request<ResultContainerOne<Result>>): ResultContainerOne<Result>
    fun <Result> process(request: Request<ResultContainerTwo<Result>>): ResultContainerTwo<Result>
}

interface Request<T : ResultContainer> {
    fun value(): T
}

class RequestOne : Request<ResultContainerOne<String>> {
    override fun value(): ResultContainerOne<String> {
        return ResultContainerOne(this::class.simpleName!!)
    }
}

class RequestTwo : Request<ResultContainerTwo<String>> {
    override fun value(): ResultContainerTwo<String> {
        return ResultContainerTwo(this::class.simpleName!!)
    }
}


interface ResultContainer

class ResultContainerOne<T>(val value: T) : ResultContainer

class ResultContainerTwo<T>(val value: T) : ResultContainer
