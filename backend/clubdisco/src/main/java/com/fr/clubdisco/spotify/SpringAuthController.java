package com.fr.clubdisco.spotify;

import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.browse.GetListOfNewReleasesRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/spotify")
public class SpringAuthController {

    private final static String clientID = "acb7c96f38634c63a1679da69fe4ebe9";

    private final static String clientSecret = "6ade0711682142f191831976ff89131a";

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/spotify/get-user-code");

    private String code= "";

    private static final ModelObjectType type = ModelObjectType.ARTIST;


    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    @GetMapping("login")
    @ResponseBody
    public String spotifyLogin(){
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("user-read-private, user-read-email, user-top-read")
                .show_dialog(true)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    @GetMapping(value = "get-user-code")
    public String getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException{
        code = userCode;
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();

        try{
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("expire in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println(e.getMessage());
        }

        response.sendRedirect("http://localhost:8081/dashboard");
        return spotifyApi.getAccessToken();
    }

    @GetMapping(value = "user-playlists")
    public PlaylistSimplified[] getUserPlaylists() {
        final GetListOfCurrentUsersPlaylistsRequest getUsersPlaylistsRequest = spotifyApi.getListOfCurrentUsersPlaylists().build();

        try {
            final Paging<PlaylistSimplified> artistPaging = getUsersPlaylistsRequest.execute();
            return artistPaging.getItems();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new PlaylistSimplified[0];
    }

    @GetMapping(value = "user-profil")
    public User getUserProfil() {
        final   GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();

        try {
            final User user = getCurrentUsersProfileRequest.execute();
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping(value = "user-followed-artists")
    public Artist[] getUserFollowedArtists() {
        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().limit(5).build();

        try {
            final CompletableFuture<Paging<Artist>> pagingCursorbasedFuture = getUsersTopArtistsRequest.executeAsync();
            final Paging<Artist> artistPagingCursorbased = pagingCursorbasedFuture.join();
            return artistPagingCursorbased.getItems();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Artist[0];
    }

    @GetMapping(value = "user-fav-tracks")
    public Track[] getUserTopTracks() {
        final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks().limit(5).build();

        try {
            final CompletableFuture<Paging<Track>> pagingFuture = getUsersTopTracksRequest.executeAsync();
            final Paging<Track> trackPaging = pagingFuture.join();
            return trackPaging.getItems();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Track[0];
    }

    @GetMapping(value = "new-releases")
    public AlbumSimplified[] getNewReleases() {
        final GetListOfNewReleasesRequest getListOfNewReleasesRequest = spotifyApi.getListOfNewReleases().limit(5).build();

        try {
            final Paging<AlbumSimplified> albumSimplifiedPaging = getListOfNewReleasesRequest.execute();
            return albumSimplifiedPaging.getItems();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new AlbumSimplified[0];
    }
}
