package com.example.dontwastetomuch.approvedgame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class ApprovedGameServiceTest {

    @Test
    void shouldAddAGame(){
        //given
        ApprovedGame approvedGame = new ApprovedGame("Fifa22", true);
        ApprovedGameRepo approvedGameRepo = Mockito.mock(ApprovedGameRepo.class);
        ApprovedGameService approvedGameService = new ApprovedGameService(approvedGameRepo);
        //when
        approvedGameService.addAApprovedGame(approvedGame);
        //then
        Mockito.verify(approvedGameRepo).save(approvedGame);
    }

    @Test
    void shouldListAllGames(){
        //given
        ApprovedGame approvedGame1 = new ApprovedGame("League of Legends", true);
        ApprovedGame approvedGame2 = new ApprovedGame("FIFA 22", true);
        ApprovedGame approvedGame3 = new ApprovedGame("Rocket League", true);
        ApprovedGameRepo approvedGameRepo = Mockito.mock(ApprovedGameRepo.class);
        ApprovedGameService approvedGameService = new ApprovedGameService(approvedGameRepo);
        Mockito.when(approvedGameRepo.findAll()).thenReturn(List.of(approvedGame1, approvedGame2, approvedGame3));
        //when
        List<ApprovedGame> allApprovedGames = approvedGameService.getAllGames();
        //then
        Assertions.assertThat(allApprovedGames).isEqualTo(List.of(approvedGame1, approvedGame2, approvedGame3));
        Assertions.assertThat(allApprovedGames).hasSize(3);
    }

    @Test
    void shouldGetOneGameWithGameId(){
        //given
        ApprovedGame approvedGame = new ApprovedGame("FIFA 22", true);
        ApprovedGameRepo approvedGameRepo = Mockito.mock(ApprovedGameRepo.class);
        ApprovedGameService approvedGameService = new ApprovedGameService(approvedGameRepo);
        Mockito.when(approvedGameRepo.findById("1337")).thenReturn(Optional.of(approvedGame));
        //when
        ApprovedGame actual = approvedGameService.getOneGame("1337");
        //then
        Assertions.assertThat(actual).isEqualTo(approvedGame);
    }


    @Test
    void shouldEditOneGame(){
        //given
        ApprovedGame approvedGameToEdit = new ApprovedGame("123","FIFA 22", 20, 100, true);
        ApprovedGame savedApprovedGame = new ApprovedGame("123","FIFA 22", 20, 100, true);
        ApprovedGameRepo approvedGameRepo = Mockito.mock(ApprovedGameRepo.class);
        ApprovedGameService approvedGameService = new ApprovedGameService(approvedGameRepo);
        //when
        Mockito.when(approvedGameRepo.findById("123")).thenReturn(Optional.of(savedApprovedGame));
        Mockito.when(approvedGameRepo.save(approvedGameToEdit)).thenReturn(approvedGameToEdit);
        //then
        Assertions.assertThatNoException().isThrownBy(()-> approvedGameService.editGame(approvedGameToEdit));
    }

}