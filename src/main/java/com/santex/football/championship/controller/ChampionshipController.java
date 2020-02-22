package com.santex.football.championship.controller;


import com.santex.football.championship.aspect.LogExecutionTime;
import com.santex.football.championship.client.FootballClient;
import com.santex.football.championship.controller.response.BasicResponse;
import com.santex.football.championship.controller.response.CompetitionResponse;
import com.santex.football.championship.controller.response.TotalPlayersResponse;
import com.santex.football.championship.service.ChampionshipService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController("Championship")
@Api(value = "Championship Service", description = "Provides all operations related to a championship service")
@RequestMapping("/championship")
public class ChampionshipController {

  private ChampionshipService championshipService;

  @Autowired
  public ChampionshipController(ChampionshipService championshipService){
    this.championshipService = championshipService;
  }

  @GetMapping("/import-league/{code}")
  @ApiOperation(value = "import a team", response = BasicResponse.class)
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully created"),
    @ApiResponse(code = 409, message = "Team already imported"),
    @ApiResponse(code = 404, message = "Not found"),
    @ApiResponse(code = 504, message = "Server error")
  })
  @LogExecutionTime
  @ResponseStatus(value = HttpStatus.CREATED)
  public BasicResponse importTeam(@ApiParam(value = "League code") @PathVariable(name = "code") String code){
    this.championshipService.importLeague(code);
    return new BasicResponse("Successfully Imported",Boolean.FALSE);
  }

  @GetMapping("/{code}")
  @ApiOperation(value = "get a team", response = CompetitionResponse.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Ok"),
    @ApiResponse(code = 404, message = "Not found"),
    @ApiResponse(code = 504, message = "Server error")
  })
  @LogExecutionTime
  public CompetitionResponse findByCode(@ApiParam(value = "League code") @PathVariable(name = "code") String code){
    return new CompetitionResponse(this.championshipService.findByCode(code),Boolean.FALSE,null);
  }

  @GetMapping("/total-players/{code}")
  @ApiOperation(value = "get a team", response = TotalPlayersResponse.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Ok"),
    @ApiResponse(code = 404, message = "Not found"),
    @ApiResponse(code = 504, message = "Server error")
  })
  @LogExecutionTime
  public TotalPlayersResponse getTotalPlayers(@ApiParam(value = "League code") @PathVariable(name = "code") String code){
    return new TotalPlayersResponse(this.championshipService.countPlayers(code),Boolean.FALSE,null);
  }

}
