package com.example.myapplication;

import android.database.Cursor;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FoodDao_Impl implements FoodDao {
  private final RoomDatabase __db;

  public FoodDao_Impl(RoomDatabase __db) {
    this.__db = __db;
  }

  @Override
  public List<Food2> getAll() {
    final String _sql = "SELECT * FROM food2";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "index");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "식품명");
      final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "에너지");
      final int _cursorIndexOfProtein = CursorUtil.getColumnIndexOrThrow(_cursor, "단백질");
      final int _cursorIndexOfFat = CursorUtil.getColumnIndexOrThrow(_cursor, "지방");
      final int _cursorIndexOfCarbon = CursorUtil.getColumnIndexOrThrow(_cursor, "탄수화물");
      final int _cursorIndexOfPer1 = CursorUtil.getColumnIndexOrThrow(_cursor, "1회제공량");
      final List<Food2> _result = new ArrayList<Food2>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Food2 _item;
        final int _tmpIndex;
        _tmpIndex = _cursor.getInt(_cursorIndexOfIndex);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCalories;
        if (_cursor.isNull(_cursorIndexOfCalories)) {
          _tmpCalories = null;
        } else {
          _tmpCalories = _cursor.getString(_cursorIndexOfCalories);
        }
        final String _tmpProtein;
        if (_cursor.isNull(_cursorIndexOfProtein)) {
          _tmpProtein = null;
        } else {
          _tmpProtein = _cursor.getString(_cursorIndexOfProtein);
        }
        final String _tmpFat;
        if (_cursor.isNull(_cursorIndexOfFat)) {
          _tmpFat = null;
        } else {
          _tmpFat = _cursor.getString(_cursorIndexOfFat);
        }
        final String _tmpCarbon;
        if (_cursor.isNull(_cursorIndexOfCarbon)) {
          _tmpCarbon = null;
        } else {
          _tmpCarbon = _cursor.getString(_cursorIndexOfCarbon);
        }
        final float _tmpPer_1;
        _tmpPer_1 = _cursor.getFloat(_cursorIndexOfPer1);
        _item = new Food2(_tmpIndex,_tmpName,_tmpCalories,_tmpProtein,_tmpFat,_tmpCarbon,_tmpPer_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
