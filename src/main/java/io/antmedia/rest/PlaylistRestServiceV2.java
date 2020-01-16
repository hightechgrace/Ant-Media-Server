package io.antmedia.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import io.antmedia.datastore.db.types.Playlist;
import io.antmedia.rest.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@Api(value = "Playlist Rest Service")
@SwaggerDefinition(
		info = @Info(
				description = "Ant Media Server REST API Reference",
				version = "V2.0",
				title = "Ant Media Server REST API Reference",
				contact = @Contact(name = "Ant Media Info", email = "contact@antmedia.io", url = "https://antmedia.io"),
				license = @License(name = "Apache 2.0", url = "http://www.apache.org")),
		consumes = {"application/json"},
		produces = {"application/json"},
		schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
		externalDocs = @ExternalDocs(value = "External Docs", url = "https://antmedia.io"),
		basePath = "/v2/Playlist"
		)
@Component
@Path("/v2/playlists")
public class PlaylistRestServiceV2 extends RestServiceBase{


	@ApiOperation(value = "Playlist list from database", response = Playlist.class)
	@GET
	@Path("/{playlistId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Playlist getPlaylist(@ApiParam(value = "id of the Playlist", required = true) @PathParam("playlistId") String playlistId) {
		Playlist playlist = null;
		if (playlistId != null) {
			playlist = getDataStore().getPlaylist(playlistId);
		}
		if (playlistId == null) {
			playlist = new Playlist();
		}
		return playlist;
	}

	@ApiOperation(value = "Delete specific Playlist", response = Result.class)
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/{playlistId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Result deletePlaylist(@ApiParam(value = "the playlistId of the Playlist", required = true) @PathParam("playlistId") String playlistId) {

		Result result = new Result(false);

		if(playlistId != null) {
			result.setSuccess(getDataStore().deletePlaylist(playlistId));
			return result;
		}
		return result;
	}


	@ApiOperation(value = "Create Playlist", notes = "", response = Result.class)
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Result createPlaylist(@ApiParam(value = "the name of the Playlist File", required = true) Playlist playlist) {

		Result result = new Result(false);

		try {
			result.setSuccess(getDataStore().createPlaylist(playlist));
			return result;

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	@ApiOperation(value = "Edit Playlist", notes = "", response = Result.class)
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/edit/{playlistId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Result editPlaylist(@ApiParam(value="id of the Playlist") @PathParam("playlistId") String playlistId, @ApiParam(value = "the name of the Playlist File", required = true) Playlist playlist) {

		Result result = new Result(false);

		try {
			result.setSuccess(getDataStore().editPlaylist(playlistId,playlist));
			return result;

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

}