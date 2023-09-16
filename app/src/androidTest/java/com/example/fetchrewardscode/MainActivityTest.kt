import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fetchrewardscode.MainActivity
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testFetchData() {
        // Use the InstrumentationRegistry to get the app context
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // Create an OkHttpClient instance
        val client = OkHttpClient()

        // Define the request
        val request = Request.Builder()
            .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            .build()

        var jsonData: String? = null

        // Use runBlocking to execute the coroutine synchronously in the test
        runBlocking {
            try {
                // Execute the HTTP request and get the response
                val response = client.newCall(request).execute()
                jsonData = response.body?.string()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        // Check if JSON data was fetched successfully
        Assert.assertNotNull(jsonData)

        // Parse JSON data
        val jsonArray = JSONArray(jsonData)

        // Create an empty map to hold the data
        val itemsMap = mutableMapOf<Int, MutableList<String>>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val listId = jsonObject.optInt("listId")
            val name = jsonObject.optString("name", null)

            if (name != "null" && !name.isNullOrEmpty()) {
                itemsMap.getOrPut(listId) { mutableListOf() }.add(name)
            }
        }

        // Check if the itemsMap is not empty
        Assert.assertTrue(itemsMap.isNotEmpty())


        // Test: Ensure that all names in the itemsMap are not blank or null
        itemsMap.forEach { (_, names) ->
            Assert.assertTrue(names.all { it != null && it.isNotBlank() })
        }

    }
}
