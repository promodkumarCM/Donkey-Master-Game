package com.sparklingapps.cardgamefrog.interfaces;

import com.sparklingapps.cardgamefrog.enums.GameStartEnum;
import com.sparklingapps.cardgamefrog.model.Room;

public interface onClickDialog {


   void onClickDialogStartGame(Room roomId, GameStartEnum gameCase);
   void onClickDialogJoinGame(int roomId, GameStartEnum gameCase);


}
