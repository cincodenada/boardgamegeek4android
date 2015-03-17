/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// FROM com.google.android.apps.iosched.util;

package com.boardgamegeek.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import timber.log.Timber;

/**
 * Proxy {@link android.os.ResultReceiver} that offers a listener interface that can be detached. Useful for when
 * sending callbacks to a {@link android.app.Service} where a listening {@link android.app.Activity} can be swapped out
 * during configuration changes.
 */
public class DetachableResultReceiver extends ResultReceiver {
	private Receiver mReceiver;

	public DetachableResultReceiver(Handler handler) {
		super(handler);
	}

	public DetachableResultReceiver(Receiver receiver) {
		super(new Handler());
		mReceiver = receiver;
	}

	public void clearReceiver() {
		mReceiver = null;
	}

	public void setReceiver(Receiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (mReceiver != null) {
			mReceiver.onReceiveResult(resultCode, resultData);
		} else {
			Timber.w("Dropping result on floor for code " + resultCode + ": " + resultData.toString());
		}
	}

	public interface Receiver {
		public void onReceiveResult(int resultCode, Bundle resultData);
	}
}