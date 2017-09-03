package rest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@Tag("rest")
public class RestApiTests {

    public static final String URL_API_GITHUB_COM_USERS = "https://api.github.com/users/";

    @Test
    public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
            throws IOException {

        // Given
        String name = RandomStringUtils.randomAlphabetic( 8 );
        HttpUriRequest request = new HttpGet( URL_API_GITHUB_COM_USERS + name );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        assumingThat(HttpStatus.SC_FORBIDDEN != httpResponse.getStatusLine().getStatusCode(), () -> {
            assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
        });
    }

    @Test
    public void givenRequestWithNoAcceptHeader_whenRequestIsExecuted_thenDefaultResponseContentTypeIsJson()
            throws IOException {

        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet( URL_API_GITHUB_COM_USERS + "irwansyahrir" );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );
    }

    @Test
    public void givenUserExists_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect()
            throws IOException {

        // Given
        HttpUriRequest request = new HttpGet( URL_API_GITHUB_COM_USERS + "irwansyahrir" );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // Then
        GitHubUser resource = RetrieveUtil.retrieveResourceFromResponse(response, GitHubUser.class);
        assertEquals("irwansyahrir", resource.getLogin());
    }

    @ParameterizedTest
    @ValueSource(strings = { "eugenp", "irwansyahrir", "whatever", "harrypotter" , "trump"})
    void givenVariousUsers_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect(String userName) throws IOException {
        HttpUriRequest request = new HttpGet(URL_API_GITHUB_COM_USERS + userName);

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assumingThat(HttpStatus.SC_FORBIDDEN != response.getStatusLine().getStatusCode(),
                () -> {
                    GitHubUser resource = RetrieveUtil.retrieveResourceFromResponse(response, GitHubUser.class);
                    assertNotNull(resource.getLogin());
                });

    }

    @Test
    @Disabled
    void testRepos() throws IOException {

        HttpUriRequest request = new HttpGet(URL_API_GITHUB_COM_USERS + "irwansyahrir/repos");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals("", response.toString());

    }
}
