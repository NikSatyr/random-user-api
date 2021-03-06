
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.niksatyr.randomuser.api.RandomUserApi
import com.niksatyr.randomuser.dto.UserDto
import com.niksatyr.randomuser.dto.UsersResponseDto
import com.niksatyr.randomuser.model.User
import com.niksatyr.randomuser.repo.UserRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.*

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var userRepositoryImpl: UserRepositoryImpl

    private lateinit var mockApi: RandomUserApi

    private val mockUser = UserDto(
        UserDto.Name("Mr", "John", "Doe"),
        UserDto.DateOfBirth(Date(), 29),
        UserDto.PhotoUrls("abc.com", "abc.com", "abc.com"),
        "aaa@a.com",
        "+38065653214",
        UserDto.Location(UserDto.Street(1, "Sunset Bvd"), "LA", "CA", "23402")
    )

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        mockApi = mock {
            onBlocking { getUsers(any()) } doReturn Response.success(
                UsersResponseDto(listOf(mockUser))
            )
        }
        userRepositoryImpl = UserRepositoryImpl(mockApi)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun test_getUsers() = testScope.runBlockingTest {
        val users = userRepositoryImpl.getUsers(1)
        val expected: List<User> = listOf(mockUser).map { User.Factory.create(it) }
        assertEquals(expected, users)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_getUsers_negativeCount() = testScope.runBlockingTest {
        val users = userRepositoryImpl.getUsers(-2)
    }

}