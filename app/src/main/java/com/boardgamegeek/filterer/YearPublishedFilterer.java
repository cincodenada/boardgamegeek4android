package com.boardgamegeek.filterer;

import android.content.Context;
import android.support.annotation.NonNull;

import com.boardgamegeek.R;
import com.boardgamegeek.provider.BggContract.Games;
import com.boardgamegeek.util.MathUtils;
import com.boardgamegeek.util.StringUtils;

import java.util.Calendar;

public class YearPublishedFilterer extends CollectionFilterer {
	public static final int MIN_RANGE = 1970;
	public static final int MAX_RANGE = Calendar.getInstance().get(Calendar.YEAR) + 1;

	private int min;
	private int max;

	public YearPublishedFilterer(Context context) {
		super(context);
	}

	public YearPublishedFilterer(Context context, int min, int max) {
		super(context);
		this.min = min;
		this.max = max;
	}

	@Override
	public void setData(@NonNull String data) {
		String[] d = data.split(DELIMITER);
		min = d.length > 0 ? MathUtils.constrain(StringUtils.parseInt(d[0], MIN_RANGE), MIN_RANGE, MAX_RANGE) : MIN_RANGE;
		max = d.length > 1 ? MathUtils.constrain(StringUtils.parseInt(d[1], MAX_RANGE), MIN_RANGE, MAX_RANGE) : MAX_RANGE;
	}

	@Override
	public int getTypeResourceId() {
		return R.string.collection_filter_type_year_published;
	}

	@Override
	public String getDisplayText() {
		String text;
		String minText = String.valueOf(min);
		String maxText = String.valueOf(max);

		if (min == MIN_RANGE && max == MAX_RANGE) {
			text = "ALL";
		} else if (min == MIN_RANGE) {
			text = maxText + "-";
		} else if (max == MAX_RANGE) {
			text = minText + "+";
		} else if (min == max) {
			text = maxText;
		} else {
			text = minText + "-" + maxText;
		}

		return text;
	}

	@Override
	public String getSelection() {
		String selection;
		if (min == MIN_RANGE && max == MAX_RANGE) {
			selection = "";
		} else if (min == MIN_RANGE) {
			selection = Games.YEAR_PUBLISHED + "<=?";
		} else if (max == MAX_RANGE) {
			selection = Games.YEAR_PUBLISHED + ">=?";
		} else if (min == max) {
			selection = Games.YEAR_PUBLISHED + "=?";
		} else {
			selection = "(" + Games.YEAR_PUBLISHED + ">=? AND " + Games.YEAR_PUBLISHED + "<=?)";
		}
		return selection;
	}

	@Override
	public String[] getSelectionArgs() {
		if (min == MIN_RANGE && max == MAX_RANGE) {
			return null;
		} else if (min == MIN_RANGE) {
			return new String[] { String.valueOf(max) };
		} else if (max == MAX_RANGE) {
			return new String[] { String.valueOf(min) };
		} else if (min == max) {
			return new String[] { String.valueOf(min) };
		} else {
			return new String[] { String.valueOf(min), String.valueOf(max) };
		}
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@NonNull
	@Override
	public String flatten() {
		return String.valueOf(min) + DELIMITER + String.valueOf(max);
	}
}
