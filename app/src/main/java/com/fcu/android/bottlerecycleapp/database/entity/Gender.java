package com.fcu.android.bottlerecycleapp.database.entity;

import androidx.annotation.Nullable;

public enum Gender {
    MALE("男"),
    FEMALE("女"),
    UNDEFINED("未設定");

    private final String chineseValue;

    // Enum 的構造方法
    Gender(String chineseValue) {
        this.chineseValue = chineseValue;
    }

    // 獲取中文值的方法
    public String getChineseValue() {
        return chineseValue;
    }

    // 根據中文值獲取 Gender 枚舉
    @Nullable
    public static Gender fromChineseValue(String chineseValue) {
        for (Gender gender : Gender.values()) {
            if (gender.getChineseValue().equals(chineseValue)) {
                return gender;
            }
        }
        return null; // 或者返回 UNDEFINED 作為預設
    }
}
