
import com.niksatyr.randomuser.dto.UserDto
import com.niksatyr.randomuser.model.User
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.*

class UserFactoryTest {

    @Test
    fun test_createUserFromDto() {
        val userDto = UserDto(
            UserDto.Name("Mr", "John", "Doe"),
            UserDto.DateOfBirth(Date(), 29),
            UserDto.PhotoUrls("abc.com", "abc.com", "abc.com"),
            "aaa@a.com",
            "+38065653214",
            UserDto.Location(UserDto.Street(1, "Sunset Bvd"), "LA", "CA", "23402")
        )
        val expectedUser = User(
            "Mr John Doe",
            Date(), 29,
            "abc.com", "abc.com", "abc.com",
            "aaa@a.com",
            "+38065653214",
            "LA, CA, 1 Sunset Bvd, 23402"
        )
        assertEquals(expectedUser.toString(), User.Factory.create(userDto).toString())
    }

}