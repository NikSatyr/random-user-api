
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.niksatyr.randomuser.api.RandomUserApi
import com.niksatyr.randomuser.api.UsersResponse
import com.niksatyr.randomuser.dto.UserDto
import com.niksatyr.randomuser.repo.RemoteUserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@ExperimentalCoroutinesApi
class RemoteUserRepositoryTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var remoteUserRepository: RemoteUserRepository

    private lateinit var mockApi: RandomUserApi

    private val mockUser = UserDto(
        UserDto.Name("Mr", "John", "Doe"),
        UserDto.DateOfBirth(Date(), 29),
        UserDto.PhotoUrls("abc.com", "abc.com", "abc.com"),
        "aaa@a.com",
        "+38065653214",
        UserDto.Location("LA", "CA", "Sunset Bvd", "23402")
    )

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mockApi = retrofit.create(RandomUserApi::class.java)
        mockApi = mock {
            onBlocking { getUsers(any()) } doReturn Response.success(UsersResponse(listOf(mockUser)))
        }
        remoteUserRepository = RemoteUserRepository(mockApi)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun test_getUsers() = testScope.runBlockingTest {
        val users = remoteUserRepository.getUsers(1)
        assertEquals(listOf(mockUser), users)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_getUsers_negativeCount() = testScope.runBlockingTest {
        val users = remoteUserRepository.getUsers(-2)
    }

}