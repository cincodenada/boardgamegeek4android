package com.boardgamegeek.util.shortcut;

import android.content.Context;
import android.content.Intent;

import com.boardgamegeek.util.ShortcutUtils;

public class GameShortcutTask extends ShortcutTask {
	private final int gameId;
	private final String gameName;

	public GameShortcutTask(Context context, int gameId, String gameName, String thumbnailUrl) {
		super(context, thumbnailUrl);
		this.gameId = gameId;
		this.gameName = gameName;
	}

	@Override
	protected Intent createIntent() {
		return ShortcutUtils.createGameShortcutIntent(context, gameId, gameName);
	}
}
